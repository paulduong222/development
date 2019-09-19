package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.ScratchReportTime;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.GlidePathReport;
import gov.nwcg.isuite.core.reports.WorkRestRatioReport;
import gov.nwcg.isuite.core.reports.data.GlidePathReportData;
import gov.nwcg.isuite.core.reports.data.WorkRestRatioReportData;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class GlidePathReportGeneratorImpl extends ReportGeneratorImpl {

	
	
	
	
	
	
	
	// Change from WorkrestratioreportFilter to GlidePathReportFilter
	
	
	
	
	
	
	
	// Private static Strings that are expected by the JasperReport. 
	// Changing these string values will require a change in the JasperReport also.
	private static final String GROUPBYCLAUSE_NONE = "";
	private static final String GROUPBYCLAUSE_SECTION = "section";
	private static final String GROUPBYCLAUSE_DATE = "date";
	
	public <E extends GlidePathReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
    	Long transactionId = null;
    	//ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		
		try {

/*        	if(!(filter instanceof WorkRestRatioReportFilter)){
    			throw new Exception("Invalid Filter Type. Expecting Filter to be of type WorkRestRatioReportFilter.");
    		}
        	
        	WorkRestRatioReportFilter wrrReportFilter = (WorkRestRatioReportFilter)filter;
        	
        	// Set 0 values in the filter to null since they will be passed to the 
        	// database stored procedures
        	if(filter.getIncidentGroupId() == 0) filter.setIncidentGroupId(null);
        	if(filter.getIncidentId() == 0) filter.setIncidentId(null);
        	if(filter.getResourceId() == 0) filter.setResourceId(null);
        	
        	// Unset resource, section, grouping, and sorting values based on the user's selection
        	// This is required in the case user selects a sub-option but then moves to a different 
        	// top level option
        	if(wrrReportFilter.isAllResources()){
        		wrrReportFilter.setResourceId(null);
        	} else {
        		wrrReportFilter.setSectionTypeAll(true);
        		wrrReportFilter.setGroupByNone(true);
        		wrrReportFilter.setGroupBySection(false);
        		wrrReportFilter.setGroupByDate(false);
        	}
        	*/
			// Call 1: Insert data in scratch table and retrieve the transaction Id
			//transactionId = reportTimeDao.insertWorkRestRatioReportDataToScratchTable((WorkRestRatioReportFilter)filter);
			
			// Call 2: Retrieve values from the scratch table and format/convert them to WorkRestRatioReportData
			//Collection<ScratchReportTime> scratchDataList = reportTimeDao.getWorkRestRatioReportData(transactionId, (WorkRestRatioReportFilter)filter);
			
			// Convert from collection of entity objects to that of WorkRestRatioReportData objects
			//List<GlidePathReportData> reportData = this.convertScratchToWorkRestRatio(scratchDataList);
			
			List<GlidePathReportData> reportData = new ArrayList<GlidePathReportData>();
			
			for (int i=0; i<20; i++){
				reportData.add(new GlidePathReportData());
			}
			
			if(reportData == null || reportData.size() == 0) {
				return noDataMessage("Glide Path Report.", dialogueVo);
			}
			
			//String groupByClause = getGroupByClause((WorkRestRatioReportFilter)filter);
			
			//List<String> reportsList = new ArrayList<String>();
			
//			IReport report = new GlidePathReport("", reportData);
//			report.addCustomField("incidentName", "My Incident Name");//filter.getIncidentName());
//			report.addCustomField("incidentTag", "My incident tag");//filter.getIncidentTag());
//			report.addCustomField("startDate", new Date()) ;//filter.getStartDate());
//			report.addCustomField("endDate", new Date()); //filter.getEndDate());
//			//report.addCustomField("groupByClause", groupByClause);
			
			//reportsList.add(createAndSaveReport(report));
			String pdfURL = null;
//			pdfURL = createAndSaveReport(report);

			// add reportsList to dialogueVo
			//CourseOfActionVo coa = new CourseOfActionVo();
			//coa.setCoaName("GENERATE_WORK_REST_RATIO_REPORT");
			//coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			//coa.setIsDialogueEnding(true);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("REPORT COMPLETE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(pdfURL);
		} catch (Exception e) {
			super.handleException(e);
		} finally {
//			if(transactionId != null) {
//				// Call 3: Delete rows from the scratch table
//				reportTimeDao.deleteReportTimeScratchData(transactionId);
//			}
		}

		return dialogueVo;	
	}
    
    /**
     * Private method that converts the records retrieved from ScratchReportTime table to a kind
     * that are needed by this report - WorkRestRatioReportData
     * @param inList - the list of scratch report time records 
     * @return list of WorkRestRatioReportData objects, which are sent to the Jasper report
     */
    private List<WorkRestRatioReportData> convertScratchToWorkRestRatio(Collection<ScratchReportTime> inList) {
    	// If inList is null or of zero length, return null.
    	if(inList==null || inList.size()==0) { 
    		return null;
    	}
    	
    	List<WorkRestRatioReportData> outList = new ArrayList<WorkRestRatioReportData>();
    	
    	for(ScratchReportTime scratchData : inList){
    		WorkRestRatioReportData reportData = new WorkRestRatioReportData();
//    		reportData.setRequestNumber(scratchData.getRequestNumber());
//    		reportData.setResourceName(scratchData.getResourceLastName() + ", " + scratchData.getResourceFirstName());
//    		reportData.setItemCode(scratchData.getItemCode());
//    		reportData.setStatus(scratchData.getStatus());
//    		reportData.setSection(scratchData.getSectionName());
//    		reportData.setHoursOfWork(scratchData.getHoursOfWork()!=null?scratchData.getHoursOfWork().doubleValue():0);
//    		reportData.setHoursOfRest(scratchData.getHoursOfRest()!=null?scratchData.getHoursOfRest().doubleValue():0);
//    		reportData.setHoursOfMitigation(scratchData.getMitigationNeeded()!=null?scratchData.getMitigationNeeded().doubleValue():0);
//			reportData.setShiftStartDate(DateUtil.getDateZeroTimeStamp(scratchData.getShiftStartDate()));
			
    		outList.add(reportData);
    	}

    	return outList;
    }
    
    /**
     * Private method to return the appropriate group-by clause to the Jasper Report
     * @param filter
     * @return
     */
    private String getGroupByClause(WorkRestRatioReportFilter filter) {
		if(filter==null) return GROUPBYCLAUSE_NONE;
		
		if(filter.isGroupBySection())
			return GROUPBYCLAUSE_SECTION;
		else if(filter.isGroupByDate())
			return GROUPBYCLAUSE_DATE;
		else return GROUPBYCLAUSE_NONE;
	}

	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter,
			DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
		return null;
	}
}
