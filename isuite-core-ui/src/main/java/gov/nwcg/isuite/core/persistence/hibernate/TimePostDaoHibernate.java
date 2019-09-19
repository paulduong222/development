package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimePostQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimePostStopDateFix;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.TimePostVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.OrderBySql;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

@SuppressWarnings("unchecked")
public class TimePostDaoHibernate extends TransactionSupportImpl implements TimePostDao{

	private final CrudDao<AssignmentTimePost> crudDao;
	
	public TimePostDaoHibernate(final CrudDao<AssignmentTimePost> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public AssignmentTimePost getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, AssignmentTimePostImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(AssignmentTimePost persistable) throws PersistenceException {
		
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<AssignmentTimePost> persistables) throws PersistenceException {
		
		for(AssignmentTimePost persistable : persistables) {
			crudDao.save(persistable);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(AssignmentTimePost persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public void deleteById(Long id) throws PersistenceException {
		String sql = "DELETE FROM ISW_ASSIGN_TIME_POST WHERE ID = " + id ;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void deleteAll(Collection<AssignmentTimePost> persistables) throws PersistenceException {
		
		for(AssignmentTimePost persistable : persistables) {
			crudDao.delete(persistable);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#getGrid(java.lang.Long)
	 */
	public Collection<AssignmentTimePostVo> getGrid(Long assignmentTimeId) throws PersistenceException {
		
		try{
			Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);
			crit.addOrder(Order.asc("this.postStartDate"));
			
			if(null == assignmentTimeId || assignmentTimeId < 1){
				throw new PersistenceException("assignmentTimeId is required.");
			}
			
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
			criteria.add( new FilterCriteria("this.assignmentTimeId",assignmentTimeId,FilterCriteria.TYPE_EQUAL));
			criteria.add( new FilterCriteria("this.primaryPosting",Boolean.TRUE,FilterCriteria.TYPE_EQUAL));
			
			CriteriaBuilder.addCriteria(crit, criteria);

			
			Collection<AssignmentTimePost> entities = crit.list();

			if(null != entities && entities.size()>0)
				return AssignmentTimePostVo.getInstances(entities, true);
			else
				return new ArrayList<AssignmentTimePostVo>();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#getGridCrew(java.util.Collection)
	 */
	public Collection<AssignmentTimePostVo> getGridCrew(Collection<Long> ids) throws PersistenceException {
		
		try{
			Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);
			crit.addOrder(Order.asc("this.postStartDate"));

			crit.add(Expression.in("assignmentTimeId", ids));
			crit.add(Expression.eq("primaryPosting", Boolean.TRUE));
			
			Collection<AssignmentTimePost> entities = crit.list();

			if(null != entities && entities.size()>0){
				return AssignmentTimePostVo.getInstancesCrew(entities, true);
			}else
				return new ArrayList<AssignmentTimePostVo>();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<AssignmentTimePost> getByIncidentResourceId(Long id) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);

		String irSql = " this_.assignment_time_id in ("+
							"  select id from isw_assignment_time " +
							"  where assignment_id in ("+
							"     select assignment_id from isw_work_period_assignment " +
							"     where work_period_id in (" +
							"       select id from isw_work_period where " +
							"       incident_resource_id = " + id + " " +
							"     ) " +
							"  ) " +
							") ";
		
		crit.add(Expression.sql(irSql));
		
		String sql2="this_.is_primary_posting = " + (super.isOracleDialect() ? 1 : true ) + " ";
		crit.add(Expression.sql(sql2));

		return crit.list();
	}
	
	public Collection<AssignmentTimePost> getByAssignmentId(Long assignmentId) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);

		String sql = " this_.assignment_time_id in ("+
							"  select id from isw_assignment_time " +
							"  where assignment_id = " + assignmentId + ") ";
		
		crit.add(Expression.sql(sql));
		crit.addOrder(Order.asc("this.postStartDate"));
		//String sql2="this_.is_primary_posting = " + (super.isOracleDialect() ? 1 : true ) + " ";
		//crit.add(Expression.sql(sql2));

		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#getByFilter(gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl)
	 */
	public Collection<AssignmentTimePost> getByFilter(TimePostQueryFilterImpl filter) throws PersistenceException {
	
		Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);

		crit.setFetchMode("refContractorRate",FetchMode.JOIN);
		crit.createAlias("refContractorRate", "rcr");

		if(null != filter){
		
			if(LongUtility.hasValue(filter.getExcludeAssignmentTimePostId()))
				crit.add(Expression.ne("id", filter.getExcludeAssignmentTimePostId()));

			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
				crit.add(Expression.eq("id",filter.getAssignmentTimePostId()));

			if(LongUtility.hasValue(filter.getIncidentResourceId())){
				String irSql = " this_.assignment_time_id in ("+
								"  select id from isw_assignment_time " +
								"  where assignment_id in ("+
								"     select assignment_id from isw_work_period_assignment " +
								"     where work_period_id in (" +
								"       select id from isw_work_period where " +
								"       incident_resource_id = " + filter.getIncidentResourceId() + " " +
								"     ) " +
								"  ) " +
								") ";
				crit.add(Expression.sql(irSql));
			}

			if(BooleanUtility.isTrue(filter.getOnlyPrimary()))
				crit.add(Expression.eq("primaryPosting",Boolean.TRUE ) );

			if(BooleanUtility.isTrue(filter.getOnlyGuarantee()))
				crit.add(Expression.eq("guaranteePosting",Boolean.TRUE ) );
			
			if(BooleanUtility.isTrue(filter.getOnlySpecial()))
				crit.add(Expression.eq("specialPosting",Boolean.TRUE ) );

			if(BooleanUtility.isTrue(filter.getExcludePrimary()))
				crit.add(Expression.eq("primaryPosting",Boolean.FALSE ) );
			
			if(BooleanUtility.isTrue(filter.getExcludeSpecial()))
				crit.add(Expression.eq("specialPosting",Boolean.FALSE ) );
			
			if(BooleanUtility.isTrue(filter.getExcludeGuarantee()))
				crit.add(Expression.eq("guaranteePosting",Boolean.FALSE ) );
			
			if(StringUtility.hasValue(filter.getUnitOfMeasure()))
				crit.add(Expression.eq("rcr.unitOfMeasure", filter.getUnitOfMeasure()));

			if(BooleanUtility.isTrue(filter.getExcludeInvoiced()))
				crit.add(Expression.isEmpty("timeInvoices"));
			
			if(BooleanUtility.isTrue(filter.getCompareTime())){
				if(DateUtil.hasValue(filter.getStartDate()) && DateUtil.hasValue(filter.getStopDate())){
					String sql="";
					sql=sql+" ( " +
					"(to_date(to_char(this_.post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
					" between  " +
					"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					" and " +
					"     to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					")  " +
					" OR " +
					"(to_date(to_char(this_.post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
					" between " +
					"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					" and " +
					"     to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					" ) "+
					/*
					" ( " +
					"     to_date(to_char(this_.post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
					"         <= " + 
					"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					"     and " +
					"     to_date(to_char(this_.post_stop_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
					"         > " +
					"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					"   ) "+
					"   OR " +
					"  ( " +
					"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					"         <= " + 
					"     to_date(to_char(this_.post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
					"     and " +
					"           to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
					"         > " +
					"     to_date(to_char(this_.post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
					"  ) " +
					*/
					" ) " ;
					crit.add(Expression.sql(sql));
				}
			}else{
				if(DateUtil.hasValue(filter.getStartDate()) && BooleanUtility.isFalse(filter.getCompareTime()))
					crit.add(Expression.eq("postStartDate",filter.getStartDate() ));
				if(DateUtil.hasValue(filter.getStopDate()) && BooleanUtility.isFalse(filter.getCompareTime()))
					crit.add(Expression.eq("postStopDate",filter.getStopDate() ));

			}
		}
		
		return crit.list();
	}
	
	public Collection<AssignmentTimePost> getTimePostings(Date lastDate, Long incidentResourceId, Long incidentAccountCodeId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);

		if(LongUtility.hasValue(incidentResourceId)){
			String irSql = " this_.assignment_time_id in ("+
							"  select id from isw_assignment_time " +
							"  where assignment_id in ("+
							"     select assignment_id from isw_work_period_assignment " +
							"     where work_period_id in (" +
							"       select id from isw_work_period where " +
							"       incident_resource_id = " + incidentResourceId + " " +
							"     ) " +
							"  ) " +
							") ";
			crit.add(Expression.sql(irSql));
		}

		String sql="";
		sql=sql+" " +
		"(to_date(to_char(this_.post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
		" <= " +
		"    to_date('"+DateUtil.toDateString(lastDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') )"; 
		crit.add(Expression.sql(sql));

		String sql2 = "";
		sql2="( " +
			    "this_.id not IN (select  assign_time_post_id from isw_assign_time_post_invoice) " +
			    "or " +
			    "this_.id not in (select  assign_time_post_id from isw_assign_time_post_invoice where time_invoice_id in (select id from isw_time_invoice where deleted_date is null ) ) " +  
			 ") "; 
		crit.add(Expression.sql(sql2));

		if(LongUtility.hasValue(incidentAccountCodeId)){
			crit.add(Expression.eq("incidentAccountCodeId", incidentAccountCodeId));
		}
		
		//String sql3="this_.is_primary_posting = " + (super.isOracleDialect() ? 1 : true ) + " ";
		/*
		sql3=" ( " +
			 "    ( " +
			 "      this_.is_primary_posting =  " + (super.isOracleDialect() ? 1 : true ) + " " +
			 "      AND " +
			 "      this_.is_special_posting = " + (super.isOracleDialect() ? 0 : false  ) + " " +
			 "     ) " +
			 "     OR " +
			 "    ( " +
			 "      this_.is_primary_posting =  " + (super.isOracleDialect() ? 0 : false ) + " " +
			 "      AND " +
			 "      this_.is_special_posting = " + (super.isOracleDialect() ? 1 : true ) + " " +
			 "     ) " +
			 " ) ";
		*/
		//crit.add(Expression.sql(sql3));
		
		String sqlOrder="";
		sqlOrder="this_.post_start_date asc " +
				 ", this_.contractor_post_type asc " +
				 ", this_.is_half_rate asc ";
				 //",case " +
				 //"  when (this_.is_primary_posting = " + (super.isOracleDialect()?1:true) + " and this_.is_special_posting = " + (super.isOracleDialect()?0:false) + " ) then 0 "+
				 //"  when (this_.is_primary_posting = " + (super.isOracleDialect()?0:false) + " and this_.is_special_posting = " + (super.isOracleDialect()?1:true) + " ) then 1 "+
				 //"end " + 
		 		 //", this_.contractor_post_type asc ";
		crit.addOrder(OrderBySql.sql(sqlOrder));
		return crit.list();
	}
	
	public int getResourceTimePostCount(Long incidentResourceId) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourceTimePostCountQuery(incidentResourceId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public int getResourcesTimePostCount(Collection<Long> ids) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourcesTimePostCountQuery();
			
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

	public int getResourceInvoicedTimePostCount(Long incidentResourceId) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourceInvoicedTimePostCountQuery(incidentResourceId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public int getResourcesInvoicedTimePostCount(Collection<Long> ids) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourcesInvoicedTimePostCountQuery();
			
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

	public int getResourceNonInvoicedTimePostCount(Long incidentResourceId) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourceNonInvoicedTimePostCountQuery(incidentResourceId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
			
			
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<AssignmentTimePost> getResourceTimePosts(Long incidentResourceId,Date lastDate) throws PersistenceException {
		try{
			String sql = TimePostQuery.getResourceTimePostingIds(incidentResourceId,lastDate);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);

			Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
			
			Collection<Object> ids = query.list();
			
			if(CollectionUtility.hasValue(ids)){
				for(Object id : ids){
					Long lngId = TypeConverter.convertToLong(id);
					
					AssignmentTimePost atp = this.getById(lngId,AssignmentTimePostImpl.class);
					timePosts.add(atp);
				}
			}
			return timePosts;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public int deleteResourceNonInvoicedTimePosts(Long incidentResourceId) throws PersistenceException {
		try{
			//String sql = TimePostQuery.getResourceNonInvoicedTimePostCountQuery(incidentResourceId);
			
			//SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			//Object rslt = query.uniqueResult();
			int cnt=0;
			//if(null != rslt)
			//	cnt= TypeConverter.convertToInteger(rslt).intValue();
			
			//if(cnt > 0){
				String sql = TimePostQuery.getDeleteResourceNonInvoicedTimePostsQuery(incidentResourceId);
				
				SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
				
				cnt=query.executeUpdate();
			//}
			
			return cnt;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#getDuplicateTimePostCount(java.lang.Long, java.util.Date, java.util.Date)
	 */
	public int getDuplicateTimePostCount(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException {
		try{
			String sql = "";
			//if(super.isOracleDialect())
				sql=TimePostQuery.getDuplicateTimePostCountQueryOr(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
			//else
			//	sql=TimePostQuery.getDuplicateTimePostCountQuery(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
				
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public int getDuplicateTimePostCountSpecial(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException {
		try{
			String sql = TimePostQuery.getDuplicateTimePostCountSpecialQuery(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<Long> getDuplicateTimePostIdsSpecial(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException {
		try{
			
			String sql = TimePostQuery.getDuplicateTimePostIdsSpecialQuery(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.list();
			Collection<Long> ids = new ArrayList<Long>();
			
			if(null != rslt){
				for(Object obj : (Collection<Object>)rslt){
					Long id = TypeConverter.convertToLong(obj);
					ids.add(id);
				}
			}
			
			return ids;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<Long> getDuplicateTimePostIds(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException {
		try{
			String sql = "";
			//if(super.isOracleDialect())
				sql=TimePostQuery.getDuplicateTimePostIdsQueryOr(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
			//else
			//	sql=TimePostQuery.getDuplicateTimePostIdsQuery(timePostId, incidentResourceId, invoiceOnly,startTime, stopTime);
				
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.list();
			
			Collection<Long> ids = new ArrayList<Long>();
			
			if(null != rslt){
				for(Object obj : (Collection<Object>)rslt){
					Long id = TypeConverter.convertToLong(obj);
					ids.add(id);
				}
			}
			
			return ids;
			
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<String> getDuplicateContractorPosts(TimePostQueryFilterImpl filter) throws PersistenceException {
		try{
			String sql = "";
			
			if(StringUtility.hasValue(filter.getUnitOfMeasure()) &&
					filter.getUnitOfMeasure().equals("HOURLY") && BooleanUtility.isTrue(filter.getIncludeTime())){
				sql = TimePostQuery.getDuplicateTimePostHourlyDatesQuery(isOracleDialect(), filter);
			}else
				sql = TimePostQuery.getDuplicateTimePostDatesQuery(isOracleDialect(), filter);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.list();
			if(null != rslt)
				return (Collection<String>)rslt;
			else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<Long> getDuplicateContractorPostsIds(TimePostQueryFilterImpl filter) throws PersistenceException {
		try{
			String sql = "";
			
			if(StringUtility.hasValue(filter.getUnitOfMeasure()) &&
					filter.getUnitOfMeasure().equals("HOURLY")){
				sql = TimePostQuery.getDuplicateTimePostHourlyIdsQuery(isOracleDialect(), filter);
			}else
				sql = TimePostQuery.getDuplicateTimePostIdsQuery(isOracleDialect(), filter);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Collection<Object> rslt = (Collection<Object>)query.list();
			if(null != rslt){
				Collection<Long> ids = new ArrayList<Long>();
				try{
					for(Object id : rslt){
						Long lngid = TypeConverter.convertToLong(id);
						ids.add(lngid);
					}
				}catch(Exception e){}
				
				return ids;
			}else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public Collection<String> getDuplicateDailyPosts(TimePostQueryFilterImpl filter) throws PersistenceException {
		try{
			String sql = "";
			
			if(StringUtility.hasValue(filter.getUnitOfMeasure()) &&
					filter.getUnitOfMeasure().equals("HOURLY") && BooleanUtility.isTrue(filter.getIncludeTime())){
				sql = TimePostQuery.getDuplicateTimePostHourlyDatesQuery(isOracleDialect(), filter);
			}else
				sql = TimePostQuery.getDuplicateTimePostDatesQuery(isOracleDialect(), filter);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.list();
			if(null != rslt)
				return (Collection<String>)rslt;
			else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Collection<Long> getDuplicateDailyPostsIds(TimePostQueryFilterImpl filter) throws PersistenceException {
		try{
			String sql = "";
			
			if(StringUtility.hasValue(filter.getUnitOfMeasure()) &&
					filter.getUnitOfMeasure().equals("HOURLY")){
				sql = TimePostQuery.getDuplicateTimePostHourlyIdsQuery(isOracleDialect(), filter);
			}else
				sql = TimePostQuery.getDuplicateTimePostIdsQuery(isOracleDialect(), filter);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Collection<Object> rslt = (Collection<Object>)query.list();
			if(null != rslt){
				Collection<Long> ids = new ArrayList<Long>();
				try{
					for(Object id : rslt){
						Long lngid = TypeConverter.convertToLong(id);
						ids.add(lngid);
					}
				}catch(Exception e){}
				
				return ids;
			}else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public Collection<Long> updateDuplicateHourlyPosts(TimePostQueryFilterImpl filter) throws PersistenceException {
		try{
			String sqlIds = TimePostQuery.getUpdateIdsDuplicateTimePostDatesQuery(isOracleDialect(),filter);
			SQLQuery query2 = super.getHibernateSession().createSQLQuery(sqlIds);
			Collection<Object> atpUpdateIds = query2.list();
			
			String sql = TimePostQuery.getUpdateDuplicateTimePostDatesQuery(isOracleDialect(), filter);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);

			query.executeUpdate();

			Collection<Long> rtnIds = new ArrayList<Long>();
			for(Object id : atpUpdateIds){
				Long atpId = TypeConverter.convertToLong(id);
				rtnIds.add(atpId);
			}
			
			return rtnIds;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public void updateDuplicateDailyPosts(Long timePostId, Long incidentResourceId, Boolean invoiceOnly, Date startDate, Date stopDate, String postType) throws PersistenceException {
		try{
			String sql = TimePostQuery.getUpdateDuplicateDailyPostQuery(super.isOracleDialect(),timePostId, incidentResourceId, invoiceOnly, startDate, stopDate, postType);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.executeUpdate();
			
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public Long getIncidentResourceId(Long assignmentTimeId) throws PersistenceException {
		
		try{
			String sql = TimePostQuery.getIncidentResourceIdQuery(assignmentTimeId);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			Object rslt = query.uniqueResult();
			
			if(null != rslt)
				return TypeConverter.convertToLong(rslt);
			else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#deleteDuplicateTimePosts(java.lang.Long, java.util.Date, java.util.Date)
	 */
	public void deleteDuplicateTimePosts(Long incidentResourceId, Date startTime, Date stopTime) throws PersistenceException {
		try{
			String sql = TimePostQuery.getDeleteTimePostsBeingReplaced(incidentResourceId, startTime, stopTime);
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);

			query.executeUpdate();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public AssignmentTimePost getLatestTimePosting(Long incidentResourceId, Long atId, Boolean isPrimary) throws PersistenceException {
		
		String sql = "select max(id) from isw_assign_time_post where assignment_time_id = "+atId + " ";
		if(isPrimary)
			sql=sql+"AND is_primary_posting = " + (super.isOracleDialect() ? 1 : true )+ " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		try{
			Long id = TypeConverter.convertToLong(rslt);
			
			return this.getById(id, AssignmentTimePostImpl.class);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
	}

	public Collection<Long> getPostByFilter(TimePostQueryFilterImpl filter) throws PersistenceException {
		String sql = TimePostQuery.getPostByFilter(super.isOracleDialect(), filter);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> ids = (Collection<Object>)query.list();
		Collection<Long> longIds = new ArrayList<Long>();
		
		for(Object o : ids){
			try{
				Long id = TypeConverter.convertToLong(o);
				longIds.add(id);
			}catch(Exception ee){}
		}
		
		return longIds;
		
	}

	public void deletePersonInvoiceRecords(Long assignmentTimeId) throws PersistenceException {
		String sql = "DELETE FROM ISW_ASSIGN_TIME_POST_INVOICE "+
		"WHERE ASSIGN_TIME_POST_ID IN ("+
		"      SELECT ID FROM ISW_ASSIGN_TIME_POST WHERE ASSIGNMENT_TIME_ID = "+assignmentTimeId+ " " +
		") ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	public void deleteInvoicedRecords(Long timePostId) throws PersistenceException {
		String sql = "DELETE FROM ISW_ASSIGN_TIME_POST_INVOICE "+
					"WHERE ASSIGN_TIME_POST_ID = " + timePostId;
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public int getInvoicedCount(Long timePostId) throws PersistenceException {
		int rtn=0;
		
		String sql = "SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST_INVOICE "+
		"WHERE ASSIGN_TIME_POST_ID = " + timePostId + " "+
		//"AND TIME_INVOICE_ID IN (" +
		//"  SELECT ID FROM ISW_TIME_INVOICE WHERE IS_FINAL = " + (super.isOracleDialect() ? 1 : true) + " " +
		//") " +
		"AND TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL )";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object cnt = query.uniqueResult();
		
		try{
			rtn=TypeConverter.convertToInt(cnt);
		}catch(Exception e){}
		
		return rtn;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TimePostDao#getChildUniqueAcctCodeIdsByDate(java.lang.Long, java.lang.Long, java.util.Date)
	 */
	public Collection<Long> getChildUniqueAcctCodeIdsByDate(Long incResId, Long excludeAcctCodeId, Date dt) throws PersistenceException {
		String sql = TimePostQuery.getChildUniqueAcctCodeIdsByDate(incResId, excludeAcctCodeId, dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> olist = (Collection<Object>)query.list();
		Collection<Long> list = new ArrayList<Long>();
		
		try{
			for(Object o : olist){
				Long lng=TypeConverter.convertToLong(o);
				list.add(lng);
			}
		}catch(Exception e){
			
		}
		
		return list;
	}
	
	public Long getAssignmentTimeId(Long id) throws PersistenceException {
		String sql = "select assignment_time_id " +
					"from isw_assign_time_post " + 
					"where id = " + id + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				Long atid = TypeConverter.convertToLong(rslt);
				return atid;
			}catch(Exception e){
				// smother
			}
		}
		
		return null;
	}

	public Collection<AssignmentTimePost> getCrewTimePostings(Collection<Long> irids,TimePostQueryFilterImpl f) throws PersistenceException {
		String sql = TimePostQuery.getCrewTimePostingIds(f);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameterList("irids", irids);
		
		Collection<AssignmentTimePost> atps = new ArrayList<AssignmentTimePost>();
		
		Collection<Object> ids = (Collection<Object>)query.list();
		
		if(CollectionUtility.hasValue(ids)){
			for(Object id : ids){
				try{
					Long longId=TypeConverter.convertToLong(id);
					AssignmentTimePost atp = this.getById(longId, AssignmentTimePostImpl.class);
					atps.add(atp);
				}catch(Exception e){}
			}
		}

		return atps;
	}

	public Date getLatestTimePostingDateByResourceId(Long resourceId) throws PersistenceException {
		String sql = TimePostQuery.getLatestTimePostingDateByResourceIdQuery(resourceId);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object result = query.uniqueResult();
		if(null != result){
			try{
				Date dt = TypeConverter.convertToDate(result);
				return dt;
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public Date getLatestTimePostingDateForParentId(Long irId) throws PersistenceException {
		String sql = TimePostQuery.getLatestTimePostingDateForParentQuery(irId);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object result = query.uniqueResult();
		if(null != result){
			try{
				Date dt = TypeConverter.convertToDate(result);
				return dt;
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public Collection<Date> getNonInvoicedUniqueTimePostDates(Long incidentResourceId) throws PersistenceException {
		Collection<Date> dates = new ArrayList<Date>();
		
		String sql = TimePostQuery.getNonInvoicedUniqueTimePostDatesQuery(incidentResourceId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		try{
			Collection<Object> list = query.list();
			if(CollectionUtility.hasValue(list)){
				for(Object o : list){
					Date dt = TypeConverter.convertToDate(o);
					dates.add(dt);
				}
			}
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return dates;
	}
	
	public Date getLastInvoiceDate(Long assignmentTimeId) throws PersistenceException {
		Date dt = null;
		
		String sql = "SELECT MAX(ti.last_include_date) " +
					 "FROM isw_time_invoice ti" +
					 "     ,isw_assign_time_post_invoice atpi"+
					 "     ,isw_assign_time_post atp " +
					 "WHERE ti.id = atpi.time_invoice_id " +
					 "AND atpi.assign_time_post_id = atp.id " +
					 "AND atp.assignment_time_id = " + assignmentTimeId + " "+
					 "AND ti.is_draft = " + (super.isOracleDialect() ? 0 : false) + " " +
					 "AND ti.deleted_date is null "
					 ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				dt=TypeConverter.convertToDate(rslt);
			}catch(Exception e){}
		}
		
		return dt;
	}
	
	public Collection<TimePostVo> getTimePostingsByAssignmentTimeId(Long id) throws PersistenceException {
		Collection<TimePostVo> vos = new ArrayList<TimePostVo>();
		
		String sql = TimePostQuery.getPostingsByAssignmentTimeId(id);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

        CustomResultTransformer crt = new CustomResultTransformer(TimePostVo.class);
 	    
        crt.addScalar("id", Long.class.getName());
        crt.addScalar("assignmentTimeId", Long.class.getName());
        crt.addScalar("incidentAccountCodeId", Long.class.getName());
        crt.addScalar("postStartDate", Date.class.getName());
        crt.addScalar("postStopDate", Date.class.getName());
        crt.addScalar("quantity", BigDecimal.class.getName());
        crt.addScalar("otherRate", BigDecimal.class.getName());
        crt.addScalar("rateAmount", BigDecimal.class.getName());
        crt.addScalar("guaranteeAmount", BigDecimal.class.getName());
        crt.addScalar("contractorRateId", Long.class.getName());
        crt.addScalar("halfRate", Boolean.class.getName());
        crt.addScalar("primaryPosting", Boolean.class.getName());
        crt.addScalar("specialPosting", Boolean.class.getName());
        crt.addScalar("guaranteePosting", Boolean.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
		
	}
	
	public void fixStopTimes(Long incidentResourceId, Long incidentId) throws PersistenceException {
		String andIncResId=""+
			"and assignment_time_id = ( " +
			"    select at.id " +
			"    from isw_assignment_time at, isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp " +
			"    where at.assignment_id = a.id and a.id = wpa.assignment_id and wpa.work_period_id = wp.id and wp.incident_resource_id = "+ (LongUtility.hasValue(incidentResourceId)?String.valueOf(incidentResourceId):"") +" " +
			") ";
		String andIncId=""+
			"and assignment_time_id = ( " +
			"    select at.id " +
			"    from isw_assignment_time at, isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp " +
			"    where at.assignment_id = a.id and a.id = wpa.assignment_id and wpa.work_period_id = wp.id and wp.incident_resource_id in ("+
			"       select id from isw_incident_resource where incident_id = "+(LongUtility.hasValue(incidentId)?String.valueOf(incidentId):"")+" " +
			"    )" +
			") ";
		
		try{
			int cnt=0;
			if(LongUtility.hasValue(incidentResourceId)){
				// only run fixes if there are time post records
				String sqlCheck="select count(atp.id) "+
				"from isw_assign_time_post atp " +
				" , isw_assignment_time at " +
				", isw_assignment a " +
				", isw_work_period_assignment wpa " +
				", isw_work_period wp " +
				"where atp.assignment_time_id = at.id " +
				"and atp.employment_type != 'CONTRACTOR' " +
				"and at.assignment_id = a.id " +
				"and a.end_date is null " +
				"and a.id = wpa.assignment_id " +
				"and wpa.work_period_id = wp.id " +
			    "and wp.incident_resource_id = " + incidentResourceId + " " +
				"and special_pay_id = (select id from iswl_special_pay where code='HP' ) "
			    ;
				SQLQuery query1 = getHibernateSession().createSQLQuery(sqlCheck);
				Object val=query1.uniqueResult();
				if(null != val)
					cnt=Integer.parseInt(String.valueOf(val));
				if(cnt==0)
					return;
			}
			
			if(super.isOracleDialect()==true){
				for(String s : TimePostStopDateFix.queryListOra){
					String sql="";
					if(LongUtility.hasValue(incidentResourceId)){
						sql=s+andIncResId;
					}
					if(LongUtility.hasValue(incidentId)){
						sql=s+andIncId;
					}
					SQLQuery query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}else{
				for(String s : TimePostStopDateFix.queryListPg){
					String sql="";
					if(LongUtility.hasValue(incidentResourceId)){
						sql=s+andIncResId;
					}
					if(LongUtility.hasValue(incidentId)){
						sql=s+andIncId;
					}
					SQLQuery query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			// smother for now
			System.out.println(e.getMessage());
		}
	}
	
	public void fixStopTimes2(Collection<Long> assignmentTimeIds) throws PersistenceException {
		String atIdsList=CollectionUtility.toCommaDelimitedLongs(assignmentTimeIds);
		
		String andAssignmentTimeIds=""+
			"and assignment_time_id in ( " + atIdsList + ") ";
		
		try{
			if(super.isOracleDialect()==true){
				for(String s : TimePostStopDateFix.queryListOra){
					String sql=s+andAssignmentTimeIds;
					SQLQuery query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}else{
				for(String s : TimePostStopDateFix.queryListPg){
					String sql=s+andAssignmentTimeIds;
					SQLQuery query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			// smother for now
			System.out.println(e.getMessage());
		}
	}
	
	public Collection<IncidentResourceTimePostDataVo> getTimePostingData(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException {
		try{
			
			String sql1 = TimePostQuery.getIncidentResourceTimePostDataQuery(incidentResourceParentId,postDate,subsOnly,super.isOracleDialect());
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);

			CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceTimePostDataVo.class);
	 	    crt.addProjection("incidentResourceId", "incidentResourceId");
	 	    crt.addProjection("assignTimePostId", "assignTimePostId");
	 	    crt.addProjection("postStartDate", "postStartDate");
	 	    crt.addProjection("postStopDate", "postStopDate");
	 	    crt.addProjection("kindCode", "kindCode");
	 	    crt.addProjection("rateType", "rateType");
	 	    crt.addProjection("quantity", "quantity");
	 	    crt.addProjection("rateAmount", "rateAmount");
	 	    crt.addProjection("otherRate", "otherRate");
	 	    crt.addProjection("employmentType", "employmentType");
	 	    crt.addProjection("accountCode", "accountCode");
	 	    crt.addProjection("rateClassRate", "rateClassRate");
	 	    crt.addProjection("rateClassName", "rateClassName");
	 	    crt.addProjection("training", "training");
	 	    crt.addProjection("incidentName", "incidentName");
	 	    crt.addProjection("incidentUnitCode", "incidentUnitCode");
	 	    crt.addProjection("incidentNumber", "incidentNumber");
	 	    crt.addProjection("returnTravelStartOnly", "returnTravelStartOnly");
	 	    crt.addProjection("specialPayCode", "specialPayCode");
	 	    crt.addProjection("specialPayId", "specialPayId");
	 	    crt.addProjection("regionCode", "regionCode");
	 	    crt.addProjection("halfRate", "halfRate");
	 	    
	        crt.addScalar("incidentResourceId", Long.class.getName());
	        crt.addScalar("resourceId", Long.class.getName());
	        crt.addScalar("assignmentId", Long.class.getName());
	        crt.addScalar("assignmentTimeId", Long.class.getName());
	        crt.addScalar("assignTimePostId", Long.class.getName());
	        crt.addScalar("quantity", BigDecimal.class.getName());
	        crt.addScalar("rateAmount", BigDecimal.class.getName());
	        crt.addScalar("otherRate", BigDecimal.class.getName());
	        crt.addScalar("rateClassRate", BigDecimal.class.getName());
	        crt.addScalar("postStartDate", Date.class.getName());
	        crt.addScalar("postStopDate", Date.class.getName());
	        crt.addScalar("training", Boolean.class.getName());
	        crt.addScalar("returnTravelStartOnly", Boolean.class.getName());
	        crt.addScalar("halfRate", Boolean.class.getName());
	        crt.addScalar("specialPayId", Long.class.getName());
			
	        query1.setResultTransformer(crt);
	        
	        Collection<IncidentResourceTimePostDataVo> vos = query1.list();

			return vos;
		}catch(Exception e){
			throw new PersistenceException(e.getMessage());
		}
	}
	
	public void saveInvoicedAmount(AssignmentTimePost atp) throws PersistenceException {
		String sql ="update isw_assign_time_post set invoiced_amount = " + atp.getInvoicedAmount() + "  where id = " + atp.getId();
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql);
		query1.executeUpdate();
	}
}
