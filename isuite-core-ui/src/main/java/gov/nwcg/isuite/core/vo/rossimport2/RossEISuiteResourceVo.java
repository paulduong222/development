package gov.nwcg.isuite.core.vo.rossimport2;


public class RossEISuiteResourceVo {
	private Long rossResId;
	private Long resourceId;
	private Long incidentResourceId;
	private Long workAreaId;
	private Long assignmentId;

	private String assignmentKindCode;
	private String assignmentKindDesc;
	private String resourceName;
	private String lastName;
	private String firstName;
	private Boolean isPerson;
	private String requestNumber;
	private String sortRequestNumber;
	private String resourceAgencyCode;
	private String resourceAgencyName;
	private Long workPeriodId;

	private String rossGroupAssignment;
	
	private Long rossResReqId;
	
	
	public RossEISuiteResourceVo(){
		
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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
	 * @return the workAreaId
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * @param workAreaId the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
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
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @return the assignmentKindCode
	 */
	public String getAssignmentKindCode() {
		return assignmentKindCode;
	}

	/**
	 * @param assignmentKindCode the assignmentKindCode to set
	 */
	public void setAssignmentKindCode(String assignmentKindCode) {
		this.assignmentKindCode = assignmentKindCode;
	}

	/**
	 * @return the assignmentKindDesc
	 */
	public String getAssignmentKindDesc() {
		return assignmentKindDesc;
	}

	/**
	 * @param assignmentKindDesc the assignmentKindDesc to set
	 */
	public void setAssignmentKindDesc(String assignmentKindDesc) {
		this.assignmentKindDesc = assignmentKindDesc;
	}

	/**
	 * @return the resourceAgencyCode
	 */
	public String getResourceAgencyCode() {
		return resourceAgencyCode;
	}

	/**
	 * @param resourceAgencyCode the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode) {
		this.resourceAgencyCode = resourceAgencyCode;
	}

	/**
	 * @return the resourceAgencyName
	 */
	public String getResourceAgencyName() {
		return resourceAgencyName;
	}

	/**
	 * @param resourceAgencyName the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName) {
		this.resourceAgencyName = resourceAgencyName;
	}

	/**
	 * @return the workPeriodId
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * @param workPeriodId the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
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

	public Long getRossResReqId() {
		return rossResReqId;
	}

	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
	}

	public String getRossGroupAssignment() {
		return rossGroupAssignment;
	}

	public void setRossGroupAssignment(String rossGroupAssignment) {
		this.rossGroupAssignment = rossGroupAssignment;
	}

	/**
	 * @return the sortRequestNumber
	 */
	public String getSortRequestNumber() {
		return sortRequestNumber;
	}

	/**
	 * @param sortRequestNumber the sortRequestNumber to set
	 */
	public void setSortRequestNumber(String sortRequestNumber) {
		this.sortRequestNumber = sortRequestNumber;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the isPerson
	 */
	public Boolean getIsPerson() {
		return isPerson;
	}

	/**
	 * @param isPerson the isPerson to set
	 */
	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}
}
