package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.filter.CostReportFilter;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.DailyCostComparisonICReport;
import gov.nwcg.isuite.core.reports.DailyCostComparisonRESRReport;
import gov.nwcg.isuite.core.reports.ResourceItemCodeByCostOHPersonnelReport;
import gov.nwcg.isuite.core.reports.ResourceItemCodeByCostReport;
import gov.nwcg.isuite.core.reports.ResourcesWithActualTimePostingButThreeOrMoreDaysReport;
import gov.nwcg.isuite.core.reports.ResourcesWithDailyCostExceeds10000Report;
import gov.nwcg.isuite.core.reports.ResourcesWithNoActualTimePostedReport;
import gov.nwcg.isuite.core.reports.ResourcesWithNoAgencyAssignedReport;
import gov.nwcg.isuite.core.reports.ResourcesWithNoDailyCostRecordsReport;
import gov.nwcg.isuite.core.reports.SummaryByResourceReport;
import gov.nwcg.isuite.core.reports.DetailByResourceReport;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonICReportData;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonICSubReportData;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonRESRReportData;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonRESRSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostOHPersonnelReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostOHPersonnelSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithActualTimePostingButThreeOrMoreDaysReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithDailyCostExceeds10000ReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithDailyCostExceeds10000SubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoActualTimePostedReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoActualTimePostedSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoAgencyAssignedReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoAgencyAssignedSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoDailyCostRecordsReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoDailyCostRecordsSubReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceSubReportData;
import gov.nwcg.isuite.core.reports.data.DetailByResourceReportData;
import gov.nwcg.isuite.core.reports.data.DetailByResourceSubReportData;
import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;
import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.generator.AircraftDetailReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.CostShareReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.GroupCategorySummaryReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.GroupCategoryTotalReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.SummaryByResourceReportGeneratorImpl;
import gov.nwcg.isuite.core.reports.generator.SummaryForCurrentDayReportGeneratorImpl;
import gov.nwcg.isuite.core.service.ReportCostService;
import gov.nwcg.isuite.core.vo.CheckboxListVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReportCostServiceImpl extends BaseReportService implements ReportCostService {
	private ReportCostDao dao;
	
	public ReportCostServiceImpl() {
		super();
	}
			
	public void initialization() {
		dao = (ReportCostDao)super.context.getBean("reportCostDao");
	}
		
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateDetailByResourceReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateDetailByResourceReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		String pdfURL = null;
		
		try {
			Collection<DetailByResourceReportData> detailByResourceReportDataList = new ArrayList<DetailByResourceReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
			filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
						
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				filter.incidentId = incident.getId();
				Collection<DetailByResourceSubReportData> subrd = dao.getDetailByResourceSubReportData(filter,incident.getId());
				
				DetailByResourceReportData detailByResourceReportData = new DetailByResourceReportData();
				
				if (null != subrd && subrd.size() > 0)
					detailByResourceReportData.setSubReportData(subrd);
				else
					continue;
				
				detailByResourceReportData.setIncidentId(incident.getId());
				detailByResourceReportData.setIncidentName(incident.getIncidentName());
				detailByResourceReportData.setIncidentNumber(incident.getIncidentNumber());
				detailByResourceReportDataList.add(detailByResourceReportData);
			}
						
			if(detailByResourceReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Detail By Resource Report","text.detailByResourceInstructions"));
				return dialogueVo;
			}
			
			IReport report = new DetailByResourceReport(detailByResourceReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("groupingName", filter.getSelectedReportGroup());
			if (filter.isOverhead())
			  report.addCustomField("reportTitle", "OH");
			else
			  report.addCustomField("reportTitle", "Non OH");
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date today = Calendar.getInstance().getTime(); 
            			
			if (filter.isDateRangeSelected())
			   report.addCustomField("reportDateRange", "(" + df.format(filter.getStartDate()) + " - " + df.format(filter.getEndDate()) + ")");
			else
			   report.addCustomField("reportDateRange", "");
			
			if(filter.isIncidentGroup()) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				report.addCustomField("reportSubTitle2", "");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateAnalysisReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		
		filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
		filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
		
		if (filter.getAnalysisReport().equals("resourceCost")) {
            if (filter.getItemCodeOrResource().equals("itemCode")) 
          	    return generateDailyCostComparisonICAnalysisReport(filter, dialogueVo);
            else 
           	    return generateDailyCostComparisonRESRAnalysisReport(filter, dialogueVo);
		}
		else if (filter.getAnalysisReport().equals("exception")) {
			if (filter.getAnalysisReportFilter().equals("noActualTimePosted")) 
          	    return generateResourcesWithNoActualTimePostedAnalysisReport(filter, dialogueVo);
			else if (filter.getAnalysisReportFilter().equals("exceeds10000"))
				return generateResourcesWithDailyCostExceeds10000AnalysisReport(filter, dialogueVo);
			else if (filter.getAnalysisReportFilter().equals("threeOrMoreDays"))
				return generateResourcesWithActualTimePostingButThreeOrMoreDaysAnalysisReport(filter, dialogueVo);
			else if (filter.getAnalysisReportFilter().equals("noAgencyAssigned"))
				return generateResourcesWithNoAgencyAssignedAnalysisReport(filter, dialogueVo);
			else
				return generateResourcesWithNoDailyCostRecordsAnalysisReport(filter, dialogueVo);
		}
		else if (filter.getAnalysisReport().equals("byCost")) {
			//need dates
			return generateResourceItemCodeByCostAnalysisReport(filter, dialogueVo);
		}
		else {
			//need dates
			return generateResourceItemCodeByCostOHPersonnelAnalysisReport(filter, dialogueVo);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateDailyCostComparisonICAnalysisReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
    public DialogueVo generateDailyCostComparisonICAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException{
    	if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		String pdfURL = null;
		
		try {
			Collection<DailyCostComparisonICReportData> dailyCostComparisonICReportDataList = new ArrayList<DailyCostComparisonICReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<DailyCostComparisonICSubReportData> subrd = dao.getDailyCostComparisonICSubReportData(filter,incident.getId());
				
				DailyCostComparisonICReportData dailyCostComparisonICReportData = new DailyCostComparisonICReportData();
				
				if (null != subrd && subrd.size() > 0)
					dailyCostComparisonICReportData.setSubReportData(subrd);
				else
					continue;
				
				dailyCostComparisonICReportData.setIncidentId(incident.getId());
				dailyCostComparisonICReportData.setIncidentName(incident.getIncidentName());
				dailyCostComparisonICReportData.setIncidentNumber(incident.getIncidentNumber());
				dailyCostComparisonICReportDataList.add(dailyCostComparisonICReportData);
			}
						
			if(dailyCostComparisonICReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Daily Cost Comparison by Item Code Summary","text.dailyCostComparisonbyItemCodeSummaryInstructions"));
				return dialogueVo;
			}
						
			// Instantiate the report controller object.
			IReport report = new DailyCostComparisonICReport(dailyCostComparisonICReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Daily Cost Comparison by Item Code Summary");
			report.addCustomField("reportSubTitle", "(Aircraft and Overhead not included)");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);
			

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
    /* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateDailyCostComparisonRESRAnalysisReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateDailyCostComparisonRESRAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException{
    	if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<DailyCostComparisonRESRReportData> dailyCostComparisonRESRReportDataList = new ArrayList<DailyCostComparisonRESRReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<DailyCostComparisonRESRSubReportData> subrd = dao.getDailyCostComparisonRESRSubReportData(filter,incident.getId());
				
				DailyCostComparisonRESRReportData dailyCostComparisonRESRReportData = new DailyCostComparisonRESRReportData();
				
				if (null != subrd && subrd.size() > 0)
					dailyCostComparisonRESRReportData.setSubReportData(subrd);
				else
					continue;
				
				dailyCostComparisonRESRReportData.setIncidentId(incident.getId());
				dailyCostComparisonRESRReportData.setIncidentName(incident.getIncidentName());
				dailyCostComparisonRESRReportData.setIncidentNumber(incident.getIncidentNumber());
				dailyCostComparisonRESRReportDataList.add(dailyCostComparisonRESRReportData);
			}
						
			if(dailyCostComparisonRESRReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Daily Cost Comparison by Resource Exceeding Standard Rate","text.dailyCostComparisonbyResourceExceedingStandardRateInstructions"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new DailyCostComparisonRESRReport(dailyCostComparisonRESRReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Daily Cost by Comparison Resource Exceeding Std Rate");
			report.addCustomField("reportSubTitle", "(Aircraft and Overhead not included)");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
		
	 /* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateResourcesWithDailyCostExceeds10000AnalysisReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateResourcesWithDailyCostExceeds10000AnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourcesWithDailyCostExceeds10000ReportData> resourcesWithDailyCostExceeds10000ReportDataList = new ArrayList<ResourcesWithDailyCostExceeds10000ReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourcesWithDailyCostExceeds10000SubReportData> subrd = dao.getResourcesWithDailyCostExceeds10000SubReportData(filter,incident.getId());
				
				ResourcesWithDailyCostExceeds10000ReportData resourcesWithDailyCostExceeds10000ReportData = new ResourcesWithDailyCostExceeds10000ReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourcesWithDailyCostExceeds10000ReportData.setSubReportData(subrd);
				else
					continue;
				
				resourcesWithDailyCostExceeds10000ReportData.setIncidentId(incident.getId());
				resourcesWithDailyCostExceeds10000ReportData.setIncidentName(incident.getIncidentName());
				resourcesWithDailyCostExceeds10000ReportData.setIncidentNumber(incident.getIncidentNumber());
				resourcesWithDailyCostExceeds10000ReportDataList.add(resourcesWithDailyCostExceeds10000ReportData);
			}
						
			if(resourcesWithDailyCostExceeds10000ReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resources With Daily Cost Exceeds 10000","text.resourcesWithDailyCostExceeds10000Instructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourcesWithDailyCostExceeds10000Report(resourcesWithDailyCostExceeds10000ReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Exception");
			report.addCustomField("reportSubTitle", "Resource Daily Cost Exceeds $" + filter.getExceeds10000());
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateResourcesWithActualTimePostingButThreeOrMoreDaysAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysReportData> resourcesWithActualTimePostingButThreeOrMoreDaysReportDataList = new ArrayList<ResourcesWithActualTimePostingButThreeOrMoreDaysReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData> subrd = dao.getResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData(filter,incident.getId());
				
				ResourcesWithActualTimePostingButThreeOrMoreDaysReportData resourcesWithActualTimePostingButThreeOrMoreDaysReportData = new ResourcesWithActualTimePostingButThreeOrMoreDaysReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourcesWithActualTimePostingButThreeOrMoreDaysReportData.setSubReportData(subrd);
				else
					continue;
				
				resourcesWithActualTimePostingButThreeOrMoreDaysReportData.setIncidentId(incident.getId());
				resourcesWithActualTimePostingButThreeOrMoreDaysReportData.setIncidentName(incident.getIncidentName());
				resourcesWithActualTimePostingButThreeOrMoreDaysReportData.setIncidentNumber(incident.getIncidentNumber());
				resourcesWithActualTimePostingButThreeOrMoreDaysReportDataList.add(resourcesWithActualTimePostingButThreeOrMoreDaysReportData);
			}
						
			if(resourcesWithActualTimePostingButThreeOrMoreDaysReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resources With Actual Time Posting But 3 or More Days","text.resourcesWithActualTimePostingBut3OrMoreDays"));
				return dialogueVo;
			}
			
			IReport report = new ResourcesWithActualTimePostingButThreeOrMoreDaysReport(resourcesWithActualTimePostingButThreeOrMoreDaysReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Exception");
			report.addCustomField("reportSubTitle", "Resources With Actual Time Posting But " + filter.getThreeOrMoreDays() + " or More Days of Unposted Time");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateResourcesWithNoActualTimePostedAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourcesWithNoActualTimePostedReportData> resourcesWithNoActualTimePostedReportDataList = new ArrayList<ResourcesWithNoActualTimePostedReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourcesWithNoActualTimePostedSubReportData> subrd = dao.getResourcesWithNoActualTimePostedSubReportData(filter,incident.getId());
				
				ResourcesWithNoActualTimePostedReportData resourcesWithNoActualTimePostedReportData = new ResourcesWithNoActualTimePostedReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourcesWithNoActualTimePostedReportData.setSubReportData(subrd);
				else
					continue;
				
				resourcesWithNoActualTimePostedReportData.setIncidentId(incident.getId());
				resourcesWithNoActualTimePostedReportData.setIncidentName(incident.getIncidentName());
				resourcesWithNoActualTimePostedReportData.setIncidentNumber(incident.getIncidentNumber());
				resourcesWithNoActualTimePostedReportDataList.add(resourcesWithNoActualTimePostedReportData);
			}
						
			if(resourcesWithNoActualTimePostedReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resources With No Actual Time Posted","text.resourcesWithNoActualTimePostedInstructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourcesWithNoActualTimePostedReport(resourcesWithNoActualTimePostedReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Exception");
			report.addCustomField("reportSubTitle", "Resources With No Actual Time Posted");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	
	
	public DialogueVo generateResourcesWithNoAgencyAssignedAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourcesWithNoAgencyAssignedReportData> resourcesWithNoAgencyAssignedReportDataList = new ArrayList<ResourcesWithNoAgencyAssignedReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourcesWithNoAgencyAssignedSubReportData> subrd = dao.getResourcesWithNoAgencyAssignedSubReportData(filter,incident.getId());
				
				ResourcesWithNoAgencyAssignedReportData resourcesWithNoAgencyAssignedReportData = new ResourcesWithNoAgencyAssignedReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourcesWithNoAgencyAssignedReportData.setSubReportData(subrd);
				else
					continue;
				
				resourcesWithNoAgencyAssignedReportData.setIncidentId(incident.getId());
				resourcesWithNoAgencyAssignedReportData.setIncidentName(incident.getIncidentName());
				resourcesWithNoAgencyAssignedReportData.setIncidentNumber(incident.getIncidentNumber());
				resourcesWithNoAgencyAssignedReportDataList.add(resourcesWithNoAgencyAssignedReportData);
			}
						
			if(resourcesWithNoAgencyAssignedReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resources With No Agency Assigned","text.resourcesWithNoAgencyAssignedInstructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourcesWithNoAgencyAssignedReport(resourcesWithNoAgencyAssignedReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Exception");
			
			/*			
			if (filter.getNoDailyCostSelections() != null) {
				String selections[] = (String[]) filter.getNoDailyCostSelections().toArray(new String[0]);
			    report.addCustomField("reportSubTitle", "Resources With No Daily Cost Records (" + selections[0] + "/" + selections[1] + "/" + selections[2] + "/" + selections[3] + ")");
			}
			else
				report.addCustomField("reportSubTitle", "Resources With No Daily Cost Records (null)");
				
            */		
//			StringBuffer headerString = new StringBuffer();
						
//			if (!filter.isAllExceptionsIncluded() && !filter.isMissingAgencyIncluded() && !filter.isMissingDatesIncluded() && !filter.isStatusIsFIncluded()) 
//				headerString.append("");
//			else {
//				if (filter.isAllExceptionsIncluded())
//					headerString.append("(-All Exceptions-)");
//				else {
//					headerString.append("(");
//					
//					if (filter.isMissingAgencyIncluded())
//						headerString.append("-Missing Agency-");
//					
//					if (filter.isMissingDatesIncluded())
//						headerString.append("-Missing Dates-");
//					
//					if (filter.isStatusIsFIncluded())
//						headerString.append("-Status Is F-");
//					
//					headerString.append(")");
//				}	
//			}
								
//			report.addCustomField("reportSubTitle", "Resources With No Daily Cost " + headerString.toString());
			report.addCustomField("reportSubTitle", "Resources With No Agency Assigned");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}		
	
	public DialogueVo generateResourcesWithNoDailyCostRecordsAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourcesWithNoDailyCostRecordsReportData> resourcesWithNoDailyCostRecordsReportDataList = new ArrayList<ResourcesWithNoDailyCostRecordsReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourcesWithNoDailyCostRecordsSubReportData> subrd = dao.getResourcesWithNoDailyCostRecordsSubReportData(filter,incident.getId());
				
				ResourcesWithNoDailyCostRecordsReportData resourcesWithNoDailyCostRecordsReportData = new ResourcesWithNoDailyCostRecordsReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourcesWithNoDailyCostRecordsReportData.setSubReportData(subrd);
				else
					continue;
				
				resourcesWithNoDailyCostRecordsReportData.setIncidentId(incident.getId());
				resourcesWithNoDailyCostRecordsReportData.setIncidentName(incident.getIncidentName());
				resourcesWithNoDailyCostRecordsReportData.setIncidentNumber(incident.getIncidentNumber());
				resourcesWithNoDailyCostRecordsReportDataList.add(resourcesWithNoDailyCostRecordsReportData);
			}
						
			if(resourcesWithNoDailyCostRecordsReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resources With Missing Assign Dates or Status is F","text.resourcesWithNoDailyCostRecordsInstructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourcesWithNoDailyCostRecordsReport(resourcesWithNoDailyCostRecordsReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis - Exception");
			
			/*			
			if (filter.getNoDailyCostSelections() != null) {
				String selections[] = (String[]) filter.getNoDailyCostSelections().toArray(new String[0]);
			    report.addCustomField("reportSubTitle", "Resources With No Daily Cost Records (" + selections[0] + "/" + selections[1] + "/" + selections[2] + "/" + selections[3] + ")");
			}
			else
				report.addCustomField("reportSubTitle", "Resources With No Daily Cost Records (null)");
				
            */		
			StringBuffer headerString = new StringBuffer();
						
//			if (!filter.isAllExceptionsIncluded() && !filter.isMissingDatesIncluded() && !filter.isStatusIsFIncluded()) 
//				headerString.append("");
//			else {
//				if (filter.isAllExceptionsIncluded())
//					headerString.append("(-All Exceptions-)");
//				else {
//					headerString.append("(");
//					
//					//if (filter.isMissingAgencyIncluded())
//					//	headerString.append("-Missing Agency-");
//					
//					if (filter.isMissingDatesIncluded())
//						headerString.append("-Missing Assign Dates-");
//					
//					if (filter.isStatusIsFIncluded())
//						headerString.append("-Status Is F-");
//					
//					headerString.append(")");
//				}	
//			}
								
			report.addCustomField("reportSubTitle", "Resources With Missing Assign Dates or Status is F " + headerString.toString());
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	
	
   public DialogueVo generateResourceItemCodeByCostAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourceItemCodeByCostReportData> resourceItemCodeByCostReportDataList = new ArrayList<ResourceItemCodeByCostReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourceItemCodeByCostSubReportData> subrd = dao.getResourceItemCodeByCostSubReportData(filter,incident.getId());
				
				ResourceItemCodeByCostReportData resourceItemCodeByCostReportData = new ResourceItemCodeByCostReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourceItemCodeByCostReportData.setSubReportData(subrd);
				else
					continue;
				
				resourceItemCodeByCostReportData.setIncidentId(incident.getId());
				resourceItemCodeByCostReportData.setIncidentName(incident.getIncidentName());
				resourceItemCodeByCostReportData.setIncidentNumber(incident.getIncidentNumber());
				resourceItemCodeByCostReportDataList.add(resourceItemCodeByCostReportData);
			}
						
			if(resourceItemCodeByCostReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resource Item Code By Cost","text.resourceItemCodeByCostInstructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourceItemCodeByCostReport(resourceItemCodeByCostReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis Report");
			report.addCustomField("reportSubTitle", "Resource Item Code By Cost");
			
			String dateRange = "";
			
			if (filter.isDateRangeIncluded())
			   dateRange = " (For Date: " + filter.getStartDateChar() + " - " + filter.getEndDateChar() + ")";
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName() + dateRange);
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")" + dateRange);
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
}
	
	public DialogueVo generateResourceItemCodeByCostOHPersonnelAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		String pdfURL = null;
		
		try {
			Collection<ResourceItemCodeByCostOHPersonnelReportData> resourceItemCodeByCostOHPersonnelReportDataList = new ArrayList<ResourceItemCodeByCostOHPersonnelReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			
			if(filter.getIncidentGroupId() > 0)
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
								
			for(Incident incident : incidents) {
				Collection<ResourceItemCodeByCostOHPersonnelSubReportData> subrd = dao.getResourceItemCodeByCostOHPersonnelSubReportData(filter,incident.getId());
				
				ResourceItemCodeByCostOHPersonnelReportData resourceItemCodeByCostOHPersonnelReportData = new ResourceItemCodeByCostOHPersonnelReportData();
				
				if (null != subrd && subrd.size() > 0)
					resourceItemCodeByCostOHPersonnelReportData.setSubReportData(subrd);
				else
					continue;
				
				resourceItemCodeByCostOHPersonnelReportData.setIncidentId(incident.getId());
				resourceItemCodeByCostOHPersonnelReportData.setIncidentName(incident.getIncidentName());
				resourceItemCodeByCostOHPersonnelReportData.setIncidentNumber(incident.getIncidentNumber());
				resourceItemCodeByCostOHPersonnelReportDataList.add(resourceItemCodeByCostOHPersonnelReportData);
			}
						
			if(resourceItemCodeByCostOHPersonnelReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Resource Item Code By Cost O/H Personnel","text.resourceItemCodeByCostOHPersonnelInstructions"));
				return dialogueVo;
			}
			
			IReport report = new ResourceItemCodeByCostOHPersonnelReport(resourceItemCodeByCostOHPersonnelReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportTitle", "Analysis Report");
			report.addCustomField("reportSubTitle", "Resource Item Code By Cost O/H Personnel");
			
            String dateRange = "";
			
			if (filter.isDateRangeIncluded())
			   dateRange = " (For Date: " + filter.getStartDateChar() + " - " + filter.getEndDateChar() + ")";
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle2", "Incident Group: " + ig.getGroupName() + dateRange);
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				//report.addCustomField("reportSubTitle2", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")" + dateRange);
			}
			
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);

		}catch(Exception e){
			//super.handleException(e);
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	

	 /* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#getGroupCategoryTotalFilters(String filterTypes, Long id, boolean isIncidentGroup, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGroupCategoryTotalFilter(Long id, String filterName, boolean isIncidentGroup, DialogueVo dialogueVo) throws Exception { 
		
		if (dialogueVo == null) 
			dialogueVo = new DialogueVo();


		List<CheckboxListVo> data = new ArrayList<CheckboxListVo>();

		try {
			data = (List<CheckboxListVo>)dao.getCostReportFilter(id,filterName,isIncidentGroup);
		
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GROUP_CATEGORY_TOTAL_FILTER");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setResultObject(data);
			dialogueVo.setResultObjectAlternate2(filterName);
			dialogueVo.setCourseOfActionVo(coa);
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateGroupCategoryTotalReport(gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateGroupCategoryTotalReport(GroupCategoryTotalReportFilter filter,DialogueVo dialogueVo) throws Exception  {
		
	   GroupCategoryTotalReportGeneratorImpl rg = new GroupCategoryTotalReportGeneratorImpl();
	   rg.setUserSessionVo(getUserSessionVo());
	   rg.setApplicationContext(super.context); 
	   rg.setServletContext(super.servletContext);
	   return rg.generateReport(filter, dialogueVo);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateGroupCategorySummaryReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateGroupCategorySummaryReport(GroupCategoryTotalReportFilter filter,DialogueVo dialogueVo) throws Exception {
		GroupCategorySummaryReportGeneratorImpl rg = new GroupCategorySummaryReportGeneratorImpl();
		rg.setApplicationContext(super.context);
		rg.setServletContext(super.servletContext);
		rg.setUserSessionVo(getUserSessionVo());
		return rg.generateReport(filter, dialogueVo);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateSummaryByResourceReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateSummaryByResourceReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws Exception {
		SummaryByResourceReportGeneratorImpl rg = new SummaryByResourceReportGeneratorImpl();
		rg.setApplicationContext(super.context);
		rg.setServletContext(super.servletContext);
		rg.setUserSessionVo(getUserSessionVo());
		return rg.generateReport(filter, dialogueVo);
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateSummaryForCurrentDayReport(gov.nwcg.isuite.core.filter.CostReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateSummaryForCurrentDayReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws Exception {
		SummaryForCurrentDayReportGeneratorImpl rg = new SummaryForCurrentDayReportGeneratorImpl();
		rg.setApplicationContext(super.context);
		rg.setServletContext(super.servletContext);
		rg.setUserSessionVo(getUserSessionVo());
		return rg.generateReport(filter, dialogueVo);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateAircraftDetailReport(gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAircraftDetailReport(AircraftDetailReportFilter filter,DialogueVo dialogueVo) throws Exception {
			AircraftDetailReportGeneratorImpl rg = new AircraftDetailReportGeneratorImpl();
			rg.setApplicationContext(super.context);
			rg.setServletContext(super.servletContext);
			rg.setUserSessionVo(getUserSessionVo());
			return rg.generateReport(filter, dialogueVo);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportCostService#generateAircraftDetailReport(gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateCostShareReport(CostShareReportFilter filter,DialogueVo dialogueVo) throws Exception {
			CostShareReportGeneratorImpl rg = new CostShareReportGeneratorImpl();
			rg.setApplicationContext(super.context);
			rg.setServletContext(super.servletContext);
			rg.setUserSessionVo(getUserSessionVo());
			return rg.generateReport(filter, dialogueVo);
	}
}
