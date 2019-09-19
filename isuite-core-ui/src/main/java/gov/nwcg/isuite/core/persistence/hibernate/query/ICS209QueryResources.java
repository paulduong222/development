package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ICS209QueryResources {
/*
        select 
           i.id as incidentId
            ,i.incident_name || ' ('||org.unit_code||'-'||i.nbr||')'
            ,r.id as resourceId
            ,r.parent_resource_id as resourceParentId
            ,a.assignment_status as assignmentStatus
            ,k.code as kindCode
            , r.number_of_personnel as personnelCount
            , k.is_strike_team as isStrikeTeam
            ,case 
                    when r.agency_id is not null then ag.agency_code
                    else 'No Agency' 
               end as agencyCode
            ,case 
                    when (r.agency_id is not null and ag.agency_type = 'STATE') then 'STATE'
                    when (r.agency_id is not null and ag.agency_type != 'STATE') then ag.agency_code
                    else 'No Agency' 
               end as agencyCode2
            ,case 
                    when r.agency_id is not null then ag.agency_type
                    else 'NONE' 
               end as agencyType
            ,case 
                    when k.sit_209_id is not null then s.description
                    else 'Non 209' 
               end as sitCategory
            , k.people as kindPeopleCount
        from isw_incident i
        		left join isw_organization org on i.unit_id = org.id
             , isw_incident_resource ir
            , isw_work_period wp
            , isw_work_period_assignment wpa
            , isw_assignment a
            , iswl_kind k
                left join iswl_sit_209 s on k.sit_209_id = s.id
            , isw_resource r
                left join iswl_agency ag on r.agency_id = ag.id
            where i.id = 10004
            and ir.incident_id = i.id
            and wp.incident_resource_id = ir.id
            and wpa.work_period_id = wp.id
            and a.id = wpa.assignment_id
            and k.id = a.kind_id
            and s2.is_standard=true
            and r.id = ir.resource_id
union
        select 
           i2.id as incidentId
            ,i2.incident_name || ' ('||org2.unit_code||'-'||i2.nbr||')'
            ,ro.id as resourceId
            ,null as resourceParentId
            ,iro.assignment_status as assignmentStatus
            ,k2.code as kindCode
            , k2.people as personnelCount
            , k2.is_strike_team as isStrikeTeam
            ,case 
                    when ro.agency_id is not null then ag2.agency_code
                    else 'No Agency' 
               end as agencyCode
            ,case 
                    when (ro.agency_id is not null and ag2.agency_type = 'STATE') then 'STATE'
                    when (ro.agency_id is not null and ag2.agency_type != 'STATE') then ag2.agency_code
                    else 'No Agency' 
               end as agencyCode2
            ,case 
                    when ro.agency_id is not null then ag2.agency_type
                    else 'NONE' 
               end as agencyType
            ,case 
                    when k2.sit_209_id is not null then s2.description
                    else 'Non 209' 
               end as sitCategory
            , k2.people as kindPeopleCount
        from isw_incident i2
        		left join isw_organization org2 on i2.unit_id = org2.id
             , isw_incident_resource_other iro
            , iswl_kind k2
                left join iswl_sit_209 s2 on k2.sit_209_id = s2.id
            , isw_resource_other ro
                left join iswl_agency ag2 on ro.agency_id = ag2.id
            where i2.id = 10004
            and iro.incident_id = i2.id
            and k2.id = ro.kind_id
            and s2.is_standard=true
            and ro.id = iro.resource_other_id
 */
	
	public static String get209ResourcesQuery(ICS209ReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

        sql.append("select ")
           .append("  i.id as incidentId ")
           .append(" , i.incident_name || ' ('||org.unit_code||'-'||i.nbr||')' as incidentNumber ")
           .append(" ,r.id as resourceId ")
           .append(" ,r.parent_resource_id as resourceParentId ")
           .append(" ,a.assignment_status as assignmentStatus ")
           .append(" ,k.code as kindCode ")
           .append(" , r.number_of_personnel as personnelCount ")
           .append(" , k.is_strike_team as isStrikeTeam ")
           .append(" , r.is_person as isPerson ");
        	if(BooleanUtility.isFalse(filter.getStateGrouping())){
	           sql.append(" ,case  ")
	           .append("      when r.agency_id is not null then ag.agency_code ")
	           .append("      else 'No Agency'  ")
	           .append(" end as agencyCode ");
        	}
        	if(BooleanUtility.isTrue(filter.getStateGrouping())){
	           sql.append(" ,case  ")
	           .append("      when (r.agency_id is not null and ag.agency_type = 'STATE') then 'STATE' ")
	           .append("      when (r.agency_id is not null and ag.agency_type != 'STATE') then ag.agency_code ")
	           .append("      else 'No Agency'  ")
	           .append(" end as agencyCode ");
        	}
           sql.append(" ,case  ")
           .append("      when r.agency_id is not null then ag.agency_type ")
           .append("      else 'NONE'  ")
           .append(" end as agencyType ")
           .append(" ,case  ")
           .append("      when k.sit_209_id is not null then s.description ")
           .append("      else 'Non 209'  ")
           .append(" end as sitCategory ")
           .append(" ,k.people as kindPeopleCount ")
           .append("from isw_incident i ")
           .append("	left join isw_organization org on i.unit_id = org.id ")
           .append(", isw_incident_resource ir ")
           .append(", isw_work_period wp ")
           .append(", isw_work_period_assignment wpa ")
           .append(", isw_assignment a ")
           .append(", iswl_kind k ")
           .append("  	left join iswl_sit_209 s on k.sit_209_id = s.id ")
           .append(", isw_resource r ")
           .append("	left join iswl_agency ag on r.agency_id = ag.id ");
           if(LongUtility.hasValue(filter.getIncidentId())){
        	   sql.append("where i.id = " + filter.getIncidentId() + " ");
			}
           if(LongUtility.hasValue(filter.getIncidentGroupId())){
        	   sql.append("where i.id in ( select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
           }
           sql.append("and ir.incident_id = i.id ")
           .append("and wp.incident_resource_id = ir.id ")
           .append("and wpa.work_period_id = wp.id ")
           .append("and a.id = wpa.assignment_id ")
           .append("and k.id = a.kind_id ")
           .append("and (k.sit_209_id is null or s.is_standard = " + (isOracle ? 1 : true) + ") ")
           .append("and r.id = ir.resource_id ");
           
           sql.append("union ");
           
           sql.append("select  ")
           .append(" i2.id as incidentId ")
           .append(" , i2.incident_name || ' ('||org2.unit_code||'-'||i2.nbr||')' as incidentNumber ")
           .append(" ,ro.id as resourceId ")
           .append(" ,null as resourceParentId ")
           .append(" ,iro.assignment_status as assignmentStatus ")
           .append(" ,k2.code as kindCode ")
           .append(" ,k2.people as personnelCount ")
           .append(" ,k2.is_strike_team as isStrikeTeam ");
           if(BooleanUtility.isTrue(isOracle)){
        	   sql.append(" , 0 as isPerson ");
           }else{
        	   sql.append(" , false as isPerson ");
           }
           if(BooleanUtility.isFalse(filter.getStateGrouping())){
	           sql.append(",case  ")
	           .append("      when ro.agency_id is not null then ag2.agency_code ")
	           .append("      else 'No Agency'  ")
	           .append(" end as agencyCode ");
           }
       		if(BooleanUtility.isTrue(filter.getStateGrouping())){
	           sql.append(",case  ")
	           .append("      when (ro.agency_id is not null and ag2.agency_type = 'STATE') then 'STATE' ")
	           .append("      when (ro.agency_id is not null and ag2.agency_type != 'STATE') then ag2.agency_code ")
	           .append("      else 'No Agency'  ")
	           .append(" end as agencyCode ");
           }
           sql.append(",case  ")
           .append("      when ro.agency_id is not null then ag2.agency_type ")
           .append("      else 'NONE'  ")
           .append(" end as agencyType ")
           .append(",case  ")
           .append("      when k2.sit_209_id is not null then s2.description ")
           .append("      else 'Non 209'  ")
           .append(" end as sitCategory ")
           .append(", k2.people as kindPeopleCount ")
           .append("from isw_incident i2 ")
           .append("	left join isw_organization org2 on i2.unit_id = org2.id ")
           .append(", isw_incident_resource_other iro ")
           .append(", iswl_kind k2 ")
           .append("  	left join iswl_sit_209 s2 on k2.sit_209_id = s2.id ")
           .append(", isw_resource_other ro ")
           .append("	left join iswl_agency ag2 on ro.agency_id = ag2.id ");
           if(LongUtility.hasValue(filter.getIncidentId())){
        	   sql.append("where i2.id = " + filter.getIncidentId() + " ");
           }
           if(LongUtility.hasValue(filter.getIncidentGroupId())){
        	   sql.append("where i2.id in ( select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
           }
           sql.append("and iro.incident_id = i2.id ")
           .append("and k2.id = ro.kind_id ")
           .append("and (k2.sit_209_id is null or s2.is_standard = " + (isOracle ? 1 : true) + ") ")
           .append("and ro.id = iro.resource_other_id ");
           
		return sql.toString();
	}
}
