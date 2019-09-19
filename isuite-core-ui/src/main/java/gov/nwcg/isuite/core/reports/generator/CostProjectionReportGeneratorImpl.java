package gov.nwcg.isuite.core.reports.generator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.reports.CostProjectionCategoryReport;
import gov.nwcg.isuite.core.reports.data.CostProjectionReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionTotalSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

public abstract class CostProjectionReportGeneratorImpl extends ReportGenerator2Impl {
		
	protected CostProjectionReportFilter filter;
	protected CostProjectionDao dao;
	protected CostProjectionReportData costProjectionReportData;
	protected List<CostProjectionReportData> mainReportDataList;
	
	@Override
	public <E extends ReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException,
	PersistenceException {
		try {	
			if (dialogueVo == null) 
				dialogueVo = new DialogueVo();
			
			if(!(filterIn instanceof CostProjectionReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type CostProjectionReportFilter.");
			}
				
			filter = (CostProjectionReportFilter) filterIn;
			dao = (CostProjectionDao) super.context.getBean("costProjectionDao");
				
			mainReportDataList = new ArrayList<CostProjectionReportData>();
			costProjectionReportData = new CostProjectionReportData();
			costProjectionReportData.setReportName(getReportName());
			costProjectionReportData.setIncidentId(filter.getIncidentId());
			costProjectionReportData.setIncidentGroupId(filter.getIncidentGroupId());
			costProjectionReportData.setIncidentName(filter.incidentName);
			costProjectionReportData.setIncidentGroupName(filter.incidentGroupName);
			costProjectionReportData.setProjectionName(filter.projectionName);
			costProjectionReportData.setIncidentNumber(filter.incidentNumber);
			costProjectionReportData.setStartDate(filter.startDate);
			costProjectionReportData.setEndDate(filter.endDate);
			costProjectionReportData.setReportType(filter.selectedReportType);
			//abstract method
			setSubReportData();
			mainReportDataList.add(costProjectionReportData);
			
			IReport report;
			report = getReportObject();
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			report.addCustomField("reportType",filter.selectedReportType);
		
			List<String> reportsList = new ArrayList<String>();
			reportsList.add(createAndSaveReport(report));
			
			// add reportsList to dialogueVo
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("COST_PROJECTION_REPORT");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);
				      
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(reportsList);				
		} catch(PersistenceException e){
			super.handleException(e);
		} catch(ServiceException e){
			throw e;
		} catch(Exception e){
			super.handleException(e);
		}
			
		return dialogueVo;	
	}
	
	public abstract void setSubReportData() throws Exception;
	public abstract IReport getReportObject();
	public abstract String getReportName();	
}