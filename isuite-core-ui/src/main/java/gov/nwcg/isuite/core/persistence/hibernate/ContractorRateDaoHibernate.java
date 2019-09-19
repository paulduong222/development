package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.impl.ContractorRateImpl;
import gov.nwcg.isuite.core.filter.ContractorRateFilter;
import gov.nwcg.isuite.core.persistence.ContractorRateDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ContractorRateQuery;
import gov.nwcg.isuite.core.vo.ContractorRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class ContractorRateDaoHibernate extends TransactionSupportImpl implements ContractorRateDao {
	private final CrudDao<ContractorRate> crudDao;

	public ContractorRateDaoHibernate(final CrudDao<ContractorRate> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getGrid(gov.nwcg.isuite.core.filter.ContractorFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContractorRateGridVo> getGrid(ContractorRateFilter filter) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(ContractorRateImpl.class);
		   
//		   crit.createAlias("adminOffice", "admin", CriteriaSpecification.LEFT_JOIN);
//		   crit.createAlias("contractor", "contr");
//		   
//		   /*
//		    * Define how we are going to transform the result to the return object
//		    */
//		   crit.setProjection(Projections.projectionList()	             
//	             .add(Projections.property("contr.id"), "contractorId")
//	             .add(Projections.property("admin.id"), "adminOfficeId")
//	             .add(Projections.property("admin.officeName"), "officeName")
//	             .add(Projections.property("RateNumber"),"RateNumber")
//	             .add(Projections.property("startDate"),"startDate") 
//	             .add(Projections.property("endDate"),"endDate")
//	             .add(Projections.property("pointOfHire"),"pointOfHire") 
//	             .add(Projections.property("deletedDate"),"deletedDate")             
//		   		 .add(Projections.property("id"),"id"));
//		   
//		   
		   crit.setResultTransformer(Transformers.aliasToBean(ContractorRateGridVo.class));

		   if (filter != null) {
			   try
			   { 	   
//				   /*
//				    * Add the filter criteria
//				    */
//				   
//				   // TYPE_ILIKE
//				   if (filter.getRateNumber() != null && !"".equals(filter.getRateNumber()))
//				   { crit.add(Restrictions.ilike("this.RateNumber", filter.getRateNumber(), MatchMode.START)); }
//				   if (filter.getPointOfHire() != null && !"".equals(filter.getPointOfHire()))
//				   { crit.add(Restrictions.ilike("this.pointOfHire", filter.getPointOfHire(), MatchMode.START)); }
//				   if ( null != filter.getAdminOffice() && !filter.getAdminOffice().isEmpty()) {
//					   crit.add(Restrictions.ilike("admin.officeName", filter.getAdminOffice(), MatchMode.START));
//				   }
//				   	   
//					// TYPE_ISNULL
//				   if (filter.getDeletedDate() == null)
//				   { crit.add(Restrictions.isNull("this.deletedDate")); }
//
//				   // TYPE_ISNOTNULL
//				   if (filter.getDeletedDate() != null)
//				   { crit.add(Restrictions.isNotNull("this.deletedDate")); }
//				   
//				   // TYPE_EQ
//				   if (filter.getContractorId() != null && filter.getContractorId() != 0) {
//					   crit.add(Restrictions.eq("this.contractorId", filter.getContractorId()));
//				   }
//				   if (filter.getAdminOfficeId() != null && !"".equals(filter.getAdminOfficeId()))
//				   { crit.add(Restrictions.eq("this.adminOfficeId", filter.getAdminOfficeId())); }					   
//				   if (filter.getContractorId() != null && !"".equals(filter.getContractorId()))
//				   { crit.add(Restrictions.eq("this.contractorId", filter.getContractorId())); }
//				   if (filter.getStartDate() != null && !"".equals(filter.getStartDate()))
//				   { crit.add(Restrictions.ge("this.startDate", filter.getStartDate())); }
//				   if (filter.getEndDate() != null && !"".equals(filter.getEndDate()))
//				   { crit.add(Restrictions.ge("this.endDate", filter.getEndDate())); }
//				   
//				   
//				   if(filter.getDeletable() != null) {
//					   if(filter.getDeletable() == false) {
//					      //TODO:  Add deletable criteria. 
//					   } else {
//					      //TODO:  Add non-deletable criteria. 
//					      return null;
//					   }
//					}
			   }
			   catch(Exception e)
			   {
				   throw new PersistenceException(e);
			   }
		   }

		   try{
			   return crit.list();
		   }catch(Exception e){
			   System.out.println(e.getMessage().toString());
		   }
		   return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ContractorRate persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public ContractorRate getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ContractorRate persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ContractorRate> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
//	/*
//	 * (non-Javadoc)
//	 * @see gov.nwcg.isuite.core.persistence.ContractorRateDao#enableContractorRates(java.util.Collection)
//	 */
//	public void enableContractorRates(Collection<Long> contractorRateIds) throws PersistenceException {
//		Query q = getHibernateSession().getNamedQuery(ContractorRateQuery.ENABLE_CONTRACTOR_RateS);
//
//		q.setParameter("enabled", Boolean.TRUE);
//		q.setParameterList("ids", contractorRateIds);
//
//		q.executeUpdate();
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see gov.nwcg.isuite.core.persistence.ContractorRateDao#disableContractorRates(java.util.Collection)
//	 */
//	public void disableContractorRates(Collection<Long> contractorRateIds) throws PersistenceException {
//		Query q = getHibernateSession().getNamedQuery(ContractorRateQuery.DISABLE_CONTRACTOR_RateS);
//
//		q.setParameter("enabled", Boolean.FALSE);
//		q.setParameterList("ids", contractorRateIds);
//
//		q.executeUpdate();
//	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorRateDao#getByNameId(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public ContractorRate getById(Long RateId, Long contractorId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ContractorRateImpl.class);
		
		crit.add(Restrictions.eq("contractorId", contractorId));
		crit.add(Restrictions.ne("id", RateId));
		crit.add(Restrictions.isNull("deletedDate"));
		
		List<ContractorRate> results = crit.list();
		
		if ( results == null || results.size() == 0 ) {
			   return null;
		   }
		return results.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorRateDao#getAssignmentTimePostingCount(java.lang.Long)
	 */
	public int getAssignmentTimePostingCount(Long refContractorRateId) throws PersistenceException {
		try{
			String sql = ContractorRateQuery.getAssignmentTimePostingCountQuery();
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("id", refContractorRateId);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
	}

}
