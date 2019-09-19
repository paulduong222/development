package gov.nwcg.isuite.core.reports.filter;

import java.util.ArrayList;
import java.util.Collection;

public class TrainingAssignmentsListReportFilter {
	private Long incidentId;
	private Long incidentGroupId;
	
	private Collection<String> sorts = new ArrayList<String>();
	private Boolean exportToExcel;

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setSorts(Collection<String> sorts) {
		this.sorts = sorts;
	}

	public Collection<String> getSorts() {
		return sorts;
	}

	/**
	 * @param exportToExcel the exportToExcel to set
	 */
	public void setExportToExcel(Boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}

	/**
	 * @return the exportToExcel
	 */
	public Boolean getExportToExcel() {
		return exportToExcel;
	}

}
