package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;

public class IncidentTrainingSummaryReportFilter {
	private Long incidentId;
	private Long incidentGroupId;
	private Long specialistId;
	
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();
	
	public IncidentTrainingSummaryReportFilter(){
	}
	
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
	
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}

	public Long getSpecialistId() {
		return specialistId;
	}

	public void setSpecialistId(Long specialistId) {
		this.specialistId = specialistId;
	}
	
}
