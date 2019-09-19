package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.core.domain.impl.BranchSettingPositionImpl;
import gov.nwcg.isuite.core.persistence.BranchSettingPositionDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class BranchSettingPositionDaoHibernate extends TransactionSupportImpl implements BranchSettingPositionDao {
	private final CrudDao<BranchSettingPosition> crudDao;
	
	public BranchSettingPositionDaoHibernate(final CrudDao<BranchSettingPosition> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(BranchSettingPosition persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public BranchSettingPosition getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(BranchSettingPosition persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<BranchSettingPosition> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.BranchSettingPositionDao#getByIncidentorGroupId(java.lang.Long, java.lang.Long)
	 */
	public Collection<BranchSettingPosition> getByIncidentorGroupId(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(BranchSettingPositionImpl.class);
		
		if(LongUtility.hasValue(incidentId)){
			crit.add(Restrictions.eq("incidentId", incidentId));
		}
		
		if(LongUtility.hasValue(incidentGroupId)){
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		}
		
		return crit.list();
	}

	public Collection<BranchSettingPosition> getByBranchAndPosition(Long branchSettingId, String position) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(BranchSettingPositionImpl.class);
		
		crit.add(Restrictions.eq("branchSettingId", branchSettingId));
		crit.add(Restrictions.eq("position",StringUtility.toUpper(position)));
		
		Collection<BranchSettingPosition> entities = (Collection<BranchSettingPosition>)crit.list();
		
		return entities;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.BranchSettingPositionDao#isDuplicatePosition(java.lang.Long, java.lang.String)
	 */
	public Boolean isDuplicatePosition(Long BranchSettingPositionId, String position) throws PersistenceException {
		Boolean val=false;
		
		String sql = "SELECT COUNT(ID) FROM ISW_BRANCH_SETTING_POSITION WHERE BRANCH_SETTING_ID = "+BranchSettingPositionId + " " +
					 "AND UPPER(POSITION) = UPPER('" + position + "') ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt>0)
					val=true;
			}
		}catch(Exception e){
		}
		
		return val;
	}

	public void updateBranchSettingPosition(Long branchSettingId, String position, Collection<Long> kindIds) throws PersistenceException {
		String sequence = (super.isOracleDialect()==true ? "SEQ_BRANCH_SETTING_POSITION.nextVal" : "nextVal('SEQ_BRANCH_SETTING_POSITION')");
		
		String sql1="INSERT INTO ISW_BRANCH_SETTING_POSITION (ID, POSITION, KIND_ID ) " +
				    "SELECT :seq, :position, ID " +
				    "FROM ISWL_KIND "+
				    "WHERE ID NOT IN ( :kindids ) ";
		sql1=sql1.replaceAll(":seq", sequence);
		sql1=sql1.replaceAll(":position", position.toUpperCase());
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		query1.setParameterList("kindids", kindIds);
		query1.executeUpdate();
		
		String sql2="DELETE FROM ISW_BRANCH_SETTING_POSITION  " +
	    			"WHERE BRANCH_SETTING_ID = " + branchSettingId + " " +
	    			"AND UPPER(POSITION) = UPPER('" + StringUtility.toUpper(position) + "') " +
   					"AND KIND_ID NOT IN ( :kindids ) " ;
		
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
		query2.setParameterList("kindids", kindIds);
		query2.executeUpdate();

	}
}
