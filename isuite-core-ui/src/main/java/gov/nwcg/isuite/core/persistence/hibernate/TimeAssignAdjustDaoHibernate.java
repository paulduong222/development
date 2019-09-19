package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimeAdjustmentQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimePostQuery;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.TimeAdjustmentVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class TimeAssignAdjustDaoHibernate extends TransactionSupportImpl implements TimeAssignAdjustDao{

	private final CrudDao<TimeAssignAdjust> crudDao;
	
	public TimeAssignAdjustDaoHibernate(final CrudDao<TimeAssignAdjust> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TimeAssignAdjust getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, TimeAssignAdjustImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TimeAssignAdjust persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<TimeAssignAdjust> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TimeAssignAdjust persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public Collection<TimeAssignAdjust> getByIncidentResourceId(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(TimeAssignAdjustImpl.class);

		String sql = "this_.assignment_id in ( " +
					 "   select assignment_id from isw_work_period_assignment where work_period_id in (" +
					 "      select id from isw_work_period where incident_resource_id = " + id + " " +
					 "   ) " +
					 ") " +
					 "AND this_.deleted_date is null ";
		crit.add(Restrictions.sqlRestriction(sql));
		
		return (Collection<TimeAssignAdjust>)crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao#getGrid(java.lang.Long)
	 */
	public Collection<TimeAssignAdjustVo> getGrid(Long assignmentId) throws PersistenceException {
		
		try{
			if(!LongUtility.hasValue(assignmentId))
				throw new PersistenceException("assignmentId is required.");
				
			Criteria crit = getHibernateSession().createCriteria(TimeAssignAdjustImpl.class);
			
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
			criteria.add( new FilterCriteria("this.assignmentId",assignmentId,FilterCriteria.TYPE_EQUAL));
			criteria.add( new FilterCriteria("this.deletedDate",null,FilterCriteria.TYPE_ISNULL));
			
			CriteriaBuilder.addCriteria(crit, criteria);
			
			Collection<TimeAssignAdjust> entities = crit.list();

			if(null != entities && entities.size()>0)
				return TimeAssignAdjustVo.getInstances(entities, true);
			else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao#getResourcesTimeAdjustmentCount(java.util.Collection)
	 */
	public int getResourcesTimeAdjustmentCount(Collection<Long> ids) throws PersistenceException {
		try{
			String sql = TimeAdjustmentQuery.getResourcesTimeAdjustmentCountQuery();
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameterList("ids", ids);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao#getIncidentId(java.lang.Long)
	 */
	public Long getIncidentId(Long assignmentId) throws PersistenceException {
		try{
			String sql = TimeAdjustmentQuery.getIncidentIdQuery(assignmentId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToLong(rslt);
			else
				return 0L;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao#getIncidentResourceId(java.lang.Long)
	 */
	public Long getIncidentResourceId(Long assignmentId) throws PersistenceException {
		try{
			String sql = TimeAdjustmentQuery.getIncidentResourceIdQuery(assignmentId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToLong(rslt);
			else
				return 0L;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public int deleteResourceAdjustments(Long incidentResourceId) throws PersistenceException {
		try{
			String sql = TimeAdjustmentQuery.getDeleteByIncidentResourceIdQuery(incidentResourceId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
				
			int cnt=query.executeUpdate();
			
			return cnt;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
	}
	
	public Collection<TimeAdjustmentVo> getTimeAdjustmentsByAssignmentId(Long id) throws PersistenceException {
		Collection<TimeAdjustmentVo> vos = new ArrayList<TimeAdjustmentVo>();
		
		String sql = TimeAdjustmentQuery.getTimeAdjustmentsQuery(id);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

        CustomResultTransformer crt = new CustomResultTransformer(TimeAdjustmentVo.class);
 	    
        crt.addScalar("id", Long.class.getName());
        crt.addScalar("assignmentId", Long.class.getName());
        crt.addScalar("incidentAccountCodeId", Long.class.getName());
        crt.addScalar("activityDate", Date.class.getName());
        crt.addScalar("adjustmentAmount", BigDecimal.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
		
	}

	public Collection<Long> getAdjustIds(TimeAssignAdjust entity, Collection<Long> crewIds) throws PersistenceException {
		Collection<Long> taaIds = new ArrayList<Long>();
		String listIds="";
		for(Long id : crewIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}
		
		String sql="" +
			"select id " +
			"from isw_time_assign_adjust " +
			"where assignment_id in ( " +
			"	select a.id " +
			"	from isw_assignment a " +
			"		, isw_work_period_assignment wpa " +
			"		, isw_work_period wp " +
			"	where wp.incident_resource_id in ("+listIds+") " +
			"	and wpa.work_period_id = wp.id " +
			"	and a.id = wpa.assignment_id " +
			") " +
			"and to_char(activity_date,'MM/DD/YYYY')='" + DateUtil.toDateString(entity.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY) +"' " +
			"and adjustment_amount=" + entity.getAdjustmentAmount() +" " +
		    "and adjust_category_id = " + entity.getAdjustCategory().getId() + " " +
			"and deleted_date is null " +
			"and commodity = '" + entity.getCommodity()+"' " +
			"and incident_account_code_id = " + entity.getIncidentAccountCodeId();

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Collection<Object> results=(Collection<Object>)q.list();
		if(null != results){
			try{
				for(Object obj : results){
					Long val=TypeConverter.convertToLong(obj);
					taaIds.add(val);
				}
			}catch(Exception e){
			}
		}
		
		return taaIds;
	}
	
	public void deleteAdjustments(Collection<Long> taaIds, Date dt) throws PersistenceException {
		String listIds="";
		for(Long id : taaIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}
		
		String sql="" +
			"update isw_time_assign_adjust " +
			"set deleted_date = to_date('" + DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"where id in ( " + listIds + ") " 
			;

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	public Collection<Long> getAssignmentIds(Collection<Long> crewIrIds) throws PersistenceException {
		Collection<Long> ids = new ArrayList<Long>();
		String listIds="";
		for(Long id : crewIrIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}
		
		String sql="" +
			"	select a.id " +
			"	from isw_assignment a " +
			"		, isw_work_period_assignment wpa " +
			"		, isw_work_period wp " +
			"	where wp.incident_resource_id in ("+listIds+") " +
			"	and wpa.work_period_id = wp.id " +
			"	and a.id = wpa.assignment_id "; 

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Collection<Object> results=q.list();
		
		if(null != results){
			try{
				for(Object obj : results){
					Long id = TypeConverter.convertToLong(obj);
					ids.add(id);
				}
			}catch(Exception e){}
		}
		
		return ids;
	}
	
	public Collection<TimeAssignAdjustVo> getLastInvoiceDateConflicts(Date activityDate,Collection<Long> crewIrIds) throws PersistenceException {
		Collection<TimeAssignAdjustVo> vos = new ArrayList<TimeAssignAdjustVo>();
		String listIds="";
		for(Long id : crewIrIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}

		String sql=""+
			"select a.request_number as requestNumber " +
			", r.last_name as lastName " +
			", r.first_name as firstName " +
			", a.id as assignmentId " +
			", to_char(ti.last_include_date,'MM/DD/YYYY') as lastInvoiceDateStr " +
			"from isw_time_invoice ti " +
				", isw_resource_invoice ri " +
				", isw_incident_resource ir " +
				", isw_resource r " +
				", isw_work_period wp " +
				", isw_work_period_assignment wpa " +
				", isw_assignment a " +
			"where ti.is_draft="+(super.isOracleDialect() ? 0 : false )+" " +
			"and ti.id = ( "+
			"  select max(id) " +
			"  from isw_time_invoice " +
			"  where id in (" +
			"    select time_invoice_id " +
			"    from isw_resource_invoice " + 
			"    where resource_id in ( select resource_id from isw_incident_resource where id in (" + listIds +")) " +
			"  ) " +
			"  and is_draft = " + (super.isOracleDialect() ? 0 : false ) + " " +
			"  and deleted_date is null  " +
			") " +
			"and ti.deleted_date is null " +
			"and ti.id = ri.time_invoice_id " +
			"and ri.resource_id = ir.resource_id " +
			"and ir.id in ("+listIds+") " +
			"and to_date('"+DateUtil.toDateString(activityDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') <= to_date(to_char(ti.last_include_date,'MM/DD/YYYY'),'MM/DD/YYYY') " +
			"and r.id = ir.resource_id " +
			"and wp.incident_resource_id = ir.id " +
			"and wpa.work_period_id = wp.id " +
			"and a.id = wpa.assignment_id ";

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(TimeAssignAdjustVo.class);
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("lastInvoiceDateStr", "lastInvoiceDateStr");
 	    crt.addProjection("assignmentId", "assignmentId");
		crt.addScalar("assignmentId", Long.class.getName());
		
		q.setResultTransformer(crt); 

		try{
			vos=q.list();
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw new PersistenceException(e);
		}
		
		return vos;
	}

	public Collection<TimeAssignAdjustVo> getResourceInfo(Collection<Long> assignmentIds) throws PersistenceException {
		Collection<TimeAssignAdjustVo> vos = new ArrayList<TimeAssignAdjustVo>();
		String listIds="";
		for(Long id : assignmentIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}

		String sql=""+
			"select a.request_number as requestNumber " +
			", r.last_name as lastName " +
			", r.first_name as firstName " +
			", a.id as assignmentId " +
			"from isw_incident_resource ir " +
				", isw_resource r " +
				", isw_work_period wp " +
				", isw_work_period_assignment wpa " +
				", isw_assignment a " +
			"where r.id = ir.resource_id " +
			"and ir.id = wp.incident_resource_id " +
			"and wp.id = wpa.work_period_id " +
			"and wpa.assignment_id = a.id " +
			"and a.id in ("+listIds+") ";

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(TimeAssignAdjustVo.class);
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("assignmentId", "assignmentId");
		crt.addScalar("assignmentId", Long.class.getName());
		
		q.setResultTransformer(crt); 

		try{
			vos=q.list();
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw new PersistenceException(e);
		}
		
		return vos;
	}

	public Collection<TimeAssignAdjustVo> getNoTimePostingsList(Collection<Long> irIds) throws PersistenceException {
		Collection<TimeAssignAdjustVo> vos = new ArrayList<TimeAssignAdjustVo>();
		String listIds="";
		for(Long id : irIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}

		String sql=""+
			"select a.request_number as requestNumber " +
			", r.last_name as lastName " +
			", r.first_name as firstName " +
			", a.id as assignmentId " +
			"from isw_incident_resource ir " +
				", isw_resource r " +
				", isw_work_period wp " +
				", isw_work_period_assignment wpa " +
				", isw_assignment a " +
				", isw_assignment_time at " +
			"where ir.id in ("+listIds+") " +
			"and r.id = ir.resource_id " +
			"and wp.incident_resource_id = ir.id " +
			"and wpa.work_period_id = wp.id " +
			"and a.id = wpa.assignment_id " +
			"and at.assignment_id = a.id " +
			"and at.id not in (" +
			"   select assignment_time_id from isw_assign_time_post where deleted_date is null " + 
			") ";

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(TimeAssignAdjustVo.class);
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("assignmentId", "assignmentId");
		crt.addScalar("assignmentId", Long.class.getName());
		
		q.setResultTransformer(crt); 

		try{
			vos=q.list();
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw new PersistenceException(e);
		}
		
		return vos;
	}

	public Collection<Long> getMatchingBatchAdjIds(TimeAssignAdjust entity, Collection<Long> crewIrIds) throws PersistenceException {
		Collection<Long> ids = new ArrayList<Long>();
		String listIds="";
		for(Long id : crewIrIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}
		
		String sql="select id " +
			       "from isw_time_assign_adjust " +
				   "where to_date('"+DateUtil.toDateString(entity.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') = to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') " +
			       "and adjust_category_id = " + entity.getAdjustCategory().getId() + " " +
			       "and adjustment_amount = " + entity.getAdjustmentAmount() + " " +
			       "and incident_account_code_id = " + entity.getIncidentAccountCodeId() + " " +
			       "and commodity = '"+entity.getCommodity()+"' " +
			       "and deleted_date is null " + 
			       "and assignment_id in ("+
			       "  select wpa.assignment_id from isw_work_period_assignment wpa "+
			       "  where work_period_id in ("+
			       "    select id from isw_work_period where incident_resource_id in ( " + listIds + ") " +
			       "  ) " +
			       ") ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Collection<Object> results = q.list();
		if(null != results){
			try{
				for(Object o : results){
					Long val=TypeConverter.convertToLong(o);
					ids.add(val);
				}
			}catch(Exception smother){}
		}
		
		return ids;
	}
	
	public void updateBatch(TimeAssignAdjust entity, Collection<Long> adjIds) throws PersistenceException {
		String listIds="";
		for(Long id : adjIds){
			if(listIds.length()==0)
				listIds=String.valueOf(id);
			else
				listIds=listIds+","+String.valueOf(id);
		}
		
		String sql="update isw_time_assign_adjust " +
			       "set adjust_category_id = " + entity.getAdjustCategory().getId() + " " +
			       ", adjustment_amount = " + entity.getAdjustmentAmount() + " " +
			       ", adjustment_type = '" + entity.getAdjustmentType().name() + "' " +
			       ", incident_account_code_id = " + entity.getIncidentAccountCodeId() + " " +
			       ", commodity = '"+entity.getCommodity()+"' " +
				   "where id in ("+listIds+") ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}
	
	public Collection<IncidentResourceTimeAdustDataVo> getTimeAdjustData(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException{
		try{
			String sql1 = TimeAdjustmentQuery.getIncidentResourceTimeAdjustDataQuery(incidentResourceParentId,postDate,subsOnly,super.isOracleDialect());
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);

			CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceTimeAdustDataVo.class);
	 	    crt.addProjection("assignmentId", "assignmentId");
	 	    crt.addProjection("incidentResourceId", "incidentResourceId");
	 	    crt.addProjection("timeAssignAdjustId", "timeAssignAdjustId");
	 	    crt.addProjection("activityDate", "activityDate");
	 	    crt.addProjection("adjustmentType", "adjustmentType");
	 	    crt.addProjection("adjustmentAmount", "adjustmentAmount");
	 	    crt.addProjection("adjustmentCategory", "adjustmentCategory");
	 	    crt.addProjection("incidentAccountCodeId", "incidentAccountCodeId");
	 	    crt.addProjection("accountingCode", "accountingCode");
	 	    crt.addProjection("commodity", "commodity");
	 	    crt.addProjection("adjustmentCategoryDesc","adjustmentCategoryDesc");
	        crt.addScalar("assignmentId", Long.class.getName());
	        crt.addScalar("incidentResourceId", Long.class.getName());
	        crt.addScalar("timeAssignAdjustId", Long.class.getName());
	        crt.addScalar("timeAssignAdjustId", Long.class.getName());
	        crt.addScalar("incidentAccountCodeId", Long.class.getName());
	        crt.addScalar("adjustmentAmount", BigDecimal.class.getName());
	        crt.addScalar("activityDate", Date.class.getName());
			
	        query1.setResultTransformer(crt);
	        
	        Collection<IncidentResourceTimeAdustDataVo> vos = query1.list();

			return vos;

		}catch(Exception e){
			throw new PersistenceException(e.getMessage());
		}
	}
	
	public Collection<IncidentResourceTimeAdustDataVo> getTimeAdjustData2(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException{
		try{
			String sql1 = TimeAdjustmentQuery.getIncidentResourceTimeAdjustDataQuery2(incidentResourceParentId,postDate,subsOnly,super.isOracleDialect());
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);

			CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceTimeAdustDataVo.class);
	 	    crt.addProjection("assignmentId", "assignmentId");
	 	    crt.addProjection("incidentResourceId", "incidentResourceId");
	 	    crt.addProjection("timeAssignAdjustId", "timeAssignAdjustId");
	 	    crt.addProjection("activityDate", "activityDate");
	 	    crt.addProjection("adjustmentType", "adjustmentType");
	 	    crt.addProjection("adjustmentAmount", "adjustmentAmount");
	 	    crt.addProjection("adjustmentCategory", "adjustmentCategory");
	 	    crt.addProjection("incidentAccountCodeId", "incidentAccountCodeId");
	 	    crt.addProjection("accountingCode", "accountingCode");
	 	    crt.addProjection("commodity", "commodity");
	 	    crt.addProjection("adjustmentCategoryDesc","adjustmentCategoryDesc");
	        crt.addScalar("assignmentId", Long.class.getName());
	        crt.addScalar("incidentResourceId", Long.class.getName());
	        crt.addScalar("timeAssignAdjustId", Long.class.getName());
	        crt.addScalar("timeAssignAdjustId", Long.class.getName());
	        crt.addScalar("incidentAccountCodeId", Long.class.getName());
	        crt.addScalar("adjustmentAmount", BigDecimal.class.getName());
	        crt.addScalar("activityDate", Date.class.getName());
			
	        query1.setResultTransformer(crt);
	        
	        Collection<IncidentResourceTimeAdustDataVo> vos = query1.list();

			return vos;

		}catch(Exception e){
			throw new PersistenceException(e.getMessage());
		}
	}

	public Date getIncidentStartDate(Long assignmentId) throws PersistenceException {
		String sql=""+
			"select to_char(i.incident_start_date,'MM/DD/YYYY') " +
			"from isw_incident i " +
			"where i.id =  " +
			"( " +
			"	select incident_id " +
			"	from isw_incident_resource ir " +
			"	      , isw_work_period wp " +
			"	      , isw_work_period_assignment wpa " +
			"	      , isw_assignment a " +
			"	where ir.id = wp.incident_resource_id " +
			"	and wp.id =wpa.work_period_id " +
			"	and wpa.assignment_id = a.id " +
			"	and a.id = "+assignmentId+" " +
			") ";
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql);
		
		Date dt=null;
		
		try{
			//Object v=query1.uniqueResult();
			String val=(String)query1.uniqueResult();
					
			if(null != val)
			  dt=DateUtil.toDate(val, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return dt;
	}


	public Date getIncidentStartDateForCrew(Collection<Long> irIds) throws PersistenceException {
		String sql=""+
			"select to_char(min(i.incident_start_date),'MM/DD/YYYY') " +
			"from isw_incident i " +
			"where i.id in  " +
			"( " +
			"	select incident_id " +
			"	from isw_incident_resource ir " +
			"	where ir.id in ( "+CollectionUtility.toCommaDelimitedLongs(irIds)+") " +
			") ";
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql);
		
		Date dt=null;
		
		try{
			//Object v=query1.uniqueResult();
			String val=(String)query1.uniqueResult();
					
			if(null != val)
			  dt=DateUtil.toDate(val, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return dt;
	}
}
