package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswContractorRate", table="isw_contractor_rate")
public class IswContractorRate {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_CONTRACTOR_RATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "RateType", sqlname = "RATE_TYPE", type="STRING")
	private String rateType;

	@XmlTransferField(name = "UnitOfMeasure", sqlname = "UNIT_OF_MEASURE", type="STRING")
	private String unitOfMeasure;

	@XmlTransferField(name = "RateAmount", sqlname = "RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal rateAmount;

	@XmlTransferField(name = "GuaranteeAmount", sqlname = "GUARANTEE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal guaranteeAmount;

	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;

	
	public IswContractorRate() {

	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType
	 *            the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure
	 *            the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount
	 *            the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount
	 *            the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
