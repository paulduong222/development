package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswResourceOther", table = "isw_resource_other")
public class IswResourceOther {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_OTHER", type="LONG")
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

	@XmlTransferField(name = "IacTransferableIdentity", alias="iacti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IacId"
						, disjoined=true, disjoinedtable="isw_incident_account_code", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_ACCOUNT_CODE_ID")
	private String iacTransferableIdentity;
	
	@XmlTransferField(name = "IacId", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="LONG"
						,derived=true, derivedfield="IacTransferableIdentity")
	private Long iacId;
	
	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;

	@XmlTransferField(name = "RequestNumber", sqlname = "REQUEST_NUMBER", type = "STRING")
	private String requestNumber;

	@XmlTransferField(name = "CostDescription", sqlname = "COST_DESCRIPTION", type = "STRING")
	private String costDescription;

	@XmlTransferField(name = "ActualReleaseDate", sqlname = "ACTUAL_RELEASE_DATE", type = "DATE")
	private Date actualReleaseDate;

	//private Collection<CostGroup> costGroups = new ArrayList<CostGroup>();


	public IswResourceOther() {
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
	 * @return the iacTransferableIdentity
	 */
	public String getIacTransferableIdentity() {
		return iacTransferableIdentity;
	}

	/**
	 * @param iacTransferableIdentity the iacTransferableIdentity to set
	 */
	public void setIacTransferableIdentity(String iacTransferableIdentity) {
		this.iacTransferableIdentity = iacTransferableIdentity;
	}

	/**
	 * @return the iacId
	 */
	public Long getIacId() {
		return iacId;
	}

	/**
	 * @param iacId the iacId to set
	 */
	public void setIacId(Long iacId) {
		this.iacId = iacId;
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
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the costDescription
	 */
	public String getCostDescription() {
		return costDescription;
	}

	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}

	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}


}
