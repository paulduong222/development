package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodOvernightStayInfo;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.persistence.ReportDemobDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.DemobQuery;
import gov.nwcg.isuite.core.reports.data.ActualDemobReportData;
import gov.nwcg.isuite.core.reports.data.ActualDemobRestOverNightData;
import gov.nwcg.isuite.core.reports.data.AirTravelQuestionReportData;
import gov.nwcg.isuite.core.reports.data.AirTravelRequestReportData;
import gov.nwcg.isuite.core.reports.data.AvailableForReleaseReportData;
import gov.nwcg.isuite.core.reports.data.CheckoutReportData;
import gov.nwcg.isuite.core.reports.data.DemobCheckoutSectionData;
import gov.nwcg.isuite.core.reports.data.DemobPlanningReportData;
import gov.nwcg.isuite.core.reports.data.GroundSupportReportData;
import gov.nwcg.isuite.core.reports.data.LastWorkDayReportData;
import gov.nwcg.isuite.core.reports.data.TentativeReleasePosterReportData;
import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class ReportDemobDaoHibernate extends TransactionSupportImpl implements ReportDemobDao {
	
	public ReportDemobDaoHibernate() {
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<CheckoutReportData> getCheckoutReportData(CheckoutReportFilter filter, Long useGroupId) throws PersistenceException {
		StringBuffer b = new StringBuffer();
		b.append("select i.incident_name as incidentName, ")
		.append("i.id as incidentId, ")
		.append("i.nbr as incidentNumber, ")
		.append("org.unit_code as incidentUnit, ")
		.append("wp.dm_tentative_release_date as tentativeReleaseDate, ")
		.append("a.request_number as requestNumber, ")
		.append("k.code as kindCode, ")
		.append("case ")
		.append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName, ")
		.append("r.number_of_personnel as numberPersonnel, ");
		/*
		if(super.isOracleDialect()){
			b.append("(select 1 from dual) as numberPersonnel,");
		}else{
			b.append("(WITH n(cnt) AS ")
			.append("(SELECT count(distinct id) ")
			.append("FROM isw_resource as n ")
			.append("where parent_resource_id = r.id ")
			.append("and is_person is true) ")
			.append("SELECT cnt from n) ")
			.append("as numberPersonnel, ");
		}
		*/
		b.append("(select last_name || ', ' || first_name as name ")
		.append("from isw_resource ")
		.append("where id = (select min(r4.id) from isw_resource r4 where r4.parent_resource_id = r.id ")
		.append("and r4.is_leader = " + super.getBooleanComparison(true) + ")) as leaderName, ")
		.append("wp.dm_travel_method as travelMethod, ")
		.append("wp.dm_release_date as actualReleaseDate, ")
		.append("wp.dm_tentative_demob_city as demobCity, ")
		.append("cs.cs_abbreviation as demobState, ")
		.append("ag.agency_code as agency, ")
		.append("wp.dm_release_remarks as releaseRemarks, ")
		.append("r.id as resourceId, ")
		.append("rorg.unit_code as unitId, ")
		.append("wp.id as workPeriodId, ")
		.append("wp.dm_tentative_arrival_date as eta, ");
		if(super.isOracleDialect()){
			b.append("(SELECT WM_CONCAT('['||wposi.city||','||wposics.cs_abbreviation||']') FROM isw_wp_overnight_stay_info wposi left join iswl_country_subdivision wposics on wposics.id = wposi.state_id " + 
			"WHERE wposi.work_period_id = wp.id ) AS ron ");
		}else{
			b.append("(SELECT ARRAY_TO_STRING(ARRAY(SELECT '['||wposi.city||','||wposics.cs_abbreviation||']' FROM isw_wp_overnight_stay_info wposi left join iswl_country_subdivision wposics on wposics.id = wposi.state_id  " +
			"WHERE wposi.work_period_id = wp.id ),',') as ARRAY_TO_STRING) AS ron ");
		}
		b.append("FROM isw_incident i ")
		.append("left join isw_organization org on i.unit_id = org.id ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
		.append("left join isw_organization rorg on r.organization_id = rorg.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id ")
		.append("left join iswl_agency ag on r.agency_id = ag.id ");
		
		Boolean bWhere=false;
		
		if(CollectionUtility.hasValue(filter.getResourceIds())) {
			 b.append("where r.id in (:rids) ");
			 bWhere=true;
		} 
		 
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if(!bWhere){
			 	b.append(" WHERE i.id = " + filter.getIncidentId() + " ");
			 	bWhere=true;
			 }else{
				 b.append(" and i.id = " + filter.getIncidentId() + " ");
			 }
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere){
				 	b.append(" WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
				 	bWhere=true;
				 }else{
				 	b.append(" and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
				 }
		 }
		 
		 if(!CollectionUtility.hasValue(filter.getResourceIds())) {
			 b.append(" and a.assignment_status = 'P' ");
			 
			 b.append(" and wp.dm_is_checkout_form_printed = " + super.getBooleanComparison(false));
			 
			 if (!filter.getIncludeSTTFComponents()) {
				 //b.append(" and (r.resource_classification is null or r.resource_classification not in ('ST','TF') ) ");
				 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
				 //b.append(" and k.is_strike_team != " + super.getBooleanComparison(true) + " ");
				 b.append(" and " );
				 b.append("( ")
				  .append("  r.parent_resource_id is null ")
				  .append(") ");
				 /* c.r. do not include subordinates
				  .append("   or ")
				  .append("  r.parent_resource_id not in ")
				  .append("   ( ")
				  .append("      select resource_id from isw_incident_resource ir2 ")
				  .append("        left join isw_work_period wp2 on ir2.id = wp2.incident_resource_id ")
				  .append("        left join isw_work_period_assignment wpa2 on wp2.id = wpa2.work_period_id ")
				  .append("        left join isw_assignment a2 on wpa2.assignment_id = a2.id ")
				  .append("        left join iswl_kind k2 on a2.kind_id = k2.id ")
				  .append("      where resource_id = r.parent_resource_id ")
				  .append("      and k2.is_strike_team = " + super.getBooleanComparison(true) + " ")
				  .append("    ) ")
				  .append(") ");
				  */
			 }else {
				 b.append(" AND (r.parent_resource_id is null or ")
				 .append(" r.parent_resource_id in ")
				 .append(" (select ir2.resource_id ")
				 .append(" from isw_incident_resource ir2 ")
				 .append(" ,isw_work_period wp2 ")
				 .append(" ,isw_work_period_assignment wpa2 ")
				 .append(" ,isw_assignment a2 ")
				 .append(" ,iswl_kind k2 ")
				 .append(" where ir2.resource_id = r.parent_resource_id ")
				 .append(" and wp2.incident_resource_id = ir2.id ")
				 .append(" and wpa2.work_period_id = wp2.id ")
				 .append(" and a2.id = wpa2.assignment_id ")
				 .append(" and a2.end_date is null ")
				 .append(" and k2.id = a2.kind_id ")
				 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
			 }
		 }
		 
		if(null != filter.getSorts() && filter.getSorts().size() > 0){
			String sortBy="";
			for(String field : filter.getSorts()){
				if(field.equals("resourceName")){
					sortBy=sortBy + (sortBy.equals("") ? "resourceName" : ",resourceName");
				}else if(field.equals("incidentNumber")){
					sortBy=sortBy + (sortBy.equals("") ? "incidentNumber" : ",incidentNumber");
				}else if(field.equals("actualReleaseDate")){
					sortBy=sortBy + (sortBy.equals("") ? "actualReleaseDate" : ",actualReleaseDate");
				}else if(field.equals("incidentName")){
					sortBy=sortBy + (sortBy.equals("") ? "incidentName" : ",incidentName");
				}else if(field.equals("requestNumber")){
					sortBy=sortBy + (sortBy.equals("") ? "sortrequestnumber(a.request_number)" : ",sortrequestnumber(a.request_number)");
				}else if(field.equals("agency")){
					sortBy=sortBy + (sortBy.equals("") ? "agency" : ",agency");
				}else if(field.equalsIgnoreCase("unitId")){
					sortBy=sortBy + (sortBy.equals("") ? "unitId" : ",unitId");
				}else if(field.equals("tentativeReleaseDate")){
					sortBy=sortBy + (sortBy.equals("") ? "tentativeReleaseDate" : ",tentativeReleaseDate");
				}
			}
			b.append(" ORDER BY I.ID, "+sortBy);
		}
		 
		SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		if(CollectionUtility.hasValue(filter.getResourceIds())) {
			query.setParameterList("rids", filter.getResourceIds());
		}
		 
		 CustomResultTransformer crt = new CustomResultTransformer(CheckoutReportData.class);
			
		 // since CustomResultTransformer doesn't handle longs too well
		 // we tell it how to handle these fields
	     crt.addScalar("incidentId", Long.class.getName());
	     crt.addScalar("numberPersonnel", BigInteger.class.getName());
	     //crt.addScalar("incidentGroupId", Long.class.getName());
	     crt.addScalar("resourceId", Long.class.getName());
	     crt.addScalar("workPeriodId", Long.class.getName());
	     crt.addScalar("ron", String.class.getName());
	     
		 query.setResultTransformer(crt); 
	
	     Collection<CheckoutReportData> crDataList = query.list();
	     
	     if(null != crDataList){
	    	 StringBuffer sql2 = new StringBuffer();
	    	 
	    	 // grab the sectionData defined for the incidentId
	    	 if(LongUtility.hasValue(filter.getIncidentId())
	    			 && !LongUtility.hasValue(useGroupId)){
		    	 sql2 = new StringBuffer()
		    	 	.append("SELECT section_name as sectionName, ")
		    	 	.append("field_label as label, ")
		    	 	.append("position as position, ")
		    	 	.append("is_selected as selected ")
		    	 	.append("FROM ISW_INCIDENT_PREFS ")
		    	 	.append("WHERE ")
		    	 	.append("SECTION_NAME IN ('LOGISTICS', 'PLANNING', 'FINANCE', 'OTHER_LABEL') ")
		    		.append("AND INCIDENT_ID = " + filter.getIncidentId() + " ");
	    	 }else if(LongUtility.hasValue(filter.getIncidentGroupId())
	    			 	|| LongUtility.hasValue(useGroupId)){
		    	 sql2 = new StringBuffer()
		    	 	.append("SELECT section_name as sectionName, ")
		    	 	.append("field_label as label, ")
		    	 	.append("position as position, ")
		    	 	.append("is_selected as selected ")
		    	 	.append("FROM ISW_INCIDENT_GROUP_PREFS ")
		    	 	.append("WHERE ")
		    	 	.append("SECTION_NAME IN ('LOGISTICS', 'PLANNING', 'FINANCE', 'OTHER_LABEL') ");
		    	 	if(LongUtility.hasValue(useGroupId))
		    	 		sql2.append("AND INCIDENT_GROUP_ID = " + useGroupId + " ");
		    	 	else
		    	 		sql2.append("AND INCIDENT_GROUP_ID = " + filter.getIncidentGroupId() + " ");
	    	 }
	    	 
	    	 	sql2.append(" ORDER BY case section_name ")
	    	 		.append("when 'LOGISTICS' then 1 ")
	    	 		.append("when 'PLANNING' then 2 ")
	    	 		.append("when 'FINANCE' then 3 ")
	    	 		.append("when 'OTHER_LABEL' then 4 ")
	    	 		.append("end, position");
			 
	    	 SQLQuery query2 = super.getHibernateSession().createSQLQuery(sql2.toString());
	         CustomResultTransformer crt2 = new CustomResultTransformer(DemobCheckoutSectionData.class);
	  	      
	         crt2.addScalar("selected", Boolean.class.getName());
	         crt2.addScalar("position", Integer.class.getName());
	         
	         query2.setResultTransformer(crt2); 
	    	 
	    	 ArrayList<DemobCheckoutSectionData> sectionDataList = (ArrayList)query2.list();
	
	    	 // set the sectionData in each checkoutreportdata
	    	 for(CheckoutReportData crd : crDataList){
		    	 crd.setSectionData(sectionDataList);
	    	 }
	     }
	     
	     return crDataList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getDemobPlanningReportData(gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<DemobPlanningReportData> getDemobPlanningReportData(DemobPlanningReportFilter filter) throws PersistenceException {

	  SQLQuery query = super.getHibernateSession().createSQLQuery(DemobQuery.buildQueryDemobPlanning(filter,super.isOracleDialect()));

    /*
     * Implementing custom result transformer to not have to
     * create setter methods in the target class with upper
     * case property names.
     */
    CustomResultTransformer crt = new CustomResultTransformer(DemobPlanningReportData.class);

    /*
     * Add any aliases field conversions.
     * Only need to set aliases if the targetclass has setter methods
     * that are different from the sql query fields.
     * 
     * ie: crt.addProjection("nbr","incidentNumber")
     */
    
    crt.addProjection("firstSortDate","firstSort");
    
    /*
     * Add scalar to handle for both oracle/postgres
     */
    
    crt.addScalar("incidentId", Long.class.getName());
    crt.addScalar("training", Boolean.class.getName());
    crt.addScalar("personCount", Integer.class.getName());
    crt.addScalar("lastWorkDay", Date.class.getName());
    crt.addScalar("firstSortDate", Date.class.getName());
    crt.addScalar("daysLeft", Double.class.getName());
    crt.addScalar("incidentName", String.class.getName());
    crt.addScalar("resourceName", String.class.getName());
    crt.addScalar("incidentNumber", String.class.getName());
    crt.addScalar("category", String.class.getName());
    crt.addScalar("section", String.class.getName());
    crt.addScalar("requestNumber", String.class.getName());
    crt.addScalar("crewLeader", String.class.getName());
    crt.addScalar("qualifications", String.class.getName());
    crt.addScalar("itemCode", String.class.getName());
    crt.addScalar("training", String.class.getName());
    crt.addScalar("status", String.class.getName());
    crt.addScalar("agency", String.class.getName());
    crt.addScalar("unit", String.class.getName());
    crt.addScalar("returnTravelMethod", String.class.getName());
    crt.addScalar("demobCity", String.class.getName());
    crt.addScalar("demobState", String.class.getName());
    crt.addScalar("reassign", String.class.getName());

    query.setResultTransformer(crt); 

    return query.list();
    
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getTentativeReleasePosterReportData(gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<TentativeReleasePosterReportData> getTentativeReleasePosterReportData(TentativeReleasePosterReportFilter filter) throws PersistenceException {
		StringBuffer b = new StringBuffer();
		b.append("select i.incident_name as incidentName, ")
		.append("i.id as incidentId, ")
		.append("i.nbr as incidentNumber, ")
		.append("org.unit_code as incidentUnit, ")
		.append("wp.dm_tentative_release_date as tentativeReleaseDate, ")
		.append("a.request_number as requestNumber, ")
		.append("k.code as kindCode, ")
		.append("case ")
		.append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName, ")
		
		.append("case ")
		.append("when " + (super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'A' then 'AIRCRAFT' "  )
		.append("when " + (super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'C' then 'CREWS' "  )
		.append("when " + (super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'E' then 'EQUIPMENT' "  )
		.append("when " + (super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'O' then 'OVERHEAD' "  )
		.append(" else rc.description ")
		
		.append(" end as resourceCategory ")
		.append(",r.id as resourceId ")
		.append(",extract(year from wp.dm_tentative_release_date) as tentReleaseYear " )
		.append(",extract(month from wp.dm_tentative_release_date) as tentReleaseMonth ")
		.append(",extract(day from wp.dm_tentative_release_date) as tentReleaseDay ")
		.append(",extract(hour from wp.dm_tentative_release_date) as tentReleaseHour ")
		.append(",extract(minute from wp.dm_tentative_release_date) as tentReleaseMinute ")
		.append("FROM isw_incident i ")
		.append("left join isw_organization org on i.unit_id = org.id ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join iswl_request_category rc on k.request_category_id = rc.id ");
		
		boolean bWhere=false;
		
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 b.append("where r.id in (:rids) ");
				 bWhere=true;
			 }
		 } 
		 
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if(!bWhere){
				 b.append("WHERE i.id = " + filter.getIncidentId() + " ");
			 	 bWhere=true;
			 }else{
				 b.append("and i.id = " + filter.getIncidentId() + " ");
			 }
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere){
			 	b.append(" WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
			 	bWhere=true;
			 }else{
			 	b.append(" and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
			 }
		 }
		 
		 if(null == filter.getResourceIds() || filter.getResourceIds().size() < 1){
			 if(!bWhere)
				 b.append("where ");
			 else
				 b.append("and ");
			 
			 b.append(super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) ")
			 .append(" in ('C','E','O','A') ");
			 //.append("and not wp.dm_tentative_release_date is null ");
			 
			 if( (null != filter.getReportStartDate()) && (!filter.getReportStartDate().isEmpty()))
				 b.append("and to_date(to_char(wp.dm_tentative_release_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= " + super.getDateStringComparison(filter.getReportStartDate()));
			 else{
				 b.append("and wp.dm_tentative_release_date is not null " );
			 }
			 if( (null != filter.getReportEndDate()) && (!filter.getReportEndDate().isEmpty()))
				 b.append(" and to_date(to_char(wp.dm_tentative_release_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= " + super.getDateStringComparison(filter.getReportEndDate()) );
			 
			 b.append("and a.assignment_status <> 'D' ");
			 
			 if (filter.getIncludeSTTFComponents()) {
				 //b.append(" and r.resource_classification in ('S','ST','TF','C') ");
				 b.append(" AND (r.parent_resource_id is null or ")
				 .append(" r.parent_resource_id in ")
				 .append(" (select ir2.resource_id ")
				 .append(" from isw_incident_resource ir2 ")
				 .append(" ,isw_work_period wp2 ")
				 .append(" ,isw_work_period_assignment wpa2 ")
				 .append(" ,isw_assignment a2 ")
				 .append(" ,iswl_kind k2 ")
				 .append(" where ir2.resource_id = r.parent_resource_id ")
				 .append(" and wp2.incident_resource_id = ir2.id ")
				 .append(" and wpa2.work_period_id = wp2.id ")
				 .append(" and a2.id = wpa2.assignment_id ")
				 .append(" and a2.end_date is null ")
				 .append(" and k2.id = a2.kind_id ")
				 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
			 } else {
				 //b.append(" and (r.resource_classification is null or r.resource_classification not in ('ST','TF' ) ) ");
				 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
				 //b.append(" and k.is_strike_team != " + super.getBooleanComparison(true) + " ");
				 b.append(" and " );
				 b.append("( ")
				  .append("  r.parent_resource_id is null ")
				  .append(" ) ");
				 /*
				  .append("   or ")
				  .append("  r.parent_resource_id not in ")
				  .append("   ( ")
				  .append("      select resource_id from isw_incident_resource ir2 ")
				  .append("        left join isw_work_period wp2 on ir2.id = wp2.incident_resource_id ")
				  .append("        left join isw_work_period_assignment wpa2 on wp2.id = wpa2.work_period_id ")
				  .append("        left join isw_assignment a2 on wpa2.assignment_id = a2.id ")
				  .append("        left join iswl_kind k2 on a2.kind_id = k2.id ")
				  .append("      where resource_id = r.parent_resource_id ")
				  .append("      and k2.is_strike_team = " + super.getBooleanComparison(true) + " ")
				  .append("    ) ")
				  .append(") ");
				  */
			 }
		 }

		 b.append("ORDER BY i.id, ")
		 .append("tentReleaseYear,tentReleaseMonth,tentReleaseDay ")
		 .append(super.isOracleDialect() ? ", substr(a.request_number, 1, 1) " : ", substring(a.request_number from 1 for 1) ")
		 .append(",tentReleaseHour,tentReleaseMinute, ");
		 
		 //.append("case when resourceCategory ")
		 //.append(" in ('A','E','C') then sortrequestnumber(a.request_number) ")
		 //.append("else case when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name else r.last_name || ', ' || r.first_name end end ");
		b.append("case when " + (super.isOracleDialect() ? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) ") + " in ('A','E','C') ")
		 .append("  then sortrequestnumber(a.request_number) " )
		 .append("else case when r.is_person = " + (super.isOracleDialect() ? "1" : "true") + " ")
		 .append("  then r.last_name || ', ' || r.first_name ")
		 .append("  else r.resource_name ")
		 .append("  end ")
		 .append("end ");
		
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 query.setParameterList("rids", filter.getResourceIds());
			 }
		 }
		 
		 CustomResultTransformer crt = new CustomResultTransformer(TentativeReleasePosterReportData.class);
			
		 // since CustomResultTransformer doesn't handle longs too well
		 // we tell it how to handle these fields
         crt.addScalar("incidentId", Long.class.getName());
         crt.addScalar("resourceId", Long.class.getName());

         crt.addSkipTransformation("tentReleaseYear");
         crt.addSkipTransformation("tentReleaseMonth");
         crt.addSkipTransformation("tentReleaseDay");
         crt.addSkipTransformation("tentReleaseHour");
         crt.addSkipTransformation("tentReleaseMinute");
         crt.addSkipTransformation("customSort");
         
		 query.setResultTransformer(crt); 

	     return query.list();
	}
	
	/*(
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getAvailableForReleaseReportData(gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AvailableForReleaseReportData> getAvailableForReleaseReportData(AvailableForReleaseReportFilter filter) throws PersistenceException {
		StringBuffer b = new StringBuffer();
		b.append("select i.incident_name as incidentName, ")
		.append("i.id as incidentId, ")
		.append("i.nbr as incidentNumber, ")
		.append("org.unit_code as incidentUnit, ")
		.append("rc.code as resourceCategoryCode, ")
		.append("a.request_number as requestNumber, ")
		.append("o.unit_code as unitId, ")
		.append("case ")
		.append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName, ")
		.append("k.code as kindCode, ")
		.append("wp.dm_tentative_demob_city as demobCity, ")
		.append("cs.cs_abbreviation as demobState, ")
		.append("wp.dm_travel_method as returnTravelMethod, ")
		.append("jp.code as returnJetport, ")
		.append("wp.dm_tentative_release_date as tentativeReleaseDate, ")
		.append("wp.dm_is_reassignable as reassignable, ")
		.append("rm.start_date as mobilizationStartDate, ") 
		.append("wp.ci_first_work_date as firstWorkDate, ")
		.append("wp.ci_length_at_assignment as lengthAtAssignment, ")
		.append("wp.dm_planning_remarks as releaseRemarks, ")
		.append("r.id as resourceId, ")
		.append("wp.id as workPeriodId ")
		.append("FROM isw_incident i ")
		.append("left join isw_organization org on i.unit_id = org.id ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join iswl_request_category rc on k.request_category_id = rc.id ")
		.append("left join isw_organization o on r.organization_id = o.id ")
		.append("left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id ")
		.append("left join iswl_jet_port jp on wp.ci_arrival_jet_port_id = jp.id ")
		.append("left join isw_resource_mobilization rm on wp.ci_mobilization_id = rm.id ");
		
		Boolean bWhere=false;
		if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 b.append("where r.id in (:rids) ");
				 bWhere=true;
			 }
		 } 
		 
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if (filter.getIncidentId() > 0) {
				 if(!bWhere)
					 b.append("where i.id = " + filter.getIncidentId() + " ");
				 else{
					 b.append(" and i.id = " + filter.getIncidentId() + " ");
				 }
			 }
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere){
			 	b.append(" WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
			 	bWhere=true;
			 }else{
			 	b.append(" and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") " );
			 }
		 }
		 
		 if (!CollectionUtility.hasValue(filter.getResourceIds())) {
			 b.append(super.isOracleDialect() ? " and substr(a.request_number, 1, 1) " : " and substring(a.request_number from 1 for 1) ")
			 .append(" in ('C','E','O','A') ")
			 .append(" and a.assignment_status = 'P' ");
			 
			 if (!filter.getIncludeSTTFComponents()) {
					//b.append(" AND k.is_strike_team = " + super.getBooleanComparison(false) + " ");
					b.append(" AND ")
					 .append(" (")
					 .append("   r.parent_resource_id is null ")
					 .append(" ) ");
					/*
					 .append("    or " )
					 .append("   r.parent_resource_id in ")
					 .append("   ( ")
					 .append("     select resource_id from isw_incident_resource ir2 ")
					 .append("                             ,isw_work_period wp2 ")
					 .append("                             ,isw_work_period_assignment wpa2 ")
					 .append("                             ,isw_assignment a2 ")
					 .append("                             ,iswl_kind k2 ")
					 .append("     where ir2.resource_id = r.parent_resource_id ")
					 .append("     and wp2.incident_resource_id = ir2.id ")
					 .append("     and wpa2.work_period_id = wp2.id ")
					 .append("     and a2.id = wpa2.assignment_id " )
					 .append("     and a2.end_date is null ")
					 .append("      and k2.id = a2.kind_id ")
					 .append("     and k2.is_strike_team = " + super.getBooleanComparison(false)+ " ")
					 .append("   ) ")
					 .append(" ) ");
						*/
					//b.append(" AND (r.resource_classification is null or r.resource_classification not in ('ST','TF') ) ");
					 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
			 }else {
				 b.append(" AND (r.parent_resource_id is null or ")
				 .append(" r.parent_resource_id in ")
				 .append(" (select ir2.resource_id ")
				 .append(" from isw_incident_resource ir2 ")
				 .append(" ,isw_work_period wp2 ")
				 .append(" ,isw_work_period_assignment wpa2 ")
				 .append(" ,isw_assignment a2 ")
				 .append(" ,iswl_kind k2 ")
				 .append(" where ir2.resource_id = r.parent_resource_id ")
				 .append(" and wp2.incident_resource_id = ir2.id ")
				 .append(" and wpa2.work_period_id = wp2.id ")
				 .append(" and a2.id = wpa2.assignment_id ")
				 .append(" and a2.end_date is null ")
				 .append(" and k2.id = a2.kind_id ")
				 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
			 }
			 
			 b.append("and wp.dm_is_planning_dispatch_notif = " + super.getBooleanComparison(false));
			 b.append(" and not wp.dm_tentative_release_date is null ");
		 }

		 b.append(" order by i.id, sortrequestnumber(a.request_number) ");
		
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 query.setParameterList("rids", filter.getResourceIds());
			 }
		 }
		 
		 CustomResultTransformer crt = new CustomResultTransformer(AvailableForReleaseReportData.class);
			
		 // since CustomResultTransformer doesn't handle longs too well
		 // we tell it how to handle these fields
         crt.addScalar("incidentId", Long.class.getName());
         crt.addScalar("resourceId", Long.class.getName());
         crt.addScalar("reassignable", Boolean.class.getName());
         crt.addScalar("lengthAtAssignment", Long.class.getName());
         crt.addScalar("workPeriodId", Long.class.getName());
         
		 query.setResultTransformer(crt); 

	     Collection<AvailableForReleaseReportData> availableForReleaseReportData = query.list();
	     
	     if(null != availableForReleaseReportData) {
	    	 
	    	 //get reference number 
	    	 String sql3 = (super.isOracleDialect() ? "SELECT SEQ_RPT_REFERENCE_NUM.NEXTVAL FROM DUAL "
					    : "SELECT nextVal('SEQ_RPT_REFERENCE_NUM') ");
	    	 SQLQuery query3 = super.getHibernateSession().createSQLQuery(sql3.toString());

	    	 List result = query3.list();
	    	 
	    	 for (AvailableForReleaseReportData arrd : availableForReleaseReportData) {
	    		 
		    	 //Get other quals
		    	 StringBuffer sql2 = new StringBuffer();
		    	 if (null != arrd.getResourceId() && arrd.getResourceId() > 0) {
		    		 String strCase = (super.isOracleDialect()==true
		    				 			? "(case when rk.is_training = 1 then k.code || ' (t)' else k.code end) as qual "
		    				 			: "(case when rk.is_training = true then k.code || ' (t)' else k.code end) as qual "
		    				 		  );
		    		 sql2.append("select " + strCase + " ")
		    		 .append("from iswl_kind k ")
		    		 .append("join isw_resource_kind rk on k.id = rk.kind_id ")
		    		 .append("where rk.resource_id = " + arrd.getResourceId())
		    		 .append(" and not k.code in ")
		    		 .append("(select cp.code as curPos ")
		    		 .append("from iswl_kind cp ")
		    		 .append("join isw_assignment a on a.kind_id = cp.id ")
		    		 .append("join isw_work_period_assignment wpa on a.id = wpa.assignment_id ")
		    		 .append("join isw_work_period wp on wpa.work_period_id = wp.id ")
		    		 .append("join isw_incident_resource ir on wp.incident_resource_id = ir.id ")
		    		 .append("join isw_resource r on ir.resource_id = r.id ")
		    		 .append("where r.id = " + arrd.getResourceId() + ")");
		    	 }
		    	 SQLQuery query2 = super.getHibernateSession().createSQLQuery(sql2.toString());
		    	 String otherQualsData = query2.list().toString();
		    	 otherQualsData.replace(arrd.getKindCode(), "");
		    	 arrd.setOtherQuals(otherQualsData.toString().substring(1, otherQualsData.toString().length() - 1));
		 
		    	//set reference number
		    	 if(null != result){
		    		 arrd.setReferenceNumber(String.valueOf(result.get(0)));
		    	 }
	    	 }
	     }
	     
	     return availableForReleaseReportData;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getAirTravelRequestReportData(gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AirTravelRequestReportData> getAirTravelRequestReportData(AirTravelRequestReportFilter filter) throws PersistenceException {
		StringBuffer b = new StringBuffer();
		b.append("select distinct i.incident_name as incidentName, ")
		.append("i.id as incidentId, ")
		.append("i.nbr as incidentNumber, ")
		.append("org.unit_code as incidentUnit, ")
		.append("wp.dm_tentative_release_date as tentativeReleaseDate, ")
		.append("a.request_number as requestNumber, ")
		.append("r.name_on_picture_id as resourceName, ")
		.append("r.last_name as lastName, ")
		.append("r.id as resourceId, ")
		.append("o.unit_code as unitId, ")
		.append("wp.dm_tentative_demob_city as demobCity, ")
		.append("c.cs_abbreviation as demobState, ")
		.append("at.remarks as specialInstructions, ")
		.append("at.hours_to_airport as travelTimeHours, ")
		.append("at.minutes_to_airport as travelTimeMinutes, ")
		.append("jpd.code as departJetport, ")
		.append("jpd.description as departCity, ")
		.append("departcs.cs_abbreviation as departState, ")
		.append("jpr.code as returnJetport, ")
		.append("jpr.description as returnCity, ")
		.append("returncs.cs_abbreviation as returnState, ")
		.append("wp.ci_first_work_date as firstWorkDate, ")
		.append("wp.ci_length_at_assignment as lengthAtAssignment, ")
		.append("wp.dm_is_reassignable as reassignment, ")
		.append("a.assignment_status as demobStatus, ")
		.append("wp.id as workPeriodId, ")
		.append("wp.dm_travel_method as travelMethod ")
		.append("FROM isw_incident i ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
	    .append("left join isw_organization org on i.unit_id = org.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join isw_air_travel at on wp.dm_air_travel_id = at.id ")
		.append("left join iswl_jet_port jpd on at.jet_port_id = jpd.id ")
		.append("left join iswl_country_subdivision departcs on jpd.country_subdivision_id = departcs.id ")
		.append("left join iswl_jet_port jpr on wp.ci_arrival_jet_port_id = jpr.id ")
		.append("left join iswl_country_subdivision returncs on jpr.country_subdivision_id = returncs.id ")
		.append("left join isw_organization o on r.organization_id = o.id ")
		.append("left join iswl_country_subdivision c on wp.dm_tentative_demob_state_id = c.id ");

		Boolean bWhere=false;
		
		if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 b.append("where r.id in (:rids) ");
				 bWhere=true;
			 }
		 } 
		 
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if (filter.getIncidentId() > 0) {
				 if(!bWhere)
					 b.append("where i.id = " + filter.getIncidentId() + " ");
				 else{
					 b.append(" and i.id = " + filter.getIncidentId() + " ");
				 }
			 }
		 }else if (null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere)
				 b.append("where i.id in ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + filter.getIncidentGroupId() + " ) ");
			 else{
				 b.append(" and i.id in ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + filter.getIncidentGroupId() + " ) ");
			 }
			 
		 }
		
		 if (null == filter.getResourceIds() || filter.getResourceIds().size() < 1) {
			 b.append(" and r.is_person = " + super.getBooleanComparison(true) + " ");
			 
			 if ((null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0) || (null != filter.getIncidentId() && filter.getIncidentId() != 0)) {	 
				 b.append(" and a.assignment_status = 'P' ");
				 
				 if (!filter.getIncludeSTTFComponents()) {
						//b.append(" AND k.is_strike_team = " + super.getBooleanComparison(false) + " ");
//						b.append(" AND ")
//						 .append(" (")
//						 .append("   r.parent_resource_id is null ")
//						 .append(" )");
						/*
						 .append("    or " )
						 .append("   r.parent_resource_id in ")
						 .append("   ( ")
						 .append("     select resource_id from isw_incident_resource ir2 ")
						 .append("                             ,isw_work_period wp2 ")
						 .append("                             ,isw_work_period_assignment wpa2 ")
						 .append("                             ,isw_assignment a2 ")
						 .append("                             ,iswl_kind k2 ")
						 .append("     where ir2.resource_id = r.parent_resource_id ")
						 .append("     and wp2.incident_resource_id = ir2.id ")
						 .append("     and wpa2.work_period_id = wp2.id ")
						 .append("     and a2.id = wpa2.assignment_id " )
						 .append("     and a2.end_date is null ")
						 .append("      and k2.id = a2.kind_id ")
						 .append("     and k2.is_strike_team = " + super.getBooleanComparison(false)+ " ")
						 .append("   ) ")
						 .append(" ) ");
							*/
						//b.append(" AND (r.resource_classification is null or r.resource_classification not in ('ST','TF') ) ");
						 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
				 }else {
//					 b.append(" AND (r.parent_resource_id is null or ")
//					 .append(" r.parent_resource_id in ")
//					 .append(" (select ir2.resource_id ")
//					 .append(" from isw_incident_resource ir2 ")
//					 .append(" ,isw_work_period wp2 ")
//					 .append(" ,isw_work_period_assignment wpa2 ")
//					 .append(" ,isw_assignment a2 ")
//					 .append(" ,iswl_kind k2 ")
//					 .append(" where ir2.resource_id = r.parent_resource_id ")
//					 .append(" and wp2.incident_resource_id = ir2.id ")
//					 .append(" and wpa2.work_period_id = wp2.id ")
//					 .append(" and a2.id = wpa2.assignment_id ")
//					 .append(" and a2.end_date is null ")
//					 .append(" and k2.id = a2.kind_id ")
//					 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
				 }
				 
				 b.append(" and at.is_dispatch_notified = " + super.getBooleanComparison(false) + " ");
			 }

			 b.append("and (wp.dm_travel_method = 'AIR' or wp.dm_travel_method = 'AR') ");
		 }
		 
		 // add some additional checks, this info is needed to get calcDate
		// b.append("and wp.ci_first_work_date is not null ");
		 //b.append("and wp.ci_length_at_assignment > 0 ");
		 b.append(" order by incidentId, lastName ");
		 
		
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 query.setParameterList("rids", filter.getResourceIds());
			 }
		 }
		 
		 CustomResultTransformer crt = new CustomResultTransformer(AirTravelRequestReportData.class);
			
		 // since CustomResultTransformer doesn't handle longs too well
		 // we tell it how to handle these fields
         crt.addScalar("incidentId", Long.class.getName());
         crt.addScalar("workPeriodId", Long.class.getName());
         crt.addScalar("resourceId", Long.class.getName());
         crt.addScalar("travelTimeHours", Integer.class.getName());
         crt.addScalar("travelTimeMinutes", Integer.class.getName());
         crt.addScalar("lengthAtAssignment", Long.class.getName());
         crt.addScalar("reassignment", Boolean.class.getName());
         
		 query.setResultTransformer(crt); 

	     Collection<AirTravelRequestReportData> airTravelRequestReportData = query.list();
	     
	     if(null != airTravelRequestReportData) {
	    	 for (AirTravelRequestReportData atrd : airTravelRequestReportData) {
	    		 //Get air travel questions
		    	 StringBuffer sql2 = new StringBuffer();
		    	 if (null != filter.getIncidentId()) {
		    		 sql2.append("select q.question as question, ")
		    		 .append("iq.position as position, ")
		    		 .append("wpqv.question_value as questionValue ")
		    		 .append("from isw_question q ")
		    		 .append("join isw_incident_question iq on q.id = iq.question_id ")
		    		 .append("left join isw_work_period_question_value wpqv on iq.id = wpqv.incident_question_id ")
		    		 .append("left join isw_work_period wp on wpqv.work_period_id = wp.id ")
		    		 .append("left join isw_incident_resource ir on wp.incident_resource_id = ir.id ")
		    		 .append("where q.question_type = 'AIRTRAVEL' ")
		    		 .append(" and iq.is_visible = " + super.getBooleanComparison(true) + " ")
		    		 .append("and iq.incident_id = " + atrd.getIncidentId())
		    		 .append(" and ir.resource_id = " + atrd.getResourceId())
		    		 .append(" order by iq.position ");
		    	 }
		    	 
		    	 SQLQuery query2 = super.getHibernateSession().createSQLQuery(sql2.toString());
		    	 CustomResultTransformer crt2 = new CustomResultTransformer(AirTravelQuestionReportData.class);
		    	 crt2.addScalar("position", Integer.class.getName());
		    	 crt2.addScalar("questionValue", Boolean.class.getName());
		    	 
		    	 query2.setResultTransformer(crt2);
		    	 ArrayList<AirTravelQuestionReportData> airTravelQuestionData = (ArrayList)query2.list();
		    	 
		    	 atrd.setTravelQuestionData(airTravelQuestionData);
		    	 
		    	 //Get reassignment quals
		    	 StringBuffer sql3 = new StringBuffer();
		    	 if (null != atrd.getResourceId() && atrd.getResourceId() > 0) {
		    		 sql3.append("select k.code || ")
		    		 .append("case when rk.is_training = " + (super.isOracleDialect() ? 1 : Boolean.TRUE) + " then ' (t)' else '' end as code ")
		    		 .append("from iswl_kind k ")
		    		 .append("join isw_resource_kind rk on k.id = rk.kind_id ")
		    		 .append("where rk.resource_id = " + atrd.getResourceId());
		    	 }
		    	 SQLQuery query3 = super.getHibernateSession().createSQLQuery(sql3.toString());
		    	 String reassignmentQualsData = query3.list().toString();
		    	 atrd.setReassignmentQuals(reassignmentQualsData.toString().substring(1, reassignmentQualsData.toString().length() - 1));
		    	 
	    	 }
	     }
	     
	     return airTravelRequestReportData;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getGroundSupportReportData(gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<GroundSupportReportData> getGroundSupportReportData(GroundSupportReportFilter filter) throws PersistenceException {
		 StringBuffer b = new StringBuffer();
		 b.append("select i.incident_name as incidentName, ")
		  .append("i.nbr as incidentNumber, ")
		  .append("i.id as incidentId, ")
		  .append("org.unit_code as incidentUnit, ")
		  .append("case ")
	      .append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
	      .append("else ")
	      .append("r.last_name || ', ' || r.first_name ")
	      .append("end as resourceName, ")
		  .append("at.leave_time as leaveTimeInt, ")
		  .append("jp.description as airport, ")
		  .append("at.airline as airline, ")
		  .append("at.flight_time as flightTimeInt, ")
		  .append("wp.dm_tentative_release_date as tentativeReleaseDate ")
		  .append("FROM isw_incident i ")
		  .append("left join isw_organization org on i.unit_id = org.id ")
		  .append("join isw_incident_resource ir on i.id = ir.incident_id ")
		  .append("join isw_resource r on ir.resource_id = r.id ")
		  .append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		  .append("left join isw_air_travel at on wp.dm_air_travel_id = at.id ")
		  .append("left join iswl_jet_port jp on at.jet_port_id = jp.id ")
		  .append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		  .append("left join isw_assignment a on wpa.assignment_id = a.id  ")
		  .append("left join iswl_kind k on a.kind_id = k.id ");

		 Boolean bWhere = false;
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 b.append("where r.id in (:rids) ");
				 bWhere=true;
			 }
		 }

		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if(!bWhere){
				b.append("where i.id = " + filter.getIncidentId() +  " ");
				bWhere=true;
			 }else{
				b.append(" and i.id = " + filter.getIncidentId() +  " ");
			 }
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere){
				 b.append("where i.id in ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + filter.getIncidentGroupId() + " ) ");
				bWhere=true;
			 }else{
				 b.append(" and i.id in ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + filter.getIncidentGroupId() + " ) ");
			 }
		 }
			 
		 if (null == filter.getResourceIds() || filter.getResourceIds().size() < 1) {
			 b.append("and a.assignment_status <> 'D' ");
			 
			 if( (null != filter.getTentativeReleaseDateStartRange()) && (!filter.getTentativeReleaseDateStartRange().isEmpty()))
				 b.append("and to_date(to_char(wp.dm_tentative_release_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= " + super.getDateStringComparison(filter.getTentativeReleaseDateStartRange()));
				 //b.append("and wp.dm_tentative_release_date >= " + super.getDateStringComparison(filter.getTentativeReleaseDateStartRange()));
			  
			 if( (null != filter.getTentativeReleaseDateEndRange()) && (!filter.getTentativeReleaseDateEndRange().isEmpty()))
				 b.append(" and to_date(to_char(wp.dm_tentative_release_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= " + super.getDateStringComparison(filter.getTentativeReleaseDateEndRange()) );

			 b.append("and wp.dm_travel_method in ('AIR','AR') ");
			 
			 if (!filter.getIncludeSTTFComponents()) {
					//b.append(" AND k.is_strike_team = " + super.getBooleanComparison(false) + " ");
					b.append(" AND ")
					 .append(" (")
					 .append("   r.parent_resource_id is null ")
					 .append(" ) ");
					/*
					 .append("    or " )
					 .append("   r.parent_resource_id in ")
					 .append("   ( ")
					 .append("     select resource_id from isw_incident_resource ir2 ")
					 .append("                             ,isw_work_period wp2 ")
					 .append("                             ,isw_work_period_assignment wpa2 ")
					 .append("                             ,isw_assignment a2 ")
					 .append("                             ,iswl_kind k2 ")
					 .append("     where ir2.resource_id = r.parent_resource_id ")
					 .append("     and wp2.incident_resource_id = ir2.id ")
					 .append("     and wpa2.work_period_id = wp2.id ")
					 .append("     and a2.id = wpa2.assignment_id " )
					 .append("     and a2.end_date is null ")
					 .append("      and k2.id = a2.kind_id ")
					 .append("     and k2.is_strike_team = " + super.getBooleanComparison(false)+ " ")
					 .append("   ) ")
					 .append(" ) ");
					 */
					//b.append(" AND (r.resource_classification is null or r.resource_classification not in ('ST','TF') ) ");
					 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
			 }else {
				 b.append(" AND (r.parent_resource_id is null or ")
				 .append(" r.parent_resource_id in ")
				 .append(" (select ir2.resource_id ")
				 .append(" from isw_incident_resource ir2 ")
				 .append(" ,isw_work_period wp2 ")
				 .append(" ,isw_work_period_assignment wpa2 ")
				 .append(" ,isw_assignment a2 ")
				 .append(" ,iswl_kind k2 ")
				 .append(" where ir2.resource_id = r.parent_resource_id ")
				 .append(" and wp2.incident_resource_id = ir2.id ")
				 .append(" and wpa2.work_period_id = wp2.id ")
				 .append(" and a2.id = wpa2.assignment_id ")
				 .append(" and a2.end_date is null ")
				 .append(" and k2.id = a2.kind_id ")
				 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
			 }
			 
			 // 07/31/2012 djp
			 b.append("and wp.is_ground_support = " + super.getBooleanComparison(true) + " " );
		 }
					 
		 b.append(" order by i.id, to_date(to_char( wp.dm_tentative_release_date, 'MMDDYYYY'),'MM/DD/YYYY'), leaveTimeInt, resourceName ");
		 
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 query.setParameterList("rids", filter.getResourceIds());
			 }
		 }
		 
		 CustomResultTransformer crt = new CustomResultTransformer(GroundSupportReportData.class);
		 crt.addScalar("incidentId", Long.class.getName());
		 crt.addScalar("leaveTimeInt", Integer.class.getName());
		 crt.addScalar("flightTimeInt", Integer.class.getName());
		
		 query.setResultTransformer(crt); 

	     return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getActualDemobReportData(gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ActualDemobReportData> getActualDemobReportData(ActualDemobReportFilter filter) throws PersistenceException {
		 StringBuffer b = new StringBuffer();
		 b.append("select i.incident_name as incidentName, ")
		  .append("i.nbr as incidentNumber, ")
		  .append("i.id as incidentId, ")
	      .append("org.unit_code as incidentUnit, ")
	      .append("case ")
	      .append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
	      .append("else ")
	      .append("r.last_name || ', ' || r.first_name ")
	      .append("end as resourceName, ")
	   	  .append("org2.unit_code as unitId, ")
	   	  .append("a.request_number as requestNumber, ")
	   	  .append("wp.dm_release_date as actualReleaseDate, ")
	   	  .append("wp.dm_tentative_demob_city as demobCity, ")
	   	  .append("cs.cs_abbreviation as demobState, ")
	   	  .append("wp.dm_travel_method as returnTravelMethod, ")
	   	  .append("wp.dm_release_remarks as remarks, ")
	   	  .append("wp.dm_tentative_arrival_date as estimatedArrivalDate, ")
	   	  .append("r.id as resourceId, ")
	   	  .append("wp.id as workPeriodId ")
		  .append("FROM isw_incident i ")
		  .append("left join isw_organization org on i.unit_id = org.id ")
		  .append("join isw_incident_resource ir on i.id = ir.incident_id ")
		  .append("join isw_resource r on ir.resource_id = r.id ")
		  .append("left join isw_organization org2 on r.organization_id = org2.id ")
		  .append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		  .append("left join isw_air_travel at on wp.dm_air_travel_id = at.id ")
		  .append("left join iswl_jet_port jp on at.jet_port_id = jp.id ")
		  .append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		  .append("left join isw_assignment a on wpa.assignment_id = a.id  ")
		  .append("left join iswl_kind k on a.kind_id = k.id ")
		  .append("left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id ")
		  ;

		 Boolean bWhere = false;
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 b.append("where r.id in (:rids) ");
				 bWhere=true;
			 }
		 }
		 
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 if(!bWhere){
				 b.append(" where i.id = " + filter.getIncidentId() + " ");
				 bWhere=true;
			 }else{
				 b.append(" and i.id = " + filter.getIncidentId() + " ");
			 }
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 if(!bWhere){
				 b.append(" where i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
				 bWhere=true;
			 }else{
				 b.append(" and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
			 }
		 }
	     
		 if (null == filter.getResourceIds() || filter.getResourceIds().size() == 0) {
			 //b.append(" and a.assignment_status <> 'D' ")
			  b.append(" and not wp.dm_release_date is null ")
			  .append("and wp.dm_is_release_dispatch_notif = " + super.getBooleanComparison(false));
				 
				 if (filter.getIncludeSTTFComponents()) {
					 b.append(" AND (r.parent_resource_id is null or ")
					 .append(" r.parent_resource_id in ")
					 .append(" (select ir2.resource_id ")
					 .append(" from isw_incident_resource ir2 ")
					 .append(" ,isw_work_period wp2 ")
					 .append(" ,isw_work_period_assignment wpa2 ")
					 .append(" ,isw_assignment a2 ")
					 .append(" ,iswl_kind k2 ")
					 .append(" where ir2.resource_id = r.parent_resource_id ")
					 .append(" and wp2.incident_resource_id = ir2.id ")
					 .append(" and wpa2.work_period_id = wp2.id ")
					 .append(" and a2.id = wpa2.assignment_id ")
					 .append(" and a2.end_date is null ")
					 .append(" and k2.id = a2.kind_id ")
					 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
				 } else {
						//b.append(" AND k.is_strike_team = " + super.getBooleanComparison(false) + " ");
						b.append(" AND ")
						 .append(" (")
						 .append("   r.parent_resource_id is null ")
						 .append(" ) ");
						/*
						 .append("    or " )
						 .append("   r.parent_resource_id in ")
						 .append("   ( ")
						 .append("     select resource_id from isw_incident_resource ir2 ")
						 .append("                             ,isw_work_period wp2 ")
						 .append("                             ,isw_work_period_assignment wpa2 ")
						 .append("                             ,isw_assignment a2 ")
						 .append("                             ,iswl_kind k2 ")
						 .append("     where ir2.resource_id = r.parent_resource_id ")
						 .append("     and wp2.incident_resource_id = ir2.id ")
						 .append("     and wpa2.work_period_id = wp2.id ")
						 .append("     and a2.id = wpa2.assignment_id " )
						 .append("     and a2.end_date is null ")
						 .append("      and k2.id = a2.kind_id ")
						 .append("     and k2.is_strike_team = " + super.getBooleanComparison(false)+ " ")
						 .append("   ) ")
						 .append(" ) ");
						 */
				 }
			 
			 b.append(" and a.end_date is null ");
		 }
		
		 b.append(" order by i.id ")
		  .append(", sortrequestnumber(a.request_number)");
//		  .append(super.isOracleDialect() ? ", substr(a.request_number, 1, 1) "
//				  : ", substring(a.request_number from 1 for 1) ");
		 
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 if (null != filter.getResourceIds()) {
			 if (filter.getResourceIds().size() > 0) {
				 query.setParameterList("rids", filter.getResourceIds());
			 }
		 }
		 
		 CustomResultTransformer crt = new CustomResultTransformer(ActualDemobReportData.class);
		 crt.addScalar("incidentId", Long.class.getName());
		 crt.addScalar("resourceId", Long.class.getName());
		 crt.addScalar("workPeriodId", Long.class.getName());
		 crt.addScalar("estimatedArrivalDate", Date.class.getName());
		 crt.addScalar("restOverNight", Boolean.class.getName());
		
		 query.setResultTransformer(crt);
	     Collection<ActualDemobReportData> list = query.list();
	     Collection<ActualDemobReportData> newList = new ArrayList<ActualDemobReportData>();
	     
	     
	     for(ActualDemobReportData data : list){
	    	 ArrayList<ActualDemobRestOverNightData> actualDemobRestOverNightDataCollection = new ArrayList<ActualDemobRestOverNightData>();
	    	 
	    	 if(null != data.getWorkPeriodId() && data.getWorkPeriodId() > 0){
		    	 Criteria crit2 = getHibernateSession().createCriteria(WorkPeriodImpl.class);
		    	 crit2.add(Restrictions.eq("id", data.getWorkPeriodId()));
		    	 
		    	 WorkPeriod wp = (WorkPeriod)crit2.uniqueResult();
		    	 if(null != wp){
		    		 if(null != wp.getWpOvernightStayInfos() && wp.getWpOvernightStayInfos().size() > 0){
		    			 
		    			 int wpOvernightCount=wp.getWpOvernightStayInfos().size();
		    			 int cnt=0;
		    			 for(WorkPeriodOvernightStayInfo si : wp.getWpOvernightStayInfos()){
		    				 cnt++;
			    			 ActualDemobRestOverNightData actualDemobRestOverNightData = new ActualDemobRestOverNightData();
			    			 actualDemobRestOverNightData.setRecordCount(wp.getWpOvernightStayInfos().size());
		    				 CountrySubdivision state = null;
		    				 
		    				 if(null != si.getStateId()){
	    						 // having to do another criteria, lazy loading not working here
	    						 Criteria crit3 = getHibernateSession().createCriteria(CountrySubdivisionImpl.class);
	    						 crit3.add(Restrictions.eq("id",si.getStateId()));
	    						 state = (CountrySubdivision)crit3.uniqueResult();
	    					 }
		    				 
		    				 actualDemobRestOverNightData.setRestOverNightCity(si.getCity());
		    				 actualDemobRestOverNightData.setRestOverNightState(state.getAbbreviation());
		    				 if(cnt==wpOvernightCount){
			    				 actualDemobRestOverNightData.setEstimatedArrivalDate(wp.getDmTentativeArrivalDate());
		    				 }
		    				 actualDemobRestOverNightData.setRestOverNight(wp.isDMRestOvernight());
		    				 
		    				 actualDemobRestOverNightDataCollection.add(actualDemobRestOverNightData);
		    				 
		    			 }
		    		 }
		    	 }
	    	 }
	    	 data.setRestOverNightData(actualDemobRestOverNightDataCollection);
	    	 newList.add(data);
	     }
	     
	     return newList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportDemobDao#getLastWorkDayReportData(gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<LastWorkDayReportData> getLastWorkDayReportData(LastWorkDayReportFilter filter) throws PersistenceException {
		StringBuffer lastWorkDayCalculation = new StringBuffer();
		 lastWorkDayCalculation.append("case ")
		 						.append("when not wp.dm_tentative_release_date is null then ")
		 						.append(super.isOracleDialect() ? "  TO_TIMESTAMP(wp.dm_tentative_release_date - 1) "
		 								  : " (wp.dm_tentative_release_date - interval '1 day') ")
		 						.append("else ")
		 						.append(super.isOracleDialect() ? "  TO_TIMESTAMP(ci_first_work_date + ci_length_at_assignment - 1) "
				                          : " (ci_first_work_date + interval '1 day' * ci_length_at_assignment - interval '1 day') ")
		 						.append("end ");
		
		StringBuffer b = new StringBuffer();
		 b.append("select i.id as incidentId, ")
		  .append("i.incident_name as incidentName, ")
		  .append("i.nbr as incidentNumber, ")
	      .append("org.unit_code as incidentUnit, ")
		  .append("case ")
		  .append("when r.is_person = "+super.getBooleanComparison(false)+ "  then r.resource_name ")
		  .append("else ")
		  .append("r.last_name || ', ' || r.first_name ")
		  .append("end as resName, ")
		  .append("r.resource_name as resourceName, ")
	   	  .append("r.last_name as lastName, ")
	   	  .append("r.first_name as firstName, ")
	   	  .append("a.request_number as requestNumber, ")
	   	  .append(lastWorkDayCalculation + " as lastWorkDate, ")
	   	  .append("rm.start_date as startDate, ")
	   	  .append("wp.ci_length_at_assignment as lengthAtAssignment, ")
	   	  .append("d.description as section,  ")
	   	  .append("rc.description as resourceCategory, ")
	   	  .append("k.code as kindCode ")
		  .append("FROM isw_incident i ")
		  .append("left join isw_organization org on i.unit_id = org.id ")
		  .append("join isw_incident_resource ir on i.id = ir.incident_id ")
		  .append("join isw_resource r on ir.resource_id = r.id ")
		  .append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		  .append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		  .append("left join isw_assignment a on wpa.assignment_id = a.id ")
		  .append("left join isw_resource_mobilization rm on wp.ci_mobilization_id = rm.id ")
		  .append("left join iswl_kind k on a.kind_id = k.id ")
		  .append("left join iswl_department d on k.department_id = d.id ")
		  .append("left join iswl_request_category rc on k.request_category_id = rc.id ");
		  
		 if (null != filter.getIncidentId() && filter.getIncidentId() != 0) {
			 b.append("where i.id = " + filter.getIncidentId() + " ");
		 }else if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() != 0){
			 b.append(" where i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
		 }
			 
		 if (!filter.getIncludeSTTFComponents()) {
			 //b.append(" and (r.resource_classification is null or r.resource_classification not in ('ST','TF') ) ");
			 //b.append(" and r.is_component = " + super.getBooleanComparison(false) + " ");
			 //b.append(" and k.is_strike_team != " + super.getBooleanComparison(true) + " ");
			 b.append(" and " );
			 b.append("( ")
			  .append("  r.parent_resource_id is null ")
			  .append(" ) ");
			 /*
			  .append("   or ")
			  .append("  r.parent_resource_id not in ")
			  .append("   ( ")
			  .append("      select resource_id from isw_incident_resource ir2 ")
			  .append("        left join isw_work_period wp2 on ir2.id = wp2.incident_resource_id ")
			  .append("        left join isw_work_period_assignment wpa2 on wp2.id = wpa2.work_period_id ")
			  .append("        left join isw_assignment a2 on wpa2.assignment_id = a2.id ")
			  .append("        left join iswl_kind k2 on a2.kind_id = k2.id ")
			  .append("      where resource_id = r.parent_resource_id ")
			  .append("      and k2.is_strike_team = " + super.getBooleanComparison(true) + " ")
			  .append("    ) ")
			  .append(") ");
			  */
		 }else {
			 b.append(" AND (r.parent_resource_id is null or ")
			 .append(" r.parent_resource_id in ")
			 .append(" (select ir2.resource_id ")
			 .append(" from isw_incident_resource ir2 ")
			 .append(" ,isw_work_period wp2 ")
			 .append(" ,isw_work_period_assignment wpa2 ")
			 .append(" ,isw_assignment a2 ")
			 .append(" ,iswl_kind k2 ")
			 .append(" where ir2.resource_id = r.parent_resource_id ")
			 .append(" and wp2.incident_resource_id = ir2.id ")
			 .append(" and wpa2.work_period_id = wp2.id ")
			 .append(" and a2.id = wpa2.assignment_id ")
			 .append(" and a2.end_date is null ")
			 .append(" and k2.id = a2.kind_id ")
			 .append(" and k2.is_strike_team = " + super.getBooleanComparison(true) + "))");
		 }
			 
			 if (!filter.getIncludeAllDates()) {
				 if (null != filter.getReportStartDate()) {
					 b.append(" and " + lastWorkDayCalculation)
					 .append(" >= " + super.getDateStringComparison(DateUtil.toDateString(filter.getReportStartDate())));
				 }
					 
				 if (null != filter.getReportEndDate()) {
					 b.append(" and " + lastWorkDayCalculation)
					 .append(" <= " +  super.getDateStringComparison(DateUtil.toDateString(filter.getReportEndDate())));
				 }
			 }else{
				 //b.append("and lastWorkDate is not null ");
			 }
			 
			 b.append(" and a.assignment_status != 'D' ")
			 .append(" and ((not ci_first_work_date is null and (ci_length_at_assignment != 0 or ci_length_at_assignment is null)) or not wp.dm_tentative_release_date is null) ")
			 .append(" and (" + (super.isOracleDialect()? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'C' ")
			 .append(" or" + (super.isOracleDialect()? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'E' ")
			 .append(" or" + (super.isOracleDialect()? " substr(a.request_number, 1, 1) " : " substring(a.request_number from 1 for 1) " ) + " = 'O') ")
			 
			 .append(" and wp.dm_release_date is null ");
			 
			 if ((null != LastWorkDayReportFilter.getSections(filter)) && (!LastWorkDayReportFilter.getSections(filter).isEmpty())) {
				 b.append(" and d.code in ( " + LastWorkDayReportFilter.getSections(filter) + ") ");
			 }
			 
			 b.append(" order by i.id ");
			 
			 if(filter.getGroupByDateResourceCategory()) {
				 b.append(", lastWorkDate, resourceCategory ");
			 }else if(filter.getGroupBySectionDate()){
				 b.append(", section, lastWorkDate ");
			 }else if(filter.getGroupBySectionResourceCategoryDate()){
				 b.append(", section, resourceCategory, lastWorkDate ");
			 }
			 
			 if (null != filter.getSortBy() && filter.getSortBy().size() > 0) {
				 String sorting="";
				 for(String sort : filter.getSortBy()){
					 
					 if(sort.equals("itemCode")){
						 sorting = sorting + ",kindCode";
						 //sorting = sorting + (sorting.length() < 1 ? "kindCode" : ",kindCode" );
					 }else if(sort.equals("requestNumber")){
						 sorting = sorting + ",sortrequestnumber(a.request_number)";
						 //sorting = sorting + (sorting.length() < 1 ? "requestNumber" : ",requestNumber" );
					 }else if(sort.equals("name")){
						 sorting = sorting + ",resName";
						 //sorting = sorting + (sorting.length() < 1 ? "resName" : ",resName" );
					 }else if(sort.equals("requestCategory")){
						 sorting = sorting + ",resourceCategory";
						 //sorting = sorting + (sorting.length() < 1 ? "resourceCategory" : ",resourceCategory" );
					 }
				 }
				 
				 if(sorting.length() > 0){
					 b.append(sorting + " ");
				 }
			 }
		 
		 SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());
		 
		 CustomResultTransformer crt = new CustomResultTransformer(LastWorkDayReportData.class);
		
		 crt.addScalar("incidentId", Long.class.getName());
		 crt.addScalar("lengthAtAssignment", BigInteger.class.getName());
		
		 query.setResultTransformer(crt);
	     return query.list();
	}
}
