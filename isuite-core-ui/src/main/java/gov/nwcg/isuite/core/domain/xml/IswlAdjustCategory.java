package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlAdjustCategory", table = "iswl_adjust_category")
public class IswlAdjustCategory {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ADJUST_CATEGORY", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "AdjustmentType", sqlname = "ADJUSTMENT_TYPE", type="STRING")
	private String adjustmentType;

	public IswlAdjustCategory() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.AdjustCategory#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.AdjustCategory#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.domain.AdjustCategory#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.AdjustCategory#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the adjustmentType
	 */
	public String getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 * @param adjustmentType
	 *            the adjustmentType to set
	 */
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
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

}
