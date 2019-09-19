package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.RscTrainingTrainerImpl;
import gov.nwcg.isuite.core.persistence.ResourceTrainingTrainerDao;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.OrderBySql;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ResourceTrainingTrainerDaoHibernate extends TransactionSupportImpl
		implements ResourceTrainingTrainerDao {
	
	private final CrudDao<RscTrainingTrainer> crudDao;
	
	public ResourceTrainingTrainerDaoHibernate(final CrudDao<RscTrainingTrainer> crudDao) {

	      if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao cannot be null");
	      }
	      this.crudDao = crudDao;
      
	 }

	@Override
	public void delete(RscTrainingTrainer persistable) throws PersistenceException {
		 crudDao.delete(persistable);
	}

	@Override
	public RscTrainingTrainer getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, RscTrainingTrainerImpl.class);
	}

	@Override
	public void save(RscTrainingTrainer persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void saveAll(Collection<RscTrainingTrainer> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
//	@SuppressWarnings("unchecked")
//	public Collection<RscTrainingTrainerVo> getEvalIncResGrid(Long incidentResourceId, Long incidentId, Long incidentGroupId) throws PersistenceException {
//		String sql = TrainingSpecialistQuery.getIncidentEvaluatorsGrid(super.isOracleDialect(), incidentResourceId, incidentId, incidentGroupId);
//		
//		SQLQuery query = getHibernateSession().createSQLQuery(sql);
//		
//		CustomResultTransformer crt = new CustomResultTransformer(RscTrainingTrainerVo.class);
//		
//		crt.addScalar("id", Long.class.getName());
//		crt.addScalar("active", Boolean.class.getName());
//		crt.addScalar("itemCodeId", Long.class.getName());
//		crt.addScalar("organizationId", Long.class.getName());
//		
//		query.setResultTransformer(crt);
//		
//		return query.list();
//	}
	
	@SuppressWarnings("unchecked")
	public Collection<RscTrainingTrainerVo> getEvalIncResGrid(Long incidentResourceId, Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);
		
//		crit.createAlias("rscTrainingTrainer", "rtt", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("workPeriod", "wp");
		crit.createAlias("wp.assignments", "a");
		crit.createAlias("resource", "r");
		
		crit.add(Restrictions.isNull("a.endDate"));
		crit.add(Restrictions.ne("id", incidentResourceId));
		crit.add(Restrictions.eq("r.person", Boolean.TRUE));
		
		if(LongUtility.hasValue(incidentId)){
			 crit.add(Restrictions.eq("incidentId", incidentId));
		 }
		 
		 if(LongUtility.hasValue(incidentGroupId)){
			 String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")";
			 crit.add(Restrictions.sqlRestriction(sqlFilter));
		 }
		 
//		 String orderSql = "sortRequestNumber(a3_.request_number)";
		 String orderSql = "sortRequestNumber(a2_.request_number)";
		 crit.addOrder(OrderBySql.sql(orderSql));
		 
		 Collection<IncidentResource> entities = crit.list();
		 
		 try {
			 return RscTrainingTrainerVo.getInstancesIncRes(entities);
		 }catch (Exception e) {
			 throw new PersistenceException(e);
		 }
	}
	

}
