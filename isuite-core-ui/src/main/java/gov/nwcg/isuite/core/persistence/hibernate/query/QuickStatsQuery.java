package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.QuickStatsNamedParameterEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class QuickStatsQuery {

	public static final String GET_QUICK_STATS_SQL_QUERY = 
		"select k.code as itemCode, " +
		"( " +
		"    select count(a.kind_id) " +
		"    from isw_assignment a, " +
		"    isw_work_period_assignment wpa, " +
		"    isw_work_period wp, " +
		"    isw_incident_resource ir " + 
		"    where a.kind_id = k.id " +
		"    and a.id = wpa.assignment_id " +
		"    and wpa.work_period_id = wp.id " +
		"    and wp.incident_resource_id = ir.id " +
		"    and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + " " +
		") as quantity " +
		"from iswl_kind k " +
		"where k.code in (:" + QuickStatsNamedParameterEnum.kindcodeslist.name() + ") " +
		"or k.id in (select incident_kind_id from isw_incident_qs_kind where incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + ") " +
		"or k.sit_209_id in " +
		"( " +
		"    select id from iswl_sit_209 s where s.code in (:" + QuickStatsNamedParameterEnum.sit209codes.name() + ") " +
		"    and k.id in " +
		"    ( " +
		"        select kind_id " + 
		"        from isw_assignment a " +
		"        inner join isw_work_period_assignment wpa on (wpa.assignment_id = a.id) " +
		"        inner join isw_work_period wp on (wpa.work_period_id = wp.id) " +
		"        inner join isw_incident_resource ir on (wp.incident_resource_id = ir.id) " +                     
		"        and a.assignment_status in (:" + QuickStatsNamedParameterEnum.checkedinorpending.name() + ") " +
		"        and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + " " +                    
		"    ) " +
		") " +         
		"order by k.code";

	public static final String GET_QUICK_STATS_TOTALS_BY_TYPE_SQL_QUERY = 
		"select " + 
		"upper(rc.description) as description, " + 
		"a.assignment_status as status, " + 
		"count(distinct ir.resource_id) as count, " +
		"r.resource_classification as type " +
		"from " + 
		"iswl_request_category rc, " + 
		"isw_assignment a, " + 
		"isw_resource r, " + 
		"isw_incident_resource ir, " +
		"isw_work_period wp, " +
		"isw_work_period_assignment wpa, " +
		"iswl_kind k " +
		"where r.id = ir.resource_id " +
		"and ir.id = wp.incident_resource_id " +
		"and wp.id = wpa.work_period_id " +
		"and wpa.assignment_id = a.id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and a.kind_id = k.id " +
		"and k.request_category_id = rc.id " +  
		"and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + " " +
		"group by rc.description, a.assignment_status, r.resource_classification " +
		"order by rc.description";

	public static final String GET_QUICK_STATS_TOTALS_SQL_QUERY =
		"select distinct ir.incident_id as incidentId, " +
		"(SELECT count(r.id) " +
		"from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r " + 
		"where assignment_status in (:" + QuickStatsNamedParameterEnum.filled.name() + ") " +
		"and a.id = wpa.assignment_id " +
		"and wpa.work_period_id = wp.id " +
		"and wp.incident_resource_id = ir.id " +
		"and ir.resource_id = r.id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + ") as filledResourceCount, " +
		"(SELECT count(r.id) " +
		"from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r " + 
		"where assignment_status in (:" + QuickStatsNamedParameterEnum.checkedinorpending.name() + ") " +
		"and a.id = wpa.assignment_id " +
		"and wpa.work_period_id = wp.id " +
		"and wp.incident_resource_id = ir.id " +
		"and ir.resource_id = r.id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + ") as checkedInResourceCount, " +
		"(SELECT count(r.id) " + 
		"from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r " + 
		"where assignment_status in (:" + QuickStatsNamedParameterEnum.releasedresources.name() + ") " +
		"and a.id = wpa.assignment_id " +
		"and wpa.work_period_id = wp.id " +
		"and wp.incident_resource_id = ir.id " +
		"and ir.resource_id = r.id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + ") as releasedResourceCount, " +
		"(SELECT count(r.id) " +  
		"from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r " + 
		"where assignment_status in (:" + QuickStatsNamedParameterEnum.checkedinorpending.name() + ") " +
		"and a.id = wpa.assignment_id " +
		"and wpa.work_period_id = wp.id " +
		"and wp.incident_resource_id = ir.id " +
		"and ir.resource_id = r.id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name() + ") as totalOrderCount, " +
		"(SELECT SUM(r.number_of_personnel) " +
		"from " +
		"isw_resource r, " +
		"isw_incident_resource ir, " +
		"isw_work_period wp, " +
		"isw_work_period_assignment wpa, " +
		"isw_assignment a " +
		"where " +
		"r.id = ir.resource_id " +
		"and r.parent_resource_id is null " +  //Only return Single and Primary resources.
		"and ir.id = wp.incident_resource_id " +
		"and wp.id = wpa.work_period_id " +
		"and wpa.assignment_id = a.id " +
		"and a.assignment_status in ('" + AssignmentStatusTypeEnum.C.name() + "', '" + AssignmentStatusTypeEnum.P.name() + "') " +
		")  as numberOfPersonnelCount " +
		"from isw_incident_resource ir " +
		"where ir.incident_id = :" + QuickStatsNamedParameterEnum.incidentid.name();

	public static String getQuickStatsQuery(Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer();
		sql.append("select k.code as itemCode, ")
		.append("( ")
		.append("    select count(a.kind_id) ")
		.append("    from isw_assignment a, ")
		.append("    isw_work_period_assignment wpa, ")
		.append("    isw_work_period wp, ")
		.append("    isw_incident_resource ir, ") 
		.append("    isw_resource r ") 
		.append("    where a.kind_id = k.id ")
		.append("    and a.id = wpa.assignment_id ")
		.append("    and a.assignment_status in ('C','P') ")
		.append("    and wpa.work_period_id = wp.id ")
		.append("    and wp.incident_resource_id = ir.id ")
		.append("    and r.id = ir.resource_id ")
		.append("    and r.parent_resource_id is null ");
		
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");

		sql.append(") as quantity ")
		.append("from iswl_kind k ")
		.append("where k.code in (:kindcodeslist) ");
		
		if(LongUtility.hasValue(incidentId))
			sql.append("or k.id in (select incident_kind_id from isw_incident_qs_kind where incident_id = " + incidentId + ") ");
		else
			sql.append("or k.id in (select incident_kind_id from isw_incident_qs_kind where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") )");
		sql.append("or k.sit_209_id in ")
		.append("( ")
		.append("    select id from iswl_sit_209 s where s.code in (:sit209codes) ")
		.append("    and k.id in ")
		.append("    ( ")
		.append("        select kind_id ") 
		.append("        from isw_assignment a ")
		.append("        inner join isw_work_period_assignment wpa on (wpa.assignment_id = a.id) ")
		.append("        inner join isw_work_period wp on (wpa.work_period_id = wp.id) ")
		.append("        inner join isw_incident_resource ir on (wp.incident_resource_id = ir.id) ")                     
		.append("        and a.assignment_status in (:checkedinorpending) ");
		if(LongUtility.hasValue(incidentId))
			sql.append("        and ir.incident_id = " + incidentId + " ");
		else
			sql.append("        and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append("  )  ) ")
		.append("order by k.code");

		return sql.toString();
	}

	public static String getQuickStatsTotalByType(Long incidentId, Long incidentGroupId){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ") 
		.append("upper(rc.description) as description, ") 
		.append("a.assignment_status as status, ") 
		.append("count(distinct ir.resource_id) as count, ")
		.append("r.resource_classification as type ")
		.append("from ") 
		.append("iswl_request_category rc, ") 
		.append("isw_assignment a, ") 
		.append("isw_resource r, ") 
		.append("isw_incident_resource ir, ")
		.append("isw_work_period wp, ")
		.append("isw_work_period_assignment wpa, ")
		.append("iswl_kind k ")
		.append("where r.id = ir.resource_id ")
		.append("and ir.id = wp.incident_resource_id ")
		.append("and wp.id = wpa.work_period_id ")
		.append("and wpa.assignment_id = a.id ")
		.append("and r.parent_resource_id is null ")  //Only return Single and Primary resources.
		.append("and a.kind_id = k.id ")
		.append("and k.code != 'UNK' ")
		.append("and k.request_category_id = rc.id ");
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append("group by rc.description, a.assignment_status, r.resource_classification ")
		.append("order by rc.description");
		
		return sql.toString();
	}
	
	public static String getQuickStatsTotalsQuery(Long incidentId, Long incidentGroupId) {
		StringBuffer sql = new StringBuffer()
		.append("select distinct i.id as incidentId, ")
		.append("(SELECT count(r.id) ")
		.append("from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r ") 
		.append("where assignment_status in (:filled) ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.kind_id not in (select id from iswl_kind where code = 'UNK') ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and ir.resource_id = r.id ")
		.append("and r.parent_resource_id is null ");  //Only return Single and Primary resources.
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append(") as filledResourceCount, ");
		sql.append("(SELECT count(ir.id) ")
		.append("from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r ") 
		.append("where assignment_status in (:checkedinorpending) ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.kind_id not in (select id from iswl_kind where code = 'UNK') ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and ir.id = wp.incident_resource_id ")
		.append("and ir.resource_id = r.id ")
		.append("and r.parent_resource_id is null ");  //Only return Single and Primary resources.
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append(") as checkedInResourceCount, ");
		sql.append("(SELECT count(ir.id) ") 
		.append("from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r ") 
		.append("where assignment_status in (:releasedresources) ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.kind_id not in (select id from iswl_kind where code = 'UNK') ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and ir.id = wp.incident_resource_id ")
		.append("and ir.resource_id = r.id ")
		.append("and r.parent_resource_id is null ");  //Only return Single and Primary resources.
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append(") as releasedResourceCount, ");
		sql.append("(SELECT count(ir.id) ")  
		.append("from isw_assignment a, isw_work_period_assignment wpa, isw_work_period wp, isw_incident_resource ir, isw_resource r ") 
		//.append("where assignment_status in (:checkedinorpending) ")
		.append("where a.id = wpa.assignment_id ")
		.append("and a.kind_id not in (select id from iswl_kind where code = 'UNK') ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and ir.id = wp.incident_resource_id ")
		.append("and ir.resource_id = r.id ")
		.append("and r.parent_resource_id is null ");  //Only return Single and Primary resources.
		if(LongUtility.hasValue(incidentId))
			sql.append("and ir.incident_id = " + incidentId + " ");
		else
			sql.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");
		sql.append(") as totalOrderCount, ");
		sql.append("(SELECT SUM(r.number_of_personnel) ")
		.append("from ")
		.append("isw_incident_resource ir, ")
		.append("isw_resource r, ")
		.append("isw_work_period wp, ")
		.append("isw_work_period_assignment wpa, ")
		.append("isw_assignment a ")
		.append("where ");
		if(LongUtility.hasValue(incidentId))
			sql.append("ir.incident_id = " + incidentId + " ");
		else
			sql.append("ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id =  " + incidentGroupId + ") ");
		sql.append("and r.id = ir.resource_id ")
		.append("and r.parent_resource_id is null ")  //Only return Single and Primary resources.
		.append("and ir.id = wp.incident_resource_id ")
		.append("and wp.id = wpa.work_period_id ")
		.append("and wpa.assignment_id = a.id ")
		.append("and a.assignment_status in ('" + AssignmentStatusTypeEnum.C.name() + "', '" + AssignmentStatusTypeEnum.P.name() + "') ")
		.append("and a.kind_id not in (select id from iswl_kind where code = 'UNK') ")
		.append(")  as numberOfPersonnelCount ")
		.append("from isw_incident i ");
		if(LongUtility.hasValue(incidentId))
			sql.append("where i.id = " + incidentId + " ");
		else
			sql.append("where i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ");

		return sql.toString();
	}

}
