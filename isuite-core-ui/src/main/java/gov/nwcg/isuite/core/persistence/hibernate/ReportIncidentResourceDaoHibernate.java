package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.persistence.ReportIncidentResourceDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ICS209Query;
import gov.nwcg.isuite.core.persistence.hibernate.query.ICS209Query2;
import gov.nwcg.isuite.core.persistence.hibernate.query.ICS209QueryResources;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceReportQuery;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.core.reports.data.QualificationsReportData;
import gov.nwcg.isuite.core.reports.data.StrikeTeamTaskForceReportData;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.vo.ICS209ResourceData;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import org.hibernate.SQLQuery;

public class ReportIncidentResourceDaoHibernate extends TransactionSupportImpl implements ReportIncidentResourceDao {


	public ReportIncidentResourceDaoHibernate() {

	}


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getAllIncidentResourcesReportData(gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AllIncidentResourcesReportData> getAllIncidentResourcesReportData(AllIncidentResourcesReportFilter filter) throws PersistenceException{

		StringBuffer b = new StringBuffer();
		b.append("SELECT i.incident_name as incidentName")
		.append(",i.nbr as incidentNumber")
		.append(",i.id as incidentId")
		.append(",io.unit_code as incidentUnit")
		.append(",d.description as section");
		if(super.isOracleDialect()){
			b.append(", case ")
			.append("when r.is_person = 0 then r.resource_name ")
			.append("else ")
			.append("r.last_name || ', ' || r.first_name ")
			.append("end as resourceName ");
		}else{
			b.append(", case ")
			.append("when r.is_person = false then r.resource_name ")
			.append("else ")
			.append("r.last_name || ', ' || r.first_name ")
			.append("end as resourceName ");
		}
		b.append(", r.phone as contactPhone")
		.append(",ag.agency_code as agency")
		.append(",org.unit_code as unit")
		.append(",wp.dm_tentative_demob_city as demobCity")
		.append(",wp.dm_travel_method as returnTravelMethod")
		.append(",wp.ci_check_in_date as checkInDate")
		.append(",a.assignment_status as status")
		.append(",cs.cs_abbreviation as demobState")
		.append(",k.code as kind")
		.append(",rq.description as requestCategory")
		.append(",a.request_number as requestNumber")
		.append(",r.is_person as person")
		.append(", r.number_of_personnel as numberOfPersonnel ")
		.append(", rm.start_date as mobDate")
		.append(", wp.ci_first_work_date as firstWorkDate")
		.append(", sortrequestnumber(a.request_number) as requestNumberSortValue ")
		.append(", wp.ci_length_at_assignment as lngOfAssignment");
		//.append(", wp.dm_tentative_release_date as demobDate ")

		if(isOracleDialect()){
			b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
					"then (wp.ci_first_work_date + (wp.ci_length_at_assignment)) " +
			"end as demobDate"); 
		}else{
			b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
					//"then (ci_first_work_date + interval '1 day' * ci_length_at_assignment - interval '1 day') " +
					"then (ci_first_work_date + interval '1 day' * ci_length_at_assignment) " +
			"end as demobDate");
		}

		b.append(", ( select r2.last_name || ', ' || r2.first_name from isw_resource r2 ")
		.append("     where r2.parent_resource_id = r.id and r2.leader_type = 1 and r2.deleted_date is null "+
				" and r2.id = (select max(id) from isw_resource where parent_resource_id = r.id and leader_type=1 ) ) as leaderName ");

		for(String field : filter.getAllResourcesSort()){
			b.append(", ");
			if(field.equals("requestNumber")){
				b.append("a.request_number");
			}else if(field.equals("resourceName")){
				b.append(" case ")
				.append("when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
				.append("else ")
				.append("r.resource_name ")
				.append("end ");
			}else if(field.equals("itemCode")){
				b.append("k.code");
			}else if(field.equals("unitId")){
				b.append("org.unit_code");
			}else if(field.equals("agencyCode")){
				b.append("ag.agency_code");
			}else if(field.equals("assignmentStatus")){
				b.append("a.assignment_status");
			}else if(field.equals("checkInDate")){
				b.append("wp.ci_check_in_date");
			}else if(field.equals("mobDate")){
				b.append("rm.start_date");
			}else if(field.equals("firstWorkDay")){
				b.append("wp.ci_first_work_date");
			}else if(field.equals("lengthAtAssignment")){
				b.append("wp.ci_length_at_assignment");
			}else if(field.equals("demobDate")){
				b.append("wp.dm_tentative_release_date");
			}else if(field.equals("mobilizationTravelMethod")){
				b.append("wp.dm_travel_method");
			}else if(field.equals("cellPhone")){
				b.append("r.phone");
			}

			b.append(" as firstSort");
			break;
		}

		b.append(" FROM isw_incident i")
		.append(" left join isw_organization io on i.unit_id = io.id ")
		.append(",isw_incident_resource ir")
		.append(",isw_resource r")
		.append("  left join iswl_agency ag on r.agency_id = ag.id")
		.append("  left join isw_organization org on r.organization_id = org.id")
		.append(",isw_work_period wp")
		.append("  left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id")
		.append("  left join iswl_jet_port jp on wp.ci_arrival_jet_port_id = jp.id")
		.append("  left join isw_resource_mobilization rm on wp.ci_mobilization_id = rm.id ")
		.append(",isw_work_period_assignment wpa")
		.append(",isw_assignment a")
		.append(",iswl_kind k ")
		.append("   left join iswl_department d on k.department_id = d.id ")
		.append(",iswl_request_category rq");
		if(LongUtility.hasValue(filter.getIncidentId()))
			b.append(" WHERE i.id = "+filter.getIncidentId() + " ");
		else if(LongUtility.hasValue(filter.getIncidentGroupId()))
			b.append(" WHERE i.id in ( select incident_id from isw_incident_group_incident where incident_group_id = " +filter.getIncidentGroupId() + " ) ");
		b.append(" AND ir.incident_id = i.id")
		.append(" AND r.id = ir.resource_id")
		.append(" AND wp.incident_resource_id = ir.id")
		.append(" AND wpa.work_period_id = wp.id")
		.append(" AND a.id = wpa.assignment_id")
		.append(" AND k.id = a.kind_id")
		.append(" AND rq.id = k.request_category_id");

		if(AllIncidentResourcesReportFilter.getTypes(filter).length()>0){
			b.append(" AND rq.code in (" + AllIncidentResourcesReportFilter.getTypes(filter) + ")");
		}

		if(AllIncidentResourcesReportFilter.getStatuses(filter).length() > 0){
			b.append(" AND a.assignment_status in (" + AllIncidentResourcesReportFilter.getStatuses(filter) + ")");
		}

		if(AllIncidentResourcesReportFilter.getSections(filter).length() > 0){
			b.append(" AND d.code in (" + AllIncidentResourcesReportFilter.getSections(filter) + " ) ");
		}

		if(!filter.getStrikeTeamTaskForce()){
			//b.append(" AND k.is_strike_team = " + super.getBooleanComparison(false) + " ");
			b.append(" AND ")
			.append(" (")
			.append("   r.parent_resource_id is null ")
			.append("  ) ");
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
		}

		b.append(" ORDER BY incidentName, incidentNumber, requestCategory ");

		if(null != filter.getAllResourcesSort() && filter.getAllResourcesSort().size() > 0){
			String sortBy="";
			for(String field : filter.getAllResourcesSort()){
				sortBy=sortBy + "," + filter.getSortField(field);
			}
			b.append(sortBy);
		}

		SQLQuery query = super.getHibernateSession().createSQLQuery(b.toString());


		/*
		 * Implementing custom result transformer to not have to
		 * create setter methods in the target class with upper
		 * case property names.
		 */
		CustomResultTransformer crt = new CustomResultTransformer(AllIncidentResourcesReportData.class);

		/*
		 * Add any aliases field conversions.
		 * Only need to set aliases if the targetclass has setter methods
		 * that are different from the sql query fields.
		 * 
		 * ie: crt.addProjection("nbr","incidentNumber")
		 */
		//crt.addProjection("incidentName", "incidentName");

		/*
		 * Add scalar to handle for both oracle/postgres
		 */
		crt.addScalar("person", Boolean.class.getName());
		crt.addScalar("lngOfAssignment", Long.class.getName());
		crt.addScalar("numberOfPersonnel", Long.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		crt.addScalar("demobDate", Date.class.getName());

		query.setResultTransformer(crt); 

		return query.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportIncidentResourceDao#getICS209ReportData(gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ICS209ReportData> getICS209ReportData(ICS209ReportFilter filter) throws PersistenceException {

		// get the resources that do not have an agency code
		SQLQuery query1 = super.getHibernateSession().createSQLQuery(ICS209Query.buildQuery(filter,super.isOracleDialect()));

		CustomResultTransformer crt1 = new CustomResultTransformer(ICS209ReportData.class);
		crt1.addScalar("crw1Count", Integer.class.getName());
		crt1.addScalar("crw1PersonCount", Integer.class.getName());
		crt1.addScalar("crw1StCount", Integer.class.getName());
		crt1.addScalar("crw1StPersonCount", Integer.class.getName());
		crt1.addScalar("crw2Count", Integer.class.getName());
		crt1.addScalar("crw2PersonCount", Integer.class.getName());
		crt1.addScalar("crw2StCount", Integer.class.getName());
		crt1.addScalar("crw2StPersonCount", Integer.class.getName());
		crt1.addScalar("hel1Count", Integer.class.getName());
		crt1.addScalar("hel1PersonCount", Integer.class.getName());
		crt1.addScalar("hel2Count", Integer.class.getName());
		crt1.addScalar("hel2PersonCount", Integer.class.getName());
		crt1.addScalar("hel3Count", Integer.class.getName());
		crt1.addScalar("hel3PersonCount", Integer.class.getName());
		crt1.addScalar("engsCount", Integer.class.getName());
		crt1.addScalar("engsPersonCount", Integer.class.getName());
		crt1.addScalar("engsStCount", Integer.class.getName());
		crt1.addScalar("engsStPersonCount", Integer.class.getName());
		crt1.addScalar("dozrCount", Integer.class.getName());
		crt1.addScalar("dozrPersonCount", Integer.class.getName());
		crt1.addScalar("dozrStCount", Integer.class.getName());
		crt1.addScalar("dozrStPersonCount", Integer.class.getName());
		crt1.addScalar("wtdrCount", Integer.class.getName());
		crt1.addScalar("wtdrPersonCount", Integer.class.getName());
		crt1.addScalar("ovhdCount", Integer.class.getName());
		crt1.addScalar("ovhdPersonCount", Integer.class.getName());
		crt1.addScalar("ccCount", Integer.class.getName());
		crt1.addScalar("ccPersonCount", Integer.class.getName());

		crt1.addScalar("non209Count", Integer.class.getName());
		crt1.addScalar("non209PersonCount", Integer.class.getName());

		query1.setResultTransformer(crt1); 


		Collection<ICS209ReportData> list1=query1.list();

		// get the non-state resources that have an agency code
		SQLQuery query = super.getHibernateSession().createSQLQuery(ICS209Query.buildQuery2(filter,super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ICS209ReportData.class);
		crt.addProjection("agency_code", "agencyCode");

		crt.addScalar("crw1Count", Integer.class.getName());
		crt.addScalar("crw1PersonCount", Integer.class.getName());
		crt.addScalar("crw1StCount", Integer.class.getName());
		crt.addScalar("crw1StPersonCount", Integer.class.getName());
		crt.addScalar("crw2Count", Integer.class.getName());
		crt.addScalar("crw2PersonCount", Integer.class.getName());
		crt.addScalar("crw2StCount", Integer.class.getName());
		crt.addScalar("crw2StPersonCount", Integer.class.getName());
		crt.addScalar("hel1Count", Integer.class.getName());
		crt.addScalar("hel1PersonCount", Integer.class.getName());
		crt.addScalar("hel2Count", Integer.class.getName());
		crt.addScalar("hel2PersonCount", Integer.class.getName());
		crt.addScalar("hel3Count", Integer.class.getName());
		crt.addScalar("hel3PersonCount", Integer.class.getName());
		crt.addScalar("engsCount", Integer.class.getName());
		crt.addScalar("engsPersonCount", Integer.class.getName());
		crt.addScalar("engsStCount", Integer.class.getName());
		crt.addScalar("engsStPersonCount", Integer.class.getName());
		crt.addScalar("dozrCount", Integer.class.getName());
		crt.addScalar("dozrPersonCount", Integer.class.getName());
		crt.addScalar("dozrStCount", Integer.class.getName());
		crt.addScalar("dozrStPersonCount", Integer.class.getName());
		crt.addScalar("wtdrCount", Integer.class.getName());
		crt.addScalar("wtdrPersonCount", Integer.class.getName());
		crt.addScalar("ovhdCount", Integer.class.getName());
		crt.addScalar("ovhdPersonCount", Integer.class.getName());
		crt.addScalar("ccCount", Integer.class.getName());
		crt.addScalar("ccPersonCount", Integer.class.getName());

		crt.addScalar("non209Count", Integer.class.getName());
		crt.addScalar("non209PersonCount", Integer.class.getName());

		query.setResultTransformer(crt); 

		Collection<ICS209ReportData> list2 = query.list();

		// get just the state agency types as one report
		SQLQuery queryState = super.getHibernateSession().createSQLQuery(ICS209Query.buildQueryState(filter,super.isOracleDialect()));

		CustomResultTransformer crtState = new CustomResultTransformer(ICS209ReportData.class);
		crtState.addProjection("agency_type", "agencyCode");

		crtState.addScalar("crw1Count", Integer.class.getName());
		crtState.addScalar("crw1PersonCount", Integer.class.getName());
		crtState.addScalar("crw1StCount", Integer.class.getName());
		crtState.addScalar("crw1StPersonCount", Integer.class.getName());
		crtState.addScalar("crw2Count", Integer.class.getName());
		crtState.addScalar("crw2PersonCount", Integer.class.getName());
		crtState.addScalar("crw2StCount", Integer.class.getName());
		crtState.addScalar("crw2StPersonCount", Integer.class.getName());
		crtState.addScalar("hel1Count", Integer.class.getName());
		crtState.addScalar("hel1PersonCount", Integer.class.getName());
		crtState.addScalar("hel2Count", Integer.class.getName());
		crtState.addScalar("hel2PersonCount", Integer.class.getName());
		crtState.addScalar("hel3Count", Integer.class.getName());
		crtState.addScalar("hel3PersonCount", Integer.class.getName());
		crtState.addScalar("engsCount", Integer.class.getName());
		crtState.addScalar("engsPersonCount", Integer.class.getName());
		crtState.addScalar("engsStCount", Integer.class.getName());
		crtState.addScalar("engsStPersonCount", Integer.class.getName());
		crtState.addScalar("dozrCount", Integer.class.getName());
		crtState.addScalar("dozrPersonCount", Integer.class.getName());
		crtState.addScalar("dozrStCount", Integer.class.getName());
		crtState.addScalar("dozrStPersonCount", Integer.class.getName());
		crtState.addScalar("wtdrCount", Integer.class.getName());
		crtState.addScalar("wtdrPersonCount", Integer.class.getName());
		crtState.addScalar("ovhdCount", Integer.class.getName());
		crtState.addScalar("ovhdPersonCount", Integer.class.getName());
		crtState.addScalar("ccCount", Integer.class.getName());
		crtState.addScalar("ccPersonCount", Integer.class.getName());

		crtState.addScalar("non209Count", Integer.class.getName());
		crtState.addScalar("non209PersonCount", Integer.class.getName());

		queryState.setResultTransformer(crtState); 

		Collection<ICS209ReportData> listState = queryState.list();


		Collection<ICS209ReportData> rtnList = new ArrayList<ICS209ReportData>();

		if(null != list1 && list1.size() == 1){
			ICS209ReportData d = list1.iterator().next();
			d.setAgencyCode("No Agency");
			rtnList.add(d);
		}

		rtnList.addAll(list2);

		if(listState != null && listState.size() > 0) {
			ICS209ReportData d = listState.iterator().next();
			rtnList.add(d);
		}

		return rtnList;
	}

	public Collection<ICS209ReportData> getICS209ReportData2(ICS209ReportFilter filter) throws PersistenceException {

		// get the resources that do not have an agency code
		SQLQuery query1 = super.getHibernateSession().createSQLQuery(ICS209Query2.buildQuery(filter,super.isOracleDialect()));

		CustomResultTransformer crt1 = new CustomResultTransformer(ICS209ReportData.class);
		crt1.addScalar("crw1Count", Integer.class.getName());
		crt1.addScalar("crw1PersonCount", Integer.class.getName());
		crt1.addScalar("crw1StCount", Integer.class.getName());
		crt1.addScalar("crw1StPersonCount", Integer.class.getName());
		crt1.addScalar("crw2Count", Integer.class.getName());
		crt1.addScalar("crw2PersonCount", Integer.class.getName());
		crt1.addScalar("crw2StCount", Integer.class.getName());
		crt1.addScalar("crw2StPersonCount", Integer.class.getName());
		crt1.addScalar("hel1Count", Integer.class.getName());
		crt1.addScalar("hel1PersonCount", Integer.class.getName());
		crt1.addScalar("hel2Count", Integer.class.getName());
		crt1.addScalar("hel2PersonCount", Integer.class.getName());
		crt1.addScalar("hel3Count", Integer.class.getName());
		crt1.addScalar("hel3PersonCount", Integer.class.getName());
		crt1.addScalar("engsCount", Integer.class.getName());
		crt1.addScalar("engsPersonCount", Integer.class.getName());
		crt1.addScalar("engsStCount", Integer.class.getName());
		crt1.addScalar("engsStPersonCount", Integer.class.getName());
		crt1.addScalar("dozrCount", Integer.class.getName());
		crt1.addScalar("dozrPersonCount", Integer.class.getName());
		crt1.addScalar("dozrStCount", Integer.class.getName());
		crt1.addScalar("dozrStPersonCount", Integer.class.getName());
		crt1.addScalar("wtdrCount", Integer.class.getName());
		crt1.addScalar("wtdrPersonCount", Integer.class.getName());
		crt1.addScalar("ovhdCount", Integer.class.getName());
		crt1.addScalar("ovhdPersonCount", Integer.class.getName());
		crt1.addScalar("ccCount", Integer.class.getName());
		crt1.addScalar("ccPersonCount", Integer.class.getName());

		crt1.addScalar("non209Count", Integer.class.getName());
		crt1.addScalar("non209PersonCount", Integer.class.getName());

		query1.setResultTransformer(crt1); 


		Collection<ICS209ReportData> list1=query1.list();

		// get the non-state resources that have an agency code
		SQLQuery query = super.getHibernateSession().createSQLQuery(ICS209Query2.buildQuery2(filter,super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ICS209ReportData.class);
		crt.addProjection("agency_code", "agencyCode");

		crt.addScalar("crw1Count", Integer.class.getName());
		crt.addScalar("crw1PersonCount", Integer.class.getName());
		crt.addScalar("crw1StCount", Integer.class.getName());
		crt.addScalar("crw1StPersonCount", Integer.class.getName());
		crt.addScalar("crw2Count", Integer.class.getName());
		crt.addScalar("crw2PersonCount", Integer.class.getName());
		crt.addScalar("crw2StCount", Integer.class.getName());
		crt.addScalar("crw2StPersonCount", Integer.class.getName());
		crt.addScalar("hel1Count", Integer.class.getName());
		crt.addScalar("hel1PersonCount", Integer.class.getName());
		crt.addScalar("hel2Count", Integer.class.getName());
		crt.addScalar("hel2PersonCount", Integer.class.getName());
		crt.addScalar("hel3Count", Integer.class.getName());
		crt.addScalar("hel3PersonCount", Integer.class.getName());
		crt.addScalar("engsCount", Integer.class.getName());
		crt.addScalar("engsPersonCount", Integer.class.getName());
		crt.addScalar("engsStCount", Integer.class.getName());
		crt.addScalar("engsStPersonCount", Integer.class.getName());
		crt.addScalar("dozrCount", Integer.class.getName());
		crt.addScalar("dozrPersonCount", Integer.class.getName());
		crt.addScalar("dozrStCount", Integer.class.getName());
		crt.addScalar("dozrStPersonCount", Integer.class.getName());
		crt.addScalar("wtdrCount", Integer.class.getName());
		crt.addScalar("wtdrPersonCount", Integer.class.getName());
		crt.addScalar("ovhdCount", Integer.class.getName());
		crt.addScalar("ovhdPersonCount", Integer.class.getName());
		crt.addScalar("ccCount", Integer.class.getName());
		crt.addScalar("ccPersonCount", Integer.class.getName());

		crt.addScalar("non209Count", Integer.class.getName());
		crt.addScalar("non209PersonCount", Integer.class.getName());

		query.setResultTransformer(crt); 

		Collection<ICS209ReportData> list2 = query.list();

		// get just the state agency types as one report
		Collection<ICS209ReportData> listState  = new ArrayList<ICS209ReportData>();
		/*
		SQLQuery queryState = super.getHibernateSession().createSQLQuery(ICS209Query.buildQueryState(filter,super.isOracleDialect()));

		CustomResultTransformer crtState = new CustomResultTransformer(ICS209ReportData.class);
		crtState.addProjection("agency_type", "agencyCode");

		crtState.addScalar("crw1Count", Integer.class.getName());
		crtState.addScalar("crw1PersonCount", Integer.class.getName());
		crtState.addScalar("crw1StCount", Integer.class.getName());
		crtState.addScalar("crw1StPersonCount", Integer.class.getName());
		crtState.addScalar("crw2Count", Integer.class.getName());
		crtState.addScalar("crw2PersonCount", Integer.class.getName());
		crtState.addScalar("crw2StCount", Integer.class.getName());
		crtState.addScalar("crw2StPersonCount", Integer.class.getName());
		crtState.addScalar("hel1Count", Integer.class.getName());
		crtState.addScalar("hel1PersonCount", Integer.class.getName());
		crtState.addScalar("hel2Count", Integer.class.getName());
		crtState.addScalar("hel2PersonCount", Integer.class.getName());
		crtState.addScalar("hel3Count", Integer.class.getName());
		crtState.addScalar("hel3PersonCount", Integer.class.getName());
		crtState.addScalar("engsCount", Integer.class.getName());
		crtState.addScalar("engsPersonCount", Integer.class.getName());
		crtState.addScalar("engsStCount", Integer.class.getName());
		crtState.addScalar("engsStPersonCount", Integer.class.getName());
		crtState.addScalar("dozrCount", Integer.class.getName());
		crtState.addScalar("dozrPersonCount", Integer.class.getName());
		crtState.addScalar("dozrStCount", Integer.class.getName());
		crtState.addScalar("dozrStPersonCount", Integer.class.getName());
		crtState.addScalar("wtdrCount", Integer.class.getName());
		crtState.addScalar("wtdrPersonCount", Integer.class.getName());
		crtState.addScalar("ovhdCount", Integer.class.getName());
		crtState.addScalar("ovhdPersonCount", Integer.class.getName());
		crtState.addScalar("ccCount", Integer.class.getName());
		crtState.addScalar("ccPersonCount", Integer.class.getName());

		crtState.addScalar("non209Count", Integer.class.getName());
		crtState.addScalar("non209PersonCount", Integer.class.getName());

		queryState.setResultTransformer(crtState); 

		listState = queryState.list();
		*/

		Collection<ICS209ReportData> rtnList = new ArrayList<ICS209ReportData>();

		if(null != list1 && list1.size() == 1){
			ICS209ReportData d = list1.iterator().next();
			d.setAgencyCode(ICS209ReportData.NO_AGENCY_CODE);
			rtnList.add(d);
		}

		rtnList.addAll(list2);

		if(listState != null && listState.size() > 0) {
			ICS209ReportData d = listState.iterator().next();
			rtnList.add(d);
		}

		return rtnList;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportIncidentResourceDao#getStrikeTeamTaskForceReportData(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<StrikeTeamTaskForceReportData> getStrikeTeamTaskForceReportData(Long incidentId, Collection<Long> resourceIds) throws PersistenceException {

		SQLQuery query = super.getHibernateSession().createSQLQuery(ResourceReportQuery.STTFReportQuery(isOracleDialect()));

		query.setParameterList("rids", resourceIds);

		CustomResultTransformer crt = new CustomResultTransformer(StrikeTeamTaskForceReportData.class);

		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("numPersonnel", Long.class.getName());
		crt.addScalar("lastWorkDay", Date.class.getName());
		crt.addScalar("tentativeReleaseDate", Date.class.getName());
		crt.addScalar("actualReleaseDate", Date.class.getName());

		query.setResultTransformer(crt); 

		Collection<StrikeTeamTaskForceReportData> resources = query.list();

		Collection<StrikeTeamTaskForceReportData> list = new ArrayList<StrikeTeamTaskForceReportData>();

		int group = 0;

		for(StrikeTeamTaskForceReportData resource : resources) {
			group++;
			int step=0;
			buildTree(list, resource, group, step);
		}

		return list;
	}

	private Collection<StrikeTeamTaskForceReportData> buildTree(Collection<StrikeTeamTaskForceReportData> list,StrikeTeamTaskForceReportData data,int groupId, int step){

		list.add(StrikeTeamTaskForceReportData.getInstance(data, groupId, step));

		try {
			Collection<StrikeTeamTaskForceReportData> childData = getStrikeTeamTaskForceReportChildData(data.getResourceId());
			if(CollectionUtility.hasValue(childData)) {
				for(StrikeTeamTaskForceReportData child : childData) {
					buildTree(list,child,groupId,(step+3));
				}
			}
		}catch(Exception e){}

		return list;
	}

	public Collection<StrikeTeamTaskForceReportData> getStrikeTeamTaskForceReportChildData(Long parentResourceId) throws PersistenceException {

		SQLQuery query = super.getHibernateSession().createSQLQuery(ResourceReportQuery.STTFReportChildrenQuery(isOracleDialect()));

		query.setParameter("prid", parentResourceId);

		CustomResultTransformer crt = new CustomResultTransformer(StrikeTeamTaskForceReportData.class);

		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("numPersonnel", Long.class.getName());
		crt.addScalar("lastWorkDay", Date.class.getName());
		crt.addScalar("tentativeReleaseDate", Date.class.getName());
		crt.addScalar("actualReleaseDate", Date.class.getName());

		query.setResultTransformer(crt); 

		Collection<StrikeTeamTaskForceReportData> reportData = query.list();

		return reportData;
	}

	//	public Collection<StrikeTeamTaskForceReportData> getStrikeTeamTaskForceReportData(Long incidentId, Collection<Long> resourceIds) throws PersistenceException {
	//
	//		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);
	//
	//		crit.add(Restrictions.in("id", resourceIds));
	//			
	//		Collection<Resource> resources = crit.list();
	//
	//		Collection<StrikeTeamTaskForceReportData> list = new ArrayList<StrikeTeamTaskForceReportData>();
	//
	//		
	//		if(null != resources){
	//			int group = 0;
	//
	//			Collections.sort((List)resources, new STRequestNumberComparator());
	//			
	//			for(Resource resource : resources){
	//				group++;
	//				int step=0;
	//				StrikeTeamTaskForceReportData.buildTree(list,resource,group, step);
	//			}
	//		}
	//
	//		return list;
	//	}

	class STRequestNumberComparator implements Comparator{

		public int compare(Object data1, Object data2){

			String rn1 = "";
			String rn2 = "";
			Resource res1 = (Resource)data1;
			Resource res2 = (Resource)data2;

			if(null != res1.getIncidentResources() && res1.getIncidentResources().size() > 0){
				IncidentResource ir = res1.getIncidentResources().iterator().next();
				if(null != ir.getWorkPeriod()){
					if(null != ir.getWorkPeriod().getAssignments()){
						for(Assignment a : ir.getWorkPeriod().getAssignments()){
							if(null ==a.getEndDate() ){
								rn1=(null != a.getRequestNumber() ? a.getRequestNumber() : "");
							}
						}
					}
				}
			}

			if(null != res2.getIncidentResources() && res2.getIncidentResources().size() > 0){
				IncidentResource ir = res2.getIncidentResources().iterator().next();
				if(null != ir.getWorkPeriod()){
					if(null != ir.getWorkPeriod().getAssignments()){
						for(Assignment a : ir.getWorkPeriod().getAssignments()){
							if(null ==a.getEndDate() ){
								rn2=(null != a.getRequestNumber() ? a.getRequestNumber() : "");
							}
						}
					}
				}
			}

			return rn1.compareTo(rn2);
		}
	}

	public Collection<QualificationsReportData> getQualificationsReportDataOrig(QualificationsReportFilter filter) throws PersistenceException{

		StringBuffer sql = new StringBuffer()
		.append("select r.id as resourceId, ")
		.append("case ")
		.append("when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("else ")
		.append("r.resource_name ")
		.append("end as resourceName ")
		.append(",AG.AGENCY_CODE as agencyCode ")
		.append(",WP.DM_TENTATIVE_RELEASE_DATE as tentativeReleaseDate ")
		.append(",wp.ci_length_at_assignment as lengthAtAssignment ")
		.append(",wp.ci_first_work_date as firstWorkDate ")
		.append(",K2.CODE as kindCode ")
		.append(",K2.DESCRIPTION as kindDesc ")
		.append(",rk.IS_TRAINING as training ")
		.append(",D2.CODE as sectionCode ")
		.append(",d2.description as sectionDesc ")
		.append(",a.request_number as requestNumber ")
		.append(", case ")
		.append("  when (a.kind_id = rk.kind_id and a.end_date is null ) then 'yes' " )
		.append("  else 'no' ")
		.append("  end as currentPositionFlag ")
		.append(",i.incident_name as incidentName ")
		.append(",i.nbr as incidentNumber ")
		.append(",i.id as incidentId ")
		.append(",org.unit_code as incidentUnit ")
		.append("from isw_resource r ")
		.append("  left join iswl_agency ag on R.AGENCY_ID = ag.id ")
		.append("  left join isw_resource_kind rk on r.id = rk.resource_id ")
		.append("  left join iswl_kind k2 on rk.kind_id = k2.id ")
		.append("  left join iswl_department d2 on k2.department_id = d2.id ")
		.append(", isw_incident_resource ir, ")
		.append(" isw_incident i ")
		.append("  left join isw_organization org on i.unit_id = org.id ")
		.append(",  isw_work_period wp ")

		.append("  left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("  left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("  left join iswl_kind k on a.kind_id = k.id ")
		.append("  left join iswl_department d on K.DEPARTMENT_ID = d.id ")
		.append("where r.id = ir.resource_id ")
		.append("and ir.incident_id = i.id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and A.END_DATE is null ")
		.append("and r.deleted_date is null ");

		if(null != filter.getIncidentId() && filter.getIncidentId() > 0){
			sql.append("and i.id = " + filter.getIncidentId() + " ");
		}if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() > 0){
			sql.append("and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
		}

		if(null != filter.getSelectedKinds() && filter.getSelectedKinds().size() > 0){
			sql.append("and k2.code in (")
			.append(QualificationsReportFilter.getKinds(filter))
			.append(") ");
		}

		if(filter.getExcludeTrainees()){
			sql.append(" and rk.is_training = " + super.getBooleanComparison(false) + " ");
		}else if (filter.getTraineesOnly()){
			sql.append(" and rk.is_training = " + super.getBooleanComparison(true) + " ");
		}

		sql.append(" UNION ")
		.append("select r.id as resourceId, ")
		.append("case ")
		.append("when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("else ")
		.append("r.resource_name ")
		.append("end as resourceName ")
		.append(",AG.AGENCY_CODE as agencyCode ")
		.append(",WP.DM_TENTATIVE_RELEASE_DATE as tentativeReleaseDate ")
		.append(",wp.ci_length_at_assignment as lengthAtAssignment ")
		.append(",wp.ci_first_work_date as firstWorkDate ")
		.append(",K.CODE as kindCode ")
		.append(",K.DESCRIPTION as kindDesc ")
		.append(",a.IS_TRAINING as training ")
		.append(",D.CODE as sectionCode ")
		.append(",d.description as sectionDesc ")
		.append(",a.request_number as requestNumber ")
		.append(",'yes' as currentPositionFlag ")
		.append(",i.incident_name as incidentName ")
		.append(",i.nbr as incidentNumber ")
		.append(",i.id as incidentId ")
		.append(",org.unit_code as incidentUnit ")
		.append("from isw_resource r   ")
		.append("left join iswl_agency ag on R.AGENCY_ID = ag.id   ")
		.append(", isw_incident_resource ir ")
		.append(", isw_incident i ")
		.append("left join isw_organization org on i.unit_id = org.id ")
		.append(", isw_work_period wp ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id   ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id   ")
		.append("left join iswl_kind k on a.kind_id = k.id   ")
		.append("left join iswl_department d on K.DEPARTMENT_ID = d.id ")
		.append("where r.id = ir.resource_id ")
		.append("and ir.incident_id = i.id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and A.END_DATE is null ")
		.append("and r.deleted_date is null ");

		if(null != filter.getIncidentId() && filter.getIncidentId() > 0){
			sql.append("and i.id = " + filter.getIncidentId() + " ");
		}if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() > 0){
			sql.append("and i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
		}

		if(null != filter.getSelectedKinds() && filter.getSelectedKinds().size() > 0){
			sql.append("and k.code in (")
			.append(QualificationsReportFilter.getKinds(filter))
			.append(") ");
		}
		if(filter.getExcludeTrainees()){
			sql.append(" and a.is_training = " + super.getBooleanComparison(false) + " ");
		}else if (filter.getTraineesOnly()){
			sql.append(" and a.is_training = " + super.getBooleanComparison(true) + " ");
		}
		sql.append("and k.id not in (select kind_id from isw_resource_kind where resource_id = r.id ) ")
		;

		/*
		 * changing sort to kindDesc instead of kindCode
		 * see defect 3204
		 */
		sql.append(" order by incidentId, kindDesc, resourceName ");

		SQLQuery query = super.getHibernateSession().createSQLQuery(sql.toString());

		CustomResultTransformer crt = new CustomResultTransformer(QualificationsReportData.class);
		crt.addProjection("requestNumber", "requestNumber");
		crt.addProjection("resourceName", "resourceName");
		crt.addProjection("agencyCode", "agency");
		crt.addProjection("training", "training");
		crt.addProjection("kindDesc", "kindDescription");
		crt.addProjection("kindCode", "kindCode");
		crt.addProjection("sectionCode", "sectionCode");
		crt.addProjection("sectionDesc", "section");
		crt.addProjection("resourceId", "resourceId");
		crt.addProjection("tentativeReleaseDate", "tentativeReleaseDate");
		crt.addProjection("lengthAtAssignment", "lengthAtAssignment");
		crt.addProjection("firstWorkDate", "firstWorkDate");

		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("training", Boolean.class.getName());
		crt.addScalar("lengthAtAssignment", Integer.class.getName());
		crt.addScalar("incidentId", Long.class.getName());


		query.setResultTransformer(crt); 

		return query.list();

	}

	public Collection<QualificationsReportData> getQualificationsReportData(QualificationsReportFilter filter) throws PersistenceException{

		StringBuffer sql = new StringBuffer()
		.append("select ")
		.append("  r.id as resourceId ")
		.append("  , case ")
		.append("      when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("      else ")
		.append("         r.resource_name ")
		.append("    end as resourceName ")
		.append("  ,AG.AGENCY_CODE as agencyCode ")
		.append("  ,WP.DM_TENTATIVE_RELEASE_DATE as tentativeReleaseDate ")
		.append("  ,wp.ci_length_at_assignment as lengthAtAssignment ")
		.append("  ,wp.ci_first_work_date as firstWorkDate ")
		.append("  ,K.CODE as kindCode ")
		.append("  ,K.DESCRIPTION as kindDesc ")
		.append("  ,a.IS_TRAINING as training ")
		.append("  ,D.CODE as sectionCode ")
		.append("  ,d.description as sectionDesc ")
		.append("  ,a.request_number as requestNumber ")
		.append("  , 'yes' as currentPositionFlag ")
		.append("  ,i.incident_name as incidentName ")
		.append("  ,i.nbr as incidentNumber ")
		.append("  ,i.id as incidentId ")
		.append("  ,org.unit_code as incidentUnit ")
		.append("from isw_incident i ")
		.append("  		left join isw_organization org on i.unit_id = org.id ")
		.append("    ,isw_incident_resource ir ")
		.append("    ,isw_resource r ")
		.append("  		left join iswl_agency ag on R.AGENCY_ID = ag.id ")
		.append("    ,isw_work_period wp ")
		.append("    ,isw_work_period_assignment wpa ")
		.append("    ,isw_assignment a ")
		.append("    ,iswl_kind k ")
		.append("  		left join iswl_department d on k.department_id = d.id ");
		if(null != filter.getIncidentId() && filter.getIncidentId() > 0){
			sql.append("where i.id = " + filter.getIncidentId() + " ");
		}if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() > 0){
			sql.append("where i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
		}
		sql
		.append("and ir.incident_id = i.id ")
		.append("and r.id = ir.resource_id ")
		.append("and r.is_person = " + (super.isOracleDialect() ? 1 : true) + " ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and A.END_DATE is null ")
		.append("and a.assignment_status in ('C','P') ")
		.append("and r.deleted_date is null ")
		.append("and k.id = a.kind_id ");

		if(null != filter.getSelectedKinds() && filter.getSelectedKinds().size() > 0){
			sql.append("and k.code in (")
			.append(QualificationsReportFilter.getKinds(filter))
			.append(") ");
		}

		if(filter.getExcludeTrainees()){
			sql.append(" and a.is_training = " + super.getBooleanComparison(false) + " ");
		}else if (filter.getTraineesOnly()){
			sql.append(" and a.is_training = " + super.getBooleanComparison(true) + " ");
		}

		sql.append(" UNION ")
		.append("select r2.id as resourceId, ")
		.append("  case ")
		.append("     when r2.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r2.last_name || ', ' || r2.first_name ")
		.append("     else ")
		.append("       r2.resource_name ")
		.append("     end as resourceName ")
		.append("  ,AG2.AGENCY_CODE as agencyCode ")
		.append("  ,WP2.DM_TENTATIVE_RELEASE_DATE as tentativeReleaseDate ")
		.append("  ,wp2.ci_length_at_assignment as lengthAtAssignment ")
		.append("  ,wp2.ci_first_work_date as firstWorkDate ")
		.append("  ,K2.CODE as kindCode ")
		.append("  ,K2.DESCRIPTION as kindDesc ")
		.append("  ,rk2.IS_TRAINING as training ")
		.append("  ,D2.CODE as sectionCode ")
		.append("  ,d2.description as sectionDesc ")
		.append("  ,a2.request_number as requestNumber ")
		.append("  ,'no' as currentPositionFlag ")
		.append("  ,i2.incident_name as incidentName ")
		.append("  ,i2.nbr as incidentNumber ")
		.append("  ,i2.id as incidentId ")
		.append("  ,org2.unit_code as incidentUnit ")
		.append("from isw_incident i2 ")
		.append("  		left join isw_organization org2 on i2.unit_id = org2.id ")
		.append("    ,isw_incident_resource ir2 ")
		.append("    ,isw_resource r2 ")
		.append("  		left join iswl_agency ag2 on R2.AGENCY_ID = ag2.id ")
		.append("    ,isw_work_period wp2 ")
		.append("    ,isw_work_period_assignment wpa2 ")
		.append("    ,isw_assignment a2 ")
		.append("    ,isw_resource_kind rk2 ")
		.append("    ,iswl_kind k2 ")
		.append("  		left join iswl_department d2 on k2.department_id = d2.id ");
		if(null != filter.getIncidentId() && filter.getIncidentId() > 0){
			sql.append("where i2.id = " + filter.getIncidentId() + " ");
		}if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() > 0){
			sql.append("where i2.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + " ) ");
		}
		sql
		.append("and ir2.incident_id = i2.id ")
		.append("and r2.id = ir2.resource_id ")
		.append("and r2.is_person = " + (super.isOracleDialect() ? 1 : true) + " ")
		.append("and wp2.incident_resource_id = ir2.id ")
		.append("and wpa2.work_period_id = wp2.id ")
		.append("and a2.id = wpa2.assignment_id ")
		.append("and A2.END_DATE is null ")
		.append("and a2.assignment_status in ('C','P') ")
		.append("and r2.deleted_date is null ")
		.append("and k2.id = rk2.kind_id ")
		.append("and rk2.resource_id = r2.id ")
		.append("and rk2.kind_id != a2.kind_id ");

		if(null != filter.getSelectedKinds() && filter.getSelectedKinds().size() > 0){
			sql.append("and k2.code in (")
			.append(QualificationsReportFilter.getKinds(filter))
			.append(") ");
		}

		if(filter.getExcludeTrainees()){
			sql.append(" and rk2.is_training = " + super.getBooleanComparison(false) + " ");
		}else if (filter.getTraineesOnly()){
			sql.append(" and rk2.is_training = " + super.getBooleanComparison(true) + " ");
		}
		;

		/*
		 * changing sort to kindDesc instead of kindCode
		 * see defect 3204
		 */
		sql.append(" order by incidentId, kindDesc, resourceName ");

		SQLQuery query = super.getHibernateSession().createSQLQuery(sql.toString());

		CustomResultTransformer crt = new CustomResultTransformer(QualificationsReportData.class);
		crt.addProjection("requestNumber", "requestNumber");
		crt.addProjection("resourceName", "resourceName");
		crt.addProjection("agencyCode", "agency");
		crt.addProjection("training", "training");
		crt.addProjection("kindDesc", "kindDescription");
		crt.addProjection("kindCode", "kindCode");
		crt.addProjection("sectionCode", "sectionCode");
		crt.addProjection("sectionDesc", "section");
		crt.addProjection("resourceId", "resourceId");
		crt.addProjection("tentativeReleaseDate", "tentativeReleaseDate");
		crt.addProjection("lengthAtAssignment", "lengthAtAssignment");
		crt.addProjection("firstWorkDate", "firstWorkDate");

		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("training", Boolean.class.getName());
		crt.addScalar("lengthAtAssignment", Integer.class.getName());
		crt.addScalar("incidentId", Long.class.getName());


		query.setResultTransformer(crt); 

		return query.list();

	}

	public Collection<ICS209ResourceData> getICS209ResourceData(ICS209ReportFilter filter) throws PersistenceException {

		// get the resources that do not have an agency code
		SQLQuery query1 = super.getHibernateSession().createSQLQuery(ICS209QueryResources.get209ResourcesQuery(filter, super.isOracleDialect()));

		CustomResultTransformer crt1 = new CustomResultTransformer(ICS209ResourceData.class);
		crt1.addScalar("incidentId", Long.class.getName());
		crt1.addScalar("resourceId", Long.class.getName());
		crt1.addScalar("resourceParentId", Long.class.getName());
		crt1.addScalar("isStrikeTeam", Boolean.class.getName());
		crt1.addScalar("isPerson", Boolean.class.getName());
		crt1.addScalar("personnelCount", Integer.class.getName());
		crt1.addScalar("kindPeopleCount", Integer.class.getName());
		
		query1.setResultTransformer(crt1); 

		Collection<ICS209ResourceData> list1=query1.list();

		return list1;
	}
	
}
