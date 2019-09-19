package gov.nwcg.isuite.core.reports.generator;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.AircraftDetailReport;
import gov.nwcg.isuite.core.reports.SummaryByResourceReport;
import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.data.AircraftDetailReportData;
import gov.nwcg.isuite.core.reports.data.AircraftDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceSubReportData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;


public class AircraftDetailReportGeneratorImpl extends ReportGenerator2Impl {
	
	private AircraftDetailReportFilter filter;
	private ReportCostDao reportCostDao;
	
	@Override
	public <E extends ReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException {
		
		try {
			if (dialogueVo == null) 
				dialogueVo = new DialogueVo();
			
			if(!(filterIn instanceof AircraftDetailReportFilter)){
				throw new ServiceException("Invalid Filter Type. Expecting Filter to be of type AircraftDetailReportFilter.");
			}
				  
			filter = (AircraftDetailReportFilter) filterIn;
			
			filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
			filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
			
			reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
		
			Collection<AircraftDetailReportData> aircraftDetailReportDataList = new ArrayList<AircraftDetailReportData>();
			Collection<Incident> incidents = new ArrayList<Incident>();
			IncidentGroup incidentGroup = null;
			
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
				incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
					
			for(Incident incident : incidents) {
				Collection<AircraftDetailSubReportData> subrd = reportCostDao.getAircraftDetailSubReportData(filter,incident.getId());
				Collection<CostReportChartReportData> subrdc = reportCostDao.getAircraftDetailSubReportChartData(filter,incident.getId());
				FormatTotalAmount(subrdc);
				AircraftDetailReportData aircraftDetailReportData = new AircraftDetailReportData();
				
				if(filter.isReportOnly && (null != subrd && subrd.size() > 0)) 
					aircraftDetailReportData.setSubReportData(subrd);
				else if(filter.isGraphOnly && (null != subrdc && subrdc.size() > 0))
					aircraftDetailReportData.setSubReportChartData(subrdc);
				else if(filter.isReportAndGraph && (null != subrd && subrd.size() > 0) 
						&& (null != subrdc && subrdc.size() > 0)) {
					aircraftDetailReportData.setSubReportData(subrd);
					aircraftDetailReportData.setSubReportChartData(subrdc);
				}
				else
					continue;
				
				aircraftDetailReportData.setIncidentId(incident.getId());
				aircraftDetailReportData.setIncidentName(incident.getIncidentName());
				aircraftDetailReportData.setIncidentNumber(incident.getIncidentNumber());
				if(filter.isIncidentGroup()) {
					aircraftDetailReportData.setIncidentGroupId(filter.getIncidentGroupId());
					aircraftDetailReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
				}
				aircraftDetailReportDataList.add(aircraftDetailReportData);
			}
						
			if(aircraftDetailReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Aircraft Detailed Report"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new AircraftDetailReport(aircraftDetailReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			if (filter.isDateRangeSelected)
				   report.addCustomField("reportDateRange", "(" + df.format(filter.getStartDate()) + " - " + df.format(filter.getEndDate()) + ")");
				else
				   report.addCustomField("reportDateRange", "");
			
			List<String> reportsList = new ArrayList<String>();
			reportsList.add(createAndSaveReport(report));
			
			// add reportsList to dialogueVo
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("AIRCRAFE_DETAIL_REPORT");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);
				      
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(reportsList);	
			
		}catch(PersistenceException e){
			super.handleException(e);
		}catch(ServiceException e){
			throw e;
		}
		catch(Exception e){
			super.handleException(e);
		}
		
		return dialogueVo;
	}
}

//report.addCustomField("SUBREPORT_DIR", "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reports\\");
//ReportGenerator2Impl::return "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reportsOutput\\"+ fileName;