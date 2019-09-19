package gov.nwcg.isuite.core.vo.rossimport2;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class RossConflictVo extends AbstractVo {
	private Long rossResId;
	private Long rossResReqId;
	private String requestNumber;
	private String sortRequestNumber;
	
	private String conflictType;
	private String conflictDesc;
	
	private String origFieldValue;
	private String newFieldValue;
	private String newFieldValue2;
	private String newFieldValue3;
	private String newFieldValue4;
	
	private Boolean isResolved=false;
	
	
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
	 * @return the conflictType
	 */
	public String getConflictType() {
		return conflictType;
	}
	/**
	 * @param conflictType the conflictType to set
	 */
	public void setConflictType(String conflictType) {
		this.conflictType = conflictType;
	}
	/**
	 * @return the isResolved
	 */
	public Boolean getIsResolved() {
		return isResolved;
	}
	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}
	/**
	 * @return the origFieldValue
	 */
	public String getOrigFieldValue() {
		return origFieldValue;
	}
	/**
	 * @param origFieldValue the origFieldValue to set
	 */
	public void setOrigFieldValue(String origFieldValue) {
		this.origFieldValue = origFieldValue;
	}
	/**
	 * @return the newFieldValue
	 */
	public String getNewFieldValue() {
		return newFieldValue;
	}
	/**
	 * @param newFieldValue the newFieldValue to set
	 */
	public void setNewFieldValue(String newFieldValue) {
		this.newFieldValue = newFieldValue;
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
	 * @return the conflictDesc
	 */
	public String getConflictDesc() {
		return conflictDesc;
	}
	/**
	 * @param conflictDesc the conflictDesc to set
	 */
	public void setConflictDesc(String conflictDesc) {
		this.conflictDesc = conflictDesc;
	}
	/**
	 * @return the newFieldValue2
	 */
	public String getNewFieldValue2() {
		return newFieldValue2;
	}
	/**
	 * @param newFieldValue2 the newFieldValue2 to set
	 */
	public void setNewFieldValue2(String newFieldValue2) {
		this.newFieldValue2 = newFieldValue2;
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
	 * @return the newFieldValue3
	 */
	public String getNewFieldValue3() {
		return newFieldValue3;
	}
	/**
	 * @param newFieldValue3 the newFieldValue3 to set
	 */
	public void setNewFieldValue3(String newFieldValue3) {
		this.newFieldValue3 = newFieldValue3;
	}
	/**
	 * @return the newFieldValue4
	 */
	public String getNewFieldValue4() {
		return newFieldValue4;
	}
	/**
	 * @param newFieldValue4 the newFieldValue4 to set
	 */
	public void setNewFieldValue4(String newFieldValue4) {
		this.newFieldValue4 = newFieldValue4;
	}
	
}
