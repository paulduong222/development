package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class CostSettingsVo extends AbstractVo {
	private Long incidentId;
	private Long incidentGroupId;
	private Integer costDefaultHours;
	private String costDefaultHoursString;
	private Boolean costAutoRun = false;
	private String costAutoRunString;

	public Integer getCostDefaultHours() {
		return costDefaultHours;
	}
	public void setCostDefaultHours(Integer costDefaultHours) {
		this.costDefaultHours = costDefaultHours;
	}
	public Boolean getCostAutoRun() {
		return costAutoRun;
	}
	public void setCostAutoRun(Boolean costAutoRun) {
		this.costAutoRun = costAutoRun;
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
	public String getCostDefaultHoursString() {
		return costDefaultHoursString;
	}
	public void setCostDefaultHoursString(String costDefaultHoursString) {
		this.costDefaultHoursString = costDefaultHoursString;
	}
	public String getCostAutoRunString() {
		return costAutoRunString;
	}
	public void setCostAutoRunString(String costAutoRunString) {
		this.costAutoRunString = costAutoRunString;
	}

}
