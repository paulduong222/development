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

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.GroupCategorySummaryReport;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.data.CostReportSubReportData;
import gov.nwcg.isuite.core.reports.data.GroupCategorySummaryReportData;
import gov.nwcg.isuite.core.reports.data.GroupCategorySummarySubReportByDateData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;


public class GroupCategorySummaryReportGeneratorImpl extends ReportGenerator2Impl {
	
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
			Collection<GroupCategorySummaryReportData> groupCategorySummaryReportDataList = new ArrayList<GroupCategorySummaryReportData>();
			//a list of data source for grouping crosstab sub report 
			Collection<CostReportSubReportData> groupCategorySummarySubReportDataByGroup = reportCostDao.getGroupCategorySummarySubReportData(filter);
			//a list of data source for date crosstab sub report 
			filter.setReportGroupForQuery("Date");
			Collection<CostReportSubReportData> groupCategorySummarySubReportDataByDate = reportCostDao.getGroupCategorySummarySubReportData(filter);
			//get sub chart report data
			Collection<CostReportChartReportData> groupCategorySummarySubChartReportData = new ArrayList<CostReportChartReportData>(); 
			
			if(!filter.isReportOnly())
				groupCategorySummarySubChartReportData = reportCostDao.getGroupCategorySummarySubReportChartData(filter);
				
			//get sub report data
			for(Incident incident : incidents) {
				//get sub report data for each incident 
				Collection<GroupCategorySummarySubReportByDateData> subrdByDate = getGroupCatSummarySubReportDataByDate(groupCategorySummarySubReportDataByDate,incident.getId());
				Collection<CostReportSubReportData> subrdByGroup = getGroupCatSummarySubReportDataByGroup(groupCategorySummarySubReportDataByGroup,incident.getId());
				
				//get sub chart report data for each incident 
				Collection<CostReportChartReportData> subrcd = getGroupCatSummarySubChartReportData(groupCategorySummarySubChartReportData,incident.getId());
					
				GroupCategorySummaryReportData groupCategorySummaryReportData = new GroupCategorySummaryReportData();

				if(filter.isReportOnly() && (null != subrdByDate && subrdByDate.size() > 0) && (null != subrdByGroup && subrdByGroup.size() > 0)) { 
					groupCategorySummaryReportData.setSubReportDataByDate(subrdByDate);
					groupCategorySummaryReportData.setSubReportDataByGroup(subrdByGroup);
				}
				else if(filter.isGraphOnly() && (null != subrcd && subrcd.size() > 0))
					groupCategorySummaryReportData.setSubReportChartData(subrcd);
				else if(filter.isReportAndGraph() && (null != subrdByDate && subrdByDate.size() > 0) && (null != subrdByGroup && subrdByGroup.size() > 0)
						&& (null != subrcd && subrcd.size() > 0)) {
						groupCategorySummaryReportData.setSubReportDataByDate(subrdByDate);
						groupCategorySummaryReportData.setSubReportDataByGroup(subrdByGroup);
						groupCategorySummaryReportData.setSubReportChartData(subrcd);
				}
				else
					continue;
					
				groupCategorySummaryReportData.setIncidentId(incident.getId());
				groupCategorySummaryReportData.setIncidentName(incident.getIncidentName());
				groupCategorySummaryReportData.setIncidentNumber(incident.getIncidentNumber());
				groupCategorySummaryReportData.setGroupingName(filter.getGroupingName());
				groupCategorySummaryReportData.setStartDate(filter.getStartDate());
				groupCategorySummaryReportData.setEndDate(filter.getEndDate());
				if(filter.isIncidentGroup()) {
					groupCategorySummaryReportData.setIncidentGroupId(filter.getIncidentGroupId());
					groupCategorySummaryReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
				}
				groupCategorySummaryReportDataList.add(groupCategorySummaryReportData);	
			}
							
			if(groupCategorySummaryReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Group Category Summary Report"));
				return dialogueVo;
			}
						
			// Instantiate the report controller object.
			IReport report = new GroupCategorySummaryReport(groupCategorySummaryReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			if (filter.isDateRangeSelected) {
				   report.addCustomField("reportDateRange", "(" + df.format(filter.getStartDate()) + " - " + df.format(filter.getEndDate()) + ")");
				   //report.addCustomField("reportDateRange", "(" + filter.getStartDateVo().getDateString() + " - " + filter.getEndDateVo().getDateString() + ")");
			}
			else
				   report.addCustomField("reportDateRange", "");
			
			List<String> reportsList = new ArrayList<String>();
			reportsList.add(createAndSaveReport(report));
			
			// add reportsList to dialogueVo
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GROUP_CATEGORY_SUMMARY_REPORT");
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
	
	private Collection<GroupCategorySummarySubReportByDateData> getGroupCatSummarySubReportDataByDate(
			Collection<CostReportSubReportData> data, final Long id) throws Exception{
	
		if(data == null || data.isEmpty())
			return null;
	
		try {
			List<CostReportSubReportData> listSubSub = new ArrayList<CostReportSubReportData>(data);
			List<GroupCategorySummarySubReportByDateData> listSub = new ArrayList<GroupCategorySummarySubReportByDateData>();
   
			final Long incidentId = id;
			List<String> filterList = getFitlerList(data,id);
            
			for(String s : filterList) {
			
				List<CostReportSubReportData> newlistSubSub = new ArrayList<CostReportSubReportData>(listSubSub);
				final String groupingName = s;
			
				CollectionUtils.filter(newlistSubSub, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						CostReportSubReportData data = (CostReportSubReportData) object;
						//for if the groupby is payment agency or cost group, these two are not required fields 
						if(groupingName == null && data.getGroupBy()==null)
							return true;
						else if(groupingName == null && data.getGroupBy()!=null)
							return false;
						else {
							return (incidentId.equals(data.getIncidentId()) &&
									groupingName.equals(data.getGroupBy()));
						}
					}
				});
		
				GroupCategorySummarySubReportByDateData subReportData = new GroupCategorySummarySubReportByDateData();
				subReportData.setGroupByName(groupingName);
				subReportData.setSubReportDataByDate(newlistSubSub);
				listSub.add(subReportData);
			}
		
//			Collections.sort(listSub, new Comparator<GroupCategorySummarySubReportByDateData>() {
//				public int compare(GroupCategorySummarySubReportByDateData o1, GroupCategorySummarySubReportByDateData o2) {
//					return o1.getGroupByName().compareTo(o2.getGroupByName());
//				}
//			});

			return listSub;
		} catch (Exception e) {
			throw e;
		}	
	}
	
	private Collection<CostReportSubReportData> getGroupCatSummarySubReportDataByGroup(
			Collection<CostReportSubReportData> data, final Long id)  throws Exception{
		
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
			
			//Collections.sort(resourceDateDataList, ResourceDateData.COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST);
			
			return list;
		} catch (Exception e) {
			throw e;
		}	
	}
	
	private Collection<CostReportChartReportData> getGroupCatSummarySubChartReportData(
			Collection<CostReportChartReportData> data, final Long id) throws Exception {
		
		if(data == null || data.isEmpty())
			return null;
		
		try {
			// copy scratchReportTimeData to a new List. it is used by CollectionUtils.filter. 
			List<CostReportChartReportData> list = new ArrayList<CostReportChartReportData>(data);
       
			final Long incidentId = id;
       
			CollectionUtils.filter(list, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CostReportChartReportData data = (CostReportChartReportData) object;
					return incidentId.equals(data.getIncidentId());
				}
			});
			
//			Collections.sort(list, new Comparator<GroupCategorySummarySubReportChartData>() {
//					public int compare(GroupCategorySummarySubReportChartData o1, GroupCategorySummarySubReportChartData o2) {
//						return o1.getGroupName().compareTo(o2.getGroupName());
//					}
//			});
					
			//Collections.sort(resourceDateDataList, ResourceDateData.COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST);
			
			//totalAmount in %
			FormatTotalAmount(list);
			
			return list;
		} catch (Exception e) {
			throw e;
		}	
		
	}
	
	private List<String> getFitlerList(Collection<CostReportSubReportData> data, Long id){
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
		
		Set<String> groupBySet = new HashSet<String> ();
		for(CostReportSubReportData s : list) {
			//if(s.getGroupBy() != null && !s.getGroupBy().isEmpty())
				groupBySet.add(s.getGroupBy());
		}
		
		List<String> groupBylist = new ArrayList<String>();
		groupBylist.addAll(groupBySet);
		
		return groupBylist;
	}
}

