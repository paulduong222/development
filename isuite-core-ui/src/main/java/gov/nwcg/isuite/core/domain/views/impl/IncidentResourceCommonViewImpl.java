package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.views.IncidentResourceCommonView;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author dprice
 */
@Entity
@Table(name = "iswv_incident_res_common_view")
public class IncidentResourceCommonViewImpl implements IncidentResourceCommonView {

	@Id
	@Column(name = "RESOURCE_ID", insertable = false, updatable = false)
	private Long resourceId;

	@Column(name = "PERMANENT", insertable = false, updatable = false)
	private Boolean permanent;

	@Column(name = "CONTRACTED", insertable = false, updatable = false)
	private Boolean contracted;

	@Column(name = "PERSON", insertable = false, updatable = false)
	private Boolean person;

	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;
	
	@Column(name = "LAST_NAME", insertable = false, updatable = false)
	private String lastName;

	@Column(name = "FIRST_NAME", insertable = false, updatable = false)
	private String firstName;
	
	@Column(name = "NAME_ON_PICTURE_ID", insertable = false, updatable = false)
	private String nameOnPictureId;

	
	@Column(name = "REQUEST_CATEGORY", insertable=false, updatable=false)
	private String requestCategory;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name="INCIDENT_RESOURCE_ID")
	private Long incidentResourceId;
	
	@Column(name = "NAME_AT_INCIDENT", insertable = false, updatable = false)
	private String nameAtIncident;

	@Column(name="WORK_PERIOD_ID")
	private Long workPeriodId;

	@Column(name="CI_CHECK_IN_DATE")
	private Date ciCheckInDate;
	
	@Column(name="DM_RELEASE_DATE")
	private Date dmReleaseDate;

	@Column(name = "RESOURCE_AGENCY_ID", insertable = false, updatable = false)
	private Long resourceAgencyId;

	@Column(name = "RESOURCE_ORGANIZATION_ID", insertable = false, updatable = false)
	private Long resourceOrganizationId;
	
	@Column(name = "RESOURCE_AGENCY_CODE", insertable = false, updatable = false)
	private String resourceAgencyCode;
	
	@Column(name = "RESOURCE_UNIT_CODE", insertable = false, updatable = false)
	private String resourceUnitCode;

	@Column(name="ASSIGNMENT_ID")
	private Long assignmentId;

	@Column(name="REQUEST_NUMBER")
	private String requestNumber;
	
	@Column(name="ASSIGNMENT_END_DATE")
	private Date assignmentEndDate;

	@Column(name="ASSIGNMENT_STATUS")
    @Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;
	
	@Column(name="IS_TRAINING")
	private Boolean training;

	@OneToOne(targetEntity=KindImpl.class,fetch=FetchType.LAZY)
    @JoinColumn(name="KIND_ID",insertable=true,updatable=true,unique=false,nullable=true)
	private Kind kind;

	@Column(name = "KIND_ID", insertable = false, updatable = false)
	private Long kindId;
	
	@Column(name = "RESOURCE_CLASSIFICATION")
    @Enumerated(EnumType.STRING)
	private ResourceClassificationEnum resourceClassification;

	public IncidentResourceCommonViewImpl() {

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
	 * Returns the requestCategory.
	 *
	 * @return 
	 *		the requestCategory to return
	 */
	public String getRequestCategory() {
		return requestCategory;
	}

	/**
	 * Sets the requestCategory.
	 *
	 * @param requestCategory 
	 *			the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * Returns the incidentResourceId.
	 *
	 * @return 
	 *		the incidentResourceId to return
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * Sets the incidentResourceId.
	 *
	 * @param incidentResourceId 
	 *			the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * Returns the nameAtIncident.
	 *
	 * @return 
	 *		the nameAtIncident to return
	 */
	public String getNameAtIncident() {
		return nameAtIncident;
	}

	/**
	 * Sets the nameAtIncident.
	 *
	 * @param nameAtIncident 
	 *			the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;
	}

	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * Returns the ciCheckInDate.
	 *
	 * @return 
	 *		the ciCheckInDate to return
	 */
	public Date getCiCheckInDate() {
		return ciCheckInDate;
	}

	/**
	 * Sets the ciCheckInDate.
	 *
	 * @param ciCheckInDate 
	 *			the ciCheckInDate to set
	 */
	public void setCiCheckInDate(Date ciCheckInDate) {
		this.ciCheckInDate = ciCheckInDate;
	}
	
	/**
	 * Returns the dmReleaseDate.
	 *
	 * @return 
	 *		the dmReleaseDate to return
	 */
	public Date getDmReleaseDate() {
		return dmReleaseDate;
	}

	/**
	 * Sets the dmReleaseDate.
	 *
	 * @param dmReleaseDate 
	 *			the dmReleaseDate to set
	 */
	public void setDmReleaseDate(Date dmReleaseDate) {
		this.dmReleaseDate = dmReleaseDate;
	}

	/**
	 * Returns the resourceAgencyId.
	 *
	 * @return 
	 *		the resourceAgencyId to return
	 */
	public Long getResourceAgencyId() {
		return resourceAgencyId;
	}

	/**
	 * Sets the resourceAgencyId.
	 *
	 * @param resourceAgencyId 
	 *			the resourceAgencyId to set
	 */
	public void setResourceAgencyId(Long resourceAgencyId) {
		this.resourceAgencyId = resourceAgencyId;
	}

	/**
	 * Returns the resourceOrganizationId.
	 *
	 * @return 
	 *		the resourceOrganizationId to return
	 */
	public Long getResourceOrganizationId() {
		return resourceOrganizationId;
	}

	/**
	 * Sets the resourceOrganizationId.
	 *
	 * @param resourceOrganizationId 
	 *			the resourceOrganizationId to set
	 */
	public void setResourceOrganizationId(Long resourceOrganizationId) {
		this.resourceOrganizationId = resourceOrganizationId;
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
	 * Returns the assignmentId.
	 *
	 * @return 
	 *		the assignmentId to return
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * Sets the assignmentId.
	 *
	 * @param assignmentId 
	 *			the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
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
	 * Returns the assignmentEndDate.
	 *
	 * @return 
	 *		the assignmentEndDate to return
	 */
	public Date getAssignmentEndDate() {
		return assignmentEndDate;
	}

	/**
	 * Sets the assignmentEndDate.
	 *
	 * @param assignmentEndDate 
	 *			the assignmentEndDate to set
	 */
	public void setAssignmentEndDate(Date assignmentEndDate) {
		this.assignmentEndDate = assignmentEndDate;
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
	 * Returns the kind.
	 *
	 * @return 
	 *		the kind to return
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind 
	 *			the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * Returns the kindId.
	 *
	 * @return 
	 *		the kindId to return
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * Sets the kindId.
	 *
	 * @param kindId 
	 *			the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * Returns the resourceClassification.
	 *
	 * @return 
	 *		the resourceClassification to return
	 */
	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}

	/**
	 * Sets the resourceClassification.
	 *
	 * @param resourceClassification 
	 *			the resourceClassification to set
	 */
	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	/**
	 * Returns the training.
	 *
	 * @return 
	 *		the training to return
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * Sets the training.
	 *
	 * @param training 
	 *			the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * Returns the nameOnPictureId.
	 *
	 * @return 
	 *		the nameOnPictureId to return
	 */
	public String getNameOnPictureId() {
		return nameOnPictureId;
	}

	/**
	 * Sets the nameOnPictureId.
	 *
	 * @param nameOnPictureId 
	 *			the nameOnPictureId to set
	 */
	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}


}
