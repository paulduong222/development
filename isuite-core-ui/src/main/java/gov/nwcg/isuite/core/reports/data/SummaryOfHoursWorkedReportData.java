package gov.nwcg.isuite.core.reports.data;

import java.util.Collection;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SummaryOfHoursWorkedReportData {

	private Long   incidentId;
	private String incidentName;
	private String incidentNumber;  //unitCode	
	private String startDate;
	private String endDate;
	private JRBeanCollectionDataSource jrGroupDateDataList;
	private Collection<GroupDateData> groupDateDataList;
	
	public SummaryOfHoursWorkedReportData(){
		
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the groupDateData
	 */
	public JRBeanCollectionDataSource getJrGroupDateDataList() {
		return new JRBeanCollectionDataSource(this.groupDateDataList);
	}

	/**
	 * @param groupDateData the groupDateData to set
	 */
	public void setJrGroupDateDataList(JRBeanCollectionDataSource jrGroupDateDataList) {
		this.jrGroupDateDataList = jrGroupDateDataList;
	}

	/**
	 * @return the groupDateData2
	 */
	public Collection<GroupDateData> getGroupDateDataList() {
		return groupDateDataList;
	}

	/**
	 * @param groupDateData2 the groupDateData2 to set
	 */
	public void setGroupDateDataList(Collection<GroupDateData> groupDateDataList) {
		this.groupDateDataList = groupDateDataList;
	}	
}
