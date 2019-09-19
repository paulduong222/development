package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ResourcesWithDailyCostExceeds10000ReportData {
	
	private Long incidentId;
	private String incidentName;
	private String incidentNumber;
	private List<ResourcesWithDailyCostExceeds10000SubReportData> subReportData = new ArrayList<ResourcesWithDailyCostExceeds10000SubReportData>();
	private JRBeanCollectionDataSource dataSourceSubReportData;
	
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

	/**
	 * @return the subReportData
	 */
	public Collection<ResourcesWithDailyCostExceeds10000SubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<ResourcesWithDailyCostExceeds10000SubReportData> subReportData) {
		this.subReportData = (List<ResourcesWithDailyCostExceeds10000SubReportData>)subReportData;
	}
	
	public JRBeanCollectionDataSource getDataSourceSubReportData() {
		this.dataSourceSubReportData = new JRBeanCollectionDataSource(subReportData);
		return this.dataSourceSubReportData;
	}

	public void setDataSourceSubReportData(
			JRBeanCollectionDataSource dataSourceSubReportData) {
		this.dataSourceSubReportData = dataSourceSubReportData;
	}	                          
}


