package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.ScratchReportTime;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.ShiftsInExcessOfStandardHoursReport;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursSubReportData;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.rules.ShiftsInExcessReportGeneratorRulesHandler;
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

public class ShiftsInExcessReportGeneratorImpl2 extends ReportGeneratorImpl {

    @Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
    	
    	ShiftsInExcessOfStandardHoursReportFilter thisReportFilter = (ShiftsInExcessOfStandardHoursReportFilter) filter;
    	ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
    	Long transactionId = null;
    	
		try {
			// apply rules
			ShiftsInExcessReportGeneratorRulesHandler rulesHandler = new ShiftsInExcessReportGeneratorRulesHandler(context);
			if (rulesHandler.execute(thisReportFilter, dialogueVo) == ShiftsInExcessReportGeneratorRulesHandler._OK) {
				
				// Set zero values to null since these will be sent to the database stored procs
				if(filter.getIncidentId() == 0) filter.setIncidentId(null); 
				if(filter.getIncidentGroupId() == 0) filter.setIncidentGroupId(null);
				if(filter.getResourceId() == 0) filter.setResourceId(null);
				
				// Step 1:
				// Retrieve top level report data that contains generic incident(s) information
				Collection<ShiftsInExcessOfStandardHoursReportData> topLevelReportList = 
					reportTimeDao.getShiftsInExcessOfStandardHoursReportData2(thisReportFilter);
				
				// If empty, send a message to the user
				if(topLevelReportList == null || topLevelReportList.size() == 0) {
					return noDataMessage("Shifts in Excess of Standard Hours", dialogueVo);
				}
				
				// Step 2:
				// Insert scratch data in scratch table based on filter values.
				// These filter values include:
				// Resource Id (either directly or indirectly via Request Number), possibly null.
				// Start Date.
				// End Date.
				transactionId = reportTimeDao.insertShiftsInExcessReportDataToScratchTable(thisReportFilter);
				
				// Step 3:
				// Retrieve values from the scratch table 
				// Values retrieved should be those that exceed the standard hours entered by the user
				Collection<ShiftsInExcessOfStandardHoursReportData> topLevelReportListWithData = 
					new ArrayList<ShiftsInExcessOfStandardHoursReportData>(); 
				
				// For every top level report(one per incident), retrieve the records from the scratch table,
				// and add them in the in the top level report. Finally, add the top level report objects 
				// in a the list of reports (one per incident) to be returned.
				for(ShiftsInExcessOfStandardHoursReportData topLevelReportData : topLevelReportList){
					Long incidentId = topLevelReportData.getIncidentId();
					
					Collection<ScratchReportTime> scratchReportList = reportTimeDao.getShiftsInExcessOfStandardHoursReportDetails2(
							incidentId,
							transactionId,
							thisReportFilter);

					// If scratch list exists, convert it and add it to the report list to be returned to the user
					if(scratchReportList != null && scratchReportList.size()>0) {
					
						// Convert scratch report data to sub-report data
						Collection<ShiftsInExcessOfStandardHoursSubReportData> subReportDataList 
							= convertScratchToShiftsInExcessSub(scratchReportList, thisReportFilter.getStandardHours());
						
						topLevelReportData.setSubReportData(subReportDataList);
						topLevelReportListWithData.add(topLevelReportData);
					}
				}
				
				// If empty, send a message to the user
				if(topLevelReportListWithData == null || topLevelReportListWithData.size() == 0) {
					return noDataMessage("Shifts in Excess of Standard Hours", dialogueVo);
				}
				
				// Create and send back the report.
				//List<String> reportsList = new ArrayList<String>();
				String pdfURL = null;
				
				try {
					IReport report = new ShiftsInExcessOfStandardHoursReport("", topLevelReportListWithData);
					report.addCustomField("SUBREPORT_DIR", getSourcePath());

					//reportsList.add(createAndSaveReport(report));
					pdfURL = createAndSaveReport(report);
				} catch (Exception e) {
					super.handleException(e);
				}			

				// add reportsList to dialogueVo
				//CourseOfActionVo coa = new CourseOfActionVo();
				//coa.setCoaName("GENERATE_SHIFTS_IN_EXCESS_OF_STANDARD_HOURS_REPORT");
				//coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				//oa.setIsDialogueEnding(true);
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("REPORT COMPLETE");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(Boolean.TRUE);	  
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(pdfURL);
			}
		} catch (Exception e) {
			super.handleException(e);
		} finally {
			if(transactionId != null) {
				// Step 4: Delete rows from the scratch table
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
    private List<ShiftsInExcessOfStandardHoursSubReportData> convertScratchToShiftsInExcessSub(
    		Collection<ScratchReportTime> inList,
    		Double standardHours) {
    	// If inList is null or of zero length, return null.
    	if(inList==null || inList.size()==0) { 
    		return null;
    	}
    	
    	List<ShiftsInExcessOfStandardHoursSubReportData> outList = new ArrayList<ShiftsInExcessOfStandardHoursSubReportData>();
    	
    	for(ScratchReportTime scratchData : inList){
    		ShiftsInExcessOfStandardHoursSubReportData reportData = new ShiftsInExcessOfStandardHoursSubReportData();
    		reportData.setRequestNumber(scratchData.getRequestNumber());
    		reportData.setResourceName(scratchData.getResourceLastName() + ", " + scratchData.getResourceFirstName());
			reportData.setShiftStartDate(DateUtil.getDateZeroTimeStamp(scratchData.getShiftStartDate()));
			reportData.setShiftEndDate(DateUtil.getDateZeroTimeStamp(scratchData.getShiftEndDate()));
			reportData.setTotalShiftHours(scratchData.getHoursOfWork()!=null?scratchData.getHoursOfWork().doubleValue():0);
			reportData.setAmountExcess(reportData.getTotalShiftHours() - standardHours);
    		outList.add(reportData);
    	}

    	return outList;
    }
}
