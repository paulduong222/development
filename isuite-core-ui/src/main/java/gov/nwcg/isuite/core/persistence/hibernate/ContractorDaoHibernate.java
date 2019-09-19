package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.impl.ContractorFilterImpl;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ContractorQuery;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

public class ContractorDaoHibernate extends TransactionSupportImpl implements ContractorDao {
	private final CrudDao<Contractor> crudDao;

	public ContractorDaoHibernate(final CrudDao<Contractor> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	public Collection<ContractorVo> getLightList(Long incidentId, Long incidentGroupId) throws PersistenceException {
		String sql=ContractorQuery.getLightListQuery(incidentId, incidentGroupId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(ContractorVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		Collection<ContractorVo> list = query.list();
		Collection<ContractorVo> returnList = new ArrayList<ContractorVo>();

		if ( CollectionUtility.hasValue(list)) {
			Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);

			StringBuffer sb = new StringBuffer();
			if(LongUtility.hasValue(incidentId)){
				   sb.append("this_.contractor_id in (select contractor_id from isw_incident_contractor where incident_id = " + incidentId + " ")
				   .append(" and deleted_date is null ) ");
			}else if(LongUtility.hasValue(incidentGroupId)){
					sb.append("this_.contractor_id in (select contractor_id from isw_incident_contractor where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ")
					.append(" and deleted_date is null) ");
			}
			
			crit.add(Restrictions.sqlRestriction(sb.toString()));
			Collection<ContractorAgreement> entities = crit.list();

			if ( CollectionUtility.hasValue(entities)) {
				for(ContractorVo cvo : list ) {
					cvo.setContractorAgreementVos(new ArrayList<ContractorAgreementVo>());
					for(ContractorAgreement agreementEntity : entities) {
						if (agreementEntity.getContractorId().compareTo(cvo.getId()) == 0){
							ContractorAgreementVo cavo = new ContractorAgreementVo();
							cavo.setId(agreementEntity.getId());
							cavo.setAgreementNumber(agreementEntity.getAgreementNumber());
							ContractorVo cvo2 = new ContractorVo();
							cvo2.setId(cvo.getId());
							cavo.setContractorVo(cvo2);
							cvo.getContractorAgreementVos().add(cavo);
						}
					}
					returnList.add(cvo);
				}
				
			}
		}

		/*
		if(CollectionUtility.hasValue(list)){
			for(ContractorVo v : list){
				String s = "select id, agreement_number as agreementNumber from isw_contractor_agreement where contractor_id = " + v.getId() + "";
				SQLQuery query2 = getHibernateSession().createSQLQuery(s);
				
				CustomResultTransformer crt2 = new CustomResultTransformer(ContractorAgreementVo.class);
				
				crt2.addScalar("id", Long.class.getName());
				//crt2.addScalar("contractorId", Long.class.getName());
				
				query2.setResultTransformer(crt2);
				
				Collection<ContractorAgreementVo> list2 = query2.list();
				if(CollectionUtility.hasValue(list2)){
					for(ContractorAgreementVo v2 : list2){
						ContractorVo vo = new ContractorVo();
						vo.setId(v.getId());
						v2.setContractorVo(vo);
						v.getContractorAgreementVos().add(v2);
					}
				}
			}
		}
		*/
		return returnList;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getGrid(gov.nwcg.isuite.core.filter.ContractorFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContractorGridVo> getGrid(ContractorFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ContractorImpl.class);

		crit.createAlias("address", "addr", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("addr.countrySubdivision", "csd", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("contractorAgreements", "agreement", CriteriaSpecification.LEFT_JOIN);
		
		if(null != filter.getIncidentIds() && filter.getIncidentIds().size() > 0) {
			crit.createAlias("incidents", "i");
		} else {
			//crit.createAlias("workAreas", "wa");
		}
		
		if (filter != null) {
			try
			{
				Collection<FilterCriteria> filterCriteria = ContractorFilterImpl.getFilterCriteria(filter);
				CriteriaBuilder.addCriteria(crit, filterCriteria);

				if(null != filter.getBeginDate())
					crit.add(Expression.ge("agreement.startDate", filter.getBeginDate()));
				if(null != filter.getEndDate())
					crit.add(Expression.ge("agreement.endDate", filter.getEndDate()));

			}
			catch(Exception e)
			{
				throw new PersistenceException(e);
			}
		}
		
		crit.add(Restrictions.isNull("this.deletedDate"));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		crit.addOrder(Order.asc("this.name"));
		
		Collection<Contractor> entities = crit.list();

		if(null != entities){
			try{
				return ContractorGridVo.getInstances(entities,true);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection<ContractorVo> getAll(ContractorFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ContractorImpl.class);

		crit.createAlias("address", "addr", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("addr.countrySubdivision", "csd", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("contractorAgreements", "agreement", CriteriaSpecification.LEFT_JOIN);
		
		if(CollectionUtility.hasValue(filter.getIncidentIds()) || (LongUtility.hasValue(filter.getIncidentGroupId())) ){
			crit.createAlias("incidents", "i");
		}
		if(LongUtility.hasValue(filter.getWorkAreaId())){
			crit.createAlias("workAreas", "wa");
		}
		
		if (filter != null) {
			try
			{
				Collection<FilterCriteria> filterCriteria = ContractorFilterImpl.getFilterCriteria(filter);
				CriteriaBuilder.addCriteria(crit, filterCriteria);

				if(null != filter.getBeginDate())
					crit.add(Expression.ge("agreement.startDate", filter.getBeginDate()));
				if(null != filter.getEndDate())
					crit.add(Expression.ge("agreement.endDate", filter.getEndDate()));

				if(LongUtility.hasValue(filter.getIncidentGroupId())){

					DetachedCriteria detachedCrit = DetachedCriteria.forClass(IncidentGroupImpl.class);
					detachedCrit.createAlias("incidents", "inc");
					detachedCrit.add(Expression.eq("this.id",filter.getIncidentGroupId()));
					detachedCrit.setProjection(Property.forName("inc.id"));

					crit.add(Subqueries.propertyIn("i.id", detachedCrit));
					
				}
			}
			catch(Exception e)
			{
				throw new PersistenceException(e);
			}
		}
		
		crit.add(Restrictions.isNull("this.deletedDate"));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		crit.addOrder(Order.asc("this.name"));
		Collection<Contractor> entities = crit.list();

		if(null != entities){
			try{
				return ContractorVo.getInstances(entities,true);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}

		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Contractor persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Contractor getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Contractor persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	public void merge(Contractor entity) throws PersistenceException {
		super.getHibernateTemplate().merge(entity);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Contractor> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#enableContractors(java.util.Collection)
	 */
	public void enableContractors(Collection<Long> contractorIds) throws PersistenceException {
		Query q = getHibernateSession().getNamedQuery(ContractorQuery.ENABLE_CONTRACTORS);

		q.setParameter("enabled", Boolean.TRUE);
		q.setParameterList("ids", contractorIds);

		q.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#disableContractors(java.util.Collection)
	 */
	public void disableContractors(Collection<Long> contractorIds) throws PersistenceException {
		Query q = getHibernateSession().getNamedQuery(ContractorQuery.DISABLE_CONTRACTORS);

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameterList("ids", contractorIds);

		q.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getByContractorNameIds(java.lang.String, java.util.Collection, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public Contractor getByContractorNameIds(String contractorName, Collection<Long> incidentIds, Collection<Long> workAreaIds, Long contractorId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(ContractorImpl.class);
		
		crit.add(Restrictions.eq("name", contractorName));
		crit.add(Restrictions.ne("id", contractorId));
		crit.add(Restrictions.isNull("deletedDate"));
		
		if (null != incidentIds) {
			crit.createAlias("incidents", "i");
			crit.add(Restrictions.in("i.id", incidentIds));
		} else {
			crit.createAlias("workAreas", "wa");
			crit.add(Restrictions.in("wa.id", workAreaIds));
		}
		
		List<Contractor> results = crit.list();
		
		if ( results == null || results.size() == 0 ) {
			   return null;
		   }
		return results.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getContractedResourcesWithOriginalInvoices(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getContractedResourcesWithOriginalInvoices(Long contractorId) throws PersistenceException {
		if(!LongUtility.hasValue(contractorId)) {
			throw new IllegalArgumentException("contractorId cannot be null.");
		}
		
		StringBuffer b = new StringBuffer();
		b.append(ContractorQuery.getContractedResourcesWithOriginalInvoices(contractorId, super.isOracleDialect()));
		
		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
		
		Object rslt = query.list();
		if(null != rslt)
			return (Collection<String>)rslt;
		else
			return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getAssignmentTimePostingCount(java.lang.Long)
	 */
	public int getAssignmentTimePostingCount(Long refContractorId) throws PersistenceException {
		try{
			String sql = ContractorQuery.getAssignmentTimePostingCountQuery();
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("id", refContractorId);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#removeContrFromContrPaymentInfo(java.lang.Long)
	 */
	public void removeContrFromContrPaymentInfo(Long contractorId) throws PersistenceException {
		try{
			String sql = ContractorQuery.REMOVE_CONTR_FROM_CONTR_PAYMENT_INFO_QUERY;
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("contractorId", contractorId);
			
			query.executeUpdate();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public void removeContrAgreementFromContrPaymentInfo(Collection<Long> agreementIds) throws PersistenceException {
		try{
			String sql = ContractorQuery.REMOVE_CONTR_AGREEMENT_FROM_CONTR_PAYMENT_INFO_QUERY;
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameterList("agreementIds", agreementIds);
			
			query.executeUpdate();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ContractorDao#getIncludedInOriginalInvoice(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContractorAgreement> getIncludedInOriginalInvoice(Long contractorId) throws PersistenceException {
		if(null == contractorId || contractorId < 1){
			throw new PersistenceException("contractor id is required.");
		}
	   
	   try {
		   Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);
		   crit.createAlias("contractorPaymentInfos", "cpi");
		   crit.createAlias("cpi.assignmentTime", "at");
		   crit.createAlias("at.assignmentTimePosts", "atp");
		   crit.createAlias("atp.timeInvoices", "ti");
 			
		   crit.add(Restrictions.eq("contractorId", contractorId));
		   crit.add(Restrictions.eq("ti.isDraft", Boolean.FALSE));
		   
		   return crit.list();
		   
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	}
	
	public Long getIncidentId(Long contractorId) throws PersistenceException {
		String sql="select incident_id " +
				   "from isw_incident_contractor " +
				   "where contractor_id = " + contractorId + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				return id;
			}catch(Exception smother){}
		}
		return null;
	}

}
