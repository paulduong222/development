package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIncident", table="isw_incident")
public class IswIncident{

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IncidentName", sqlname="INCIDENT_NAME", type="STRING")
	private String incidentName;

	@XmlTransferField(name = "IncidentDescription", sqlname="INCIDENT_DESCRIPTION", type="STRING")
	private String incidentDescription;

	@XmlTransferField(name = "Nbr", sqlname="NBR", type="STRING")
	private String nbr;

	@XmlTransferField(name = "IrwinIrwinId", sqlname="IRWIN_IRWINID", type="STRING")
	private String irwinIrwinId;

	@XmlTransferField(name = "Location", sqlname="LOCATION", type="STRING")
	private String location;

	@XmlTransferField(name = "Latitude", sqlname="LATITUDE", type="STRING")
	private String latitude;

	@XmlTransferField(name = "Longitude", sqlname="LONGITUDE", type="STRING")
	private String longitude;

	@XmlTransferField(name = "IncidentStartDate", sqlname="INCIDENT_START_DATE", type="DATE")
	private Date incidentStartDate;

	@XmlTransferField(name = "IncidentEndDate", sqlname="INCIDENT_END_DATE", type="DATE")
	private Date incidentEndDate;

	@XmlTransferField(name = "IncidentYear", sqlname="INCIDENT_YEAR", type="INTEGER")
	private Integer incidentYear;

	@XmlTransferField(name = "Restricted", sqlname="RESTRICTED_FLG", type="BOOLEAN")
	private Boolean restricted;

	@XmlTransferField(name = "RossIncidentId", sqlname="ROSS_INCIDENT_ID", type="STRING")
	private String rossIncidentId;

	@XmlTransferField(name = "RossXmlFileId", sqlname="ROSS_XML_FILE_ID", type="LONG")
	private Long rossXmlFileId;

	@XmlTransferField(name = "IapPersonNameOrder", sqlname="IAP_PERSON_NAME_ORDER", type="SHORT", defaultvalue="0")
	private Short iapPersonNameOrder;

	@XmlTransferField(name = "IncludeFilled", sqlname="INCLUDE_FILLED", type="STRING")
	private String includeFilled;

	@XmlTransferField(name = "IapResourceToDisplayFrom", sqlname="IAP_RESOURCE_TO_DISPLAY_FROM", type="SHORT", defaultvalue="0")
	private Short iapResourceToDisplayFrom;

	@XmlTransferField(name = "IapTreeviewDisplay", sqlname="IAP_TREEVIEW_DISPLAY", type="SHORT", defaultvalue="0")
	private Short iapTreeviewDisplay;

	@XmlTransferField(name = "ByDate", sqlname="BY_DATE", type="DATE")
	private Date byDate;

	@XmlTransferField(name = "NbrOfDaysPrior", sqlname="NBR_OF_DAYS_PRIOR", type="SHORT")
	private Short nbrOfDaysPrior;

	@XmlTransferField(name = "IncidentCostDefaultHours", sqlname="COST_DEFAULT_HOURS", type="INTEGER")
	private Integer incidentCostDefaultHours;

	@XmlTransferField(name = "CostAutoRun", sqlname="COST_AUTORUN", type="STRING")
	private String costAutoRun;

	@XmlTransferField(name = "AccrualExtractNumber", sqlname="ACCRUAL_EXTRACT_NUMBER", type="INTEGER")
	private Integer accrualExtractNumber;


	/* Non-Standard Ref Data mappings 
	 * PROCESS THESE FIRST BEFORE OTHER LINKED TABLES */
	@XmlTransferField(name = "IswlKind", type="COMPLEX", target=IswlKind.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlKind> iswlKinds = new ArrayList<IswlKind>();

	@XmlTransferField(name = "IswlJetPort", type="COMPLEX", target=IswlJetPort.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlJetPort> iswlJetPorts = new ArrayList<IswlJetPort>();

	@XmlTransferField(name = "IswlSubGroupCategory", type="COMPLEX", target=IswlSubGroupCategory.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlSubGroupCategory> iswlSubGroupCategorys = new ArrayList<IswlSubGroupCategory>();

	@XmlTransferField(name = "IswlAgency", type="COMPLEX", target=IswlAgency.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlAgency> iswlAgencys = new ArrayList<IswlAgency>();

	@XmlTransferField(name = "IswlAgencyGroup", type="COMPLEX", target=IswlAgencyGroup.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlAgencyGroup> iswlAgencyGroups = new ArrayList<IswlAgencyGroup>();

	@XmlTransferField(name = "IswlSit209", type="COMPLEX", target=IswlSit209.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswlSit209> iswlSit209s = new ArrayList<IswlSit209>();
	
	@XmlTransferField(name = "IswOrganization", type="COMPLEX", target=IswOrganization.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswOrganization> iswOrganizations = new ArrayList<IswOrganization>();
	
	/* End non-standard ref data */
	
	@XmlTransferField(name = "IncidentAccountCode", type="COMPLEX", target=IswIncidentAccountCode.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentAccountCode> incidentAccountCodes = new ArrayList<IswIncidentAccountCode>();
	
	@XmlTransferField(name = "IncidentPrefsOtherFields", type="COMPLEX", target=IswIncidentPrefsOtherFields.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private IswIncidentPrefsOtherFields incidentPrefsOtherFields;
	
	@XmlTransferField(name = "IncidentShift", type="COMPLEX", target=IswIncidentShift.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentShift> incidentShifts = new ArrayList<IswIncidentShift>();

	@XmlTransferField(name = "IncidentCostDefaults", type="COMPLEX", target=IswIncidentCostDefaults.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private IswIncidentCostDefaults incidentCostDefaults;

	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;

	@XmlTransferField(name = "EventTypeTransferableIdentity", alias="eventtypeti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="EventTypeId"
						, disjoined=true, disjoinedtable="iswl_event_type", disjoinedfield="transferable_identity",disjoinedsource="EVENT_TYPE_ID")
	private String eventTypeTransferableIdentity;

	@XmlTransferField(name = "EventTypeId", sqlname="EVENT_TYPE_ID", type="LONG"
						,derived=true, derivedfield="EventTypeTransferableIdentity")
	private Long eventTypeId;
	
	@XmlTransferField(name = "CountrySubdivision", type="COMPLEX", target=IswlCountrySubdivision.class
							, lookupname="Id", sourcename="CountrySubdivisionId")
	private IswlCountrySubdivision countrySubdivision;
	
	@XmlTransferField(name = "CountrySubdivisionId", sqlname="COUNTRY_SUBDIVISION_ID", type="LONG"
						,derived=true, derivedfield="CountrySubdivision")
	private Long countrySubdivisionId;
	
	@XmlTransferField(name = "HomeUnitTransferableIdentity", alias="homeunitti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="HomeUnitId"
						, disjoined=true, disjoinedtable="isw_organization", disjoinedfield="transferable_identity",disjoinedsource="UNIT_ID")
	private String homeUnitTransferableIdentity;
	
	@XmlTransferField(name = "HomeUnitId", sqlname="UNIT_ID", type="LONG"
						,derived=true, derivedfield="HomeUnitTransferableIdentity")
	private Long homeUnitId;

	@XmlTransferField(name = "PdcTransferableIdentity", alias="pdcti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="PdcId"
						, disjoined=true, disjoinedtable="isw_organization", disjoinedfield="transferable_identity",disjoinedsource="PDC_ID")
	private String pdcTransferableIdentity;

	@XmlTransferField(name = "PdcId", sqlname="PDC_ID", type="LONG"
						,derived=true, derivedfield="PdcTransferableIdentity")
	private Long pdcId;

	@XmlTransferField(name = "RateClassTransferableIdentity", alias="rateclsti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="RateClassId"
						, disjoined=true, disjoinedtable="iswl_rate_class", disjoinedfield="transferable_identity",disjoinedsource="RATE_CLASS_ID")
	private String rateClassTransferableIdentity;

	@XmlTransferField(name = "RateClassId", sqlname="RATE_CLASS_ID", type="LONG"
						,derived=true, derivedfield="RateClassTransferableIdentity")
	private Long rateClassId;

	@XmlTransferField(name = "FinancialExport", type="COMPLEX", target=IswFinancialExport.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswFinancialExport> financialExports = new ArrayList<IswFinancialExport>();
	
	@XmlTransferField(name = "IapMasterFrequency", type="COMPLEX", target=IswIapMasterFrequency.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapMasterFrequency> iapMasterFrequencys = new ArrayList<IswIapMasterFrequency>();
	
	@XmlTransferField(name = "IapPositionItemCode", type="COMPLEX", target=IswIapPositionItemCode.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPositionItemCode> iapPositionItemCodes = new ArrayList<IswIapPositionItemCode>();

	@XmlTransferField(name = "IncidentQSKind", type="COMPLEX", target=IswIncidentQSKind.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentQSKind> incidentQSKinds = new ArrayList<IswIncidentQSKind>();

	@XmlTransferField(name = "IncidentPrefs", type="COMPLEX", target=IswIncidentPrefs.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentPrefs> incidentPrefss = new ArrayList<IswIncidentPrefs>();
	
	@XmlTransferField(name = "IncidentQuestion", type="COMPLEX", target=IswIncidentQuestion.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentQuestion> incidentQuestions = new ArrayList<IswIncidentQuestion>();
	

	@XmlTransferField(name = "IncidentContractor", type = "COMPLEX", target=IswIncidentContractor.class
			,lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentContractor> incidentContractors = new ArrayList<IswIncidentContractor>();

	@XmlTransferField(name = "CostGroup", type="COMPLEX", target=IswCostGroup.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswCostGroup> costGroups = new ArrayList<IswCostGroup>();
	
	@XmlTransferField(name = "IncidentResource", type="COMPLEX", target=IswIncidentResource.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentResource> incidentResources = new ArrayList<IswIncidentResource>();
	
	@XmlTransferField(name = "IncidentResourceOther", type="COMPLEX", target=IswIncidentResourceOther.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentResourceOther> incidentResourceOthers = new ArrayList<IswIncidentResourceOther>();

	@XmlTransferField(name = "Projection", type="COMPLEX", target=IswProjection.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswProjection> projections = new ArrayList<IswProjection>();

	@XmlTransferField(name = "CostAccrualExtract", type="COMPLEX", target=IswCostAccrualExtract.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswCostAccrualExtract> costAccrualExtracts = new ArrayList<IswCostAccrualExtract>();

	@XmlTransferField(name = "IapPlan", type="COMPLEX", target=IswIapPlan.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPlan> iapPlans = new ArrayList<IswIapPlan>();

	@XmlTransferField(name = "IncidentCostRate", type="COMPLEX", target=IswIncidentCostRate.class
			, lookupname="IncidentId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentCostRate> incidentCostRates = new ArrayList<IswIncidentCostRate>();
	

	
	/*
	@OneToMany(targetEntity=RestrictedIncidentUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<RestrictedIncidentUser> restrictedIncidentUsers;

	@OneToMany(targetEntity=BranchSettingImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
    @OrderBy("positionNum")
	private Collection<BranchSetting> branchSettings = new ArrayList<BranchSetting>();

	@OneToMany(targetEntity=IncidentBranchImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<IncidentBranch> incidentBranches = new ArrayList<IncidentBranch>();

	@OneToMany(targetEntity=IncidentOrgImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<IncidentOrg> incidentOrgs;
	 */

	public IswIncident(){
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
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}



	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}



	/**
	 * @return the incidentDescription
	 */
	public String getIncidentDescription() {
		return incidentDescription;
	}



	/**
	 * @param incidentDescription the incidentDescription to set
	 */
	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}



	/**
	 * @return the nbr
	 */
	public String getNbr() {
		return nbr;
	}



	/**
	 * @param nbr the nbr to set
	 */
	public void setNbr(String nbr) {
		this.nbr = nbr;
	}



	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}



	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}



	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}



	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}



	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}



	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}



	/**
	 * @return the incidentEndDate
	 */
	public Date getIncidentEndDate() {
		return incidentEndDate;
	}



	/**
	 * @param incidentEndDate the incidentEndDate to set
	 */
	public void setIncidentEndDate(Date incidentEndDate) {
		this.incidentEndDate = incidentEndDate;
	}



	/**
	 * @return the incidentYear
	 */
	public Integer getIncidentYear() {
		return incidentYear;
	}



	/**
	 * @param incidentYear the incidentYear to set
	 */
	public void setIncidentYear(Integer incidentYear) {
		this.incidentYear = incidentYear;
	}



	/**
	 * @return the restricted
	 */
	public Boolean getRestricted() {
		return restricted;
	}



	/**
	 * @param restricted the restricted to set
	 */
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}



	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}



	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}



	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}



	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}



	/**
	 * @return the iapPersonNameOrder
	 */
	public Short getIapPersonNameOrder() {
		return iapPersonNameOrder;
	}



	/**
	 * @param iapPersonNameOrder the iapPersonNameOrder to set
	 */
	public void setIapPersonNameOrder(Short iapPersonNameOrder) {
		this.iapPersonNameOrder = iapPersonNameOrder;
	}



	/**
	 * @return the includeFilled
	 */
	public String getIncludeFilled() {
		return includeFilled;
	}



	/**
	 * @param includeFilled the includeFilled to set
	 */
	public void setIncludeFilled(String includeFilled) {
		this.includeFilled = includeFilled;
	}



	/**
	 * @return the iapResourceToDisplayFrom
	 */
	public Short getIapResourceToDisplayFrom() {
		return iapResourceToDisplayFrom;
	}



	/**
	 * @param iapResourceToDisplayFrom the iapResourceToDisplayFrom to set
	 */
	public void setIapResourceToDisplayFrom(Short iapResourceToDisplayFrom) {
		this.iapResourceToDisplayFrom = iapResourceToDisplayFrom;
	}



	/**
	 * @return the iapTreeviewDisplay
	 */
	public Short getIapTreeviewDisplay() {
		return iapTreeviewDisplay;
	}



	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(Short iapTreeviewDisplay) {
		this.iapTreeviewDisplay = iapTreeviewDisplay;
	}



	/**
	 * @return the byDate
	 */
	public Date getByDate() {
		return byDate;
	}



	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate) {
		this.byDate = byDate;
	}



	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior() {
		return nbrOfDaysPrior;
	}



	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior) {
		this.nbrOfDaysPrior = nbrOfDaysPrior;
	}



	/**
	 * @return the incidentCostDefaultHours
	 */
	public Integer getIncidentCostDefaultHours() {
		return incidentCostDefaultHours;
	}



	/**
	 * @param incidentCostDefaultHours the incidentCostDefaultHours to set
	 */
	public void setIncidentCostDefaultHours(Integer incidentCostDefaultHours) {
		this.incidentCostDefaultHours = incidentCostDefaultHours;
	}



	/**
	 * @return the costAutoRun
	 */
	public String getCostAutoRun() {
		return costAutoRun;
	}



	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(String costAutoRun) {
		this.costAutoRun = costAutoRun;
	}



	/**
	 * @return the accrualExtractNumber
	 */
	public Integer getAccrualExtractNumber() {
		return accrualExtractNumber;
	}



	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Integer accrualExtractNumber) {
		this.accrualExtractNumber = accrualExtractNumber;
	}



	/**
	 * @return the iswlKinds
	 */
	public Collection<IswlKind> getIswlKinds() {
		return iswlKinds;
	}



	/**
	 * @param iswlKinds the iswlKinds to set
	 */
	public void setIswlKinds(Collection<IswlKind> iswlKinds) {
		this.iswlKinds = iswlKinds;
	}



	/**
	 * @return the iswlJetPorts
	 */
	public Collection<IswlJetPort> getIswlJetPorts() {
		return iswlJetPorts;
	}



	/**
	 * @param iswlJetPorts the iswlJetPorts to set
	 */
	public void setIswlJetPorts(Collection<IswlJetPort> iswlJetPorts) {
		this.iswlJetPorts = iswlJetPorts;
	}



	/**
	 * @return the iswlSubGroupCategorys
	 */
	public Collection<IswlSubGroupCategory> getIswlSubGroupCategorys() {
		return iswlSubGroupCategorys;
	}



	/**
	 * @param iswlSubGroupCategorys the iswlSubGroupCategorys to set
	 */
	public void setIswlSubGroupCategorys(
			Collection<IswlSubGroupCategory> iswlSubGroupCategorys) {
		this.iswlSubGroupCategorys = iswlSubGroupCategorys;
	}



	/**
	 * @return the iswlAgencys
	 */
	public Collection<IswlAgency> getIswlAgencys() {
		return iswlAgencys;
	}



	/**
	 * @param iswlAgencys the iswlAgencys to set
	 */
	public void setIswlAgencys(Collection<IswlAgency> iswlAgencys) {
		this.iswlAgencys = iswlAgencys;
	}



	/**
	 * @return the iswlAgencyGroups
	 */
	public Collection<IswlAgencyGroup> getIswlAgencyGroups() {
		return iswlAgencyGroups;
	}



	/**
	 * @param iswlAgencyGroups the iswlAgencyGroups to set
	 */
	public void setIswlAgencyGroups(Collection<IswlAgencyGroup> iswlAgencyGroups) {
		this.iswlAgencyGroups = iswlAgencyGroups;
	}



	/**
	 * @return the iswlSit209s
	 */
	public Collection<IswlSit209> getIswlSit209s() {
		return iswlSit209s;
	}



	/**
	 * @param iswlSit209s the iswlSit209s to set
	 */
	public void setIswlSit209s(Collection<IswlSit209> iswlSit209s) {
		this.iswlSit209s = iswlSit209s;
	}



	/**
	 * @return the iswOrganizations
	 */
	public Collection<IswOrganization> getIswOrganizations() {
		return iswOrganizations;
	}



	/**
	 * @param iswOrganizations the iswOrganizations to set
	 */
	public void setIswOrganizations(Collection<IswOrganization> iswOrganizations) {
		this.iswOrganizations = iswOrganizations;
	}



	/**
	 * @return the incidentAccountCodes
	 */
	public Collection<IswIncidentAccountCode> getIncidentAccountCodes() {
		return incidentAccountCodes;
	}



	/**
	 * @param incidentAccountCodes the incidentAccountCodes to set
	 */
	public void setIncidentAccountCodes(
			Collection<IswIncidentAccountCode> incidentAccountCodes) {
		this.incidentAccountCodes = incidentAccountCodes;
	}



	/**
	 * @return the incidentPrefsOtherFields
	 */
	public IswIncidentPrefsOtherFields getIncidentPrefsOtherFields() {
		return incidentPrefsOtherFields;
	}



	/**
	 * @param incidentPrefsOtherFields the incidentPrefsOtherFields to set
	 */
	public void setIncidentPrefsOtherFields(
			IswIncidentPrefsOtherFields incidentPrefsOtherFields) {
		this.incidentPrefsOtherFields = incidentPrefsOtherFields;
	}



	/**
	 * @return the incidentShifts
	 */
	public Collection<IswIncidentShift> getIncidentShifts() {
		return incidentShifts;
	}



	/**
	 * @param incidentShifts the incidentShifts to set
	 */
	public void setIncidentShifts(Collection<IswIncidentShift> incidentShifts) {
		this.incidentShifts = incidentShifts;
	}



	/**
	 * @return the incidentCostDefaults
	 */
	public IswIncidentCostDefaults getIncidentCostDefaults() {
		return incidentCostDefaults;
	}



	/**
	 * @param incidentCostDefaults the incidentCostDefaults to set
	 */
	public void setIncidentCostDefaults(IswIncidentCostDefaults incidentCostDefaults) {
		this.incidentCostDefaults = incidentCostDefaults;
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
	 * @return the eventTypeTransferableIdentity
	 */
	public String getEventTypeTransferableIdentity() {
		return eventTypeTransferableIdentity;
	}



	/**
	 * @param eventTypeTransferableIdentity the eventTypeTransferableIdentity to set
	 */
	public void setEventTypeTransferableIdentity(
			String eventTypeTransferableIdentity) {
		this.eventTypeTransferableIdentity = eventTypeTransferableIdentity;
	}



	/**
	 * @return the eventTypeId
	 */
	public Long getEventTypeId() {
		return eventTypeId;
	}



	/**
	 * @param eventTypeId the eventTypeId to set
	 */
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}



	/**
	 * @return the countrySubdivision
	 */
	public IswlCountrySubdivision getCountrySubdivision() {
		return countrySubdivision;
	}



	/**
	 * @param countrySubdivision the countrySubdivision to set
	 */
	public void setCountrySubdivision(IswlCountrySubdivision countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}



	/**
	 * @return the countrySubdivisionId
	 */
	public Long getCountrySubdivisionId() {
		return countrySubdivisionId;
	}



	/**
	 * @param countrySubdivisionId the countrySubdivisionId to set
	 */
	public void setCountrySubdivisionId(Long countrySubdivisionId) {
		this.countrySubdivisionId = countrySubdivisionId;
	}



	/**
	 * @return the homeUnitTransferableIdentity
	 */
	public String getHomeUnitTransferableIdentity() {
		return homeUnitTransferableIdentity;
	}



	/**
	 * @param homeUnitTransferableIdentity the homeUnitTransferableIdentity to set
	 */
	public void setHomeUnitTransferableIdentity(String homeUnitTransferableIdentity) {
		this.homeUnitTransferableIdentity = homeUnitTransferableIdentity;
	}



	/**
	 * @return the homeUnitId
	 */
	public Long getHomeUnitId() {
		return homeUnitId;
	}



	/**
	 * @param homeUnitId the homeUnitId to set
	 */
	public void setHomeUnitId(Long homeUnitId) {
		this.homeUnitId = homeUnitId;
	}



	/**
	 * @return the pdcTransferableIdentity
	 */
	public String getPdcTransferableIdentity() {
		return pdcTransferableIdentity;
	}



	/**
	 * @param pdcTransferableIdentity the pdcTransferableIdentity to set
	 */
	public void setPdcTransferableIdentity(String pdcTransferableIdentity) {
		this.pdcTransferableIdentity = pdcTransferableIdentity;
	}



	/**
	 * @return the pdcId
	 */
	public Long getPdcId() {
		return pdcId;
	}



	/**
	 * @param pdcId the pdcId to set
	 */
	public void setPdcId(Long pdcId) {
		this.pdcId = pdcId;
	}



	/**
	 * @return the rateClassTransferableIdentity
	 */
	public String getRateClassTransferableIdentity() {
		return rateClassTransferableIdentity;
	}



	/**
	 * @param rateClassTransferableIdentity the rateClassTransferableIdentity to set
	 */
	public void setRateClassTransferableIdentity(
			String rateClassTransferableIdentity) {
		this.rateClassTransferableIdentity = rateClassTransferableIdentity;
	}



	/**
	 * @return the rateClassId
	 */
	public Long getRateClassId() {
		return rateClassId;
	}



	/**
	 * @param rateClassId the rateClassId to set
	 */
	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}



	/**
	 * @return the iapMasterFrequencys
	 */
	public Collection<IswIapMasterFrequency> getIapMasterFrequencys() {
		return iapMasterFrequencys;
	}



	/**
	 * @param iapMasterFrequencys the iapMasterFrequencys to set
	 */
	public void setIapMasterFrequencys(
			Collection<IswIapMasterFrequency> iapMasterFrequencys) {
		this.iapMasterFrequencys = iapMasterFrequencys;
	}



	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IswIapPositionItemCode> getIapPositionItemCodes() {
		return iapPositionItemCodes;
	}



	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(
			Collection<IswIapPositionItemCode> iapPositionItemCodes) {
		this.iapPositionItemCodes = iapPositionItemCodes;
	}



	/**
	 * @return the incidentQSKinds
	 */
	public Collection<IswIncidentQSKind> getIncidentQSKinds() {
		return incidentQSKinds;
	}



	/**
	 * @param incidentQSKinds the incidentQSKinds to set
	 */
	public void setIncidentQSKinds(Collection<IswIncidentQSKind> incidentQSKinds) {
		this.incidentQSKinds = incidentQSKinds;
	}



	/**
	 * @return the incidentPrefss
	 */
	public Collection<IswIncidentPrefs> getIncidentPrefss() {
		return incidentPrefss;
	}



	/**
	 * @param incidentPrefss the incidentPrefss to set
	 */
	public void setIncidentPrefss(Collection<IswIncidentPrefs> incidentPrefss) {
		this.incidentPrefss = incidentPrefss;
	}



	/**
	 * @return the incidentQuestions
	 */
	public Collection<IswIncidentQuestion> getIncidentQuestions() {
		return incidentQuestions;
	}



	/**
	 * @param incidentQuestions the incidentQuestions to set
	 */
	public void setIncidentQuestions(
			Collection<IswIncidentQuestion> incidentQuestions) {
		this.incidentQuestions = incidentQuestions;
	}



	/**
	 * @return the incidentContractors
	 */
	public Collection<IswIncidentContractor> getIncidentContractors() {
		return incidentContractors;
	}



	/**
	 * @param incidentContractors the incidentContractors to set
	 */
	public void setIncidentContractors(
			Collection<IswIncidentContractor> incidentContractors) {
		this.incidentContractors = incidentContractors;
	}



	/**
	 * @return the incidentResources
	 */
	public Collection<IswIncidentResource> getIncidentResources() {
		return incidentResources;
	}



	/**
	 * @param incidentResources the incidentResources to set
	 */
	public void setIncidentResources(
			Collection<IswIncidentResource> incidentResources) {
		this.incidentResources = incidentResources;
	}



	/**
	 * @return the costGroups
	 */
	public Collection<IswCostGroup> getCostGroups() {
		return costGroups;
	}



	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<IswCostGroup> costGroups) {
		this.costGroups = costGroups;
	}



	/**
	 * @return the projections
	 */
	public Collection<IswProjection> getProjections() {
		return projections;
	}



	/**
	 * @param projections the projections to set
	 */
	public void setProjections(Collection<IswProjection> projections) {
		this.projections = projections;
	}



	/**
	 * @return the costAccrualExtracts
	 */
	public Collection<IswCostAccrualExtract> getCostAccrualExtracts() {
		return costAccrualExtracts;
	}



	/**
	 * @param costAccrualExtracts the costAccrualExtracts to set
	 */
	public void setCostAccrualExtracts(
			Collection<IswCostAccrualExtract> costAccrualExtracts) {
		this.costAccrualExtracts = costAccrualExtracts;
	}



	/**
	 * @return the iapPlans
	 */
	public Collection<IswIapPlan> getIapPlans() {
		return iapPlans;
	}



	/**
	 * @param iapPlans the iapPlans to set
	 */
	public void setIapPlans(Collection<IswIapPlan> iapPlans) {
		this.iapPlans = iapPlans;
	}



	/**
	 * @return the incidentCostRates
	 */
	public Collection<IswIncidentCostRate> getIncidentCostRates() {
		return incidentCostRates;
	}



	/**
	 * @param incidentCostRates the incidentCostRates to set
	 */
	public void setIncidentCostRates(
			Collection<IswIncidentCostRate> incidentCostRates) {
		this.incidentCostRates = incidentCostRates;
	}



	/**
	 * @return the financialExports
	 */
	public Collection<IswFinancialExport> getFinancialExports() {
		return financialExports;
	}



	/**
	 * @param financialExports the financialExports to set
	 */
	public void setFinancialExports(
			Collection<IswFinancialExport> financialExports) {
		this.financialExports = financialExports;
	}



	/**
	 * @return the incidentResourceOthers
	 */
	public Collection<IswIncidentResourceOther> getIncidentResourceOthers() {
		return incidentResourceOthers;
	}



	/**
	 * @param incidentResourceOthers the incidentResourceOthers to set
	 */
	public void setIncidentResourceOthers(
			Collection<IswIncidentResourceOther> incidentResourceOthers) {
		this.incidentResourceOthers = incidentResourceOthers;
	}



	/**
	 * @return the irwinIrwinId
	 */
	public String getIrwinIrwinId() {
		return irwinIrwinId;
	}



	/**
	 * @param irwinIrwinId the irwinIrwinId to set
	 */
	public void setIrwinIrwinId(String irwinIrwinId) {
		this.irwinIrwinId = irwinIrwinId;
	}



}
