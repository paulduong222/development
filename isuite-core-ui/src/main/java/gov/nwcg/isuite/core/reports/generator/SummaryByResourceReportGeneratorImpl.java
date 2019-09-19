package gov.nwcg.isuite.core.reports.generator;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.SummaryByResourceReport;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.data.CostReportSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceDateData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceSubReportByDateData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceSubReportData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;


public class SummaryByResourceReportGeneratorImpl extends ReportGenerator2Impl {
	
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
			
			filter.setGroupingName(filter.getSelectedReportGroup()); //set report grouping name 
			filter.setReportGroupForQuery(filter.getSelectedReportGroup());  //set reportGroupForQuery
			
			
			//get incidents for incident group 
			Collection<Incident> incidents = new ArrayList<Incident>();
			IncidentGroup incidentGroup = null;
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
				incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
			reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
			//a list of data source for master report  
			Collection<SummaryByResourceReportData> summaryByResourceReportDataList = new ArrayList<SummaryByResourceReportData>();
			//a list of data source for grouping crosstab sub report 
			Collection<SummaryByResourceSubReportData> summaryByResourceSubDataByGroup = reportCostDao.getSummaryByResourceReportData(filter);
			//a list of data source for date crosstab sub report  
			filter.setReportGroupForQuery("Date");  
			Collection<SummaryByResourceSubReportData> summaryByResourceSubDataByDate = reportCostDao.getSummaryByResourceReportData(filter);	
			
			for(Incident incident : incidents) {
				//get sub report data for each incident 
				Collection<SummaryByResourceSubReportByDateData> subrdByDate = getSummaryByResourceSubDataByDate(summaryByResourceSubDataByDate,incident.getId());
				Collection<SummaryByResourceSubReportData> subrdByGroup = getSummaryByResourceSubDataByGroup(summaryByResourceSubDataByGroup,incident.getId());
				
				if((subrdByDate == null || subrdByDate.isEmpty()) && (subrdByGroup == null || subrdByGroup.isEmpty()))
					continue;
				
				//master report data object
				SummaryByResourceReportData summaryByResourceReportData = new SummaryByResourceReportData();

				summaryByResourceReportData.setIncidentId(incident.getId());
				summaryByResourceReportData.setIncidentName(incident.getIncidentName());
				summaryByResourceReportData.setIncidentNumber(incident.getIncidentNumber());
				summaryByResourceReportData.setGroupingName(filter.getGroupingName());
				summaryByResourceReportData.setStartDate(filter.getStartDate());
				summaryByResourceReportData.setEndDate(filter.getEndDate());
				if(filter.isIncidentGroup()) {
					summaryByResourceReportData.setIncidentGroupId(filter.getIncidentGroupId());
					summaryByResourceReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
				}
				
				if(filter.isNonOverhead){
					summaryByResourceReportData.setIsNonOH(true);
				} else {
					summaryByResourceReportData.setIsNonOH(false);
				}
				
				summaryByResourceReportData.setSubReportDataByGroup(subrdByGroup);
				summaryByResourceReportData.setSubReportDataByDate(subrdByDate);
				summaryByResourceReportDataList.add(summaryByResourceReportData);
			}
			
			if(summaryByResourceReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Summary By Resource Report"));
				return dialogueVo;
			}
	
			// Instantiate the report controller object.
			IReport report = new SummaryByResourceReport(summaryByResourceReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			//report.addCustomField("SUBREPORT_DIR", "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reports\\");
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			if (filter.isDateRangeSelected)
				   report.addCustomField("reportDateRange", "(" + df.format(filter.getStartDate()) + " - " + df.format(filter.getEndDate()) + ")");
				else
				   report.addCustomField("reportDateRange", "");
			
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
	
	private Collection<SummaryByResourceSubReportByDateData> getSummaryByResourceSubDataByDate(
			Collection<SummaryByResourceSubReportData> data, final Long id) throws Exception{
		
		if(data == null || data.isEmpty())
			return null;
		
		try {
			List<SummaryByResourceSubReportData> listSubSub = new ArrayList<SummaryByResourceSubReportData>(data);
			List<SummaryByResourceSubReportByDateData> listSub = new ArrayList<SummaryByResourceSubReportByDateData>();
       
			final Long incidentId = id;
			List<String> filterList = getFitlerList(data,id);
	            
			for(String s : filterList) {
				
				List<SummaryByResourceSubReportData> newlistSubSub = new ArrayList<SummaryByResourceSubReportData>(listSubSub);
				final String groupingName = s;
				
				CollectionUtils.filter(newlistSubSub, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						SummaryByResourceSubReportData data = (SummaryByResourceSubReportData) object;
						
						//for if the groupby is payment agency or cost group, these two are not required fields 
						if(groupingName == null && data.getGroupBy()==null)
							return true;
						else if(groupingName == null && data.getGroupBy()!=null)
							return false;
						else {
							return (incidentId.equals(data.getIncidentId()) 
								&& groupingName.equals(data.getGroupBy()));
						}
					}
				});
			
				SummaryByResourceSubReportByDateData subReportData = new SummaryByResourceSubReportByDateData();
				subReportData.setGroupByName(groupingName);
				subReportData.setSubReportDataByDate(newlistSubSub);
				listSub.add(subReportData);
			}
			
			return listSub;
		} catch (Exception e) {
			throw e;
		}	
	}
	
	private Collection<SummaryByResourceSubReportData> getSummaryByResourceSubDataByGroup(
			Collection<SummaryByResourceSubReportData> data, final Long id) throws Exception{
		
		if(data == null || data.isEmpty())
			return null;
		
		try {
			List<SummaryByResourceSubReportData> list = new ArrayList<SummaryByResourceSubReportData>(data);
       
			final Long incidentId = id;
       
			CollectionUtils.filter(list, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					SummaryByResourceSubReportData data = (SummaryByResourceSubReportData) object;
					boolean t = incidentId.equals(data.getIncidentId());
					return incidentId.equals(data.getIncidentId()); 
				}
			});
						
			return list;
		} catch (Exception e) {
			throw e;
		}	
	}
	
	private List<String> getFitlerList(Collection<SummaryByResourceSubReportData> data, Long id){
		List<SummaryByResourceSubReportData> list = new ArrayList<SummaryByResourceSubReportData>(data);
	       
		final Long incidentId = id;
   
		CollectionUtils.filter(list, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				SummaryByResourceSubReportData data = (SummaryByResourceSubReportData) object;
				boolean t = incidentId.equals(data.getIncidentId());
				return incidentId.equals(data.getIncidentId());
			}
		});
		
		Set<String> groupBySet = new HashSet<String> ();
		for(CostReportSubReportData s : list) {
			//if(s.getGroupBy() != null && !s.getGroupBy().isEmpty())
				groupBySet.add(s.getGroupBy());
		}
		
		List<String> groupBylist = new ArrayList<String>();
		groupBylist.addAll(groupBySet);
		
		return groupBylist;
	}
	
	public static final Comparator<String> COMPARATOR_STRING_IGNORE_CASE_NULL_FIRST = new Comparator<String>() {
		public int compare(String s1, String s2) {
			return compareStr(s1, s2);
	    }
	};
	    
	private static int compareStr(String o1, String o2) {
	    	if (o1 == null && o2 == null) {
	    		return 0;
	    	}

	    	if (o1 == null && o2 != null) {
	    		return -1;
	    	}

	    	if (o1 != null && o2 == null) {
	    		return 1;
	    	}
	    	return o1.compareTo(o2);
	    }
	
}

//@Override
//public <E extends ReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException,
//			PersistenceException {
//	
//	try {
//		if (dialogueVo == null) 
//			dialogueVo = new DialogueVo();
//		
//		if(!(filterIn instanceof GroupCategoryTotalReportFilter)){
//			throw new Exception("Invalid Filter Type. Expecting Filter to be of type GroupCategoryTotalReportFilter.");
//		}
//			
//		filter = (GroupCategoryTotalReportFilter) filterIn;
//		reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
//		
//		Collection<SummaryByResourceReportData> summaryByResourceReportDataList = new ArrayList<SummaryByResourceReportData>();
//		Collection<Incident> incidents = new ArrayList<Incident>();
//		IncidentGroup incidentGroup = null;
//		
//		if(filter.isIncidentGroup()) {
//			incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
//			incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
//		}
//		else 
//			incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
//		
//		filter.setGroupingName(filter.selectedReportGroup);
//		
//		//get sub report data by group
//		Collection<SummaryByResourceSubReportData> summaryByResourceSubDateByGroup = reportCostDao.getSummaryByResourceReportData(filter);
//				//incident.getId(),incident.getEventType().getEventTypeCode());
//		
//		//get sub report data by date the top report
//		filter.setSelectedReportGroup("Date");
//		Collection<SummaryByResourceSubReportData> summaryByResourceSubDateByDate = reportCostDao.getSummaryByResourceReportData(filter);	
//		
//		for(Incident incident : incidents) {
//			//get sub report data for each incident 
//			Collection<SummaryByResourceSubReportData> subrdByDate = getSummaryByResourceSubDateByIncidentId(summaryByResourceSubDateByDate,incident.getId(),incident.getEventTypeId());
//			Collection<SummaryByResourceSubReportData> subrdByGroup = getSummaryByResourceSubDateByIncidentId(summaryByResourceSubDateByGroup,incident.getId(),incident.getEventTypeId());
//			
//			SummaryByResourceReportData summaryByResourceReportData = new SummaryByResourceReportData();
//
//			summaryByResourceReportData.setIncidentId(incident.getId());
//			summaryByResourceReportData.setIncidentName(incident.getIncidentName());
//			summaryByResourceReportData.setIncidentNumber(incident.getIncidentNumber());
//			summaryByResourceReportData.setGroupingName(filter.getGroupingName());
//			summaryByResourceReportData.setStartDate(filter.getStartDate());
//			summaryByResourceReportData.setEndDate(filter.getEndDate());
//			if(filter.isIncidentGroup()) {
//				summaryByResourceReportData.setIncidentGroupId(filter.getIncidentGroupId());
//				summaryByResourceReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
//			}
//			
//			if(filter.isNonoverhead()){
//				summaryByResourceReportData.setIsNonOH(true);
//			} else {
//				summaryByResourceReportData.setIsNonOH(false);
//			}
//			//summaryByResourceReportData.setSubReportDataByGroup(subrdByGroup);
//			summaryByResourceReportData.setSubReportDataByDate(subrdByDate);
//			summaryByResourceReportDataList.add(summaryByResourceReportData);
//		}
//		
//		// Instantiate the report controller object.
//		IReport report = new SummaryByResourceReport(summaryByResourceReportDataList);
//		report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
//		
//		List<String> reportsList = new ArrayList<String>();
//		reportsList.add(createAndSaveReport(report));
//		
//		// add reportsList to dialogueVo
//		CourseOfActionVo coa = new CourseOfActionVo();
//		coa.setCoaName("SUMMARY_BY_RESOURCE_REPORT");
//		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//		coa.setIsDialogueEnding(true);
//		dialogueVo.setCourseOfActionVo(coa);
//		dialogueVo.setResultObject(reportsList);
//
//	}catch(Exception e){
//		super.dialogueException(dialogueVo, e);
//	}
//
//	return dialogueVo;	
//}






//report.addCustomField("SUBREPORT_DIR", "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reports\\");
//ReportGenerator2Impl::return "C:\\workspaceISuite2012\\isuite-core\\Webroot\\reportsOutput\\"+ fileName;