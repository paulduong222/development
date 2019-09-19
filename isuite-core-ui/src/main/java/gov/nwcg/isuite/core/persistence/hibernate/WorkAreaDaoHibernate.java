package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaUserImpl;
import gov.nwcg.isuite.core.domain.views.impl.WorkAreaIncidentsViewImpl;
import gov.nwcg.isuite.core.domain.views.impl.WorkAreaResourceViewImpl;
import gov.nwcg.isuite.core.domain.views.impl.WorkAreaResourcesViewImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.filter.impl.WorkAreaResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.WorkAreaQuery;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaIncidentVo;
import gov.nwcg.isuite.core.vo.WorkAreaPicklistVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.IncidentRestrictedStatusTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;



public class WorkAreaDaoHibernate extends TransactionSupportImpl implements WorkAreaDao {
   
   static final Logger LOG = Logger.getLogger(WorkAreaDaoHibernate.class);
   private static final String STATIC_DATA = "STATIC_DATA";
   private final CrudDao<WorkArea>         crudDao;
   
   public WorkAreaDaoHibernate(final CrudDao<WorkArea> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#getGrid(gov.nwcg.isuite.domain.access.WorkAreaFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<WorkAreaGridVo> getGrid(WorkAreaFilter filter) throws PersistenceException {

	   if (filter == null || filter.getCurrentUserId() == null) {
         throw new PersistenceException("userId of WorkAreaFilter must not be null.");
      }

	   StringBuffer sql = new StringBuffer().append(WorkAreaQuery.GET_WORK_AREA_GRID_SQL_QUERY);

	   if(StringUtility.hasValue(filter.getName()))
    	   sql.append("AND wa.name LIKE '"+filter.getName().toUpperCase().trim() + "%' ");

	   if(StringUtility.hasValue(filter.getDescription()))
    	   sql.append("AND wa.description LIKE '"+filter.getDescription().toUpperCase().trim() + "%' ");

	   if(StringUtility.hasValue(filter.getSharedOutAsString())){
			if(filter.getSharedOutAsString().equalsIgnoreCase("YES"))
	    	   sql.append("AND wa.shared_out_flg = " + super.getBooleanComparison(Boolean.TRUE) + " ");
			else if(filter.getSharedOutAsString().equalsIgnoreCase("NO"))
		       sql.append("AND wa.shared_out_flg = " + super.getBooleanComparison(Boolean.FALSE) + " ");
	   }

	   if(StringUtility.hasValue(filter.getCreatedBy()))
    	   sql.append("AND wa.created_by LIKE '"+filter.getCreatedBy().toUpperCase() + "%' ");
	   
	   if(StringUtility.hasValue(filter.getStandardAsString())){
			if(filter.getStandardAsString().equalsIgnoreCase("YES"))
	    	   sql.append("AND wa.standard_organization_id IS NOT NULL ");
			else if(filter.getStandardAsString().equalsIgnoreCase("NO"))
		       sql.append("AND wa.standard_organization_id IS NULL ");
	   }
	   
	   sql.append(" order by wa.name asc ");
	   
       SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
       q.setLong("userid", filter.getCurrentUserId());
	   
       CustomResultTransformer crt = new CustomResultTransformer(WorkAreaGridVo.class);
	   crt.addProjection("id", "id");
	   crt.addProjection("name", "waName");
	   crt.addProjection("description", "waDesc");
	   crt.addProjection("standardOrganizationId", "standardOrgId");
	   crt.addProjection("sharedOut", "sharedOut");
	   crt.addProjection("createdBy", "createdByWhom");
	   crt.addProjection("userId", "userId");
       
       crt.addScalar("id", Long.class.getName());
       crt.addScalar("sharedOut", Boolean.class.getName());
       crt.addScalar("standardOrganizationId", Long.class.getName());
       crt.addScalar("userId", Long.class.getName());

       
       q.setResultTransformer(crt); 

       
       return q.list();
   }

   @SuppressWarnings("unchecked")
   public WorkAreaGridVo getGridItem(Long userId, Long workAreaId) throws PersistenceException {

	   if (userId == null) {
         throw new PersistenceException("userId must not be null.");
       }

	   if (workAreaId == null) {
	         throw new PersistenceException("workAreaId must not be null.");
	   }
	   
	   StringBuffer sql = new StringBuffer().append(WorkAreaQuery.GET_WORK_AREA_GRID_SQL_QUERY);
	   sql.append(" and wa.id = " + workAreaId + " ");
	   
       SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
       q.setLong("userid", userId);
	   
       CustomResultTransformer crt = new CustomResultTransformer(WorkAreaGridVo.class);
	   crt.addProjection("id", "id");
	   crt.addProjection("name", "waName");
	   crt.addProjection("description", "waDesc");
	   crt.addProjection("standardOrganizationId", "standardOrgId");
	   crt.addProjection("sharedOut", "sharedOut");
	   crt.addProjection("createdBy", "createdByWhom");
	   crt.addProjection("userId", "userId");
       
       crt.addScalar("id", Long.class.getName());
       crt.addScalar("sharedOut", Boolean.class.getName());
       crt.addScalar("standardOrganizationId", Long.class.getName());
       crt.addScalar("userId", Long.class.getName());
       
       q.setResultTransformer(crt); 

       Collection<WorkAreaGridVo> vos = q.list();
       if(null != vos && vos.size()>0)
    	   return (WorkAreaGridVo)vos.iterator().next();
       else
    	   return null;
   }
   
	public Collection<WorkAreaResourceGridVo> getResourceGrid(WorkAreaResourceFilter filter) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(WorkAreaResourceViewImpl.class);
	    
	   /*
	    * Define how we are going to transform the result to the return object
	    */
	   crit.setProjection(Projections.projectionList()
             .add(Projections.property("workAreaId"),"workAreaId")
             .add(Projections.property("workAreaResourceId"),"workAreaResourceId")
             .add(Projections.property("parentResourceId"), "parentResourceId")
             .add(Projections.property("this.resourceName"),"resourceName")
             .add(Projections.property("resourceId"),"resourceId")
             .add(Projections.property("this.lastName"),"lastName")
             .add(Projections.property("firstName"),"firstName")
             .add(Projections.property("resourceAgencyCode"),"agencyCode")
             .add(Projections.property("resourceUnitCode"),"unitCode")
             .add(Projections.property("primaryDispatchCenterId"),"primaryDispatchCenterId")
             .add(Projections.property("incidentName"),"incidentName")
             .add(Projections.property("requestNumber"),"requestNumber")
             .add(Projections.property("assignmentStatus"),"assignmentStatus")
             .add(Projections.property("kindDescription"),"kindDescription")
             .add(Projections.property("enabled"), "enabled")             
             .add(Projections.property("leaderType"), "leaderType")   
             .add(Projections.property("permanentResourceId"), "permanentResourceId")
       );
      
	   crit.setResultTransformer(Transformers.aliasToBean(WorkAreaResourceGridVo.class));
	      
	   if (filter != null) {
		   try{
			   if( (null==filter.getWorkAreaId()) || (filter.getWorkAreaId().equals(0L))){
				   throw new PersistenceException("WorkAreaId cannot be empty.");
			   }
			   
			   /*
			    * Add the criteria
			    */
				Collection<FilterCriteria> filterCriteria = WorkAreaResourceFilterImpl.getFilterCriteria(filter);
				
				CriteriaBuilder.addCriteria(crit, filterCriteria);
				
				Collection<String> reqCatList = new ArrayList<String>();
				if(filter.getAircraft())reqCatList.add("A");
				if(filter.getCrews())reqCatList.add("C");
				if(filter.getEquipment())reqCatList.add("E");
				if(filter.getOverhead())reqCatList.add("O");

				if(null != reqCatList && reqCatList.size() > 0){
					// crit.add(Restrictions.in("this.resourceKindsCategories",reqCatList));

					/*
					 *	Add checkbox filter criteria - only if the particular checkbox is selected, and add 
					 *  as disjunction to ensure that when some or all checkboxes are selected, 
					 *  records are returned that match any of the selected checkboxes
					 *	(rather than only those records that meet all selected boxes)
					 */
					crit.add(Restrictions.disjunction()
							.add(filter.getAircraft() ? Expression.ilike("this.resourceKindsCategories", "A", MatchMode.ANYWHERE): Restrictions.isNull("this.resourceId"))
							.add(filter.getCrews() ? Expression.ilike("this.resourceKindsCategories", "C", MatchMode.ANYWHERE): Restrictions.isNull("this.resourceId"))
							.add(filter.getEquipment() ? Expression.ilike("this.resourceKindsCategories", "E", MatchMode.ANYWHERE): Restrictions.isNull("this.resourceId"))
							.add(filter.getOverhead() ? Expression.ilike("this.resourceKindsCategories", "O", MatchMode.ANYWHERE): Restrictions.isNull("this.resourceId"))
						);
				}				
				
				

				if(filter.isExcludeFilled()){
					crit.add(Restrictions.disjunction()
						.add(Restrictions.isNull("this.assignmentStatus"))
						.add(Restrictions.ne("this.assignmentStatus", AssignmentStatusTypeEnum.F))
					);
				}

				if(!filter.isIncludeDemob()){
					crit.add(Restrictions.disjunction()
							.add(Restrictions.isNull("this.assignmentStatus"))
							.add(Restrictions.ne("this.assignmentStatus", AssignmentStatusTypeEnum.D))
						);
				}
				
				if(filter.isPersonnel()) {
					crit.add(Restrictions.eq("this.person", true));
				}
		   }catch(Exception e){
			   throw new PersistenceException(e);
		   }
	   }
	   crit.addOrder(Order.asc("resourceName"));
	   crit.addOrder(Order.asc("lastName"));
	   return crit.list();
   }
   
   public Collection<WorkAreaResourceGridVo> getUnassignedResources(WorkAreaResourceFilter filter) throws PersistenceException {
	   
	   //String sql = WorkAreaQuery.getUnassignedResourcesQuery(filter,super.isOracleDialect());
	   String sql = WorkAreaQuery.getUnassignedResourcesQuery2(filter,super.isOracleDialect());
	   
       //SQLQuery q = getHibernateSession().createSQLQuery(WorkAreaQuery.GET_UNASSIGNED_RESOURCES_SQL_QUERY);
       SQLQuery q = getHibernateSession().createSQLQuery(sql);
       //q.setLong("waid", filter.getWorkAreaId());

	    if(CollectionUtility.hasValue(filter.getExcludedResourceIds())){
	    	Collection<Long> ids = IntegerUtility.convertToLongs(filter.getExcludedResourceIds());

	    	q.setParameterList("ids", ids);
	    }
       
       CustomResultTransformer crt = new CustomResultTransformer(WorkAreaResourceGridVo.class);
       
       crt.addScalar("workAreaId", Long.class.getName());
       crt.addScalar("workAreaResourceId", Long.class.getName());
       crt.addScalar("parentResourceId", Long.class.getName());
       crt.addScalar("resourceId", Long.class.getName());
              
       q.setResultTransformer(crt); 
       
       q.setParameter("resourceEnabled",super.getBooleanComparison(true));
       
       return q.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#getPicklist(gov.nwcg.isuite.domain.access.WorkAreaFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<WorkAreaPicklistVo> getPicklist(WorkAreaFilter filter) throws PersistenceException {
      if (filter == null || filter.getCurrentUserId() == null) {
         throw new PersistenceException("userId of WorkAreaFilter must not be null.");
      }
      Criteria crit = getHibernateSession().createCriteria(WorkAreaUserImpl.class, "wau");
      crit.createAlias("workArea", "wa");
      crit.setProjection(Projections.projectionList()
               .add( Projections.property("workAreaId"), "id")
               .add( Projections.property("wa.name"), "waName")
               .add( Projections.property("wa.auditable.createdBy"), "createdByWhom")
               );
      crit.setResultTransformer(Transformers.aliasToBean(WorkAreaPicklistVo.class));
      setWorkAreaFilterCriterion(crit, filter);

      crit.setMaxResults(super.getMaxResultSize());      
      return crit.list();
   }
   
   /**
    * Filter settings for the Work Area  
    * 
    * @param filter populated WorkAreaFilter
    * @param crit the Criteria object to set.
    */
   private void setWorkAreaFilterCriterion(Criteria crit, WorkAreaFilter filter) {
      // A userId is required.
      crit.add(Expression.eq("wau.userId", filter.getCurrentUserId()));
      if (filter.getName() != null && filter.getName().trim().length() > 0) {
         crit.add(Expression.ilike("wa.name", filter.getName(), MatchMode.START));
      }
      if (filter.getDescription() != null && filter.getDescription().trim().length() > 0) {
         crit.add(Expression.ilike("wa.description", filter.getDescription(), MatchMode.START));
      }
      //if (filter.getStandard() != null) {
      if (null != filter.getStandardAsString()) {
         String soi = "wa.standardOrganizationId";
         //Criterion ex = (filter.getStandard())
         Criterion ex = (Boolean.parseBoolean(filter.getStandardAsString())) 
            ?  Expression.isNotNull(soi) :  Expression.isNull(soi);
         crit.add(ex);
      }
      //if (filter.getSharedOut() != null) {
      if (null != filter.getSharedOutAsString()) {
         //crit.add(Expression.eq("wa.sharedOut", filter.getSharedOut()));
    	  crit.add(Expression.eq("wa.sharedOut", Boolean.parseBoolean(filter.getSharedOutAsString())));
      }
      if (filter.getCreatedBy() != null && filter.getCreatedBy().trim().length() > 0) {
         crit.add(Expression.ilike("wa.auditable.createdBy", filter.getCreatedBy(), MatchMode.START));
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#getResourcesByWorkAreaId(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<ResourceGridVo> getResourcesByWorkAreaId(Long workAreaId) throws PersistenceException {
      if (workAreaId == null) {
         throw new PersistenceException("Work Area ID required to call this method.");
      }
      
      Criteria crit = getHibernateSession().createCriteria(WorkAreaResourcesViewImpl.class);
      
      crit.createAlias("resource", "r");
      crit.setProjection(Projections.projectionList()
             .add(Projections.property("r.id"), "id")
             .add(Projections.property("r.resourceName"), "resourceName"));

      crit.setResultTransformer(Transformers.aliasToBean(ResourceGridVo.class));
      crit.add(Expression.eq("workArea.id", workAreaId));
      crit.add(Expression.isNull("r.deleted_date"));
      crit.addOrder(Order.asc("resourceName"));
      return crit.list();
      
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getWorkAreaIncidents(gov.nwcg.isuite.core.filter.IncidentFilter, java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<IncidentGridVo> getWorkAreaIncidents(IncidentFilter filter, Long userId) throws PersistenceException {
      if (filter == null || filter.getWorkAreaId() == null || filter.getWorkAreaId() == 0) {
         throw new PersistenceException("Work Area ID required to call this method.");
      }
      
      LOG.debug("Retrieving Incidents for Work Area: " + filter.getWorkAreaId());
      Criteria crit = getHibernateSession().createCriteria(WorkAreaIncidentsViewImpl.class);
      
      setWorkAreaIncidentCriteria(filter, crit);
      
      crit.add(Expression.eq("workAreaId", filter.getWorkAreaId()));
      crit.addOrder(Order.asc("this.incidentName"));
      
      if(StringUtility.hasValue(filter.getIncidentTagNumber())){
    	  crit.add(Expression.ilike("this.incidentTagNumber", filter.getIncidentTagNumber(),MatchMode.START));
      }
      
      Collection<IncidentGridVo> vos = crit.list();
      return vos;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getWorkAreaIncidentSelectorData(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(Long workAreaId) throws PersistenceException {
      if(workAreaId == null || workAreaId == 0) {
         throw new PersistenceException("Work Area ID is required.");
      }
      Criteria crit = getHibernateSession().createCriteria(WorkAreaIncidentsViewImpl.class);
      //crit.createAlias("incident", "inc");
      //crit.createAlias("inc.countrySubdivision", "csd", CriteriaSpecification.LEFT_JOIN);
      //crit.createAlias("csd.countryCode", "cc");
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("incidentId"), "incidentId")
               .add(Projections.property("incidentNbr"), "incidentNumber")
               .add(Projections.property("incidentName"), "incidentName")
               .add(Projections.property("restrictedFlg"), "isRestrictedIncident")
//               .add(Projections.property("incidentJurisdiction"), "incidentJurisdictionCode")
               //.add(Projections.property("cc.abbreviation"), "countryCode")
               .add(Projections.property("stateCode"), "countrySubdivisionCode")
               .add(Projections.property("unitCode"), "unitCode")
               );
      //Exclude Incidents that belong to an IncidentGroup.
      String sql = "this_.incident_id not in(select incident_id from isw_incident_group_incident)";
      crit.add(Expression.eq("workAreaId", workAreaId));
      crit.add(Restrictions.sqlRestriction(sql));
      crit.setResultTransformer(Transformers.aliasToBean(IncidentSelectorVo.class));
      return crit.list();
   }

   public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(IncidentFilter filter) throws PersistenceException,Exception {

	   if(!LongUtility.hasValue(filter.getWorkAreaId() ))
		   throw new PersistenceException("Work Area ID is required.");
	   
	   Criteria crit = getHibernateSession().createCriteria(WorkAreaIncidentsViewImpl.class);
	   crit.setProjection(Projections.projectionList()
			   .add(Projections.property("incidentId"), "incidentId")
			   .add(Projections.property("incidentNbr"), "incidentNumber")
			   .add(Projections.property("incidentName"), "incidentName")
			   .add(Projections.property("restrictedFlg"), "isRestrictedIncident")
			   .add(Projections.property("incidentJurisdiction"), "incidentJurisdictionCode")
			   .add(Projections.property("defaultAccountingCode"), "defaultAccountingCode")
			   .add(Projections.property("eventType"), "eventTypeName")
			   .add(Projections.property("stateCode"), "countrySubdivisionCode")
			   .add(Projections.property("unitCode"), "unitCode")
			   .add(Projections.property("startDate"), "incidentStartDate")
	   );
	   
	   Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
	   criteria.add( LongUtility.hasValue(filter.getWorkAreaId()) ? new FilterCriteria("workAreaId",filter.getWorkAreaId(),FilterCriteria.TYPE_EQUAL) : null);
	   criteria.add( StringUtility.hasValue(filter.getIncidentName()) ? new FilterCriteria("this.incidentName",filter.getIncidentName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
	   criteria.add( StringUtility.hasValue(filter.getAgency()) ? new FilterCriteria("this.incidentJurisdiction",filter.getAgency().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
	   criteria.add( StringUtility.hasValue(filter.getDefaultAccountingCode()) ? new FilterCriteria("this.defaultAccountingCode",filter.getDefaultAccountingCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
	   criteria.add( StringUtility.hasValue(filter.getEventType()) ? new FilterCriteria("this.eventType",filter.getEventType().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
	   applyCrypticDateFilter(crit, filter.getStartDateString(), "this_.START_DATE");
	   
	   CriteriaBuilder.addCriteria(crit, criteria);

	   if(StringUtility.hasValue(filter.getIncidentTagNumber())){
		   crit.add(Expression.ilike("this.incidentTagNumber", filter.getIncidentTagNumber(),MatchMode.START));
	   }
	   
	   //Exclude Incidents that belong to an IncidentGroup.
	   String sql = "this_.incident_id not in (select incident_id from isw_incident_group_incident)";
	   crit.add(Restrictions.sqlRestriction(sql));
	   
	   crit.setResultTransformer(Transformers.aliasToBean(IncidentSelectorVo.class));
	  
	   Collection<IncidentSelectorVo> vos = crit.list();
	   Collection<IncidentSelectorVo> rtnVos = new ArrayList<IncidentSelectorVo>();
	   
		if(StringUtility.hasValue(filter.getRiuType())){
			for(IncidentSelectorVo vo : vos){
				String val="";
				if(BooleanUtility.isTrue(vo.getIsRestrictedIncident()))
					val=IncidentRestrictedStatusTypeEnum.RESTRICTED_ACCESS.getDescription();
				else
					val=IncidentRestrictedStatusTypeEnum.UNRESTRICTED.getDescription();

				if(StringUtility.hasValue(val)){
					if(val.equals(filter.getRiuType())){
						rtnVos.add(vo);
					}
				}
			}
			
			return rtnVos;
		}
		
		return vos;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getWorkAreaResources(gov.nwcg.isuite.core.filter.ResourceFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<WorkAreaResourceGridVo> getWorkAreaResources(WorkAreaResourcesFilter filter) throws PersistenceException {
	   if (null == filter || filter.getWorkAreaId() == null || filter.getWorkAreaId() == 0) {
		   throw new PersistenceException("Work Area ID required to call this method.");
	   }

	   String sql="";
	   
	   try{
		   sql = WorkAreaQuery.getWorkAreaResourcesSqlQuery(filter,super.isOracleDialect());
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	   
	   SQLQuery crit = getHibernateSession().createSQLQuery(sql.toString());

	   CustomResultTransformer crt = new CustomResultTransformer(WorkAreaResourceGridVo.class);
	   crt.addProjection("resourceId", "id");
	   crt.addProjection("workAreaId", "workAreaId");
	   crt.addProjection("incidentId", "incidentId");
	   crt.addProjection("incidentName", "incidentName");
	   crt.addProjection("resourceName", "resourceName");
	   crt.addProjection("lastName", "lastName");
	   crt.addProjection("firstName", "firstName");
	   crt.addProjection("unitCode", "unitCode");
	   crt.addProjection("requestCategory", "requestCategory");
	   crt.addProjection("requestCategoryCode", "requestCategoryCode");
	   crt.addProjection("assignment", "assignment");
	   crt.addProjection("assignmentStatus", "assignmentStatusString");

	   crt.addScalar("resourceId", Long.class.getName());
	   crt.addScalar("workAreaId", Long.class.getName());
	   crt.addScalar("incidentId", Long.class.getName());

	   crit.setResultTransformer(crt); 


	   Collection<WorkAreaResourceGridVo> vos = crit.list();
	   return vos;
	   /*
	   Criteria crit = getHibernateSession().createCriteria(WorkAreaResourcesViewImpl.class, "war");
	   setWorkAreaResourceCriteria(filter, crit);
	   crit.add(Expression.eq("workAreaId", filter.getWorkAreaId()));
	   Collection<WorkAreaResourceGridVo> vos = crit.list();
	   return vos;
	    */
   }
   
   /**
    * Common filter settings for the Work Area Incident available and selected 
    * queries.
    * 
    * @param filter populated IncidentFilter
    * @param crit the Criteria object to set.
    */
   private void setWorkAreaResourceCriteria(IncidentResourceFilter filter, Criteria crit) {
      crit.setProjection(Projections.projectionList()
            .add(Projections.property("war.resourceId"), "id")
            .add(Projections.property("war.resourceName"), "resourceName")
            .add(Projections.property("war.lastName"), "lastName")
            .add(Projections.property("war.firstName"), "firstName")
            .add(Projections.property("war.unitCode"), "unitCode")
            .add(Projections.property("war.requestCategory"), "requestCategory")
            .add(Projections.property("war.incidentName"), "incidentName")
            .add(Projections.property("war.assignment"), "assignment")
            .add(Projections.property("war.assignmentStatus"), "assignmentStatus"));
   
      crit.setResultTransformer(Transformers.aliasToBean(WorkAreaResourceGridVo.class));

      if (filter.getResourceName() != null && filter.getResourceName().trim().length() > 0) {
         crit.add(Expression.ilike("war.resourceName", filter.getResourceName().trim(), MatchMode.START));
      }
      if (filter.getResourceOrganizationName() != null && filter.getResourceOrganizationName().trim().length() > 0) {
         crit.add(Expression.ilike("war.unitCode", filter.getResourceOrganizationName().trim(), MatchMode.START));
      }
      if (filter.getRequestCategoryCodes() != null && filter.getRequestCategoryCodes().size() > 0) {
         crit.add(Expression.in("war.requestCategoryCode", filter.getRequestCategoryCodes()));
      }
      if (filter.getIncidentName() != null && filter.getIncidentName().trim().length() > 0) {
         crit.add(Expression.ilike("war.incidentName", filter.getIncidentName().trim(), MatchMode.START));
      }
      if (filter.getKindDescription() != null && filter.getKindDescription().trim().length() > 0) {
         crit.add(Expression.ilike("war.assignment", filter.getKindDescription().trim(), MatchMode.START));
      }
      if (filter.getAssignmentStatusCodes() != null && filter.getAssignmentStatusCodes().size() > 0) {
         crit.add(Expression.in("war.assignmentStatus", filter.getAssignmentStatusCodes()));
      } 
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getAvailableWorkAreaIncidents(gov.nwcg.isuite.core.filter.IncidentFilter, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   public Collection<IncidentGridVo> getAvailableWorkAreaIncidents(IncidentFilter filter, Boolean privilegedUser) throws PersistenceException {
      if (filter == null || filter.getWorkAreaId() == null || filter.getWorkAreaId() == 0) {
         throw new PersistenceException("Work Area ID required to call this method.");
      }
      
      Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
      
      crit.createAlias("this.eventType", "et");
      crit.createAlias("this.countrySubdivision", "cs", CriteriaSpecification.LEFT_JOIN);
      crit.createAlias("this.homeUnit", "unit");
      crit.createAlias("this.agency", "ag");
      
      String sqlFilter = "(this_.UNIT_ID IN (select organization_id from isw_work_area_organization wo where wo.work_area_id = " + filter.getWorkAreaId() + ") or " + 
      "this_.UNIT_ID in (select organization_id from isw_organization_pdc where pdc_id in (select distinct(organization_id) from isw_work_area_organization wao where wao.work_area_id = " + filter.getWorkAreaId() + ")))";
      crit.add(Restrictions.sqlRestriction(sqlFilter));
      sqlFilter = "this_.id not in(select incident_id from isw_work_area_incident where work_area_id = " + filter.getWorkAreaId() + ")";
      crit.add(Restrictions.sqlRestriction(sqlFilter));
      
      if (StringUtility.hasValue(filter.getIncidentName())){
    	  crit.add(Restrictions.ilike("this.incidentName", filter.getIncidentName().trim().toUpperCase(), MatchMode.START));
	   }
	   if (StringUtility.hasValue(filter.getEventType())){
		   crit.add(Restrictions.ilike("et.eventType", filter.getEventType().trim().toUpperCase(), MatchMode.START));
	   }
	   if (StringUtility.hasValue(filter.getCountrySubdivision())){
		   crit.add(Restrictions.ilike("cs.abbreviation", filter.getCountrySubdivision().trim().toUpperCase(), MatchMode.START));
	   }
	   if(StringUtility.hasValue(filter.getHomeUnit())){
		   crit.add(Restrictions.ilike("unit.unitCode", filter.getHomeUnit().trim().toUpperCase(), MatchMode.START));
	   }
	   if(StringUtility.hasValue(filter.getIncidentNumber())){
		   crit.add(Restrictions.ilike("this.nbr", filter.getIncidentNumber().trim().toUpperCase(), MatchMode.START));
	   }
	   if(StringUtility.hasValue(filter.getAgency())){
		   crit.add(Restrictions.ilike("ag.agencyCode", filter.getAgency().trim().toUpperCase(), MatchMode.START));
	   }
	   if(filter.getIncidentStartDate() != null) {
			crit.add(Expression.eq("this.incidentStartDate", filter.getIncidentStartDate()));
		} else if(StringUtility.hasValue(filter.getStartDateString())) {
			try {
				super.applyCrypticDateFilter(crit, filter.getStartDateString(), "this_.incident_start_date");
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}

	   crit.addOrder(Order.asc("this.incidentName"));
	   Collection<Incident> incidents = crit.list();
	   return IncidentGridVo.getInstances(incidents, filter.getCurrentUserId(), privilegedUser);
   }
   
   /**
    * Common filter settings for the Work Area Incident available and selected 
    * queries.
    * 
    * @param filter populated IncidentFilter
    * @param crit the Criteria object to set.
    * @throws PersistenceException 
    */
   private void setWorkAreaIncidentCriteria(IncidentFilter filter, Criteria crit) throws PersistenceException {
      crit.setProjection(Projections.projectionList()
            .add(Projections.property("incidentId"), "id")
            .add(Projections.property("incidentName"), "incidentName")
            .add(Projections.property("eventType"), "eventTypeName")
            .add(Projections.property("stateCode"), "countrySubdivisionCode")
            .add(Projections.property("unitCode"), "unitCode")
            .add(Projections.property("incidentJurisdiction"), "incidentJurisdictionCode")
            .add(Projections.property("incidentNbr"), "incidentNumber")
            .add(Projections.property("startDate"), "incidentStartDt")
            .add(Projections.property("restrictedFlg"), "restricted"));
   
      crit.setResultTransformer(Transformers.aliasToBean(IncidentGridVo.class));

      if (filter.getIncidentName() != null && filter.getIncidentName().trim().length() > 0) {
         crit.add(Expression.ilike("this.incidentName", filter.getIncidentName().trim(), MatchMode.START));
      }
      if (filter.getEventType() != null && filter.getEventType().trim().length() > 0) {
         crit.add(Expression.ilike("this.eventType", filter.getEventType().trim(), MatchMode.START));
      }
      if (filter.getCountrySubdivision() != null && filter.getCountrySubdivision().trim().length() > 0) {
         crit.add(Expression.ilike("this.stateCode", filter.getCountrySubdivision().trim(), MatchMode.START));
      }
      if (filter.getHomeUnit() != null && filter.getHomeUnit().trim().length() > 0) {
         crit.add(Expression.ilike("this.unitCode", filter.getHomeUnit().trim(), MatchMode.START));
      }
      if (filter.getAgency() != null && filter.getAgency().trim().length() > 0) {
         crit.add(Expression.ilike("this.incidentJurisdiction", filter.getAgency().trim(), MatchMode.START));
      }
      if (filter.getIncidentNumber() != null && filter.getIncidentNumber().trim().length() > 0) {
         crit.add(Expression.ilike("this.incidentNbr", filter.getIncidentNumber().trim(), MatchMode.START));
      }
      if (filter.getIncidentStartDate() != null) {
         crit.add(Expression.ge("this.startDate", filter.getIncidentStartDate()));
      }
      
      if(filter.getIncidentStartDate() != null) {
			crit.add(Expression.eq("this.startDate", filter.getIncidentStartDate()));
		} else if(StringUtility.hasValue(filter.getStartDateString())) {
			try {
				super.applyCrypticDateFilter(crit, filter.getStartDateString(), "this_.start_date");
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}
      
      //TODO get the following working for restricted
//      if (null != filter.isRestricted()) {
//    	  crit.add(Expression.eq("restricted", Boolean.parseBoolean(filter.isRestricted())));
//      }
      
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(WorkArea persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public WorkArea getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, clazz);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(WorkArea persistable) throws PersistenceException {
	  if(persistable.getId()>0L)
		  super.getHibernateSession().merge(persistable);
	  else
		  crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<WorkArea> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#removeResourcesFromWorkArea(java.util.Collection, java.lang.Long)
    */
   public void removeIncidentsFromWorkArea(Collection<Long> incidentIds, Long workAreaId) throws PersistenceException {
      if (workAreaId != null && incidentIds != null && incidentIds.size() > 0) {
         String sql = "DELETE FROM ISW_WORK_AREA_INCIDENT WHERE WORK_AREA_ID = :waid AND INCIDENT_ID IN (:iids)";
         Query q = getHibernateSession().createSQLQuery(sql);
         q.setLong("waid", workAreaId);
         q.setParameterList("iids", incidentIds);
         q.executeUpdate();
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#removeResourcesFromWorkArea(java.util.Collection, java.lang.Long)
    */
   public void removeResourcesFromWorkArea(Collection<Long> resourceIds, Long workAreaId) throws PersistenceException {
	   if (workAreaId != null && resourceIds != null && resourceIds.size() > 0) {
		   String sql = "DELETE FROM ISW_WORK_AREA_RESOURCE WHERE WORK_AREA_ID = :waid AND RESOURCE_ID IN (:rids)";
	         Query q = getHibernateSession().createSQLQuery(sql);
	         q.setLong("waid", workAreaId);
	         q.setParameterList("rids", resourceIds);
	         q.executeUpdate();
	   }
   }

   /**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
   public Collection<WorkAreaResourceGridVo> getAvailableWorkAreaResources(WorkAreaResourcesFilter filter) throws PersistenceException {
	   if (filter == null || filter.getWorkAreaId() == null || filter.getWorkAreaId() == 0) {
	         throw new PersistenceException("Work Area ID required to call this method.");
       }
	   
	   String sql="";
	   
	   try{
		   sql = WorkAreaQuery.getAvailableWorkAreaResourcesSqlQuery(filter,super.isOracleDialect());
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	   
	   SQLQuery crit = getHibernateSession().createSQLQuery(sql.toString());
	   
       CustomResultTransformer crt = new CustomResultTransformer(WorkAreaResourceGridVo.class);
       crt.addProjection("resourceId", "resourceId");
       crt.addProjection("workAreaId", "workAreaId");
       crt.addProjection("resourceName", "resourceName");
       crt.addProjection("lastName", "lastName");
       crt.addProjection("firstName", "firstName");
       crt.addProjection("unitCode", "unitcode");
       crt.addProjection("requestCategory", "requestCategory");
       crt.addProjection("requestCategoryCode", "requestCategoryCode");
       crt.addProjection("incidentName", "incidentName");
       crt.addProjection("assignment", "assignment");
       crt.addProjection("assignmentStatus", "assignmentStatusString");
	      
       crt.addScalar("workAreaId", Long.class.getName());
       crt.addScalar("resourceId", Long.class.getName());
       
       crit.setResultTransformer(crt); 

       
	   /*
	   	//Criteria crit = getHibernateSession().createCriteria(AvailableWorkAreaResourcesViewImpl.class, "war");
	   	setWorkAreaResourceCriteria(filter, crit);
	   	crit.add(Expression.eq("workAreaId", filter.getWorkAreaId()));
	   	return vos;
	    */

       Collection<WorkAreaResourceGridVo> vos = crit.list();
       
	   return vos;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#isWorkAreaNameUniqueToUser(java.lang.Long, java.lang.String)
    */
   public boolean isWorkAreaNameUniqueToUser(Long waId, String waName, Long uId) throws PersistenceException {

	   String sql = WorkAreaQuery.IS_WORK_AREA_NAME_UNIQUE_TO_USER;
	   
	   if(null!=waId)
		   sql = sql + " AND id != :waid ";
	   
	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   
	   query.setParameter("name", waName.toUpperCase());
	   query.setLong("userid", uId);
	   if(null!=waId)
		   query.setParameter("waid", waId);
	   
	   try{
		   Long val = TypeConverter.convertToLong(query.uniqueResult());
		   return (val==0 ? true : false);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
      
	  /*
      String hql = "";
      if ( waName == null || waName.length() < 1 || uId == null || uId == 0 ) {
         throw new PersistenceException("waName and userid are required!");
      }
      
      hql = "select count(*) from WorkAreaImpl wa where (wa.name = :name";
      if ( !(waId == null) ) {
         hql = hql + " and not wa.id = :waId";
      }
      hql = hql + " and wa.userId = :userId) or (wa.name = :name";
      
      hql = hql + " and wa.auditable.createdBy = '" + STATIC_DATA + "')";
      
      Query query = getHibernateSession().createQuery(hql);
      query.setString("name", waName);
      if ( !(waId == null) ) {
         query.setLong("waId", waId);
      }
      query.setLong("userId", uId);
      return ((Long) query.uniqueResult()).equals(0L);
      */
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#isWorkAreaNameUniqueStandard(java.lang.Long, java.lang.String, java.lang.Long)
    */
   public boolean isWorkAreaNameUniqueStandard(String waName) throws PersistenceException {

	   String sql = WorkAreaQuery.IS_WORK_AREA_NAME_UNIQUE_STANDARD;
	   
	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   query.setParameter("name", waName.toUpperCase());
	   
	   try{
		   Long val = TypeConverter.convertToLong(query.uniqueResult());
		   return (val==0 ? true : false);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaDao#getWorkAreaPickListByUserId(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<WorkAreaPicklistVo> getWorkAreaPickListByUserId(Long userId) throws PersistenceException {
	  /*
      String hql = "select " +
      					"wa.id as id, " +
      					"wa.name as waName, " +
      					"wa.description as waDesc, " +
      					"wa.auditable.createdBy as createdByWhom " +
      				"from WorkAreaImpl wa, " +
      					 "WorkAreaUserImpl wau " +
      				"where wa.id=wau.workArea.id " +
      				"and wau.user.id=" + userId; 
      Query query = getHibernateSession().createQuery(hql);
      query.setResultTransformer(Transformers.aliasToBean(WorkAreaPicklistVo.class));
      return query.list();
      */
	      Criteria crit = getHibernateSession().createCriteria(WorkAreaUserImpl.class)
	      	.setFetchMode("workArea", FetchMode.JOIN)
	      	.setFetchMode("workArea.workAreaIncidents", FetchMode.JOIN );
	      
	      //crit.createCriteria("workArea.workAreaIncidents");
	      
	      crit.add(Expression.eq("this.userId", userId));

	      
	      List<WorkAreaUserImpl> list = crit.list();
	      
	      List<WorkAreaPicklistVo> voList = new ArrayList<WorkAreaPicklistVo>();
	      for(WorkAreaUserImpl wau : list){
	    	  WorkAreaPicklistVo vo = new WorkAreaPicklistVo();
	    	  vo.setId(wau.getWorkAreaId());
	    	  vo.setWaName(wau.getWorkArea().getName());
	    	  vo.setWaDesc(wau.getWorkArea().getDescription());
	    	  vo.setCreatedByWhom(wau.getWorkArea().getCreatedBy());
	    	  
	    	  for(Incident incident : wau.getWorkArea().getWorkAreaIncidents()){
	    		  WorkAreaIncidentVo incVo = new WorkAreaIncidentVo(incident);
	    		  vo.addWorkAreaIncidentVo(incVo);
	    	  }
	    	  
	    	  voList.add(vo);
	      }
	      
	      return voList;
   }
   
   public void addResourceToWorkArea(Long workAreaId, Long resourceId) throws PersistenceException {
	   StringBuffer sql = new StringBuffer()
	   	.append("INSERT INTO ISW_WORK_AREA_RESOURCE (WORK_AREA_ID, RESOURCE_ID)")
	   	.append(" VALUES (" + workAreaId + ", " + resourceId + ")");
	   
	   SQLQuery query = super.getHibernateSession().createSQLQuery(sql.toString());
	   
	   query.executeUpdate();
   }
   
   public Collection<Resource> getAvailableRosterResources(Long workAreaId, Long resourceId) throws PersistenceException {
	  
	   Query q = getHibernateSession().createQuery(WorkAreaQuery.GET_AVAILABLE_RESOURCES_TO_ROSTER_QUERY(super.isOracleDialect()));

	   q.setParameter("waid",workAreaId);
	   Collection<Resource> entities = q.list();
	   
	   // Do not add resource as 'available to roster' if:		 
	   // it is the parent of the primary resource, 
	   // and if it is in the parent hierarchy of the primary resource	
	   entities=removeParentGroupRosterResources(entities, resourceId);
	   	   
	   Collection<Resource> list = new ArrayList<Resource>();
	   if(null != entities) {
		   for(Resource r : entities){	
			   // add resource as 'available to roster' if it does not already have a parent
			   if(null == r.getParentResourceId()) {
				   list.add(r); 
			   }
		   }
	   }
	   return list;
   }
   
   private Collection<Resource> removeParentGroupRosterResources(Collection<Resource> entities, Long resourceId) {
		
		if(entities.isEmpty())
			return null;
		else {
			Resource[] entityArray = (Resource[])entities.toArray(new Resource[0]);			
			for (int i=0; i<entityArray.length; i++) {
				if(null != entityArray[i]){
					Resource r = (Resource)entityArray[i];
					
					// Remove resource from potential 'available to roster' list if:
						// it is the primary resource
						// and if it is in the parent hierarchy of the primary resource
					if(null != r.getId() && null != resourceId && r.getId().compareTo(resourceId)==0) {	
						Long parentResourceId = r.getParentResourceId();
						entities.remove(r);					
						entities=removeParentGroupRosterResources(entities, parentResourceId);
					}
				}
			}
			return entities;
		}
	}
	

   public Collection<OrganizationVo> getAssociatedUserOrganizations(Long userId) throws PersistenceException {
	   if (null == userId || userId == 0) {
		   throw new PersistenceException("User ID required to call this method.");
	   }

	   StringBuffer sql = new StringBuffer()
	   .append(WorkAreaQuery.GET_USER_WORK_AREA_ORGANIZATIONS_SQL_QUERY);

	   SQLQuery crit = getHibernateSession().createSQLQuery(sql.toString());
	   crit.setParameter("userid", userId);
	   
	   CustomResultTransformer crt = new CustomResultTransformer(OrganizationVo.class);
	   crt.addProjection("organizationId", "id");
	   crt.addProjection("organizationName", "name");
	   crt.addProjection("dispatchCenter", "dispatchCenter");
	   crt.addProjection("unitCode", "unitCode");

	   crt.addScalar("organizationId", Long.class.getName());
	   crt.addScalar("dispatchCenter", Boolean.class.getName());

	   crit.setResultTransformer(crt); 

	   
	   Collection<OrganizationVo> vos = crit.list();
	   return vos;
   }

   public Collection<WorkArea> getWorkAreasForUser(Long id) throws PersistenceException {
	   try {
		   String sql = WorkAreaQuery.getWorkAreaIdsForUserSQLQuery(null);

		   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   q.setLong("userid", id);

		   q.setParameter("param1", (super.isOracleDialect() ? 1 : true ) );

		   Collection<Object> list = q.list();

		   if( (null != list) && (list.size()>0)) {
			   Criteria crit = getHibernateSession().createCriteria(WorkAreaImpl.class);
			   Collection<Long> ids = new ArrayList<Long>();
			   for(Object obj : list){
				   try{
					   ids.add(TypeConverter.convertToLong(obj));
				   }catch(Exception e){
					   throw new PersistenceException(e);
				   }
			   }

			   crit.add(Restrictions.in("id", ids));

			   return crit.list();
		   }else
			   return null;

	   } catch(Exception e) {
		   throw new PersistenceException(e);
	   }
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getWorkAreasForUser(gov.nwcg.isuite.core.filter.WorkAreaFilter)
    */
   public Collection<WorkArea> getWorkAreasForUser(WorkAreaFilter filter) throws PersistenceException {
	   try {
		   String sql = WorkAreaQuery.getWorkAreaIdsForUserSQLQuery(filter);

		   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   q.setLong("userid", filter.getCurrentUserId());
		   q.setParameter("param1", (super.isOracleDialect() ? 1 : true ) );

		   // add filter clauses

		   Collection<Object> list = q.list();

		   if( (null != list) && (list.size()>0)) {
			   Criteria crit = getHibernateSession().createCriteria(WorkAreaImpl.class);
			   Collection<Long> ids = new ArrayList<Long>();
			   for(Object obj : list){
				   try{
					   ids.add(TypeConverter.convertToLong(obj));
				   }catch(Exception e){
					   throw new PersistenceException(e);
				   }
			   }

			   crit.add(Restrictions.in("id", ids));

			   return crit.list();
		   }else
			   return null;

	   } catch (Exception e) {
		   throw new PersistenceException(e);
	   }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getWorkAreaIdByStandardOrganizationId(java.lang.Long)
    */
   public Long getWorkAreaIdByStandardOrganizationId(Long standardOrganizationId) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(WorkAreaImpl.class);
      crit.setProjection(Projections.projectionList().add(Projections.property("id")));
      crit.add(Restrictions.eq("standardOrganizationId", standardOrganizationId));
      return (Long)crit.uniqueResult();
   }
   
   public Long getResourceIdInWorkArea(Long workAreaId, Long resourceId) throws PersistenceException {
       SQLQuery q = getHibernateSession().createSQLQuery(WorkAreaQuery.GET_RESOURCE_ID_IN_WORK_AREA);
       q.setLong("workareaid", workAreaId);
       q.setLong("resourceid", resourceId);

       Object obj= (Object)q.uniqueResult();

       try{
	       if(null != obj){
	    	   Long id = TypeConverter.convertToLong(obj);
	    	   return id;
	       }else{
	    	   return 0L;
	       }
       }catch(Exception e){
    	   throw new PersistenceException(e);
       }
       
   }

   public Long getPermanentResourceIdInWorkArea(Long workAreaId, Long resourceId) throws PersistenceException {
       SQLQuery q = getHibernateSession().createSQLQuery(WorkAreaQuery.GET_PERMANENT_RESOURCE_ID_IN_WORK_AREA);
       q.setLong("workareaid", workAreaId);
       q.setLong("resourceid", resourceId);

       Object obj= (Object)q.uniqueResult();
       
       try{
	       if(null != obj){
	    	   Long id = TypeConverter.convertToLong(obj);
	    	   return id;
	       }else{
	    	   return 0L;
	       }
       }catch(Exception e){
    	   throw new PersistenceException(e);
       }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#getByStandardOrganizationId(java.lang.Long)
    */
   @Override
   public WorkArea getByStandardOrganizationId(Long standardOrganizationId) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(WorkAreaImpl.class);
	   crit.add(Restrictions.eq("standardOrganizationId", standardOrganizationId));
	   crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	   return (WorkArea)crit.uniqueResult();
   }

   public void updateRosterChildrenLeaderType(Long parentResourceId, Long excludeChildId, Integer fromType, Integer toType) throws PersistenceException {
	   if(null != parentResourceId && parentResourceId > 0){
		   String sql = "UPDATE ISW_RESOURCE SET LEADER_TYPE = " + toType + 
		   				", IS_LEADER = " + (super.isOracleDialect() ? 0 : false ) + 
	       				" WHERE PARENT_RESOURCE_ID = " + parentResourceId  + " " +
	       				"AND ID != " + excludeChildId + " " +
	       				"AND LEADER_TYPE = " + fromType + " ";
	       Query q = getHibernateSession().createSQLQuery(sql);
	       q.executeUpdate();
	   }
   }
   
   public void syncUserRestrictedWorkArea(Long userId) throws PersistenceException {
	   
	   // Sync 1
	   String sqlCount="select count(*) from isw_work_area where "+
	   "id in " +
	   " (   select id from isw_work_area where user_id = " + userId + " and name = 'MY RESTRICTED WORK AREA' ) " ;

	   SQLQuery sqlCountQuery = getHibernateSession().createSQLQuery(sqlCount);

	   Object rslt = sqlCountQuery.uniqueResult();
	   int cnt = 0;
	   try{
		   cnt = TypeConverter.convertToInteger(rslt).intValue();
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }

	   if(cnt < 1){
		   // remove first sql after next build - 04/26/2011
		   String removeSql = "";
		   removeSql = "insert into isw_work_area (id, name, description, user_id,shared_out_flg, created_by, last_modified_by ) " +
		   "select " + (super.isOracleDialect() ? "seq_work_area.nextVal" : "nextVal('SEQ_WORK_AREA')") + ", 'MY RESTRICTED WORK AREA', 'MY RESTRICTED WORK AREA', id, " + (super.isOracleDialect() ? 0 : false ) + ", login_name, login_name from isw_user " +
		   "where id = " + userId + " " +
		   "and id not in ( " +
		   " select user_id from isw_work_area where name = 'MY RESTRICTED WORK AREA' " +
		   ") ";
		   SQLQuery removeQuery = getHibernateSession().createSQLQuery(removeSql);
		   removeQuery.executeUpdate();
	   }


	   // Sync 2
	   sqlCount = "select count(*) FROM ISW_RESTRICTED_INCIDENT_USER " + 
	   "WHERE USER_ID = " + userId + " " +
	   "AND  INCIDENT_ID NOT IN " +
	   "( " + 
	   "    select incident_id from isw_work_area_incident where work_area_id =  " +
	   "        (   select id from isw_work_area where user_id = " + userId + " and name = 'MY RESTRICTED WORK AREA' ) " +
	   ") ";
	   
	   sqlCountQuery = getHibernateSession().createSQLQuery(sqlCount);

	   rslt = sqlCountQuery.uniqueResult();
	   cnt = 0;
	   try{
		   cnt = TypeConverter.convertToInteger(rslt).intValue();
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }

	   if(cnt > 0) {
		   // create any new associations
		   String sql = "INSERT INTO ISW_WORK_AREA_INCIDENT (WORK_AREA_ID, INCIDENT_ID) " +
		   "select (select id from isw_work_area where user_id = " + userId + " and name = 'MY RESTRICTED WORK AREA') , INCIDENT_ID FROM ISW_RESTRICTED_INCIDENT_USER " + 
		   "WHERE USER_ID = " + userId + " " +
		   "AND  INCIDENT_ID NOT IN " +
		   "( " + 
		   "    select incident_id from isw_work_area_incident where work_area_id =  " +
		   "        (   select id from isw_work_area where user_id = " + userId + " and name = 'MY RESTRICTED WORK AREA' ) " +
		   ") ";

		   SQLQuery query = getHibernateSession().createSQLQuery(sql);
		   query.executeUpdate();
	   }

	   
	   // Sync 3
	   
	   // remove any invalid assocations
	   String sql = "DELETE FROM ISW_WORK_AREA_INCIDENT " +
	   "WHERE WORK_AREA_ID = (select id from isw_work_area where user_id = "+userId+" and name = 'MY RESTRICTED WORK AREA') " + 
	   "AND INCIDENT_ID NOT IN ( " + 
	   "    SELECT INCIDENT_ID FROM ISW_RESTRICTED_INCIDENT_USER  " + 
	   "    where user_id =  " + userId + ") " +
	   " ";

	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   query.executeUpdate();

   }
   
   public void updateSharedOutFlag() throws PersistenceException {
	   SQLQuery q = getHibernateSession().createSQLQuery(WorkAreaQuery.getUpdateSharedOutFlagQuery(super.isOracleDialect()));
	   q.executeUpdate();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#removeWorkAreaOrgsNotInUserOrgs(java.lang.Long)
    */
   public void removeWorkAreaOrgsNotInUserOrgs(Long userId) throws PersistenceException {
	   if (userId != null && userId > 0) {
		   String sql = WorkAreaQuery.deleteWorkAreaOrgsNotInUserOrgs(super.isOracleDialect());
	       Query q = getHibernateSession().createSQLQuery(sql);
	       q.setLong("id", userId);
	       q.executeUpdate();
	   }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#removeWorkAreaIncidentsNotInUserOrgs(java.lang.Long)
    */
   public void removeWorkAreaIncidentsNotInUserOrgs(Long userId) throws PersistenceException {
	   if (userId != null && userId > 0) {
		   String sql = WorkAreaQuery.deleteWorkAreaIncidentsNotInUserOrgs();
	       Query q = getHibernateSession().createSQLQuery(sql);
	       q.setLong("id", userId);
	       q.executeUpdate();
	   }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaDao#removeWorkAreaResourcesNotInUserOrgs(java.lang.Long)
    */
   public void removeWorkAreaResourcesNotInUserOrgs(Long userId) throws PersistenceException {
	   if (userId != null && userId > 0) {
		   String sql = WorkAreaQuery.deleteWorkAreaResourcesNotInUserOrgs();
	       Query q = getHibernateSession().createSQLQuery(sql);
	       q.setLong("id", userId);
	       q.executeUpdate();
	   }
   }

   public void removeWorkAreaIncidentAssociation(Long pdcId, Long incidentId) throws PersistenceException {
	   Query q = getHibernateSession().createSQLQuery(WorkAreaQuery.getRemoveWorkAreaIncidentAssociationQuery(pdcId, incidentId));
	   
	   q.executeUpdate();
   }

   public void addWorkAreaIncidentAssociation(Long pdcId, Long incidentId) throws PersistenceException {
	   Query q = getHibernateSession().createSQLQuery(WorkAreaQuery.getAddWorkAreaIncidentAssociationQuery(pdcId, incidentId));

	   try{
		   q.executeUpdate();
	   }catch(Exception e){
		   	//smother 
	   }
   }

}
