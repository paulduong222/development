package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswCostAccrualExtract", table = "isw_cost_accrual_extract")
public class IswCostAccrualExtract {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_ACCRUAL_EXTRACT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "ExtractDate", sqlname = "extract_date", type = "DATE")
	private Date extractDate;

	@XmlTransferField(name = "Finalized", sqlname = "FINALIZED", type = "STRING", defaultvalue="N")
	private String finalized;

	@XmlTransferField(name = "IsFromSingleIncident", sqlname = "IS_FROM_SINGLE_INCIDENT", type = "STRING", defaultvalue="N")
	private String isFromSingleIncident;
	
	@XmlTransferField(name = "FinalizedDate", sqlname = "finalized_date", type = "DATE")
	private Date finalizedDate;

	@XmlTransferField(name = "PreparedBy", sqlname = "PREPARED_BY", type = "STRING")
	private String preparedBy;

	@XmlTransferField(name = "PreparedPhone", sqlname = "PREPARED_PHONE", type = "STRING")
	private String preparedPhone;

	@XmlTransferField(name = "IncidentGroupTransferableIdentity", alias="igti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentGroupId"
		, disjoined=true, disjoinedtable="isw_incident_group", disjoinedfield="transferable_identity",disjoinedsource="incident_group_id")
	private String incidentGroupTransferableIdentity;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG"
		,derived=true,derivedfield="IncidentGroupTransferableIdentity")
	private Long incidentGroupId;

	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentId"
		, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
	private String incidentTransferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
		,derived=true,derivedfield="IncidentTransferableIdentity")
	private Long incidentId;

	@XmlTransferField(name = "SequenceNumber", sqlname = "SEQUENCE_NUMBER", type = "SHORT")
	private Short sequenceNumber;

	@XmlTransferField(name = "Exported", sqlname = "is_exported", type = "STRING", defaultvalue="N")
	private String exported;
	
	@XmlTransferField(name = "CostAccrualExtRsc", type="COMPLEX", target=IswCostAccrualExtractRsc.class
			, lookupname="CostAccrualExtractId", sourcename="Id"
			, cascade=true)
	private Collection<IswCostAccrualExtractRsc> costAccrualExtRscs = new ArrayList<IswCostAccrualExtractRsc>();

	/**
	 * Default constructor.
	 * 
	 */
	public IswCostAccrualExtract() {
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
	 * @return the extractDate
	 */
	public Date getExtractDate() {
		return extractDate;
	}

	/**
	 * @param extractDate the extractDate to set
	 */
	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}

	/**
	 * @return the finalized
	 */
	public String getFinalized() {
		return finalized;
	}

	/**
	 * @param finalized the finalized to set
	 */
	public void setFinalized(String finalized) {
		this.finalized = finalized;
	}

	/**
	 * @return the finalizedDate
	 */
	public Date getFinalizedDate() {
		return finalizedDate;
	}

	/**
	 * @param finalizedDate the finalizedDate to set
	 */
	public void setFinalizedDate(Date finalizedDate) {
		this.finalizedDate = finalizedDate;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedPhone
	 */
	public String getPreparedPhone() {
		return preparedPhone;
	}

	/**
	 * @param preparedPhone the preparedPhone to set
	 */
	public void setPreparedPhone(String preparedPhone) {
		this.preparedPhone = preparedPhone;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the sequenceNumber
	 */
	public Short getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Short sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the exported
	 */
	public String getExported() {
		return exported;
	}

	/**
	 * @param exported the exported to set
	 */
	public void setExported(String exported) {
		this.exported = exported;
	}

	/**
	 * @return the incidentGroupTransferableIdentity
	 */
	public String getIncidentGroupTransferableIdentity() {
		return incidentGroupTransferableIdentity;
	}

	/**
	 * @param incidentGroupTransferableIdentity the incidentGroupTransferableIdentity to set
	 */
	public void setIncidentGroupTransferableIdentity(
			String incidentGroupTransferableIdentity) {
		this.incidentGroupTransferableIdentity = incidentGroupTransferableIdentity;
	}

	/**
	 * @return the incidentTransferableIdentity
	 */
	public String getIncidentTransferableIdentity() {
		return incidentTransferableIdentity;
	}

	/**
	 * @param incidentTransferableIdentity the incidentTransferableIdentity to set
	 */
	public void setIncidentTransferableIdentity(String incidentTransferableIdentity) {
		this.incidentTransferableIdentity = incidentTransferableIdentity;
	}

	/**
	 * @return the costAccrualExtRscs
	 */
	public Collection<IswCostAccrualExtractRsc> getCostAccrualExtRscs() {
		return costAccrualExtRscs;
	}

	/**
	 * @param costAccrualExtRscs the costAccrualExtRscs to set
	 */
	public void setCostAccrualExtRscs(
			Collection<IswCostAccrualExtractRsc> costAccrualExtRscs) {
		this.costAccrualExtRscs = costAccrualExtRscs;
	}

	/**
	 * @return the isFromSingleIncident
	 */
	public String getIsFromSingleIncident() {
		return isFromSingleIncident;
	}

	/**
	 * @param isFromSingleIncident the isFromSingleIncident to set
	 */
	public void setIsFromSingleIncident(String isFromSingleIncident) {
		this.isFromSingleIncident = isFromSingleIncident;
	}


}
