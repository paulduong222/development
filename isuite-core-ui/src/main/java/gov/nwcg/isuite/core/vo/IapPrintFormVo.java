package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class IapPrintFormVo extends AbstractVo {
	private String formType;
	private Long formId;
	private Integer position;
	private Long attachmentId;
	private Boolean isAttachment = false;
	
	public IapPrintFormVo(){
		
	}

	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}

	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}

	/**
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the isAttachment
	 */
	public Boolean getIsAttachment() {
		return isAttachment;
	}

	/**
	 * @param isAttachment the isAttachment to set
	 */
	public void setIsAttachment(Boolean isAttachment) {
		this.isAttachment = isAttachment;
	}

	/**
	 * @return the attachmentId
	 */
	public Long getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	
}
