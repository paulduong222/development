package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswIapBranchPersonnel", table = "isw_iap_branch_personnel")
public class IswIapBranchPersonnel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_PERSONNEL", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapBranchTransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapBranchId"
		, disjoined=true, disjoinedtable="isw_iap_branch", disjoinedfield="transferable_identity",disjoinedsource="iap_branch_id")
	private String iapBranchTransferableIdentity;

	@XmlTransferField(name = "IapBranchId", sqlname="IAP_BRANCH_ID", type="LONG"
		,derived=true,derivedfield="IapBranchTransferableIdentity")
	private Long iapBranchId;
	
	@XmlTransferField(name = "Role", sqlname="ROLE", type="STRING")
	private String role;

	@XmlTransferField(name = "RoleType", sqlname="ROLE_TYPE", type="STRING")
	private String roleType;

	@XmlTransferField(name = "ResourceName", sqlname="RESOURCE_NAME", type="STRING")
	private String resourceName;
	
	@XmlTransferField(name = "Phone1", sqlname="PHONE1", type="STRING")
	private String phone1;
	
	@XmlTransferField(name = "Phone2", sqlname="PHONE2", type="STRING")
	private String phone2;

	@XmlTransferField(name = "IsTrainee", sqlname="IS_TRAINEE", type="STRING")
	private String isTrainee;
	
	@XmlTransferField(name = "IapBranchPersonnelRes", type="COMPLEX", target=IswIapBranchPersonnelRes.class
			, lookupname="IapBranchPersonnelId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapBranchPersonnelRes> iapBranchPersonnelRess = new ArrayList<IswIapBranchPersonnelRes>();

	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchPersonnel() {
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
	 * @return the iapBranchTransferableIdentity
	 */
	public String getIapBranchTransferableIdentity() {
		return iapBranchTransferableIdentity;
	}

	/**
	 * @param iapBranchTransferableIdentity the iapBranchTransferableIdentity to set
	 */
	public void setIapBranchTransferableIdentity(
			String iapBranchTransferableIdentity) {
		this.iapBranchTransferableIdentity = iapBranchTransferableIdentity;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
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
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
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
	 * @return the iapBranchPersonnelRess
	 */
	public Collection<IswIapBranchPersonnelRes> getIapBranchPersonnelRess() {
		return iapBranchPersonnelRess;
	}

	/**
	 * @param iapBranchPersonnelRess the iapBranchPersonnelRess to set
	 */
	public void setIapBranchPersonnelRess(
			Collection<IswIapBranchPersonnelRes> iapBranchPersonnelRess) {
		this.iapBranchPersonnelRess = iapBranchPersonnelRess;
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



}
