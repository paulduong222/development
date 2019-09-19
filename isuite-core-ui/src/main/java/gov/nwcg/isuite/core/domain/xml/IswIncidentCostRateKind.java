package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostRateKind", table="isw_inccost_rate_kind"
					,filter=" and (last_modified_by is not null or last_modified_by != '' ) " )
public class IswIncidentCostRateKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "KindTransferableIdentity", alias="kindti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="KindId"
		, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname="KIND_ID", type="LONG"
				,derived=true, derivedfield="KindTransferableIdentity")
	private Long kindId;
	
	@XmlTransferField(name = "RateType", sqlname="RATE_TYPE", type="STRING")
	private String rateType;
	
	@XmlTransferField(name = "RateAmount", sqlname="RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal rateAmount;
	
	//@XmlTransferField(name = "IncidentCostRateId", sqlname="INCCOST_RATE_ID", type="LONG",updateable=false)
	//private Long incidentCostRateId;

	@XmlTransferField(name = "IncidentCostRateTransferableIdentity", alias="icrti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentCostRateId"
		, disjoined=true, disjoinedtable="isw_inccost_rate", disjoinedfield="transferable_identity",disjoinedsource="inccost_rate_id")
	private String incidentCostRateTransferableIdentity;

	@XmlTransferField(name = "IncidentCostRateId", sqlname="INCCOST_RATE_ID", type="LONG"
		,derived=true,derivedfield="IncidentCostRateTransferableIdentity")
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
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
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

	/**
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	/**
	 * @return the incidentCostRateTransferableIdentity
	 */
	public String getIncidentCostRateTransferableIdentity() {
		return incidentCostRateTransferableIdentity;
	}

	/**
	 * @param incidentCostRateTransferableIdentity the incidentCostRateTransferableIdentity to set
	 */
	public void setIncidentCostRateTransferableIdentity(
			String incidentCostRateTransferableIdentity) {
		this.incidentCostRateTransferableIdentity = incidentCostRateTransferableIdentity;
	}
	

}
