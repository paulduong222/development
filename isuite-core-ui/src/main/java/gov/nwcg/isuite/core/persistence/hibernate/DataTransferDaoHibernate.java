package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.core.domain.impl.DataTransferImpl;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.DataTransferQuery;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class DataTransferDaoHibernate extends TransactionSupportImpl implements DataTransferDao {

	private final CrudDao<DataTransfer> crudDao;

	/**
	 * Constructor.
	 * @param crudDao can't be null
	 * @param transferableDao can't be null
	 */
	public DataTransferDaoHibernate(final CrudDao<DataTransfer> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(DataTransfer persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public DataTransfer getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(DataTransfer persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<DataTransfer> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.DataTransferDao#getExportHistory(java.lang.Long, java.lang.Long)
	 */
	public Collection<DataTransfer> getExportHistory(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(DataTransferImpl.class);
		
		if(LongUtility.hasValue(incidentId)){
			crit.add(Restrictions.eq("incidentId", incidentId));
		}
		
		if(LongUtility.hasValue(incidentGroupId)){
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		}
		
		return crit.list();
	}

	public Long executeQueryUniqueId(String sql) throws PersistenceException {
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object val=query.uniqueResult();
		Long id=0L;
		
		if(null != val){
			try{
				id=TypeConverter.convertToLong(val);
			}catch(Exception e){
			}
		}
		return id;
	}

	public Object executeQuery(String sql) throws PersistenceException {
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslts=query.list();
		
		return rslts;
	}

	public int executeUpdate(String sql) throws PersistenceException {
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object result=query.executeUpdate();
		int rslt=-1;
		try{
			if(null != result)
				rslt=TypeConverter.convertToInt(result);
			
		}catch(Exception e){
			
		}
		return rslt;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.DataTransferDao#getFileList()
	 */
	public Collection<DataTransferVo> getFileList() throws PersistenceException {
		String sql = DataTransferQuery.getFileListQuery();
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(DataTransferVo.class);
		
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		crt.addScalar("incidentGroupId", Long.class.getName());
		crt.addScalar("createdDate", Date.class.getName());

 	    crt.addProjection("id", "id");
 	    crt.addProjection("incidentId", "incidentId");
 	    crt.addProjection("incidentGroupId", "incidentGroupId");
 	    crt.addProjection("createdDate", "createdDate");
 	    crt.addProjection("storedFilepath", "storedFilepath");
 	    crt.addProjection("filename", "filename");
		
		query.setResultTransformer(crt); 

		return query.list();
	}
	
	public void doGroupSiteManagedFalse(String ti) throws PersistenceException{
		String sql="update isw_incident_group set is_site_managed='N' where transferable_identity='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
		
		sql="update isw_incident set is_site_managed='N' "+
			"where id in ("+
			"  select incident_id from isw_incident_group_incident "+
			"  where incident_group_id in (select id from isw_incident_group where transferable_identity='"+ti+"') " +
			")";
		q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}
	
	public void doIncidentSiteManagedFalse(String ti) throws PersistenceException{
		String sql="update isw_incident set is_site_managed='N' where transferable_identity='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	public void addGroupUser(String ti, Long userId) throws PersistenceException {
		String sql = "select count(id) from isw_incident_group_user " +
					 "where user_id = " + userId + " " +
					 "and incident_group_id = (select id from isw_incident_group where transferable_identity='" + ti + "') ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt < 1) {
					sql="insert into isw_incident_group_user (id, incident_group_id, user_id) "+
					    "values (seq_incident_group_user.nextVal,"+
					    "(select id from isw_incident_group where transferable_identity='"+ti+"')," +
					    userId+")";
					q = getHibernateSession().createSQLQuery(sql);
					q.executeUpdate();
				}
			}catch(Exception e){
			}
		}
		
	}
	
	public void addIncidentUser(String ti, Long userId) throws PersistenceException {
		String sql = "select count(id) from isw_restricted_incident_user " +
	 	  			 "where user_id = " + userId + " " +
	 	  			 "and incident_id = (select id from isw_incident where transferable_identity='" + ti + "') ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt < 1){
					sql="insert into isw_restricted_incident_user (id, incident_id, user_id,user_type) "+
					    "values (seq_restricted_incident_user.nextVal,"+
					    "(select id from isw_incident where transferable_identity='"+ti+"')," +
					    userId+", 'OWNER')";
					q = getHibernateSession().createSQLQuery(sql);
					q.executeUpdate();
				}
			}catch(Exception e){
			}
		}
		
	}

	public void addIncidentUser(Long id, Long userId) throws PersistenceException {
		String sql = "select count(id) from isw_restricted_incident_user " +
	 	  			 "where user_id = " + userId + " " +
	 	  			 "and incident_id = " + id + " ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt < 1){
					sql="insert into isw_restricted_incident_user (id, incident_id, user_id,user_type) "+
					    "values (seq_restricted_incident_user.nextVal,"+
					    id+","+
					    userId+", 'OWNER')";
					q = getHibernateSession().createSQLQuery(sql);
					q.executeUpdate();
				}
			}catch(Exception e){
			}
		}
		
	}
	
}
