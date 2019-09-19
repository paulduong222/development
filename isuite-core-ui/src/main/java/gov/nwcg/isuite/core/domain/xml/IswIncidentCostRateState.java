package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswIncidentCostRateState", table="isw_inccost_rate_state"
					,filter=" and id in ( " +
								"select inccost_rate_state_id from isw_inccost_rate_state_kind where " +
								"last_modified_by is not null or last_modified_by != '' " +
					          ") "
)
public class IswIncidentCostRateState {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_STATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="AgencyId"
		, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
				,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;
	
	@XmlTransferField(name = "DirectRate", sqlname="DIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal directRate;

	@XmlTransferField(name = "InDirectRate", sqlname="INDIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal indirectRate;
	
	@XmlTransferField(name = "SingleRate", sqlname="SINGLE_RATE", type="BIGDECIMAL")
	private BigDecimal singleRate;
	
	@XmlTransferField(name = "SubordinateRate", sqlname="SUBORDINATE_RATE", type="BIGDECIMAL")
	private BigDecimal subordinateRate;
	
	//@XmlTransferField(name = "IncidentCostRateId", sqlname="INCCOST_RATE_ID", type="LONG",updateable=false)
	//private Long incidentCostRateId;

	@XmlTransferField(name = "IncidentCostRateTransferableIdentity", alias="icrti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentCostRateId"
		, disjoined=true, disjoinedtable="isw_inccost_rate", disjoinedfield="transferable_identity",disjoinedsource="inccost_rate_id")
	private String incidentCostRateTransferableIdentity;

	@XmlTransferField(name = "IncidentCostRateId", sqlname="INCCOST_RATE_ID", type="LONG"
		,derived=true,derivedfield="IncidentCostRateTransferableIdentity")
	private Long incidentCostRateId;

	@XmlTransferField(name = "IncidentCostRateStateKind", type="COMPLEX", target=IswIncidentCostRateStateKind.class
			, lookupname="IncidentCostRateStateId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentCostRateStateKind> incidentCostRateStateKinds = new ArrayList<IswIncidentCostRateStateKind>();

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

	/**
	 * @return the incidentCostRateStateKinds
	 */
	public Collection<IswIncidentCostRateStateKind> getIncidentCostRateStateKinds() {
		return incidentCostRateStateKinds;
	}

	/**
	 * @param incidentCostRateStateKinds the incidentCostRateStateKinds to set
	 */
	public void setIncidentCostRateStateKinds(
			Collection<IswIncidentCostRateStateKind> incidentCostRateStateKinds) {
		this.incidentCostRateStateKinds = incidentCostRateStateKinds;
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
