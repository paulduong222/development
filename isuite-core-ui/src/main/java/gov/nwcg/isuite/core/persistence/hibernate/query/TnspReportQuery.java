package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;


public class TnspReportQuery {
	
	public static String getTrainingAssignmentList(TrainingAssignmentsListReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ")
		   .append("  r.last_name || ', ' || r.first_name as traineeName ")
		   .append("  ,a.request_number as traineeRequestNumber ")
		   .append("  ,k.code as traineeItemCode ")
		   .append("  ,d.description as section ")
		   .append("  ,ag.agency_code as traineeAgencyCode ")
		   .append("  ,o.unit_code as traineeUnitCode ")
		   .append("  ,case when rt.start_date is null then '' else to_char(rt.start_date,'MM/DD/YYYY') end as traineeStart ")
		   .append("  ,case when rt.end_date is null then '' else to_char(rt.end_date,'MM/DD/YYYY') end as traineeEnd ")
		   .append("  ,recom.code as recommend ")
		   .append("  ,'' || rt.ptb_percentage as ptb ");
		if(isOracle){
	        sql.append(", ( ")
	           //.append("     select wm_concat(rtt_r.first_Name ||' ' || rtt_r.last_name) ")
	           .append("     select wm_concat(rtt.resource_name) ")
	           .append("     from isw_rsc_training_trainer rtt  ")
	           .append("         left join isw_incident_resource rtt_ir on rtt.incident_resource_id = rtt_ir.id ")
	           .append("         left join isw_resource rtt_r on rtt_ir.resource_id = rtt_r.id ")
	           .append("     where rtt.resource_training_id = rt.id ")
	           .append("   ) as trainerName ");
		}else{ 
			//sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT (rtt_r.first_Name ||' ' || rtt_r.last_name) FROM isw_rsc_training_trainer rtt " +
			sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT (rtt.resource_name) FROM isw_rsc_training_trainer rtt " +
					"  left join isw_incident_resource rtt_ir on rtt.incident_resource_id = rtt_ir.id " +
					"  left join isw_resource rtt_r on rtt_ir.resource_id = rtt_r.id " +
					" where rtt.resource_training_id = rt.id),',') as ARRAY_TO_STRING) AS trainerName ");
		}
		   sql.append("from isw_resource_training rt ")
		   .append("          left join iswl_recommendation recom on rt.recommendation_id = recom.id ")
		   .append("        left join iswl_kind k on rt.kind_id = k.id ")
		   .append("        left join iswl_department d on k.department_id = d.id ")
		   .append("     , isw_incident_resource ir")
		   .append("     , isw_work_period wp")
		   .append("     , isw_work_period_assignment wpa")
		   .append("     , isw_assignment a")
		   .append("     , isw_resource r ")
		   .append("        left join iswl_agency ag on r.agency_id = ag.id ")
		   .append("        left join isw_organization o on r.organization_id = o.id ")
		   .append("where rt.incident_resource_id = ir.id ")
		   .append("and r.id = ir.resource_id ")
		   .append("and wp.incident_resource_id = ir.id ")
		   .append("and wpa.work_period_id = wp.id ")
		   .append("and a.id = wpa.assignment_id ")
		   .append("and a.end_date is null ")
		   ;
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			sql.append("and ir.incident_id = " + filter.getIncidentId() + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		
		String sort="order by ";		
		if(CollectionUtility.hasValue(filter.getSorts())){
			int cnt=0;
			for(String s : filter.getSorts()){
				if(cnt>0)sort=sort+",";
				if(s.equalsIgnoreCase("resourceName"))
					sort=sort+"r.last_name, r.first_name ";
				if(s.equalsIgnoreCase("requestNumber"))
					sort=sort+"sortrequestnumber(a.request_number) ";
				//if(s.equalsIgnoreCase("traineeAssignment"))
				//	sort=sort+"r.last_name, r.first_name";
				if(s.equalsIgnoreCase("agencyCode"))
					sort=sort+"ag.agency_code ";
				if(s.equalsIgnoreCase("itemCode"))
					sort=sort+"k.code ";
				if(s.equalsIgnoreCase("unitId"))
					sort=sort+"o.unit_code ";
				if(s.equalsIgnoreCase("assignmentStatus"))
					sort=sort+"a.assignment_status ";
				if(s.equalsIgnoreCase("assignmentStartDate"))
					sort=sort+"rt.start_date ";
				if(s.equalsIgnoreCase("assignmentEndDate"))
					sort=sort+"rt.end_date ";
				if(s.equalsIgnoreCase("trainee"))
					sort=sort+"a.is_training ";
				cnt++;
			}
		}else{
			sort=sort+" r.last_name, r.first_name ";
		}
		sql.append(sort);
		return sql.toString();
	}
	
	public static String getTrainingDataQuery(Long rtId, Long trainerId, Long tnspId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		.append("select r.last_name || ', ' || r.first_name as traineeName ")
        .append(", a.request_number as requestNumber ")
        .append(", k.code as traineeItemCode ")
        .append(", k.description as traineeItemCodeDesc ")
        .append(", d.description as traineeSection ")
        .append(", rt.is_initial_assignment as traineeIA ")
        .append(", ag.agency_code as traineeAgency ")
        .append(", org.unit_code as traineeUnitCode ")
        .append(", org.organization_name as traineeUnitDesc ")
        .append(", case when rt.start_date is null then '' else to_char(rt.start_date,'MM/DD/YYYY') end as traineeAssnStart ")
        .append(", case when rt.end_date is null then '' else to_char(rt.end_date,'MM/DD/YYYY') end as traineeAssnEnd ")
        .append(", case when rt.is_valid_red_card = 'N' then 'NO' else 'YES' end as traineeRedCard ")
        .append(", case when rt.incident_task_book = 'Y' then 'NO' else 'YES' end as traineeCurrentTaskBook ")
        .append(", case when rt.incident_task_book = 'Y' then 'YES' else 'NO' end as traineeIncidentTaskBook ")
        .append(", rt.is_fs_priority_trainee as traineePriority ")
        .append(", case when rt.priority_program_id is null then '' else pp.code end as traineePriorityProgram ")
        .append(", huc.contact_name as traineeHUName ")
        .append(", huorg.unit_code as traineeHUCode ")
        .append(", huorg.organization_name as traineeHUDesc ")
        .append(", huaddr.address_line_1 as traineeHUAddress ")
        .append(", huaddr.city as traineeHUCity ")
        .append(", huccs.cs_abbreviation as traineeHUState ")
        .append(", huaddr.postal_code as traineeHUZip ")
        .append(", huc.phone as traineeHUPhone ")
        .append(", huc.email as traineeHUEmail ")
        .append(", rto1.objective as traineeGoal1 ")
        .append(", rto2.objective as traineeGoal2 ")
        .append(", rto3.objective as traineeGoal3 ")
        .append(", rtt.resource_name as trainerName ")
        .append(", rtt.reqest_number as trainerRequestNumber ")
        .append(", rttorg.unit_code as trainerUnit ")
        .append(", rttorg.organization_name as trainerUnitDesc ")
        .append(", rttkind.code as trainerItemCode ")
        .append(", rttkind.description as trainerItemDesc ")
        .append(", rttaddr.address_line_1 as trainerAddress ")
        .append(", rttaddr.city as trainerCity ")
        .append(", rttcs.cs_abbreviation as trainerState ")
        .append(", rttaddr.postal_code as trainerZip ")
        .append(", rtt.phone as trainerPhone ")
        .append(", rtt.email as trainerEmail ")
        .append(", rtrecom.code as trainerRecommend ")
        .append(", '' || rt.ptb_percentage as trainerPtbProgress ")
        .append(", i.incident_name as incidentName ")
        .append(", iorg.unit_code || '-' || i.nbr as incidentNumber ")
        .append(", evt.event_type_code as incidentType ")
        .append(", cx2.code as incidentComplexity ")
        .append(", '' || rt.number_of_acres as incidentAcres ");
		if(isOracle){
			sql.append(",( select case when count(rtft.resource_training_id) > 0 then wm_concat(ft.code) ")
	           .append("      	else null end ")
	           .append("   from isw_rsc_training_fuel_type rtft ")
	           .append("left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id ")
	           .append("where rtft.resource_training_id = rt.id ")
	           .append(") as incidentFuelType ");
		}else{
			sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT ft.code FROM isw_rsc_training_fuel_type rtft " +
					"  left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id " +
					" where rtft.resource_training_id = rt.id),',') as ARRAY_TO_STRING) AS incidentFuelType ");
		}
        sql.append(", tbl1.tnspName as tnspFullName ")
        .append(", tbl1.tnspAgency as tnspAgency ")
        .append(", tbl1.tnspUnit as tnspUnit ")
        .append(", tbl1.tnspUnitDesc as tnspUnitDescription ")
        .append(", tbl1.tnspPhone as tnspPhone ")
        .append(", tbl1.tnspEmail as tnspEmail ")
        .append("from isw_resource_training rt ")
        .append("    left join iswl_priority_program pp on rt.priority_program_id = pp.id ")
        .append("    left join isw_rsc_training_objective rto1 on (rt.id = rto1.resource_training_id and rto1.position_num=1) ")
        .append("    left join isw_rsc_training_objective rto2 on (rt.id = rto2.resource_training_id and rto2.position_num=2) ")
        .append("    left join isw_rsc_training_objective rto3 on (rt.id = rto3.resource_training_id and rto3.position_num=3) ")
        .append("    left join isw_rsc_training_trainer rtt on (rt.id = rtt.resource_training_id and rtt.id="+trainerId+") ")
        .append("    left join isw_organization rttorg on rtt.unit_id = rttorg.id ")
        .append("    left join iswl_kind rttkind on rtt.kind_id = rttkind.id ")
        .append("    left join isw_address rttaddr on rtt.address_id = rttaddr.id ")
        .append("    left join iswl_country_subdivision rttcs on rttaddr.country_subdivision_id = rttcs.id ")
        .append("    left join iswl_recommendation rtrecom on rt.recommendation_id = rtrecom.id ")
        .append("    left join iswl_complexity cx2 on rt.complexity_id = cx2.id ")
        .append(", isw_incident_resource ir ")
        .append(" 	 left join isw_training_settings ts on ir.incident_id = ts.incident_id ")
        .append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append("    left join isw_resource_home_unit_contact huc on ir.id = huc.incident_resource_id ")
        .append("    left join isw_organization huorg on huc.unit_id = huorg.id ")
        .append("    left join isw_address huaddr on huc.address_id = huaddr.id ")
        .append("    left join iswl_country_subdivision huccs on huaddr.country_subdivision_id = huccs.id ")
        .append(", isw_work_period wp ")
        .append(", isw_work_period_assignment wpa ")
        .append(", isw_assignment a ")
        .append(", iswl_kind k ")
        .append("    left join iswl_department d on k.department_id = d.id ")
        .append(", isw_resource r ")
        .append(", iswl_agency ag ")
        .append(", isw_organization org ")
        //.append(", isw_training_settings ts ")
        //.append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append(", isw_incident i ")
        .append("    left join isw_organization iorg on i.unit_id = iorg.id ")
        .append("    left join (")
        .append("      select case when tc.id is not null then tc_r.last_name || ', ' || tc_r.first_name else ' ' end as tnspName ")
        .append("              , tc_ir.id as tnspIncidentResourceId ")
        .append("              , tc_ir.incident_id as incidentid ")
        .append("              , tc.phone as tnspPhone ")
        .append("              , tc.email as tnspEmail ")
        .append("              , tc_ag.agency_code as tnspAgency ")
        .append("              , tc_org.unit_code as tnspUnit ")
        .append("              , tc_org.organization_name as tnspUnitDesc ")
        .append("      from isw_incident_resource tc_ir ")
        .append("             left join isw_training_contact tc on tc_ir.id = tc.incident_resource_id ")
        .append("             left join isw_resource tc_r on tc_ir.resource_id = tc_r.id ")
        .append("             left join iswl_agency tc_ag on tc_r.agency_id = tc_ag.id ")
        .append("             left join isw_organization tc_org on tc_r.organization_id = tc_org.id ")
        .append("      where tc.id="+tnspId+" ")  //is_active='Y'
        .append("    ) tbl1 on i.id = tbl1.incidentid ")
        .append(", iswl_event_type evt ")
		.append("where rt.id = " + rtId + " ")
		.append("and ir.id = rt.incident_resource_id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = rt.kind_id ")
		.append("and ag.id = r.agency_id ")
		.append("and org.id = r.organization_id ")
		//.append("and ts.incident_id = ir.incident_id ")
		.append("and i.id = ir.incident_id ")
		.append("and evt.id = i.event_type_id ")
		;
		
		return sql.toString();
	}
	
	public static String getTrainingEvaluationDataQuery(Long rtId, Long rttId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		.append("select r.last_name || ', ' || r.first_name as traineeName ");
		
		if(isOracle){
			sql.append(", to_char(TRUNC(rt.end_date) - TRUNC(rt.start_date)) as field1 ");
		}else {
			sql.append(", to_char(date_part('days', (rt.end_date - rt.start_date)), '999') as field1 ");
		}
        
        sql.append(", a.request_number as requestNumber ")
        .append(", case when rt.start_date is null then '' else to_char(rt.start_date,'MM/DD/YYYY') end as traineeAssnStart ")
        .append(", case when rt.end_date is null then '' else to_char(rt.end_date,'MM/DD/YYYY') end as traineeAssnEnd ")
        .append(", k.code as traineeItemCode ")
        .append(", k.description as traineeItemCodeDesc ")
        .append(", ag.agency_code as traineeAgency ")
        .append(", org.unit_code as traineeUnitCode ")
        .append(", org.organization_name as traineeUnitDesc ")
        .append(", huc.contact_name as traineeHUName ")
        .append(", huorg.unit_code as traineeHUCode ")
        .append(", huorg.organization_name as traineeHUDesc ")
        .append(", huaddr.address_line_1 as traineeHUAddress ")
        .append(", huaddr.city as traineeHUCity ")
        .append(", huccs.cs_abbreviation as traineeHUState ")
        .append(", huaddr.postal_code as traineeHUZip ")
        .append(", huc.phone as traineeHUPhone ")
        .append(", huc.email as traineeHUEmail ")
        .append(", rtt.resource_name as trainerName ")
        .append(", rtt.reqest_number as trainerRequestNumber ")
        .append(", rttorg.unit_code as trainerUnit ")
        .append(", rttorg.organization_name as trainerUnitDesc ")
        .append(", rttkind.code as trainerItemCode ")
        .append(", rttkind.description as trainerItemDesc ")
        .append(", rttaddr.address_line_1 as trainerAddress ")
        .append(", rttaddr.city as trainerCity ")
        .append(", rttcs.cs_abbreviation as trainerState ")
        .append(", rttaddr.postal_code as trainerZip ")
        .append(", rtt.phone as trainerPhone ")
        .append(", rtt.email as trainerEmail ")
        .append(", rtrecom.code as trainerRecommend ")
        .append(", i.incident_name as incidentName ")
        .append(", iorg.unit_code || '-' || i.nbr as incidentNumber ")
        .append(", evt.event_type_code as incidentType ")
        .append(", cx2.code as incidentComplexity ")
        .append(", '' || rt.number_of_acres as incidentAcres ");
		if(isOracle){
			sql.append(",( select case when count(rtft.resource_training_id) > 0 then wm_concat(ft.code) ")
	           .append("      	else null end ")
	           .append("   from isw_rsc_training_fuel_type rtft ")
	           .append("left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id ")
	           .append("where rtft.resource_training_id = rt.id ")
	           .append(") as incidentFuelType ");
		}else{
			sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT ft.code FROM isw_rsc_training_fuel_type rtft " +
					"  left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id " +
					" where rtft.resource_training_id = rt.id),',') as ARRAY_TO_STRING) AS incidentFuelType ");
		}
        sql.append(", rt.tnsp_comments as trainerComments ")
        .append("from isw_resource_training rt ")
        .append("    left join iswl_priority_program pp on rt.priority_program_id = pp.id ")
        .append("    left join isw_rsc_training_objective rto1 on (rt.id = rto1.resource_training_id and rto1.position_num=1) ")
        .append("    left join isw_rsc_training_objective rto2 on (rt.id = rto2.resource_training_id and rto2.position_num=2) ")
        .append("    left join isw_rsc_training_objective rto3 on (rt.id = rto3.resource_training_id and rto3.position_num=3) ")
        .append("    left join isw_rsc_training_trainer rtt on (rt.id = rtt.resource_training_id and rtt.id = "+rttId+") ")
        .append("    left join isw_organization rttorg on rtt.unit_id = rttorg.id ")
        .append("    left join iswl_kind rttkind on rtt.kind_id = rttkind.id ")
        .append("    left join isw_address rttaddr on rtt.address_id = rttaddr.id ")
        .append("    left join iswl_country_subdivision rttcs on rttaddr.country_subdivision_id = rttcs.id ")
        .append("    left join iswl_recommendation rtrecom on rt.recommendation_id = rtrecom.id ")
        .append("    left join iswl_complexity cx2 on rt.complexity_id = cx2.id ")
        .append(", isw_incident_resource ir ")
        .append("    left join isw_training_settings ts on ir.incident_id = ts.incident_id ")
        .append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append("    left join isw_resource_home_unit_contact huc on ir.id = huc.incident_resource_id ")
        .append("    left join isw_organization huorg on huc.unit_id = huorg.id ")
        .append("    left join isw_address huaddr on huc.address_id = huaddr.id ")
        .append("    left join iswl_country_subdivision huccs on huaddr.country_subdivision_id = huccs.id ")
        .append(", isw_work_period wp ")
        .append(", isw_work_period_assignment wpa ")
        .append(", isw_assignment a ")
        .append(", iswl_kind k ")
        .append("    left join iswl_department d on k.department_id = d.id ")
        .append(", isw_resource r ")
        .append(", iswl_agency ag ")
        .append(", isw_organization org ")
        //.append(", isw_training_settings ts ")
        //.append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append(", isw_incident i ")
        .append("    left join isw_organization iorg on i.unit_id = iorg.id ")
        .append(", iswl_event_type evt ")
		.append("where rt.id = " + rtId + " ")
		//.append("and rtt.id = " + rttId + " ")
		.append("and ir.id = rt.incident_resource_id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = rt.kind_id ")
		.append("and ag.id = r.agency_id ")
		.append("and org.id = r.organization_id ")
		//.append("and ts.incident_id = ir.incident_id ")
		.append("and i.id = ir.incident_id ")
		.append("and evt.id = i.event_type_id ")
		;
		
		return sql.toString();
	}

	public static String getTrainingHomeUnitDataQuery(Long rtId, Long tnspId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		.append("select r.last_name || ', ' || r.first_name as traineeName ")
        .append(", a.request_number as requestNumber ")
        .append(", k.code as traineeItemCode ")
        .append(", k.description as traineeItemCodeDesc ")
        .append(", ag.agency_code as traineeAgency ")
        .append(", org.unit_code as traineeUnitCode ")
        .append(", org.organization_name as traineeUnitDesc ")
        .append(", huc.contact_name as traineeHUName ")
        .append(", huorg.unit_code as traineeHUCode ")
        .append(", huorg.organization_name as traineeHUDesc ")
        .append(", huaddr.address_line_1 as traineeHUAddress ")
        .append(", huaddr.city as traineeHUCity ")
        .append(", huccs.cs_abbreviation as traineeHUState ")
        .append(", huaddr.postal_code as traineeHUZip ")
        .append(", huc.phone as traineeHUPhone ")
        .append(", huc.email as traineeHUEmail ")
        .append(", rtrecom.code as trainerRecommend ")
        .append(", i.incident_name as incidentName ")
        .append(", iorg.unit_code || '-' || i.nbr as incidentNumber ")
        .append(", evt.event_type_code as incidentType ")
        .append(", cx2.code as incidentComplexity ")
        .append(", '' || rt.number_of_acres as incidentAcres ");
		if(isOracle){
			sql.append(",( select case when count(rtft.resource_training_id) > 0 then wm_concat(ft.code) ")
	           .append("      	else null end ")
	           .append("   from isw_rsc_training_fuel_type rtft ")
	           .append("left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id ")
	           .append("where rtft.resource_training_id = rt.id ")
	           .append(") as incidentFuelType ");
		}else{
			sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT ft.code FROM isw_rsc_training_fuel_type rtft " +
					"  left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id " +
					" where rtft.resource_training_id = rt.id),',') as ARRAY_TO_STRING) AS incidentFuelType ");
		}
        sql.append(", rt.tnsp_comments as trainerComments ")
        .append("from isw_resource_training rt ")
        .append("    left join iswl_priority_program pp on rt.priority_program_id = pp.id ")
        .append("    left join iswl_recommendation rtrecom on rt.recommendation_id = rtrecom.id ")
        .append("    left join iswl_complexity cx2 on rt.complexity_id = cx2.id ")
        .append(", isw_incident_resource ir ")
        .append("    left join isw_resource_home_unit_contact huc on ir.id = huc.incident_resource_id ")
        .append("    left join isw_organization huorg on huc.unit_id = huorg.id ")
        .append("    left join isw_address huaddr on huc.address_id = huaddr.id ")
        .append("    left join iswl_country_subdivision huccs on huaddr.country_subdivision_id = huccs.id ")
        .append("    left join isw_training_settings ts on ir.incident_id = ts.incident_id ")
        .append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append(", isw_work_period wp ")
        .append(", isw_work_period_assignment wpa ")
        .append(", isw_assignment a ")
        .append(", iswl_kind k ")
        .append("    left join iswl_department d on k.department_id = d.id ")
        .append(", isw_resource r ")
        .append(", iswl_agency ag ")
        .append(", isw_organization org ")
        .append(", isw_incident i ")
        .append("    left join isw_organization iorg on i.unit_id = iorg.id ")
        .append(", iswl_event_type evt ")
		.append("where rt.id = " + rtId + " ")
		.append("and ir.id = rt.incident_resource_id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = rt.kind_id ")
		.append("and ag.id = r.agency_id ")
		.append("and org.id = r.organization_id ")
		.append("and i.id = ir.incident_id ")
		.append("and evt.id = i.event_type_id ")
		;
		
		return sql.toString();
	}

	public static String getExitInterviewDataQuery(Long rtId, Long trainerId, Long tnspId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		.append("select r.last_name || ', ' || r.first_name as traineeName ")
        .append(", a.request_number as requestNumber ")
        .append(", k.code as traineeItemCode ")
        .append(", k.description as traineeItemCodeDesc ")
        .append(", rtt.resource_name as trainerName ")
        .append(", rtt.reqest_number as trainerRequestNumber ")
        .append(", rttorg.unit_code as trainerUnit ")
        .append(", rttorg.organization_name as trainerUnitDesc ")
        .append(", rttkind.code as trainerItemCode ")
        .append(", rttkind.description as trainerItemDesc ")
        .append(", i.incident_name as incidentName ")
        .append(", iorg.unit_code || '-' || i.nbr as incidentNumber ")
        .append(", tbl1.tnspName as tnspFullName " )
        .append("from isw_resource_training rt ");
        
		if(LongUtility.hasValue(trainerId))
        	sql.append("    left join isw_rsc_training_trainer rtt on (rt.id=rtt.resource_training_id and rtt.id = "+trainerId+") ");
        else
        	sql.append("    left join isw_rsc_training_trainer rtt on (rt.id=rtt.resource_training_id and rtt.id = "+trainerId+") ");

        sql.append("    left join isw_organization rttorg on rtt.unit_id = rttorg.id ")
        .append("    left join iswl_kind rttkind on rtt.kind_id = rttkind.id ")
        .append(", isw_incident_resource ir ")
        .append(", isw_work_period wp ")
        .append(", isw_work_period_assignment wpa ")
        .append(", isw_assignment a ")
        .append(", iswl_kind k ")
        .append("    left join iswl_department d on k.department_id = d.id ")
        .append(", isw_resource r ")
        .append(", iswl_agency ag ")
        .append(", isw_organization org ")
        .append(", isw_incident i ")
        .append("    left join isw_organization iorg on i.unit_id = iorg.id ")
        .append("    left join (")
        .append("      select case when tc.id is not null then tc_r.last_name || ', ' || tc_r.first_name else ' ' end as tnspName ")
        .append("              , tc_ir.id as tnspIncidentResourceId ")
        .append("              , tc_ir.incident_id as incidentid ")
        .append("      from isw_incident_resource tc_ir ");
		
        if(LongUtility.hasValue(tnspId))
			sql.append("             left join isw_training_contact tc on (tc_ir.id = tc.incident_resource_id and tc.id = " + tnspId + ") ");
		else
			sql.append("             left join isw_training_contact tc on (tc_ir.id = tc.incident_resource_id and tc.id = " + 0L + ") ");
        
		sql.append("             left join isw_resource tc_r on tc_ir.resource_id = tc_r.id ")
        .append("      where tc.is_active='Y' ")
        .append("    ) tbl1 on i.id = tbl1.incidentid ")
		.append("where rt.id = " + rtId + " ")
		.append("and ir.id = rt.incident_resource_id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = rt.kind_id ")
		.append("and ag.id = r.agency_id ")
		.append("and org.id = r.organization_id ")
		.append("and i.id = ir.incident_id ")
		;
		
		return sql.toString();
	}

	public static String getPerformanceEvalDataQuery(Long rtId, Long rttId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		.append("select r.last_name || ', ' || r.first_name as traineeName ")
        .append(", a.request_number as requestNumber ")
        .append(", k.code as traineeItemCode ")
        .append(", k.description as traineeItemCodeDesc ")
        .append(", d.description as traineeSection ")
        .append(", rt.is_initial_assignment as traineeIA ")
        .append(", ag.agency_code as traineeAgency ")
        .append(", org.unit_code as traineeUnitCode ")
        .append(", org.organization_name as traineeUnitDesc ")
        .append(", case when rt.start_date is null then '' else to_char(rt.start_date,'MM/DD/YYYY') end as traineeAssnStart ")
        .append(", case when rt.end_date is null then '' else to_char(rt.end_date,'MM/DD/YYYY') end as traineeAssnEnd ")
        .append(", rt.is_valid_red_card as traineeRedCard ")
        .append(", case when rt.incident_task_book = 'NO' then 'YES' else 'NO' end as traineeCurrentTaskBook ")
        .append(", case when rt.incident_task_book = 'YES' then 'YES' else 'NO' end as traineeIncidentTaskBook ")
        .append(", rt.is_fs_priority_trainee as traineePriority ")
        .append(", case when rt.priority_program_id is null then '' else pp.code end as traineePriorityProgram ")
        .append(", huc.contact_name as traineeHUName ")
        .append(", huorg.unit_code as traineeHUCode ")
        .append(", huorg.organization_name as traineeHUDesc ")
        .append(", huaddr.address_line_1 as traineeHUAddress ")
        .append(", huaddr.city as traineeHUCity ")
        .append(", huccs.cs_abbreviation as traineeHUState ")
        .append(", huaddr.postal_code as traineeHUZip ")
        .append(", huc.phone as traineeHUPhone ")
        .append(", huc.email as traineeHUEmail ")
        .append(", rto1.objective as traineeGoal1 ")
        .append(", rto2.objective as traineeGoal2 ")
        .append(", rto3.objective as traineeGoal3 ")
        .append(", rtt.resource_name as trainerName ")
        .append(", rtt.reqest_number as trainerRequestNumber ")
        .append(", rttorg.unit_code as trainerUnit ")
        .append(", rttorg.organization_name as trainerUnitDesc ")
        .append(", rttkind.code as trainerItemCode ")
        .append(", rttkind.description as trainerItemDesc ")
        .append(", rttaddr.address_line_1 as trainerAddress ")
        .append(", rttaddr.city as trainerCity ")
        .append(", rttcs.cs_abbreviation as trainerState ")
        .append(", rttaddr.postal_code as trainerZip ")
        .append(", rtt.phone as trainerPhone ")
        .append(", rtt.email as trainerEmail ")
        .append(", rtrecom.code as trainerRecommend ")
        .append(", '' || rt.ptb_percentage as trainerPtbProgress ")
        .append(", i.incident_name as incidentName ")
        .append(", iorg.unit_code || '-' || i.nbr as incidentNumber ")
        .append(", evt.event_type_code as incidentType ")
        .append(", cx.code as incidentComplexity ")
        .append(", '' || rt.number_of_acres as incidentAcres ");
		if(isOracle){
			sql.append(",( select case when count(rtft.resource_training_id) > 0 then wm_concat(ft.code) ")
	           .append("      	else null end ")
	           .append("   from isw_rsc_training_fuel_type rtft ")
	           .append("left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id ")
	           .append("where rtft.resource_training_id = rt.id ")
	           .append(") as incidentFuelType ");
		}else{
			sql.append(",(SELECT ARRAY_TO_STRING(ARRAY(SELECT ft.code FROM isw_rsc_training_fuel_type rtft " +
																		"  left join iswl_fuel_type ft on rtft.fuel_type_id = ft.id " +
																		" where rtft.resource_training_id = rt.id),',') as ARRAY_TO_STRING) AS incidentFuelType ");
		}
        //sql.append(", tbl1.tnspName as tnspFullName ")
        //.append(", tbl1.tnspAgency as tnspAgency ")
        //.append(", tbl1.tnspUnit as tnspUnit ")
        //.append(", tbl1.tnspUnitDesc as tnspUnitDescription ")
        //.append(", tbl1.tnspPhone as tnspPhone ")
        //.append(", tbl1.tnspEmail as tnspEmail ")
        sql.append("from isw_resource_training rt ")
        .append("    left join iswl_priority_program pp on rt.priority_program_id = pp.id ")
        .append("    left join isw_rsc_training_objective rto1 on (rt.id = rto1.resource_training_id and rto1.position_num=1) ")
        .append("    left join isw_rsc_training_objective rto2 on (rt.id = rto2.resource_training_id and rto2.position_num=2) ")
        .append("    left join isw_rsc_training_objective rto3 on (rt.id = rto3.resource_training_id and rto3.position_num=3) ")
        .append("    left join isw_rsc_training_trainer rtt on (rt.id = rtt.resource_training_id and rtt.id = " + rttId + ") ")
        .append("    left join isw_organization rttorg on rtt.unit_id = rttorg.id ")
        .append("    left join iswl_kind rttkind on rtt.kind_id = rttkind.id ")
        .append("    left join isw_address rttaddr on rtt.address_id = rttaddr.id ")
        .append("    left join iswl_country_subdivision rttcs on rttaddr.country_subdivision_id = rttcs.id ")
        .append("    left join iswl_recommendation rtrecom on rt.recommendation_id = rtrecom.id ")
        .append(", isw_incident_resource ir ")
        .append("    left join isw_training_settings ts on ir.incident_id = ts.incident_id ")
        .append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append("    left join isw_resource_home_unit_contact huc on ir.id = huc.incident_resource_id ")
        .append("    left join isw_organization huorg on huc.unit_id = huorg.id ")
        .append("    left join isw_address huaddr on huc.address_id = huaddr.id ")
        .append("    left join iswl_country_subdivision huccs on huaddr.country_subdivision_id = huccs.id ")
        .append(", isw_work_period wp ")
        .append(", isw_work_period_assignment wpa ")
        .append(", isw_assignment a ")
        .append(", iswl_kind k ")
        .append("    left join iswl_department d on k.department_id = d.id ")
        .append(", isw_resource r ")
        .append(", iswl_agency ag ")
        .append(", isw_organization org ")
        //.append(", isw_training_settings ts ")
        //.append("    left join iswl_complexity cx on ts.complexity_id = cx.id ")
        .append(", isw_incident i ")
        .append("    left join isw_organization iorg on i.unit_id = iorg.id ")
        .append(", iswl_event_type evt ")
		.append("where rt.id = " + rtId + " ")
		.append("and ir.id = rt.incident_resource_id ")
		.append("and r.id = ir.resource_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = rt.kind_id ")
		.append("and ag.id = r.agency_id ")
		.append("and org.id = r.organization_id ")
		//.append("and ts.incident_id = ir.incident_id ")
		.append("and i.id = ir.incident_id ")
		.append("and evt.id = i.event_type_id ")
		;
		
		return sql.toString();
	}

	public static String getTrainingSpecialistQuery(Long incidentId, Long groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.last_name || ', ' || r.first_name as tnspName ")
	       .append(", ag.agency_code as tnspAgency ")
	       .append(", org.unit_code as tnspUnit ")
	       .append(", tc.phone as tnspPhone ")
	       .append(", tc.email as tnspEmail ")
	       .append("from isw_training_contact tc ")
	       .append(" left join isw_incident_resource ir on tc.incident_resource_id = ir.id ")
	       .append(" left join isw_resource r on ir.resource_id = r.id ")
	       .append(" left join iswl_agency ag on r.agency_id = ag.id ")
	       .append(" left join isw_organization org on r.organization_id = org.id ");
		if(LongUtility.hasValue(incidentId)){
			sql.append("where tc.incident_resource_id in (select id from isw_incident_resource where incident_id = "+incidentId+") ");
		}else{
			sql.append("where tc.incident_resource_id in ("+
						"  select id from isw_incident_resource where incident_id in ("+
						"      select incident_id from isw_incident_group_incident where incident_group_id="+groupId+")) ");
		}
		sql.append("and tc.is_active='Y' ");
		
		return sql.toString();
	}

	public static String getTrainingSpecialist2Query(Long incidentId, Long groupId,Long rtId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tc.id as id ")
			.append(",r.last_name || ', ' || r.first_name as tnspName ")
	       .append(", ag.agency_code as tnspAgency ")
	       .append(", org.unit_code as tnspUnit ")
	       .append(", tc.phone as tnspPhone ")
	       .append(", tc.email as tnspEmail  ")
	       .append("from isw_training_contact tc ")
	       .append("     , isw_incident_resource ir ")
	       .append(" 			left join isw_resource r on ir.resource_id = r.id ")
	       .append(" 			left join iswl_agency ag on r.agency_id = ag.id ")
	       .append(" 			left join isw_organization org on r.organization_id = org.id ")
		   .append("where tc.incident_resource_id = ir.id ");
		if(LongUtility.hasValue(incidentId)){
			sql.append("and ir.incident_id = " + incidentId + " " );
		}else if(LongUtility.hasValue(groupId)){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + groupId + ") ");
		}else if(LongUtility.hasValue(rtId)){
			sql.append("and ir.id in (select incident_resource_id from isw_resource_training where id = " + rtId + ") ");
		}
		
		sql.append("and tc.is_active='Y' ");
		
		return sql.toString();
	}

	public static String getTrainingSpecialist3Query(Long tnspId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tc.id as id ")
			.append(",r.last_name || ', ' || r.first_name as tnspName ")
	       .append(", ag.agency_code as tnspAgency ")
	       .append(", org.unit_code as tnspUnit ")
	       .append(", tc.phone as tnspPhone ")
	       .append(", tc.email as tnspEmail  ")
	       .append("from isw_training_contact tc ")
	       .append("      		left join isw_incident_resource ir on tc.incident_resource_id = ir.id ")
	       .append(" 			left join isw_resource r on ir.resource_id = r.id ")
	       .append(" 			left join iswl_agency ag on r.agency_id = ag.id ")
	       .append(" 			left join isw_organization org on r.organization_id = org.id ")
		   .append("where tc.id = " + tnspId + " ");
		
		return sql.toString();
	}

	public static String getTrainingSummaryQuery3(IncidentTrainingSummaryReportFilter filter, Boolean isOracle) {
		StringBuffer sql=new StringBuffer()
			
		.append("select sum(tbl1.code1Count) as code1Count ")
        .append("        ,sum(tbl1.code2Count) as code2Count")
        .append("        ,sum(tbl1.code3Count) as code3Count ")
        .append("        ,sum(tbl1.code4Count) as code4Count ")
		.append("        ,sum(tbl1.code5Count) as code5Count ")
		//.append("        ,tbl3.tnspResourceCount as trainingCount ")
		//.append("        ,tbl2.incTrainingCount as incTrainingCount ")
		.append("from ( ")
		.append("    select case ")
		.append("            when r.code is not null and r.code='1' then 1 else 0 end as code1Count ")
		.append("           ,case ")
		.append("            when r.code is not null and r.code='2' then 1 else 0 end as code2Count ")
		.append("           ,case ")
		.append("            when r.code is not null and r.code='3' then 1 else 0 end as code3Count ")
		.append("           ,case ")
		.append("            when r.code is not null and r.code='4' then 1 else 0 end as code4Count ")
		.append("           ,case ")
		.append("            when r.code is not null and r.code='5' then 1 else 0 end as code5Count ")
		.append("           , 1 as trainingCount ")
		.append("from isw_resource_training rt ")
		.append("            left join iswl_recommendation r on rt.recommendation_id = r.id ")
		.append("     ,isw_incident_resource ir ")
		.append("where rt.incident_resource_id = ir.id ");
		if(LongUtility.hasValue(filter.getIncidentId())){
			sql.append("and ir.incident_id = " + filter.getIncidentId() + " " );
		}else{
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		}
		if(StringUtility.hasValue(filter.getStartDateVo().dateString)
				&& StringUtility.hasValue(filter.getEndDateVo().dateString)){
			String startDate=filter.getStartDateVo().dateString;
			String endDate=filter.getEndDateVo().dateString;
			sql.append("                and (to_date(to_char(rt.start_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+endDate+"','MM/DD/YYYY') )");
			sql.append("                and (")
			   .append(" to_date(to_char(rt.end_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+startDate+"','MM/DD/YYYY') ")
			   .append(" or rt.end_date is null ")
			   //.append(" or to_date(to_char(rt.end_date,'MM/DD/YYYY'),'MM/DD/YYYY') between to_date('"+startDate+"','MM/DD/YYYY') and to_date('"+endDate+"','MM/DD/YYYY') ")
			   .append(") ");
		}
		sql.append(") tbl1 ")
		;
		
		return sql.toString();
	}
	
	public static String getTrainingSummaryQuery3Pt2(IncidentTrainingSummaryReportFilter filter, Boolean isOracle) {
		StringBuffer sql=new StringBuffer();
		String startDate=filter.getStartDateVo().dateString;
		String endDate=filter.getEndDateVo().dateString;
			
	   sql.append("    select count(distinct(ir3.id)) as  tnspResourceCount ")
		   .append("    from isw_incident_resource ir3, isw_work_period wp3, isw_work_period_assignment wpa3, isw_assignment a3  ")
		   .append("    where wp3.incident_resource_id = ir3.id ")
		   .append("    and wpa3.work_period_id = wp3.id ")
		   .append("    and a3.id = wpa3.assignment_id ")
		   .append(" and (")
		   .append("       (ir3.id in (select incident_resource_id from isw_resource_training where start_date <= to_date('"+endDate+"','MM/DD/YYYY')) ");
		   sql.append("     or ")
		   .append("       ( ");
		   if(isOracle==true)
				sql.append("    a3.is_training=1 ");
			else
				sql.append("    a3.is_training=true ");
			sql.append(" and wp3.ci_check_in_date <= to_date('"+endDate+"','MM/DD/YYYY') ");
			sql.append(" and (");
			sql.append(" wp3.dm_release_date is null ")
			   .append(" or ")
			   .append(" wp3.dm_release_date >= to_date('"+startDate+"','MM/DD/YYYY')");
			sql.append(") ");
		   sql.append("       ) ");
		   sql.append(" ) ");
			if(LongUtility.hasValue(filter.getIncidentId())){
				sql.append("and ir3.incident_id = " + filter.getIncidentId() + " " );
			}else{
				sql.append("and ir3.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
			}
		sql.append(" )  ");

		
		return sql.toString();
	}

	public static String getTrainingSummaryQuery3Pt3(IncidentTrainingSummaryReportFilter filter, Boolean isOracle) {
		StringBuffer sql=new StringBuffer();
		String startDate=filter.getStartDateVo().dateString;
		String endDate=filter.getEndDateVo().dateString;

		sql.append("    select count(ir.id) as incTrainingCount ")
		.append("    from isw_incident_resource ir, isw_work_period wp, isw_work_period_assignment wpa, isw_assignment a  ")
		.append("    where wp.incident_resource_id = ir.id ")
		.append("    and wpa.work_period_id = wp.id ")
		.append("    and a.id = wpa.assignment_id ");
		sql.append(" and wp.ci_check_in_date <= to_date('"+endDate+"','MM/DD/YYYY') ");
		if(isOracle==true)
			sql.append("    and a.is_training=1 ");
		else
			sql.append("    and a.is_training=true ");
		if(LongUtility.hasValue(filter.getIncidentId())){
			sql.append("and ir.incident_id = " + filter.getIncidentId() + " " );
		}else{
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		}
		sql.append(" and (");
		sql.append(" wp.dm_release_date is null ")
		   .append(" or ")
		   .append(" wp.dm_release_date >= to_date('"+startDate+"','MM/DD/YYYY')");
		sql.append(") ");
		
		
		return sql.toString();
	}

	public static String getTrainingSummaryQuery4(IncidentTrainingSummaryReportFilter filter, Boolean isOracle) {
		String startDate=filter.getStartDateVo().dateString;
		String endDate=filter.getEndDateVo().dateString;
		StringBuffer sql=new StringBuffer()
		.append("select distinct(p.code) as programName ")
        .append("        ,count(rt.id) as resourceCount ")
		.append("from iswl_priority_program p ");

		if(StringUtility.hasValue(filter.getStartDateVo().dateString)
				&& StringUtility.hasValue(filter.getEndDateVo().dateString)){
			sql.append("       left join isw_resource_training rt on ("+
					   				"p.id = rt.priority_program_id " +
					   				"and  ( (rt.start_date <= to_date('"+endDate+"','MM/DD/YYYY')) "+
					   				"and (rt.end_date is null or rt.end_date >= to_date('"+startDate+"','MM/DD/YYYY')) ) ) ");
		}else if(StringUtility.hasValue(filter.getStartDateVo().dateString)
					&& !StringUtility.hasValue(filter.getEndDateVo().dateString)){
			sql.append("       left join isw_resource_training rt on ("+
	   				"p.id = rt.priority_program_id " +
	   				"and rt.start_date <= to_date('"+endDate+"','MM/DD/YYYY') ) ");
			
		}else {	
			sql.append("       left join isw_resource_training rt on p.id = rt.priority_program_id ");
		}

		if(LongUtility.hasValue(filter.getIncidentId())){
			sql.append("where (p.incident_id = " + filter.getIncidentId() + " or p.incident_group_id in (select incident_group_id from isw_incident_group_incident where incident_id = "+filter.getIncidentId()+") ) ");
		}else if(LongUtility.hasValue(filter.getIncidentGroupId())){
			sql.append("where (p.incident_group_id = "+filter.getIncidentGroupId() + " or p.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getIncidentGroupId()+") ) ");
		}
		sql.append("group by p.code ")
		.append("order by p.code ");
		
		return sql.toString();
	}

	public static String getTrainingSummaryQuery(IncidentTrainingSummaryReportFilter filter) {
		StringBuffer sql = new StringBuffer()
		.append("select tbl1.agencyCode as agency ")
		.append("        , sum(tbl1.commandCount) as commandCount ")
		.append("        , sum(tbl1.operationsCount) as operationsCount ")
		.append("        , sum(tbl1.plansCount) as plansCount ")
		.append("        , sum(tbl1.logisticsCount) as logisticsCount ")
		.append("        , sum(tbl1.financeCount) as financeCount ")
		.append("        , sum(tbl1.externalCount) as externalCount ")
		.append("from ( ")
		.append("        select case ")
		.append("                when ag.agency_code not in (select agency_code from iswl_agency where agency_type = 'STATE') then ag.agency_code ")
		.append("                when ag.agency_code in (select agency_code from iswl_agency where agency_type = 'STATE') then 'STATE' ")
		.append("               end as agencyCode ")
		.append("               , case when d.description = 'COMMAND' then 1 else 0 end as commandCount ")
		.append("               , case when d.description = 'OPERATIONS' then 1 else 0 end as operationsCount ")
		.append("               , case when d.description = 'PLANS' then 1 else 0 end as plansCount ")
		.append("               , case when d.description = 'LOGISTICS' then 1 else 0 end as logisticsCount ")
		.append("               , case when d.description = 'FINANCE' then 1 else 0 end as financeCount ")
		.append("               , case when d.description = 'EXTERNAL' then 1 else 0 end as externalCount ")
		.append("        from isw_resource_training rt ")
		.append("                left join iswl_kind k on rt.kind_id = k.id ")
		.append("                left join iswl_department d on k.department_id = d.id ")
		.append("             ,isw_incident_resource ir ")
		.append("                left join isw_resource r on ir.resource_id = r.id")
		.append("                left join iswl_agency ag on r.agency_id = ag.id ")
		//.append("                left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		//.append("                left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		//.append("                left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("        where rt.incident_resource_id = ir.id ");
		if(StringUtility.hasValue(filter.getStartDateVo().dateString)
				&& StringUtility.hasValue(filter.getEndDateVo().dateString)){
			String startDate=filter.getStartDateVo().dateString;
			String endDate=filter.getEndDateVo().dateString;
			sql.append("                and (to_date(to_char(rt.start_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+endDate+"','MM/DD/YYYY') )");
			sql.append("                and (")
			   .append(" to_date(to_char(rt.end_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+startDate+"','MM/DD/YYYY') ")
			   .append(" or rt.end_date is null ")
			   //.append(" or to_date(to_char(rt.end_date,'MM/DD/YYYY'),'MM/DD/YYYY') between to_date('"+startDate+"','MM/DD/YYYY') and to_date('"+endDate+"','MM/DD/YYYY') ")
			   .append(") ");
		}
		/*
		if(StringUtility.hasValue(filter.getStartDateVo().dateString)
				&& StringUtility.hasValue(filter.getEndDateVo().dateString)){
			String startDate=filter.getStartDateVo().dateString;
			sql.append("                and (")
			   .append(" rt.end_date >= to_date('"+startDate+"','MM/DD/YYYY') ) ");
		}
		*/
		if(LongUtility.hasValue(filter.getIncidentId())){
			sql.append("and ir.incident_id = " + filter.getIncidentId() + " ");
		}else if(LongUtility.hasValue(filter.getIncidentGroupId())){
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		}
		
		sql.append("    ) tbl1 ")
		.append("group by agencyCode ")
		.append("order by agencyCode ");
	
		return sql.toString();
	}

	public static String getHomeUnitContactLabelQuery(Collection<? extends Object> huIds) {
		String s="";
		for(Object o : huIds){
			if(StringUtility.hasValue(s))
				s=s+",";
			s=s+String.valueOf(o);
		}
		StringBuffer sql = new StringBuffer()
		.append("select hu.contact_name as labelName ")		
		.append("		, addr.address_line_1 as address1 ")
		.append("		, addr.city as city ")
		.append("		, cs.cs_abbreviation as state ")
		.append("		, addr.postal_code as zip ")
		.append("		, org.organization_name as unitDescription ")// newline
		.append("		from isw_resource_home_unit_contact hu ")	//new line
		.append("		left join isw_organization org on hu.unit_id = org.id ")
		.append("		left join isw_address addr on hu.address_id = addr.id ")
		.append("		left join iswl_country_subdivision cs on addr.country_subdivision_id = cs.id ")
		.append("	where hu.id in ("+s+") ");
		return sql.toString();
	}
}
