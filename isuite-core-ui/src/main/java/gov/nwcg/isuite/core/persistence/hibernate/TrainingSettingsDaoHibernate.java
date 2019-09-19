package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.core.domain.impl.TrainingSettingsImpl;
import gov.nwcg.isuite.core.persistence.TrainingSettingsDao;
import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class TrainingSettingsDaoHibernate extends TransactionSupportImpl
		implements TrainingSettingsDao {
	
	private final CrudDao<TrainingSettings> crudDao;
	
	public TrainingSettingsDaoHibernate(final CrudDao<TrainingSettings> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TrainingSettings persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TrainingSettings getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TrainingSettings persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<TrainingSettings> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TrainingSettingsVo> getTrainingSettings(Long incidentId, Long incidentGroupId) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(TrainingSettingsImpl.class);
		 
		 if(LongUtility.hasValue(incidentId)){
			 crit.add(Restrictions.eq("incidentId", incidentId));
		 }
		 
		 if(LongUtility.hasValue(incidentGroupId)){
			 String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")";
			 crit.add(Restrictions.sqlRestriction(sqlFilter));
		 }
		 
		 Collection<TrainingSettings> entities = crit.list();
		 
		 try{
			 return TrainingSettingsVo.getInstances(entities, true);
		 }catch (Exception e) {
			 throw new PersistenceException(e);
		 }
	}
	
//	public TrainingSettings getTrainingSettings(Long incidentId, Long incidentGroupId) throws PersistenceException {
//		 Criteria crit = getHibernateSession().createCriteria(TrainingSettingsImpl.class);
//		 
//		 if(LongUtility.hasValue(incidentId)){
//			 crit.add(Restrictions.eq("incidentId", incidentId));
//		 }
//		 
//		 if(LongUtility.hasValue(incidentGroupId)){
//			 String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")";
//			 crit.add(Restrictions.sqlRestriction(sqlFilter));
//		 }
//		 
//		 return (TrainingSettings)crit.uniqueResult();
//	}
	
	public TrainingSettings getByIncidentGroupId (Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(TrainingSettingsImpl.class);
		
		if(LongUtility.hasValue(incidentGroupId)){
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		}
		
		return (TrainingSettings)crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TrainingSettings> getByIncidentIds(Collection<Long> incidentIds) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(TrainingSettingsImpl.class);
		
		crit.add(Restrictions.in("incidentId", incidentIds));
		
		return crit.list();
	}

}
