package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswCostGroupAgencyDaySharePercentage", table="isw_cost_group_ag_ds_pct")
public class IswCostGroupAgencyDaySharePercentage {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_COST_GROUP_AG_DS_PCT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "CostGroupAgencyDayShareId", sqlname = "COST_GROUP_DAY_SHARE_ID", type="LONG", updateable=false)
	private Long costGroupAgencyDayShareId;

	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;
	
	@XmlTransferField(name = "Percentage", sqlname = "PERCENTAGE", type="BIGDECIMAL")
	private BigDecimal percentage;
	
	public IswCostGroupAgencyDaySharePercentage() {
		super();
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
	 * @return the costGroupAgencyDayShareId
	 */
	public Long getCostGroupAgencyDayShareId() {
		return costGroupAgencyDayShareId;
	}

	/**
	 * @param costGroupAgencyDayShareId the costGroupAgencyDayShareId to set
	 */
	public void setCostGroupAgencyDayShareId(Long costGroupAgencyDayShareId) {
		this.costGroupAgencyDayShareId = costGroupAgencyDayShareId;
	}

	/**
	 * @return the agencyTransferableIdentity
	 */
	public String getAgencyTransferableIdentity() {
		return agencyTransferableIdentity;
	}

	/**
	 * @param agencyTransferableIdentity the agencyTransferableIdentity to set
	 */
	public void setAgencyTransferableIdentity(String agencyTransferableIdentity) {
		this.agencyTransferableIdentity = agencyTransferableIdentity;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}


}
