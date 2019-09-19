package gov.nwcg.isuite.core.reports.generator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.SummaryForCurrentDayReport;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.data.CostReportSubReportData;
import gov.nwcg.isuite.core.reports.data.SummaryForCurrentDayReportData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;


public class SummaryForCurrentDayReportGeneratorImpl extends ReportGenerator2Impl {
	
	private GroupCategoryTotalReportFilter filter;
	private ReportCostDao reportCostDao;
	
	@Override
	public <E extends ReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException,
				PersistenceException {
		
		try {
			if (dialogueVo == null) 
				dialogueVo = new DialogueVo();
			
			if(!(filterIn instanceof GroupCategoryTotalReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type GroupCategoryTotalReportFilter.");
			}
				
			filter = (GroupCategoryTotalReportFilter) filterIn;
			
			filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
			filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
			
			filter.setGroupingName(filter.getSelectedReportGroup());
			
			Collection<Incident> incidents = new ArrayList<Incident>();
			IncidentGroup incidentGroup = null;
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
				incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			 
			reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
			Collection<SummaryForCurrentDayReportData> summaryForCurrentDayReportDataList = new ArrayList<SummaryForCurrentDayReportData>();
			//get sub report data by group
			Collection<CostReportSubReportData> summaryForCurrentDaySubReportData = reportCostDao.getSummaryForCurrentDayReportData(filter);
					
			for(Incident incident : incidents) {
				//get sub report data for each incident 
				Collection<CostReportSubReportData> subrdDate = getSummaryForCurrentDaySubReportDataByIncidentId(summaryForCurrentDaySubReportData,incident.getId());
				Collection<CostReportSubReportData> subrdGroup = getSummaryForCurrentDaySubReportDataByIncidentId(summaryForCurrentDaySubReportData,incident.getId());
				
				if((subrdDate == null || subrdDate.isEmpty()) && (subrdGroup == null || subrdGroup.isEmpty()))
					continue;
				
				SummaryForCurrentDayReportData summaryForCurrentDayReportData = new SummaryForCurrentDayReportData();

				summaryForCurrentDayReportData.setIncidentId(incident.getId());
				summaryForCurrentDayReportData.setIncidentName(incident.getIncidentName());
				summaryForCurrentDayReportData.setIncidentNumber(incident.getIncidentNumber());
				summaryForCurrentDayReportData.setGroupingName(filter.getGroupingName());
				summaryForCurrentDayReportData.setStartDate(filter.getStartDate());
				
				if(filter.isIncidentGroup()) {
					summaryForCurrentDayReportData.setIncidentGroupId(filter.getIncidentGroupId());
					summaryForCurrentDayReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
				}
				
				
				summaryForCurrentDayReportData.setSubReportDataByGroup(subrdGroup);
				summaryForCurrentDayReportData.setSubReportDataByDate(subrdDate);
				summaryForCurrentDayReportData.setGroupingNameList(filter.getAdditionalFilterString());
				summaryForCurrentDayReportDataList.add(summaryForCurrentDayReportData);
			}
			
			if(summaryForCurrentDayReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Summary For Current Day Report"));
				return dialogueVo;
			}
			
			// Instantiate the report controller object.
			IReport report = new SummaryForCurrentDayReport(summaryForCurrentDayReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			
			List<String> reportsList = new ArrayList<String>();
			reportsList.add(createAndSaveReport(report));
			
			// add reportsList to dialogueVo
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("SUMMARY_BY_RESOURCE_REPORT");
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
	
	private Collection<CostReportSubReportData> getSummaryForCurrentDaySubReportDataByIncidentId(
			Collection<CostReportSubReportData> data, final Long id) throws Exception{
		
		if(data == null || data.isEmpty())
			return null;
		
		try {
			List<CostReportSubReportData> list = new ArrayList<CostReportSubReportData>(data);
       
			final Long incidentId = id;
       
			CollectionUtils.filter(list, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CostReportSubReportData data = (CostReportSubReportData) object;
					boolean t = incidentId.equals(data.getIncidentId());
					return incidentId.equals(data.getIncidentId());
				}
			});
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}

//report.addCustomField("SUBREPORT_DIR", "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reports\\");
//ReportGenerator2Impl::return "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reportsOutput\\"+ fileName;