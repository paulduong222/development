package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class DetailSummaryReportData {

	private Long incidentId;
	private String incidentName;
	private String incidentNumber;
	private Boolean isWildfire;
	private List<DetailSummarySubReportData> subReportData = new ArrayList<DetailSummarySubReportData>();
	private JRBeanCollectionDataSource dataSourceReportData;
	
	
	public DetailSummaryReportData() {}

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
	public Collection<DetailSummarySubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<DetailSummarySubReportData> subReportData) {
		this.subReportData = (List<DetailSummarySubReportData>)subReportData;
	}

	public JRBeanCollectionDataSource getDataSourceReportData() {
		this.dataSourceReportData = new JRBeanCollectionDataSource(subReportData);
		return this.dataSourceReportData;
	}

	public void setDataSourceReportData(
			JRBeanCollectionDataSource dataSourceReportData) {
		this.dataSourceReportData = dataSourceReportData;
	}

	/**
	 * @param isWildfire the isWildfire to set
	 */
	public void setIsWildfire(Boolean isWildfire) {
		this.isWildfire = isWildfire;
	}

	/**
	 * @return the isWildfire
	 */
	public Boolean getIsWildfire() {
		return isWildfire;
	}

}
