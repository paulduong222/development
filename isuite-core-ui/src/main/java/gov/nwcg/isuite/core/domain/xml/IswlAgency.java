package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswlAgency", table = "iswl_agency")
public class IswlAgency {
	
	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_AGENCY", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AgencyCode", sqlname = "AGENCY_CODE", type = "STRING")
	private String agencyCode;

	@XmlTransferField(name = "AgencyName", sqlname = "AGENCY_NAME", type = "STRING")
	private String agencyName;

	@XmlTransferField(name = "AgencyType", sqlname = "AGENCY_TYPE", type = "STRING")
	private String agencyType;

	@XmlTransferField(name = "Standard", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "State", sqlname = "IS_STATE", type="BOOLEAN")
	private Boolean state;
	
	@XmlTransferField(name = "AgencyGroup", type="COMPLEX", target=IswlAgencyGroup.class
						, lookupname="Id", sourcename="AgencyGroupId")
	private IswlAgencyGroup agencyGroup;

	@XmlTransferField(name = "AgencyGroupId", sqlname = "AGENCY_GROUP_ID",  type="LONG"
						,derived=true, derivedfield="AgencyGroup")
	private Long agencyGroupId;

	@XmlTransferField(name = "RateGroup", type="COMPLEX", target=IswlRateGroup.class
						, lookupname="Id", sourcename="RateGroupId")
	private IswlRateGroup rateGroup;

	@XmlTransferField(name = "RateGroupId", sqlname = "RATE_GROUP_ID", type="LONG"
						,derived=true, derivedfield="RateGroup")
	private Long rateGroupId;

	@XmlTransferField(name = "IncidentId", sqlname = "INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname = "INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type = "STRING")
	private String active;

	@XmlTransferField(name = "CreatedBy", sqlname = "CREATED_BY", type = "STRING")
	private String createdBy;

	@XmlTransferField(name = "CreatedDate", sqlname = "CREATED_DATE", type = "DATE")
	private Date createdDate;
	
	@XmlTransferField(name = "LastModifiedBy", sqlname = "LAST_MODIFIED_BY", type = "STRING")
	private String lastModifiedBy;

	@XmlTransferField(name = "LastModifiedDate", sqlname = "LAST_MODIFIED_DATE", type = "DATE")
	private Date lastModifiedDate;

	public IswlAgency() {
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
	 * @return the agencyCode
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * @param agencyCode the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName the agencyName to set
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * @return the agencyType
	 */
	public String getAgencyType() {
		return agencyType;
	}

	/**
	 * @param agencyType the agencyType to set
	 */
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the state
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Boolean state) {
		this.state = state;
	}

	/**
	 * @return the agencyGroup
	 */
	public IswlAgencyGroup getAgencyGroup() {
		return agencyGroup;
	}

	/**
	 * @param agencyGroup the agencyGroup to set
	 */
	public void setAgencyGroup(IswlAgencyGroup agencyGroup) {
		this.agencyGroup = agencyGroup;
	}

	/**
	 * @return the agencyGroupId
	 */
	public Long getAgencyGroupId() {
		return agencyGroupId;
	}

	/**
	 * @param agencyGroupId the agencyGroupId to set
	 */
	public void setAgencyGroupId(Long agencyGroupId) {
		this.agencyGroupId = agencyGroupId;
	}

	/**
	 * @return the rateGroup
	 */
	public IswlRateGroup getRateGroup() {
		return rateGroup;
	}

	/**
	 * @param rateGroup the rateGroup to set
	 */
	public void setRateGroup(IswlRateGroup rateGroup) {
		this.rateGroup = rateGroup;
	}

	/**
	 * @return the rateGroupId
	 */
	public Long getRateGroupId() {
		return rateGroupId;
	}

	/**
	 * @param rateGroupId the rateGroupId to set
	 */
	public void setRateGroupId(Long rateGroupId) {
		this.rateGroupId = rateGroupId;
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
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


}
