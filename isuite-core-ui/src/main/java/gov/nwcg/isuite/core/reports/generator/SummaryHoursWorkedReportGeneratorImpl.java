package gov.nwcg.isuite.core.reports.generator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import gov.nwcg.isuite.core.domain.impl.ScratchReportTimeImpl;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.SummaryHoursWorkedReport;
import gov.nwcg.isuite.core.reports.data.SummaryOfHoursWorkedReportData;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.data.GroupDateData;
import gov.nwcg.isuite.core.reports.data.ResourceDateData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;


public class SummaryHoursWorkedReportGeneratorImpl extends ReportGeneratorImpl {
	
	private SummaryHoursWorkedReportFilter filter;
	private ReportTimeDao reportTimeDao;
	private List<ResourceDateData> scratchReportTimeData;
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException,
				PersistenceException {
		try {	
			if (dialogueVo == null) 
				dialogueVo = new DialogueVo();
			
			if(!(filterIn instanceof SummaryHoursWorkedReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type SummaryHoursWorkedReportFilter.");
			}
				
			filter = (SummaryHoursWorkedReportFilter) filterIn;
			reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			reportTimeDao.setLoggingInterceptor(super.logger);
			
			initReportFilter(filter);
				
			// 1. call sp to insert report data to isw_scratch_time_report table
			reportTimeDao.insertSummaryHoursWorkedReportDataToScratchTable(filter);
	
			// 2. get report data from isw_scratch_time_report table
			Collection<SummaryOfHoursWorkedReportData> reportDataList = getReportData(filter);
				
			if(reportDataList == null || reportDataList.size() < 1){
				//dialogueVo.setCourseOfActionVo(super.("Summary Of Hours Worked Report","text.summaryHoursWorkedReportInstructions"));
				return noDataMessage("Summary Of Hours Worked Report.", dialogueVo);
			}
				
			IReport report = new SummaryHoursWorkedReport(reportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
		
			//List<String> reportsList = new ArrayList<String>();
			//reportsList.add(createAndSaveReport(report));
			String pdfURL = null;
			pdfURL = createAndSaveReport(report);
			
			// add reportsList to dialogueVo
			//CourseOfActionVo coa = new CourseOfActionVo();
			//coa.setCoaName("SUMMARY_HOURS_WORK_REPORT");
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
			if(filter.getTransactionId()!= null) {
				// 3.Delete rows from the scratch table
				reportTimeDao.deleteReportTimeScratchData(filter.getTransactionId());
			}
		}

		return dialogueVo;	
	}
	
	private void initReportFilter(SummaryHoursWorkedReportFilter filter) throws Exception{
		try {
			// Set 0 values in the filter to null for calling database stored procedures
			if(filter.getIncidentGroupId() == 0) 
				filter.setIncidentGroupId(null);
			else 
				filter.setIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()));
    	
			if(filter.getIncidentId() == 0) 
				filter.setIncidentId(null);
			else
				filter.getIncidentIds().add(filter.getIncidentId());
    	
			if(filter.getResourceId() == 0) 
				filter.setResourceId(null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<SummaryOfHoursWorkedReportData> getReportData(SummaryHoursWorkedReportFilter filter) throws Exception {
		try {
			// the list of master report data source
			List<SummaryOfHoursWorkedReportData> summaryHoursWorkedReportDataList = new ArrayList<SummaryOfHoursWorkedReportData>();
			
			// get data from isw_scratch_report_time table
			scratchReportTimeData = reportTimeDao.getSummaryHoursWorkedReportData(filter);
			if(scratchReportTimeData == null || scratchReportTimeData.size() == 0) {
				return null;
			}
			
			//remove the dup rows
			Map<String,ResourceDateData> tempMap = new HashMap<String,ResourceDateData>();
			for(ResourceDateData data:scratchReportTimeData) {
				String key = data.getRequestNumber()+data.getWeek().toString();
				if(tempMap.containsKey(key)) 
					tempMap.get(key).setTotal(tempMap.get(key).getTotal()+data.getTotal());
				else
					tempMap.put(key, data);
			}
			scratchReportTimeData.clear();
			Iterator it = tempMap.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry pairs = (Map.Entry)it.next();
			    scratchReportTimeData.add((ResourceDateData)pairs.getValue());   
			}
			
			//build report data by incident 
			for(Long id : filter.getIncidentIds()) {
				//master report data source
				SummaryOfHoursWorkedReportData summaryHoursWorkedReportData = new SummaryOfHoursWorkedReportData();
				
				//set incident data
				ResourceDateData resourceDateData = getIncidentData(id);
				if(resourceDateData != null) {
					summaryHoursWorkedReportData.setIncidentId(resourceDateData.getIncidentId());
                	summaryHoursWorkedReportData.setIncidentName(resourceDateData.getIncidentName());
                	summaryHoursWorkedReportData.setIncidentNumber(resourceDateData.getIncidentNumber());
                	summaryHoursWorkedReportData.setStartDate(filter.getStartDateString());
                	summaryHoursWorkedReportData.setEndDate(filter.getEndDateString());
				}
				else continue;  // if no incident found then skip to next incident
				
				//sub report data source
				List<GroupDateData> groupDateDataList = new ArrayList<GroupDateData>();
				
				//build sub report data by week
				int counter = filter.getNumOfWeeks();
				for(int i=0; i < counter; i++) {
					GroupDateData groupDateData = new GroupDateData();
					
					//set sub report column header  
					groupDateData.setDays(filter.getDaysByWeek(i),filter.getEndDate());
					
					//build sub sub report data by incident and week
					List<ResourceDateData> resourceDateDataList = getResourceDateDataByIncidentIdAndWeek(id,i+1);	                    
				    groupDateData.setResourceDateDataList(resourceDateDataList);   
				    groupDateDataList.add(groupDateData);
				}
				
				summaryHoursWorkedReportData.setGroupDateDataList(groupDateDataList);  
				summaryHoursWorkedReportDataList.add(summaryHoursWorkedReportData);
			}
			
			return summaryHoursWorkedReportDataList;
		}
		catch (Exception e) {
			super.handleException(e);
		}
		
		return null; 
	}
	
	private List<ResourceDateData> getResourceDateDataByIncidentIdAndWeek(Long id, int week) throws Exception {
		try {
			// copy scratchReportTimeData to a new List. it is used by CollectionUtils.filter. 
			List<ResourceDateData> resourceDateDataList = new ArrayList<ResourceDateData>(scratchReportTimeData);
        
			final Long incidentId = id;
			final int numOfWeek = week;
        
			CollectionUtils.filter(resourceDateDataList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ResourceDateData data = (ResourceDateData) object;
					// removed data if it is not the incident and the week 
					return incidentId.equals(data.getIncidentId()) && (numOfWeek-data.getWeek()) == 0;
				}
			});
			
			if(filter.isSortByResourceName) {
				Collections.sort(resourceDateDataList, new Comparator<ResourceDateData>() {
					public int compare(ResourceDateData o1, ResourceDateData o2) {
						return o1.getFirstName().compareTo(o2.getFirstName());
					}
				});
			}
			else {
				Collections.sort(resourceDateDataList, new Comparator<ResourceDateData>() {
					public int compare(ResourceDateData o1, ResourceDateData o2) {
						return o1.getSortedRequestNumber().compareTo(o2.getSortedRequestNumber());
					}
				});
			}
			
			//Collections.sort(resourceDateDataList, ResourceDateData.COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST);
			
			return resourceDateDataList;
		} catch (Exception e) {
			super.handleException(e);
		}
		
        return null;  
	}
	
	private ResourceDateData getIncidentData(Long id) throws Exception {
		try {
			List<ResourceDateData> resourceDateDataList = new ArrayList<ResourceDateData>(scratchReportTimeData);
  
			final Long incidentId = id;
			ResourceDateData resourceDataDate = (ResourceDateData)CollectionUtils.find(resourceDateDataList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ResourceDateData data = (ResourceDateData) object;
					return incidentId.equals(data.getIncidentId());
				}
			});
			
			return resourceDataDate;
		} catch (Exception e) {
			super.handleException(e);
		}
		
        return null;  
	}
}