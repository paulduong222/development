package gov.nwcg.isuite.framework.filter;

import java.util.Date;

public class ReportFilter {

	public Date endDate;
	public Date startDate;

	private Boolean print;

	private String sortBy;

	public Long incidentId;
	public Long incidentGroupId;

	

	public ReportFilter() {
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Boolean getPrint() {
		return print;
	}

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
