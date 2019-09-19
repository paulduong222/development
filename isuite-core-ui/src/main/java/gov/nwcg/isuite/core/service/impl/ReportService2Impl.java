package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ReportDemobDao;
import gov.nwcg.isuite.core.persistence.ReportIncidentResourceDao;
import gov.nwcg.isuite.core.reports.ActualDemobReport;
import gov.nwcg.isuite.core.reports.AirTravelRequestReport;
import gov.nwcg.isuite.core.reports.AllIncidentResourcesReport;
import gov.nwcg.isuite.core.reports.AvailableForReleaseReport;
import gov.nwcg.isuite.core.reports.CheckoutReport;
import gov.nwcg.isuite.core.reports.DemobPlanningReport;
import gov.nwcg.isuite.core.reports.GroundSupportReport;
import gov.nwcg.isuite.core.reports.ICS209Report;
import gov.nwcg.isuite.core.reports.ICS209ReportV3;
import gov.nwcg.isuite.core.reports.LastWorkDayReport;
import gov.nwcg.isuite.core.reports.QualificationsReport;
import gov.nwcg.isuite.core.reports.StrikeTeamTaskForceReport;
import gov.nwcg.isuite.core.reports.TentativeReleasePosterReport;
import gov.nwcg.isuite.core.reports.data.ActualDemobReportData;
import gov.nwcg.isuite.core.reports.data.AirTravelRequestReportData;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.data.AvailableForReleaseReportData;
import gov.nwcg.isuite.core.reports.data.CheckoutReportData;
import gov.nwcg.isuite.core.reports.data.DemobPlanningReportData;
import gov.nwcg.isuite.core.reports.data.GroundSupportReportData;
import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.core.reports.data.ICS209ReportV3Data;
import gov.nwcg.isuite.core.reports.data.LastWorkDayReportData;
import gov.nwcg.isuite.core.reports.data.QualificationsReportData;
import gov.nwcg.isuite.core.reports.data.StrikeTeamTaskForceReportData;
import gov.nwcg.isuite.core.reports.data.TentativeReleasePosterReportData;
import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.StrikeTeamTaskForceReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.core.service.ReportService2;
import gov.nwcg.isuite.core.vo.ICS209ResourceData;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class ReportService2Impl extends BaseReportService implements ReportService2 {

	public ReportService2Impl(){

	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateAllIncidentResourcesReport(gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try{
			/*
			 * Get the collection report data objects needed for the AllIncidentResourcesReport.
			 */
			Collection<AllIncidentResourcesReportData> reportData = dao.getAllIncidentResourcesReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("All Resources"));
				return dialogueVo;
			}
			
			/*
			 * Instantiate the report controller object.
			 */
			IReport report = new AllIncidentResourcesReport(reportData);

			report.addCustomField("includeSubTotal", filter.getSubTotalsFirstSort());
			
			/*
			 * Create the pdf report. Set the name of the output file.
			 */
			String pdfUrl = this.generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateGroundSupportReport(gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateGroundSupportReport(GroundSupportReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the ground support report.
			Collection<GroundSupportReportData> reportData = dao.getGroundSupportReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Ground Support","text.groundSupportInstructions"));
				return dialogueVo;
			}

			// Instantiate the report controller object.
			IReport report = new GroundSupportReport(reportData);
			
			String pdfUrl= generateReport(report);
			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateTentativeReleasePosterReport(gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateTentativeReleasePosterReport(TentativeReleasePosterReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the tentative release report.
			Collection<TentativeReleasePosterReportData> reportData = dao.getTentativeReleasePosterReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Tentative Release","text.tentativePosterInstructions"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new TentativeReleasePosterReport(reportData);

			String pdfUrl= generateReport(report);

			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateAirTravelRequestReport(gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAirTravelRequestReport(AirTravelRequestReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the air travel request report.
			Collection<AirTravelRequestReportData> reportData2 = dao.getAirTravelRequestReportData(filter);

			if(null==reportData2 || reportData2.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Air Travel Request","text.airTravelRequestInstructions"));
				return dialogueVo;
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(AirTravelRequestReportData d : reportData2){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the air travel dispatch notified field
								irDao.updateAirTravelDispatchNotified(ids);
							}
						}else{
							// update the air travel dispatch notified field
							irDao.updateAirTravelDispatchNotified(wpIds);
						}
					}
				}
				
			}

			Collection<AirTravelRequestReportData> reportData = new ArrayList<AirTravelRequestReportData>();
			
			for(AirTravelRequestReportData data : reportData2){
				try{
					if( null != data.getLengthAtAssignment() && data.getLengthAtAssignment() > 0){
						if(null != data.getFirstWorkDate()){
							data.getCalcDate();
						}
					}
					reportData.add(data);
				}catch(Exception zz){
					//System.out.println(zz.getMessage());
				}
			}
			
			// Instantiate the report controller object.
			IReport report = new AirTravelRequestReport(reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			String pdfUrl = generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateAvailableForReleaseReport(gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateAvailableForReleaseReport(AvailableForReleaseReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the availabe for release support report.
			Collection<AvailableForReleaseReportData> reportData = dao.getAvailableForReleaseReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Available For Release","text.availableForReleaseInstructions"));
				return dialogueVo;
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(AvailableForReleaseReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the tentative release dispatch notified field
								irDao.updateTentReleaseDispathNotified(ids);
							}
						}else{
							// update the tentative release dispatch notified field
							irDao.updateTentReleaseDispathNotified(wpIds);
						}
					}
				}
				
			}
			
			// Instantiate the report controller object.
			IReport report = new AvailableForReleaseReport(reportData);

			String pdfUrl = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateActualDemobReport(gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateActualDemobReport(ActualDemobReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the actual demob report.
			Collection<ActualDemobReportData> reportData = dao.getActualDemobReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Actual Demob","text.actualDemobInstructions"));
				return dialogueVo;
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(ActualDemobReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}

					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the tentative release dispatch notified field
								irDao.updateActualReleaseDispathNotified(ids);
							}
						}else{
							// update the tentative release dispatch notified field
							irDao.updateActualReleaseDispathNotified(wpIds);
						}
					}
					
				}
				
			}
			
			// Instantiate the report controller object.
			IReport report = new ActualDemobReport(reportData);
			
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			String pdfUrl= generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService2#generateCheckoutReport(gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateCheckoutReport(CheckoutReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			Long groupId=0L;
			if(LongUtility.hasValue(filter.getIncidentId())){
				// check if incident is part of group
				// if yes, use group checkout settings
				IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");
				groupId=incDao.getIncidentGroupId(filter.getIncidentId());
			}
			
			// Gets the collection of report data objects needed for the checkout report.
			Collection<CheckoutReportData> reportData = dao.getCheckoutReportData(filter,groupId);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Checkout","text.checkOutInstructions"));
				return dialogueVo;
			}else{
				if(filter.getMarkCheckOutFormPrinted()){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(CheckoutReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								irDao.updateCheckOutFormPrinted(ids);
							}
						}else{
							// update the check-out form printed field
							irDao.updateCheckOutFormPrinted(wpIds);
						}
					}
				}
			}

			Collection<CheckoutReportData> reportData2 = new ArrayList<CheckoutReportData>();
			if(BooleanUtility.isTrue(filter.getMarkIncludeBox12())){
				for(CheckoutReportData data : reportData){
					data.setIncludeBoxTwelve("YES");
					reportData2.add(data);
				}
				reportData=reportData2;
			}
			
			// Instantiate the report controller object.
			IReport report = new CheckoutReport(reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			String pdfUrl= generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateLastWorkDayReport(LastWorkDayReportFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the last work day report.
			Collection<LastWorkDayReportData> reportData = dao.getLastWorkDayReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Last Work Day"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = null;

			if(filter.getGroupByDateResourceCategory()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_1,reportData);
			}else if(filter.getGroupBySectionDate()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_2,reportData);
			}else if(filter.getGroupBySectionResourceCategoryDate()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_3,reportData);
			}
			
			// Set custom parameters
			Map<String,Object> map = new HashMap<String,Object>();
			if(filter.getIncludeAllDates()){
				map.put("reportDateValue", "Includes All Dates");
			}else{
				map.put("reportDateValue", DateUtil.toDateString(filter.getReportStartDate()) + " - " + DateUtil.toDateString(filter.getReportEndDate()));
			}
			
			report.setCustomFields(map);

			String pdfUrl = generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateDemobPlanningReport(DemobPlanningReportFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Collection<Incident> entities = new ArrayList<Incident>();
			
			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup ig = igdao.getById(filter.getIncidentGroupId(), IncidentGroupImpl.class);
				for(Incident inc : ig.getIncidents()){
					entities.add(inc);
				}
			}else{
				Incident incidentEntity = incidentDao.getById(filter.getIncidentId());
				entities.add(incidentEntity);
			}
			
			// Gets the collection of report data objects needed for the demob planning report.
			Collection<DemobPlanningReportData> reportData = new ArrayList<DemobPlanningReportData>();
			
			for(Incident inc : entities){
				//DemobPlanningReportFilter filter2 = new DemobPlanningReportFilter();
				//filter2.setIncidentId(inc.getId());
				filter.setIncidentId(inc.getId());
				Collection<DemobPlanningReportData> reportData2=dao.getDemobPlanningReportData(filter);
				for(DemobPlanningReportData rd : reportData2){
					rd.setIncidentId(inc.getId());
					String st =inc.getIncidentName() + " " +
								inc.getHomeUnit().getUnitCode() +
									"-"+inc.getNbr() ;
					rd.setPagesubtitle(st);
					reportData.add(rd);
				}
			}
			
			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Demob Planning"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new DemobPlanningReport(reportData);
			report.addCustomField("includeSubTotal", filter.getSubTotalsFirstSort());

			String pdfUrl=generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateICS209ReportOld(ICS209ReportFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try {
			if(!LongUtility.hasValue(filter.getIncidentId())){
				if(!LongUtility.hasValue(filter.getIncidentGroupId())){
					throw new ServiceException("IncidentId or IncidentGroupId must be set to run ICS209 report.");
				}
			}

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Collection<Incident> entities = new ArrayList<Incident>();
			IncidentGroup ig = null;
			
			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				ig = igdao.getById(filter.getIncidentGroupId(), IncidentGroupImpl.class);
				for(Incident inc : ig.getIncidents()){
					entities.add(inc);
				}
			}else{
				Incident incidentEntity = incidentDao.getById(filter.getIncidentId());
				entities.add(incidentEntity);
			}

			// Gets the collection of report data objects needed for the ics209 report.
			Collection<ICS209ReportData> reportData = new ArrayList<ICS209ReportData>();

			for(Incident inc : entities){
				ICS209ReportFilter filter2 = new ICS209ReportFilter();
				filter2.setIncidentId(inc.getId());
				Collection<ICS209ReportData> reportData2=new ArrayList<ICS209ReportData>();
				if(BooleanUtility.isTrue(filter.getStateGrouping()))
					reportData2=dao.getICS209ReportData(filter2);
				else
					reportData2=dao.getICS209ReportData2(filter2);
				
				for(ICS209ReportData rd : reportData2){
					rd.setIncidentId(inc.getId());
					String st =inc.getIncidentName() + " " +
								inc.getHomeUnit().getUnitCode() +
									"-"+inc.getNbr() ;
					rd.setPagesubtitle(st);
					reportData.add(rd);
				}
			}
			
			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("ICS 209"));
				return dialogueVo;
			}

			// ////////////////////////////////////////////////////////////////////////////////
			// CR 3883 - Provide a 209 Summary Report Page for all Incidents in a Group
			// If this report is for an incident group (and not just an incident), 
			// generate a summary object of type ICS209ReportData and add back to reportData
			if(entities!=null && entities.size()>1) { // More than 1 incident == Incident Group
				
				Map<String, ICS209ReportData> totalMap = new TreeMap<String, ICS209ReportData>();
				for(ICS209ReportData existingRecord : reportData){
					if(!totalMap.containsKey(existingRecord.getAgencyCode())) { 
						ICS209ReportData totalRecordCopy = new ICS209ReportData(existingRecord);
						totalRecordCopy.setIncidentId(-1L); // Reqd. differentiating marker only; NOT displayed 
						totalRecordCopy.setPagesubtitle(ig.getGroupName() + " - SUMMARY REPORT");
						totalMap.put(existingRecord.getAgencyCode(), totalRecordCopy);
					} else { 
						ICS209ReportData totalRecordCopy = totalMap.get(existingRecord.getAgencyCode());
						ICS209ReportData.addToTotal(totalRecordCopy, existingRecord);
					}
				}

				// Retrieve values from totalMap and append to the original results collection
				ICS209ReportData noAgencyRecord = totalMap.remove(ICS209ReportData.NO_AGENCY_CODE);
				if(null!=noAgencyRecord) {
					reportData.add(noAgencyRecord);
				}
				reportData.addAll(totalMap.values());
			}
			// ////////////////////////////////////////////////////////////////////////////////
			
			// Instantiate the report controller object
			String subTitle = "do not use this one for this report";
			IReport report = new ICS209Report(subTitle,reportData);

			String pdfUrl=generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateICS209Report(ICS209ReportFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try {
			if(!LongUtility.hasValue(filter.getIncidentId())){
				if(!LongUtility.hasValue(filter.getIncidentGroupId())){
					throw new ServiceException("IncidentId or IncidentGroupId must be set to run ICS209 report.");
				}
			}

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");

			Collection<ICS209ResourceData> data = dao.getICS209ResourceData(filter);

			Collection<ICS209ReportV3Data> reportData = ICS209ReportV3Data.buildReportData(data,true,"");

			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				// ////////////////////////////////////////////////////////////////////////////////
				// CR 3883 - Provide a 209 Summary Report Page for all Incidents in a Group
				// If this report is for an incident group (and not just an incident), 
				// generate a summary object of type ICS209ReportData and add back to reportData

				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup ig = incidentGroupDao.getById(filter.getIncidentGroupId());
				if(null != ig)
					incidentGroupDao.flushAndEvict(ig);
				String groupName=ig.getGroupName();
				
				// add one more report object, consolidating all data as one
				Collection<ICS209ReportV3Data> reportData2 = ICS209ReportV3Data.buildReportData(data, false,groupName);
				if(CollectionUtility.hasValue(reportData2)){
					reportData.addAll(reportData2);
				}
			}
			
			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("ICS 209"));
				return dialogueVo;
			}


			// Instantiate the report controller object
			String subTitle = "do not use this one for this report";
			IReport report = new ICS209ReportV3("",reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			
			String pdfUrl=generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateStrikeTeamTaskForceReport(StrikeTeamTaskForceReportFilter filter,DialogueVo dialogueVo) throws ServiceException{
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try {
			ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");
			Collection<Long> ids = filter.getLongResourceIds();
			Collection<StrikeTeamTaskForceReportData> reportData = dao.getStrikeTeamTaskForceReportData(filter.getIncidentId(),ids);
			
			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Strike Team/Task Force"));
				return dialogueVo;
			}

			String subTitle="";
			
			if(LongUtility.hasValue(filter.getIncidentId())){
				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
				Incident incEntity = incidentDao.getById(filter.getIncidentId());
				subTitle = incEntity.getIncidentName() + " " + (null != incEntity.getHomeUnit() ? incEntity.getHomeUnit().getUnitCode() : "") + "-" + incEntity.getNbr();
			}else if(LongUtility.hasValue(filter.getIncidentGroupId())){
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup incGroupEntity = incidentGroupDao.getById(filter.getIncidentGroupId(),IncidentGroupImpl.class);
				
				if(null != incGroupEntity && incGroupEntity.getIncidents().size() > 0){
					for(Incident i : incGroupEntity.getIncidents()){
						if(subTitle.length() > 0)
							subTitle = subTitle + ", ";
						subTitle = subTitle + i.getIncidentName() + " " + (null != i.getHomeUnit() ? i.getHomeUnit().getUnitCode() : "") + "-" + i.getNbr();
					}
				}
			}
			
			IReport report = new StrikeTeamTaskForceReport(subTitle, reportData);
			String pdfUrl= this.generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateQualificationsReport(QualificationsReportFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try {
			Collection<QualificationsReportData> reportData = dao.getQualificationsReportData(filter);

			if(null==reportData || reportData.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Qualifications"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new QualificationsReport("",reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			String pdfUrl= generateReport(report);
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
}
