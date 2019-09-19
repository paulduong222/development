package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswResource", table = "isw_resource")
public class IswResource {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "ResourceName", sqlname="RESOURCE_NAME", type="STRING")
	private String resourceName;

	@XmlTransferField(name = "FirstName", sqlname="FIRST_NAME", type="STRING")
	private String firstName;

	@XmlTransferField(name = "LastName", sqlname="LAST_NAME", type="STRING")
	private String lastName;
	
	@XmlTransferField(name = "ParentResource", type="COMPLEX", target=IswResource.class
						, lookupname="Id", sourcename="ParentResourceId")
	private IswResource parentResource;

	@XmlTransferField(name = "ParentResourceId", sqlname="PARENT_RESOURCE_ID", type="LONG"
					,derived=true, derivedfield="ParentResource")
	private Long parentResourceId;
	
	@XmlTransferField(name = "PermanentResource", type="COMPLEX", target=IswResource.class
			, lookupname="Id", sourcename="PermanentResourceId")
	private IswResource permanentResource;

	@XmlTransferField(name = "PermanentResourceId", sqlname="PERMANENT_RESOURCE_ID", type="LONG"
		,derived=true, derivedfield="PermanentResource")
	private Long permanentResourceId;

//	private Collection<IswResource> childResources = new ArrayList<IswResource>();
	
	@XmlTransferField(name = "OrgTransferableIdentity", alias="orgti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="OrgId"
						, disjoined=true, disjoinedtable="isw_organization", disjoinedfield="transferable_identity",disjoinedsource="ORGANIZATION_ID")
	private String orgTransferableIdentity;
	
	@XmlTransferField(name = "OrgId", sqlname="ORGANIZATION_ID", type="LONG"
				,derived=true, derivedfield="OrgTransferableIdentity")
	private Long orgId;
	
	@XmlTransferField(name = "PdcTransferableIdentity", alias="pdcti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="PdcId"
						, disjoined=true, disjoinedtable="isw_organization", disjoinedfield="transferable_identity",disjoinedsource="PRIMARY_DISP_CTR_ID")
	private String pdcTransferableIdentity;
	
	@XmlTransferField(name = "PdcId", sqlname="PRIMARY_DISP_CTR_ID", type="LONG"
				,derived=true, derivedfield="PdcTransferableIdentity")
	private Long pdcId;
	
	@XmlTransferField(name = "ResourceClassification", sqlname="RESOURCE_CLASSIFICATION", type="STRING",nullwhenempty=true)
	private String resourceClassification;

	@XmlTransferField(name = "ResourceStatus", sqlname="RESOURCE_STATUS", type="STRING",nullwhenempty=true)
	private String resourceStatus;
	
	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;

	@XmlTransferField(name = "Person", sqlname="IS_PERSON", type = "BOOLEAN")
	private Boolean person;

	@XmlTransferField(name = "Contracted", sqlname="IS_CONTRACTED", type = "BOOLEAN")
	private Boolean contracted;

	@XmlTransferField(name = "Leader", sqlname = "IS_LEADER", type = "BOOLEAN")
	private Boolean leader;

	@XmlTransferField(name = "Component", sqlname="IS_COMPONENT", type = "BOOLEAN")
	private Boolean component=false;

	@XmlTransferField(name = "NameOnPictureId", sqlname="NAME_ON_PICTURE_ID", type="STRING")
	private String nameOnPictureId;

	@XmlTransferField(name = "ContactName", sqlname="CONTACT_NAME", type="STRING")
	private String contactName;

	@XmlTransferField(name = "Phone", sqlname="PHONE", type="STRING")
	private String phone;

	@XmlTransferField(name = "Email", sqlname="EMAIL", type="STRING")
	private String email;

	@XmlTransferField(name = "Other1", sqlname="OTHER_1", type="STRING")
	private String other1;

	@XmlTransferField(name = "Other2", sqlname="OTHER_2", type="STRING")
	private String other2;

	@XmlTransferField(name = "Other3", sqlname="OTHER_3", type="STRING")
	private String other3;

	@XmlTransferField(name = "Enabled", sqlname="IS_ENABLED", type = "BOOLEAN")
	private Boolean enabled;

	@XmlTransferField(name = "Permanent", sqlname="IS_PERMANENT", type = "BOOLEAN")
	private Boolean permanent;

	@XmlTransferField(name = "LeaderType", sqlname="LEADER_TYPE", type = "INTEGER")
	private Integer leaderType;

	@XmlTransferField(name = "EmploymentType", sqlname = "EMPLOYMENT_TYPE", type="STRING",nullwhenempty=true)
	private String employmentType;

	@XmlTransferField(name = "NumberOfPersonnel", sqlname="NUMBER_OF_PERSONNEL", type = "LONG")
	private Long numberOfPersonnel;

	@XmlTransferField(name = "DeletedDate", sqlname="DELETED_DATE", type="DATE")
	private Date deletedDate;
	
	@XmlTransferField(name = "RossResId", sqlname="ROSS_RES_ID", type = "LONG")
	private Long rossResId;

	@XmlTransferField(name = "Contractor", type="COMPLEX", target=IswContractor.class
				, lookupname="Id", sourcename="DefaultContractorId")
	private IswContractor contractor;
	
	@XmlTransferField(name = "DefaultContractorId", sqlname="DEFAULT_CONTRACTOR_ID", type="LONG"
			,derived=true, derivedfield="Contractor")
	private Long defaultContractorId;
	
	@XmlTransferField(name = "ContractorAgreement", type="COMPLEX", target=IswContractorAgreement.class
				, lookupname="Id", sourcename="DefaultAgreementId")
	private IswContractorAgreement contractorAgreement;
	
	@XmlTransferField(name = "DefaultAgreementId", sqlname="DEFAULT_AGREEMENT_ID", type="LONG"
				,derived=true, derivedfield="ContractorAgreement")
	private Long defaultAgreementId;
	
	@XmlTransferField(name = "RossResourceName", sqlname="ROSS_RESOURCE_NAME", type="STRING")
	private String rossResourceName;

	@XmlTransferField(name = "RossFirstName", sqlname="ROSS_FIRST_NAME", type="STRING")
	private String rossFirstName;

	@XmlTransferField(name = "RossLastName", sqlname="ROSS_LAST_NAME", type="STRING")
	private String rossLastName;
	
	@XmlTransferField(name = "LastRossUpdatedDate", sqlname="LAST_ROSS_UPDATED_DATE", type="DATE")
	private Date lastRossUpdatedDate;
	
	@XmlTransferField(name = "RossGroupAssignment", sqlname="ROSS_GROUP_ASSIGNMENT", type="STRING")
	private String rossGroupAssignment;
	
	@XmlTransferField(name = "ResourceKind", type="COMPLEX", target=IswResourceKind.class
			, lookupname="ResourceId", sourcename="Id"
			, cascade=true)
	private Collection<IswResourceKind> resourceKinds = new ArrayList<IswResourceKind>();
	
	@XmlTransferField(name = "ResourceMobilization", type="COMPLEX", target=IswResourceMobilization.class
			, lookupname="ResourceId", sourcename="Id"
			, cascade=true)
	private Collection<IswResourceMobilization> resourceMobilizations = new ArrayList<IswResourceMobilization>();
	
	public IswResource(){

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
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the parentResource
	 */
	public IswResource getParentResource() {
		return parentResource;
	}

	/**
	 * @param parentResource the parentResource to set
	 */
	public void setParentResource(IswResource parentResource) {
		this.parentResource = parentResource;
	}

	/**
	 * @return the parentResourceId
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}

	/**
	 * @param parentResourceId the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	/**
	 * @return the permanentResource
	 */
	public IswResource getPermanentResource() {
		return permanentResource;
	}

	/**
	 * @param permanentResource the permanentResource to set
	 */
	public void setPermanentResource(IswResource permanentResource) {
		this.permanentResource = permanentResource;
	}

	/**
	 * @return the permanentResourceId
	 */
	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	/**
	 * @param permanentResourceId the permanentResourceId to set
	 */
	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

	/**
	 * @return the orgTransferableIdentity
	 */
	public String getOrgTransferableIdentity() {
		return orgTransferableIdentity;
	}

	/**
	 * @param orgTransferableIdentity the orgTransferableIdentity to set
	 */
	public void setOrgTransferableIdentity(String orgTransferableIdentity) {
		this.orgTransferableIdentity = orgTransferableIdentity;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	 * @return the resourceClassification
	 */
	public String getResourceClassification() {
		return resourceClassification;
	}

	/**
	 * @param resourceClassification the resourceClassification to set
	 */
	public void setResourceClassification(String resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	/**
	 * @return the resourceStatus
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}

	/**
	 * @param resourceStatus the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
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
	 * @return the person
	 */
	public Boolean getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
	}

	/**
	 * @return the contracted
	 */
	public Boolean getContracted() {
		return contracted;
	}

	/**
	 * @param contracted the contracted to set
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	/**
	 * @return the leader
	 */
	public Boolean getLeader() {
		return leader;
	}

	/**
	 * @param leader the leader to set
	 */
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	/**
	 * @return the component
	 */
	public Boolean getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(Boolean component) {
		this.component = component;
	}

	/**
	 * @return the nameOnPictureId
	 */
	public String getNameOnPictureId() {
		return nameOnPictureId;
	}

	/**
	 * @param nameOnPictureId the nameOnPictureId to set
	 */
	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the other1
	 */
	public String getOther1() {
		return other1;
	}

	/**
	 * @param other1 the other1 to set
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}

	/**
	 * @return the other2
	 */
	public String getOther2() {
		return other2;
	}

	/**
	 * @param other2 the other2 to set
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}

	/**
	 * @return the other3
	 */
	public String getOther3() {
		return other3;
	}

	/**
	 * @param other3 the other3 to set
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the permanent
	 */
	public Boolean getPermanent() {
		return permanent;
	}

	/**
	 * @param permanent the permanent to set
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * @return the leaderType
	 */
	public Integer getLeaderType() {
		return leaderType;
	}

	/**
	 * @param leaderType the leaderType to set
	 */
	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}

	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the numberOfPersonnel
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @return the contractor
	 */
	public IswContractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(IswContractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * @return the defaultContractorId
	 */
	public Long getDefaultContractorId() {
		return defaultContractorId;
	}

	/**
	 * @param defaultContractorId the defaultContractorId to set
	 */
	public void setDefaultContractorId(Long defaultContractorId) {
		this.defaultContractorId = defaultContractorId;
	}

	/**
	 * @return the contractorAgreement
	 */
	public IswContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(IswContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * @return the defaultAgreementId
	 */
	public Long getDefaultAgreementId() {
		return defaultAgreementId;
	}

	/**
	 * @param defaultAgreementId the defaultAgreementId to set
	 */
	public void setDefaultAgreementId(Long defaultAgreementId) {
		this.defaultAgreementId = defaultAgreementId;
	}

	/**
	 * @return the rossResourceName
	 */
	public String getRossResourceName() {
		return rossResourceName;
	}

	/**
	 * @param rossResourceName the rossResourceName to set
	 */
	public void setRossResourceName(String rossResourceName) {
		this.rossResourceName = rossResourceName;
	}

	/**
	 * @return the rossFirstName
	 */
	public String getRossFirstName() {
		return rossFirstName;
	}

	/**
	 * @param rossFirstName the rossFirstName to set
	 */
	public void setRossFirstName(String rossFirstName) {
		this.rossFirstName = rossFirstName;
	}

	/**
	 * @return the rossLastName
	 */
	public String getRossLastName() {
		return rossLastName;
	}

	/**
	 * @param rossLastName the rossLastName to set
	 */
	public void setRossLastName(String rossLastName) {
		this.rossLastName = rossLastName;
	}

	/**
	 * @return the lastRossUpdatedDate
	 */
	public Date getLastRossUpdatedDate() {
		return lastRossUpdatedDate;
	}

	/**
	 * @param lastRossUpdatedDate the lastRossUpdatedDate to set
	 */
	public void setLastRossUpdatedDate(Date lastRossUpdatedDate) {
		this.lastRossUpdatedDate = lastRossUpdatedDate;
	}

	/**
	 * @return the rossGroupAssignment
	 */
	public String getRossGroupAssignment() {
		return rossGroupAssignment;
	}

	/**
	 * @param rossGroupAssignment the rossGroupAssignment to set
	 */
	public void setRossGroupAssignment(String rossGroupAssignment) {
		this.rossGroupAssignment = rossGroupAssignment;
	}

	/**
	 * @return the resourceKinds
	 */
	public Collection<IswResourceKind> getResourceKinds() {
		return resourceKinds;
	}

	/**
	 * @param resourceKinds the resourceKinds to set
	 */
	public void setResourceKinds(Collection<IswResourceKind> resourceKinds) {
		this.resourceKinds = resourceKinds;
	}

	/**
	 * @return the resourceMobilizations
	 */
	public Collection<IswResourceMobilization> getResourceMobilizations() {
		return resourceMobilizations;
	}

	/**
	 * @param resourceMobilizations the resourceMobilizations to set
	 */
	public void setResourceMobilizations(
			Collection<IswResourceMobilization> resourceMobilizations) {
		this.resourceMobilizations = resourceMobilizations;
	}

}
