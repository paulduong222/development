package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.persistence.GlidePathReportDao;
import gov.nwcg.isuite.core.reports.GlidePathReport;
import gov.nwcg.isuite.core.reports.GlidePathReportTabEnum;
import gov.nwcg.isuite.core.reports.data.GlidePathReportData;
import gov.nwcg.isuite.core.reports.data.GlidePathReportResourceData;
import gov.nwcg.isuite.core.reports.data.GlidePathReportResourceDayData;
import gov.nwcg.isuite.core.reports.data.GlidePathReportSummaryData;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.service.GlidePathReportService;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.ReportSectionCategorySelectVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlidePathReportServiceImpl extends BaseReportService implements GlidePathReportService {
	
	public GlidePathReportServiceImpl() {
		super();
	}
	
	public void initialization(){
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GlidePathReportService#generateGlidePathReport(gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@Override
	public DialogueVo generateGlidePathReport(GlidePathReportFilter filter, DialogueVo dialogueVo) throws ServiceException,	PersistenceException {

		if (dialogueVo == null) { dialogueVo = new DialogueVo(); }
		
		GlidePathReportDao dao = (GlidePathReportDao)super.context.getBean("glidePathReportDao");
		
		if(filter.getIncidentGroupId() == 0) filter.setIncidentGroupId(null);
    	if(filter.getIncidentId() == 0) filter.setIncidentId(null);
    	
    	if(validate(filter, dialogueVo)){
			try {
				// Determine list of tabs to render
				List<GlidePathReportTabEnum> tabsToGenerate = null;
				if(filter.getOptionSections() == Boolean.TRUE) {
					tabsToGenerate = new ArrayList<GlidePathReportTabEnum>();
					for(ReportSectionCategorySelectVo vo: filter.getSectionCategories()) {
						GlidePathReportTabEnum tabEnum = GlidePathReportTabEnum.getFromCode(vo.getValue());
						if(tabEnum != null) {
							tabsToGenerate.add(tabEnum);
						}
					}
				} else { // For both all tabs and summary tab only. 
					tabsToGenerate = GlidePathReportTabEnum.getAllContentTabs();
				}
				
				Collection<GlidePathReportResourceData> combinedReportData = dao.getGlidePathReportData(filter, tabsToGenerate);
				
				// Create map of Lists to hold different records for different tabs.
				Map<String, List<GlidePathReportResourceData>> dataSplitByTabTitle = new HashMap<String, List<GlidePathReportResourceData>>();
				for(GlidePathReportTabEnum tab: tabsToGenerate){
					dataSplitByTabTitle.put(tab.getContentTitle(), new ArrayList<GlidePathReportResourceData>());
				}
				
				for(GlidePathReportResourceData resourceData: combinedReportData){
					dataSplitByTabTitle.get(resourceData.getSubSectionName()).add(resourceData);
				}
				
				int numberOfDays = filter.getNumberOfDays();
				numberOfDays = (Math.min(GlidePathReportData.MAXIMUM_NUMBER_OF_DAYS, Math.max(GlidePathReportData.MINIMUM_NUMBER_OF_DAYS, numberOfDays)));
				
				Date userSelectedReportStartDate = DateTransferVo.getDate(filter.getStartDateVo());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(userSelectedReportStartDate);
			    calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
			    Date userDefinedReportEndDate = calendar.getTime();
				
				List<GlidePathReportData> worksheets = new ArrayList<GlidePathReportData>();
				List<String> excelWorksheetNames = new ArrayList<String>();
				
				////////////////////////////////////////////////////////////////////////////////
				// Add Summary Sheet Data as the first object 
				List<GlidePathReportSummaryData> summaryDataList = new ArrayList<GlidePathReportSummaryData>();
				
				GlidePathReportData summarySheet = new GlidePathReportData();
				// Section name for summary sheet MUST be set to "SUMMARY". Needed by JRXML.
				summarySheet.setSectionName("SUMMARY");
				excelWorksheetNames.add("SUMMARY");
				summarySheet.setSummaryDataList(summaryDataList);
				summarySheet.setResourceDataList(null);
				
				worksheets.add(summarySheet);
				////////////////////////////////////////////////////////////////////////////////
				
				////////////////////////////////////////////////////////////////////////////////
				// Now add other section sheets
				
				for(GlidePathReportTabEnum tab: tabsToGenerate){
					GlidePathReportData sectionSheet = new GlidePathReportData();
					
					List<GlidePathReportResourceData> sectionResourceDataList =  dataSplitByTabTitle.get(tab.getContentTitle()); 
					if(sectionResourceDataList.size()>0){//section!=dummyEmptySectionSheetNumber){
						
						// NOTE: Leave sub-section code for future use in case clients want to add subsections as in original glidepath report.
	//					// for every subsection
	//					for(int subsectionNumber = 1; subsectionNumber<=numberOfSubsections; subsectionNumber++){
							
							GlidePathReportResourceData totalsRecord = GlidePathReportResourceData.generateResourceDataTotalsRecord(tab.getContentTitle(), numberOfDays);
							
							// for every resource/record in a subsection
							for(GlidePathReportResourceData resourceData : sectionResourceDataList) {
								resourceData.populateDayLabels(userSelectedReportStartDate, userDefinedReportEndDate, numberOfDays);
								GlidePathReportResourceDayData.addToTotals(totalsRecord, resourceData, numberOfDays);
							}
	
							// Add the totals record as the last record in this iteration
							sectionResourceDataList.add(totalsRecord);
							
							//Convert the totalsRecord to GlidePathReportSummaryData and add to Summary Sheet.
							GlidePathReportSummaryData summaryData = new GlidePathReportSummaryData(tab.getContentTitle());
							for(int dayNumber = 0; dayNumber<numberOfDays; dayNumber++) {
								String totalString = totalsRecord.getResourceDayData().get(dayNumber).getText();
	
								try { // Only add if parsing passed
									summaryData.getDayValues().add(Integer.parseInt(totalString)); 
								}
								catch(NumberFormatException nfe) { // Don't want to show non-numeric here
									summaryData.getDayValues().add(GlidePathReportSummaryData.NA);
								}
							}
							summaryDataList.add(summaryData);
	//					}
					}
					else { 
						sectionResourceDataList.add(GlidePathReportResourceData.getSectionSheetEmptyPlaceholder(tab.getContentTitle(), numberOfDays));
						GlidePathReportSummaryData summaryData = new GlidePathReportSummaryData(tab.getContentTitle());
						for(int dayNumber = 0; dayNumber<numberOfDays; dayNumber++) {
							summaryData.getDayValues().add(0); 
						}
						summaryDataList.add(summaryData);
					}
					
					if(filter.getOptionSummaryOnly()==Boolean.FALSE) {
						sectionSheet.setSectionName(tab.getTabTitle()); // Used in group expression by Jasper to break content into separate tabs. 
						excelWorksheetNames.add(tab.getTabTitle()); // Used to tab names in bottom tab bar in excel
						sectionSheet.setResourceDataList(sectionResourceDataList);
						sectionSheet.setSummaryDataList(null);
						worksheets.add(sectionSheet);
					}
				}
				
				IReport glidePathReport = new GlidePathReport("", worksheets);
				glidePathReport.addCustomField("incidentName", filter.getIncidentName()); 
				glidePathReport.addCustomField("incidentTag", filter.getIncidentTag()); 
				glidePathReport.addCustomField("numberOfDays", numberOfDays);			
				glidePathReport.addCustomField("startingDate", userSelectedReportStartDate);
				glidePathReport.addCustomField("SUBREPORT_DIR", getSourcePath());
				glidePathReport.setExcelWorksheetNames(excelWorksheetNames);
	
				String reportFileURL = generateReport(glidePathReport);
				
				/*
				// Create and modify a JasperDesign object based on user choices
				JasperDesign jasperDesign = this.modifyJasperDesign(glidePathReport, filter);
				// Send the IReport and JasperDesign object to create a report
				String reportFileURL = generateReportFromJasperDesign(glidePathReport, jasperDesign);
				*/
				
				List<String> reportsList = new ArrayList<String>();
				reportsList.add(reportFileURL);
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("EXCEL REPORT COMPLETE"); 
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(reportsList);
			}catch(Exception e){
				e.printStackTrace();
				super.dialogueException(dialogueVo, e);
			}
		}
		return dialogueVo;
	}
	
	/*
	private JasperDesign modifyJasperDesign(IReport glidePathReport, GlidePathReportFilter filter) throws Exception {
		String templateName = glidePathReport.getReportName();
		JasperDesign jasperDesign = getJasperDesignFromTemplate(templateName);
		return jasperDesign;
		// Delegate to custom delegate class 
		//return GlidePathDesigner.modifyGlidePathReportDesign(glidePathReport, filter, jasperDesign);
	}*/
	
	private boolean validate(GlidePathReportFilter filter, DialogueVo dialogueVo) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		// 1.
		// User didnt enter any date
		if(DateTransferVo.getDate(filter.getStartDateVo()) == null) {
			error = new ErrorObject("error.mustSelectItem","Start Date");
			errorObjects.add(error);
		}
		
		// 2.
		// User selected Section/Categories choice, but didnt specify any category
		if(filter.getOptionSections() == Boolean.TRUE && 
				(filter.getSectionCategories() == null || filter.getSectionCategories().size()==0)) {
			error = new ErrorObject("error.mustSelectItem","Section/Category");
			errorObjects.add(error);
		}
		
		if(errorObjects.size() > 0) {
			CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName("GLIDEPATH_ERRORS");
		    coa.setCoaType(CourseOfActionTypeEnum.ERROR);
		    coa.setErrorObjectVos(errorObjects);
		    coa.setIsDialogueEnding(true);
		    
		    dialogueVo.setCourseOfActionVo(coa);
		    return false;
		}
		return true;
	}
}	
