package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIncidentGroup", table="isw_incident_group")
public class IswIncidentGroup {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "GroupName", sqlname="GROUP_NAME", type="STRING")
	private String groupName;

	@XmlTransferField(name = "CostAutoRun", sqlname="COST_AUTORUN", type="STRING", defaultvalue="N")
	private String costAutoRun = "N";

	@XmlTransferField(name = "IncidentGroupCostDefaultHours", sqlname="COST_DEFAULT_HOURS", type="INTEGER")
	private Integer incidentGroupCostDefaultHours;

	@XmlTransferField(name = "IapPersonNameOrder", sqlname="IAP_PERSON_NAME_ORDER", type="SHORT", defaultvalue="0")
	private Short iapPersonNameOrder;

	@XmlTransferField(name = "IncludeFilled", sqlname="INCLUDE_FILLED", type="STRING", defaultvalue="N")
	private String includeFilled;

	@XmlTransferField(name = "PrimaryIncidentTransferableIdentity", alias="piti",  type="STRING"
					, lookupname="TransferableIdentity", sourcename="PrimaryIncidentId"
					, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="PRIMARY_INCIDENT_ID")
	private String incidentTransferableIdentity;
	
	@XmlTransferField(name = "PrimaryIncidentId", sqlname="PRIMARY_INCIDENT_ID", type="Long"
						,derived=true, derivedfield="PrimaryIncidentTransferableIdentity")
	private Long primaryIncidentId;

	@XmlTransferField(name = "AccrualExtractNumber", sqlname="SEQUENCE_NUMBER", type="SHORT")
	private Short accrualExtractNumber;
	
	@XmlTransferField(name = "IapResourceToDisplayFrom", sqlname="IAP_RESOURCE_TO_DISPLAY_FROM", type="SHORT", defaultvalue="0")
	private Short iapResourceToDisplayFrom;

	@XmlTransferField(name = "IapTreeviewDisplay", sqlname="IAP_TREEVIEW_DISPLAY", type="SHORT", defaultvalue="0")
	private Short iapTreeviewDisplay;

	@XmlTransferField(name = "ByDate", sqlname="BY_DATE", type="DATE")
	private Date byDate;

	@XmlTransferField(name = "NbrOfDaysPrior", sqlname="NBR_OF_DAYS_PRIOR", type="SHORT")
	private Short nbrOfDaysPrior;
	
	@XmlTransferField(name = "IncidentGroupQSKind", type="COMPLEX", target=IswIncidentGroupQSKind.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private Collection<IswIncidentGroupQSKind> incidentGroupQSKinds = new ArrayList<IswIncidentGroupQSKind>();
	
	@XmlTransferField(name = "IncidentGroupQuestion", type="COMPLEX", target=IswIncidentGroupQuestion.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private Collection<IswIncidentGroupQuestion> incidentGroupQuestions = new ArrayList<IswIncidentGroupQuestion>();

	@XmlTransferField(name = "IncidentGroupPrefs", type="COMPLEX", target=IswIncidentGroupPrefs.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private Collection<IswIncidentGroupPrefs> incidentGroupPrefss = new ArrayList<IswIncidentGroupPrefs>();
	
	@XmlTransferField(name = "IapMasterFrequency", type="COMPLEX", target=IswIapMasterFrequency.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private Collection<IswIapMasterFrequency> iapMasterFrequencys = new ArrayList<IswIapMasterFrequency>();

	@XmlTransferField(name = "IapPositionItemCode", type="COMPLEX", target=IswIapPositionItemCode.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private Collection<IswIapPositionItemCode> iapPositionItemCodes = new ArrayList<IswIapPositionItemCode>();

	@XmlTransferField(name = "IncidentPrefsOtherFields", type="COMPLEX", target=IswIncidentPrefsOtherFields.class
						, lookupname="IncidentGroupId", sourcename="Id"
						, cascade=true)
	private IswIncidentPrefsOtherFields incidentPrefsOtherFields;

	@XmlTransferField(name = "IncidentGroupIncident", type="COMPLEX", target=IswIncidentGroupIncident.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentGroupIncident> incidentGroupIncidents = new ArrayList<IswIncidentGroupIncident>();
	
	@XmlTransferField(name = "CostAccrualExtract", type="COMPLEX", target=IswCostAccrualExtract.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswCostAccrualExtract> costAccrualExtracts = new ArrayList<IswCostAccrualExtract>();


	/*
	@OneToMany(targetEntity=IncidentCostRateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<IncidentCostRate> incidentCostRates;

	@OneToMany(targetEntity=ProjectionImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<Projection> projections;

	@OneToMany(targetEntity=CostAccrualExtractImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<CostAccrualExtract> costAccrualExtracts;

	@OneToMany(targetEntity=BranchSettingImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	@OrderBy("positionNum")
	private Collection<BranchSetting> branchSettings = new ArrayList<BranchSetting>();

	 */
	
	/* Non-Standard Ref Data mappings */
	@XmlTransferField(name = "IswlKind", type="COMPLEX", target=IswlKind.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlKind> iswlKinds = new ArrayList<IswlKind>();

	@XmlTransferField(name = "IswlJetPort", type="COMPLEX", target=IswlJetPort.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlJetPort> iswlJetPorts = new ArrayList<IswlJetPort>();

	@XmlTransferField(name = "IswlSubGroupCategory", type="COMPLEX", target=IswlSubGroupCategory.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlSubGroupCategory> iswlSubGroupCategorys = new ArrayList<IswlSubGroupCategory>();

	@XmlTransferField(name = "IswlAgency", type="COMPLEX", target=IswlAgency.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlAgency> iswlAgencys = new ArrayList<IswlAgency>();

	@XmlTransferField(name = "IswlAgencyGroup", type="COMPLEX", target=IswlAgencyGroup.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlAgencyGroup> iswlAgencyGroups = new ArrayList<IswlAgencyGroup>();

	@XmlTransferField(name = "IswlSit209", type="COMPLEX", target=IswlSit209.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswlSit209> iswlSit209s = new ArrayList<IswlSit209>();
	
	@XmlTransferField(name = "IswOrganization", type="COMPLEX", target=IswOrganization.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswOrganization> iswOrganizations = new ArrayList<IswOrganization>();
	
	@XmlTransferField(name = "FinancialExport", type="COMPLEX", target=IswFinancialExport.class
			, lookupname="IncidentGroupId", sourcename="Id"
			, cascade=true)
	private Collection<IswFinancialExport> financialExports = new ArrayList<IswFinancialExport>();

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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	 * @return the incidentGroupCostDefaultHours
	 */
	public Integer getIncidentGroupCostDefaultHours() {
		return incidentGroupCostDefaultHours;
	}

	/**
	 * @param incidentGroupCostDefaultHours the incidentGroupCostDefaultHours to set
	 */
	public void setIncidentGroupCostDefaultHours(
			Integer incidentGroupCostDefaultHours) {
		this.incidentGroupCostDefaultHours = incidentGroupCostDefaultHours;
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
	 * @return the primaryIncidentId
	 */
	public Long getPrimaryIncidentId() {
		return primaryIncidentId;
	}

	/**
	 * @param primaryIncidentId the primaryIncidentId to set
	 */
	public void setPrimaryIncidentId(Long primaryIncidentId) {
		this.primaryIncidentId = primaryIncidentId;
	}

	/**
	 * @return the accrualExtractNumber
	 */
	public Short getAccrualExtractNumber() {
		return accrualExtractNumber;
	}

	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Short accrualExtractNumber) {
		this.accrualExtractNumber = accrualExtractNumber;
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
	 * @return the incidentGroupQSKinds
	 */
	public Collection<IswIncidentGroupQSKind> getIncidentGroupQSKinds() {
		return incidentGroupQSKinds;
	}

	/**
	 * @param incidentGroupQSKinds the incidentGroupQSKinds to set
	 */
	public void setIncidentGroupQSKinds(
			Collection<IswIncidentGroupQSKind> incidentGroupQSKinds) {
		this.incidentGroupQSKinds = incidentGroupQSKinds;
	}

	/**
	 * @return the incidentGroupQuestions
	 */
	public Collection<IswIncidentGroupQuestion> getIncidentGroupQuestions() {
		return incidentGroupQuestions;
	}

	/**
	 * @param incidentGroupQuestions the incidentGroupQuestions to set
	 */
	public void setIncidentGroupQuestions(
			Collection<IswIncidentGroupQuestion> incidentGroupQuestions) {
		this.incidentGroupQuestions = incidentGroupQuestions;
	}

	/**
	 * @return the incidentGroupPrefss
	 */
	public Collection<IswIncidentGroupPrefs> getIncidentGroupPrefss() {
		return incidentGroupPrefss;
	}

	/**
	 * @param incidentGroupPrefss the incidentGroupPrefss to set
	 */
	public void setIncidentGroupPrefss(
			Collection<IswIncidentGroupPrefs> incidentGroupPrefss) {
		this.incidentGroupPrefss = incidentGroupPrefss;
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
	 * @return the incidentGroupIncidents
	 */
	public Collection<IswIncidentGroupIncident> getIncidentGroupIncidents() {
		return incidentGroupIncidents;
	}

	/**
	 * @param incidentGroupIncidents the incidentGroupIncidents to set
	 */
	public void setIncidentGroupIncidents(
			Collection<IswIncidentGroupIncident> incidentGroupIncidents) {
		this.incidentGroupIncidents = incidentGroupIncidents;
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
	 * @param costAccrualExtracts the costAccrualExtracts to set
	 */
	public void setCostAccrualExtracts(Collection<IswCostAccrualExtract> costAccrualExtracts) {
		this.costAccrualExtracts = costAccrualExtracts;
	}

	/**
	 * @return the costAccrualExtracts
	 */
	public Collection<IswCostAccrualExtract> getCostAccrualExtracts() {
		return costAccrualExtracts;
	}

	
}
