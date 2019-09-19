package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapAttachment", table = "isw_iap_attachment")
public class IswIapAttachment {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_ATTACHMENT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPlanTransferableIdentity", alias="planti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPlanId"
		, disjoined=true, disjoinedtable="isw_iap_plan", disjoinedfield="transferable_identity",disjoinedsource="iap_plan_id")
	private String iapPlanTransferableIdentity;

	@XmlTransferField(name = "AttachmentName", sqlname="ATTACHMENT_NAME", type="STRING")
	private String attachmentName;
	
	@XmlTransferField(name = "FileName", sqlname="FILENAME", type="STRING")
	private String filename;

	@XmlTransferField(name = "Attached", sqlname="ATTACHED", type="STRING")
	private String attached;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAttachment() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the iapPlanTransferableIdentity
	 */
	public String getIapPlanTransferableIdentity() {
		return iapPlanTransferableIdentity;
	}

	/**
	 * @param iapPlanTransferableIdentity the iapPlanTransferableIdentity to set
	 */
	public void setIapPlanTransferableIdentity(String iapPlanTransferableIdentity) {
		this.iapPlanTransferableIdentity = iapPlanTransferableIdentity;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the attached
	 */
	public String getAttached() {
		return attached;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(String attached) {
		this.attached = attached;
	}


}
