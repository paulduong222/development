package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.CrewRosterReport;
import gov.nwcg.isuite.core.reports.data.CrewRosterReportData;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrewRosterReportGeneratorImpl extends ReportGeneratorImpl {

	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
    	
    	ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		
		try {
        	if(!(filter instanceof TimeReportFilter)){
    			throw new Exception("Invalid Filter Type. Expecting Filter to be of type TimeReportFilter.");
    		}
        	
        	// Retrieve report data based on the values in the filter
        	Collection<CrewRosterReportData> reportData = reportTimeDao.getCrewRosterReportData((TimeReportFilter)filter);
        	
			if(reportData == null || reportData.size() == 0) {
				return noDataMessage("Crew Roster Report.", dialogueVo);
			}
			
			//List<String> reportsList = new ArrayList<String>();
			String pdfURL = null;
			IReport report = new CrewRosterReport("", reportData);
			report.addCustomField("incidentName", filter.getIncidentName());
			report.addCustomField("incidentTag", filter.getIncidentTag());
			report.addCustomField("reportTitle", "Crew Roster Report for " + filter.getRequestNumber() + ": " + filter.getResourceName());
			//reportsList.add(createAndSaveReport(report));
			pdfURL = createAndSaveReport(report);
			
			// add reportsList to dialogueVo
			//CourseOfActionVo coa = new CourseOfActionVo();
			//coa.setCoaName("GENERATE_CREW_ROSTER_REPORT");
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
		} 

		return dialogueVo;	
	}
}
