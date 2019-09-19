package gov.nwcg.isuite.core.reports.data;

public class CostProjectionTotalSubReportData extends CostProjectionSubReportData{
	
	private Double costToDate;
	private Double projectionCost;
	
	public Double getCostToDate() {
		return costToDate;
	}
	public void setCostToDate(Double costToDate) {
		this.costToDate = costToDate;
	}
	public Double getProjectionCost() {
		return projectionCost;
	}
	public void setProjectionCost(Double projectionCost) {
		this.projectionCost = projectionCost;
	}
}
