package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.Date;

public class IncidentResourceQuery {

	public static final String GET_AVAILABLE_WA_RESOURCES_FOR_ROSTER_QUERY = 
		"select " +
		"r.id AS resourceId, " +
		"r.resource_name AS resourceName, " +
		"r.first_name AS firstName, " +
		"r.last_name AS lastName, " +
        "ag.agency_name AS agencyName, " +
        "ag.agency_code as agencyCode, " + 
        "org.organization_name AS orgName, " +
        "org.unit_code as orgCode, " + 
        "k.description as kindDescription " +
		"from isw_resource r " +
        "   left join iswl_agency ag on r.agency_id = ag.id  " + 
        "   left join isw_organization org on r.organization_id = org.id " +
        "   left join isw_resource_kind rk on (r.id = rk.resource_id and rk.is_primary = :primary ) " +
        "   left join iswl_kind k on rk.kind_id = k.id, " +
		"isw_work_area wa, " +
		"isw_work_area_resource war " +
		"where R.PARENT_RESOURCE_ID is null " + 
		"and r.id = war.resource_id " + 
		"and r.id != :parentresourceid " +
		"and war.work_area_id = wa.id " + 
		"and wa.id = :waid " +  
		"and ( " +
		"(r.id not in (select resource_id from isw_incident_resource where incident_id = :incidentid )) " + 
        "   and " + 
        "    (r.id not in (select r2.permanent_resource_id from isw_resource r2 where r2.permanent_resource_id is not null and r2.id in (select resource_id from isw_incident_resource where incident_id = :incidentid) )) " + 
       ") ";


	public static String GET_UNROSTER_RESOURCES_QUERY() {
		String sql = 
			"UPDATE ISW_RESOURCE "
			+ "SET " 
				+"PARENT_RESOURCE_ID = NULL " 
				+", LEADER_TYPE = 99 "
				//+ ", RESOURCE_CLASSIFICATION = 'S' " 
			+ "WHERE ID IN ( :ids )";
		
		return sql;
	}
	
	
	/**
	 * @param parentResourceId
	 * @return
	 */
	public static String getAssignmentTimeRecordCount(Long parentResourceId) {
		
		StringBuffer sql = new StringBuffer();
		
		 sql.append("SELECT COUNT(A.ID) ")
			.append("FROM ISW_ASSIGNMENT A ")
			.append("WHERE ID IN ( ")
		    .append("	SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ") 
		    .append("	WHERE WORK_PERIOD_ID IN ")  
		    .append("   ( ")
		    .append(" 		SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN ")  
		    .append("		( ")
		    .append("			SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ") 
		    .append("				SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = " + parentResourceId + " ") 
		    .append("			) ") 
		    .append("		) ") 
		    .append("	) ") 
		    .append(") ") 
		    .append("AND A.ID NOT IN (SELECT ASSIGNMENT_ID FROM ISW_ASSIGNMENT_TIME) ");
		
		return sql.toString();
	}
	
	public static String getAssignmentTimeRecordIds(Long parentResourceId,Boolean isOracle) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(IR.ID) ")
		.append("FROM ISW_INCIDENT_RESOURCE IR ")
		.append("		        ,ISW_RESOURCE R ")
		.append("		        ,ISW_WORK_PERIOD WP ")
		.append("		        ,ISW_WORK_PERIOD_ASSIGNMENT WPA ")
		.append("		        ,ISW_ASSIGNMENT A ")
		.append("WHERE IR.RESOURCE_ID = R.ID ")
		.append("AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
		.append("AND WPA.WORK_PERIOD_ID = WP.id ")
		.append("AND A.ID = WPA.ASSIGNMENT_ID ")
		.append("AND  ")
		.append("( ")
		.append("    (A.ID NOT IN (SELECT ASSIGNMENT_ID FROM ISW_ASSIGNMENT_TIME)) ")
		.append("    OR ")
		.append("    (A.ID IN (SELECT ASSIGNMENT_ID FROM ISW_ASSIGNMENT_TIME WHERE (EMPLOYMENT_TYPE IS NULL OR EMPLOYMENT_TYPE = ''))) ")
		.append(") ")
		.append("AND R.IS_PERSON = " + (isOracle ? 1 : true) + " ")
		.append("AND R.PARENT_RESOURCE_ID = " + parentResourceId + " ");
		
		return sql.toString();
	}

	
	
	
	/**
	 * @param parentResourceId
	 * @param isOracle
	 * @return
	 */
	public static String getInsertAssignmentTime(Long parentResourceId, Boolean isOracle) {
		
		StringBuffer sql = new StringBuffer();
		
		 sql.append("INSERT INTO ISW_ASSIGNMENT_TIME (ID, ASSIGNMENT_ID) ")
			.append("SELECT " + (isOracle ? "seq_assignment_time.nextVal" : "nextVal('seq_assignment_time')") + ", ")
			.append("A.ID ")
			.append("FROM ISW_ASSIGNMENT A ")
			.append("WHERE ID IN ( ")
		    .append("	SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ") 
		    .append("	WHERE WORK_PERIOD_ID IN ")  
		    .append("   ( ")
		    .append(" 		SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN ")  
		    .append("		( ")
		    .append("			SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ") 
		    .append("				SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = " + parentResourceId + " ")
		    .append("			) ") 
		    .append("		) ") 
		    .append("	) ") 
		    .append(") ") 
		    .append("AND A.ID NOT IN (SELECT ASSIGNMENT_ID FROM ISW_ASSIGNMENT_TIME) ");
		
		return sql.toString();
	}
	
	/**
	 * @param parentResourceId
	 * @param type
	 * @return
	 */
	public static String getUpdateCrewEmploymentType(Boolean isOracle, Long parentResourceId, String type){
		StringBuffer sql = new StringBuffer();
	
		sql.append("UPDATE ISW_ASSIGNMENT_TIME ")
		   .append("SET EMPLOYMENT_TYPE = '" + type + "' ")
		   .append("WHERE ")
		   .append("ASSIGNMENT_ID IN ( ")
		   .append("   SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ")
		   .append("   WHERE WORK_PERIOD_ID IN  ")
		   .append("   ( ")
		   .append("      SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN  ")
		   .append("      ( ")
		   .append("	     SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ")
		   .append("		     SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = " + parentResourceId + " ")
		   .append("		     AND IS_PERSON = " + (isOracle ? 1 : true) + " ")
		   .append("         ) ")
           .append("      ) ")
		   .append("  ) ")
		   .append(") ")
		   .append("AND (EMPLOYMENT_TYPE IS NULL OR EMPLOYMENT_TYPE = '' ) ");
						
		return sql.toString();
	}

	public static String getUpdateCrewHireInfo(Boolean isOracle, Long parentResourceId, String name,String phone, String fax){
		StringBuffer sql = new StringBuffer();
	
		sql.append("UPDATE ISW_ASSIGNMENT_TIME ")
		   .append("SET HIRING_UNIT_NAME = '" + name + "' ")
		   .append(", HIRING_UNIT_PHONE = '" + phone + "' ")
		   .append(", HIRING_UNIT_FAX = '" + fax + "' ")
		   .append("WHERE ")
		   .append("ASSIGNMENT_ID IN ( ")
		   .append("   SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ")
		   .append("   WHERE WORK_PERIOD_ID IN  ")
		   .append("   ( ")
		   .append("      SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN  ")
		   .append("      ( ")
		   .append("	     SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ")
		   .append("		     SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = " + parentResourceId + " ")
		   .append("         ) ")
           .append("      ) ")
		   .append("  ) ")
		   .append(") ");
						
		return sql.toString();
	}

	public static String getUpdateCrewOtherRate(Boolean isOracle, Long parentResourceId){
		StringBuffer sql = new StringBuffer();
	
		sql.append("UPDATE ISW_ASSIGNMENT_TIME ")
		   .append("SET OTHER_DEFAULT_RATE = (")
		   .append("   select other_default_rate ")
		   .append("   from isw_assignment_time at ")
		   .append("        ,isw_assignment a ")
		   .append("        ,isw_work_period_assignment wpa ")
		   .append("        ,isw_work_period wp ")
		   .append("        ,isw_incident_resource ir ")
		   .append("   where at.assignment_id = a.id ")
		   .append("   and a.end_date is null ")
		   .append("   and wpa.assignment_id = a.id ")
		   .append("   and wp.id = wpa.work_period_id ")
		   .append("   and ir.id = wp.incident_resource_id ")
		   .append("   and ir.resource_id = " + parentResourceId + " ")
		   .append(") ")
		   .append("WHERE ")
		   .append("ASSIGNMENT_ID IN ( ")
		   .append("   SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ")
		   .append("   WHERE WORK_PERIOD_ID IN  ")
		   .append("   ( ")
		   .append("      SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN  ")
		   .append("      ( ")
		   .append("	     SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ")
		   .append("		     SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = " + parentResourceId + " ")
		   .append("		     AND IS_PERSON = " + (isOracle ? 1 : true) + " ")
		   .append("         ) ")
           .append("      ) ")
		   .append("  ) ")
		   .append(") ")
		   .append("AND (EMPLOYMENT_TYPE = 'OTHER' ) ")
		   .append("AND (OTHER_DEFAULT_RATE IS NULL or OTHER_DEFAULT_RATE < 1 ) ");
						
		return sql.toString();
	}

	public static String getUpdateCrewOtherRate2(Boolean isOracle, Long assignmentTimeId, BigDecimal otherRate){
		StringBuffer sql = new StringBuffer();
	
		sql.append("UPDATE ISW_ASSIGNMENT_TIME ")
		   .append("SET OTHER_DEFAULT_RATE = "+otherRate+" ")
		   .append("WHERE id = " + assignmentTimeId + " ");
		
		return sql.toString();
	}
	
	/**
	 * @param parentResourceId
	 * @return
	 */
	public static String getUpdateCrewAddresses(Long incidentResourceId, Long parentResourceId){
		StringBuffer sql = new StringBuffer();
		   
		return sql.toString();
	}

	/**
	 * @param parentResourceId
	 * @return
	 */
	public static String getClearCrewAddresses(Long parentResourceId){
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ADDRESS ")
		   .append("SET ADDRESS_LINE_1='' ")
		   .append(", ADDRESS_LINE_2='' ")
		   .append(", CITY='' ")
		   .append(", POSTAL_CODE='' ")
		   .append(", COUNTRY_SUBDIVISION_ID = NULL ")
		   .append("WHERE ID IN ( ")
		   .append("SELECT MAILING_ADDRESS_ID FROM ISW_ASSIGNMENT_TIME ")
		   .append("WHERE ")
		   .append("ASSIGNMENT_ID IN ( ")
		   .append("   SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ")
		   .append("   WHERE WORK_PERIOD_ID IN ")
		   .append("   ( ")
		   .append("     SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN ")
           .append("     ( ")
		   .append("          SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE RESOURCE_ID IN ( ")
		   .append("             SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID = "+parentResourceId+ " " )
	       .append("          ) ")
		   .append("     ) ")
		   .append("   ) ")
		   .append(") ")
		   .append(") ");
		   
		return sql.toString();
	}

	public static String getClearNonContractorEmploymentType(Long id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE ISW_ASSIGNMENT_TIME SET EMPLOYMENT_TYPE = NULL ")
		    .append(", OF_REMARKS='', PHONE='', FAX='', MAILING_ADDRESS_ID = NULL ")
			.append("WHERE ASSIGNMENT_ID IN (")
			.append("  SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT ")
			.append("  WHERE WORK_PERIOD_ID IN (")
			.append("     SELECT ID FROM ISW_WORK_PERIOD ")
			.append("     WHERE INCIDENT_RESOURCE_ID = " + id + " ") 
			.append("  ) ")
			.append(") ");
		
		return sql.toString();
	}
	
	public static String getClearContractorInfo(Boolean isOracle,Long assignmentTimeId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE ISW_CONTR_PAYMENT_INFO ")
			.append("SET VIN_NAME='' ")
			.append(", DESC1='' ")
			.append(", DESC2='' ")
			.append(", CONTRACTOR_ID = NULL ")
			.append(", CONTRACTOR_AGREEMENT_ID = NULL ")
			.append(", HIRED_DATE = null ")
			.append(", IS_OPERATION = " + (isOracle ? 0 : false) + " ")
			.append(", IS_SUPPLIES = " + (isOracle ? 0 : false) + " ")
			.append(", IS_WITHDRAWN = " + (isOracle ? 0 : false) + " ")
			.append(", POINT_OF_HIRE = '' ")
			.append("WHERE ASSIGNMENT_TIME_ID = " + assignmentTimeId + " ");
		
		return sql.toString();
	}

	public static String getClearContractorRateInfo(Long assignmentTimeId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("DELETE FROM ISW_CONTR_PAYINFO_RATE ")
			.append("WHERE CONTRACTOR_PAY_INFO_ID = ( ")
			.append("  SELECT ID FROM ISW_CONTR_PAYMENT_INFO WHERE ASSIGNMENT_TIME_ID = " + assignmentTimeId + " ")
			.append(") ");
		
		return sql.toString();
	}

	public static String getUnassignedResourceInventoryResources(String dispatchCenter,Long userId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ")
			.append("distinct(R.id) as resourceId  ")
			.append(",case ")
			.append("when R.is_person = "+(isOracle ? 0 : false)+ "  then R.resource_name ")
			.append("else ")
			.append("R.last_name || ', ' || R.first_name ")
			.append("end as resourceName ")
			.append(",AG.AGENCY_CODE as agencyCode  ")
			.append(",ORG.UNIT_CODE as unitCode    ")
			.append(",K.DESCRIPTION as kindDescription ")
			.append("FROM ISW_RESOURCE R ")
			.append("    LEFT JOIN ISWL_AGENCY AG ON R.AGENCY_ID = AG.ID ")
			.append("    LEFT JOIN ISW_ORGANIZATION ORG ON R.ORGANIZATION_ID = ORG.ID ")
			.append("    LEFT JOIN ISW_RESOURCE_KIND RK ON R.ID = RK.RESOURCE_ID ")
			.append("    LEFT JOIN ISWL_KIND K ON  ")
			.append("    			(RK.KIND_ID = K.ID ")
			.append("    			 AND  ")
			.append("    			 RK.IS_PRIMARY="+(isOracle ? 1 : true)+" ")
			.append("    			 ) ")
			.append("WHERE R.IS_PERMANENT = " + (isOracle ? 1 : true) + " ")
			.append("AND R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
			.append("AND R.DELETED_DATE IS NULL ")
			.append("AND R.PRIMARY_DISP_CTR_ID = (SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+dispatchCenter+"' AND IS_DISPATCH_CENTER="+(isOracle ? 1 : true)+" ) ")
			.append("AND R.ID NOT IN  ")
			.append("( ")
			.append("   select r1.permanent_resource_id from isw_resource r1 where r1.permanent_resource_id is not null and r1.id in  ")
			.append("   (     ")
			.append("      select resource_id  ")
			.append("      from isw_incident_resource   ir   ")
			.append("         left join isw_work_period wp2 on ir.id = wp2.incident_resource_id ")                           
			.append("      where ir.id in  ")
			.append("      (  ")    
			.append("           select wp.incident_resource_id  ")
			.append("           from isw_work_period wp  ")    
			.append("           where  wp.incident_resource_id = ir.id  ")
			.append("           and wp.id in  ")
			.append("            (   ")   
			.append("               select wpa.work_period_id  ")
			.append("               from isw_work_period_assignment wpa  ")
			.append("               where wpa.work_period_id = wp.id      ")
			.append("               and wpa.assignment_id in  ")
			.append("               (      ")
			.append("                    select a.id from isw_assignment a  ")
			.append("                    where a.id = wpa.assignment_id     ")
			.append("                    and ( ")
			.append("                       a.assignment_status is null  ")
			.append("                       or  ")
			.append("                       A.ASSIGNMENT_STATUS in ('C','F','P' )  ")
			.append("                    )                                        ")
			.append("               )    ")
			.append("            )   ")
			.append("       )   ")
			.append("    )   ")
			.append(" )    ")
			.append("AND R.ID NOT IN (select resource_id from isw_user_resinvview_exclude where user_id = "+userId+" ) ");

		return sql.toString();
	}
	
	public static String getUpdateUseActualsOnly(Long incidentResourceId, Boolean val, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ISW_COST_DATA ");
		if(isOracle)
			sql.append(" SET IS_USE_ACCRUALS_ONLY = " + (val==true ? 1 : 0) + " ");
		else
			sql.append(" SET IS_USE_ACCRUALS_ONLY = " + (val==true ? true : false) + " ");
		
		sql.append("WHERE ID = " )
			.append(" ( SELECT COST_DATA_ID FROM ISW_INCIDENT_RESOURCE WHERE ID = " + incidentResourceId + ") ");

		return sql.toString();
	}
	
	public static String getByAssignmentTimeId(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append("select wp.incident_resource_id  " )
		   .append("from isw_work_period wp " )
		   .append(" ,isw_work_period_assignment wpa " )
		   .append(" ,isw_assignment a " )
		   .append(" ,isw_assignment_time at " )
		   .append("where at.id = " + id + " " )
		   .append("and a.id = at.assignment_id " )
		   .append("and wpa.assignment_id = a.id " )
		   .append("and wp.id = wpa.work_period_id ");
		
		return sql.toString();
	}
	
	public static String getByAssignmentTimePostId(Long atpId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select wp.incident_resource_id  ")
		   .append("from isw_work_period wp ") 
		   .append(" ,isw_work_period_assignment wpa " )
		   .append(" ,isw_assignment a " )
		   .append(" ,isw_assignment_time at " )
		   .append(" ,isw_assign_time_post atp " )
		   .append("where atp.id = " + atpId + " " )
		   .append("and at.id = atp.assignment_time_id " )
		   .append("and a.id = at.assignment_id " )
		   .append("and wpa.assignment_id = a.id " )
		   .append("and wp.id = wpa.work_period_id ");
		
		return sql.toString();
	}
	
	public static String getTopLevelResources(Long incidentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID FROM ISW_INCIDENT_RESOURCE ")
			.append("WHERE INCIDENT_ID = " + incidentId + " ")
			.append("AND RESOURCE_ID IN (")
			.append("   SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID IS NULL AND DELETED_DATE IS NULL ")
			.append(")" );
		
		return sql.toString();
	}
	
	public static String getTopLevelResourcesIG(Long incidentGroupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID FROM ISW_INCIDENT_RESOURCE ")
			.append("WHERE INCIDENT_ID IN ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + incidentGroupId + ") ")
			.append("AND RESOURCE_ID IN (")
			.append("   SELECT ID FROM ISW_RESOURCE WHERE PARENT_RESOURCE_ID IS NULL AND DELETED_DATE IS NULL ")
			.append(")" );
		
		return sql.toString();
	}
	
	public static String getValidResourcesIdsForCostQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
			.append("      distinct(ir.id) ")
			.append("	from isw_incident_resource ir ")
			.append("   , isw_cost_data cd ")
			.append("   , isw_resource r ")
			.append("          left join iswl_agency ag on r.agency_id = ag.id ")
			.append("   , isw_work_period wp ")
			.append("   , isw_work_period_assignment wpa ")
			.append("   , isw_assignment a ")
			.append("        left join isw_assignment_time at on a.id = at.assignment_id ")
			.append("        left join isw_assign_time_post atp on at.id = atp.assignment_time_id ")
			.append("where ir.incident_id = " + incidentId + " ")
			.append("and cd.id = ir.cost_data_id ")
			.append("and r.id = ir.resource_id ")
			.append("and r.parent_resource_id is null ")
			.append("and r.deleted_date is null ")
			.append("and wp.incident_resource_id = ir.id ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ")
			.append("and a.end_date is null ")
			.append("and (")
			.append("   wp.ci_check_in_date is not null ")
			.append("   and cd.assign_date is not null ")
			.append("   and a.assignment_status != 'F' ")
			.append("   and ( ")
			.append("         ag.agency_code is not null ")
			.append("         or ")
			.append("         (select count(atp2.id) from isw_assign_time_post atp2 where atp2.assignment_time_id = at.id) > 0 ")
			.append("         or ")
			.append("         (select count(taa2.id) from isw_time_assign_adjust taa2 where taa2.assignment_id = a.id) > 0 ")
			.append("       ) ")
			.append(") ")
			;

		return sql.toString();
	}

	public static String getIncidentResourceChildrenIdsQuery(Long irid){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ir.id ")
			.append("from isw_incident_resource ir ")
		     .append(", isw_resource r ")
		.append("where ir.resource_id = r.id ")
		.append("and r.id in ( ")
		.append("   select id from isw_resource ")
		.append("   where parent_resource_id in ( ")
		.append("	    select resource_id from isw_incident_resource ")
		.append("       where id = " + irid + " ")
		.append("	) ")
		.append(") ");
		
		return sql.toString();
	}

	public static String getDeleteAdInfo(Long id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("DELETE FROM ISW_AD_PAYMENT_INFO ")
			.append("WHERE ID = " + id + " " );
		
		return sql.toString();
	}

	public static String getEarliestDatesByIncResourceId(Long irId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct(ir.id) as incidentResourceId ")
	       .append(", cd.assign_date as assignDate ")
	       .append(", cd.id as costDataId ")
	       .append(", cpi.hired_date as hiredDate        ")
	       .append(", wp.ci_check_in_date as checkInDate ")
	       .append(", (select min(atp.post_start_date) from isw_assign_time_post atp where atp.assignment_time_id=at.id and atp.deleted_date is null) as earliestTimePostDate ")
	       .append(", (select min(taa.activity_date) from isw_time_assign_adjust taa where taa.assignment_id = a.id and taa.deleted_date is null) as earliestTimeAdjDate ")
	       .append(", ( ")
	       .append("     select min(cd2.assign_date) ")
	       .append("     from isw_incident_resource ir2 ")
	       .append("             left join isw_cost_data cd2 on ir2.cost_data_id = cd2.id ")
	       .append("          , isw_resource r2 ")
	       .append("     where r2.parent_resource_id = r.id ")
	       .append("     and ir2.resource_id = r2.id ")
	       .append(") as earliestSubAssignDate ")
	       .append("from isw_incident_resource ir ")
	       .append("    left join isw_cost_data cd on ir.cost_data_id = cd.id ")
	       .append(", isw_incident i ")
	       .append(", isw_resource r ")
	       .append(", isw_work_period wp ")
	       .append(", isw_work_period_assignment wpa ")
	       .append(", isw_assignment a ")
	       .append("    left join isw_assignment_time at on a.id=at.assignment_id ")
	       .append("    left join isw_contr_payment_info cpi on at.id = cpi.assignment_time_id ")
	       .append("where ir.id = " + irId + " ")
	       .append("and i.id = ir.incident_id ")
	       .append("and cd.id = ir.cost_data_id ")
	       .append("and r.id = ir.resource_id ")
	       .append("and wp.incident_resource_id = ir.id ")
	       .append("and wpa.work_period_id = wp.id ")
	       .append("and a.id = wpa.assignment_id ")
	       .append("and a.end_date is null ")
	       .append("order by ir.id desc ");   
		
		return sql.toString();
	}

	public static String getIapResourceQuery(Long incidentId, Long incidentGroupId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
			sql.append("select ir.id as incidentResourceId ")
				.append(", r.id as resourceId ")
				.append(", r.resource_name as resourceName ")
				.append(", r.last_name as lastName ")
				.append(", r.first_name as firstName ")
				.append(", WP.CI_CHECK_IN_DATE as checkInDate ")
				.append(", WP.CI_FIRST_WORK_DATE as firstWorkDate ")
				.append(", WP.CI_LENGTH_AT_ASSIGNMENT as lengthAtAssignment ")
				.append(", a.assignment_status as status ")
				.append(", a.request_number as requestNumber ")
				.append(", sortrequestnumber(a.request_number) as requestNumberSortValue ")
				.append(", a.is_training as training ")
				.append(", k.code as kindCode ")
				.append(", k.description as kindDescription ")
				.append(", ag.agency_code as resourceAgencyCode ")
				.append(", ag.agency_name as resourceAgencyName ")
				.append(", O.UNIT_CODE as resourceUnitCode ")
				.append(" from isw_incident_resource ir ")
				.append(", isw_resource r ")
				.append("    left join iswl_agency ag on r.agency_id = ag.id ")
				.append("    left join isw_organization o on r.organization_id = o.id ")
				.append(", isw_work_period wp ")
				.append(", isw_work_period_assignment wpa ")
				.append(", isw_assignment a ")
				.append("left join iswl_kind k on a.kind_id = k.id ");
			if(LongUtility.hasValue(incidentId)){
				sql.append("where ir.incident_id = " + incidentId + " ");
			}else{
				sql.append("where ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
			}
				sql.append("and r.id = ir.resource_id ")
				.append("and wp.incident_resource_id = ir.id ")
				.append("and wpa.work_period_id = wp.id ")
				.append("and a.id = wpa.assignment_id ")
				.append("and r.deleted_date is null ")
				;
		
		return sql.toString();
		
	}
	
	public static String getResourceDataForAccrualQuery(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("select ir.id as incidentResourceId ")
        	.append(", r.id as resourceId ")
        	.append(", cd.id as costDataId ");
			if(BooleanUtility.isTrue(isOracle)){
				sql.append(", case when cd.is_accrual_locked is null or cd.is_accrual_locked = 0 then 'false' else 'true' end as accrualLocked ");
			}else{
				sql.append(", case when cd.is_accrual_locked is null or cd.is_accrual_locked = false then 'false' else 'true' end as accrualLocked ");
			}
	     sql.append(", ac.code as accrualCode ")
	        .append(", cdPayAg.agency_code as resourcePaymentAgency ")
	        .append(", incAg.agency_code as incidentAgency ")
	        .append(", resAg.agency_code as resourceAgencyCode ");
			if(BooleanUtility.isTrue(isOracle)){
				sql.append(", case when resAg.is_state is null or resAg.is_state = 0 then 'false' else 'true' end as resourceAgencyState ");
			}else{
				sql.append(", case when resAg.is_state is null or resAg.is_state = false then 'false' else 'true' end as resourceAgencyState ");
			}
	     sql.append(", at.employment_type as resourceEmploymentType      ")  
	        .append(", k.code as resourceItemCode ")
	        .append(", rq.code as resourceItemCodeCategory ")
	        .append(", resOrg.unit_code as resourceUnitCode ")
	        .append(", iOrg.unit_code as incidentUnitCode ")
	        .append(", cs.cs_abbreviation as incidentState ")
	        .append(", ( ")
            .append("    select count(irdc.id) ")
            .append("    from isw_inc_res_daily_cost irdc ")
            .append("    where irdc.incident_resource_id = ir.id ");
			if(BooleanUtility.isTrue(isOracle)){
				sql.append(" and is_locked is null or is_locked = 0 ");
			}else{
				sql.append(" and is_locked is null or is_locked = false ");
			}
	     sql.append("  ) as resourceCostCount ")
	        .append("from isw_incident_resource ir ")
	        .append(", isw_work_period wp ")
	        .append(", isw_work_period_assignment wpa ")
	        .append(", isw_assignment a ")
	        .append("     left join iswl_kind k on a.kind_id = k.id ")
	        .append("     left join iswl_request_category rq on k.request_category_id = rq.id ")
	        .append("     left join isw_assignment_time at on a.id = at.assignment_id ")
	        .append(", isw_cost_data cd ")
	        .append("     left join iswl_accrual_code ac on cd.accrual_code_id = ac.id ")
	        .append("     left join iswl_agency cdPayAg on cd.payment_agency_id = cdPayAg.id ")
	        .append(", isw_resource r ")
	        .append("     left join isw_organization resOrg on r.organization_id = resOrg.id ")
	        .append("     left join iswl_agency resAg on r.agency_id = resAg.id ")
	        .append(", isw_incident i ")
	        .append(", isw_organization iOrg ")
	        .append(", iswl_agency incAg  ")
	        .append(", iswl_country_subdivision cs  ")
	        .append("where ir.incident_id = " + incidentId + " ")
	        .append("and cd.id = ir.cost_data_id ")
	        .append("and wp.incident_resource_id = ir.id ")
	        .append("and wpa.work_period_id = wp.id ")
	        .append("and a.id = wpa.assignment_id ")
	        .append("and r.id = ir.resource_id ")
	        .append("and i.id = ir.incident_id ")
	        .append("and iOrg.id = i.unit_id ")
	        .append("and cs.id = i.country_subdivision_id ")
	        .append("and incAg.id = i.AGENCY_ID	 ");
			
		return sql.toString(); 
	}
	
	public static String getResourceCostExceptionQuery(Long incidentId, Long incidentGroupId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ir.id as id ")
		   .append("       , case ")
		   .append("           when cd.assign_date is null or wp.ci_check_in_date is null then 'Missing Check-In/Assign Date' ")
		   .append("           when a.assignment_status is null or a.assignment_status = 'F' then 'Status is F' ")
		   .append("         end as dailyCostException ")
		   .append("FROM isw_incident_resource ir ")
		   .append("     , isw_cost_data cd ")
		   .append("     , isw_work_period wp " )
		   .append("     , isw_work_period_assignment wpa ")
		   .append("     , isw_assignment a ");
		if(LongUtility.hasValue(incidentId)){
			sql.append("WHERE ir.incident_id = " + incidentId + " ");
		}else{
			sql.append("WHERE ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		}
		sql.append("AND wp.incident_resource_id = ir.id ")
		   .append("AND cd.id = ir.cost_data_id ")
		   .append("AND wpa.work_period_id = wp.id ")
		   .append("AND a.id = wpa.assignment_id ")
		   .append("AND (")
		   .append("  (cd.assign_date is null or wp.ci_check_in_date is null ) ")
		   .append("  OR ")
		   .append("  (a.assignment_status is null or a.assignment_status = 'F' ) ")
		   .append(") ")
		   .append("");
		return sql.toString();
	}
	
	public static String getResourceTimeCostRecordsCountQuery() {
		String sql = 
			"select count(atp.id) as timePostCount " + 
			", count(dc.id) as costCount " +
			", count(taa.id) as timeAdjustmentCount " + 
			"from isw_incident_resource ir " + 
			"left join isw_inc_res_daily_cost dc on ir.id = dc.incident_resource_id " + 
			", isw_work_period wp " + 
			", isw_work_period_assignment wpa " + 
			", isw_assignment a " + 
			"left join isw_time_assign_adjust taa on a.id = taa.assignment_id " + 
			", isw_assignment_time at " + 
			"left join isw_assign_time_post atp on at.id = atp.assignment_time_id " + 
			"where ir.id in ( :ids ) " +   
			"and wp.incident_resource_id = ir.id " + 
			"and wpa.work_period_id = wp.id " + 
			"and a.id =wpa.assignment_id " + 
			"and AT.ASSIGNMENT_ID=a.id " + 
			"and a.end_date is null " 
			;
		return sql;	
	}

	public static String getIncResTimeDataQuery(Long parentIncidentResourceId, Boolean subsOnly,Date postDate, Boolean isOracle) {
		String sql = 
			"select ir.id as incidentResourceId " + 
			", i.nbr as incidentNumber " +
			", i.incident_year as incidentYear " +
			", iorg.unit_code as incidentUnitCode " +
			", ir.res_num_id as resNumId " +
			", r.id as resourceId " +
			", a.id as assignmentId " +
			", at.id as assignmentTimeId " +
			", r.resource_name as resourceName " +
			", o.unit_code as resourceUnitCode " +
			", r.last_name as lastName " +
			", r.first_name as firstName " +
			", a.request_number as requestNumber " +
			", at.employment_type as employmentType " +
			", k.code as kindCode " +
			", at.hiring_unit_name as hiringUnitName " +
			", at.hiring_phone as hiringPhone " +
			", at.hiring_fax as hiringFax " +
			", apiorg.unit_code as pointOfHire " +
			", api.eci as eci " +
			", rcr.rate as adRate " +
			", at.other_default_rate as otherDefaultRate " +
			", r.is_person as person " +
			", wpac.account_code as defaultAccountCode " +
			", at.of_remarks as ofRemarks " +
			", addr.address_line_1 as addressLine1 " +
			", addr.address_line_2 as addressLine2 " +
			", addr.city as city " +
			", addr.postal_code as postalCode " +
			", cs.cs_abbreviation as state " +
			", at.phone as phone " +
			", at.fax as fax " +
			", i.incident_name as incidentName " +
		"from isw_incident_resource ir " +
		"	, isw_resource r " +
		"		left join isw_organization o on r.organization_id = o.id " +
		"	, isw_work_period wp " +
		"		left join isw_incident_account_code wpiac on wp.def_incident_account_code_id = wpiac.id " +
		"		left join isw_account_code wpac on wpiac.account_code_id = wpac.id " +
		"	, isw_work_period_assignment wpa " +
		"	, isw_assignment a " +
		"	, iswl_kind k " +
		"	, isw_assignment_time at " +
		"		left join isw_ad_payment_info api on at.id = api.assignment_time_id " +
		"		left join isw_organization apiorg on api.point_of_hire_id = apiorg.id " +
		"		left join iswl_rate_class_rate rcr on api.rate_class_rate_id = rcr.id " +
		"       left join isw_address addr on at.mailing_address_id = addr.id " +
		"       left join iswl_country_subdivision cs on addr.country_subdivision_id = cs.id " +
		"	, isw_incident i " +
		"		left join isw_organization iorg on i.unit_id = iorg.id ";
		if(BooleanUtility.isTrue(subsOnly)){
			sql=sql+"where ir.resource_id = r.id " +
			"and r.parent_resource_id = (select resource_id from isw_incident_resource where id = "+parentIncidentResourceId+") ";
		}else{
			sql=sql+"where ir.id = " + parentIncidentResourceId + " " +
			"and r.id = ir.resource_id ";
		}
		sql=sql+"and wp.incident_resource_id = ir.id " +
		"and wp.id = wpa.work_period_id " +
		"and wpa.assignment_id = a.id " +
		"and a.end_date is null " +
		"and at.assignment_id = a.id " +
		"and at.employment_type in ('FED','AD','OTHER') " +
		"and k.id = a.kind_id " +
		"and i.id = ir.incident_id " +
		"and  " +
		"( " +
		"	( " +
		"		select count(atp.id) " +
		"		from isw_assign_time_post atp " +
		"		where atp.assignment_time_id = at.id " +
		"		and to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"			< to_timestamp('"+DateUtil.toDateString(postDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		and atp.id not in ( " +
		"			select atpi.assign_time_post_id " +
		"			from isw_assign_time_post_invoice atpi " +
		"				, isw_time_invoice ti " +
		"			where ti.id = atpi.time_invoice_id " +
		"			and ti.is_draft = " + (isOracle ? 0 : false ) + " " +
		"			and ti.deleted_date is null " +
		"			and atpi.assign_time_post_id = atp.id " +
		"	) " +
		"	) > 0 " +
		"	OR " +
		"	( " +
		"		select count(taa.id) " +
		"		from isw_time_assign_adjust taa " +
		"		where taa.assignment_id = a.id " +
		"		and to_timestamp(to_char(taa.activity_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"			< to_timestamp('"+DateUtil.toDateString(postDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		and taa.id not in ( " +
		"			select taai.time_post_adjust_id " +
		"			from isw_time_assign_adj_invoice taai " +
		"				, isw_time_invoice ti " +
		"			where ti.id = taai.time_invoice_id " +
		"			and ti.is_draft = " + (isOracle ? 0 : false ) + " " +
		"			and ti.deleted_date is null " +
		"			and taai.time_post_adjust_id = taa.id " +
		"		) " +
		"	) > 0 " +
		") "
		;
		
		return sql;
	}
}
