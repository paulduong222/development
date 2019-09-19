package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnelRes;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * IapPersonnel entity.
 */
@Entity
@Table(name = "isw_iap_personnel")
@SequenceGenerator(name="SEQ_IAP_PERSONNEL", sequenceName="SEQ_IAP_PERSONNEL")
public class IapPersonnelImpl extends PersistableImpl implements IapPersonnel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_PERSONNEL")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm203Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_203_ID", nullable = true)
	private IapForm203 iapForm203;
	
	@Column(name = "IAP_FORM_203_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long iapForm203Id;
	
	@ManyToOne(targetEntity=IapForm220Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_220_ID", nullable = true)
	private IapForm220 iapForm220;
	
	@Column(name = "IAP_FORM_220_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long iapForm220Id;

	@ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long incidentResourceId;

	@Column(name = "AGENCY_NAME", length = 50)
	private String agencyName;
	
	@Column(name = "ROLE", length = 50)
	private String role;
	
	@Column(name = "ROLE_TYPE", length = 30)
	private String roleType;

	@Column(name = "NAME", length = 200)
	private String name;
	
	@Column(name = "PHONE", length = 50)
	private String phone;
	
	@Column(name = "FORM", length = 50)
	private String form;
	
	@Column(name = "SECTION", length = 50)
	private String section;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;

	@Column(name = "IS_BLANK_BRANCH", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankBranch;

	@Column(name = "DIVISION_GROUP_NAME", length = 50)
	private String divisionGroupName;
	
	@Column(name = "IS_TRAINEE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isTrainee;
	
	@ManyToOne(targetEntity=BranchSettingImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_SETTING_ID", nullable = true)
	private BranchSetting branchSetting;
	
	@Column(name = "BRANCH_SETTING_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long branchSettingId;

	@Column(name = "IS_BRANCH_NAME", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBranchName;

	@ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.IapPersonnelImpl.class,cascade=CascadeType.PERSIST)
	@JoinColumn(name = "IAP_PERSONNEL_BRANCH_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private IapPersonnel iapBranchPersonnelParent;
	
	@Column(name="IAP_PERSONNEL_BRANCH_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long iapBranchPersonnelParentId;

	@OneToMany(cascade=CascadeType.ALL, targetEntity=IapPersonnelImpl.class)
	@JoinColumn(name="IAP_PERSONNEL_BRANCH_ID")
    @OrderBy("positionNum")
	private Collection<IapPersonnel> branchPersonnel;
	
	@OneToMany(cascade=CascadeType.ALL, targetEntity=IapPersonnelResImpl.class,fetch = FetchType.LAZY, mappedBy = "iapPersonnel")
	@JoinColumn(name="IAP_PERSONNEL_ID")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("positionNum")
	private Collection<IapPersonnelRes> iapPersonnelResources;

	/** 
	 * Default constructor 
	 */
	public IapPersonnelImpl() {
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
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @return the iapForm203
	 */
	public IapForm203 getIapForm203() {
		return iapForm203;
	}

	/**
	 * @param iapForm203 the iapForm203 to set
	 */
	public void setIapForm203(IapForm203 iapForm203) {
		this.iapForm203 = iapForm203;
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
	 * @return the iapForm220
	 */
	public IapForm220 getIapForm220() {
		return iapForm220;
	}

	/**
	 * @param iapForm220 the iapForm220 to set
	 */
	public void setIapForm220(IapForm220 iapForm220) {
		this.iapForm220 = iapForm220;
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
	public StringBooleanEnum getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

	/**
	 * @return the branchSetting
	 */
	public BranchSetting getBranchSetting() {
		return branchSetting;
	}

	/**
	 * @param branchSetting the branchSetting to set
	 */
	public void setBranchSetting(BranchSetting branchSetting) {
		this.branchSetting = branchSetting;
	}

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId() {
		return branchSettingId;
	}

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId) {
		this.branchSettingId = branchSettingId;
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
	 * @return the isTrainee
	 */
	public StringBooleanEnum getIsTrainee() {
		return isTrainee;
	}

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(StringBooleanEnum isTrainee) {
		this.isTrainee = isTrainee;
	}

	/**
	 * @return the isBranchName
	 */
	public StringBooleanEnum getIsBranchName() {
		return isBranchName;
	}

	/**
	 * @param isBranchName the isBranchName to set
	 */
	public void setIsBranchName(StringBooleanEnum isBranchName) {
		this.isBranchName = isBranchName;
	}

	/**
	 * @return the iapBranchPersonnelParent
	 */
	public IapPersonnel getIapBranchPersonnelParent() {
		return iapBranchPersonnelParent;
	}

	/**
	 * @param iapBranchPersonnelParent the iapBranchPersonnelParent to set
	 */
	public void setIapBranchPersonnelParent(IapPersonnel iapBranchPersonnelParent) {
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
	 * @return the branchPersonnel
	 */
	public Collection<IapPersonnel> getBranchPersonnel() {
		return branchPersonnel;
	}

	/**
	 * @param branchPersonnel the branchPersonnel to set
	 */
	public void setBranchPersonnel(Collection<IapPersonnel> branchPersonnel) {
		this.branchPersonnel = branchPersonnel;
	}

	/**
	 * @return the isBlankBranch
	 */
	public StringBooleanEnum getIsBlankBranch() {
		return isBlankBranch;
	}

	/**
	 * @param isBlankBranch the isBlankBranch to set
	 */
	public void setIsBlankBranch(StringBooleanEnum isBlankBranch) {
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
	 * @return the iapPersonnelResources
	 */
	public Collection<IapPersonnelRes> getIapPersonnelResources() {
		if(null==iapPersonnelResources)
			iapPersonnelResources = new ArrayList<IapPersonnelRes>();
		
		return iapPersonnelResources;
	}

	/**
	 * @param iapPersonnelResources the iapPersonnelResources to set
	 */
	public void setIapPersonnelResources(
			Collection<IapPersonnelRes> iapPersonnelResources) {
		this.iapPersonnelResources = iapPersonnelResources;
	}

}
