package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceKindImpl;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodQuestionValueImpl;
import gov.nwcg.isuite.core.domain.views.impl.IncidentResourceCommonViewImpl;
import gov.nwcg.isuite.core.domain.views.impl.IncidentResourceOnlyViewImpl;
import gov.nwcg.isuite.core.domain.views.impl.IncidentResourceView2Impl;
import gov.nwcg.isuite.core.domain.views.impl.IncidentResourceViewImpl;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostResourceDataQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentResourceQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceQuery;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.EarliestDateVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCheckInDemobVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCommonVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceAssignmentVo;
import gov.nwcg.isuite.core.vo.ResourceCostVo;
import gov.nwcg.isuite.core.vo.ResourceDataForAccrualVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.ResourceKindVo;
import gov.nwcg.isuite.core.vo.ResourceTimeVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.WorkPeriodQuestionValueVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;


/**
 * 
 * @author bsteiner
 */
public class IncidentResourceDaoHibernate extends TransactionSupportImpl implements IncidentResourceDao {

	private final CrudDao<IncidentResource> crudDao;

	public IncidentResourceDaoHibernate(final CrudDao<IncidentResource> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;

	}

	public Collection<IncidentResourceGridVo> getGrid2(IncidentResourceFilter filter, Collection<String> sortFields) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentResourceView2Impl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("cellPhoneNumber"), "cellPhoneNumber")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("leaderLastName"), "leaderLastName")
				.add(Projections.property("leaderFirstName"), "leaderFirstName")
				.add(Projections.property("leaderIsTrainee"), "leaderIsTrainee")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciArrivalJetPort"),"ciArrivalJetPort")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"plansRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmReleaseDate"),"actualReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("ciTravelMethod"),"ciTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("requestNumberSortValue"), "requestNumberSortValue")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("dmReassignable"),"dmReassignable")
				.add(Projections.property("dmCheckoutFormPrinted"),"dmCheckoutFormPrinted")
				.add(Projections.property("dmPlanningDispatchNotified"),"dmPlanningDispatchNotified")
				.add(Projections.property("dmReleaseDispatchNotified"),"dmReleaseDispatchNotified")
				.add(Projections.property("dmRestOvernight"),"dmRestOvernight")
				.add(Projections.property("dmReleaseRemarks"),"dmReleaseRemarks")
				.add(Projections.property("dmEstimatedArrivalDate"),"dmEstimatedArrivalDate")
				.add(Projections.property("dmAirDispatchNotified"),"dmAirDispatchNotified")
				.add(Projections.property("airRemarks"),"airRemarks")
				.add(Projections.property("hoursToAirport"),"hoursToAirport")
				.add(Projections.property("minutesToAirport"),"minutesToAirport")
				.add(Projections.property("itineraryReceived"),"itineraryReceived")
				.add(Projections.property("nameOnPictureId"),"nameOnPictureId")
				.add(Projections.property("departFromJetport"),"departFromJetport")
				.add(Projections.property("overnightRemarks"),"overnightRemarks")
				.add(Projections.property("departmentCode"),"departmentCode")
				.add(Projections.property("departmentSubCode"),"departmentSubCode")
				.add(Projections.property("departmentDesc"),"departmentDesc")
				.add(Projections.property("departmentSubDesc"),"departmentSubDesc")
				.add(Projections.property("assignmentTimeId"), "assignmentTimeId")
				.add(Projections.property("leaderType"),"leaderTypeNumber")
				.add(Projections.property("accountCode"),"accountingCode")
				.add(Projections.property("employmentType"),"employmentType")
				.add(Projections.property("vinName"),"vinName")
				.add(Projections.property("contractorName"),"contractorName")
				.add(Projections.property("contractorAgreementNumber"),"contractorAgreementNumber")
				.add(Projections.property("assignDate"),"assignDate")
				.add(Projections.property("paymentAgency"),"paymentAgency")
				.add(Projections.property("accrualCode"),"accrualCode")
				.add(Projections.property("strikeTeam"),"strikeTeam")
				.add(Projections.property("isPerson"),"isPerson")
				.add(Projections.property("lineOverhead"),"isLineOverhead")
				.add(Projections.property("subGroupCategoryCode"),"subGroupCategoryCode")
				.add(Projections.property("costRemarks"),"costRemarks")
				.add(Projections.property("hasTnspRecord"), "hasTnspRecord")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		criteria.add(new FilterCriteria("this.irActive", true ,FilterCriteria.TYPE_EQUAL));

		if(null != filter.getCheckboxFilter()) {
			if(filter.getCheckboxFilter().isPersonnel()){
				crit.add(Restrictions.disjunction()
						.add(Restrictions.eq("this.requestCategory", "O"))
						.add(Restrictions.ne("this.lastName",""))
					);
			}
			if(filter.getCheckboxFilter().isNonpersonnel()){
				crit.add(Restrictions.disjunction()
						.add(Restrictions.ne("this.resourceName",""))
						.add(Restrictions.isNotNull("this.resourceName"))
						.add(Restrictions.eq("this.lastName",""))
						.add(Restrictions.isNull("this.lastName"))
					);
			}
		}
		
		if(LongUtility.hasValue(filter.getRosterParentId())){
			crit.add(Restrictions.disjunction()
					.add(Restrictions.eq("this.resourceId", filter.getRosterParentId()))
					.add(Restrictions.eq("this.parentResourceId", filter.getRosterParentId()))
			);
		}

		if(BooleanUtility.isTrue(filter.getUnRosteredOnly())){
			crit.add(Restrictions.isNull("this.parentResourceId"));
		}

		if(LongUtility.hasValue(filter.getExcludeResourceId())){
			crit.add(Restrictions.ne("this.resourceId", filter.getExcludeResourceId()));
		}
		
		if(LongUtility.hasValue(filter.getIncidentId())){
			crit.add(Restrictions.eq("this.incidentId", filter.getIncidentId()));
		}
		
		if(LongUtility.hasValue(filter.getIncidentGroupId())){
			String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getIncidentGroupId()+")";
			crit.add(Restrictions.sqlRestriction(sqlFilter));
		}

		if(LongUtility.hasValue(filter.getIncidentResourceId())){
			crit.add(Restrictions.eq("this.incidentResourceId", filter.getIncidentResourceId()));
		}
		
		if(LongUtility.hasValue(filter.getResourceId())){
			crit.add(Restrictions.eq("this.resourceId", filter.getResourceId()));
		}
		
		//sort, if sort fields were passed
		if(CollectionUtility.hasValue(sortFields)) {
			
			for (String sortField : sortFields) {
				
				if(sortField.equalsIgnoreCase("itemName")) sortField = "kindDescription";
				crit.addOrder(Order.asc("this." + sortField));
			}
		}else{
			crit.addOrder(Order.asc("this.requestNumberSortValue"));
		}

		Collection<IncidentResourceGridVo>  vos = crit.list();

		return vos;
	}
	
	public Collection<IncidentResourceGridVo> getGrid(IncidentResourceFilter incidentResourceFilter, Collection<String> sortFields) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentResourceViewImpl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("cellPhoneNumber"), "cellPhoneNumber")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciArrivalJetPort"),"ciArrivalJetPort")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"plansRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmReleaseDate"),"actualReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("requestNumberSortValue"), "requestNumberSortValue")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("workAreaId"),"workAreaId")
				.add(Projections.property("workAreaIncidentId"),"workAreaIncidentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("dmReassignable"),"dmReassignable")
				.add(Projections.property("dmCheckoutFormPrinted"),"dmCheckoutFormPrinted")
				.add(Projections.property("dmPlanningDispatchNotified"),"dmPlanningDispatchNotified")
				.add(Projections.property("dmReleaseDispatchNotified"),"dmReleaseDispatchNotified")
				.add(Projections.property("dmRestOvernight"),"dmRestOvernight")
				.add(Projections.property("dmReleaseRemarks"),"dmReleaseRemarks")
				.add(Projections.property("dmEstimatedArrivalDate"),"dmEstimatedArrivalDate")
				.add(Projections.property("dmAirDispatchNotified"),"dmAirDispatchNotified")
				.add(Projections.property("airRemarks"),"airRemarks")
				.add(Projections.property("hoursToAirport"),"hoursToAirport")
				.add(Projections.property("minutesToAirport"),"minutesToAirport")
				.add(Projections.property("itineraryReceived"),"itineraryReceived")
				.add(Projections.property("nameOnPictureId"),"nameOnPictureId")
				.add(Projections.property("departFromJetport"),"departFromJetport")
				.add(Projections.property("overnightRemarks"),"overnightRemarks")
				.add(Projections.property("departmentCode"),"departmentCode")
				.add(Projections.property("departmentSubCode"),"departmentSubCode")
				.add(Projections.property("departmentDesc"),"departmentDesc")
				.add(Projections.property("departmentSubDesc"),"departmentSubDesc")
				.add(Projections.property("assignmentTimeId"), "assignmentTimeId")
				.add(Projections.property("leaderType"),"leaderTypeNumber")
				.add(Projections.property("accountCode"),"accountingCode")
				.add(Projections.property("employmentType"),"employmentType")
				.add(Projections.property("vinName"),"vinName")
				.add(Projections.property("contractorName"),"contractorName")
				.add(Projections.property("contractorAgreementNumber"),"contractorAgreementNumber")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		criteria.add(new FilterCriteria("this.irActive", true ,FilterCriteria.TYPE_EQUAL));
		
		if (incidentResourceFilter != null) {
			try{
				if(!LongUtility.hasValue(incidentResourceFilter.getIncidentResourceId())){
					if( !LongUtility.hasValue(incidentResourceFilter.getWorkAreaId()))
						throw new PersistenceException("WorkAreaId cannot be empty.");
					
					if( !LongUtility.hasValue(incidentResourceFilter.getWorkAreaIncidentId())){
						if( !LongUtility.hasValue(incidentResourceFilter.getWorkAreaIncidentGroupId()))
								throw new PersistenceException("WorkAreaIncidentId and WorkAreaIncidentGroupId cannot both be empty.");
					}
				}
				
				/*
				 * Add the criteria
				 */
//				Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
				criteria.add( null != incidentResourceFilter.getWorkAreaId() && incidentResourceFilter.getWorkAreaId() > 0L ? new FilterCriteria("this.workAreaId",incidentResourceFilter.getWorkAreaId(),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( LongUtility.hasValue(incidentResourceFilter.getIncidentResourceId()) ? new FilterCriteria("this.incidentResourceId",incidentResourceFilter.getIncidentResourceId(),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( LongUtility.hasValue(incidentResourceFilter.getAssignmentId()) ? new FilterCriteria("this.incidentResourceId",incidentResourceFilter.getAssignmentId(),FilterCriteria.TYPE_EQUAL) : null);
				
				if(null != incidentResourceFilter.getWorkAreaIncidentId() && incidentResourceFilter.getWorkAreaIncidentId() > 0L){
					criteria.add( new FilterCriteria("this.workAreaIncidentId",incidentResourceFilter.getWorkAreaIncidentId(),FilterCriteria.TYPE_EQUAL));
				}else if (null != incidentResourceFilter.getWorkAreaIncidentGroupId() && incidentResourceFilter.getWorkAreaIncidentGroupId() > 0L) {
					String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentResourceFilter.getWorkAreaIncidentGroupId()+")";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}

				criteria.add( null != incidentResourceFilter.getResourceId() && incidentResourceFilter.getResourceId() > 0L ? new FilterCriteria("this.resourceId",incidentResourceFilter.getResourceId(),FilterCriteria.TYPE_EQUAL) : null);
				// parentResourceId
				criteria.add( null != incidentResourceFilter.getCellPhoneNumber() && !incidentResourceFilter.getCellPhoneNumber().isEmpty() ? new FilterCriteria("this.cellPhoneNumber",incidentResourceFilter.getCellPhoneNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// permanent
				
				if(null != incidentResourceFilter.getCheckboxFilter()) {
					criteria.add( null != incidentResourceFilter.getCheckboxFilter().getEnabled() ? new FilterCriteria("this.enabled", incidentResourceFilter.getCheckboxFilter().getEnabled(), FilterCriteria.TYPE_EQUAL) : null);	
				}
				
				//criteria.add( null != incidentResourceFilter.getContractedString() && !incidentResourceFilter.getContractedString().isEmpty() ? new FilterCriteria("this.contracted",(incidentResourceFilter.getContractedString().equals("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				if(StringUtility.hasValue(incidentResourceFilter.getContractedString())){
					if(incidentResourceFilter.getContractedString().equalsIgnoreCase("YES"))
						criteria.add(new FilterCriteria("this.employmentType","CONTRACTOR",FilterCriteria.TYPE_EQUAL));
					else
						criteria.add(new FilterCriteria("this.employmentType","CONTRACTOR",FilterCriteria.TYPE_NOT_EQUAL));
				}

				// leader
				criteria.add( null != incidentResourceFilter.getOther1() && !incidentResourceFilter.getOther1().isEmpty() ? new FilterCriteria("this.other1",incidentResourceFilter.getOther1().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getOther2() && !incidentResourceFilter.getOther2().isEmpty() ? new FilterCriteria("this.other2",incidentResourceFilter.getOther2().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getOther3() && !incidentResourceFilter.getOther3().isEmpty() ? new FilterCriteria("this.other3",incidentResourceFilter.getOther3().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// requestCategory - implemented below
				// resourceName,firstName,lastName - implemented below
				// resourceStatus
				criteria.add( null != incidentResourceFilter.getResourceClassification() && !incidentResourceFilter.getResourceClassification().isEmpty() ? new FilterCriteria("this.resourceClassification",(incidentResourceFilter.getResourceClassification().equals("ST") ? ResourceClassificationEnum.ST : ResourceClassificationEnum.TF),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getNumberOfPersonnelString() && !incidentResourceFilter.getNumberOfPersonnelString().isEmpty() ? new FilterCriteria("this.numberOfPersonnel",Long.valueOf(incidentResourceFilter.getNumberOfPersonnelString()),FilterCriteria.TYPE_EQUAL) : null);
				// resourceAgencyName
				criteria.add( null != incidentResourceFilter.getAgency() && !incidentResourceFilter.getAgency().isEmpty() ? new FilterCriteria("this.resourceAgencyCode",incidentResourceFilter.getAgency().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// org name
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getUnitId()) ? new FilterCriteria("this.resourceUnitCode",incidentResourceFilter.getUnitId().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticFilterCodeForMobilizationStartDate(), "this_.MOBILIZATION_START_DATE");
				// mobilizationSTartTime
				// incidentId
				criteria.add( null != incidentResourceFilter.getIncidentName() && !incidentResourceFilter.getIncidentName().isEmpty() ? new FilterCriteria("this.incidentName",incidentResourceFilter.getIncidentName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getIncidentNumber() && !incidentResourceFilter.getIncidentNumber().isEmpty() ? new FilterCriteria("this.incidentNumber",incidentResourceFilter.getIncidentNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// incidentResourceId
				criteria.add( null != incidentResourceFilter.getNameAtIncident() && !incidentResourceFilter.getNameAtIncident().isEmpty() ? new FilterCriteria("this.nameAtIncidentName",incidentResourceFilter.getNameAtIncident().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// workPeriodId
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticDateFilterCodeForCheckInDate(), "this_.CI_CHECK_IN_DATE");
				super.applyTimeFilter(crit, incidentResourceFilter.getCheckInTime(), "this_.CI_CHECK_IN_DATE");
				if(StringUtility.hasValue(incidentResourceFilter.getCheckInTime())){
				}
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticFilterCodeForCiFirstWorkDate(), "this_.CI_FIRST_WORK_DATE");
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getCiArrivalJetPort()) ? new FilterCriteria("this.ciArrivalJetPort",incidentResourceFilter.getCiArrivalJetPort().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getCiLengthAtAssignmentString() && !incidentResourceFilter.getCiLengthAtAssignmentString().isEmpty() ? new FilterCriteria("this.ciLengthAtAssignment",Long.valueOf(incidentResourceFilter.getCiLengthAtAssignmentString()),FilterCriteria.TYPE_EQUAL) : null);
				if(StringUtility.hasValue(incidentResourceFilter.getCiPrePlanningRemarks())){
					String sqlFilter = " upper(this_.ci_pre_planning_remarks) LIKE '%" + incidentResourceFilter.getCiPrePlanningRemarks().toUpperCase() + "%' ";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getDmTentativeDemobCity()) ? new FilterCriteria("this.dmTentativeDemobCity",incidentResourceFilter.getDmTentativeDemobCity().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getDmTentativeDemobState()) ? new FilterCriteria("this.dmTentativeDemobState",incidentResourceFilter.getDmTentativeDemobState().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticFilterCodeForTentativeDemobDate() , "this_.DM_TENTATIVE_Release_DATE");
				super.applyTimeFilter(crit, incidentResourceFilter.getTentativeReleaseTime(), "this_.DM_TENTATIVE_Release_DATE");
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticDateFilterCodeForActualReleaseDate() , "this_.DM_Release_DATE");
				super.applyTimeFilter(crit, incidentResourceFilter.getActualReleaseTime() , "this_.DM_Release_DATE");
				criteria.add( null != incidentResourceFilter.getDmTravelMethod() ? new FilterCriteria("this.dmTravelMethod",incidentResourceFilter.getDmTravelMethod(),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getItemCode() && !incidentResourceFilter.getItemCode().isEmpty() ? new FilterCriteria("this.kindCode",incidentResourceFilter.getItemCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getItemName() && !incidentResourceFilter.getItemName().isEmpty() ? new FilterCriteria("this.kindDescription",incidentResourceFilter.getItemName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getRequestNumber() && !incidentResourceFilter.getRequestNumber().isEmpty() ? new FilterCriteria("this.requestNumber",incidentResourceFilter.getRequestNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getTraineeString() && !incidentResourceFilter.getTraineeString().isEmpty() ? new FilterCriteria("this.trainee",(incidentResourceFilter.getTraineeString().equals("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getAssignmentStatus() && !incidentResourceFilter.getAssignmentStatus().equals("") ? new FilterCriteria("this.assignmentStatus",incidentResourceFilter.getAssignmentStatus(),FilterCriteria.TYPE_EQUAL) : null) ;
				// assignmentId
				// workAreaId
				// workAreaIncidentId
				criteria.add( null != incidentResourceFilter.getLeaderName() && !incidentResourceFilter.getLeaderName().isEmpty() ? new FilterCriteria("this.leaderName",incidentResourceFilter.getLeaderName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != incidentResourceFilter.getDmReassignableString() && !incidentResourceFilter.getDmReassignableString().isEmpty() ? new FilterCriteria("this.dmReassignable",(incidentResourceFilter.getDmReassignableString().equals("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getDmCheckoutFormPrintedString() && !incidentResourceFilter.getDmCheckoutFormPrintedString().isEmpty() ? new FilterCriteria("this.dmCheckoutFormPrinted",(incidentResourceFilter.getDmCheckoutFormPrintedString().equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				if(StringUtility.hasValue(incidentResourceFilter.getDmPlanningDispatchNotifiedString())){
					criteria.add( null != incidentResourceFilter.getDmPlanningDispatchNotifiedString() && !incidentResourceFilter.getDmPlanningDispatchNotifiedString().isEmpty() ? new FilterCriteria("this.dmPlanningDispatchNotified",(incidentResourceFilter.getDmPlanningDispatchNotifiedString().equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);	
				}				
				if(StringUtility.hasValue(incidentResourceFilter.getDmReleaseDispatchNotifiedString())){
					criteria.add( null != incidentResourceFilter.getDmReleaseDispatchNotifiedString() && !incidentResourceFilter.getDmReleaseDispatchNotifiedString().isEmpty() ? new FilterCriteria("this.dmReleaseDispatchNotified",(incidentResourceFilter.getDmReleaseDispatchNotifiedString().equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				}
				criteria.add( null != incidentResourceFilter.getDmRestOvernightString() && !incidentResourceFilter.getDmRestOvernightString().isEmpty() ? new FilterCriteria("this.dmRestOvernight",(incidentResourceFilter.getDmRestOvernightString().equals("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				if(StringUtility.hasValue(incidentResourceFilter.getDmReleaseRemarks())){
					String sqlFilter = " upper(this_.dm_release_remarks) LIKE '%" + incidentResourceFilter.getDmReleaseRemarks().toUpperCase() + "%' ";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				if(StringUtility.hasValue(incidentResourceFilter.getDmReleaseRemarks())){
					String sqlFilter = " upper(this_.DM_RELEASE_REMARKS) LIKE '%" + incidentResourceFilter.getDmReleaseRemarks().toUpperCase() + "%' ";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				super.applyCrypticDateFilter(crit, incidentResourceFilter.getCrypticFilterCodeForEstimatedArrivalDate(), "this_.DM_ESTIMATED_ARRIVAL_DATE");
								
				if(StringUtility.hasValue(incidentResourceFilter.getDmAirDispatchNotifiedString())){
					criteria.add( null != incidentResourceFilter.getDmAirDispatchNotifiedString() && !incidentResourceFilter.getDmAirDispatchNotifiedString().isEmpty() ? new FilterCriteria("this.dmAirDispatchNotified",(incidentResourceFilter.getDmAirDispatchNotifiedString().equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);	
				}	
				
				
				if(StringUtility.hasValue(incidentResourceFilter.getAirRemarks())){
					String sqlFilter = " upper(this_.AIR_REMARKS) LIKE '%" + incidentResourceFilter.getAirRemarks().toUpperCase() + "%' ";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				criteria.add( null != incidentResourceFilter.getHoursToAirportString() && !incidentResourceFilter.getHoursToAirportString().isEmpty() ? new FilterCriteria("this.hoursToAirport",Integer.valueOf(incidentResourceFilter.getHoursToAirportString()),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getMinutesToAirportString() && !incidentResourceFilter.getMinutesToAirportString().isEmpty() ? new FilterCriteria("this.minutesToAirport",Integer.valueOf(incidentResourceFilter.getMinutesToAirportString()),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( null != incidentResourceFilter.getItineraryReceivedString() && !incidentResourceFilter.getItineraryReceivedString().isEmpty() ? new FilterCriteria("this.itineraryReceived",(incidentResourceFilter.getItineraryReceivedString().equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE),FilterCriteria.TYPE_EQUAL) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getNameOnPictureId()) ? new FilterCriteria("this.nameOnPictureId",incidentResourceFilter.getNameOnPictureId().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getDepartFromJetport()) ? new FilterCriteria("this.departFromJetport",incidentResourceFilter.getDepartFromJetport().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				if(StringUtility.hasValue(incidentResourceFilter.getOvernightRemarks())){
					String sqlFilter = " upper(this_.OSI_REMARKS) LIKE '%" + incidentResourceFilter.getOvernightRemarks().toUpperCase() + "%' ";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				// departmentCode
				// departmentSubCode
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getSectionDescription()) ? new FilterCriteria("this.departmentDesc",incidentResourceFilter.getSectionDescription().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getSubSectionDescription()) ? new FilterCriteria("this.departmentSubDesc",incidentResourceFilter.getSubSectionDescription().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				// assignmentTimeId
				criteria.add(StringUtility.hasValue(incidentResourceFilter.getLeaderType()) ? new FilterCriteria("this.leaderType", (incidentResourceFilter.getLeaderType().equals("PRIMARY") ? 1 : 2) , FilterCriteria.TYPE_EQUAL): null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getAccountingCode()) ? new FilterCriteria("this.accountCode",incidentResourceFilter.getAccountingCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getContractorCooperatorName()) ? new FilterCriteria("this.contractorName",incidentResourceFilter.getContractorCooperatorName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getVinName()) ? new FilterCriteria("this.vinName",incidentResourceFilter.getVinName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getEmploymentType()) ? new FilterCriteria("this.employmentType",incidentResourceFilter.getEmploymentType().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( StringUtility.hasValue(incidentResourceFilter.getContractorAgreementNumber()) ? new FilterCriteria("this.contractorAgreementNumber",incidentResourceFilter.getContractorAgreementNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);

				/*
				 * Checkbox filters, other than 'All Personnel', are required to filter on 
				 * only the parent resource, and display any children whether or not the children match.
				 * They should not find children with matching item codes and display them 
				 * if the parent does not also match. 
				 */
				Collection<String> reqCatList = new ArrayList<String>();
				if(null != incidentResourceFilter.getCheckboxFilter()) {
					if(incidentResourceFilter.getCheckboxFilter().isPersonnel()){
						reqCatList.add("O");
						criteria.add( null != reqCatList && reqCatList.size() > 0 ? new FilterCriteria("this.requestCategory",reqCatList,FilterCriteria.TYPE_IN_STRING) : null);
					}else{
						if(incidentResourceFilter.getCheckboxFilter().isOverhead())reqCatList.add("O");
						if(incidentResourceFilter.getCheckboxFilter().isAircraft())reqCatList.add("A");
						if(incidentResourceFilter.getCheckboxFilter().isCrews())reqCatList.add("C");
						if(incidentResourceFilter.getCheckboxFilter().isEquipment())reqCatList.add("E");
												
						if(null != reqCatList && reqCatList.size() > 0) {
							crit.add(Restrictions.conjunction()								
									.add(Expression.in("this.requestCategory", reqCatList))
									.add(Expression.isNull("this.parentResourceId"))
								);
						}
					}					
				}
				
				
				// filter by deletable true/false
				//TODO: Add additional criteria to determine if resource is deletable such as:
				// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
//				if (null != filter.getDeletable()) {
//					if(filter.getDeletable()) {
//						criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNULL));						
//					}
//					else {
//						criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNOTNULL));												
//					}			
//				}
				
				
				CriteriaBuilder.addCriteria(crit, criteria);
				
				// filter by deletable true/false
				//TODO: Add additional criteria to determine if resource is deletable such as:
				// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
				// Keep consistent with deletable criteria outlined in IncidentResourceServiceImpl.java-->getGrid method.
				if (null != incidentResourceFilter.getDeletable()) {
					if(incidentResourceFilter.getDeletable()) {
						crit.add(Restrictions.disjunction()
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.NA))
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.D))
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.R))
								//.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.L))
								.add(Expression.isNull("this.assignmentStatus"))
							);	
					}
					else {
						crit.add(Restrictions.disjunction()
								//.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.S))
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.F))
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.C))
								.add(Expression.eq("this.assignmentStatus", AssignmentStatusTypeEnum.P))
							);		
					}			
				}
				
				
				if(null != incidentResourceFilter.getCheckboxFilter()) {
					if(incidentResourceFilter.getCheckboxFilter().isExcludeFilled()){
						crit.add(Restrictions.or(Restrictions.ne("this.assignmentStatus", AssignmentStatusTypeEnum.F)
								, Restrictions.isNull("this.assignmentStatus")));
					}
					if(incidentResourceFilter.getCheckboxFilter().getExcludeDemob()){
						crit.add(Restrictions.conjunction()
								.add(Expression.ne("this.assignmentStatus", AssignmentStatusTypeEnum.D))
								.add(Expression.ne("this.assignmentStatus", AssignmentStatusTypeEnum.R))
								//.add(Expression.isNull("this.assignmentStatus"))
							);		
					}					
				}

				if(StringUtility.hasValue(incidentResourceFilter.getResourceName())){
					crit.add(Restrictions.disjunction() 
							.add(Restrictions.ilike("this.resourceName", incidentResourceFilter.getResourceName().toUpperCase(),MatchMode.START))
							.add(Restrictions.ilike("this.lastName", incidentResourceFilter.getResourceName().toUpperCase(),MatchMode.START))
							);
				}
				
				//sort, if sort fields were passed
				if(CollectionUtility.hasValue(sortFields)) {
					
					for (String sortField : sortFields) {
						
						if(sortField.equalsIgnoreCase("itemName")) sortField = "kindDescription";
						crit.addOrder(Order.asc("this." + sortField));
					}
				}else{
					crit.addOrder(Order.asc("this.requestNumberSortValue"));
				}
				
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}


		Collection<IncidentResourceGridVo>  vos = crit.list();

		if(null != incidentResourceFilter.getCheckboxFilter()) {
			if(!incidentResourceFilter.getCheckboxFilter().isPersonnel()){
				// always get children for parent resources not already in the collection
				Collection<Long> resourceIds = new ArrayList<Long>();
				if(null != vos){
					for(IncidentResourceGridVo vo : vos ){
						resourceIds.add(vo.getResourceId());
					}
				}
				if(null != resourceIds && resourceIds.size()>0){
					Collection<IncidentResourceGridVo> childVos = this.getGridChildren(incidentResourceFilter, resourceIds);
					if(null != childVos){
						vos.addAll(childVos);
					}
				}
			}			
		}
		
		return vos;
	}

	public Collection<IncidentResourceGridVo> getGridChildren(IncidentResourceFilter filter,Collection<Long> resourceIds) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceViewImpl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("cellPhoneNumber"), "cellPhoneNumber")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciArrivalJetPort"),"ciArrivalJetPort")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"plansRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmReleaseDate"),"actualReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("workAreaId"),"workAreaId")
				.add(Projections.property("workAreaIncidentId"),"workAreaIncidentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("dmReassignable"),"dmReassignable")
				.add(Projections.property("dmCheckoutFormPrinted"),"dmCheckoutFormPrinted")
				.add(Projections.property("dmPlanningDispatchNotified"),"dmPlanningDispatchNotified")
				.add(Projections.property("dmReleaseDispatchNotified"),"dmReleaseDispatchNotified")
				.add(Projections.property("dmRestOvernight"),"dmRestOvernight")
				.add(Projections.property("dmReleaseRemarks"),"dmReleaseRemarks")
				.add(Projections.property("dmEstimatedArrivalDate"),"dmEstimatedArrivalDate")
				.add(Projections.property("dmAirDispatchNotified"),"dmAirDispatchNotified")
				.add(Projections.property("airRemarks"),"airRemarks")
				.add(Projections.property("hoursToAirport"),"hoursToAirport")
				.add(Projections.property("minutesToAirport"),"minutesToAirport")
				.add(Projections.property("itineraryReceived"),"itineraryReceived")
				.add(Projections.property("nameOnPictureId"),"nameOnPictureId")
				.add(Projections.property("departFromJetport"),"departFromJetport")
				.add(Projections.property("overnightRemarks"),"overnightRemarks")
				.add(Projections.property("departmentCode"),"departmentCode")
				.add(Projections.property("departmentSubCode"),"departmentSubCode")
				.add(Projections.property("departmentDesc"),"departmentDesc")
				.add(Projections.property("departmentSubDesc"),"departmentSubDesc")
				.add(Projections.property("assignmentTimeId"), "assignmentTimeId")
				.add(Projections.property("leaderType"),"leaderTypeNumber")
				.add(Projections.property("accountCode"),"accountingCode")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		criteria.add(new FilterCriteria("this.irActive", true ,FilterCriteria.TYPE_EQUAL));
		
		if (filter != null) {
			try{
				if( (null==filter.getWorkAreaId()) || (filter.getWorkAreaId().equals(0L))){
					throw new PersistenceException("WorkAreaId cannot be empty.");
				}
				if( (null==filter.getWorkAreaIncidentId()) || (filter.getWorkAreaIncidentId().equals(0L))){
					if( (null==filter.getWorkAreaIncidentGroupId()) || (filter.getWorkAreaIncidentGroupId().equals(0L))){
						throw new PersistenceException("WorkAreaIncidentId and WorkAreaIncidentGroupId cannot both be empty.");
					}
				}

				criteria.add( null != filter.getWorkAreaId() && filter.getWorkAreaId() > 0L ? new FilterCriteria("this.workAreaId",filter.getWorkAreaId(),FilterCriteria.TYPE_EQUAL) : null);
				
				if(null != filter.getWorkAreaIncidentId() && filter.getWorkAreaIncidentId() > 0L){
					criteria.add( new FilterCriteria("this.workAreaIncidentId",filter.getWorkAreaIncidentId(),FilterCriteria.TYPE_EQUAL));
				}else if (null != filter.getWorkAreaIncidentGroupId() && filter.getWorkAreaIncidentGroupId() > 0L) {
					String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getWorkAreaIncidentGroupId()+")";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				
				CriteriaBuilder.addCriteria(crit, criteria);

				//crit.add(Restrictions.in("this.parentResourceId",resourceIds));
				crit.add(Restrictions.not(Restrictions.in("this.resourceId", resourceIds)));

				String strIds="";
				for(Long id : resourceIds){
					strIds = ( strIds.length()<1 ? String.valueOf(id) : strIds + ","+String.valueOf(id));
				}
				String sqlFilter="";
				if(super.isOracleDialect()){
					sqlFilter="( this_.resource_id in " +
							  "      (select r.id as resourceId " +
							  "       from isw_resource r "+
							  "       where R.ID in ( " +
							  "           select id " +
							  "           from isw_resource where parent_resource_id is not null " +
							  "  		  and parent_resource_id in (" + strIds + ")" +							  
							  "       ) " +
							  "       and r.id not in (" + strIds + ")) " +
							  " ) "; 					
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}else{
					sqlFilter="( " +
							  "  this_.resource_id in " +
							  "	   (select r.id as resourceId " +
							  "	     from isw_resource r " +
							  "	     where r.id in ( " +
							  "		     select id from isw_resource " +
							  "		     where parent_resource_id in ( " +
							  "			    WITH n(parent_resource_id) AS " +
							  "			    ( " +
							  "				   SELECT parent_resource_id " +
							  "				   FROM isw_resource as n " +
							  "				   where id in ( " + strIds + " ) " +
							  "			    ) " +
							  "			    SELECT parent_resource_id from n " +
							  "		     ) " +
						      "		) and r.id not in ( " + strIds + ") " + 	
							  "    ) " +
						      ") " ;
					crit.add(Restrictions.sqlRestriction(sqlFilter));
				}
				
				return crit.list();
				
			}catch(Exception e){
				
			}
		}
	
		
		return null;
	}
	
	public Collection<IncidentResourceGridVo> getRosterResourceGrid(IncidentResourceFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentResourceViewImpl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"resourceAgencyCode")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"resourceUnitCode")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"ciPrePlanningRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"kindCode")
				.add(Projections.property("kindDescription"),"kindDescription")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("workAreaId"),"workAreaId")
				.add(Projections.property("workAreaIncidentId"),"workAreaIncidentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("employmentType"),"employmentType")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));

		if (filter != null) {
			try{
				if( (null==filter.getWorkAreaId()) || (filter.getWorkAreaId().equals(0L))){
					throw new PersistenceException("WorkAreaId cannot be empty.");
				}
				if( (null==filter.getResourceId()) || (filter.getResourceId().equals(0L))){
					throw new PersistenceException("ResourceId cannot be empty.");
				}
				
				crit.add(Restrictions.eq("this.workAreaId", filter.getWorkAreaId()));
				
				crit.add(Restrictions.disjunction()
							.add(Restrictions.eq("this.resourceId", filter.getResourceId()))
							.add(Restrictions.eq("this.parentResourceId",filter.getResourceId()))
						);

				crit.add(Restrictions.or(Restrictions.ne("this.assignmentStatus", AssignmentStatusTypeEnum.D)
						, Restrictions.isNull("this.assignmentStatus")));
					
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}


		return crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getIncidentResourceCheckInDemobData(java.lang.Long)
	 */
	public IncidentResourceCheckInDemobVo getIncidentResourceCheckInDemobData(Long incidentResourceId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);

		crit.setFetchMode("resource",FetchMode.JOIN);
		crit.setFetchMode("workPeriod",FetchMode.JOIN);
		crit.setFetchMode("workPeriod.ciResourceMobilization", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.dmAirTravel", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.ciArrivalJetPort",FetchMode.JOIN);
		crit.setFetchMode("workPeriod.dmAirTravel",FetchMode.JOIN);
		crit.setFetchMode("workPeriod.dmTentativeDemobState",FetchMode.JOIN);
		crit.createAlias("resource", "r");
		crit.createAlias("workPeriod", "wp");

		crit.setProjection(Projections.projectionList()
				.add(Projections.property("wp.ciRentalLocation"), "ciRentalLocation")
				.add(Projections.property("wp.ciFirstWorkDate"), "ciFirstWorkDate")
				.add(Projections.property("wp.ciPrePlanningRemarks"), "ciPrePlanningRemarks")
				.add(Projections.property("wp.ciLengthAtAssignment"), "ciLengthAtAssignment")
				.add(Projections.property("wp.ciResourceMobilization"), "ciResourceMobilization")
				.add(Projections.property("wp.ciArrivalJetPort"), "ciArrivalJetPort")
				.add(Projections.property("wp.dmTentativeDemobCity"), "dmTentativeDemobCity")
				.add(Projections.property("wp.dmTentativeDemobState.id"), "dmTentativeDemobState")
				.add(Projections.property("wp.dmReAssignable"), "dmReAssignable")
				.add(Projections.property("wp.dmRestOvernight"), "dmRestOvernight")
				.add(Projections.property("wp.dmTentativeReleaseDate"), "dmTentativeReleaseDate")
				.add(Projections.property("wp.dmReleaseDispatchNotified"), "dmReleaseDispatchNotified")
				.add(Projections.property("wp.dmPlanningDispatchNotified"), "dmPlanningDispatchNotified")
				.add(Projections.property("wp.dmCheckoutFormPrinted"), "dmCheckoutFormPrinted")
				.add(Projections.property("wp.dmReleaseRemarks"), "dmReleaseRemarks")
				.add(Projections.property("wp.dmPlanningRemarks"), "dmPlanningRemarks")
				.add(Projections.property("wp.dmTravelMethod"), "dmTravelMethod")
				.add(Projections.property("wp.dmAirTravel"), "dmAirTravel")
				.add(Projections.property("wp.id"), "workPeriodId")
				.add(Projections.property("this.resourceId"), "resourceId")
				.add(Projections.property("this.incidentId"), "incidentId")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceCheckInDemobVo.class));

		try{
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

			criteria.add( null != incidentResourceId && incidentResourceId > 0L ? new FilterCriteria("this.id",incidentResourceId,FilterCriteria.TYPE_EQUAL) : null);

			if(criteria.size()==0){
				throw new PersistenceException("incidentResourceId must be set to get IncidentResourceCheckInDemobData.");
			}

			CriteriaBuilder.addCriteria(crit, criteria);

		}catch(Exception e){
			throw new PersistenceException(e);
		}

		IncidentResourceCheckInDemobVo vo = (IncidentResourceCheckInDemobVo)crit.uniqueResult();

		if(null != vo){
			Collection<ResourceKindVo> resourceKindVos = (Collection<ResourceKindVo>)this.getResourceKindVos(vo.getResourceId());
			if(null!=resourceKindVos)
				vo.setResourceKindVos(resourceKindVos);
			Collection<IncidentQuestionVo> incidentQuestions = (Collection<IncidentQuestionVo>)this.getIncidentQuestions(vo.getIncidentId());
			if(null!=incidentQuestions){
				vo.setIncidentQuestions(incidentQuestions);
			}
			Collection<WorkPeriodQuestionValueVo> wpQuestionValueVos = (Collection<WorkPeriodQuestionValueVo>)this.getWorkPeriodQuestionValueVos(vo.getWorkPeriodId());
			if(null!=wpQuestionValueVos){
				vo.setWorkPeriodQuestionValues(wpQuestionValueVos);
			}
		}

		return vo;
	}

	/**
	 * Helper method to return collection of resourceKindVo's for the resourceId supplied.
	 * 
	 * @param resourceId
	 * 			the resourceId to filter by
	 * @return
	 * 		collection of resourceKindVo's
	 * @throws PersistenceException
	 */
	private Collection<ResourceKindVo> getResourceKindVos(Long resourceId) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(ResourceKindImpl.class);

		crit.setFetchMode("kind",FetchMode.JOIN);

		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("kind"), "kind")
				.add(Projections.property("kindId"), "kindId")
				.add(Projections.property("training"), "training")
		);

		crit.setResultTransformer(Transformers.aliasToBean(ResourceKindVo.class));

		crit.add(Expression.eq("this.resourceId",resourceId));

		return crit.list();
	}

	/**
	 * Helper method to return collection of incidentQuestionVo's for the incidentId supplied.
	 * 
	 * @param incidentId
	 * 			the incidentId to filter by
	 * @return
	 * 		collection of incidentQuestionVo's
	 * @throws PersistenceException
	 */
	public Collection<IncidentQuestionVo> getIncidentQuestions(Long incidentId) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);

		crit.setFetchMode("question",FetchMode.JOIN);
		crit.setFetchMode("incident",FetchMode.JOIN);

		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("question"), "question")
				.add(Projections.property("incident"), "incident")
				.add(Projections.property("visible"), "visible")
				.add(Projections.property("position"), "position")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentQuestionVo.class));

		crit.add(Expression.eq("this.incidentId",incidentId));
		crit.add(Expression.eq("this.visible", Boolean.TRUE));
		
		return crit.list();
	}

	/**
	 * Helper method to return collection of workPeriodQuestionValueVo's for the workPeriodId supplied.
	 * 
	 * @param workPeriodId
	 * 			the workPeriodId to filter by
	 * @return
	 * 		collection of workPeriodQuestionValueVo's
	 * @throws PersistenceException
	 */
	private Collection<WorkPeriodQuestionValueVo> getWorkPeriodQuestionValueVos(Long workPeriodId) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(WorkPeriodQuestionValueImpl.class);

		crit.setFetchMode("incidentQuestion",FetchMode.JOIN);
		crit.setFetchMode("incidentQuestion.question",FetchMode.JOIN);

		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("workPeriodId"), "workPeriodId")
				.add(Projections.property("incidentQuestion"), "incidentQuestion")
				.add(Projections.property("incidentQuestionId"), "incidentQuestionId")
				.add(Projections.property("questionValue"), "questionValue")
		);

		crit.setResultTransformer(Transformers.aliasToBean(WorkPeriodQuestionValueVo.class));

		crit.add(Expression.eq("this.workPeriodId",workPeriodId));

		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentResource> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
	 */
	public void delete(IncidentResource persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(IncidentResource persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentResource getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentResourceImpl.class);
	}

	public Collection<IncidentResource> getAllByIncidentId(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);
		crit.createAlias("resource", "r");
		
		crit.add(Restrictions.eq("incidentId", incidentId));
		crit.add(Restrictions.isNull("r.parentResourceId"));
		
		return (Collection<IncidentResource>)crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#deleteQualifications(java.util.Collection)
	 */
	public void deleteQualifications(Collection<Long> ids) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#getResourceAssignmentData(java.lang.Long)
	 */
	public ResourceAssignmentVo getResourceAssignmentData(Long id) throws PersistenceException {
		ResourceAssignmentVo rav = new ResourceAssignmentVo();
		/*
      rav.setResourceCommonVo(this.getResourceCommonData(id));
      rav.setResourceCheckinDemobVo(this.getResourceCheckinDemobData(id));
      rav.setResourceTimeVo(this.getResourceTimeData(id));
      rav.setResourceCostVo(this.getResourceCostData(id));
		 */
		return rav;
	}


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getIncidentResourceCommonData(java.lang.Long, java.lang.Long)
	 */
	public IncidentResourceCommonVo getIncidentResourceCommonData(Long resourceId, Long assignmentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceCommonViewImpl.class);

		crit.createAlias("kind", "k");

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("person"), "person")
				.add(Projections.property("resourceName"), "resourceName")
				.add(Projections.property("lastName"), "lastName")
				.add(Projections.property("firstName"), "firstName")
				.add(Projections.property("nameOnPictureId"), "nameOnPictureId")
				.add(Projections.property("requestCategory"), "requestCategory")
				.add(Projections.property("phone"), "phone")
				.add(Projections.property("incidentId"), "incidentId")
				.add(Projections.property("incidentResourceId"), "incidentResourceId")
				.add(Projections.property("nameAtIncident"), "nameAtIncident")
				.add(Projections.property("workPeriodId"), "workPeriodId")
				.add(Projections.property("ciCheckInDate"), "ciCheckInDate")
				.add(Projections.property("dmReleaseDate"), "dmReleaseDate")
				.add(Projections.property("resourceAgencyId"), "resourceAgencyId")
				.add(Projections.property("resourceOrganizationId"), "resourceOrganizationId")
				.add(Projections.property("resourceAgencyCode"), "resourceAgencyCode")
				.add(Projections.property("resourceUnitCode"), "resourceUnitCode")
				.add(Projections.property("assignmentId"), "assignmentId")
				.add(Projections.property("requestNumber"), "requestNumber")
				.add(Projections.property("assignmentEndDate"), "assignmentEndDate")
				.add(Projections.property("assignmentStatus"), "assignmentStatus")
				.add(Projections.property("training"), "training")
				.add(Projections.property("kindId"), "kindId")
				.add(Projections.property("k.description"), "kindDescription")
				.add(Projections.property("resourceClassification"), "resourceClassification")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceCommonVo.class));

		try{
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

			criteria.add( null != resourceId && resourceId > 0L ? new FilterCriteria("this.resourceId",resourceId,FilterCriteria.TYPE_EQUAL) : null);
			if(criteria.size()==0)
				criteria.add( null != assignmentId && assignmentId > 0L ? new FilterCriteria("this.assignmentId",assignmentId,FilterCriteria.TYPE_EQUAL) : null);

			if(criteria.size()==0){
				throw new PersistenceException("Either resourceId or assignmentId must be set to get IncidentResourceCommonData.");
			}

			CriteriaBuilder.addCriteria(crit, criteria);
		}catch(Exception e){
			throw new PersistenceException(e);
		}

		return (IncidentResourceCommonVo) crit.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#getResourceCostData(java.lang.Long)
	 */
	public ResourceCostVo getResourceCostData(Long id) throws PersistenceException {
		//TODO Need to create query when implementing cost module
		return new ResourceCostVo();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#getResourceTimeData(java.lang.Long)
	 */
	public ResourceTimeVo getResourceTimeData(Long id) throws PersistenceException {
		//TODO Need to create query when implementing time module
		return new ResourceTimeVo();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#saveQualifications()
	 */
	public void saveQualifications() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.resource.IncidentResourceDao#saveQualifications(java.util.Collection)
	 */
	public void saveQualifications(Collection<Long> ids) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getAllIncidentResourcesReportData(gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter)
	 */
	public Collection<AllIncidentResourcesReportData> getAllIncidentResourcesReportData(AllIncidentResourcesReportFilter filter ) throws PersistenceException{

		StringBuffer b = new StringBuffer();
		b.append("SELECT i.incident_name as incidentName")
		.append(",i.nbr as incidentNumber")
		.append(",r.resource_name as resourceName")
		.append(", r.last_name as lastName")
		.append(", r.first_name as firstName")
		.append(", r.phone as contactPhone")
		.append(",ag.agency_code as agency")
		.append(",org.unit_code as unit")
		.append(",wp.dm_tentative_demob_city as demobCity")
		.append(",wp.dm_travel_method as returnTravelMethod")
		.append(",wp.ci_check_in_date as checkInDate")
		.append(",a.assignment_status as status")
		.append(",cs.cs_name as demobState")
		.append(",k.code as kind")
		.append(",rq.description as requestCategory")
		.append(",a.request_number as requestNumber")
		.append(",r.is_person as person")
		.append(" FROM isw_incident i")
		.append(",isw_incident_resource ir")
		.append(",isw_resource r")
		.append("  left join iswl_agency ag on r.agency_id = ag.id")
		.append("  left join isw_organization org on r.organization_id = org.id")
		.append(",isw_work_period wp")
		.append("  left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id")
		.append("  left join iswl_jet_port jp on wp.ci_arrival_jet_port_id = jp.id")
		.append(",isw_work_period_assignment wpa")
		.append(",isw_assignment a")
		.append(",iswl_kind k")
		.append(",iswl_request_category rq")
		.append(" WHERE i.id = "+filter.getIncidentId())
		.append(" AND ir.incident_id = i.id")
		.append(" AND r.id = ir.resource_id")
		.append(" AND wp.incident_resource_id = ir.id")
		.append(" AND wpa.work_period_id = wp.id")
		.append(" AND a.id = wpa.assignment_id")
		.append(" AND k.id = a.kind_id")
		.append(" AND rq.id = k.request_category_id");

		if(filter.getResourceTypes().size()>0){
			String[] resourceTypes = new String[filter.getResourceTypes().size()];
			filter.getResourceTypes().toArray(resourceTypes);
			b.append(" AND k.code in (" + resourceTypes + ")");
		}

		b.append(" ORDER BY r.resource_name,r.last_name");

		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());


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
		 * ie: crt.addAlias("nbr","incidentNumber")
		 */
		//crt.addAlias("incidentName", "incidentName");

		/*
		 * Add scalar to handle for both oracle/postgres
		 */
		crt.addScalar("person", Boolean.TRUE.getClass().getName());

		query.setResultTransformer(crt); 

		query.setMaxResults(getMaxResultSize());

		return query.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#disableResources(java.util.Collection, boolean)
	 */
	public int disableResources(Collection<Long> resourceIds,boolean removeParentAssociations) throws PersistenceException{
		Query q = null;

		if(removeParentAssociations)
			q = getHibernateSession().createQuery(ResourceQuery.DISABLE_RESOURCES_REMOVE_PARENT_ASSOC_QUERY );
		else
			q = getHibernateSession().createQuery(ResourceQuery.DISABLE_RESOURCES_QUERY);

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameter("component", Boolean.FALSE);
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();

		return rslt;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#enableResources(java.util.Collection)
	 */
	public int enableResources(Collection<Long> resourceIds) throws PersistenceException {
		Query q = getHibernateSession().createQuery(ResourceQuery.ENABLE_RESOURCES_QUERY);

		q.setParameter("enabled", Boolean.TRUE);
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();

		return rslt;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getAvailableResourcesForRoster(gov.nwcg.isuite.core.filter.IncidentResourceFilter)
	 */
	public Collection<IncidentResourceGridVo> getAvailableResourcesForRoster(IncidentResourceFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceOnlyViewImpl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"ciPrePlanningRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("workAreaId"),"workAreaId")
				.add(Projections.property("workAreaIncidentId"),"workAreaIncidentId")
				.add(Projections.property("leaderName"),"leaderName")
		);

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));

		if (filter != null) {
			try{
				if( (null==filter.getWorkAreaId()) || (filter.getWorkAreaId().equals(0L))){
					throw new PersistenceException("WorkAreaId cannot be empty.");
				}
				if( (null==filter.getWorkAreaIncidentId()) || (filter.getWorkAreaIncidentId().equals(0L))){
					throw new PersistenceException("WorkAreaIncidentId cannot be empty.");
				}
				if( (null==filter.getResourceId()) || (filter.getResourceId().equals(0L))){
					throw new PersistenceException("Parent ResourceId cannot be empty.");
				}

				crit.add(Restrictions.eq("this.workAreaId", filter.getWorkAreaId()));
				
				crit.add(Restrictions.eq("this.incidentId", filter.getWorkAreaIncidentId()));

				if(null != filter.getRosterParentId()){
					crit.add(Restrictions.ne("this.resourceId", filter.getRosterParentId()));
					crit.add(Restrictions.isNull("this.parentResourceId"));
					String sqlFilter = "";
					
					if(super.isOracleDialect()){
						sqlFilter="( " +
							      "this_.resource_id not in ( " +
							      "      SELECT parent_resource_id " +
							      "      FROM " +
							      "      ISW_RESOURCE WHERE parent_resource_id is not null CONNECT BY PRIOR id=parent_resource_id " +
							      "      START WITH id = (select parent_resource_id from isw_resource where id = " + filter.getRosterParentId() + " ) " +
							      "      ) " +
							      ") ";
						
						crit.add(Restrictions.sqlRestriction(sqlFilter));
					}else{
						sqlFilter="( " +
								  " this_.resource_id not in ( " +
								  "		select distinct(parent_resource_id) from isw_resource " +
								  "	    where parent_resource_id in ( " +
								  "			WITH n(parent_resource_id) AS " +
								  "			( " +
								  "				SELECT parent_resource_id " +
								  "				FROM isw_resource as n " + 
								  "				where id = " + filter.getRosterParentId() + " " +
								  "			) " +
								  "			SELECT parent_resource_id from n " +
								  "	    )	" +
								  " ) " +
							      ") ";						
						crit.add(Restrictions.sqlRestriction(sqlFilter));
					}
				}else{
					crit.add(Restrictions.ne("this.resourceId", filter.getResourceId()));
				}
				
				crit.add(Restrictions.disjunction()
						.add(Expression.isNull("this.assignmentStatus"))
						.add(Restrictions.conjunction()
						.add(Expression.ne("this.assignmentStatus", AssignmentStatusTypeEnum.D))
						.add(Expression.ne("this.assignmentStatus", AssignmentStatusTypeEnum.R)))
					);	

				//crit.add(Restrictions.or(Restrictions.ne("this.assignmentStatus", AssignmentStatusTypeEnum.D)
				//		, Restrictions.isNull("this.assignmentStatus")));

				crit.add(Restrictions.eq("this.enabled", Boolean.TRUE));
				
				if(StringUtility.hasValue(filter.getResourceName())){
					crit.add(Restrictions.disjunction() 
							.add(Restrictions.ilike("this.resourceName", filter.getResourceName().toUpperCase(),MatchMode.START))
							.add(Restrictions.ilike("this.lastName", filter.getResourceName().toUpperCase(),MatchMode.START))
							);
				}
				
				if(StringUtility.hasValue(filter.getRequestNumber())) {
					crit.add(Restrictions.ilike("this.requestNumber", filter.getRequestNumber(), MatchMode.START));
				}
				
				if(StringUtility.hasValue(filter.getItemName())) {
					crit.add(Restrictions.ilike("this.kindDescription", filter.getItemName(), MatchMode.START));
				}
				
				if(StringUtility.hasValue(filter.getUnitId())) {
					crit.add(Restrictions.ilike("this.resourceUnitCode", filter.getUnitId(), MatchMode.START));
				}

				if(StringUtility.hasValue(filter.getAgency() )) {
					crit.add(Restrictions.ilike("this.resourceAgencyCode", filter.getAgency(), MatchMode.START));
				}
				
				if(null != filter.getAssignmentStatus()) {
					crit.add(Restrictions.eq("this.assignmentStatus", filter.getAssignmentStatus()));
				}
				
				//
//				criteria.add( null != filter.getRequestNumber() && !filter.getRequestNumber().isEmpty() ? new FilterCriteria("this.requestNumber",filter.getRequestNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
//				criteria.add( null != filter.getKindDescription() && !filter.getKindDescription().isEmpty() ? new FilterCriteria("this.kindDescription",filter.getKindDescription().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
//				criteria.add( null != filter.getResourceOrganizationName() && !filter.getResourceOrganizationName().isEmpty() ? new FilterCriteria("this.resourceOrganizationName",filter.getResourceOrganizationName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
				//
				

				//String sqlFilter = "this_.resource_id not in (select parent_resource_id from isw_resource where id = "+filter.getResourceId()+")";
				//crit.add(Restrictions.sqlRestriction(sqlFilter));
				
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}


		return crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getAvailableWorkAreaResourcesForRoster(gov.nwcg.isuite.core.filter.IncidentResourceFilter)
	 */
	public Collection<IncidentResourceGridVo> getAvailableWorkAreaResourcesForRoster(IncidentResourceFilter filter) throws PersistenceException {
		
		if( (null==filter.getWorkAreaId()) || (filter.getWorkAreaId() < 1)) {
			throw new PersistenceException("Work Area id is a required field.");
		}
		if( (null==filter.getWorkAreaIncidentId()) || (filter.getWorkAreaIncidentId() < 1)) {
			throw new PersistenceException("Work Area Incident id is a required field.");
		}
		if( (null==filter.getResourceId()) || (filter.getResourceId() < 1)) {
			throw new PersistenceException("Parent Resource id is a required field.");
		}
		
		String sql = IncidentResourceQuery.GET_AVAILABLE_WA_RESOURCES_FOR_ROSTER_QUERY;

		//resource name
		if(StringUtility.hasValue(filter.getResourceName())) {
			
			sql +=
				" and (r.resource_name LIKE '" + filter.getResourceName().toUpperCase() + "' or r.last_name LIKE '%" + filter.getResourceName().toUpperCase() + "%')";
		}
		
		//agency 
		if(StringUtility.hasValue(filter.getResourceAgencyName())) {
			sql += " and ag.agency_name LIKE '" + filter.getResourceAgencyName().toUpperCase() + "%' ";
		}
		
		//unit code
		if(StringUtility.hasValue(filter.getResourceUnitCode())) {
			
			sql += " and org.unit_code LIKE '" + filter.getResourceUnitCode().toUpperCase() + "%'";
		}
		
		//kind description
		if(StringUtility.hasValue(filter.getKindDescription())) {
			
			sql += " and k.description LIKE '" + filter.getKindDescription().toUpperCase() + "%'";
		}
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.setLong("waid", filter.getWorkAreaId());
		query.setLong("incidentid", filter.getWorkAreaIncidentId());
		query.setLong("parentresourceid", filter.getResourceId());
		
		query.setParameter("primary", (super.isOracleDialect() ? 1 : true ));
		
        CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceGridVo.class);
 	    crt.addProjection("resourceId", "resourceId");
 	    crt.addProjection("resourceName", "resourceName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("orgName", "resourceOrganizationName");
 	    crt.addProjection("orgCode", "resourceUnitCode");
 	    crt.addProjection("agencyName", "resourceAgencyName");
 	    crt.addProjection("agencyCode", "resourceAgencyCode");
 	    crt.addProjection("kindDescription", "itemName");
 	    
        crt.addScalar("resourceId", Long.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
	}
	
	public void unrosterResources(Collection<Long> ids) throws PersistenceException {
		SQLQuery query = getHibernateSession().createSQLQuery(IncidentResourceQuery.GET_UNROSTER_RESOURCES_QUERY());
		
		query.setParameterList("ids", ids);

		query.executeUpdate();	

		String q2 = "UPDATE ISW_RESOURCE SET IS_COMPONENT = " + super.getBooleanComparison(false) + " " +
		  " WHERE ID IN ( :ids ) AND IS_COMPONENT = " + super.getBooleanComparison(true) + " " +
		  " AND (RESOURCE_CLASSIFICATION IS NULL  OR RESOURCE_CLASSIFICATION not in ('ST','TF') )" ;
		
		SQLQuery query2 = getHibernateSession().createSQLQuery(q2);
		query2.setParameterList("ids",ids);
		
		query2.executeUpdate();
		
	}

	public Collection<IncidentResourceGridVo> checkRequestNumber(Long workAreaId, IncidentResourceVo vo) throws PersistenceException {
		
		IncidentResourceFilter filter = new IncidentResourceFilterImpl();
		Collection<IncidentResourceVo> vos;// = new ArrayList<IncidentResourceVo>();
		
		filter.setWorkAreaIncidentId(vo.getIncidentVo().getId());
		filter.setRequestNumber(vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber());
		filter.setResourceId(vo.getResourceVo().getId());
		
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceView2Impl.class);
		//crit.add(Restrictions.eq("this.workAreaId", workAreaId));
		crit.add(Restrictions.eq("this.incidentId", filter.getWorkAreaIncidentId()));
		if(filter.getRequestNumber() != null) {
			crit.add(Restrictions.eq("this.requestNumber", filter.getRequestNumber().toUpperCase()));
		}
		crit.add(Expression.ne("id", filter.getResourceId()));

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("cellPhoneNumber"), "cellPhoneNumber")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciArrivalJetPort"),"ciArrivalJetPort")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"plansRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmReleaseDate"),"actualReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("dmReassignable"),"dmReassignable")
				.add(Projections.property("dmCheckoutFormPrinted"),"dmCheckoutFormPrinted")
				.add(Projections.property("dmPlanningDispatchNotified"),"dmPlanningDispatchNotified")
				.add(Projections.property("dmReleaseDispatchNotified"),"dmReleaseDispatchNotified")
				.add(Projections.property("dmRestOvernight"),"dmRestOvernight")
				.add(Projections.property("dmReleaseRemarks"),"dmReleaseRemarks")
				.add(Projections.property("dmEstimatedArrivalDate"),"dmEstimatedArrivalDate")
				.add(Projections.property("dmAirDispatchNotified"),"dmAirDispatchNotified")
				.add(Projections.property("airRemarks"),"airRemarks")
				.add(Projections.property("hoursToAirport"),"hoursToAirport")
				.add(Projections.property("minutesToAirport"),"minutesToAirport")
				.add(Projections.property("itineraryReceived"),"itineraryReceived")
				.add(Projections.property("nameOnPictureId"),"nameOnPictureId")
				.add(Projections.property("departFromJetport"),"departFromJetport")
				.add(Projections.property("overnightRemarks"),"overnightRemarks")
				.add(Projections.property("departmentCode"),"departmentCode")
				.add(Projections.property("departmentSubCode"),"departmentSubCode")
				.add(Projections.property("departmentDesc"),"departmentDesc")
				.add(Projections.property("departmentSubDesc"),"departmentSubDesc")
		);		

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		
		return crit.list();
	}
	
	public Collection<IncidentResourceGridVo> getStrikeTeams(Long incidentId, Collection<Long> incidentIds) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentResourceView2Impl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("requestNumber"), "requestNumber")
				.add(Projections.property("resourceName"), "resourceName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("lastName"),"lastName")
			);

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		if(CollectionUtility.hasValue(incidentIds)){
			crit.add(Restrictions.in("this.incidentId", incidentIds));
		}

		StringBuffer sql = new StringBuffer();
		sql.append("this_.kind_code in (select code from iswl_kind where is_strike_team = " + super.getBooleanComparison(true) + " ) ");
		
		try{
			CriteriaBuilder.addCriteria(crit, criteria);
			crit.add(Restrictions.sqlRestriction(sql.toString()));
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getByIncidentId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentResourceVo> getByIncidentId(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);

		crit.add(Restrictions.eq("incidentId", incidentId));
		Collection<IncidentResource> entities = crit.list();
		try {
			return IncidentResourceVo.getInstances(entities, true);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
	
	public void updateCheckOutFormPrinted(Collection<Long> ids) throws PersistenceException {
		String sql = "UPDATE ISW_WORK_PERIOD SET DM_IS_CHECKOUT_FORM_PRINTED = :val " +
			"WHERE id in ( :ids ) ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameter("val", (super.isOracleDialect() ? 1 : true ) );
		query.setParameterList("ids", ids);
		
		query.executeUpdate();
	}

	public void updateAirTravelDispatchNotified(Collection<Long> ids) throws PersistenceException {
		String sql = "UPDATE ISW_AIR_TRAVEL SET IS_DISPATCH_NOTIFIED = :val " +
			"WHERE id in ( select dm_air_travel_id from isw_work_period where id in (:ids ) ) ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameter("val", (super.isOracleDialect() ? 1 : true ) );
		query.setParameterList("ids", ids);
		
		query.executeUpdate();
	}
	
	public void updateTentReleaseDispathNotified(Collection<Long> ids) throws PersistenceException {
		String sql = "UPDATE ISW_WORK_PERIOD SET DM_IS_PLANNING_DISPATCH_NOTIF = :val " +
			"WHERE id in ( :ids ) ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameter("val", (super.isOracleDialect() ? 1 : true ) );
		query.setParameterList("ids", ids);
		
		query.executeUpdate();
	}

	public void updateActualReleaseDispathNotified(Collection<Long> ids) throws PersistenceException {
		String sql = "UPDATE ISW_WORK_PERIOD SET DM_IS_RELEASE_DISPATCH_NOTIF = :val " +
			"WHERE id in ( :ids ) ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameter("val", (super.isOracleDialect() ? 1 : true ) );
		query.setParameterList("ids", ids);
		
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getIncidentResourceIdByIncidentIdAndResourceId(java.lang.Long, java.lang.Long)
	 */
	public Long getIncidentResourceIdByIncidentIdAndResourceId(Long incidentId, Long resourceId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id")));
		crit.add(Restrictions.eq("incidentId", incidentId));
		crit.add(Restrictions.eq("resourceId", resourceId));
		return (Long)crit.uniqueResult();
	}

	public Collection<Long> getIncidentResourceChildrenIds(Long incidentResourceId) throws PersistenceException {
		Collection<Long> ids = new ArrayList<Long>();
		
		String sql = IncidentResourceQuery.getIncidentResourceChildrenIdsQuery(incidentResourceId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		try{
			Collection<Object> list = query.list();

			if(CollectionUtility.hasValue(list)){
				for(Object o : list){
					Long id = TypeConverter.convertToLong(o);
					ids.add(id);
				}
			}
		}catch(Exception e){
			
		}
		
		return ids;
	}
	
	/*
	 * 
	 */
	public Collection<IncidentResourceVo> getNonInvoicedIncidentResourcesById(Long incidentResourceId, Date startDate) throws PersistenceException, ServiceException {
		Collection<IncidentResource> irs;
		Collection<IncidentResourceVo> irvs;

		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);
		crit.add(Restrictions.or(Restrictions.eq("id", incidentResourceId), Restrictions
				.sqlRestriction("resource_id in (select id from isw_resource " + "where permanent_resource_id = "
						+ "(select permanent_resource_id from isw_resource where id = " 
						+ "(select resource_id from isw_incident_resource where id = " + incidentResourceId + ")))")));

		crit.setFetchMode("resource", FetchMode.JOIN);
		crit.setFetchMode("incident", FetchMode.JOIN);
		crit.setFetchMode("incident.agency", FetchMode.JOIN);
		crit.setFetchMode("incident.countrySubdivision", FetchMode.JOIN);
		crit.setFetchMode("workPeriod", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.mailingAddress", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.mailingAddress.countrySubdivision", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.contractor", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.contractor.address", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.contractor.address.countrySubdivision", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.adminOffice", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.adminOffice.address", FetchMode.JOIN);
		crit.setFetchMode("workPeriod.assignments.assignmentTime.contractorPaymentInfo.contractorAgreement.adminOffice.address.countrySubdivision", FetchMode.JOIN);
	
		try {
			irs = (Collection<IncidentResource>) crit.list();
			irvs = IncidentResourceVo.getInstances(irs, true);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		for (IncidentResourceVo irv : irvs) {
			Collection<AssignmentVo> as = irv.getWorkPeriodVo().getAssignmentVos();
			Collection<AssignmentVo> asToRemove = new ArrayList<AssignmentVo>();
			
			if(as.iterator().next().getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.CONTRACTOR) {
				Long rId = irv.getId();
				for(AssignmentVo av : as) {
					for(IncidentResource x : irs) {
						if(rId == x.getId()) {
							for(Assignment a : x.getWorkPeriod().getAssignments()){
								if(av.getId().compareTo(a.getId())==0) {
									try {
										if(null != a.getAssignmentTime().getContractorPaymentInfo().getContractorAgreement()){
											if(null != a.getAssignmentTime().getContractorPaymentInfo().getContractorAgreement().getAdminOffice()){
												AdminOfficeVo aov = AdminOfficeVo.getInstance(a.getAssignmentTime().getContractorPaymentInfo().getContractorAgreement().getAdminOffice(), true);
												av.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorAgreementVo().setAdminOfficeVo(aov);
											}else{
												av.getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorAgreementVo().setAdminOfficeVo(new AdminOfficeVo());
											}
										}
									} catch (Exception e) {
										throw new ServiceException(e);
									}
									break;
								}
							}
						}
					}
				}
			}

			for (AssignmentVo a : as) {
				AssignmentTimeVo at = a.getAssignmentTimeVo();
				Criteria crit2 = getHibernateSession().createCriteria(AssignmentTimePostImpl.class);
				crit2.add(Restrictions.eq("assignmentTimeId", at.getId()));
				/*
				 9/7/2014  Dan - commenting out primaryPosting=true, if 2 contractor special postings
				 				 on same day, no time postings are found, defect 4367
				*/
				//crit2.add(Restrictions.eq("primaryPosting", true));
				crit2.add(Restrictions.le("postStartDate", DateUtil.makeEndOfDay(startDate)));
				crit2.add(Restrictions.sqlRestriction("this_.id not in (" + "select distinct assign_time_post_id "
						+ "from isw_assign_time_post_invoice ati, isw_time_invoice ti "
						+ "where ti.id = ati.time_invoice_id and ti.deleted_date is null "+
						"and ti.is_final = " + (super.isOracleDialect() ? 1 : true) +")"));
				crit2.addOrder(Order.asc("postStartDate"));

				Collection<AssignmentTimePost> atps = (Collection<AssignmentTimePost>) crit2.list();

				Criteria crit3 = getHibernateSession().createCriteria(TimeAssignAdjustImpl.class);
				crit3.add(Restrictions.eq("assignmentId", a.getId()));
				crit3.add(Restrictions.sqlRestriction("this_.id not in (" + "select distinct time_post_adjust_id "
						+ "from isw_time_assign_adj_invoice ata, isw_time_invoice ti "
						+ "where ti.id = ata.time_invoice_id and ti.deleted_date is null "+
						"and ti.is_final = " + (super.isOracleDialect() ? 1 : true) +")"));

				Collection<TimeAssignAdjust> adjs = (Collection<TimeAssignAdjust>) crit3.list();
				
				boolean posts = false;

				if (atps.size() > 0) {
					Collection<AssignmentTimePostVo> atpvs;
					try {
						atpvs = AssignmentTimePostVo.getInstances(atps, true);
					} catch (Exception e) {
						throw new ServiceException(e);
					}
					at.setAssignmentTimePostVos(atpvs);
					posts = true;
				}
				
				if (adjs.size() > 0) {
					Collection<TimeAssignAdjustVo> adjvs;
					try {
						adjvs = TimeAssignAdjustVo.getInstances(adjs, true);
					} catch (Exception e) {
						throw new ServiceException(e);
					}
					a.setTimeAssignAdjustVos(adjvs);
					posts = true;
				} else {
					a.setTimeAssignAdjustVos(new ArrayList<TimeAssignAdjustVo>());
				}
				if (!posts) {
					asToRemove.add(a);
				}
			}

			if (asToRemove.size() > 0) {
				for (AssignmentVo a : asToRemove) {
					as.remove(a);
				}
			}
		}

		return irvs;
  }
  
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#insertAssignmentTime(java.lang.Long)
	 */
	public Long assignmentTimeRecordCount(Long parentResourceId) throws PersistenceException {
		
		String sql = IncidentResourceQuery.getAssignmentTimeRecordCount(parentResourceId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		try {
			Long val = TypeConverter.convertToLong(rslt);
			return val;
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return 0L;
	}
	
	public Collection<Long> getCrewUpdateIds(Long parentResourceId) throws PersistenceException,Exception {
		
		String sql = IncidentResourceQuery.getAssignmentTimeRecordIds(parentResourceId,super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Collection<Object> ids = query.list();
		Collection<Long> rtnIds = new ArrayList<Long>();

		for(Object o : ids){
			Long rtnId=TypeConverter.convertToLong(o);
			rtnIds.add(rtnId);
		}
		
		return rtnIds;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#insertAssignmentTime(java.lang.Long)
	 */
	public void insertAssignmentTime(Long parentResourceId) throws PersistenceException {
		
		String sql = IncidentResourceQuery.getInsertAssignmentTime(parentResourceId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		try{
			query.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateCrewEmploymentType(java.lang.String, java.lang.Long)
	 */
	public void updateCrewEmploymentType(String type, Long parentResourceId) throws PersistenceException {
		
		String sql = IncidentResourceQuery.getUpdateCrewEmploymentType(super.isOracleDialect(),parentResourceId, type);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
		if(type.equals("OTHER")){
			///sql = IncidentResourceQuery.getUpdateCrewOtherRate(super.isOracleDialect(),parentResourceId);
			
			//query = getHibernateSession().createSQLQuery(sql);
			
			//query.executeUpdate();
		}
			
	}

	public void updateCrewHireInfo(String name, String phone, String fax, Long parentResourceId) throws PersistenceException {
		
		String sql = IncidentResourceQuery.getUpdateCrewHireInfo(super.isOracleDialect(),parentResourceId, name, phone, fax);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}

	public void updateOtherRate(Long assignmentTimeId, BigDecimal otherRate) throws PersistenceException {
		String sql = IncidentResourceQuery.getUpdateCrewOtherRate2(super.isOracleDialect(),assignmentTimeId, otherRate);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateCrewAddresses(java.lang.Long)
	 */
	public void updateCrewAddresses(Long incidentResourceId, Long parentResourceId) throws PersistenceException {
		String sql = IncidentResourceQuery.getUpdateCrewAddresses(incidentResourceId, parentResourceId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#clearCrewAddresses(java.lang.Long)
	 */
	public void clearCrewAddresses(Long parentResourceId) throws PersistenceException {
		String sql = IncidentResourceQuery.getClearCrewAddresses(parentResourceId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}

	public void clearNonContractorEmploymentType(Long id) throws PersistenceException {
		String sql = IncidentResourceQuery.getClearNonContractorEmploymentType(id);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}

	public void clearContractorInfo(Long assignmentTimeId) throws PersistenceException {
		String sql = IncidentResourceQuery.getClearContractorInfo(super.isOracleDialect(),assignmentTimeId);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
		sql = IncidentResourceQuery.getClearContractorRateInfo(assignmentTimeId);

		query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
		sql="DELETE FROM ISW_CONTR_PAYMENT_INFO WHERE ASSIGNMENT_TIME_ID = " + assignmentTimeId + " ";
		query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	public void deleteAdPaymentInfo(Long id) throws PersistenceException {
		String sql = IncidentResourceQuery.getDeleteAdInfo(id);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getUnassignedInventoryResources(java.lang.String)
	 */
	public Collection<ResourceInventoryGridVo> getUnassignedInventoryResources(String dispatchCenter,Long userId) throws PersistenceException {
		
		
		String sql = IncidentResourceQuery.getUnassignedResourceInventoryResources(dispatchCenter,userId,super.isOracleDialect());

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

        CustomResultTransformer crt = new CustomResultTransformer(ResourceInventoryGridVo.class);
 	    crt.addProjection("resourceId", "id");
 	    crt.addProjection("resourceName", "resourceName");
 	    crt.addProjection("unitCode", "unitId");
 	    crt.addProjection("agencyCode", "agency");
 	    crt.addProjection("kindDescription", "itemName");
 	    
        crt.addScalar("resourceId", Long.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateCostDataUseActuals(java.lang.Long, java.lang.Boolean)
	 */
	public void updateCostDataUseActuals(Long incidentResourceId, Boolean val) throws PersistenceException {
		String sql = IncidentResourceQuery.getUpdateUseActualsOnly(incidentResourceId, val, super.isOracleDialect());

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getByAssignmentTimeId(java.lang.Long)
	 */
	public IncidentResource getByAssignmentTimeId(Long atId) throws PersistenceException {
		String sql = IncidentResourceQuery.getByAssignmentTimeId(atId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				if(LongUtility.hasValue(id))
					return this.getById(id, IncidentResourceImpl.class);
			}catch(Exception e){
				// smother
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getByAssignmentTimePostId(java.lang.Long)
	 */
	public IncidentResource getByAssignmentTimePostId(Long atpId) throws PersistenceException {
		String sql = IncidentResourceQuery.getByAssignmentTimePostId(atpId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				if(LongUtility.hasValue(id))
					return this.getById(id, IncidentResourceImpl.class);
			}catch(Exception e){
				// smother
			}
		}
		
		return null;
	}

	public Collection<Long> getTopLevelResourceIds(Long incidentId) throws PersistenceException {
		String sql = IncidentResourceQuery.getTopLevelResources(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		Collection<Long> ids= new ArrayList<Long>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				ids.add(id);
			}catch(Exception e){
				//smother
			}
		}
		
		return ids;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getTopLevelResources(java.lang.Long)
	 */
	public Collection<IncidentResource> getTopLevelResources(Long incidentId) throws PersistenceException {
		String sql = IncidentResourceQuery.getTopLevelResources(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		Collection<IncidentResource> entities = new ArrayList<IncidentResource>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				entities.add(this.getById(id, IncidentResourceImpl.class));
			}catch(Exception e){
				//smother
			}
		}
		
		return entities;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getTopLevelResourcesIG(java.lang.Long)
	 */
	public Collection<IncidentResource> getTopLevelResourcesIG(Long incidentGroupId) throws PersistenceException {
		String sql = IncidentResourceQuery.getTopLevelResourcesIG(incidentGroupId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		Collection<IncidentResource> entities = new ArrayList<IncidentResource>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				entities.add(this.getById(id, IncidentResourceImpl.class));
			}catch(Exception e){
				//smother
			}
		}
		
		return entities;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateDefaultCostGroup(java.lang.Long, java.lang.Long)
	 */
	public void updateDefaultCostGroup(Long costDataId, Long cgId) throws PersistenceException{
		String sql = "update isw_cost_data set default_cost_group_id = " + cgId + " where id = " + costDataId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateDefaultShift(java.lang.Long, java.lang.Long)
	 */
	public void updateDefaultShift(Long costDataId, Long shiftId) throws PersistenceException{
		String sql = "update isw_cost_data set default_inc_shift_id = " + shiftId + " where id = " + costDataId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public Collection<IncidentResource> getAllByIncidentId(Long incidentId, Boolean excludeChildren) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);

		crit.add(Restrictions.eq("incidentId", incidentId));

		if(excludeChildren==true){
			String sql="this_.resource_id in (select id from isw_resource where parent_resource_id is null ) ";
			crit.add(Expression.sqlRestriction(sql));
		}
		
		Collection<IncidentResource> entities = crit.list();
		
		return entities;
	}

	public Collection<IncidentResourceGridVo> getReportResourceData(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceView2Impl.class);

		/*
		 * Define how we are going to transform the result to the return object
		 */
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("resourceId"), "resourceId")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("cellPhoneNumber"), "cellPhoneNumber")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("other1"),"other1")
				.add(Projections.property("other2"),"other2")
				.add(Projections.property("other3"),"other3")
				.add(Projections.property("requestCategory"),"requestCategory")
				.add(Projections.property("resourceName"),"resourceName")
				.add(Projections.property("lastName"),"lastName")
				.add(Projections.property("firstName"),"firstName")
				.add(Projections.property("resourceStatus"),"resourceStatus")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("numberOfPersonnel"),"numberOfPersonnel")
				.add(Projections.property("resourceAgencyName"),"resourceAgencyName")
				.add(Projections.property("resourceAgencyCode"),"agency")
				.add(Projections.property("resourceOrganizationName"),"resourceOrganizationName")
				.add(Projections.property("resourceUnitCode"),"unitId")
				.add(Projections.property("mobilizationStartDate"),"mobilizationStartDate")
				.add(Projections.property("incidentId"),"incidentId")
				.add(Projections.property("incidentName"),"incidentName")
				.add(Projections.property("incidentNumber"),"incidentNumber")
				.add(Projections.property("incidentResourceId"),"incidentResourceId")
				.add(Projections.property("nameAtIncident"),"nameAtIncident")
				.add(Projections.property("workPeriodId"),"workPeriodId")
				.add(Projections.property("ciCheckInDate"),"ciCheckInDate")
				.add(Projections.property("ciFirstWorkDate"),"ciFirstWorkDate")
				.add(Projections.property("ciArrivalJetPort"),"ciArrivalJetPort")
				.add(Projections.property("ciLengthAtAssignment"),"ciLengthAtAssignment")
				.add(Projections.property("ciPrePlanningRemarks"),"plansRemarks")
				.add(Projections.property("dmTentativeDemobCity"),"dmTentativeDemobCity")
				.add(Projections.property("dmTentativeDemobState"),"dmTentativeDemobState")
				.add(Projections.property("dmTentativeReleaseDate"),"dmTentativeReleaseDate")
				.add(Projections.property("dmReleaseDate"),"actualReleaseDate")
				.add(Projections.property("dmTravelMethod"),"dmTravelMethod")
				.add(Projections.property("kindCode"),"itemCode")
				.add(Projections.property("kindDescription"),"itemName")
				.add(Projections.property("requestNumber"),"requestNumber")
				.add(Projections.property("requestNumberSortValue"), "requestNumberSortValue")
				.add(Projections.property("trainee"),"trainee")
				.add(Projections.property("assignmentStatus"),"assignmentStatus")
				.add(Projections.property("assignmentId"),"assignmentId")
				.add(Projections.property("leaderName"),"leaderName")
				.add(Projections.property("dmReassignable"),"dmReassignable")
				.add(Projections.property("dmCheckoutFormPrinted"),"dmCheckoutFormPrinted")
				.add(Projections.property("dmPlanningDispatchNotified"),"dmPlanningDispatchNotified")
				.add(Projections.property("dmReleaseDispatchNotified"),"dmReleaseDispatchNotified")
				.add(Projections.property("dmRestOvernight"),"dmRestOvernight")
				.add(Projections.property("dmReleaseRemarks"),"dmReleaseRemarks")
				.add(Projections.property("dmEstimatedArrivalDate"),"dmEstimatedArrivalDate")
				.add(Projections.property("dmAirDispatchNotified"),"dmAirDispatchNotified")
				.add(Projections.property("airRemarks"),"airRemarks")
				.add(Projections.property("hoursToAirport"),"hoursToAirport")
				.add(Projections.property("minutesToAirport"),"minutesToAirport")
				.add(Projections.property("itineraryReceived"),"itineraryReceived")
				.add(Projections.property("nameOnPictureId"),"nameOnPictureId")
				.add(Projections.property("departFromJetport"),"departFromJetport")
				.add(Projections.property("overnightRemarks"),"overnightRemarks")
				.add(Projections.property("departmentCode"),"departmentCode")
				.add(Projections.property("departmentSubCode"),"departmentSubCode")
				.add(Projections.property("departmentDesc"),"departmentDesc")
				.add(Projections.property("departmentSubDesc"),"departmentSubDesc")
				.add(Projections.property("assignmentTimeId"), "assignmentTimeId")
				.add(Projections.property("leaderType"),"leaderTypeNumber")
				.add(Projections.property("accountCode"),"accountingCode")
				.add(Projections.property("employmentType"),"employmentType")
				.add(Projections.property("vinName"),"vinName")
				.add(Projections.property("contractorName"),"contractorName")
				.add(Projections.property("contractorAgreementNumber"),"contractorAgreementNumber")
				.add(Projections.property("assignDate"),"assignDate")
				.add(Projections.property("paymentAgency"),"paymentAgency")
				.add(Projections.property("accrualCode"),"accrualCode")
		);

		crit.setResultTransformer(Transformers.aliasToBean(IncidentResourceGridVo.class));
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		criteria.add(new FilterCriteria("this.irActive", true ,FilterCriteria.TYPE_EQUAL));
		
		if(LongUtility.hasValue(incidentId)){
			crit.add(Restrictions.eq("this.incidentId", incidentId));
		}
		
		if(LongUtility.hasValue(incidentGroupId)){
			String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentGroupId+")";
			crit.add(Restrictions.sqlRestriction(sqlFilter));
		}
		
		crit.addOrder(Order.asc("this.requestNumberSortValue"));

		Collection<IncidentResourceGridVo>  vos = crit.list();

		return vos;
	}
	
	public Date getParentAssignDate(Long parentResourceId,Long incidentId) throws PersistenceException {
		Date dt=null;
		
		String sql = "select cd.assign_date "+
					 "from isw_cost_data cd " +
					 "     , isw_incident_resource ir " +
					 "where cd.id = ir.cost_data_id " +
					 "and ir.resource_id = " + parentResourceId + " " +
					 "and ir.incident_id = " + incidentId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				dt=TypeConverter.convertToDate(rslt);
			}catch(Exception e){}
		}
		return dt;
	}
	
	public void updateCostAssignDate(Long resourceId, Long incidentId, Date assignDate) throws PersistenceException {
		String sql="update isw_cost_data set assign_date = to_date('" + DateUtil.toDateString(assignDate,DateUtil.MM_SLASH_DD_SLASH_YYYY) + "','MM/DD/YYYY') "+
				   "where id = ("+
				   "     select cost_data_id "+
				   "     from isw_incident_resource "+
				   "     where incident_id = " + incidentId + " " +
				   "     and resource_id = " + resourceId + " " +
				   ") ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getByResourceId(java.lang.Long)
	 */
	public IncidentResource getByResourceId(Long resourceId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceImpl.class);
		crit.add(Expression.eq("resourceId", resourceId));
		IncidentResource entity = (IncidentResource)crit.uniqueResult();
		
		return entity;
	}

	public Collection<IncidentResource> getValidResourcesIdsForCost(Long incidentId) throws PersistenceException {
		String sql = IncidentResourceQuery.getValidResourcesIdsForCostQuery(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		
		Collection<IncidentResource> entities = new ArrayList<IncidentResource>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				entities.add(this.getById(id, IncidentResourceImpl.class));
			}catch(Exception e){
				//smother
			}
		}
		
		return entities;
	}

	public Collection<CostResourceDataVo> getCostResourceData(Long incidentResourceId,Long incidentId,Long incidentGroupId) throws PersistenceException {
		Collection<CostResourceDataVo> vos = new ArrayList<CostResourceDataVo>();
		
		String sql = CostResourceDataQuery.getResourceCostDataQuery(incidentResourceId,incidentId,incidentGroupId, super.isOracleDialect());

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

        CustomResultTransformer crt = new CustomResultTransformer(CostResourceDataVo.class);

        crt.addScalar("incidentResourceId", Long.class.getName());
        crt.addScalar("resourceId", Long.class.getName());
        crt.addScalar("assignmentId", Long.class.getName());
        crt.addScalar("assignmentTimeId", Long.class.getName());
        crt.addScalar("contractorPaymentInfoId", Long.class.getName());
        crt.addScalar("agencyId", Long.class.getName());
        crt.addScalar("costGroupId", Long.class.getName());
        crt.addScalar("shiftId", Long.class.getName());
        crt.addScalar("kindId", Long.class.getName());
        crt.addScalar("parentResourceId", Long.class.getName());
        crt.addScalar("defIncidentAccountCodeId", Long.class.getName());
        crt.addScalar("assignDate", Date.class.getName());
        crt.addScalar("releaseDate", Date.class.getName());
        crt.addScalar("arrivalDate", Date.class.getName());
        crt.addScalar("useActualsOnly", Boolean.class.getName());
        //crt.addScalar("generateCosts", Boolean.class.getName());
        //crt.addScalar("generateCostsSys", Boolean.class.getName());
        crt.addScalar("subordinateCount", Integer.class.getName());
        crt.addScalar("defaultHours", Integer.class.getName());
        crt.addScalar("accrualCodeId", Long.class.getName());
        crt.addScalar("costDataId", Long.class.getName());
        crt.addScalar("defaultHours", Integer.class.getName());
        //crt.addScalar("actualRate", BigDecimal.class.getName());
        //crt.addScalar("estimateRate", BigDecimal.class.getName());
        //crt.addScalar("estimateStateCustomRate", BigDecimal.class.getName());
        crt.addScalar("timePostCount", Integer.class.getName());
        crt.addScalar("adjustmentCount", Integer.class.getName());
        crt.addScalar("subordinateTimePostCount",Integer.class.getName());
        crt.addScalar("subordinateTimeAdjustmentCount",Integer.class.getName());
        crt.addScalar("contractorRate", BigDecimal.class.getName());
        crt.addScalar("fedRate", BigDecimal.class.getName());
        crt.addScalar("stateRate", BigDecimal.class.getName());
        crt.addScalar("stateCustomRate", BigDecimal.class.getName());
        crt.addScalar("actualOtherRate", BigDecimal.class.getName());
        crt.addScalar("endDate", Date.class.getName());
        crt.addScalar("incidentStartDate", Date.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
	}
	
	public EarliestDateVo getEarliestDatesByIncResId(Long irId) throws PersistenceException {
		String sql = IncidentResourceQuery.getEarliestDatesByIncResourceId(irId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(EarliestDateVo.class);

        crt.addScalar("incidentResourceId", Long.class.getName());
        crt.addScalar("assignDate", Date.class.getName());
        crt.addScalar("hiredDate", Date.class.getName());
        crt.addScalar("checkInDate", Date.class.getName());
        crt.addScalar("costDataId", Long.class.getName());
        crt.addScalar("earliestTimePostDate", Date.class.getName());
        crt.addScalar("earliestTimeAdjDate", Date.class.getName());
        crt.addScalar("earliestSubAssignDate", Date.class.getName());
        
		query.setResultTransformer(crt);
		
		Collection<EarliestDateVo> vos = query.list();
		if(CollectionUtility.hasValue(vos)){
			return vos.iterator().next();
		}

		return null;
	}
	
	public void updateCostAssignDate2(Long costDataId, Date assignDate) throws PersistenceException {
		String sql="update isw_cost_data set assign_date = to_date('" + DateUtil.toDateString(assignDate,DateUtil.MM_SLASH_DD_SLASH_YYYY) + "','MM/DD/YYYY') "+
				   "where id = " + costDataId + " ";
				   		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	public void updateChildGenCostsTrue(Long parentResourceId) throws PersistenceException {
		String sql="update isw_cost_data set is_generate_costs = "+(super.isOracleDialect()?1:true) +
		   ", is_generate_costs_sys = 'Y' " +
		   "where id in ( "+
		   "    select cost_data_id from isw_incident_resource where resource_id in (" + 
		   "         select id from isw_resource where parent_resource_id = " + parentResourceId + " " +
		   "    ) " +
		   ") " + 
		   "and is_generate_costs_sys = 'N'";
		   		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IncidentAccountCodeVo> getIncidentGroupAccountCodes(Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		crit.createAlias("accountCode", "ac");
		
		if(LongUtility.hasValue(incidentGroupId)){
			String sql = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+") ";
			crit.add(Restrictions.sqlRestriction(sql));
		}
		
		crit.addOrder(Order.asc("ac.accountCode"));
		
		Collection<IncidentAccountCode> entities = crit.list();
		
		try {
			return IncidentAccountCodeVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}	
	}

	public Collection<IncidentResourceGridVo> getIapResources(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();

		String sql = IncidentResourceQuery.getIapResourceQuery(incidentId, incidentGroupId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceGridVo.class);
 	    crt.addProjection("incidentResourceId", "incidentResourceId");
 	    crt.addProjection("resourceId", "resourceId");
 	    crt.addProjection("resourceName", "resourceName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("resourceUnitCode", "resourceUnitCode");
 	    crt.addProjection("resourceAgencyName", "resourceAgencyName");
 	    crt.addProjection("resourceAgencyCode", "resourceAgencyCode");
 	    crt.addProjection("kindDescription", "kindDescription");
 	    crt.addProjection("kindCode", "itemCode");
 	    crt.addProjection("status", "statusCode");
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("lengthAtAssignment", "ciLengthAtAssignment");
 	    crt.addProjection("checkInDate", "ciCheckInDate");
 	    crt.addProjection("firstWorkDate", "ciCheckInDate");
 	    crt.addProjection("training", "trainee");
 	    crt.addProjection("requestNumberSortValue", "requestNumberSortValue");
 	    
 	    crt.addScalar("resourceId", Long.class.getName());
        crt.addScalar("incidentResourceId", Long.class.getName());
        crt.addScalar("checkInDate", Date.class.getName());
        crt.addScalar("firstWorkDate", Date.class.getName());
        crt.addScalar("lengthAtAssignment", Long.class.getName());
        crt.addScalar("training", Boolean.class.getName());
        
		query.setResultTransformer(crt);
		
		vos=query.list();
		
		return vos;
	}

	public void persistSqls(Collection<String> sqls) throws PersistenceException {
		for(String sql : sqls){	
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}

	public Collection<ResourceDataForAccrualVo> getResourceDataForAccrual(Long incidentId) throws PersistenceException {
		Collection<ResourceDataForAccrualVo> vos = new ArrayList<ResourceDataForAccrualVo>();

		String sql = IncidentResourceQuery.getResourceDataForAccrualQuery(incidentId, super.isOracleDialect());
		SQLQuery query = super.getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(ResourceDataForAccrualVo.class);
 	    crt.addProjection("incidentResourceId", "incidentResourceId");
 	    crt.addProjection("costDataId", "costDataId");
 	    crt.addProjection("resourceId", "resourceId");
 	    crt.addProjection("accrualLocked", "accrualLocked");
 	    crt.addProjection("accrualCode", "accrualCode");
 	    crt.addProjection("incidentAgency", "incidentAgency");
 	    crt.addProjection("incidentState", "incidentState");
 	    crt.addProjection("resourcePaymentAgency", "resourcePaymentAgency");
 	    crt.addProjection("resourceAgencyCode", "resourceAgencyCode");
 	    crt.addProjection("resourceAgencyState", "resourceAgencyState");
 	    crt.addProjection("resourceEmploymentType", "resourceEmploymentType");
 	    crt.addProjection("resourceItemCode", "resourceItemCode");
 	    crt.addProjection("resourceItemCodeCategory", "resourceItemCodeCategory");
 	    crt.addProjection("resourceUnitCode", "resourceUnitCode");
 	    crt.addProjection("incidentUnitCode", "incidentUnitCode");
 	    crt.addProjection("resourceCostCount", "resourceCostCount");
		
        crt.addScalar("incidentResourceId", Long.class.getName());
        crt.addScalar("resourceId", Long.class.getName());
        crt.addScalar("costDataId", Long.class.getName());
        crt.addScalar("resourceCostCount", Integer.class.getName());

		query.setResultTransformer(crt); 

		try{
			vos=query.list();
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw new PersistenceException(e);
		}
		return vos;
	}
	
	public int getUnlockedCostRecordCount(Long irId) throws PersistenceException {
		String sql = "SELECT COUNT(ID) FROM ISW_INC_RES_DAILY_COST " +
					 "WHERE INCIDENT_RESOURCE_ID = " + irId + " " +
					 "AND (IS_LOCKED IS NULL OR IS_LOCKED = "+(super.isOracleDialect() ? 0 : false)+") ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object val = query.uniqueResult();
		
		int count=0;
		
		try{
			if(null != val)
				count=TypeConverter.convertToInt(val);
		}catch(Exception e){
			// smother
		}
		
		return count;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#updateDailyCostExceptions(java.lang.Long, java.lang.Long)
	 */
	public void updateDailyCostExceptions(Long incidentId, Long incidentGroupId) throws PersistenceException {
		String sql1 = IncidentResourceQuery.getResourceCostExceptionQuery(incidentId, incidentGroupId);
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);

		CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceVo.class);
 	    crt.addProjection("id", "id");
 	    crt.addProjection("dailyCostException", "dailyCostException");
        crt.addScalar("id", Long.class.getName());
		
        query1.setResultTransformer(crt);
        
        Collection<IncidentResourceVo> vos = query1.list();
        if(CollectionUtility.hasValue(vos)){
        	Collection<String> sqls = new ArrayList<String>();
        	for(IncidentResourceVo vo : vos){
        		String sql = "UPDATE isw_incident_resource " +
        			 		 "set daily_cost_exception = '"+vo.getDailyCostException() +"' "+
        			 		 "where id = " + vo.getId() + " ";
        		sqls.add(sql);
        	}
        	this.persistSqls(sqls);
        }
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#getResourceTimeCostRecordCount(java.lang.Long)
	 */
	public Object[] getResourceTimeCostRecordCount(Collection<Long> incidentResourceIds) throws PersistenceException {
		try{
			String sql = IncidentResourceQuery.getResourceTimeCostRecordsCountQuery();
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameterList("ids", incidentResourceIds);
			
			return (Object[]) query.uniqueResult();
			
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDao#setResourceDefIAC(java.lang.Long, java.lang.Long)
	 */
	public void setResourceDefIAC(Long iacId, Long incidentId) throws PersistenceException {
		String sql="SELECT count(id) " +
				   "FROM isw_incident_resource " +
				   "WHERE incident_id = " + incidentId + " " +
				   "AND id in (" +
				   "  select incident_resource_id "+
				   "  from isw_work_period " +
				   "  where def_incident_account_code_id is null " +
				   ") ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		try{
			int cnt=TypeConverter.convertToInt(rslt);
			
			if(cnt > 0){
				sql="update isw_work_period " +
					"set def_incident_account_code_id = " + iacId + " " +
					"where incident_resource_id in (" +
					"  select id "+
					"  from isw_incident_resource " +
					"  where incident_id = " + incidentId + " " +
					") " +
					"and def_incident_account_code_id is null ";
				
				query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}catch(Exception e){
			
		}
	}
	
	public Long getIdByResourceId(Long id ) throws PersistenceException {
		String sql="select id from isw_incident_resource where resource_id = " + id + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		
		if(null != rslt){
			try{
				Long irid = TypeConverter.convertToLong(rslt);
				return irid;
			}catch(Exception e){}
		}
		
		return null;
	}
	
	public Long getParentResourceId(Long irid ) throws PersistenceException {
		String sql="select parent_resource_id "+
				   "from isw_resource "+
				   "where id = (select resource_id from isw_incident_resource where id = " + irid + ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		
		if(null != rslt){
			try{
				Long parentResId = TypeConverter.convertToLong(rslt);
				return parentResId;
			}catch(Exception e){}
		}
		
		return null;
	}

	public Long updateResNumId(Long irId) throws PersistenceException {
		Long resNumId=0L;
		
		String sql="select res_num_id "+
				   "from isw_incident_resource "+
				   "where id = " + irId;
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt = q.uniqueResult();
		if(null==rslt) rslt=0L;
		
		if(null != rslt){
			try{
				resNumId=TypeConverter.convertToLong(rslt);
				if(!LongUtility.hasValue(resNumId)){
					sql="select UPDATE_RES_NUM(ir.id) "+
						"from isw_incident_resource ir " +
						"where ir.id = " + irId + " ";
					q = getHibernateSession().createSQLQuery(sql);
					rslt = q.uniqueResult();
					if(null != rslt){
						Long maxResNumId=TypeConverter.convertToLong(rslt);
						resNumId=maxResNumId;
					}		
				}else{
					return resNumId;
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		return resNumId;
	}
	
	public Long updateResNumId2(Long irId) throws PersistenceException {
		Long resNumId=0L;
		
		if(LongUtility.hasValue(irId)){
			String sql="select res_num_id from isw_incident_resource where id = " + irId;
			SQLQuery q = getHibernateSession().createSQLQuery(sql);
			Object rslt = q.uniqueResult();
			if(null==rslt) rslt=0L;
			
			if(null != rslt){
				try{
					resNumId=TypeConverter.convertToLong(rslt);
					if(!LongUtility.hasValue(resNumId)){
						sql="select max(res_num_id) from isw_incident_resource " +
							"where incident_id = (select incident_id from isw_incident_resource " +
							"   where id = " + irId + ")";
						q = getHibernateSession().createSQLQuery(sql);
						rslt = q.uniqueResult();
						if(rslt==null) rslt=0;
						if(null != rslt){
							Long maxResNumId=TypeConverter.convertToLong(rslt);
							resNumId=maxResNumId+1;
							sql="update isw_incident_resource set res_num_id="+maxResNumId+" where id="+irId+" ";
							q = getHibernateSession().createSQLQuery(sql);
							q.executeUpdate();
						}		
					}else{
						return resNumId;
					}
					
				}catch(Exception e){}
			}
		}
		return resNumId;
	}
	
	public void resetResNumId(Long irId) throws PersistenceException {
		
	}

	public void cleanUpDuplicateQuestionIssue(Long incidentResourceId) throws PersistenceException {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("")
			   .append("select count(qv.id) ")
			   .append("from isw_work_period_question_value  qv ")
			   .append("where qv.work_period_id in ( ")
				.append("	select id ")
				.append("	from isw_work_period ")
				.append("	where incident_resource_id = " + incidentResourceId + " ")
				.append(") ")
				.append("and qv.id not in (")
				.append("  select min(qv2.id) " )
				.append("  from isw_work_period_question_value qv2 ")
				.append("  where qv2.work_period_id = qv.work_period_id ")
				.append("  group by qv2.work_period_id, qv2.incident_question_id ")
				.append(") ");
			
			SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
			Object rslt = q.uniqueResult();
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = new StringBuffer();
					sql.append("")
					   .append("delete  ")
					   .append("from isw_work_period_question_value  qv ")
					   .append("where qv.work_period_id in ( ")
						.append("	select id ")
						.append("	from isw_work_period ")
						.append("	where incident_resource_id = " + incidentResourceId + " ")
						.append(") ")
						.append("and qv.id not in (")
						.append("  select min(qv2.id) " )
						.append("  from isw_work_period_question_value qv2 ")
						.append("  where qv2.work_period_id = qv.work_period_id ")
						.append("  group by qv2.work_period_id, qv2.incident_question_id ")
						.append(") ");
					q = getHibernateSession().createSQLQuery(sql.toString());
					q.executeUpdate();
				}
			}
		}catch(Exception e){
			throw new PersistenceException("cannot cleanup resource questions");
		}
	}	

	public Collection<IncidentResourceTimeDataVo> getIncidentResourceTimeDataVos(Long parentIncidentResourceId, Boolean subsOnly, Date postDate) throws PersistenceException {
		
		try{
			String sql1 = IncidentResourceQuery.getIncResTimeDataQuery(parentIncidentResourceId,subsOnly,postDate,super.isOracleDialect());
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);

			CustomResultTransformer crt = new CustomResultTransformer(IncidentResourceTimeDataVo.class);
	 	    crt.addProjection("incidentResourceId", "incidentResourceId");
	 	    crt.addProjection("resourceId", "resourceId");
	 	    crt.addProjection("assignmentId", "assignmentId");
	 	    crt.addProjection("assignmentTimeId", "assignmentTimeId");
	 	    crt.addProjection("resNumId", "resNumId");
	 	    crt.addProjection("adRate", "adRate");
	 	    crt.addProjection("otherDefaultRate", "otherDefaultRate");
	 	    crt.addProjection("incidentNumber", "incidentNumber");
	 	    crt.addProjection("incidentYear", "incidentYear");
	 	    crt.addProjection("incidentUnitCode", "incidentUnitCode");
	 	    crt.addProjection("resourceName", "resourceName");
	 	    crt.addProjection("lastName", "lastName");
	 	    crt.addProjection("firstName", "firstName");
	 	    crt.addProjection("resourceUnitCode", "resourceUnitCode");
	 	    crt.addProjection("requestNumber", "requestNumber");
	 	    crt.addProjection("employmentType", "employmentType");
	 	    crt.addProjection("kindCode","kindCode");
	 	    crt.addProjection("hiringUnitName","hiringUnitName");
	 	    crt.addProjection("hiringPhone","hiringPhone");
	 	    crt.addProjection("hiringFax","hiringFax");
	 	    crt.addProjection("pointOfHire","pointOfHire");
	 	    crt.addProjection("eci","eci");
	 	    crt.addProjection("person","person");
	 	    crt.addProjection("defaultAccountCode","defaultAccountCode");
	 	    crt.addProjection("addressLine1","addressLine1");
	 	    crt.addProjection("addressLine2","addressLine2");
	 	    crt.addProjection("city","city");
	 	    crt.addProjection("state","state");
	 	    crt.addProjection("postalCode","postalCode");
	 	    crt.addProjection("phone","phone");
	 	    crt.addProjection("fax","fax");
	 	    crt.addProjection("incidentName","incidentName");
	 	    
	        crt.addScalar("incidentResourceId", Long.class.getName());
	        crt.addScalar("resourceId", Long.class.getName());
	        crt.addScalar("assignmentId", Long.class.getName());
	        crt.addScalar("assignmentTimeId", Long.class.getName());
	        crt.addScalar("resNumId", Long.class.getName());
	        crt.addScalar("adRate", BigDecimal.class.getName());
	        crt.addScalar("otherDefaultRate", BigDecimal.class.getName());
	        crt.addScalar("incidentYear", Integer.class.getName());
	        crt.addScalar("person", Boolean.class.getName());
			
	        query1.setResultTransformer(crt);
	        
	        Collection<IncidentResourceTimeDataVo> vos = query1.list();

			return vos;
		}catch(Exception e){
			throw new PersistenceException(e.getMessage());
		}
	}
	
}
