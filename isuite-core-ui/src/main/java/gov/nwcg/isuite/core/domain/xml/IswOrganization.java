package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswOrganization", table = "isw_organization")
public class IswOrganization  {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_ORGANIZATION", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "OrganizationType", sqlname = "ORGANIZATION_TYPE", type = "STRING",nullwhenempty=true)
	private String organizationType;

	@XmlTransferField(name = "Name", sqlname = "ORGANIZATION_NAME", type = "STRING")
	private String name;

	@XmlTransferField(name = "UnitCode", sqlname = "UNIT_CODE", type = "STRING")
	private String unitCode;

	@XmlTransferField(name = "CountrySubAbbreviation", sqlname = "COUNTRY_SUBDIVISION", type = "STRING")
	private String countrySubAbbreviation;

	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;

	@XmlTransferField(name = "DispatchCenter", sqlname = "IS_DISPATCH_CENTER", type="BOOLEAN")
	private Boolean dispatchCenter;

	@XmlTransferField(name = "SupplyCache", sqlname = "IS_SUPPLY_CACHE", type="BOOLEAN")
	private Boolean supplyCache;

	@XmlTransferField(name = "Standard", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "Local", sqlname = "IS_LOCAL", type="BOOLEAN")
	private Boolean local;

	//private Collection<Organization> dispatchCenters;

	@XmlTransferField(name = "IncidentId", sqlname = "INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname = "INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type = "STRING")
	private String active;


	public IswOrganization(){}


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
	 * @return the organizationType
	 */
	public String getOrganizationType() {
		return organizationType;
	}


	/**
	 * @param organizationType the organizationType to set
	 */
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}


	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}


	/**
	 * @return the countrySubAbbreviation
	 */
	public String getCountrySubAbbreviation() {
		return countrySubAbbreviation;
	}


	/**
	 * @param countrySubAbbreviation the countrySubAbbreviation to set
	 */
	public void setCountrySubAbbreviation(String countrySubAbbreviation) {
		this.countrySubAbbreviation = countrySubAbbreviation;
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
	 * @return the dispatchCenter
	 */
	public Boolean getDispatchCenter() {
		return dispatchCenter;
	}


	/**
	 * @param dispatchCenter the dispatchCenter to set
	 */
	public void setDispatchCenter(Boolean dispatchCenter) {
		this.dispatchCenter = dispatchCenter;
	}


	/**
	 * @return the supplyCache
	 */
	public Boolean getSupplyCache() {
		return supplyCache;
	}


	/**
	 * @param supplyCache the supplyCache to set
	 */
	public void setSupplyCache(Boolean supplyCache) {
		this.supplyCache = supplyCache;
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
	 * @return the local
	 */
	public Boolean getLocal() {
		return local;
	}


	/**
	 * @param local the local to set
	 */
	public void setLocal(Boolean local) {
		this.local = local;
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


}
