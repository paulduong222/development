package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ICS209Query {

	public final static String ORACLE_PERSON_QUERY = "" +
	 "SELECT count(distinct id) " +
	 "FROM ISW_RESOURCE " +
	 "CONNECT BY PRIOR ID=PARENT_RESOURCE_ID "+
	 "START WITH PARENT_RESOURCE_ID = r.id ";

	//	 "WHERE IS_PERSON = 1 CONNECT BY PRIOR ID=PARENT_RESOURCE_ID "+

	public final static String ORACLE_PERSON_QUERY_NEW = "" +
	 "SELECT number_of_personnel " +
	 "FROM ISW_RESOURCE where id = r.id " ; 
	
	public final static String POSTGRES_PERSON_QUERY = "" +
	 "WITH n(cnt) AS " +
	 "( " +
	 "	SELECT count(distinct id) " + 
     "	FROM isw_resource as n " + 
	 "   where parent_resource_id = r.id " + 
	 " ) " +
	 "SELECT cnt from n ";
	
//	 "    and is_person is true " +

	public final static String POSTGRES_PERSON_QUERY_NEW_OLD = "" +
	 "WITH n(cnt) AS " +
	 "( " +
	 "	SELECT sum(number_of_personnel) " + 
    "	FROM isw_resource as n " + 
	 "   where parent_resource_id = r.id " + 
	 " ) " +
	 "SELECT cnt from n ";

	public final static String POSTGRES_PERSON_QUERY_NEW = "" +
	 "	SELECT number_of_personnel " + 
	 "	FROM isw_resource " + 
	 "   where id = r.id " + 
	 "  " ;
	
	public static String buildQuery(ICS209ReportFilter filter, Boolean isOracle)  {
		StringBuffer b = new StringBuffer();
			 b.append("select sum(case when s.code = 'C1' then 1 else 0 end ) as crw1Count ")
			 .append(",sum(case when s.code = 'C1' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw1PersonCount ")
			 .append(",sum(case when s.code = 'C3' then 1 else 0 end ) as crw1StCount ")
			 .append(",sum(case when s.code = 'C3' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw1StPersonCount ")
			 .append(",sum(case when s.code = 'C2' then 1 else 0 end ) as crw2Count ")
			 .append(",sum(case when s.code = 'C2' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw2PersonCount ")
			 .append(",sum(case when s.code = 'C4' then 1 else 0 end ) as crw2StCount ")
			 .append(",sum(case when s.code = 'C4' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw2StPersonCount ")
			 .append(",sum(case when s.code = 'H1' then 1 else 0 end ) as hel1Count ")
			 .append(",sum(case when s.code = 'H1' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel1PersonCount ")
			 .append(",sum(case when s.code = 'H2' then 1 else 0 end ) as hel2Count ")
			 .append(",sum(case when s.code = 'H2' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel2PersonCount ")
			 .append(",sum(case when s.code = 'H3' then 1 else 0 end ) as hel3Count ")
			 .append(",sum(case when s.code = 'H3' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel3PersonCount ")
			 .append(",sum(case when s.code = 'E' then 1 else 0 end ) as engsCount ")
			 .append(",sum(case when s.code = 'E' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as engsPersonCount ")
			 .append(",sum(case when s.code = 'ES' then 1 else 0 end ) as engsStCount ")
			 .append(",sum(case when s.code = 'ES' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as engsStPersonCount ")
			 .append(",sum(case when s.code = 'D' then 1 else 0 end ) as dozrCount ")
			 .append(",sum(case when s.code = 'D' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as dozrPersonCount ")
			 .append(",sum(case when s.code = 'DS' then 1 else 0 end ) as dozrStCount ")
			 .append(",sum(case when s.code = 'DS' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as dozrStPersonCount ")
			 .append(",sum(case when s.code = 'W' then 1 else 0 end ) as wtdrCount ")
			 .append(",sum(case when s.code = 'W' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as wtdrPersonCount ")
			 .append(",sum(case when s.code = 'O' then 1 else 0 end ) as ovhdCount ")
			 .append(",sum(case when s.code = 'O' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as ovhdPersonCount ")
			 .append(",sum(case when s.code = 'CC' then 1 else 0 end ) as ccCount ")
			 .append(",sum(case when s.code = 'CC' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as ccPersonCount ")
			 .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then 1 else 0 end ) as non209Count ") 
			 .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then ( ");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as non209PersonCount ")
			 .append("from isw_incident_resource ir,isw_work_period wp, isw_work_period_assignment wpa ")
			 .append(", isw_assignment a, iswl_kind k, isw_resource r, iswl_sit_209 s ")
			 .append("where k.id = a.kind_id ")
			 .append("and a.id = wpa.assignment_id ")
			 .append("and wpa.work_period_id = wp.id ")
			 .append("and wp.incident_resource_id = ir.id ")
			 .append("and a.assignment_status in ('C','P') ")
			 .append("and r.id = ir.resource_id  ")
			 .append("and r.parent_resource_id is null ")
			 .append("and r.agency_id is null ")
			 // if we are supposed to use other agencies, use line below instead of one above
			 //.append("and ( (r.agencyId is null) or (r.agency_id not in (select id from iswl_agency where agency_code not in ('BIA','BLM','FS','FWS','NPS','PVT','RUR' ))) ")
			 .append("and s.id =K.SIT_209_ID ");
			 if(LongUtility.hasValue(filter.getIncidentId()))
				 b.append("and ir.incident_id = " + filter.getIncidentId() + " ");
			 else if(LongUtility.hasValue(filter.getIncidentGroupId()))
				 b.append("and ir.incident_id in ( select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
				
		
		return b.toString();
	}

	public static String buildQuery2(ICS209ReportFilter filter, Boolean isOracle)  {
		StringBuffer b = new StringBuffer();
		b.append("select ag2.agency_code, ")
	         .append("crw1Count, crw1PersonCount, ")
	         .append("crw1StCount, crw1StPersonCount, ")
	    	 .append("crw2Count, crw2PersonCount, ")
	    	 .append("crw2StCount, crw2StPersonCount, ")
			 .append("hel1Count, hel1PersonCount, ")
			 .append("hel2Count, hel2PersonCount, ")
			 .append("hel3Count, hel3PersonCount, ")
			 .append("engsCount, engsPersonCount, ")
			 .append("engsStCount, engsStPersonCount, ")
			 .append("dozrCount,  dozrPersonCount, ")
			 .append("dozrStCount, dozrStPersonCount, ")
			 .append("wtdrCount, wtdrPersonCount, ")
			 .append("ovhdCount, ovhdPersonCount, ")
			 .append("ccCount,  ccPersonCount, ")
			 .append("non209Count,  non209PersonCount ")
			 .append("from iswl_agency ag2 left join ")
			 .append("(select ag.agency_code as agencyCode ")
			 .append(",sum(case when s.code = 'C1' then 1 else 0 end ) as crw1Count ")
			 .append(",sum(case when s.code = 'C1' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw1PersonCount ")
			 .append(",sum(case when s.code = 'C3' then 1 else 0 end ) as crw1StCount ")
			 .append(",sum(case when s.code = 'C3' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw1StPersonCount ")
			 .append(",sum(case when s.code = 'C2' then 1 else 0 end ) as crw2Count ")
			 .append(",sum(case when s.code = 'C2' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw2PersonCount ")
			 .append(",sum(case when s.code = 'C4' then 1 else 0 end ) as crw2StCount ")
			 .append(",sum(case when s.code = 'C4' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as crw2StPersonCount ")
			 .append(",sum(case when s.code = 'H1' then 1 else 0 end ) as hel1Count ")
			 .append(",sum(case when s.code = 'H1' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel1PersonCount ")
			 .append(",sum(case when s.code = 'H2' then 1 else 0 end ) as hel2Count ")
			 .append(",sum(case when s.code = 'H2' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel2PersonCount ")
			 .append(",sum(case when s.code = 'H3' then 1 else 0 end ) as hel3Count ")
			 .append(",sum(case when s.code = 'H3' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as hel3PersonCount ")
			 .append(",sum(case when s.code = 'E' then 1 else 0 end ) as engsCount ")
			 .append(",sum(case when s.code = 'E' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as engsPersonCount ")
			 .append(",sum(case when s.code = 'ES' then 1 else 0 end ) as engsStCount ")
			 .append(",sum(case when s.code = 'ES' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as engsStPersonCount ")
			 .append(",sum(case when s.code = 'D' then 1 else 0 end ) as dozrCount ")
			 .append(",sum(case when s.code = 'D' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as dozrPersonCount ")
			 .append(",sum(case when s.code = 'DS' then 1 else 0 end ) as dozrStCount ")
			 .append(",sum(case when s.code = 'DS' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as dozrStPersonCount ")
			 .append(",sum(case when s.code = 'W' then 1 else 0 end ) as wtdrCount ")
			 .append(",sum(case when s.code = 'W' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as wtdrPersonCount ")
			 .append(",sum(case when s.code = 'O' then 1 else 0 end ) as ovhdCount ")
			 .append(",sum(case when s.code = 'O' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as ovhdPersonCount ")
			 .append(",sum(case when s.code = 'CC' then 1 else 0 end ) as ccCount ")
			 .append(",sum(case when s.code = 'CC' then (");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as ccPersonCount ")
			 .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then r.number_of_personnel else 0 end ) as non209Count ") 
			 .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then ( ");
			if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
			if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
			b.append(") else 0 end ) as non209PersonCount ")
			 .append("from isw_incident_resource ir,isw_work_period wp, isw_work_period_assignment wpa ")
			 .append(", isw_assignment a, iswl_kind k, iswl_agency ag, isw_resource r, iswl_sit_209 s ")
			 .append("where k.id = a.kind_id ")
			 .append("and a.id = wpa.assignment_id ")
			 .append("and wpa.work_period_id = wp.id ")
			 .append("and wp.incident_resource_id = ir.id ")
			 .append("and a.assignment_status in ('C','P') ")
			 .append("and r.id = ir.resource_id  ")
			 .append("and r.parent_resource_id is null ")
			 .append("and ag.id = r.agency_id  ")
			 //.append("and ag.agency_code in ('BIA','BLM','USFS','FWS','NPS','PVT','RUR' ) ")
			 .append("and s.id =K.SIT_209_ID ");
			 if(LongUtility.hasValue(filter.getIncidentId()))
				 b.append("and ir.incident_id = " + filter.getIncidentId() + " ");
			 else if(LongUtility.hasValue(filter.getIncidentGroupId()))
				 b.append("and ir.incident_id in ( select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
				 
       b.append("and ag.agency_type != 'STATE' ")
			 .append("group by ag.agency_code ")
			 .append(") subq on ag2.agency_code = subq.agencyCode ")
			 .append("where ag2.agency_code = subq.agencyCode ")
			 .append("group by ag2.agency_code, crw1Count,crw1PersonCount,crw1StCount,crw1StPersonCount, crw2Count,crw2PersonCount,  ")
			 .append("crw2StCount,crw2StPersonCount, hel1Count,hel1PersonCount ")
			 .append(",hel2Count, hel2PersonCount,hel3Count, hel3PersonCount, engsCount, engsPersonCount, engsStCount, engsStPersonCount, ")
			 .append("dozrCount, dozrPersonCount, dozrStCount, dozrStPersonCount,wtdrCount, wtdrPersonCount, ovhdCount, ovhdPersonCount ")
			 .append(",ccCount, ccPersonCount, non209Count,non209PersonCount ")
			 .append("order by ag2.agency_code ")
			 ;	   
		
		return b.toString();
	}

	public static String buildQueryState(ICS209ReportFilter filter, Boolean isOracle)  {
	  StringBuffer b = new StringBuffer();
    b.append("select ag.agency_type as agency_type ")
       .append(",sum(case when s.code = 'C1' then 1 else 0 end ) as crw1Count ")
       .append(",sum(case when s.code = 'C1' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as crw1PersonCount ")
       .append(",sum(case when s.code = 'C3' then 1 else 0 end ) as crw1StCount ")
       .append(",sum(case when s.code = 'C3' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as crw1StPersonCount ")
       .append(",sum(case when s.code = 'C2' then 1 else 0 end ) as crw2Count ")
       .append(",sum(case when s.code = 'C2' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as crw2PersonCount ")
       .append(",sum(case when s.code = 'C4' then 1 else 0 end ) as crw2StCount ")
       .append(",sum(case when s.code = 'C4' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as crw2StPersonCount ")
       .append(",sum(case when s.code = 'H1' then 1 else 0 end ) as hel1Count ")
       .append(",sum(case when s.code = 'H1' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as hel1PersonCount ")
       .append(",sum(case when s.code = 'H2' then 1 else 0 end ) as hel2Count ")
       .append(",sum(case when s.code = 'H2' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as hel2PersonCount ")
       .append(",sum(case when s.code = 'H3' then 1 else 0 end ) as hel3Count ")
       .append(",sum(case when s.code = 'H3' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as hel3PersonCount ")
       .append(",sum(case when s.code = 'E' then 1 else 0 end ) as engsCount ")
       .append(",sum(case when s.code = 'E' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as engsPersonCount ")
       .append(",sum(case when s.code = 'ES' then 1 else 0 end ) as engsStCount ")
       .append(",sum(case when s.code = 'ES' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as engsStPersonCount ")
       .append(",sum(case when s.code = 'D' then 1 else 0 end ) as dozrCount ")
       .append(",sum(case when s.code = 'D' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as dozrPersonCount ")
       .append(",sum(case when s.code = 'DS' then 1 else 0 end ) as dozrStCount ")
       .append(",sum(case when s.code = 'DS' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as dozrStPersonCount ")
       .append(",sum(case when s.code = 'W' then 1 else 0 end ) as wtdrCount ")
       .append(",sum(case when s.code = 'W' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as wtdrPersonCount ")
       .append(",sum(case when s.code = 'O' then 1 else 0 end ) as ovhdCount ")
       .append(",sum(case when s.code = 'O' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as ovhdPersonCount ")
       .append(",sum(case when s.code = 'CC' then 1 else 0 end ) as ccCount ")
       .append(",sum(case when s.code = 'CC' then (");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as ccPersonCount ")
       .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then 1 else 0 end ) as non209Count ") 
       .append(",sum(case when s.code not in ('C1','C2','C3','C4','H1','H2','H3','E','ES','D','DS','W','O','CC' ) then ( ");
      if(isOracle)b.append(ICS209Query.ORACLE_PERSON_QUERY_NEW+" ");
      if(!isOracle)b.append(ICS209Query.POSTGRES_PERSON_QUERY_NEW+" ");
      b.append(") else 0 end ) as non209PersonCount ")
       .append("from isw_incident_resource ir,isw_work_period wp, isw_work_period_assignment wpa ")
       .append(", isw_assignment a, iswl_kind k, iswl_agency ag, isw_resource r, iswl_sit_209 s ")
       .append("where k.id = a.kind_id ")
       .append("and a.id = wpa.assignment_id ")
       .append("and wpa.work_period_id = wp.id ")
       .append("and wp.incident_resource_id = ir.id ")
       .append("and a.assignment_status in ('C','P') ")
       .append("and r.id = ir.resource_id  ")
       .append("and r.parent_resource_id is null ")
       .append("and ag.id = r.agency_id  ")
       .append("and s.id =K.SIT_209_ID ")
       .append("and ag.agency_type = 'STATE' ")
       ;     

      if(LongUtility.hasValue(filter.getIncidentId()))
          b.append("and ir.incident_id = " + filter.getIncidentId() + " ");
      else if(LongUtility.hasValue(filter.getIncidentGroupId()))
          b.append("and ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");

      b.append("group by ag.agency_type ");

    return b.toString();
  }
	
}
