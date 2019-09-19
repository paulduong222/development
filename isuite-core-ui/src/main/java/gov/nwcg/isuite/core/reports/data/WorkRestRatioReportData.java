package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for WorkRestRatioReport.jrxml.
 */
public class WorkRestRatioReportData {
	
	private Long incidentId;
	private String incidentName;
	private String incidentTag;
		
	private Collection<WorkRestRatioSubReportData> subReportData = new ArrayList<WorkRestRatioSubReportData>();

	public WorkRestRatioReportData() {
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentTag
	 */
	public String getIncidentTag() {
		return incidentTag;
	}

	/**
	 * @param incidentTag the incidentTag to set
	 */
	public void setIncidentTag(String incidentTag) {
		this.incidentTag = incidentTag;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<WorkRestRatioSubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(
			Collection<WorkRestRatioSubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	/**
	 * @return the dataSourceSubReport
	 */
	public JRBeanCollectionDataSource getDataSource() {
		return new JRBeanCollectionDataSource(this.getSubReportData());
	}

}
