package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswIapPersonnel", table="isw_iap_personnel")
public class IswIapPersonnel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PERSONNEL", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapForm203TransferableIdentity", alias="fti1", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm203Id"
		, disjoined=true, disjoinedtable="isw_iap_form_203", disjoinedfield="transferable_identity",disjoinedsource="iap_form_203_id")
	private String iapForm203TransferableIdentity;

	@XmlTransferField(name = "IapForm203Id", sqlname="IAP_FORM_203_ID", type="LONG"
		,derived=true,derivedfield="IapForm203TransferableIdentity")
	private Long iapForm203Id;

	@XmlTransferField(name = "IapForm220TransferableIdentity", alias="fti1", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm220Id"
		, disjoined=true, disjoinedtable="isw_iap_form_220", disjoinedfield="transferable_identity",disjoinedsource="iap_form_220_id")
	private String iapForm220TransferableIdentity;

	@XmlTransferField(name = "IapForm220Id", sqlname="IAP_FORM_220_ID", type="LONG"
		,derived=true,derivedfield="IapForm220TransferableIdentity")
	private Long iapForm220Id;

	@XmlTransferField(name = "AgencyName", sqlname="AGENCY_NAME", type="STRING")
	private String agencyName;
	
	@XmlTransferField(name = "Role", sqlname="ROLE", type="STRING")
	private String role;
	
	@XmlTransferField(name = "RoleType", sqlname="ROLE_TYPE", type="STRING")
	private String roleType;

	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;
	
	@XmlTransferField(name = "Phone", sqlname="PHONE", type="STRING")
	private String phone;
	
	@XmlTransferField(name = "Form", sqlname="FORM", type="STRING")
	private String form;
	
	@XmlTransferField(name = "Section", sqlname="SECTION", type="STRING")
	private String section;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	@XmlTransferField(name = "IsBlankBranch", sqlname="IS_BLANK_BRANCH", type="STRING")
	private String isBlankBranch;

	@XmlTransferField(name = "DivisionGroupName", sqlname="DIVISION_GROUP_NAME", type="STRING")
	private String divisionGroupName;
	
	@XmlTransferField(name = "IsTrainee", sqlname="IS_TRAINEE", type="STRING")
	private String isTrainee;
	
	//@ManyToOne(targetEntity=BranchSettingImpl.class, fetch = FetchType.LAZY)
	//@JoinColumn(name = "BRANCH_SETTING_ID", nullable = true)
	//private BranchSetting branchSetting;
	
	//@Column(name = "BRANCH_SETTING_ID", insertable = false, updatable = false, unique=false, nullable = true)
	//private Long branchSettingId;

	@XmlTransferField(name = "IsBranchName", sqlname="IS_BRANCH_NAME", type="STRING")
	private String isBranchName;

	@XmlTransferField(name = "IapBranchPersonnelParent", type="COMPLEX", target=IswIapPersonnel.class
			, lookupname="Id", sourcename="IapBranchPersonnelParentId")
	private IswIapPersonnel iapBranchPersonnelParent;

	@XmlTransferField(name = "IapBranchPersonnelParentId", sqlname="IAP_PERSONNEL_BRANCH_ID", type="LONG"
			,derived=true, derivedfield="IapBranchPersonnelParent")
	private Long iapBranchPersonnelParentId;
	
	
	//@OneToMany(cascade=CascadeType.ALL, targetEntity=IapPersonnelImpl.class)
	//@JoinColumn(name="IAP_PERSONNEL_BRANCH_ID")
    //@OrderBy("positionNum")
	//private Collection<IapPersonnel> branchPersonnel;
	
	@XmlTransferField(name = "IapPersonnelResource", type="COMPLEX", target=IswIapPersonnelRes.class
			, lookupname="IapPersonnelId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPersonnelRes> iapPersonnelResources = new ArrayList<IswIapPersonnelRes>();


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
	 * @return the iapForm203TransferableIdentity
	 */
	public String getIapForm203TransferableIdentity() {
		return iapForm203TransferableIdentity;
	}


	/**
	 * @param iapForm203TransferableIdentity the iapForm203TransferableIdentity to set
	 */
	public void setIapForm203TransferableIdentity(
			String iapForm203TransferableIdentity) {
		this.iapForm203TransferableIdentity = iapForm203TransferableIdentity;
	}


	/**
	 * @return the iapForm203Id
	 */
	public Long getIapForm203Id() {
		return iapForm203Id;
	}


	/**
	 * @param iapForm203Id the iapForm203Id to set
	 */
	public void setIapForm203Id(Long iapForm203Id) {
		this.iapForm203Id = iapForm203Id;
	}


	/**
	 * @return the iapForm220TransferableIdentity
	 */
	public String getIapForm220TransferableIdentity() {
		return iapForm220TransferableIdentity;
	}


	/**
	 * @param iapForm220TransferableIdentity the iapForm220TransferableIdentity to set
	 */
	public void setIapForm220TransferableIdentity(
			String iapForm220TransferableIdentity) {
		this.iapForm220TransferableIdentity = iapForm220TransferableIdentity;
	}


	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}


	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
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
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}


	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
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
	 * @return the form
	 */
	public String getForm() {
		return form;
	}


	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}


	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}


	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}


	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}


	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}


	/**
	 * @return the isBlankLine
	 */
	public String getIsBlankLine() {
		return isBlankLine;
	}


	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(String isBlankLine) {
		this.isBlankLine = isBlankLine;
	}


	/**
	 * @return the isBlankBranch
	 */
	public String getIsBlankBranch() {
		return isBlankBranch;
	}


	/**
	 * @param isBlankBranch the isBlankBranch to set
	 */
	public void setIsBlankBranch(String isBlankBranch) {
		this.isBlankBranch = isBlankBranch;
	}


	/**
	 * @return the divisionGroupName
	 */
	public String getDivisionGroupName() {
		return divisionGroupName;
	}


	/**
	 * @param divisionGroupName the divisionGroupName to set
	 */
	public void setDivisionGroupName(String divisionGroupName) {
		this.divisionGroupName = divisionGroupName;
	}


	/**
	 * @return the isTrainee
	 */
	public String getIsTrainee() {
		return isTrainee;
	}


	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(String isTrainee) {
		this.isTrainee = isTrainee;
	}


	/**
	 * @return the isBranchName
	 */
	public String getIsBranchName() {
		return isBranchName;
	}


	/**
	 * @param isBranchName the isBranchName to set
	 */
	public void setIsBranchName(String isBranchName) {
		this.isBranchName = isBranchName;
	}


	/**
	 * @return the iapBranchPersonnelParent
	 */
	public IswIapPersonnel getIapBranchPersonnelParent() {
		return iapBranchPersonnelParent;
	}


	/**
	 * @param iapBranchPersonnelParent the iapBranchPersonnelParent to set
	 */
	public void setIapBranchPersonnelParent(IswIapPersonnel iapBranchPersonnelParent) {
		this.iapBranchPersonnelParent = iapBranchPersonnelParent;
	}


	/**
	 * @return the iapBranchPersonnelParentId
	 */
	public Long getIapBranchPersonnelParentId() {
		return iapBranchPersonnelParentId;
	}


	/**
	 * @param iapBranchPersonnelParentId the iapBranchPersonnelParentId to set
	 */
	public void setIapBranchPersonnelParentId(Long iapBranchPersonnelParentId) {
		this.iapBranchPersonnelParentId = iapBranchPersonnelParentId;
	}


	/**
	 * @return the iapPersonnelResources
	 */
	public Collection<IswIapPersonnelRes> getIapPersonnelResources() {
		return iapPersonnelResources;
	}


	/**
	 * @param iapPersonnelResources the iapPersonnelResources to set
	 */
	public void setIapPersonnelResources(
			Collection<IswIapPersonnelRes> iapPersonnelResources) {
		this.iapPersonnelResources = iapPersonnelResources;
	}
	
	
}
