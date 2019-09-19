package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.Collection;

public class CostProjectionReportData {
	private String reportName;
	private String reportType;
	private Long incidentId;
	private String incidentName;
	private String incidentNumber;
	private Long incidentGroupId;
	private String incidentGroupName;
	private String projectionName;
	private Date startDate;
	private Date endDate;
	private List<CostProjectionCategoryDetailSubReportData> categoryDetailSubReportData = new ArrayList<CostProjectionCategoryDetailSubReportData>();
	private List<CostProjectionTotalSubReportData> subTotalReportData = new ArrayList<CostProjectionTotalSubReportData>();
	private List<CostReportChartReportData> subChartReportData = new ArrayList<CostReportChartReportData>();
	private List<CostReportChartReportData> subChartReportToDateData = new ArrayList<CostReportChartReportData>();
	
	private JRBeanCollectionDataSource dataSourceSubReportData;
	private JRBeanCollectionDataSource dataSourceSubTotalReportData;
	private JRBeanCollectionDataSource dataSourceSubReportChartData;
	private JRBeanCollectionDataSource dataSourceSubReportChartToDateData;
	
    private List<String> labels = new ArrayList<String>();
	private JRBeanCollectionDataSource labelList;
	
	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Long getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentName() {
		return incidentName;
	}
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	public String getIncidentNumber() {
		return incidentNumber;
	}
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	public String getIncidentGroupName() {
		return incidentGroupName;
	}
	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}
	
	public String getProjectionName() {
		return projectionName;
	}
	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
	}
	/**
	 * @return the subReportData
	 */
	public Collection<CostProjectionCategoryDetailSubReportData> getCategoryDetailSubReportData() {
		return categoryDetailSubReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setCategoryDetailSubReportData(Collection<CostProjectionCategoryDetailSubReportData> categoryDetailSubReportData) {
		this.categoryDetailSubReportData = (List<CostProjectionCategoryDetailSubReportData>)categoryDetailSubReportData;
	}
	
	public JRBeanCollectionDataSource getDataSourceSubReportData() {
		this.dataSourceSubReportData = new JRBeanCollectionDataSource(categoryDetailSubReportData);
		return dataSourceSubReportData;
	}

	public void setDataSourceSubReportData(
			JRBeanCollectionDataSource dataSourceSubReportData) {
		this.dataSourceSubReportData = dataSourceSubReportData;
	}	
	
	public Collection<CostProjectionTotalSubReportData> getSubTotalReportData() {
		return subTotalReportData;
	}
	public void setSubTotalReportData(
			Collection<CostProjectionTotalSubReportData> subTotalReportData) {
		this.subTotalReportData = (List<CostProjectionTotalSubReportData>)subTotalReportData;
	}
	
	public JRBeanCollectionDataSource getDataSourceSubTotalReportData() {
		this.dataSourceSubTotalReportData = new JRBeanCollectionDataSource(subTotalReportData);
		return dataSourceSubTotalReportData;
	}

	public void setDataSourceSubTotalReportData(
			JRBeanCollectionDataSource dataSourceSubTotalReportData) {
		this.dataSourceSubTotalReportData = dataSourceSubTotalReportData;
	}
	
	public List<CostReportChartReportData> getSubChartReportData() {
		return subChartReportData;
	}
	
	public List<CostReportChartReportData> getSubChartReportToDateData() {
		return subChartReportToDateData;
	}
	
	public List<String> getLables() {
		return labels;
	}

	public void setSubChartReportData(
			Collection<CostReportChartReportData> subChartReportData) {
		this.subChartReportData = (List<CostReportChartReportData>)subChartReportData;
	}	
	
	public void setSubChartReportToDateData(
			Collection<CostReportChartReportData> subChartReportToDateData) {
		this.subChartReportToDateData = (List<CostReportChartReportData>)subChartReportToDateData;
	}	
	
	public JRBeanCollectionDataSource getDataSourceSubReportChartData() {
		this.dataSourceSubReportChartData = new JRBeanCollectionDataSource(subChartReportData);
		return dataSourceSubReportChartData;
	}

	public void setDataSourceSubReportChartData(
			JRBeanCollectionDataSource dataSourceSubReportChartData) {
		this.dataSourceSubReportChartData = dataSourceSubReportChartData;
	}

	public JRBeanCollectionDataSource getDataSourceSubReportChartToDateData() {
		this.dataSourceSubReportChartToDateData = new JRBeanCollectionDataSource(subChartReportToDateData);
		return dataSourceSubReportChartToDateData;
	}
	
	public void setDataSourceSubReportChartToDateData(
			JRBeanCollectionDataSource dataSourceSubReportChartToDateData) {
		this.dataSourceSubReportChartToDateData = dataSourceSubReportChartToDateData;
	}
	
	public JRBeanCollectionDataSource getLabelList() {
		//for(CostReportChartReportData data:this.subChartReportData)
		labels.add("Aircraft ($1000)");
		labels.add("Equip ($2000)");
		labels.add("Crews ($3000)");
		this.labelList = new JRBeanCollectionDataSource(labels);
		return this.labelList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
