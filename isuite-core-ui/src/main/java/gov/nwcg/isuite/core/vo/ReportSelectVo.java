package gov.nwcg.isuite.core.vo;

import java.util.Date;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class ReportSelectVo extends AbstractVo implements PersistableVo {

	private String label;
	private Long resourceId;
	private Long incidentResourceId;
	private Date releaseDate;
	private Boolean isPerson;
	private Boolean isContractor;
	
	public ReportSelectVo() {

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Boolean getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	public Boolean getIsContractor() {
		return isContractor;
	}

	public void setIsContractor(Boolean isContractor) {
		this.isContractor = isContractor;
	}
}

