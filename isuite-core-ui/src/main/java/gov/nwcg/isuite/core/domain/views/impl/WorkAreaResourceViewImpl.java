package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.WorkAreaResourceView;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="iswv_resource_work_area")
public class WorkAreaResourceViewImpl implements WorkAreaResourceView {

	@Id
	@Column(name = "RESOURCE_ID", insertable = false, updatable = false)
	private Long resourceId;

	@Column(name="PARENT_RESOURCE_ID")
	private Long parentResourceId;

	@Column(name = "PERSON", insertable = false, updatable = false)
	private Boolean person;
	
	@Column(name = "PERMANENT", insertable = false, updatable = false)
	private Boolean permanent;

	@Column(name = "ENABLED", insertable = false, updatable = false)
	private Boolean enabled;

	@Column(name = "CONTRACTED", insertable = false, updatable = false)
	private Boolean contracted;
	
	@Column(name = "LEADER", insertable = false, updatable = false)
	private Boolean leader;

	@Column(name = "OTHER_1", insertable=false, updatable=false)
	private String other1;
	
	@Column(name = "OTHER_2", insertable=false, updatable=false)
	private String other2;

	@Column(name = "OTHER_3", insertable=false, updatable=false)
	private String other3;
	
	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;

	@Column(name = "LAST_NAME", insertable = false, updatable = false)
	private String lastName;

	@Column(name = "FIRST_NAME", insertable = false, updatable = false)
	private String firstName;

	@Column(name = "RESOURCE_STATUS", insertable = false, updatable = false)
	private String resourceStatus;

	@Column(name = "NUMBER_OF_PERSONNEL", insertable = false, updatable = false)
	private Long numberOfPersonnel;

    @Column(name="LEADER_NAME",insertable=false,updatable=false)
    private String leaderName;
    
	@Column(name = "RESOURCE_AGENCY_NAME", insertable = false, updatable = false)
	private String resourceAgencyName;
	
	@Column(name = "RESOURCE_ORGANIZATION_NAME", insertable = false, updatable = false)
	private String resourceOrganizationName;

    @Column(name="WORK_AREA_ID", insertable = false, updatable = false)
    private Long workAreaId;
	
    @Column(name="WORK_AREA_RESOURCE_ID", insertable = false, updatable = false)
    private Long workAreaResourceId;

	@Column(name = "INCIDENT_NAME", insertable = false, updatable = false)
	private String incidentName;
 
	@Column(name = "RESOURCE_AGENCY_CODE", insertable = false, updatable = false)
	private String resourceAgencyCode;
	
	@Column(name = "RESOURCE_UNIT_CODE", insertable = false, updatable = false)
	private String resourceUnitCode;

	@Column(name="REQUEST_NUMBER", insertable = false, updatable = false)
	private String requestNumber;

	//@Column(name="KIND_DESCRIPTION", insertable = false, updatable = false)
	@Column(name="RESOURCE_KINDS_DESCRIPTIONS", insertable = false, updatable = false)
	private String kindDescription;

	@Column(name="ASSIGNMENT_STATUS", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;

	@Column(name="ASSIGN_KIND_REQUEST_CATEGORY", insertable = false, updatable = false)
	private String assignmentKindRequestCategory;
	
	@Column(name="RESOURCE_KINDS_CATEGORIES", insertable = false, updatable = false)
	private String resourceKindsCategories;
	
	@Column(name="PRIMARY_DISP_CTR_ID", insertable = false, updatable = false)
	private Long primaryDispatchCenterId;

	@Column(name="LEADER_TYPE", insertable = false, updatable = false)
	private Integer leaderType;
	
	@Column(name="PERMANENT_RESOURCE_ID")
	private Long permanentResourceId;
	
	public WorkAreaResourceViewImpl(){
    	
    }

	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Returns the parentResourceId.
	 *
	 * @return 
	 *		the parentResourceId to return
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}

	/**
	 * Sets the parentResourceId.
	 *
	 * @param parentResourceId 
	 *			the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	/**
	 * Returns the person.
	 *
	 * @return 
	 *		the person to return
	 */
	public Boolean getPerson() {
		return person;
	}

	/**
	 * Sets the person.
	 *
	 * @param person 
	 *			the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
	}

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public Boolean getPermanent() {
		return permanent;
	}

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted() {
		return contracted;
	}

	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	/**
	 * Returns the leader.
	 *
	 * @return 
	 *		the leader to return
	 */
	public Boolean getLeader() {
		return leader;
	}

	/**
	 * Sets the leader.
	 *
	 * @param leader 
	 *			the leader to set
	 */
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	/**
	 * Returns the other1.
	 *
	 * @return 
	 *		the other1 to return
	 */
	public String getOther1() {
		return other1;
	}

	/**
	 * Sets the other1.
	 *
	 * @param other1 
	 *			the other1 to set
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}

	/**
	 * Returns the other2.
	 *
	 * @return 
	 *		the other2 to return
	 */
	public String getOther2() {
		return other2;
	}

	/**
	 * Sets the other2.
	 *
	 * @param other2 
	 *			the other2 to set
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}

	/**
	 * Returns the other3.
	 *
	 * @return 
	 *		the other3 to return
	 */
	public String getOther3() {
		return other3;
	}

	/**
	 * Sets the other3.
	 *
	 * @param other3 
	 *			the other3 to set
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
	}

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the resourceStatus.
	 *
	 * @return 
	 *		the resourceStatus to return
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}

	/**
	 * Sets the resourceStatus.
	 *
	 * @param resourceStatus 
	 *			the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	/**
	 * Returns the numberOfPersonnel.
	 *
	 * @return 
	 *		the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * Sets the numberOfPersonnel.
	 *
	 * @param numberOfPersonnel 
	 *			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * Returns the resourceAgencyName.
	 *
	 * @return 
	 *		the resourceAgencyName to return
	 */
	public String getResourceAgencyName() {
		return resourceAgencyName;
	}

	/**
	 * Sets the resourceAgencyName.
	 *
	 * @param resourceAgencyName 
	 *			the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName) {
		this.resourceAgencyName = resourceAgencyName;
	}

	/**
	 * Returns the resourceOrganizationName.
	 *
	 * @return 
	 *		the resourceOrganizationName to return
	 */
	public String getResourceOrganizationName() {
		return resourceOrganizationName;
	}

	/**
	 * Sets the resourceOrganizationName.
	 *
	 * @param resourceOrganizationName 
	 *			the resourceOrganizationName to set
	 */
	public void setResourceOrganizationName(String resourceOrganizationName) {
		this.resourceOrganizationName = resourceOrganizationName;
	}

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * Returns the workAreaResourceId.
	 *
	 * @return 
	 *		the workAreaResourceId to return
	 */
	public Long getWorkAreaResourceId() {
		return workAreaResourceId;
	}

	/**
	 * Sets the workAreaResourceId.
	 *
	 * @param workAreaResourceId 
	 *			the workAreaResourceId to set
	 */
	public void setWorkAreaResourceId(Long workAreaResourceId) {
		this.workAreaResourceId = workAreaResourceId;
	}

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the resourceAgencyCode.
	 *
	 * @return 
	 *		the resourceAgencyCode to return
	 */
	public String getResourceAgencyCode() {
		return resourceAgencyCode;
	}

	/**
	 * Sets the resourceAgencyCode.
	 *
	 * @param resourceAgencyCode 
	 *			the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode) {
		this.resourceAgencyCode = resourceAgencyCode;
	}

	/**
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode() {
		return resourceUnitCode;
	}

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode) {
		this.resourceUnitCode = resourceUnitCode;
	}

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	
	/**
	 * Returns the kindDescription.
	 *
	 * @return 
	 *		the kindDescription to return
	 */
	public String getKindDescription() {
		return kindDescription;
	}

	/**
	 * Sets the kindDescription.
	 *
	 * @param kindDescription 
	 *			the kindDescription to set
	 */
	public void setKindDescription(String kindDescription) {
		this.kindDescription = kindDescription;
	}

	/**
	 * Returns the assignmentKindRequestCategory.
	 *
	 * @return 
	 *		the assignmentKindRequestCategory to return
	 */
	public String getAssignmentKindRequestCategory() {
		return assignmentKindRequestCategory;
	}

	/**
	 * Sets the assignmentKindRequestCategory.
	 *
	 * @param assignmentKindRequestCategory 
	 *			the assignmentKindRequestCategory to set
	 */
	public void setAssignmentKindRequestCategory(
			String assignmentKindRequestCategory) {
		this.assignmentKindRequestCategory = assignmentKindRequestCategory;
	}

	/**
	 * @param resourceKindsCategories the resourceKindsCategories to set
	 */
	public void setResourceKindsCategories(String resourceKindsCategories) {
		this.resourceKindsCategories = resourceKindsCategories;
	}

	/**
	 * @return the resourceKindsCategories
	 */
	public String getResourceKindsCategories() {
		return resourceKindsCategories;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourceView#getPrimaryDispatchCenterId()
	 */
	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourceView#setPrimaryDispatchCenterId(java.lang.Long)
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId) {
		 this.primaryDispatchCenterId = primaryDispatchCenterId;
	}

	public Integer getLeaderType() {
		return leaderType;
	}

	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourceView#getPermanentResourceId()
	 */
	public Long getPermanentResourceId() {
		return permanentResourceId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourceView#setPermanentResourceId(java.lang.Long)
	 */
	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

}
