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

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.GroupCategoryTotalReport;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.data.CostReportSubReportData;
import gov.nwcg.isuite.core.reports.data.GroupCategoryTotalReportData;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DecimalUtil;


public class GroupCategoryTotalReportGeneratorImpl extends ReportGenerator2Impl {
	
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
			filter.setReportGroupForQuery(filter.getSelectedReportGroup()); 
			
			Collection<Incident> incidents = new ArrayList<Incident>();
			IncidentGroup incidentGroup = null;
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
				incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
			reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
			
			//the collection of master report
			Collection<GroupCategoryTotalReportData> groupCategoryTotalReportDataList = new ArrayList<GroupCategoryTotalReportData>();
			
			//get sub report data
			Collection<CostReportSubReportData> groupCategoryTotalSubReportData = reportCostDao.getGroupCategoryTotalSubReportData(filter);
					
			//get sub chart report data
			Collection<CostReportChartReportData> groupCategoryTotalSubChartReportData = new ArrayList<CostReportChartReportData>(); 
			if(!filter.isReportOnly())
				groupCategoryTotalSubChartReportData = reportCostDao.getGroupCategoryTotalSubReportChartData(filter);
			
			// for groupBy incident: groupby incident is didferent than other group by.
			// rule: if groupBy = incident and is incident group show total. 
			//       if groupBy = incident and is single incident not show total. 
			GroupCategoryTotalReportData groupByincidentData = new GroupCategoryTotalReportData();
			
			//build report data structure by incident
			//report structure:      main report ----> sub report 
			//report data structure: a list of incidents ----> incident data (a list)
			for(Incident incident : incidents) {
				
				//get sub report data for each incident 
				Collection<CostReportSubReportData> subrd = getGCTSubReportDataByIncidentId(groupCategoryTotalSubReportData,incident.getId(),incident.getEventTypeId(),incident.getIncidentNumber());			
				//get sub chart report data for each incident 
				Collection<CostReportChartReportData> subrcd = getGCTSubChartReportDataByIncident(groupCategoryTotalSubChartReportData,incident.getId(),incident.getEventTypeId());
				
				
				//groupCategoryTotalReportData contain each incident data 
				GroupCategoryTotalReportData groupCategoryTotalReportData = new GroupCategoryTotalReportData();
					
				if(filter.isReportOnly() && (null != subrd && subrd.size() > 0)) {
					groupCategoryTotalReportData.setSubReportData(subrd);
				}else if(filter.isGraphOnly() && (null != subrcd && subrcd.size() > 0)){
					groupCategoryTotalReportData.setSubReportChartData(subrcd);
				}else if(filter.isReportAndGraph() && (null != subrd && subrd.size() > 0) && (null != subrcd && subrcd.size() > 0)) {
						groupCategoryTotalReportData.setSubReportData(subrd);
						groupCategoryTotalReportData.setSubReportChartData(subrcd);
				}
				else
					continue;
				
				//groupBy is not "incident"
				if(!filter.selectedReportGroup.equals("Incident")) {
					if(filter.isIncidentGroup()) {
						groupCategoryTotalReportData.setIncidentGroupId(filter.getIncidentGroupId());
						groupCategoryTotalReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
					}
					
					groupCategoryTotalReportData.setIncidentId(incident.getId());
					groupCategoryTotalReportData.setIncidentName(incident.getIncidentName());
					groupCategoryTotalReportData.setIncidentNumber(incident.getIncidentNumber());
					groupCategoryTotalReportData.setGroupingName(filter.getSelectedReportGroup());
					groupCategoryTotalReportData.setStartDate(filter.getStartDate());
					groupCategoryTotalReportData.setEndDate(filter.getEndDate());
					
					// the groupCategoryTotalReportDataList only has each groupCategoryTotalReportData instant for each incident
					//groupCategoryTotalReportDataList.add(groupCategoryTotalReportData);	
					Collection<CostReportChartReportData> combinedData=buildCombinedChartData(groupCategoryTotalSubChartReportData);
					groupByincidentData.setSubReportChartData(combinedData);
				}
				else {
					if(filter.isIncidentGroup()) {
						groupByincidentData.setIncidentGroupId(filter.getIncidentGroupId());
						groupByincidentData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
						groupByincidentData.setGroupingName("All Incidents");
						groupByincidentData.setIncidentName("Multiple");
						groupByincidentData.setIncidentNumber("Multiple");
					}
					else {
						groupByincidentData.setGroupingName(filter.getSelectedReportGroup());
						groupByincidentData.setIncidentId(incident.getId());
						groupByincidentData.setIncidentName(incident.getIncidentName());
						groupByincidentData.setIncidentNumber(incident.getIncidentNumber());
					}
					groupByincidentData.setStartDate(filter.getStartDate());
					groupByincidentData.setEndDate(filter.getEndDate());
					
					//all the incidents data add to the same GroupCategoryTotalReportData instant
					groupByincidentData.addAllIncidentsSubReportData(groupCategoryTotalReportData.getSubReportData());
					if(groupCategoryTotalReportData.getSubReportChartData() != null && 
							!((List<CostReportChartReportData>)groupCategoryTotalReportData.getSubReportChartData()).isEmpty()){
						Collection<CostReportChartReportData> combinedData=buildCombinedChartData(groupCategoryTotalSubChartReportData);
						groupByincidentData.setSubReportChartData(combinedData);
						//groupByincidentData.setSubReportChartData(groupCategoryTotalSubChartReportData);
						//groupByincidentData.addSubReportChartData(groupCategoryTotalReportData.getSubReportChartData());
					}						
				}
			}
			
			// the groupCategoryTotalReportDataList only has one groupCategoryTotalReportData instant
			if(filter.selectedReportGroup.equals("Incident"))
				groupCategoryTotalReportDataList.add(groupByincidentData);
			
			if(groupCategoryTotalReportDataList.size() < 1){
				dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Group Category Total Report"));
				return dialogueVo;
			}
									
			// Instantiate the report controller object.
			IReport report = new GroupCategoryTotalReport(groupCategoryTotalReportDataList);
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
			coa.setCoaName("GROUP_CATEGORY_TOTAL_REPORT");
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

	private Collection<CostReportChartReportData> buildCombinedChartData(Collection<CostReportChartReportData> data) {
		Collection<CostReportChartReportData> newData=new ArrayList<CostReportChartReportData>();
		Double allCategoryTotal=new Double(0.0);
		Collection<String> categories=new ArrayList<String>();
		// get the categories
		for(CostReportChartReportData d : data){
			allCategoryTotal=allCategoryTotal+d.getTotalAmount();
			if(!categories.contains(d.getGroupName()))
				categories.add(d.getGroupName());
		}

		for(String s : categories){
			Double total=new Double(0.0);
			
			for(CostReportChartReportData d : data){
				if(d.getGroupName().equals(s)){
					total=total+d.getTotalAmount();
				}
			}
			CostReportChartReportData newGroupRecord=new CostReportChartReportData();
			newGroupRecord.setGroupName(s);
			newGroupRecord.setTotalAmount(total);
			newGroupRecord.setTotalAmounInPercentage("0.0");
			Double pct=total / allCategoryTotal;
			double rpct=DecimalUtil.formatAsRoundedDown(pct.doubleValue(), 4);
			rpct=rpct*100;
			newGroupRecord.setTotalAmounInPercentage(String.valueOf(rpct) +"%");
			newData.add(newGroupRecord);
		}
		
		return newData;
	}
	
//	GroupCategoryTotalReportData groupCategoryTotalReportData2 = new GroupCategoryTotalReportData();
//	
//	if(filter.isIncidentGroup() && filter.selectedReportGroup.equals("Incident")) {
//		groupCategoryTotalReportData2.setIncidentGroupId(filter.getIncidentGroupId());
//		groupCategoryTotalReportData2.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
//		groupCategoryTotalReportData2.setAllIncidentsSubReportData(groupCategoryTotalAllIncidentsSubReportData);
//		groupCategoryTotalReportData2.setGroupingName("All Incidents");
//		groupCategoryTotalReportData2.setIncidentName("Multiple");
//		groupCategoryTotalReportData2.setIncidentNumber("Multiple");
//		groupCategoryTotalReportDataList.add(groupCategoryTotalReportData2);
//	}
//	groupCategoryTotalReportData.setIncidentId(incident.getId());
//	groupCategoryTotalReportData.setIncidentName(incident.getIncidentName());
//	groupCategoryTotalReportData.setIncidentNumber(incident.getIncidentNumber());
//	if(filter.isIncidentGroup() && filter.selectedReportGroup.equals("Incident"))
//		groupCategoryTotalReportData.setGroupingName("All Incidents");
//	else
//		groupCategoryTotalReportData.setStartDate(filter.getStartDate());
//	
//	groupCategoryTotalReportData.setEndDate(filter.getEndDate());
//	if(filter.isIncidentGroup()) {
//		groupCategoryTotalReportData.setIncidentGroupId(filter.getIncidentGroupId());
//		groupCategoryTotalReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
//	}	
	
	private Collection<CostReportSubReportData> getGCTSubReportDataByIncidentId(
			Collection<CostReportSubReportData> data, final Long id, Long eventTypeId, String incidentNumber) throws Exception {
		
		if(data == null || data.isEmpty())
			return null;
		
		try {
			List<CostReportSubReportData> list = new ArrayList<CostReportSubReportData>(data);
       
			final Long incidentId = id;
			final String thisIncidentNumber = incidentNumber;
       
			CollectionUtils.filter(list, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CostReportSubReportData data = (CostReportSubReportData) object;
					boolean t = incidentId.equals(data.getIncidentId());
					if(t && filter.selectedReportGroup.equals("Incident"))
						data.setGroupBy(thisIncidentNumber);
					return incidentId.equals(data.getIncidentId());
				}
			});
			
			Collections.sort(list, new Comparator<CostReportSubReportData>() {
				public int compare(CostReportSubReportData o1, CostReportSubReportData o2) {
					return o1.getDirectIndirectName().compareTo(o2.getDirectIndirectName());
				}
		    });
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	private Collection<CostReportChartReportData> getGCTSubChartReportDataByIncident(
			Collection<CostReportChartReportData> data, final Long id,Long eventTypeId) throws Exception{
		
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
			
			Collections.sort(list, new Comparator<CostReportChartReportData>() {
					public int compare(CostReportChartReportData o1, CostReportChartReportData o2) {
						return o1.getGroupName().compareTo(o2.getGroupName());
					}
			});
			
			FormatTotalAmount(list);
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}