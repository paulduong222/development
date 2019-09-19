package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlSpecialPay", table = "iswl_special_pay")
public class IswlSpecialPay {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_SPECIAL_PAY", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "AvailableToAd", sqlname = "IS_AVAIL_TO_AD", type="BOOLEAN")
	private Boolean availableToAd;

	@XmlTransferField(name = "AvailableToFed", sqlname = "IS_AVAIL_TO_FED", type="BOOLEAN")
	private Boolean availableToFed;

	@XmlTransferField(name = "AvailableToOther", sqlname = "IS_AVAIL_TO_OTHER", type="BOOLEAN")
	private Boolean availableToOther;

	@XmlTransferField(name = "OrdinalValue", sqlname = "ORDINAL_VALUE", type="INTEGER")
	private Integer ordinalValue;

	public IswlSpecialPay() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public Boolean isStandard() {
		return this.standard;
	}

	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

	public Boolean getAvailableToAd() {
		return availableToAd;
	}

	public void setAvailableToAd(Boolean availableToAd) {
		this.availableToAd = availableToAd;
	}

	public Boolean getAvailableToFed() {
		return availableToFed;
	}

	public void setAvailableToFed(Boolean availableToFed) {
		this.availableToFed = availableToFed;
	}

	public Boolean getAvailableToOther() {
		return availableToOther;
	}

	public void setAvailableToOther(Boolean availableToOther) {
		this.availableToOther = availableToOther;
	}

	public void setOrdinalValue(Integer ordinalValue) {
		this.ordinalValue = ordinalValue;
	}

	public Integer getOrdinalValue() {
		return ordinalValue;
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
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

}
