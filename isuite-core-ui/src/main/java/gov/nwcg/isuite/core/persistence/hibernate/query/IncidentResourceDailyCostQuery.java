package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Date;

public class IncidentResourceDailyCostQuery {

	public static String getParentRollupQuery(Long irId, Long acctCodeId, Date actDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(total_cost_amount) from isw_inc_res_daily_cost ")
		.append("where incident_resource_id in ( ")
		.append("   select id from isw_incident_resource ")
		.append("   where resource_id in ( ")
		.append("	   select id from isw_resource where parent_resource_id = ( ")
		.append("   			select resource_id from isw_incident_resource where id = " + irId + " ")
		.append("	    ) ")
		.append("   ) ")
		.append(") ")
		.append("and incident_account_code_id = " + acctCodeId + " ")
		.append("and ")
		.append("   to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " )
		.append("   = ")
		.append("   to_date('"+DateUtil.toDateString(actDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ") 
		.append(" " );
		;

		return sql.toString();
	}

	public static String getChildUniqueAcctCodeIdsByDate(Long irId, Long excludeAcctCodeId, Date dt) {
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct(dc.incident_account_code_id) ")
		.append("from isw_inc_res_daily_cost dc ")
		.append("where to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" = to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");
		if(null != excludeAcctCodeId)
			sql.append("and dc.incident_account_code_id != "+excludeAcctCodeId + " ");
		sql.append("and dc.incident_resource_id in ( ")
		.append(" select id from isw_incident_resource where resource_id in (")
		.append("    select id from isw_resource where parent_resource_id in ( ")
		.append("         select resource_id from isw_incident_resource where id = " + irId + " ")
		.append("     ) ")
		.append(" ) ")
		.append(") ");

		return sql.toString();
	}

	public static String getChildUniqueCostAcctCodeIds(Long irId) {
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct(dc.incident_account_code_id) as incidentAccountCodeId ")
		   .append(", dc.activity_date as activityDate ")
		   .append("from isw_inc_res_daily_cost dc ")
		   .append("where dc.incident_resource_id in ( ")
		   .append(" select id from isw_incident_resource where resource_id in (")
		   .append("    select id from isw_resource where parent_resource_id in ( ")
		   .append("         select resource_id from isw_incident_resource where id = " + irId + " ")
		   .append("     ) ")
		   .append(" ) ")
		   .append(") ")
		   .append("group by dc.incident_account_code_id, dc.activity_date ");

		return sql.toString();
	}
	
	public static String getIncidentTotalCostQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select sum(irdc.total_cost_amount) ")
		.append("from isw_inc_res_daily_cost irdc ")
		.append("       left join isw_incident_resource ir on irdc.incident_resource_id = ir.id ")
		.append("       left join isw_resource r on ir.resource_id = r.id ")
		.append("       left join isw_incident_resource_other iro on irdc.incident_resource_other_id = iro.id ")
		.append("where irdc.incident_account_code_id in ( ")
		.append("       select id from isw_incident_account_code ")
		.append("       where incident_id = " + incidentId + " ")
		.append(") ")
		.append("and r.parent_resource_id is null ");		
		return sql.toString();
	}

	public static String getIncidentGroupTotalCostQuery(Long incidentGroupId) {
		StringBuffer sql = new StringBuffer();

		sql.append("select sum(irdc.total_cost_amount) ")
		.append("from isw_inc_res_daily_cost irdc ")
		.append("       left join isw_incident_resource ir on irdc.incident_resource_id = ir.id ")
		.append("       left join isw_resource r on ir.resource_id = r.id ")
		.append("       left join isw_incident_resource_other iro on irdc.incident_resource_other_id = iro.id ")
		.append("where irdc.incident_account_code_id in ( ")
		.append("       select id from isw_incident_account_code ")
		.append("       where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ")
		.append(") ")
		.append("and r.parent_resource_id is null ");		
		return sql.toString();
	}

	public static String getManualCostCountQuery(Long incidentResourceId, Long incidentResourceOtherId,Date dt){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(ID) FROM ")
		.append("ISW_INC_RES_DAILY_COST ");
		if(null != incidentResourceId)
			sql.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ");
		if(null != incidentResourceOtherId)
			sql.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ");
		sql.append("AND COST_LEVEL = 'M' ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" = to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getDeleteManualCostQuery(Long incidentResourceId, Long incidentResourceOtherId, Date dt){
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ")
		.append("ISW_INC_RES_DAILY_COST ");
		if(null != incidentResourceId)
			sql.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ");
		if(null != incidentResourceOtherId)
			sql.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ");
		sql.append("AND COST_LEVEL = 'M' ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" = to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getDeleteResEstCostsByAcctCodeIdQuery(Long incidentResourceId, Long incidentResourceOtherId,Long acctCodeId) {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ")
		.append("ISW_INC_RES_DAILY_COST ");
		if(null != incidentResourceId)
			sql.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ");
		if(null != incidentResourceOtherId)
			sql.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ");
		sql.append("AND COST_LEVEL IN ( 'E' , 'F') ")
		.append("AND INCIDENT_ACCOUNT_CODE_ID != " + acctCodeId + " ");

		return sql.toString();

	}

	public static String getDeleteResEstCostsQuery(Long incidentResourceId, Long incidentResourceOtherId) {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ")
		.append("ISW_INC_RES_DAILY_COST ");
		if(null != incidentResourceId)
			sql.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ");
		if(null != incidentResourceOtherId)
			sql.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ");
		sql.append("AND COST_LEVEL IN ( 'E' , 'F')  ");

		return sql.toString();

	}

	public static String getSetCostGroupId(Long incidentResourceId, Long costGroupId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ISW_INC_RES_DAILY_COST ")
		.append("SET COST_GROUP_ID = " + costGroupId + " " )
		.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ")
		.append("AND IS_LOCKED = " + (isOracle ? 0 : false) + " ");

		return sql.toString();
	}

	public static String getSetShiftId(Long incidentResourceId, Long shiftId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ISW_INC_RES_DAILY_COST ")
		.append("SET INCIDENT_SHIFT_ID = " + shiftId + " " )
		.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ")
		.append("AND IS_LOCKED = " + (isOracle ? 0 : false) + " ");

		return sql.toString();
	}

	public static String getDeleteResourceCostsBeforeDateQuery(Long incidentResourceId, Date dt) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ISW_INC_RES_DAILY_COST ")
		.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" < to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getDeleteResourceOtherCostsBeforeDateQuery(Long incidentResourceOtherId, Date dt) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ISW_INC_RES_DAILY_COST ")
		.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" < to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getDeleteResourceCostsAfterDateQuery(Long incidentResourceId, Date dt) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ISW_INC_RES_DAILY_COST ")
		.append("WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" > to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getDeleteResourceOtherCostsAfterDateQuery(Long incidentResourceOtherId, Date dt) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ISW_INC_RES_DAILY_COST ")
		.append("WHERE INCIDENT_RESOURCE_OTHER_ID = " + incidentResourceOtherId + " ")
		.append("AND to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append(" > to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");

		return sql.toString();
	}

	public static String getResourceActualsCountQuery(Long incidentId, Long resourceId) {
		StringBuffer sql = new StringBuffer();
		sql.append("")
		.append("select count(ir.id) ")
		.append("from isw_incident_resource ir ")
		.append("     ,isw_work_period wp ")
		.append("     ,isw_work_period_assignment wpa ")
		.append("     ,isw_assignment a ")
		.append("     ,isw_assignment_time at ")
		.append("where ir.incident_id = " + incidentId + " ")
		.append("and ir.resource_id = " + resourceId + " " )
		.append("and wp.incident_resource_id = ir.id " )
		.append("and wpa.work_period_id = wp.id " )
		.append("and a.id = wpa.assignment_id " )
		.append("and at.assignment_id = a.id ")
		.append("and (")
		.append("     at.id in (")
		.append("       select assignment_time_id from isw_assign_time_post ")
		.append("     ) ")
		.append("     or " )
		.append("     a.id in (")
		.append("        select assignment_id from isw_time_assign_adjust ")
		.append("     ) ")
		.append(") ");

		return sql.toString();
	}

	public static String getSubordinateActualsCountQueryOra(Long incidentId, Long resourceId) {
		StringBuffer sql = new StringBuffer();
		sql.append("")
		.append("select count(id) ")
		.append("from isw_resource ")
		.append("where id in ( ")
		.append("	select r.id ")
		.append("	from isw_resource r ")
		.append("   where r.id in ( ")
		.append("		select distinct(ir.resource_id)  ")
		.append("		from isw_incident_resource ir ")
		.append("			, isw_work_period wp ")
		.append("			, isw_work_period_assignment wpa ")
		.append("			, isw_assignment a ")
		.append("			, isw_assignment_time at ")
		.append("		where ir.incident_id = " + incidentId + " ")
		.append("		and wp.incident_resource_id = ir.id ")
		.append("		and wpa.work_period_id = wp.id ")
		.append("		and a.id = wpa.assignment_id ")
		.append("		and at.assignment_id = a.id ")
		.append("		and  ")
		.append("		(       ")  
		.append("			at.id in ( ")
		.append("				select assignment_time_id ")
		.append("				from isw_assign_time_post ")
		.append("			) ")
		.append("			or ")
		.append("			a.id in ( ")
		.append("				select assignment_id ")
		.append("				from isw_time_assign_adjust ")            
		.append("		) ")
		.append("	) ") 
		.append(") ")
		.append("connect by prior id=parent_resource_id ")
		.append("start with id = " + resourceId + " ")
		.append(") ");

		return sql.toString();
	}

	public static String getSubordinateActualsCountQueryPg(Long incidentId, Long resourceId) {
		StringBuffer sql = new StringBuffer();

		sql.append("")
		.append("select count(r.id) ")
		.append("from isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_assignment_time at ")
		.append(", isw_resource r ")
		.append("where ir.incident_id = "+incidentId+" ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and at.assignment_id = a.id ")
		.append("and ir.resource_id = r.id ")
		.append("and r.id in ( ")
		.append("	with recursive n as ( ")
		.append("		select id,parent_resource_id ")
		.append("		from isw_resource ")
		.append("		where id in (select resource_id from isw_incident_resource where incident_id = "+incidentId+") ")
		.append("		and id =  " + resourceId + " ")
		.append("		union  ")
		.append("		select r2.id, r2.parent_resource_id ")
		.append("		from n join isw_resource r2 on n.id=r2.parent_resource_id ")
		.append("		where r2.id in (select resource_id from isw_incident_resource where incident_id = "+incidentId+") ")
		.append("		and r2.id !=  " + resourceId + " ")
		.append("	) select id from n where n.id != " + resourceId + " ")
		.append(") ")
		.append("and ( ")
		.append("	at.id in ( ")
		.append("		select assignment_time_id ")
		.append("		from isw_assign_time_post ")
		.append("	) ")
		.append("	or ")
		.append("	a.id in ( ")
		.append("		select assignment_id ")
		.append("		from isw_time_assign_adjust ")
		.append("	) ")
		.append(") ");

		return sql.toString();
	}

	public static String getResourceCostExceptionQuery(Long irid) {
		StringBuffer sql = new StringBuffer();

		sql.append("select ")
			.append("		case  ")
			.append("			when (wp.ci_check_in_date is null and cd.assign_date is null) then 'Missing Check-In/Assign Date' ")
			//.append("			when cd.assign_date is null then 'Missing Check-In/Assign Date' ")
			.append("			when ag.agency_code is null and (atp1.postcount < 1 and taa1.adjcount < 1) then 'Missing Agency' ")
			.append("			when a.assignment_status = 'F' then 'Status is F' ")
			.append("			else ''  ")
			.append("		end as costexception ")
			.append("	from isw_incident_resource ir ")
			.append("   , isw_cost_data cd ")
			.append("   , isw_resource r ")
			.append("          left join iswl_agency ag on r.agency_id = ag.id ")
			.append("   , isw_work_period wp ")
			.append("   , isw_work_period_assignment wpa ")
			.append("   , isw_assignment a ")
			.append("	, ( ")
	        .append("    	select count(atp.id) as postcount ")
	        .append("    	from isw_assign_time_post atp ")
	        .append("    	where atp.assignment_time_id = ")
	        .append("        ( ")
	        .append("            select at.id  ")
	        .append("            from isw_assignment_time at ")
	        .append("                 , isw_assignment a ")
	        .append("                 , isw_work_period_assignment wpa ")
	        .append("                 , isw_work_period wp ")
	        .append("            where at.assignment_id = a.id ")
	        .append("            and a.id = wpa.assignment_id ")
	        .append("            and wpa.work_period_id = wp.id ")
	        .append("            and wp.incident_resource_id = " + irid + " ")
	        .append("        ) ")
	        .append("	) atp1 ")
	        .append("	, ( ")
	        .append("		select count(taa.id) as adjcount ")
	        .append("		from isw_time_assign_adjust taa ")
	        .append("		where taa.assignment_id = ")
	        .append("		( ")
	        .append(			"select a.id  ")
	        .append("            from isw_assignment a ")
			.append("           	 , isw_work_period_assignment wpa ")
			.append("                , isw_work_period wp ")
	        .append("            where a.id = wpa.assignment_id ")
	        .append("    		 and wpa.work_period_id = wp.id ")
	        .append("            and wp.incident_resource_id = " + irid + " ")
			.append("       ) ")
			.append("   ) taa1 ")
			.append("where ir.id = " + irid + " ")
			.append("and cd.id = ir.cost_data_id ")
			.append("and r.id = ir.resource_id ")
			.append("and wp.incident_resource_id = ir.id ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ")
			.append("and a.end_date is null ");

		return sql.toString();
	}
	
	public static String getDailyCostQuery(Long incidentResourceId, Long incidentResourceOtherId, Date startDate) {
		StringBuffer sql = new StringBuffer();

		sql.append("select id as id ")
	       .append(", accrual_code_id as accrualCodeId ")
	       .append(", incident_shift_id as incidentShiftId ")
	       .append(", cost_group_id as costGroupId ")
	       .append(", incident_account_code_id as incidentAccountCodeId ")
	       .append(", incident_resource_id as incidentResourceId ")
	       .append(", incident_resource_other_id as incidentResourceOtherId ")
	       .append(", resource_cost_type as resourceCostType ")
	       .append(", activity_date as activityDate ")
	       .append(", rate_type as rateType ")
	       .append(", units as units ")
	       .append(", unit_cost_amount as unitCostAmount ")
	       .append(", adjustment_amount as adjustmentAmount ")
	       .append(", cost_level as costLevel ")
	       .append(", is_locked as isLocked ")
	       .append(", is_flowdown as isFlowdown ")
	       .append(", primary_total_amount as primaryTotalAmount ")
	       .append(", subordinate_total_amount as subordinateTotalAmount ")
	       .append(", total_cost_amount as totalCostAmount ")
	       .append(", aircraft_cost_amount as aircraftCostAmount ")
	       .append(", flight_hours as flightHours ")
	       .append(", number_of_loads as numberOfLoads ")
	       .append(", water_gallons as waterGallons ")
	       .append(", retardant_gallons as retardantGallons ")
	       .append(", cargo_pounds as cargoPounds ")
	       .append(", number_of_trips as numberOfTrips ")
	       .append(", number_of_passengers as numberOfPassengers ")
	       .append("from isw_inc_res_daily_cost ");
			if(LongUtility.hasValue(incidentResourceId))
				sql.append("where incident_resource_id = "+incidentResourceId+" ");
			else
				sql.append("where incident_resource_other_id = "+incidentResourceOtherId+" ");

			if(DateUtil.hasValue(startDate)){
				sql.append("and to_date(to_char(activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");
			}
			
	       sql.append("order by activity_date asc ");
		
		return sql.toString();
		
	}

	public static String getContractorRatesQuery(Long contractorPayInfoId) {
		StringBuffer sql = new StringBuffer();

		sql.append("select id as id ")
	       .append(", rate_type as rateType ")
	       .append(", unit_of_measure as unitOfMeasure ")
	       .append(", rate_amount as rateAmount ")
	       .append(", guarantee_amount as guaranteeAmount ")
	       .append("from isw_contractor_rate ")
	       .append("where id in ( ")
	       .append("	select contractor_rate_id ")
	       .append("	from isw_contr_payinfo_rate ")
	       .append("	where contractor_pay_info_id = " + contractorPayInfoId + " ")
	       .append(") ");
		
		return sql.toString();
	}
}
