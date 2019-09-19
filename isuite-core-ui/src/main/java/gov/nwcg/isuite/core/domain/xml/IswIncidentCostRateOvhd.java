package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostRateOvhd", table="isw_inccost_rate_ovhd"
					,filter=" and (last_modified_by is not null or last_modified_by != '' ) " )
public class IswIncidentCostRateOvhd {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_OVHD", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "DirectRate", sqlname="DIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal directRate;

	@XmlTransferField(name = "InDirectRate", sqlname="INDIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal indirectRate;
	
	@XmlTransferField(name = "SingleRate", sqlname="SINGLE_RATE", type="BIGDECIMAL")
	private BigDecimal singleRate;
	
	@XmlTransferField(name = "SubordinateRate", sqlname="SUBORDINATE_RATE", type="BIGDECIMAL")
	private BigDecimal subordinateRate;
	
	@XmlTransferField(name = "IncidentCostRateId", sqlname="INCCOST_RATE_ID", type="LONG",updateable=false)
	private Long incidentCostRateId;

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
	 * @return the directRate
	 */
	public BigDecimal getDirectRate() {
		return directRate;
	}

	/**
	 * @param directRate the directRate to set
	 */
	public void setDirectRate(BigDecimal directRate) {
		this.directRate = directRate;
	}

	/**
	 * @return the indirectRate
	 */
	public BigDecimal getIndirectRate() {
		return indirectRate;
	}

	/**
	 * @param indirectRate the indirectRate to set
	 */
	public void setIndirectRate(BigDecimal indirectRate) {
		this.indirectRate = indirectRate;
	}

	/**
	 * @return the singleRate
	 */
	public BigDecimal getSingleRate() {
		return singleRate;
	}

	/**
	 * @param singleRate the singleRate to set
	 */
	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	/**
	 * @return the subordinateRate
	 */
	public BigDecimal getSubordinateRate() {
		return subordinateRate;
	}

	/**
	 * @param subordinateRate the subordinateRate to set
	 */
	public void setSubordinateRate(BigDecimal subordinateRate) {
		this.subordinateRate = subordinateRate;
	}

	/**
	 * @return the incidentCostRateId
	 */
	public Long getIncidentCostRateId() {
		return incidentCostRateId;
	}

	/**
	 * @param incidentCostRateId the incidentCostRateId to set
	 */
	public void setIncidentCostRateId(Long incidentCostRateId) {
		this.incidentCostRateId = incidentCostRateId;
	}
	

}
