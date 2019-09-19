package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for ShiftsInExcessStandardHours.jrxml.
 */
public class ShiftsInExcessOfStandardHoursReportData {

	private Long incidentId;
	private String incidentName;
	private String incidentTag;
	private Date firstDateToInclude;
	private Date lastDateToInclude;
	private Date reportPrintedDate;
	private Double standardHours;

	private Collection<ShiftsInExcessOfStandardHoursSubReportData> subReportData = new ArrayList<ShiftsInExcessOfStandardHoursSubReportData>();

	public ShiftsInExcessOfStandardHoursReportData() {
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

	public String getIncidentTag() {
		return incidentTag;
	}

	public void setIncidentTag(String incidentTag) {
		this.incidentTag = incidentTag;
	}

	public Date getFirstDateToInclude() {
		return firstDateToInclude;
	}

	public void setFirstDateToInclude(Date firstDateToInclude) {
		this.firstDateToInclude = firstDateToInclude;
	}

	public Date getLastDateToInclude() {
		return lastDateToInclude;
	}

	public void setLastDateToInclude(Date lastDateToInclude) {
		this.lastDateToInclude = lastDateToInclude;
	}

	public Date getReportPrintedDate() {
		return reportPrintedDate;
	}

	public void setReportPrintedDate(Date reportPrintedDate) {
		this.reportPrintedDate = reportPrintedDate;
	}

	/**
	 * @return the standardHours
	 */
	public Double getStandardHours() {
		return standardHours;
	}

	/**
	 * @param standardHours the standardHours to set
	 */
	public void setStandardHours(Double standardHours) {
		this.standardHours = standardHours;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<ShiftsInExcessOfStandardHoursSubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<ShiftsInExcessOfStandardHoursSubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	/**
	 * @return the dataSourceSubReport
	 */
	public JRBeanCollectionDataSource getDataSource() {
		return new JRBeanCollectionDataSource(this.getSubReportData());
	}
}
