package gov.nwcg.isuite.core.reports.generator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import gov.nwcg.isuite.core.reports.CostProjectionCategoryReport;
import gov.nwcg.isuite.core.reports.CostProjectionDetailReport;
import gov.nwcg.isuite.core.reports.data.CostProjectionCategoryDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionTotalSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;

public class CostProjectionCategoryDetailReportGeneratorImpl extends CostProjectionReportGeneratorImpl{

	public void setSubReportData() throws Exception{
		try{		
			if(filter.selectedReportType.equals("Report Only")) {
				costProjectionReportData.setCategoryDetailSubReportData(getCostProjectionSubReportData());
				costProjectionReportData.setSubTotalReportData(getCostProjectionTotalSubReportData());
			}
			else if(filter.selectedReportType.equals("Graph Only")) { 
				costProjectionReportData.setSubChartReportData(getChartReportData());
			}
			else {
				costProjectionReportData.setCategoryDetailSubReportData(getCostProjectionSubReportData());
				costProjectionReportData.setSubTotalReportData(getCostProjectionTotalSubReportData());
				costProjectionReportData.setSubChartReportData(getChartReportData());
			}
		} catch(Exception e){
			throw e;
		}
	}
		
    public IReport getReportObject() {
    	if(filter.isByDayReport)
    		return new CostProjectionDetailReport(mainReportDataList);
    	else
    		return new CostProjectionCategoryReport(mainReportDataList);
	}
	
    public String getReportName() {
    	if(filter.isByDayReport)
    		return "Detail";
    	else
    		return "Category";
    }
    
	public Collection<CostProjectionCategoryDetailSubReportData> getCostProjectionSubReportData() throws Exception {
		Collection<CostProjectionCategoryDetailSubReportData> subreportDataList = new ArrayList<CostProjectionCategoryDetailSubReportData>();
		
		try {
			filter.isSuppourtingData = false;
			Collection<CostProjectionCategoryDetailSubReportData> subreportDataList1 = dao.getCostProjectionCategoryDetailReportData(filter);		
			//the first query get projection data and second query get OD, OS and SC data
			filter.isSuppourtingData = true;
			Collection<CostProjectionCategoryDetailSubReportData> subreportDataList2 = dao.getCostProjectionCategoryDetailReportData(filter);
			
			subreportDataList1.addAll(subreportDataList2);
			List<CostProjectionCategoryDetailSubReportData> list = (List<CostProjectionCategoryDetailSubReportData>)subreportDataList1;
			
			if(subreportDataList1 == null || subreportDataList1.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Projection Category/Detail Report Data"));
			}
						
			//build sub report data by week
			int counter = filter.getNumOfWeeks();
			int listCounter = list.size();
			for(int i=0; i < counter; i++) {
				for(int j=0; j < listCounter; j++) {
					if(list.get(j).getWeek() == i+1) 
						//set sub report column header 
						list.get(j).setDays(filter.getDaysByWeek(i),filter.getEndDate());
				}
			}
			
			for(int i=0; i < counter; i++) {
				final int week = i+1;
				List<CostProjectionCategoryDetailSubReportData> resourceDataDate = (List<CostProjectionCategoryDetailSubReportData>)CollectionUtils.select(list, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						CostProjectionCategoryDetailSubReportData data = (CostProjectionCategoryDetailSubReportData) object;
						return data.getWeek()==week;
					}
				});
				
				subreportDataList.addAll(resourceDataDate);
			}
			
			
		} catch(Exception e){
			super.handleException(e);
		}
		
		return subreportDataList;
	}
	
	private Collection<CostProjectionTotalSubReportData> getCostProjectionTotalSubReportData() throws Exception {
		Collection<CostProjectionTotalSubReportData> subreportDataList = null;
		
		try {
			filter.isSuppourtingData = true;
			subreportDataList = dao.getCostProjectionCategoryDetailTotalReportData(filter);
			//the first query get projection data and second query get OD, OS and SC data
			filter.isSuppourtingData = false;
			subreportDataList.addAll(dao.getCostProjectionCategoryDetailTotalReportData(filter));
			
			if(subreportDataList == null || subreportDataList.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Projection Category/Detail Report Data"));
			}
						
			filter.isSuppourtingData = false;
		} catch(Exception e){
			super.handleException(e);
		}
		
		return subreportDataList;
	}
	
	public Collection<CostReportChartReportData> getChartReportData() throws Exception {
		Collection<CostReportChartReportData> subReportData = null;
		
		try {
			filter.isSuppourtingData = true;
			subReportData = dao.getCostProjectionCategoryDetailChartReportData(filter);
			filter.isSuppourtingData = false;
			subReportData.addAll(dao.getCostProjectionCategoryDetailChartReportData(filter));
			
			
			if(subReportData == null || subReportData.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Projection Category/Detail Chart Report Data"));
			}
			
			Map<String,Double> totalMap = new HashMap<String, Double>();
			for(CostReportChartReportData data:subReportData){
				String groupName = data.getGroupName();
				if(totalMap.containsKey(groupName)) 
					totalMap.put(groupName, totalMap.get(groupName) + data.getTotalAmount());
				else
					totalMap.put(groupName, data.getTotalAmount());
			}
			
			for(CostReportChartReportData data:subReportData){
				String groupName = data.getGroupName();
				DecimalFormat df = new DecimalFormat("####0.00");//DecimalFormat formatter = new DecimalFormat("$###,###,###,###");
				data.setGroupName(groupName + "( $"+ df.format(totalMap.get(groupName)) +" )");
			}
			
			
		} catch(Exception e){
			super.handleException(e);
		}
		
		return subReportData;
	}
	
}