package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapPlanPrintOrder", table = "isw_iap_plan_print_order")

public class IswIapPlanPrintOrder {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PLAN_PRINT_ORDER", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPlanTransferableIdentity", alias="planti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPlanId"
		, disjoined=true, disjoinedtable="isw_iap_plan", disjoinedfield="transferable_identity",disjoinedsource="iap_plan_id")
	private String iapPlanTransferableIdentity;

	
	@XmlTransferField(name = "FileType", sqlname="FILETYPE", type="STRING")
	private String fileType;

	@XmlTransferField(name = "FileId", sqlname="FILEID", type="LONG"  )
	private Long fileId;
	
	@XmlTransferField(name = "Position", sqlname="POSITION", type="INTEGER")
	private Integer position; 
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapPlanPrintOrder() {
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
	 * @return the FileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	
	/**
	 * @return the fileId
	 */
	public Long getFileId(){
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId){
		this.fileId = fileId;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position){
		this.position = position;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

}
