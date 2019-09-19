package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.LongUtility;


public class RossIncDataBlacklistGridVo extends AbstractVo {
	private Long rossResourceId;
	private Long rossResReqId;
	private String lastName;
	private String firstName;
	private String assignmentName;
	private String rossResId;
	private String rossIncId;
	private String resourceName;
	private String requestNumber;
	private String agency;
	
	public RossIncDataBlacklistGridVo(){
		
	}
	
	/**
	 * @return the rossIncId
	 */
	public String getRossIncId() {
		return rossIncId;
	}

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId) {
		this.rossIncId = rossIncId;
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
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * @return the rossResourceId
	 */
	public Long getRossResourceId() {
		return rossResourceId;
	}

	/**
	 * @param rossResourceId the rossResourceId to set
	 */
	public void setRossResourceId(Long rossResourceId) {
		this.rossResourceId = rossResourceId;
		if(LongUtility.hasValue(this.rossResourceId))
			this.rossResId=String.valueOf(this.rossResourceId);
	}

	public String getRossResId() {
		return rossResId;
	}

	public void setRossResId(String rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @return the rossResReqId
	 */
	public Long getRossResReqId() {
		return rossResReqId;
	}

	/**
	 * @param rossResReqId the rossResReqId to set
	 */
	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
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
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}

	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

}
