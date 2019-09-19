package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for PersonnelTimeReport.jrxml.
 */
public class PersonnelTimeReportData {

	private Long incidentId;
	private String incidentName;
	private String incidentNumber;
	protected Date postStartDate;
	protected Date postStopDate;
	private String faxNumber;
	private String timeUnitLeaderName;
	private String timeUnitLeaderPhoneNumber;
	private Collection<PersonnelTimeSubReportData> subReportData = new ArrayList<PersonnelTimeSubReportData>();
	
	public PersonnelTimeReportData() {}

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

	public Date getPostStartDate() {
		return postStartDate;
	}

	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}

	public Date getPostStopDate() {
		return postStopDate;
	}

	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the timeUnitLeaderName
	 */
	public String getTimeUnitLeaderName() {
		return timeUnitLeaderName;
	}

	/**
	 * @param timeUnitLeaderName the timeUnitLeaderName to set
	 */
	public void setTimeUnitLeaderName(String timeUnitLeaderName) {
		this.timeUnitLeaderName = timeUnitLeaderName;
	}

	/**
	 * @return the timeUnitLeaderPhoneNumber
	 */
	public String getTimeUnitLeaderPhoneNumber() {
		return timeUnitLeaderPhoneNumber;
	}

	/**
	 * @param timeUnitLeaderPhoneNumber the timeUnitLeaderPhoneNumber to set
	 */
	public void setTimeUnitLeaderPhoneNumber(String timeUnitLeaderPhoneNumber) {
		this.timeUnitLeaderPhoneNumber = timeUnitLeaderPhoneNumber;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<PersonnelTimeSubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<PersonnelTimeSubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	public JRBeanCollectionDataSource getDataSource() {
		return new JRBeanCollectionDataSource(this.getSubReportData());
	}

}
