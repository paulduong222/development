package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.impl.TimeInvoiceImpl;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class TimeInvoiceDaoHibernate extends TransactionSupportImpl implements TimeInvoiceDao {
	private final CrudDao<TimeInvoice> crudDao;
	private static final Logger LOG = Logger.getLogger(TimeInvoiceDaoHibernate.class);
	
	public TimeInvoiceDaoHibernate(final CrudDao<TimeInvoice> crudDao) {
    if ( crudDao == null ) {
      LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
      throw new IllegalArgumentException("crudDao can not be null");
    }
    this.crudDao = crudDao;
  }
	
  @Override
  public void delete(TimeInvoice persistable) throws PersistenceException {
    crudDao.delete(persistable);    
  }
  
  @Override
  public TimeInvoice getById(Long id, Class<?> clazz) throws PersistenceException {
    return crudDao.getById(id, clazz);
  }
  
  @Override
  public void save(TimeInvoice persistable) throws PersistenceException {
    if(null != persistable.getId() && persistable.getId()>0)
      super.getHibernateSession().merge(persistable);
    else
      crudDao.save(persistable);
  }
  
  @Override
  public void saveAll(Collection<TimeInvoice> persistables)
      throws PersistenceException {
    // TODO Auto-generated method stub
  }

  @Override
  public Collection<TimeInvoiceVo> getForResource(Long resourceId) throws PersistenceException {
	  try{
		    if(null == resourceId || resourceId < 1){
				throw new PersistenceException("resourceId is required.");
			}
		    
			Criteria crit = getHibernateSession().createCriteria(TimeInvoiceImpl.class);
			crit.addOrder(Order.asc("this.id"));
			crit.add(Restrictions.sqlRestriction("id in " +
					"(select distinct time_invoice_id " +
					" from isw_resource_invoice " +
					" where resource_id = " + resourceId + ") "));
						
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
			criteria.add(new FilterCriteria("this.isDraft",Boolean.FALSE,FilterCriteria.TYPE_EQUAL));
			criteria.add(new FilterCriteria("this.isDuplicate",Boolean.FALSE,FilterCriteria.TYPE_EQUAL));
			criteria.add(new FilterCriteria("this.deletedDate",null,FilterCriteria.TYPE_ISNULL));
			
			CriteriaBuilder.addCriteria(crit, criteria);

			Collection<TimeInvoice> entities = crit.list();

			if(null != entities && entities.size()>0)
				return TimeInvoiceVo.getInstances(entities, true);
			else
				return new ArrayList<TimeInvoiceVo>();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
  }
  
//  	public Collection<TimeInvoice> getByAdminId(Long adminOfficeId) throws PersistenceException {
//  		
//  		if(null == adminOfficeId || adminOfficeId < 1){
//			throw new PersistenceException("adminOfficeId is required.");
//		}
//  		
//  		try {
//  			Criteria crit = getHibernateSession().createCriteria(TimeInvoiceImpl.class);
//  			
//  			crit.createAlias("contractorAgreement", "a");
//  			
//  			crit.add(Restrictions.eq("a.adminOfficeId", adminOfficeId));
//  			
//  			return crit.list();
//		  
//		  
//  		}catch(Exception e){
//  			throw new PersistenceException(e);
//  		}
//  	}

  	/*
  	 * (non-Javadoc)
  	 * @see gov.nwcg.isuite.core.persistence.TimeInvoiceDao#getUnexportedInvoices(java.util.Collection, java.lang.Boolean)
  	 */
  	@SuppressWarnings("unchecked")
	public Collection<TimeInvoice> getUnexportedInvoices(Collection<Long> incidentIds, Boolean contractor)throws PersistenceException{
  		try{
  			Criteria crit = getHibernateSession().createCriteria(TimeInvoiceImpl.class);
  			crit.createAlias("resources", "res");
  			crit.createAlias("res.incidents", "i");
  			
  			crit.add(Restrictions.eq("exported",StringBooleanEnum.N));
  			crit.add(Restrictions.eq("isDraft", Boolean.FALSE));
  			crit.add(Restrictions.eq("isDuplicate", Boolean.FALSE));
  			crit.add(Restrictions.isNull("deletedDate"));
  			
  			crit.createAlias("assignmentTimePosts", "atp");
			crit.createAlias("res.agency", "a");
			crit.createAlias("i.agency", "ia");
			crit.createAlias("res.incidentResources", "ir");
			crit.createAlias("ir.costData", "cd", CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("cd.paymentAgency", "pa", CriteriaSpecification.LEFT_JOIN);
  			
			if(contractor) {
	  			crit.add(Restrictions.eq("atp.employmentType", EmploymentTypeEnum.CONTRACTOR));
	  			crit.add(Restrictions.eq("a.agencyCode", "PVT"));
	  				
	  			String sql = " ((ia5_.agency_code = 'USFS' and (pa8_.agency_code = 'USFS' or pa8_.agency_code is null)) " +
	  					" or (ia5_.agency_code != 'USFS' and pa8_.agency_code = 'USFS')) ";
	  				
	  			crit.add(Restrictions.sqlRestriction(sql));
	  			
	  				
	  		}else {
	  			crit.add(Restrictions.eq("atp.employmentType", EmploymentTypeEnum.AD));
	  			crit.add(Restrictions.eq("a.agencyCode", "USFS"));
	  		}
  			
  			crit.add(Restrictions.in("i.id", incidentIds));
  			crit.addOrder(Order.asc("this.id"));
  			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
  			
  			return crit.list();
  		}catch(Exception e){
  			throw new PersistenceException(e);
  		}
  		
  	}
  	
  	/*
  	 * (non-Javadoc)
  	 * @see gov.nwcg.isuite.core.persistence.TimeInvoiceDao#getInvoiceByInvoiceNumber(java.lang.String)
  	 */
  	@SuppressWarnings("unchecked")
	public Collection<TimeInvoice> getInvoiceByInvoiceNumber(String invoiceNumber)throws PersistenceException{
  	
  		try{
  			Criteria crit = getHibernateSession().createCriteria(TimeInvoiceImpl.class);
  			crit.add(Restrictions.eq("invoiceNumber",invoiceNumber));
  			return crit.list();
  			
  		}catch(Exception e){
  			throw new PersistenceException(e);
  		}
  	}
  	
  	/* (non-Javadoc)
  	 * @see gov.nwcg.isuite.core.persistence.TimeInvoiceDao#getAllInvoiceFileNames()
  	 */
  	public Collection<String> getAllInvoiceFileNames() throws PersistenceException {
  		Collection<String> list = new ArrayList<String>();
  		
  		String sql = "select rpt.file_name " +
  					 "from isw_time_invoice ti "+
  					 "     , isw_report rpt " +
  					 "where ti.deleted_date is null " +
  					 "and ti.is_draft = " + (super.isOracleDialect() ? 0 : false) + " " +
  					 "and rpt.id = ti.invoice_report_id " ;
  		
  		SQLQuery query = getHibernateSession().createSQLQuery(sql);
  		
  		Collection<Object> rslts = query.list();
  		
  		if(CollectionUtility.hasValue(rslts)){
  			try{
  				for(Object obj : rslts){
  	  				String s = TypeConverter.convertToString(obj);
  	  				list.add(s);
  				}
  			}catch(Exception e){
  				
  			}
  		}
  		
  		return list;
  	}
  	
  	public Long getResourceId(Long invoiceTimeId) throws PersistenceException {
  		String sql="select resource_id from isw_resource_invoice where time_invoice_id = " + invoiceTimeId + " ";
  		SQLQuery q = getHibernateSession().createSQLQuery(sql);
  		
  		Object rslt=q.uniqueResult();
  		
  		if(null != rslt){
  			try{
  				Long resourceId=TypeConverter.convertToLong(rslt);
  				return resourceId;
  			}catch(Exception e){}
  		}
  		return null;
  	}
  	
  	public Long getResNumId(Long incResId) throws PersistenceException {
  		String sql="select res_num_id from isw_incident_resource where id = " + incResId + " ";
  		SQLQuery q = getHibernateSession().createSQLQuery(sql);
  		
  		Object rslt=q.uniqueResult();
  		
  		if(null != rslt){
  			try{
  				Long resNumId=TypeConverter.convertToLong(rslt);
  				return resNumId;
  			}catch(Exception e){}
  		}
  		return null;
  	}

  	public Collection<Long> getADSubordinateResourceIds(Long parentResourceId) throws PersistenceException {
  		String sql="select ir.resource_id " +
  				   "from isw_incident_resource ir " +
  				   ", isw_resource r " +
  				   "where ir.resource_id = r.id " +
  				   "and ir.id in ( " +
  				   "	select wp.incident_resource_id " +
  				   "    from isw_work_period wp " +
  				   "         , isw_work_period_assignment wpa " +
  				   "         , isw_assignment a " +
  				   "         , isw_assignment_time at " +
  				   "    where wp.id = wpa.work_period_id " +
  				   "    and wpa.assignment_id = a.id " +
  				   "    and a.id = at.assignment_id " +
  				   "    and at.employment_type='AD' " +
  				   ") " +
  				   "and r.id in (select id from isw_resource where parent_resource_id = "+parentResourceId+")";

  		SQLQuery q = getHibernateSession().createSQLQuery(sql);
  		Collection<Object> results = q.list();
  		Collection<Long> adResourceIds=new ArrayList<Long>();
  		
  		if(null != results){
  			for(Object o : results){
  				try{
  					Long id = TypeConverter.convertToLong(o);
  					adResourceIds.add(id);
  				}catch(Exception ee){}
  			}
  		}
  		
  		return adResourceIds;
  	}
  	
  	public void cleanDuplicateInvoicePostings(Long sourceId) throws PersistenceException {
  		String sql="delete from isw_assign_time_post_invoice " +
  				   "where time_invoice_id > " + sourceId + " " +
  				   "and assign_time_post_id in (" +
  				   " select assign_time_post_id from isw_assign_time_post_invoice where time_invoice_id = " + sourceId + " " +
  				   ")";
  		SQLQuery q = getHibernateSession().createSQLQuery(sql);
  		q.executeUpdate();
  		
  	}
  	
  	public void cleanDuplicateInvoiceAdjustments(Long sourceId) throws PersistenceException {
  		String sql="delete from isw_time_assign_adj_invoice " +
		   "where time_invoice_id > " + sourceId + " " +
		   "and time_post_adjust_id in (" +
		   " select time_post_adjust_id from isw_time_assign_adj_invoice where time_invoice_id = " + sourceId + " " +
		   ")";
  		SQLQuery q = getHibernateSession().createSQLQuery(sql);
  		q.executeUpdate();
  		
  	}
}

