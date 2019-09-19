package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import gov.nwcg.isuite.core.domain.TrainingContact;
import gov.nwcg.isuite.core.domain.impl.TrainingContactImpl;
import gov.nwcg.isuite.core.persistence.TrainingContactDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.TrainingSpecialistQuery;
import gov.nwcg.isuite.core.vo.TrainingContactVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.OrderBySql;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

public class TrainingContactDaoHibernate extends TransactionSupportImpl
		implements TrainingContactDao {
	
	private final CrudDao<TrainingContact> crudDao;
	
	public TrainingContactDaoHibernate(final CrudDao<TrainingContact> crudDao) {
		
		if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao cannot be null");
	      }
	      this.crudDao = crudDao;
	}

	@Override
	public void delete(TrainingContact persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public TrainingContact getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id,TrainingContactImpl.class);
	}

	@Override
	public void save(TrainingContact persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void saveAll(Collection<TrainingContact> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TrainingContactVo> getContactResourcesGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		String sql = TrainingSpecialistQuery.getContactResourcesGrid(super.isOracleDialect(), incidentId, incidentGroupId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(TrainingContactVo.class);
		
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("active", Boolean.class.getName());
		
		query.setResultTransformer(crt);
		
		return query.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TrainingContactVo> getTrainingContactGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(TrainingContactImpl.class);
		
		crit.createAlias("incidentResource", "ir");
		crit.createAlias("ir.workPeriod", "wp");
		crit.createAlias("wp.assignments", "a");
		crit.createAlias("ir.resource", "r");
		
		crit.add(Restrictions.isNull("a.endDate"));
		//crit.add(Restrictions.isNull("r.parentResourceId"));
		crit.add(Restrictions.isNull("r.deletedDate"));
		
		if(LongUtility.hasValue(incidentId)){
			 crit.add(Restrictions.eq("ir.incidentId", incidentId));
		 }
		 
		 if(LongUtility.hasValue(incidentGroupId)){
			 String sqlFilter = "incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")";
			 crit.add(Restrictions.sqlRestriction(sqlFilter));
		 }
		 
		 String orderSql = "sortRequestNumber(a3_.request_number)";
		 crit.addOrder(OrderBySql.sql(orderSql));
		 
		 Collection<TrainingContact> entities = crit.list();
		 
		 try {
			 return TrainingContactVo.getInstances(entities, true);
		 }catch (Exception e) {
			 throw new PersistenceException(e);
		 }
		
	}
	
//	@SuppressWarnings("unchecked")
//	public Collection<TrainingContactVo> getTrainingContactGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
//		String sql = TrainingSpecialistQuery.getTrainingContactGrid(super.isOracleDialect(), incidentId, incidentGroupId);
//		
//		SQLQuery query = getHibernateSession().createSQLQuery(sql);
//		
//		CustomResultTransformer crt = new CustomResultTransformer(TrainingContactVo.class);
//		
//		crt.addScalar("incidentResourceId", Long.class.getName());
//		crt.addScalar("active", Boolean.class.getName());
//		
//		query.setResultTransformer(crt);
//		
//		return query.list();
//		
//	}
	
}
