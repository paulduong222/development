package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * 
 * @author gdyer	
 */
public class AdminOfficeDaoHibernate extends TransactionSupportImpl implements AdminOfficeDao {

   private final CrudDao<AdminOffice> crudDao;
   
   public AdminOfficeDaoHibernate(final CrudDao<AdminOffice> crudDao) {
      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
      
      
   }
   
   public void merge(AdminOffice adminOffice) throws PersistenceException {
	   	super.fixCasing(adminOffice);
		super.getHibernateTemplate().merge(adminOffice);
   }

   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AdminOfficeDao#getGrid(gov.nwcg.isuite.core.filter.AdminOfficeFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<AdminOfficeGridVo> getGrid(AdminOfficeFilter filter) throws PersistenceException {
      
	   Criteria crit = getHibernateSession().createCriteria(AdminOfficeImpl.class);
	   
	   crit.createAlias("address", "addr");
	   crit.createAlias("addr.countrySubdivision", "csd");
	   
	   /*
	    * Define how we are going to transform the result to the return object
	    */
	   crit.setProjection(Projections.projectionList()
             .add(Projections.property("officeName"), "officeName")
             .add(Projections.property("addr.addressLine1"), "addressLine1")
             .add(Projections.property("addr.addressLine2"), "addressLine2")
             .add(Projections.property("addr.city"), "city")
             .add(Projections.property("addr.postalCode"), "postalCode")
             .add(Projections.property("csd.abbreviation"), "countrySubdivision")
             .add(Projections.property("phone"),"phone")
             .add(Projections.property("deletedDate"),"deletedDate")             
	   		 .add(Projections.property("id"),"id"));
	   
	   
	   crit.setResultTransformer(Transformers.aliasToBean(AdminOfficeGridVo.class));

	   if (filter != null) {
		   try
		   { 	   
			   /*
			    * Add the filter criteria
			    */

			   if(LongUtility.hasValue(filter.getId()))
				   crit.add(Restrictions.eq("this.id", filter.getId()));
			   
			   // TYPE_ILIKE
			   if (filter.getOfficeName() != null && !"".equals(filter.getOfficeName()))
			   { crit.add(Restrictions.ilike("this.officeName", filter.getOfficeName(), MatchMode.START)); }
			   if (filter.getPhone() != null && !"".equals(filter.getPhone()))
			   { crit.add(Restrictions.ilike("this.phone", filter.getPhone(), MatchMode.START)); }			   
			   if (filter.getAddressLine1() != null && !"".equals(filter.getAddressLine1()))
			   { crit.add(Restrictions.ilike("addr.addressLine1", filter.getAddressLine1(),MatchMode.START)); }			   
			   if (filter.getAddressLine2() != null && !"".equals(filter.getAddressLine2()))
			   { crit.add(Restrictions.ilike("addr.addressLine2", filter.getAddressLine2(),MatchMode.START)); }
			   if (filter.getCity() != null && !"".equals(filter.getCity()))
			   { crit.add(Restrictions.ilike("addr.city", filter.getCity(),MatchMode.START)); }
			   if (filter.getCountrySubdivision() != null && !"".equals(filter.getCountrySubdivision()))
			   { crit.add(Restrictions.ilike("csd.abbreviation", filter.getCountrySubdivision(),MatchMode.START)); }
			   if (filter.getPostalCode() != null && !"".equals(filter.getPostalCode()))
			   { crit.add(Restrictions.ilike("addr.postalCode", filter.getPostalCode(),MatchMode.START)); }
					   
				// TYPE_ISNULL
			   if (filter.getDeletedDate() == null)
			   { crit.add(Restrictions.isNull("this.deletedDate")); }

			   // TYPE_ISNOTNULL
			   if (filter.getDeletedDate() != null)
			   { crit.add(Restrictions.isNotNull("this.deletedDate")); }
			   
		   }
		   catch(Exception e)
		   {
			   throw new PersistenceException(e);
		   }
	   }

	   crit.addOrder(Order.asc("officeName"));
	   
	   return crit.list();
   }

  

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<AdminOffice> persistables) throws PersistenceException {
	   crudDao.saveAll(persistables);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(AdminOffice persistable) throws PersistenceException {
	   crudDao.delete(persistable);
   }
      
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.TransferableDao#getByUniqueIdentity(java.lang.String, java.lang.Class)
    */
   public AdminOffice getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException, DuplicateItemException 
   {
	      throw new UnsupportedOperationException();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(AdminOffice persistable) throws PersistenceException {
	   crudDao.save(persistable);
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public AdminOffice getById(Long id, Class clazz) throws PersistenceException {
	   return crudDao.getById(id, clazz);
   } 
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public AdminOffice getById(Long id) throws PersistenceException {
	   return crudDao.getById(id, AdminOfficeImpl.class);
   } 
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public AdminOffice getByAdminOfficeName(String officeName) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(AdminOfficeImpl.class);
	   
	   crit.add(Restrictions.eq("officeName", officeName));
	   crit.add(Restrictions.isNull("deletedDate"));
	   List<AdminOffice> results = crit.list();

	   if ( results == null || results.size() == 0 ) {
		   return null;
	   }
	   return results.get(0);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AdminOfficeDao#getPicklist()
    */
   @SuppressWarnings("unchecked")
   public Collection<AdminOfficeVo> getPicklist() throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(AdminOfficeImpl.class);
	   crit.addOrder(Order.asc("officeName"));
	   crit.add(Restrictions.isNull("deletedDate"));  // be sure not to include deleted admin offices
	   Collection<AdminOffice> entities = crit.list();

	   if(null != entities){
			try{
				return AdminOfficeVo.getInstances(entities,true);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
	   }

	   return null;
   }
   
   @SuppressWarnings("unchecked")
   public Collection<ContractorAgreement> getIncludedInOriginalInvoice(Long adminOfficeId) throws PersistenceException {
	   
	   if(null == adminOfficeId || adminOfficeId < 1){
			throw new PersistenceException("adminOfficeId is required.");
		}
	   
	   try {
		   Criteria crit = getHibernateSession().createCriteria(ContractorAgreementImpl.class);
		   crit.createAlias("contractorPaymentInfos", "cpi");
		   crit.createAlias("cpi.assignmentTime", "at");
		   crit.createAlias("at.assignmentTimePosts", "atp");
		   crit.createAlias("atp.timeInvoices", "ti");
 			
		   crit.add(Restrictions.eq("adminOfficeId", adminOfficeId));
		   crit.add(Restrictions.eq("ti.isDraft", Boolean.FALSE));
		   
		   return crit.list();
		   
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
   }
   
   
   
}
