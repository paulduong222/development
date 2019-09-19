package gov.nwcg.isuite.core.vo;


public class MissingDataVo {
	private Long incidentResourceId;
	private Long parentRecordId;
	private String requestNumber;
	private String resourceName;
	private String dataType;
	
	public MissingDataVo(){
		
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the parentRecordId
	 */
	public Long getParentRecordId() {
		return parentRecordId;
	}

	/**
	 * @param parentRecordId the parentRecordId to set
	 */
	public void setParentRecordId(Long parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
