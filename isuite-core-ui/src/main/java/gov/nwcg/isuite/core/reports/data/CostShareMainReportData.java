package gov.nwcg.isuite.core.reports.data;

import java.util.Collection;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CostShareMainReportData {

	private Long   incidentId;
	private String incidentName;
	private String incidentNumber;  //unitCode	
	private Long   incidentGroupId;
	private String incidentGroupName;
	private String incidentGroupNumber;  //unitCode	
	private JRBeanCollectionDataSource dataSourceSubReportData;
	private Collection<CostShareSubSubReportData> costShareSubReportDataList;
	
	public CostShareMainReportData(){
		
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

	public String getIncidentGroupNumber() {
		return incidentGroupNumber;
	}

	public void setIncidentGroupNumber(String incidentGroupNumber) {
		this.incidentGroupNumber = incidentGroupNumber;
	}

	/**
	 * @return the groupDateData
	 */
	public JRBeanCollectionDataSource getDataSourceSubReportData() {
		return new JRBeanCollectionDataSource(this.costShareSubReportDataList);
	}

	/**
	 * @param groupDateData the groupDateData to set
	 */
	public void setDataSourceSubReportData(JRBeanCollectionDataSource jrCostShareSubReportDataList) {
		this.dataSourceSubReportData = jrCostShareSubReportDataList;
	}

	/**
	 * @return the groupDateData2
	 */
	public Collection<CostShareSubSubReportData> getCostShareSubReportDataList() {
		return costShareSubReportDataList;
	}

	/**
	 * @param groupDateData2 the groupDateData2 to set
	 */
	public void setCostShareSubReportDataList(Collection<CostShareSubSubReportData> costShareSubReportDataList) {
		this.costShareSubReportDataList = costShareSubReportDataList;
	}	
}
