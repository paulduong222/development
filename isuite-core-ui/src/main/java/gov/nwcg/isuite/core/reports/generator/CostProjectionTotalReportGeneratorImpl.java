package gov.nwcg.isuite.core.reports.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import gov.nwcg.isuite.core.reports.CostProjectionTotalReport;
import gov.nwcg.isuite.core.reports.data.CostProjectionTotalSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;

public class CostProjectionTotalReportGeneratorImpl extends CostProjectionReportGeneratorImpl{
	private Collection<CostProjectionTotalSubReportData> subreportDataList;
	Collection<CostReportChartReportData> subReportDataForProjection; 
	Collection<CostReportChartReportData> subReportDataForTotalCost; 

	public void setSubReportData() throws Exception{
		try{
			subreportDataList = getCostProjectionTotalSubReportData();
		
			if(filter.selectedReportType.equals("Report Only")) 
				costProjectionReportData.setSubTotalReportData(subreportDataList);
			else if(filter.selectedReportType.equals("Graph Only")) { 
				getTotalChartReportData();
				FormatTotalAmount(subReportDataForProjection);
				FormatTotalAmount(subReportDataForTotalCost);
				costProjectionReportData.setSubChartReportData(subReportDataForProjection);	
				costProjectionReportData.setSubChartReportToDateData(subReportDataForTotalCost);
			}
			else {
				costProjectionReportData.setSubTotalReportData(subreportDataList);
				getTotalChartReportData();
				FormatTotalAmount(subReportDataForProjection);
				FormatTotalAmount(subReportDataForTotalCost);
				costProjectionReportData.setSubChartReportData(subReportDataForProjection);	
				costProjectionReportData.setSubChartReportToDateData(subReportDataForTotalCost);
			}
		} catch(Exception e){
			throw e;
		}
	}
	
    public IReport getReportObject() {
    	return new CostProjectionTotalReport(mainReportDataList);
	}
	
    public String getReportName() {
    	return "Total";
    }
    
	private Collection<CostProjectionTotalSubReportData> getCostProjectionTotalSubReportData() throws Exception {
		Collection<CostProjectionTotalSubReportData> subreportDataList = null;
		Collection<CostProjectionTotalSubReportData> subreportDataList2 = null;
		
		try {
			//subreportDataList = dao.getCostProjectionCategoryDetailTotalReportData(filter);
			filter.isSuppourtingData = false;
			subreportDataList = dao.getCostProjectionCategoryDetailTotalReportData(filter);
			
			//the first query get projection data and second query get OD, OS and SC data
			filter.isSuppourtingData = true;
			subreportDataList.addAll(dao.getCostProjectionCategoryDetailTotalReportData(filter));
			
			if(subreportDataList == null || subreportDataList.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Projection Category Report Data"));
			}
			
			subreportDataList2 = dao.getCostProjectionCostToDateTotalReportData(filter);
			if(subreportDataList2 == null || subreportDataList2.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Projection Total Report Data"));
			}
			
			
			for(CostProjectionTotalSubReportData d:subreportDataList) {
				for(CostProjectionTotalSubReportData dd:subreportDataList2) {
					if(d.getSubGroupCatDescription().equals(dd.getSubGroupCatDescription()))
						d.setCostToDate(dd.getCostToDate());
				}
			}
			
			Collections.sort((List<CostProjectionTotalSubReportData>)subreportDataList, new Comparator<CostProjectionTotalSubReportData>() {
				public int compare(CostProjectionTotalSubReportData o1, CostProjectionTotalSubReportData o2) {
					return o1.getSubGroupCatDescription().compareTo(o2.getSubGroupCatDescription());
				}
			});
			
			Collections.sort((List<CostProjectionTotalSubReportData>)subreportDataList, new Comparator<CostProjectionTotalSubReportData>() {
				public int compare(CostProjectionTotalSubReportData o1, CostProjectionTotalSubReportData o2) {
					return o1.getKindGroupDescription().compareTo(o2.getKindGroupDescription());
				}
			});
			
			Collections.sort((List<CostProjectionTotalSubReportData>)subreportDataList, new Comparator<CostProjectionTotalSubReportData>() {
				public int compare(CostProjectionTotalSubReportData o1, CostProjectionTotalSubReportData o2) {
					return o1.getDirectIndirectName().compareTo(o2.getDirectIndirectName());
				}
			});
			
			
		} catch(Exception e){
			super.handleException(e);
		}
		
		return subreportDataList;
	}
	
	public void getTotalChartReportData() {
		subReportDataForProjection = new ArrayList<CostReportChartReportData>();
		subReportDataForTotalCost = new ArrayList<CostReportChartReportData>();
		
		Map<String,CostReportChartReportData> map = new HashMap<String,CostReportChartReportData>();
		for(CostProjectionTotalSubReportData cost : subreportDataList) {
			if(!map.containsKey(cost.getKindGroupDescription())) {
				CostReportChartReportData data = new CostReportChartReportData();
				data.setGroupName(cost.getKindGroupDescription());
				data.setTotalAmount(cost.getProjectionCost());
				map.put(cost.getKindGroupDescription(), data);
				subReportDataForProjection.add(data);
			}
			else {
				Double d = map.get(cost.getKindGroupDescription()).getTotalAmount()+ cost.getProjectionCost();
				map.get(cost.getKindGroupDescription()).setTotalAmount(d);
			}
		}
		
		map.clear();
		for(CostProjectionTotalSubReportData cost : subreportDataList) {
			if(!map.containsKey(cost.getKindGroupDescription())) {
				CostReportChartReportData data = new CostReportChartReportData();
				data.setGroupName(cost.getKindGroupDescription());
				data.setTotalAmount(cost.getCostToDate()+cost.getProjectionCost());
				map.put(cost.getKindGroupDescription(), data);
				subReportDataForTotalCost.add(data);
			}
			else {
				Double d = map.get(cost.getKindGroupDescription()).getTotalAmount()+ cost.getCostToDate()+ cost.getProjectionCost();
				map.get(cost.getKindGroupDescription()).setTotalAmount(d);
			}
		}
	}
	
//	public Collection<CostReportChartReportData> getChartReportData() throws Exception {
//		Collection<CostReportChartReportData> subReportData = null;
//		
//		try {
//			//isSupportingData is used for sorting the OD,OS and Other Supporting
//			filter.isSuppourtingData = false;
//			subReportData = dao.getCostProjectionTotalGraphReportData(filter);
//			filter.isSuppourtingData = true;
//			subReportData.addAll(dao.getCostProjectionTotalGraphReportData(filter));
//			
//			
//			if(subReportData == null || subReportData.size() < 1){
//				throw new ServiceException(super.buildNoDataMessage("Cost Projection Chart Report Data"));
//			}
//			FormatTotalAmount(subReportData);
//		} catch(Exception e){
//			super.handleException(e);
//		}
//		
//		return subReportData;
//	}
//	
//	public Collection<CostReportChartReportData> getToDateChartReportData() throws Exception {
//		Collection<CostReportChartReportData> subReportData = null;
//		
//		try {
//			subReportData = dao.getCostToDateTotalGraphReportData(filter);
//			if(subReportData == null || subReportData.size() < 1){
//				throw new ServiceException(super.buildNoDataMessage("Cost Projection Chart Report Data"));
//			}
//			FormatTotalAmount(subReportData);
//		} catch(Exception e){
//			super.handleException(e);
//		}
//		
//		return subReportData;
//	}
}