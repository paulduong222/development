package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossBlacklistQuery;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.RossIncDataBlacklistGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import org.hibernate.SQLQuery;

public class RossIncDataBlacklistDaoHibernate extends TransactionSupportImpl implements RossIncDataBlacklistDao{
	private final CrudDao<RossIncDataBlacklist> crudDao;

	public RossIncDataBlacklistDaoHibernate(final CrudDao<RossIncDataBlacklist> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(RossIncDataBlacklist persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public void deleteByRossIncidentId(String rossIncidentId) throws PersistenceException {
		String sql = "DELETE FROM ISW_ROSS_INC_DATA_BLACKLIST "+
				     "WHERE ROSS_INC_ID = '" + rossIncidentId + "' ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void deleteByRossIncIdResReqId(String rossIncidentId, Long reqId) throws PersistenceException {
		String sql = "DELETE FROM ISW_ROSS_INC_DATA_BLACKLIST "+
				     "WHERE ROSS_INC_ID = '" + rossIncidentId + "' " +
				     "AND ROSS_RES_REQ_ID = " + reqId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RossIncDataBlacklist getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(RossIncDataBlacklist persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RossIncDataBlacklist> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao#blacklistResources(java.lang.Long, java.util.Collection)
	 */
	public void blacklistResources(Long rossXmlFileId, Collection<Long> rossResourceIds) throws PersistenceException {
		
		for(Long id : rossResourceIds){
			String sql = RossBlacklistQuery.getRossIncDataInsertQuery(super.isOracleDialect(), rossXmlFileId);
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.setParameter("resid", id);
			query.executeUpdate();
		}
	}

	public Collection<IncidentGridVo> getRossIncidents(IncidentFilter filter) throws PersistenceException {
		String sql = RossBlacklistQuery.getRossIncidentsQuery(super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(IncidentGridVo.class);

		crt.addScalar("id", Long.class.getName());
		crt.addScalar("rossXmlFileId", Long.class.getName());
		crt.addScalar("incidentStartDt", Date.class.getName());

		query.setResultTransformer(crt); 
		
		return query.list();
	}
	
	public Collection<RossIncDataBlacklistGridVo> getByRossIncId(String rossIncidentId) throws PersistenceException {

		String sql = RossBlacklistQuery.getRossIncExcludedResources(super.isOracleDialect(), rossIncidentId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(RossIncDataBlacklistGridVo.class);

		crt.addScalar("rossResReqId", Long.class.getName());

		query.setResultTransformer(crt); 
		
		return query.list();
		
	}

	public void updateStatuses(Collection<Long> rossResourceIds, Long rxfId, String status) throws PersistenceException {

		if(null != rossResourceIds && rossResourceIds.size()>999){
			/*
			 * Split out rossResourceIds in chunks of 999
			 */
			Hashtable table = CollectionUtility.splitCollection(rossResourceIds, 999);
			
			for(int i=1;i<(table.size()+1);i++){
				Collection<Long> ids = (Collection<Long>)table.get(i);

				String sql = RossBlacklistQuery.getUpdateStatuses(super.isOracleDialect(), rxfId, status);
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.setParameterList("ids", ids);
				
				query.executeUpdate();
				
			}
		}else{
			String sql = RossBlacklistQuery.getUpdateStatuses(super.isOracleDialect(), rxfId, status);
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.setParameterList("ids", rossResourceIds);
			
			query.executeUpdate();
		}
	}
	
	public void createExcludedResources(Collection<Long> rossXmlFileDataIds,String rossIncId) throws PersistenceException {
		StringBuffer sql = new StringBuffer()
			.append("INSERT INTO ISW_ROSS_INC_DATA_BLACKLIST ")
			.append(" (ID, RES_ID, ROSS_RES_REQ_ID, ROSS_INC_ID, IMPORT_STATUS ) ")
			.append("SELECT ");
		if(super.isOracleDialect()){
			sql.append("SEQ_ISW_ROSS_INC_DATA_BL.nextVal, ");
		}else{
			sql.append("nextVal('SEQ_ISW_ROSS_INC_DATA_BL'), ");
		}
		sql.append("rxfd.REQ_ID, rxfd.REQ_ID, "+rossIncId+", '' ");
		sql.append("FROM ISW_ROSS_XML_FILE_DATA rxfd ");
		sql.append("WHERE rxfd.id in ( :ids ) ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.setParameterList("ids", rossXmlFileDataIds);
		
		query.executeUpdate();
		
		StringBuffer sql2 = new StringBuffer()
		.append("UPDATE ISW_ROSS_XML_FILE_DATA ")
		.append("SET IMPORT_STATUS = 'EXCLUDED' ")
		.append("WHERE id in ( :ids ) ");
		
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2.toString());

		query2.setParameterList("ids", rossXmlFileDataIds);
		
		query2.executeUpdate();
		
	}
}
