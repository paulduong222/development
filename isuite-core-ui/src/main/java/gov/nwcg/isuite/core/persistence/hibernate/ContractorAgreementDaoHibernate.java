package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ContractorAgreementQuery;
import gov.nwcg.isuite.core.vo.ContractorAgreementGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class ContractorAgreementDaoHibernate extends TransactionSupportImpl implements ContractorAgreementDao {
	private final CrudDao<ContractorAgreement> crudDao;

	public ContractorAgreementDaoHibernate(final CrudDao<ContractorAgreement> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getGrid(gov.nwcg.isuite.core.filter.ContractorFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContractorAgreementGridVo> getGrid(ContractorAgreementFilter filter) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);
		   
		   crit.createAlias("adminOffice", "admin", CriteriaSpecification.LEFT_JOIN);
		   crit.createAlias("contractor", "contr");
		   
		   /*
		    * Define how we are going to transform the result to the return object
		    */
		   crit.setProjection(Projections.projectionList()	             
	             .add(Projections.property("contr.id"), "contractorId")
	             .add(Projections.property("admin.id"), "adminOfficeId")
	             .add(Projections.property("admin.officeName"), "officeName")
	             .add(Projections.property("agreementNumber"),"agreementNumber")
	             .add(Projections.property("this.startDate"),"startDate") 
	             .add(Projections.property("this.endDate"),"endDate")
	             .add(Projections.property("pointOfHire"),"pointOfHire") 
	             .add(Projections.property("deletedDate"),"deletedDate")             
		   		 .add(Projections.property("id"),"id"));
		   
		   
		   crit.setResultTransformer(Transformers.aliasToBean(ContractorAgreementGridVo.class));

		   if (filter != null) {
			   try
			   { 	   
				   /*
				    * Add the filter criteria
				    */
				   
				   // TYPE_ILIKE
				   if (filter.getAgreementNumber() != null && !"".equals(filter.getAgreementNumber()))
				   { crit.add(Restrictions.ilike("this.agreementNumber", filter.getAgreementNumber(), MatchMode.START)); }
				   if (filter.getPointOfHire() != null && !"".equals(filter.getPointOfHire()))
				   { crit.add(Restrictions.ilike("this.pointOfHire", filter.getPointOfHire(), MatchMode.START)); }
				   if ( null != filter.getAdminOffice() && !filter.getAdminOffice().isEmpty()) {
					   crit.add(Restrictions.ilike("admin.officeName", filter.getAdminOffice(), MatchMode.START));
				   }
				   	   
					// TYPE_ISNULL
				   if (filter.getDeletedDate() == null)
				   { crit.add(Restrictions.isNull("this.deletedDate")); }

				   // TYPE_ISNOTNULL
				   if (filter.getDeletedDate() != null)
				   { crit.add(Restrictions.isNotNull("this.deletedDate")); }
				   
				   // TYPE_EQ
				   if (filter.getContractorId() != null && filter.getContractorId() != 0) {
					   crit.add(Restrictions.eq("this.contractorId", filter.getContractorId()));
				   }
				   if (filter.getAdminOfficeId() != null && !"".equals(filter.getAdminOfficeId()))
				   { crit.add(Restrictions.eq("this.adminOfficeId", filter.getAdminOfficeId())); }					   
				   if (filter.getContractorId() != null && !"".equals(filter.getContractorId()))
				   { crit.add(Restrictions.eq("this.contractorId", filter.getContractorId())); }
				   if (filter.getStartDate() != null && !"".equals(filter.getStartDate()))
				   { crit.add(Restrictions.ge("this.startDate", filter.getStartDate())); }
				   if (filter.getEndDate() != null && !"".equals(filter.getEndDate()))
				   { crit.add(Restrictions.ge("this.endDate", filter.getEndDate())); }
				   
				   
				   if(filter.getDeletable() != null) {
					   if(filter.getDeletable() == false) {
					      //TODO:  Add deletable criteria. 
					   } else {
					      //TODO:  Add non-deletable criteria. 
					      return null;
					   }
					}
					
				   if(StringUtility.hasValue(filter.getCrypticDateFilterCodeStartDate())) {
					   super.applyCrypticDateFilter(crit, 
							   filter.getCrypticDateFilterCodeStartDate(), "this_.start_date");
				   }
				   if(StringUtility.hasValue(filter.getCrypticDateFilterCodeEndDate())) {
					   super.applyCrypticDateFilter(crit, 
							   filter.getCrypticDateFilterCodeEndDate(), "this_.end_date");
				   }
					//Collection<FilterCriteria> filterCriteria = ContractorAgreementFilterImpl.getFilterCriteria(filter);				
					//CriteriaBuilder.addCriteria(crit, filterCriteria);
				   
			   }
			   catch(Exception e)
			   {
				   throw new PersistenceException(e);
			   }
		   }

		   try{
			   crit.addOrder(Order.asc("this.agreementNumber"));
			   return crit.list();
		   }catch(Exception e){
			   System.out.println(e.getMessage().toString());
		   }
		   return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ContractorAgreement persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public ContractorAgreement getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ContractorAgreement persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ContractorAgreement> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorAgreementDao#enableContractorAgreements(java.util.Collection)
	 */
	public void enableContractorAgreements(Collection<Long> contractorAgreementIds) throws PersistenceException {
		Query q = getHibernateSession().getNamedQuery(ContractorAgreementQuery.ENABLE_CONTRACTOR_AGREEMENTS);

		q.setParameter("enabled", Boolean.TRUE);
		q.setParameterList("ids", contractorAgreementIds);

		q.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorAgreementDao#disableContractorAgreements(java.util.Collection)
	 */
	public void disableContractorAgreements(Collection<Long> contractorAgreementIds) throws PersistenceException {
		Query q = getHibernateSession().getNamedQuery(ContractorAgreementQuery.DISABLE_CONTRACTOR_AGREEMENTS);

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameterList("ids", contractorAgreementIds);

		q.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorAgreementDao#getByNameId(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public ContractorAgreement getByNameId(String agreementNumber, Long agreementId, Long contractorId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);
		
		crit.add(Restrictions.eq("agreementNumber", agreementNumber.toUpperCase()));
		crit.add(Restrictions.eq("contractorId", contractorId));
		crit.add(Restrictions.ne("id", agreementId));
		crit.add(Restrictions.isNull("deletedDate"));
		
		List<ContractorAgreement> results = crit.list();
		
		if ( results == null || results.size() == 0 ) {
			   return null;
		   }
		return results.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorAgreementDao#getOriginalInvoices(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContractorAgreement> getAgreementsWithOriginalInvoices(Long contractorAgreementId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);
		
		crit.createAlias("timeInvoices", "ti");
		
		crit.add(Expression.eq("id", contractorAgreementId));
		crit.add(Expression.isNotNull("ti.originalPrintDate"));
		
		return crit.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorAgreementDao#getAssignmentTimePostingCount(java.lang.Long)
	 */
	public int getAssignmentTimePostingCount(Long refContractorAgreementId) throws PersistenceException {
		try{
			String sql = ContractorAgreementQuery.getAssignmentTimePostingCountQuery();
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("id", refContractorAgreementId);
			
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
