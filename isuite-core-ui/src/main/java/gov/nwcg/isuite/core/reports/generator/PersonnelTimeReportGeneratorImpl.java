package gov.nwcg.isuite.core.reports.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.PersonnelTimeReport;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.rules.PersonnelTimeReportGeneratorRulesHandler;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

public class PersonnelTimeReportGeneratorImpl extends ReportGeneratorImpl {

  @Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
	  try{ 
			ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
	
			// apply rules
			PersonnelTimeReportGeneratorRulesHandler rulesHandler = new PersonnelTimeReportGeneratorRulesHandler(context);
			if (rulesHandler.execute((PersonnelTimeReportFilter) filter, dialogueVo) == PersonnelTimeReportGeneratorRulesHandler._OK) {
				Collection<PersonnelTimeReportData> reportData;
				reportData = reportTimeDao.getPersonnelTimeReportData((PersonnelTimeReportFilter) filter, filter.getIncidentId());
				
				if(reportData == null || reportData.size() == 0) {
					return noDataMessage("Personnel Time Post", dialogueVo);
				}
				
				//List<String> reportsList = new ArrayList<String>();
				String pdfURL = null;
				try {
					IReport report = new PersonnelTimeReport("", reportData);
					
					// Enable export to excel if required
					if(filter.isExportToExcel()) {
						report.enableExportToExcel();
					}

					report.addCustomField("SUBREPORT_DIR", getSourcePath());
					
					//reportsList.add(createAndSaveReport(report));
					pdfURL = createAndSaveReport(report);
				} catch (Exception e) {
					super.handleException(e);
				}
	
				//CourseOfActionVo coa = new CourseOfActionVo();
				//coa.setCoaName("GENERATE_PERSONNEL_TIME_REPORT"); 
				//coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				//coa.setIsDialogueEnding(true);
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("REPORT COMPLETE");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(Boolean.TRUE);	 
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(pdfURL);
			}
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return dialogueVo;	
	}

}
