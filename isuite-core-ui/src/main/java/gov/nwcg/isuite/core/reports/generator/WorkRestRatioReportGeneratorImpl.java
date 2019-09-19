package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.ScratchReportTime;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.WorkRestRatioReport;
import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.core.reports.data.WorkRestRatioReportData;
import gov.nwcg.isuite.core.reports.data.WorkRestRatioSubReportData;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WorkRestRatioReportGeneratorImpl extends ReportGeneratorImpl {

	// Private static Strings that are expected by the JasperReport. 
	// Changing these string values will require a change in the JasperReport also.
	private static final String GROUPBYCLAUSE_NONE = "";
	private static final String GROUPBYCLAUSE_SECTION = "section";
	private static final String GROUPBYCLAUSE_DATE = "date";
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
    	Long transactionId = null;
    	ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		
		try {

        	if(!(filter instanceof WorkRestRatioReportFilter)){
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
        	
			// Call 1: Insert data in scratch table and retrieve the transaction Id
			transactionId = reportTimeDao.insertWorkRestRatioReportDataToScratchTable((WorkRestRatioReportFilter)filter);
			
			// Call 2: Retrieve values from the scratch table and format/convert them to WorkRestRatioReportData
			Collection<ScratchReportTime> scratchDataList = reportTimeDao.getWorkRestRatioReportData(transactionId, (WorkRestRatioReportFilter)filter);
			
			// Convert from collection of entity objects to that of WorkRestRatioReportData objects
			List<WorkRestRatioReportData> reportData = this.convertScratchToWorkRestRatio(scratchDataList, wrrReportFilter);
			
			if(reportData == null || reportData.size() == 0) {
				return noDataMessage("Work/Rest Ratio Report.", dialogueVo);
			}
			
			String groupByClause = getGroupByClause((WorkRestRatioReportFilter)filter);
			
			IReport report = new WorkRestRatioReport("", reportData);
			report.addCustomField("SUBREPORT_DIR", getSourcePath());
			report.addCustomField("startDate", filter.getStartDate());
			report.addCustomField("endDate", filter.getEndDate());
			report.addCustomField("groupByClause", groupByClause);
			
			String pdfURL = null;
			pdfURL = createAndSaveReport(report);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("REPORT COMPLETE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(pdfURL);
		} catch (Exception e) {
			super.handleException(e);
		} finally {
			if(transactionId != null) {
				// Call 3: Delete rows from the scratch table
				reportTimeDao.deleteReportTimeScratchData(transactionId);
			}
		}
		return dialogueVo;	
	}
    
    /**
     * Private method that converts the records retrieved from ScratchReportTime table to a kind
     * that are needed by this report - WorkRestRatioReportData
     * @param inList - the list of scratch report time records 
     * @return list of WorkRestRatioReportData objects, which are sent to the Jasper report
     */
    private List<WorkRestRatioReportData> convertScratchToWorkRestRatio(Collection<ScratchReportTime> generatedScratchList, WorkRestRatioReportFilter filter) {
    	// If generatedScratchList is null or of zero length, return null.
    	if(generatedScratchList==null || generatedScratchList.size()==0) { 
    		return null;
    	}
    	
    	List<WorkRestRatioReportData> outList = new ArrayList<WorkRestRatioReportData>();
    	
    	if(filter.getIncidentGroupId()!=null){// Report was generated for an incident group
    		Map<Long, List<ScratchReportTime>> mainReportMap = new TreeMap<Long, List<ScratchReportTime>>();
    		for(ScratchReportTime scratchData : generatedScratchList){
    			if(!mainReportMap.containsKey(scratchData.getIncidentId())) { 
    				List<ScratchReportTime> incidentList = new ArrayList<ScratchReportTime>();
    				incidentList.add(scratchData);
    				mainReportMap.put(scratchData.getIncidentId(), incidentList);
				} else { 
					(mainReportMap.get(scratchData.getIncidentId())).add(scratchData);
				}
    		}
    		
    		for(Map.Entry<Long, List<ScratchReportTime>> entry : mainReportMap.entrySet()) {
    			//Long incidentId = entry.getKey();
    			List<ScratchReportTime> scratchListForSingleIncident = entry.getValue();
    			
    			WorkRestRatioReportData singleIncidentReportData = new WorkRestRatioReportData();
    			// Need not check if element exists at index 0 because the code above guarantees it.
        		singleIncidentReportData.setIncidentId(scratchListForSingleIncident.get(0).getIncidentId());
        		singleIncidentReportData.setIncidentName(scratchListForSingleIncident.get(0).getIncidentName());
        		singleIncidentReportData.setIncidentTag(scratchListForSingleIncident.get(0).getIncidentNumber());
        		
        		List<WorkRestRatioSubReportData> subReportList = getSubReportDataForSingleIncident(scratchListForSingleIncident);
        		singleIncidentReportData.setSubReportData(subReportList);
        		outList.add(singleIncidentReportData);
			}
    	} else {// Report was generated for a single incident 
    		WorkRestRatioReportData singleIncidentReportData = new WorkRestRatioReportData();
    		singleIncidentReportData.setIncidentId(filter.getIncidentId());
    		singleIncidentReportData.setIncidentName(filter.getIncidentName());
    		singleIncidentReportData.setIncidentTag(filter.getIncidentTag());
    		
    		List<WorkRestRatioSubReportData> subReportList = getSubReportDataForSingleIncident(generatedScratchList);
    		singleIncidentReportData.setSubReportData(subReportList);
    		outList.add(singleIncidentReportData);
    	}
    	return outList;
    }
    
    private static List<WorkRestRatioSubReportData> getSubReportDataForSingleIncident(Collection<ScratchReportTime> scratchListForSingleIncident){
    	List<WorkRestRatioSubReportData> subReportList = new ArrayList<WorkRestRatioSubReportData>();
		for(ScratchReportTime scratchData : scratchListForSingleIncident){
    		WorkRestRatioSubReportData reportData = new WorkRestRatioSubReportData();
    		reportData.setRequestNumber(scratchData.getRequestNumber()); 
    		reportData.setResourceName(scratchData.getResourceLastName() + ", " + scratchData.getResourceFirstName());
    		reportData.setItemCode(scratchData.getItemCode());
    		reportData.setStatus(scratchData.getStatus());
    		reportData.setSection(scratchData.getSectionName());
    		reportData.setHoursOfWork(scratchData.getHoursOfWork()!=null?scratchData.getHoursOfWork().doubleValue():0);
    		reportData.setHoursOfRest(scratchData.getHoursOfRest()!=null?scratchData.getHoursOfRest().doubleValue():0);
    		reportData.setHoursOfMitigation(scratchData.getMitigationNeeded()!=null?scratchData.getMitigationNeeded().doubleValue():0);
			reportData.setShiftStartDate(DateUtil.getDateZeroTimeStamp(scratchData.getShiftStartDate()));
			
			subReportList.add(reportData);
    	}
		return subReportList;
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
}
