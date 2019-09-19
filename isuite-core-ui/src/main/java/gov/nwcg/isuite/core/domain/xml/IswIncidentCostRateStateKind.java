package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostRateStateKind", table="isw_inccost_rate_state_kind"
					,filter=" and (last_modified_by is not null or last_modified_by != '' ) "
)
public class IswIncidentCostRateStateKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_STATE_KIND", type="LONG")
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

	//@XmlTransferField(name = "IncidentCostRateStateId", sqlname="INCCOST_RATE_STATE_ID", type="LONG",updateable=false)
	//private Long incidentCostRateStateId;

	@XmlTransferField(name = "IncidentCostRateStateTransferableIdentity", alias="icrsti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentCostRateStateId"
		, disjoined=true, disjoinedtable="isw_inccost_rate_state", disjoinedfield="transferable_identity",disjoinedsource="inccost_rate_state_id")
	private String incidentCostRateStateTransferableIdentity;

	@XmlTransferField(name = "IncidentCostRateStateId", sqlname="INCCOST_RATE_STATE_ID", type="LONG"
		,derived=true,derivedfield="IncidentCostRateStateTransferableIdentity")
	private Long incidentCostRateStateId;
	
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
	 * @return the incidentCostRateStateId
	 */
	public Long getIncidentCostRateStateId() {
		return incidentCostRateStateId;
	}

	/**
	 * @param incidentCostRateStateId the incidentCostRateStateId to set
	 */
	public void setIncidentCostRateStateId(Long incidentCostRateStateId) {
		this.incidentCostRateStateId = incidentCostRateStateId;
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
	 * @return the incidentCostRateStateTransferableIdentity
	 */
	public String getIncidentCostRateStateTransferableIdentity() {
		return incidentCostRateStateTransferableIdentity;
	}

	/**
	 * @param incidentCostRateStateTransferableIdentity the incidentCostRateStateTransferableIdentity to set
	 */
	public void setIncidentCostRateStateTransferableIdentity(
			String incidentCostRateStateTransferableIdentity) {
		this.incidentCostRateStateTransferableIdentity = incidentCostRateStateTransferableIdentity;
	}

	
}
