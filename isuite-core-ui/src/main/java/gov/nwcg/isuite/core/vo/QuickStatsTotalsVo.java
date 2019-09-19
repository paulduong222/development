package gov.nwcg.isuite.core.vo;

public class QuickStatsTotalsVo {

	private Long incidentId;
	
	private Integer numberOfPersonnelCount;
	private Integer filledResourceCount;
	private Integer checkedInResourceCount;
	private Integer releasedResourceCount;
	private Integer totalOrderCount;
	
	public QuickStatsTotalsVo() {
		this.numberOfPersonnelCount = new Integer(0);
		this.filledResourceCount = new Integer(0);
		this.checkedInResourceCount = new Integer(0);
		this.releasedResourceCount = new Integer(0);
		this.totalOrderCount = new Integer(0);
	}

	/**
	 * @return the numberOfPersonnelCount
	 */
	public Integer getNumberOfPersonnelCount() {
		if(null != numberOfPersonnelCount)
			return numberOfPersonnelCount;
		else
			return 0;
	}

	/**
	 * @param numberOfPersonnelCount the numberOfPersonnelCount to set
	 */
	public void setNumberOfPersonnelCount(Integer numberOfPersonnelCount) {
		this.numberOfPersonnelCount = numberOfPersonnelCount;
	}

	/**
	 * @return the filledResourceCount
	 */
	public Integer getFilledResourceCount() {
		return filledResourceCount;
	}

	/**
	 * @param filledResourceCount the filledResourceCount to set
	 */
	public void setFilledResourceCount(Integer filledResourceCount) {
		this.filledResourceCount = filledResourceCount;
	}

	/**
	 * @return the checkedInResourceCount
	 */
	public Integer getCheckedInResourceCount() {
		return checkedInResourceCount;
	}

	/**
	 * @param checkedInResourceCount the checkedInResourceCount to set
	 */
	public void setCheckedInResourceCount(Integer checkedInResourceCount) {
		this.checkedInResourceCount = checkedInResourceCount;
	}

	/**
	 * @return the releasedResourceCount
	 */
	public Integer getReleasedResourceCount() {
		return releasedResourceCount;
	}

	/**
	 * @param releasedResourceCount the releasedResourceCount to set
	 */
	public void setReleasedResourceCount(Integer releasedResourceCount) {
		this.releasedResourceCount = releasedResourceCount;
	}

	/**
	 * @return the totalOrderCount
	 */
	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	/**
	 * @param totalOrderCount the totalOrderCount to set
	 */
	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
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
}
