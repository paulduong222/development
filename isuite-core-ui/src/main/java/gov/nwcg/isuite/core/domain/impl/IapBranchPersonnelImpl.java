package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchPersonnelRes;
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
 * IapBranchPersonnel entity.
 */
@Entity
@Table(name = "isw_iap_branch_personnel")
@SequenceGenerator(name="SEQ_IAP_BRANCH_PERSONNEL", sequenceName="SEQ_IAP_BRANCH_PERSONNEL")
public class IapBranchPersonnelImpl extends PersistableImpl implements IapBranchPersonnel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH_PERSONNEL")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapBranchImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_BRANCH_ID", nullable = false)
	private IapBranch iapBranch;
	
	@Column(name = "IAP_BRANCH_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapBranchId;
	
	@ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentResourceId;
	
	@Column(name = "ROLE", length = 50)
	private String role;

	@Column(name = "ROLE_TYPE", length = 30)
	private String roleType;

	@Column(name = "RESOURCE_NAME", length = 100)
	private String resourceName;
	
	@Column(name = "PHONE1", length = 20)
	private String phone1;
	
	@Column(name = "PHONE2", length = 20)
	private String phone2;

	@Column(name = "IS_TRAINEE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isTrainee;
	
	@OneToMany(cascade=CascadeType.ALL, targetEntity=IapBranchPersonnelResImpl.class,fetch = FetchType.LAZY, mappedBy = "iapBranchPersonnel")
	@JoinColumn(name="IAP_BRANCH_PERSONNEL_ID")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("positionNum")
	private Collection<IapBranchPersonnelRes> iapBranchPersonnelResources;

    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable = false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapBranchPersonnelImpl() {
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
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch) {
		this.iapBranch = iapBranch;
	}

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch() {
		return iapBranch;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
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
	 * @return the iapBranchPersonnelResources
	 */
	public Collection<IapBranchPersonnelRes> getIapBranchPersonnelResources() {
		if(null==iapBranchPersonnelResources)
			iapBranchPersonnelResources = new ArrayList<IapBranchPersonnelRes>();
		return iapBranchPersonnelResources;
	}

	/**
	 * @param iapBranchPersonnelResources the iapBranchPersonnelResources to set
	 */
	public void setIapBranchPersonnelResources(
			Collection<IapBranchPersonnelRes> iapBranchPersonnelResources) {
		this.iapBranchPersonnelResources = iapBranchPersonnelResources;
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
}
