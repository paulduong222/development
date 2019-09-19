package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;


public class IncidentResourceCommonVo extends AbstractVo {
	private Boolean dirtyFlag = false;

	private Long resourceId;
	private Boolean permanent;
	private Boolean contracted;
	private Boolean person;
	private String resourceName;
	private String lastName;
	private String firstName;
	private String nameOnPictureId;
	private String requestCategory;
	private String phone;
	private Long incidentId;
	private Long incidentResourceId;
	private String nameAtIncident;
	private Long workPeriodId;
	private Date ciCheckInDate;
	private String ciCheckInTime;
	private Date dmReleaseDate;
	private String dmReleaseTime;
	private Long resourceAgencyId;
	private Long resourceOrganizationId;
	private String resourceAgencyCode;
	private String resourceUnitCode;
	private Long assignmentId;
	private String requestNumber;
	private Date assignmentEndDate;
	private AssignmentStatusTypeEnum assignmentStatus;
	private Boolean training;
	private Long kindId;
	private String kindDescription;
	private ResourceClassificationEnum resourceClassification;

	public IncidentResourceCommonVo(){
		
	}

	/**
	 * Returns the dirtyFlag.
	 *
	 * @return 
	 *		the dirtyFlag to return
	 */
	public Boolean getDirtyFlag() {
		return dirtyFlag;
	}

	/**
	 * Sets the dirtyFlag.
	 *
	 * @param dirtyFlag 
	 *			the dirtyFlag to set
	 */
	public void setDirtyFlag(Boolean dirtyFlag) {
		this.dirtyFlag = dirtyFlag;
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
		if(null!=this.ciCheckInDate){
			this.ciCheckInTime=DateUtil.toTime(this.ciCheckInDate);
		}
	}

	/**
	 * Returns the ciCheckInString.
	 *
	 * @return 
	 *		the ciCheckInString to return
	 */
	public String getCiCheckInTime() {
		return ciCheckInTime;
	}

	/**
	 * Sets the ciCheckInTime.
	 *
	 * @param ciCheckInTime 
	 *			the ciCheckInTime to set
	 */
	public void setCiCheckInTime(String ciCheckInTime) {
		this.ciCheckInTime = ciCheckInTime;
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
		if(null!=this.dmReleaseDate){
			this.dmReleaseTime=DateUtil.toTime(this.dmReleaseDate);
		}
	}
	
	/**
	 * Returns the dmReleaseTime.
	 *
	 * @return 
	 *		the dmReleaseTime to return
	 */
	public String getDmReleaseTime() {
		return dmReleaseTime;
	}

	/**
	 * Sets the dmReleaseTime.
	 *
	 * @param dmReleaseTime 
	 *			the dmReleaseTime to set
	 */
	public void setDmReleaseTime(String dmReleaseTime) {
		this.dmReleaseTime = dmReleaseTime;
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
