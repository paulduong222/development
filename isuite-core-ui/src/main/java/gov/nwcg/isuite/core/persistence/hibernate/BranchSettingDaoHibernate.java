package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.impl.BranchSettingImpl;
import gov.nwcg.isuite.core.persistence.BranchSettingDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IapDefaultSettingsQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class BranchSettingDaoHibernate extends TransactionSupportImpl implements BranchSettingDao {
	private final CrudDao<BranchSetting> crudDao;
	
	public BranchSettingDaoHibernate(final CrudDao<BranchSetting> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(BranchSetting persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public BranchSetting getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(BranchSetting persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<BranchSetting> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.BranchSettingDao#getByIncidentorGroupId(java.lang.Long, java.lang.Long)
	 */
	public Collection<BranchSetting> getByIncidentorGroupId(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(BranchSettingImpl.class);
		
		if(LongUtility.hasValue(incidentId)){
			crit.add(Restrictions.eq("incidentId", incidentId));
		}
		
		if(LongUtility.hasValue(incidentGroupId)){
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		}
		
		return crit.list();
	}
	
	public void createDefaultPositions(Long branchSettingId) throws PersistenceException {
		String sequence = (super.isOracleDialect()==true ? "SEQ_BRANCH_SETTING_POSITION.nextVal" : "nextVal('SEQ_BRANCH_SETTING_POSITION')");
		for(IapDefaultSettingsQuery._203BranchSettings _203BranchEnum : IapDefaultSettingsQuery._203BranchSettings.values()){
			String sql = _203BranchEnum.sql;
			sql=sql.replaceAll(":seqId", sequence);
			sql=sql.replaceAll(":branchSettingId", String.valueOf(branchSettingId));
			
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.executeUpdate();
			
		}
	}
	

}
