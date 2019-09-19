package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.SQLUtility;
import gov.nwcg.isuite.framework.util.StringUtility;


public class WorkAreaQuery {

	public static final String GET_AVAILABLE_RESOURCES_TO_ROSTER="GET_AVAILABLE_RESOURCES_TO_ROSTER";
	public static final String GET_AVAILABLE_RESOURCES_TO_ROSTER_QUERY(Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT r FROM WorkAreaResourceImpl war JOIN war.resource r WHERE war.workArea.id = :waid")
		 .append(" AND r.enabled = " + (isOracle ? 1 : Boolean.TRUE))
		 .append(" AND r.deletedDate is NULL "); 	
		
		return sql.toString();
	}

		

	public static final String GET_UNASSIGNED_RESOURCES_SQL="GET_UNASSIGNED_RESOURCES_SQL";
	public static final String GET_UNASSIGNED_RESOURCES_SQL_QUERY = 
		"select wa.id as workAreaId ,war.resource_id as workAreaResourceId ,r.parent_resource_id as parentResourceId ,r.resource_name as resourceName ,r.id as resourceId ,r.last_name as lastName ,r.first_name as firstName ,AG.AGENCY_CODE as agencyCode ,ORG.UNIT_CODE as unitCode  " +
		",(select k.description from iswl_kind where k.id = ( select kind_id from isw_resource_kind rk where rk.resource_id = r.id and rk.primary )) as kindDescription " +
		"from isw_work_area wa , " +
		"isw_work_area_resource war , " +
		"isw_resource r      " +
		"    left join isw_organization org on R.ORGANIZATION_ID = org.id  " +   
		"    left join iswl_agency ag on R.AGENCY_ID=ag.id  " + 
		"where war.work_area_id = wa.id  " +
		"and r.id = war.resource_id  " +
		"and r.id not in ( " +
		"        select resource_id from isw_incident_resource " +
		"            where incident_id in ( " +
		"                select incident_id from isw_work_area_incident " +
		"                where work_area_id = wa.id " +
		"            ) " +
		"    ) " +
		"and r.id not in ( " +
		"    select r1.permanent_resource_id from isw_resource r1 where r1.permanent_resource_id is not null and r1.id in ( " +
		"        select resource_id from isw_incident_resource " +
		"			where incident_id in (              " +    
		"            	select incident_id from isw_work_area_incident " +
		"				where work_area_id = wa.id " +
		"            ) " +    
		"     ) " + 
		")  " +
		"and R.PERMANENT_RESOURCE_ID not in  (    " +
		"    select r2.id from isw_resource r2   where R2.PERMANENT_RESOURCE_ID=r.permanent_resource_id    " +
		"    and r2.id in ( " +
		"             select ir2.resource_id from isw_incident_resource ir2  " +
		"             where ir2.resource_id = r2.id   " +       
		"             and ir2.id in (  " +
		"                       select wp.incident_resource_id from isw_work_period wp  " +
		"                       where  wp.incident_resource_id = ir2.id and wp.id in (  " +
		"                            select wpa.work_period_id from isw_work_period_assignment wpa where wpa.work_period_id = wp.id  " +
		"                            and wpa.assignment_id in (  " +
		"                                select a.id from isw_assignment a where a.id = wpa.assignment_id " +
		"                                and A.ASSIGNMENT_STATUS in ('C','F','P' )                 )               )         )   ) ) ";
		
	public static final String GET_UNASSIGNED_RESOURCES_SQL_QUERY2=
		"select wa.id as workAreaId " +
        ",war.resource_id as workAreaResourceId " +
        ",r.parent_resource_id as parentResourceId " +
        ",r.resource_name as resourceName " +
        ",r.id as resourceId " +
        ",r.last_name as lastName " +
        ",r.first_name as firstName " +
        ",AG.AGENCY_CODE as agencyCode " +
        ",ORG.UNIT_CODE as unitCode " +
        "from isw_work_area wa " +
        ",isw_work_area_resource war " +
        ",isw_resource r  " +
        "   left join isw_organization org on R.ORGANIZATION_ID = org.id " +
        "   left join iswl_agency ag on R.AGENCY_ID=ag.id " +
        "where " +
        "war.work_area_id = wa.id " +
		"and r.id = war.resource_id " +
        "and r.id not in ( " +
        "        select resource_id from isw_incident_resource " +
        "            where incident_id in ( " +
        "                select incident_id from isw_work_area_incident " +
        "                where work_area_id = wa.id " +
        "            ) " +
        "    ) " +
		"and R.PERMANENT_RESOURCE_ID not in  " +
		"( " +
		"  select r2.id from isw_resource r2 " +
		"  where R2.PERMANENT_RESOURCE_ID=r.permanent_resource_id " +
		"  and r2.id in ( " +
        "		select ir2.resource_id from isw_incident_resource ir2 " +
		" 		where ir2.resource_id = r2.id " +
		"		and ir2.id in  " +
        "		( " +
        "			select wp.incident_resource_id from isw_work_period wp " +
        "			where  wp.incident_resource_id = ir2.id " +
        "			and wp.id in  " +
        "			( " +
        "       		select wpa.work_period_id from isw_work_period_assignment wpa " +
        "				where wpa.work_period_id = wp.id " +
        "				and wpa.assignment_id in  " +
		"				( " +
		"					select a.id from isw_assignment a " +
		"					where a.id = wpa.assignment_id " +
		"					and A.ASSIGNMENT_STATUS in ('C','F','P' ) " +
		"				)  " +
		"			 ) " +
        "		) " +
		"  ) " +
		")";		
	

	public static final String GET_WORK_AREA_RESOURCES_SQL_QUERY(Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct( R.ID ) AS resourceId, ")
        .append("WA.ID as workAreaId, ")
        .append("case ")
        .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName, ")
        .append("UNIT.UNIT_CODE as unitCode, ") 
        .append("RC.DESCRIPTION AS requestCategory, ")
        .append("RC.CODE AS requestCategoryCode, ") 
        .append("I.INCIDENT_NAME as incidentName, ") 
        .append("K.DESCRIPTION AS assignment,  ")
        .append("A.ASSIGNMENT_STATUS as assignmentStatus ")
		.append("FROM ISW_RESOURCE R ")
		.append("LEFT JOIN ISW_INCIDENT_RESOURCE IR ON R.ID = IR.RESOURCE_ID ")
		.append("LEFT JOIN ISW_INCIDENT I ON I.ID = IR.INCIDENT_ID ")
		.append("LEFT JOIN ISW_ORGANIZATION UNIT ON R.ORGANIZATION_ID = UNIT.ID ")
		.append("LEFT JOIN ISW_WORK_PERIOD WP ON WP.INCIDENT_RESOURCE_ID = IR.ID ")
		.append("LEFT JOIN ISW_WORK_PERIOD_ASSIGNMENT WPA ON WPA.WORK_PERIOD_ID = WP.ID ")
		.append("LEFT JOIN ISW_ASSIGNMENT A ON A.ID = WPA.ASSIGNMENT_ID ")
		.append("LEFT JOIN ISWL_KIND K ON K.ID = A.KIND_ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RC ON RC.ID = K.REQUEST_CATEGORY_ID, ")
		.append("ISW_WORK_AREA WA ")
		.append("WHERE R.PERMANENT_RESOURCE_ID IS NOT NULL ")
		.append("AND R.DELETED_DATE IS NULL ")
		.append("AND R.IS_ENABLED = " + (isOracle ? 1 : Boolean.TRUE))
		.append(" AND R.ID IN ( ")
		.append("   SELECT war2.resource_id from isw_work_area_resource war2 ")
		.append("   where war2.work_area_id = wa.id ")
		.append(") " );
		
		return sql.toString();
	}
		
	
	/*
	 * Returns resources that are available in the custom work area,
	 * based on resources that are associated to the work area's
	 * filtered organizations
	 */
	public static final String GET_AVAILABLE_WORK_AREA_RESOURCES_SQL_QUERY(Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT distinct( R.ID ) AS resourceId, ")  
        .append("WA.ID as workAreaId, ")
        .append("case ")
        .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName, ")
        .append("UNIT.UNIT_CODE as unitCode, ") 
        .append("RC.DESCRIPTION AS requestCategory,  ")
        .append("RC.CODE AS requestCategoryCode, ") 
        .append("I.INCIDENT_NAME as incidentName, ")
        .append("K.DESCRIPTION AS assignment,  ")
        .append("A.ASSIGNMENT_STATUS as assignmentStatus")
		.append(" FROM ISW_RESOURCE R ")
		.append("LEFT JOIN ISW_INCIDENT_RESOURCE IR ON R.ID = IR.RESOURCE_ID ")
		.append("LEFT JOIN ISW_INCIDENT I ON I.ID = IR.INCIDENT_ID ")
		.append("LEFT JOIN ISW_ORGANIZATION UNIT ON R.ORGANIZATION_ID = UNIT.ID ")
		.append("LEFT JOIN ISW_WORK_PERIOD WP ON WP.INCIDENT_RESOURCE_ID = IR.ID ")
		.append("LEFT JOIN ISW_WORK_PERIOD_ASSIGNMENT WPA ON WPA.WORK_PERIOD_ID = WP.ID ")
		.append("LEFT JOIN ISW_ASSIGNMENT A ON A.ID = WPA.ASSIGNMENT_ID ")
		.append("LEFT JOIN ISWL_KIND K ON K.ID = A.KIND_ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RC ON RC.ID = K.REQUEST_CATEGORY_ID, ")
		.append("ISW_WORK_AREA WA ")
		.append("WHERE R.PERMANENT_RESOURCE_ID IS NULL ")
		.append("AND R.ID NOT IN ( ")
		.append("   SELECT r2.permanent_resource_id from isw_resource r2 ")
		.append("   where r2.id in (select resource_id from isw_work_area_resource war2 where war2.work_area_id = wa.id) ")
		.append(") ")
	    .append("and R.ORGANIZATION_ID in ( ")
        .append("select distinct(organization_id) from isw_work_area_organization wao where wao.work_area_id = wa.id) ")
        
		.append("AND R.ID NOT IN ")
		.append(" (SELECT R3.PERMANENT_RESOURCE_ID ")
		.append(" FROM ISW_RESOURCE R3 ")
		.append(" WHERE R3.PERMANENT_RESOURCE_ID = R.ID AND R3.IS_ENABLED = " + (isOracle ? 0 : false) + " ")
		.append(" AND R3.DELETED_DATE IS NULL) ")
		.append(" AND R.ID NOT IN (SELECT IR.RESOURCE_ID FROM ISW_INCIDENT_RESOURCE IR) ");
		
		return sql.toString();
	}
		
	/* [all resources where orgid matches an organization in the users dispatch center]
    and R.ORGANIZATION_ID in (  
            select distinct(org2.id) from isw_organization org2   
            where org2.id in (select uo.organization_id from isw_user_organization uo where  uo.user_id = u.id)   
            union    
            select distinct(org3.id) from isw_organization org3    
            where org3.managing_organization_id in (select managing_organization_id from isw_organization where id in (select organization_id from isw_user_organization where user_id = u.id ) )   
    )
	*/
	/*
	 * Returns incidents that are available in the custom work area,
	 * based on incidents that are associated to the work area's
	 * filtered organizations
	 */
	public static final String GET_AVAILABLE_WORK_AREA_INCIDENTS_SQL_QUERY=
		"select " + 
	    "distinct (I.ID) AS incidentId, " + 
	    "I.INCIDENT_NAME AS incidentName," +
	    "ET.EVENT_TYPE AS eventType, " + 
	    "CS.CS_ABBREVIATION AS stateCode, " + 
	    "UNIT.UNIT_CODE AS unitCode, " + 
	    "I.NBR AS incidentNumber, " + 
	    "AG.AGENCY_CODE AS agencyCode, " + 
	    "I.INCIDENT_START_DATE AS startDate, " + 
	    "I.RESTRICTED_FLG AS restricted " +  
	    "FROM ISW_INCIDENT I, " + 
	    " ISWL_COUNTRY_SUBDIVISION CS, " + 
	    " ISWL_EVENT_TYPE ET, " + 
	    " ISW_ORGANIZATION UNIT, " + 
	    " ISWL_AGENCY AG,	" + 
	    " ISW_WORK_AREA WA , " +
	    " ISW_USER U " +
	    " WHERE I.UNIT_ID = UNIT.ID " + 
	    " AND I.EVENT_TYPE_ID = ET.ID " + 
	    " AND I.AGENCY_ID = AG.ID " + 
	    " AND I.COUNTRY_SUBDIVISION_ID = CS.ID " + 
	    " AND i.id not in (select incident_id from isw_work_area_incident where work_area_id = wa.id) " +
        " and I.UNIT_ID in ( " +  
        "select distinct(organization_id) from isw_work_area_organization wao where wao.work_area_id = wa.id " +
        " ) ";
		/*
        " and I.UNIT_ID in ( " +  
        "        select distinct(org3.id) from isw_organization org3   " +
        "        where org3.managing_organization_id  = wa.standard_organization_id   " +
        "        union " +
        "        ( " +
        "        select distinct(org2.id) from isw_organization org2  " +
        "        where org2.id in (select uo.organization_id from isw_user_organization uo where  uo.user_id = u.id)  " +
        "        union " +  
        "        select distinct(org3.id) from isw_organization org3 " +  
        "        where org3.managing_organization_id in (select managing_organization_id from isw_organization where id in (select organization_id from isw_user_organization where user_id = u.id ) ) " + 
        "        ) " +
        " ) ";
		 */
	
	public static final String GET_WORK_AREA_GRID_SQL_QUERY=""+
	"select wa.id as id " +
    ", WA.NAME as name " +
    ",wa.description as description " +
    ",WA.STANDARD_ORGANIZATION_ID as standardOrganizationId " +
    ",WA.SHARED_OUT_FLG as sharedOut " + 
    ",wa.created_by as createdBy " +
    ",wa.user_id as userId from isw_work_area wa, isw_user u " +
    "where wa.id in ( " +
        "select distinct(wa2.id) from isw_work_area wa2 where wa2.user_id = u.id or wa2.id in (select work_area_id from isw_work_area_user where user_id = u.id ) " +
        "union " +
        "select distinct(wa3.id) from isw_work_area wa3 where wa3.standard_organization_id in  ( " +
                "select org.id from isw_organization org, isw_user u2  " +
                "where org.id in ( " +
                        "select distinct(org2.managing_organization_id) from isw_organization org2 " +
                        "where org2.id in ( " +
                            "select uo.organization_id from isw_user_organization uo where  uo.user_id = u2.id " +
                        ") " +
                   ") and u2.id = :userid " +
            ") " +
      ") " +
      "and u.id = :userid ";

	public static String getWorkAreaIdsForUserSQLQuery(WorkAreaFilter filter) throws Exception {
	
		StringBuffer sql = new StringBuffer();
		sql.append("select wa.id as id ")
		.append("from isw_work_area wa, isw_user u ")
		.append("where wa.id in ( " )
		.append("    select distinct(wa2.id) from isw_work_area wa2 where wa2.user_id = u.id or wa2.id in ")
		.append("    	( ")
		.append("  		 select wau1.work_area_id from isw_work_area_user wau1 where wau1.user_id = u.id ")
		.append("  		 and ")
		.append("    		(wau1.USER_TYPE != 'OWNER' ")
		.append("    			or ")
		.append("    		(wau1.user_type = 'SHARED' and (  (select u2.enabled from isw_user u2 where u2.id = ( select user_id from isw_work_area_user wau2 where wau2.work_area_id = wau1.work_area_id and wau2.user_type  = 'OWNER') ) = :param1     ) )) ")
		.append("    	) ")
		.append("    union ")
		.append("    select distinct(wa3.id) from isw_work_area wa3 where wa3.standard_organization_id in  ( ")
		.append("            select org.id from isw_organization org, isw_user u2  ")
		.append("            where org.id in ( ")
		.append("                    select distinct(org2.managing_organization_id) from isw_organization org2 ")
		.append("                    where org2.id in ( ")
		.append("                        select uo.organization_id from isw_user_organization uo where  uo.user_id = u2.id ")
		.append("                    ) ")
		.append("               ) and u2.id = :userid ")
		.append("        ) ")
		.append("  ) ")
		.append("  and u.id = :userid ");
	
		if(null != filter) {
			if(StringUtility.hasValue(filter.getCode())) {
				//TODO:  Add filter criteria.
			}
			if(StringUtility.hasValue(filter.getName())) {
				sql.append("and wa.name like '" + filter.getName().trim().toUpperCase() + "%'");
			}
			if(StringUtility.hasValue(filter.getDescription())) {
				sql.append("and wa.description like '" + filter.getDescription().trim().toUpperCase() + "%'");
			}
			if(StringUtility.hasValue(filter.getCreatedBy())) {
				sql.append("and wa.created_by like '" + filter.getCreatedBy().trim().toUpperCase() + "%'");
			}
		}
		
		return sql.toString();
	}
	
	   public static final String GET_USER_WORK_AREA_ORGANIZATIONS_SQL_QUERY =
		   "select distinct(org.id) as organizationId " +
	       ", ORG.ORGANIZATION_NAME as organizationName " +
	       ",ORG.IS_DISPATCH_CENTER as dispatchCenter " +
	       ",ORG.UNIT_CODE as unitCode " +
		   "from isw_organization org, isw_user u " +
		   "where org.id in ( " +
		                               "select distinct(org2.id) from isw_organization org2 " + 
		                               "where org2.id in (select uo.organization_id from isw_user_organization uo where  uo.user_id = :userid) " + 
		                               "union  " +
		                               "select distinct(org3.id) from isw_organization org3  " +
                                       "where org3.id in (select organization_id from isw_organization_pdc where pdc_id in (select organization_id from isw_user_organization where user_id = :userid ) ) " +
		   					") " +
		   "and u.id = :userid " +
		   "order by org.unit_code asc ";

	   
	   public static final String IS_WORK_AREA_NAME_UNIQUE_TO_USER = 
		   "SELECT count(*) FROM ISW_WORK_AREA " +
		   "WHERE name = :name AND user_id = :userid " +
		   " ";
	
	   public static final String IS_WORK_AREA_NAME_UNIQUE_STANDARD = 
		   "SELECT count(*) FROM ISW_WORK_AREA " +
		   "WHERE name = :name AND standard_organization_id is not null " +
		   " ";

	   public static final String GET_RESOURCE_ID_IN_WORK_AREA = 
		   "SELECT RESOURCE_ID as resourceId FROM ISW_WORK_AREA_RESOURCE " +
		   "where resource_id = :resourceid " +
		   "and work_area_id = :workareaid ";
	   
	   public static final String GET_PERMANENT_RESOURCE_ID_IN_WORK_AREA = 
		   "SELECT distinct(RESOURCE_ID) as resourceId FROM ISW_WORK_AREA_RESOURCE " +
		   "where resource_id in (select id from isw_resource where permanent_resource_id = :resourceid ) " +
		   "and work_area_id = :workareaid ";
		   
	   public static String getWorkAreaResourcesSqlQuery(WorkAreaResourcesFilter filter,Boolean isOracle) throws Exception {
		   StringBuffer sql = new StringBuffer()
		   	.append(WorkAreaQuery.GET_WORK_AREA_RESOURCES_SQL_QUERY(isOracle) +
				   " AND wa.ID = " + filter.getWorkAreaId() + " " );
		   
		   
		   if(StringUtility.hasValue(filter.getResourceName())){
			  sql.append("AND (")
			      .append("R.last_name LIKE '" + filter.getResourceName().trim().toUpperCase() + "%' ")
			      .append("OR ")
			      .append("R.resource_name LIKE '" + filter.getResourceName().trim().toUpperCase() + "%' ")
			      .append(") ");
		   }
		   
		   if(StringUtility.hasValue(filter.getUnitCode())){
			   sql.append("AND UNIT.UNIT_CODE LIKE '" + filter.getUnitCode().trim().toUpperCase() + "%' ");
		   }

		   if(StringUtility.hasValue(filter.getIncidentName())){
			   sql.append("AND I.INCIDENT_NAME LIKE '" + filter.getIncidentName().trim().toUpperCase() + "%' ");
		   }
		   
		   if(StringUtility.hasValue(filter.getAssignment())){
			   sql.append("AND K.CODE LIKE '" + filter.getAssignment().trim().toUpperCase() + "%' ");
		   }
		   
		   if(CollectionUtility.hasValue(filter.getRequestCategoryVos())){
			   sql.append("AND RC.DESCRIPTION in (" + 
					   SQLUtility.toSqlStringList(filter.getRequestCategoryVos(),RequestCategoryVo.class,"description") + 
					   ") ");
		   }
		   
		   if(CollectionUtility.hasValue(filter.getAssignmentStatusVos())){
			   sql.append("AND A.ASSIGNMENT_STATUS in (" + 
					   SQLUtility.toSqlStringList(filter.getAssignmentStatusVos(),AssignmentStatusVo.class,"description") + 
					   ") ");
		   }
		   sql.append(" ORDER BY resourceName ");
		   
		   return sql.toString();
	   }
	   
	   public static String getAvailableWorkAreaResourcesSqlQuery(WorkAreaResourcesFilter filter,Boolean isOracle) throws Exception {
		   StringBuffer sql = new StringBuffer();
		   	sql.append(WorkAreaQuery.GET_AVAILABLE_WORK_AREA_RESOURCES_SQL_QUERY(isOracle)); 
		   
		   if (StringUtility.hasValue(filter.getResourceName())){
			   sql.append("AND ( ")
			   	  .append("      (R.RESOURCE_NAME LIKE '" + filter.getResourceName().toUpperCase().trim() + "%' )")
			      .append("       OR ")
			   	  .append("      (R.LAST_NAME LIKE '" + filter.getResourceName().toUpperCase().trim() + "%' )")
			      .append("    ) ");
			   
		   }
		   if (StringUtility.hasValue(filter.getUnitCode())){
			   sql.append("AND UNIT.UNIT_CODE LIKE '" + filter.getUnitCode().trim() + "%' ");
		   }
		   if (StringUtility.hasValue(filter.getIncidentName())){
			   sql.append("AND I.INCIDENT_NAME LIKE '" + filter.getIncidentName().trim() + "%' ");
		   }
		   if(CollectionUtility.hasValue(filter.getRequestCategoryVos())){
			   sql.append("AND RC.DESCRIPTION in (" + 
					   SQLUtility.toSqlStringList(filter.getRequestCategoryVos(),RequestCategoryVo.class,"description") + 
					   ") ");
		   }
		   if(CollectionUtility.hasValue(filter.getAssignmentStatusVos())){
			   sql.append("AND A.ASSIGNMENT_STATUS in (" + 
					   SQLUtility.toSqlStringList(filter.getAssignmentStatusVos(),AssignmentStatusVo.class,"description") + 
					   ") ");
		   }
		   
		   sql.append("AND WA.ID = " + filter.getWorkAreaId() + " " )
		   .append(" ORDER BY resourceName ");
		   
		   return sql.toString();
	   }

	   public static String getAvailableWorkAreaIncidentsSqlQuery(IncidentFilter filter,Boolean isOracle) throws Exception {
		   StringBuffer sql = new StringBuffer()
		   	.append(WorkAreaQuery.GET_AVAILABLE_WORK_AREA_INCIDENTS_SQL_QUERY + " ");

		   if (StringUtility.hasValue(filter.getIncidentName())){
			   sql.append("AND I.INCIDENT_NAME LIKE '" + filter.getIncidentName().toUpperCase().trim() + "%' ");
		   }
		   if (StringUtility.hasValue(filter.getEventType())){
			   sql.append("AND ET.EVENT_TYPE_CODE LIKE '" + filter.getEventType().toUpperCase().trim() + "%' ");
		   }
		   if (StringUtility.hasValue(filter.getCountrySubdivision())){
			   sql.append("AND CS.CS_ABBREVIATION LIKE '" + filter.getCountrySubdivision().toUpperCase().trim() + "%' ");
		   }
		   if(StringUtility.hasValue(filter.getHomeUnit())){
			   sql.append("AND UNIT.UNIT_CODE LIKE '" + filter.getHomeUnit().toUpperCase().trim() + "%' ");
		   }
		   if(StringUtility.hasValue(filter.getIncidentNumber())){
			   sql.append("AND I.NBR LIKE '" + filter.getIncidentNumber().toUpperCase().trim() + "%' ");
		   }
		   if(StringUtility.hasValue(filter.getAgency())){
			   sql.append("AND AG.AGENCY_CODE LIKE '" + filter.getAgency().toUpperCase().trim() + "%' ");
		   }
		   if(null != filter.getIncidentStartDate()){
			   //sql.append("AND I.INCIDENT_START_DATE >= " + filter.getIncidentStartDate() + " ");
		   }
		   
		   sql.append("AND WA.ID = " + filter.getWorkAreaId() + " " );
		   sql.append("AND U.ID = " + filter.getCurrentUserId() + " " );
		   
		   return sql.toString();
	   }

	   public static String getUnassignedResourcesQuery(WorkAreaResourceFilter filter,Boolean isOracle) {
		   StringBuffer sql = new StringBuffer()
		   	.append(
			"select wa.id as workAreaId ,war.resource_id as workAreaResourceId ,r.parent_resource_id as parentResourceId ,r.resource_name as resourceName ,r.id as resourceId ,r.last_name as lastName ,r.first_name as firstName ,AG.AGENCY_CODE as agencyCode ,ORG.UNIT_CODE as unitCode  " +
			",(select k.description from iswl_kind k where k.id = ( select kind_id from isw_resource_kind rk where rk.resource_id = r.id and rk.is_primary = " + (isOracle ? 1 : true) + ")) as kindDescription " +
			"from isw_work_area wa , " +
			"isw_work_area_resource war , " +
			"isw_resource r      " +
			"    left join isw_organization org on R.ORGANIZATION_ID = org.id  " +   
			"    left join iswl_agency ag on R.AGENCY_ID=ag.id  " + 
			"where war.work_area_id = wa.id  " +
			"and r.id = war.resource_id  " +
			"and r.id not in ( " +
			"        select resource_id from isw_incident_resource " +
			"            where incident_id in ( " +
			"                select incident_id from isw_work_area_incident " +
			"                where work_area_id = wa.id " +
			"            ) " +
			"    ) " +
			"and r.id not in ( " +
			"    select r1.permanent_resource_id from isw_resource r1 where r1.permanent_resource_id is not null and r1.id in ( " +
			"        select resource_id from isw_incident_resource " +
			"			where incident_id in (              " +    
			"            	select incident_id from isw_work_area_incident " +
			"				where work_area_id = wa.id " +
			"            ) " +    
			"     ) " + 
			")  " +
			"and R.PERMANENT_RESOURCE_ID not in  (    " +
			"    select r2.id from isw_resource r2   where R2.PERMANENT_RESOURCE_ID=r.permanent_resource_id    " +
			"    and r2.id in ( " +
			"             select ir2.resource_id from isw_incident_resource ir2  " +
			"             where ir2.resource_id = r2.id   " +       
			"             and ir2.id in (  " +
			"                       select wp.incident_resource_id from isw_work_period wp  " +
			"                       where  wp.incident_resource_id = ir2.id and wp.id in (  " +
			"                            select wpa.work_period_id from isw_work_period_assignment wpa where wpa.work_period_id = wp.id  " +
			"                            and wpa.assignment_id in (  " +
			"                                select a.id from isw_assignment a where a.id = wpa.assignment_id " +
			"                                and A.ASSIGNMENT_STATUS in ('C','F','P' )                 )               )         )   ) ) "
			)
		   	.append(" AND r.deleted_Date is null ")
		   	.append(" AND wa.id= " + filter.getWorkAreaId() + " ")
		    .append(" AND r.is_enabled = :resourceEnabled ");
		   
		    if(StringUtility.hasValue(filter.getResourceName())){
		    	sql.append("AND (")
		    		.append("r.resource_name like '"+filter.getResourceName().toUpperCase()+"%' ")
		    		.append(" OR ")
		    		.append("r.last_name like '"+filter.getResourceName().toUpperCase()+"%' ")
		    	    .append(") ");
		    }
		    
		    if(StringUtility.hasValue(filter.getAgencyCode())){
		    	sql.append("AND AG.AGENCY_CODE like '"+filter.getAgencyCode().toUpperCase()+"%' ");
		    }
		    
		    if(StringUtility.hasValue(filter.getIncidentName())){
		    	
		    }
		    
		    if(StringUtility.hasValue(filter.getResourceUnitCode())){
		    	sql.append("AND ORG.UNIT_CODE like '"+filter.getResourceUnitCode().toUpperCase()+"%' ");
		    }
		    
//		    if(StringUtility.hasValue(filter.getUnitCode())){
//		    	sql.append("AND ORG.UNIT_CODE like '"+filter.getUnitCode().toUpperCase()+"%' ");
//		    }
		    
		    if(StringUtility.hasValue(filter.getKindDescription())){
		    	
		    }
		    
		   return sql.toString();
	   }
	   
	   public static String getUnassignedResourcesQuery2(WorkAreaResourceFilter filter,Boolean isOracle) {
		   StringBuffer sql = new StringBuffer()
		   	.append(
		          "select wa.id as workAreaId ,war.resource_id as workAreaResourceId ,r.parent_resource_id as parentResourceId ,r.resource_name as resourceName ,r.id as resourceId ,r.last_name as lastName ,r.first_name as firstName ,AG.AGENCY_CODE as agencyCode ,ORG.UNIT_CODE as unitCode  " +
		          " , k.description as kindDescription " +
		          //",(select k.description from iswl_kind k where k.id = ( select kind_id from isw_resource_kind rk where rk.resource_id = r.id and rk.is_primary = " + (isOracle ? "1 " : "true ") + ")) as kindDescription " + 
		          "  from isw_work_area wa ,  " +
		          "  isw_work_area_resource war , " +
		          "  isw_resource r       " +
		          "      left join isw_resource_kind rk on r.id = rk.resource_id " +
		          "      left join iswl_kind k on rk.kind_id = k.id " +
		          "      left join isw_organization org on R.ORGANIZATION_ID = org.id      " +
		          "      left join iswl_agency ag on R.AGENCY_ID=ag.id   " + 
		          "  where war.work_area_id = wa.id " +  
		          " and (k.id = ( select kind_id from isw_resource_kind rk where rk.resource_id = r.id and rk.is_primary = " + (isOracle ? "1 " : "true ") + "))" + 
		          "  and r.id = war.resource_id " ); 
		   
		   		  // does not have active assignment 
		          sql.append("and  " +
		          " ( " +
		          "     ( " +
		          "         r.id not in (   " +
		          "         select r1.permanent_resource_id from isw_resource r1 where r1.permanent_resource_id is not null and r1.id in (   " +
		          "             select resource_id from isw_incident_resource   ir " +
		          "                     left join isw_work_period wp2 on ir.id = wp2.incident_resource_id                          " +
		          "                 where incident_id in (                    " +
		          "                     select incident_id from isw_work_area_incident   " +
		          "                     where work_area_id = wa.id   " +
		          "                 )  " +
		          "                and ir.id in (    " +
		          "                            select wp.incident_resource_id from isw_work_period wp    " +
		          "                            where  wp.incident_resource_id = ir.id and wp.id in (    " +
		          "                                 select wpa.work_period_id from isw_work_period_assignment wpa where wpa.work_period_id = wp.id    " +
		          "                                 and wpa.assignment_id in (    " +
		          "                                     select a.id from isw_assignment a where a.id = wpa.assignment_id   " +
		          "                                     and (a.assignment_status is null or A.ASSIGNMENT_STATUS in ('C','F','P' ) )                 )  " +                    
		          "                            )  " +  
		          "                        )  " +
		          "                  )  " +
		          "          ) " +
		          "     ) " +
		          "    or " +
		          "     ( " +
		          "         r.id not in (   " +
		          "             select r1.permanent_resource_id from isw_resource r1 where r1.permanent_resource_id is not null and r1.id in (   " +
		          "                 select resource_id from isw_incident_resource   ir " +
		          "                 where id in ( " +
		          "                                     select incident_resource_id from isw_work_period wp " +
		          "                                         where wp.id in  " +
		          "                                         ( " +
		          "                                             select work_period_id from isw_work_period_assignment wpa " +
		          "                                                 where wpa.assignment_id in  " +
		          "                                                 ( " +
		          "                                                     select a.id from isw_assignment a where a.id = wpa.assignment_id  " + 
		          "                                                     and (a.assignment_status is null or A.ASSIGNMENT_STATUS in ('C','F','P' ) )                 ) " +                     
		          "                                                 ) " +
		          "                                         ) " +
		          "                                 ) " +
		          "          ) " +
		          "     ) " +
		          "  ) ");  
		   
		          sql.append("  AND r.deleted_Date is null " +
		          "  AND wa.id=" + filter.getWorkAreaId()+ " " +
		          " AND r.is_enabled = :resourceEnabled ");
		            
		    if(StringUtility.hasValue(filter.getResourceName())){
		    	sql.append("AND (")
		    		.append("r.resource_name like '"+filter.getResourceName().toUpperCase()+"%' ")
		    		.append(" OR ")
		    		.append("r.last_name like '"+filter.getResourceName().toUpperCase()+"%' ")
		    	    .append(") ");
		    }
		    
		    //filter on Resource Name
		    if(StringUtility.hasValue(filter.getAgencyCode())){
		    	sql.append("AND AG.AGENCY_CODE like '"+filter.getAgencyCode().toUpperCase()+"%' ");
		    }
		    
		    if(StringUtility.hasValue(filter.getIncidentName())){
		    	
		    }	
		    
		    //filter on Resource Unit Code
		    if(StringUtility.hasValue(filter.getResourceUnitCode())){
		    	sql.append("AND ORG.UNIT_CODE like '"+filter.getResourceUnitCode().toUpperCase()+"%' ");
		    }
		    
//		    if(StringUtility.hasValue(filter.getUnitCode())){
//		    	sql.append("AND ORG.UNIT_CODE like '"+filter.getUnitCode().toUpperCase()+"%' ");
//		    }
		    
		    //filter on Item Name (i.e. kindDescription)
		    if(StringUtility.hasValue(filter.getKindDescription())){
		    	sql.append("AND k.description like '"+filter.getKindDescription().toUpperCase()+"%' ");
		    }
		    
		    if(CollectionUtility.hasValue(filter.getExcludedResourceIds())){
		    	sql.append("AND r.id not in ( :ids ) ");
		    	
		    }
		    
		   return sql.toString();
	   }
	   
	   public static String getUpdateSharedOutFlagQuery(Boolean isOracle) {
		   StringBuffer sql = new StringBuffer();
		   
		   sql.append("UPDATE ISW_WORK_AREA WA ")
		   	.append("SET SHARED_OUT_FLG = " + (isOracle ? "0 " : "false "))
		   	.append("WHERE SHARED_OUT_FLG = " + (isOracle ? "1 " : "true "))
		   	.append(" AND NOT WA.ID IN ")
		   	.append(" (SELECT WORK_AREA_ID FROM ISW_WORK_AREA_USER WAU ")
		   	.append("WHERE USER_TYPE = '" + WorkAreaUserTypeEnum.SHARED +"')");
		   	
		   return sql.toString();
	   }
	   
	   public static String deleteWorkAreaOrgsNotInUserOrgs(Boolean isOracle) {
		   StringBuffer sql = new StringBuffer();
		   
		   sql.append("DELETE FROM ISW_WORK_AREA_ORGANIZATION WAO WHERE ")
		   .append(" WAO.WORK_AREA_ID IN ")
		   .append(" (SELECT WA.ID FROM ISW_WORK_AREA WA WHERE WA.USER_ID = :id ")
		   .append(" AND NOT WA.NAME = 'MY RESTRICTED WORK AREA') " )
		   .append(" AND ")
		   .append(" ((WAO.ORGANIZATION_ID IN ")
		   .append(" (SELECT ID FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? "1 " : "true ") + ") ")
		   .append(" AND WAO.ORGANIZATION_ID NOT IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_USER_ORGANIZATION WHERE USER_ID = :id)) ")
		   .append(" OR ")
		   .append(" (WAO.ORGANIZATION_ID IN (SELECT ID FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? "0 " : "false ") + ") ")
		   .append(" AND NOT ")
		   .append(" WAO.ORGANIZATION_ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_USER_ORGANIZATION WHERE USER_ID = :id))))");
		
		   return sql.toString();
	   }
	   
	   public static String deleteWorkAreaIncidentsNotInUserOrgs() {
		   StringBuffer sql = new StringBuffer();
		   
		   sql.append("DELETE FROM ISW_WORK_AREA_INCIDENT WAI WHERE WAI.WORK_AREA_ID IN ")
		   .append(" (SELECT WA.ID FROM ISW_WORK_AREA WA WHERE WA.USER_ID = :id ")
		   .append(" AND NOT WA.NAME = 'MY RESTRICTED WORK AREA') " )
		   .append(" AND NOT WAI.INCIDENT_ID IN ")
		   .append(" (SELECT I.ID FROM ISW_INCIDENT I WHERE I.UNIT_ID IN ")
		   .append(" (SELECT O.ID FROM ISW_ORGANIZATION O WHERE O.ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_USER_ORGANIZATION WHERE USER_ID = :id))))");
		
		   return sql.toString();
	   }
	   
	   public static String deleteWorkAreaResourcesNotInUserOrgs() {
		   StringBuffer sql = new StringBuffer();
		   
		   sql.append("DELETE FROM ISW_WORK_AREA_RESOURCE WAR WHERE WAR.WORK_AREA_ID IN ")
		   .append(" (SELECT WA.ID FROM ISW_WORK_AREA WA WHERE WA.USER_ID = :id ")
		   .append(" AND NOT WA.NAME = 'MY RESTRICTED WORK AREA') " )
		   .append(" AND NOT WAR.RESOURCE_ID IN ")
		   .append(" (SELECT R.ID FROM ISW_RESOURCE R WHERE R.ORGANIZATION_ID IN ")
		   .append(" (SELECT O.ID FROM ISW_ORGANIZATION O WHERE O.ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID IN ")
		   .append(" (SELECT ORGANIZATION_ID FROM ISW_USER_ORGANIZATION WHERE USER_ID = :id))))");
		
		   return sql.toString();
	   }

	   public static String getRemoveWorkAreaIncidentAssociationQuery(Long pdcId, Long incId) {
		   StringBuffer sql = new StringBuffer();
		   
		   sql.append("DELETE FROM ISW_WORK_AREA_INCIDENT WHERE INCIDENT_ID = " + incId + " ")
		   	  .append("and WORK_AREA_ID IN (")
		   	  .append("  select id from isw_work_area where standard_organization_id is not null " )
		   	  .append("   and standard_organization_id != " + pdcId + " ")
		   	  .append(") ");
		   ;
		   
		   return sql.toString();
	   }

	   public static String getAddWorkAreaIncidentAssociationQuery(Long pdcId, Long incId) {
		   StringBuffer sql = new StringBuffer();

		   sql.append("INSERT INTO ISW_WORK_AREA_INCIDENT (WORK_AREA_ID, INCIDENT_ID) ")
		   	  .append("SELECT ID, "+incId+ " FROM ISW_WORK_AREA  WHERE ")
		   	  .append("STANDARD_ORGANIZATION_ID = " + pdcId + " ");
		   
		   return sql.toString();
	   }

}
