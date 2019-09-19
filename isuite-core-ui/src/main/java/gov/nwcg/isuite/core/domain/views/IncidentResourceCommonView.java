package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;

import java.util.Date;


/**
 * This represents the view for the common data associated with a resource assigned
 * to an incident. 
 * 
 * @author dprice
 */
public interface IncidentResourceCommonView {

	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId();
	

	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId);
	

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public Boolean getPermanent();
	

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(Boolean permanent);
	

	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted();
	

	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted);
	

	/**
	 * Returns the person.
	 *
	 * @return 
	 *		the person to return
	 */
	public Boolean getPerson();
	

	/**
	 * Sets the person.
	 *
	 * @param person 
	 *			the person to set
	 */
	public void setPerson(Boolean person);
	

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName();
	

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName);
	

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName();
	

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName);
	

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName();
	

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName);
	

	/**
	 * Returns the requestCategory.
	 *
	 * @return 
	 *		the requestCategory to return
	 */
	public String getRequestCategory();
	

	/**
	 * Sets the requestCategory.
	 *
	 * @param requestCategory 
	 *			the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory);
	

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone();
	

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone);
	

	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId();
	

	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	

	/**
	 * Returns the incidentResourceId.
	 *
	 * @return 
	 *		the incidentResourceId to return
	 */
	public Long getIncidentResourceId();
	

	/**
	 * Sets the incidentResourceId.
	 *
	 * @param incidentResourceId 
	 *			the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);
	

	/**
	 * Returns the nameAtIncident.
	 *
	 * @return 
	 *		the nameAtIncident to return
	 */
	public String getNameAtIncident();
	

	/**
	 * Sets the nameAtIncident.
	 *
	 * @param nameAtIncident 
	 *			the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident);
	

	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId();
	

	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId);
	

	/**
	 * Returns the ciCheckInDate.
	 *
	 * @return 
	 *		the ciCheckInDate to return
	 */
	public Date getCiCheckInDate();
	

	/**
	 * Sets the ciCheckInDate.
	 *
	 * @param ciCheckInDate 
	 *			the ciCheckInDate to set
	 */
	public void setCiCheckInDate(Date ciCheckInDate);
	

	/**
	 * Returns the resourceAgencyId.
	 *
	 * @return 
	 *		the resourceAgencyId to return
	 */
	public Long getResourceAgencyId();
	

	/**
	 * Sets the resourceAgencyId.
	 *
	 * @param resourceAgencyId 
	 *			the resourceAgencyId to set
	 */
	public void setResourceAgencyId(Long resourceAgencyId);
	

	/**
	 * Returns the resourceOrganizationId.
	 *
	 * @return 
	 *		the resourceOrganizationId to return
	 */
	public Long getResourceOrganizationId();
	

	/**
	 * Sets the resourceOrganizationId.
	 *
	 * @param resourceOrganizationId 
	 *			the resourceOrganizationId to set
	 */
	public void setResourceOrganizationId(Long resourceOrganizationId);
	

	/**
	 * Returns the resourceAgencyCode.
	 *
	 * @return 
	 *		the resourceAgencyCode to return
	 */
	public String getResourceAgencyCode();
	

	/**
	 * Sets the resourceAgencyCode.
	 *
	 * @param resourceAgencyCode 
	 *			the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode);
	

	/**
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode();
	

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode);
	

	/**
	 * Returns the assignmentId.
	 *
	 * @return 
	 *		the assignmentId to return
	 */
	public Long getAssignmentId();
	

	/**
	 * Sets the assignmentId.
	 *
	 * @param assignmentId 
	 *			the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId);
	

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber();
	

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber);
	

	/**
	 * Returns the assignmentEndDate.
	 *
	 * @return 
	 *		the assignmentEndDate to return
	 */
	public Date getAssignmentEndDate();
	

	/**
	 * Sets the assignmentEndDate.
	 *
	 * @param assignmentEndDate 
	 *			the assignmentEndDate to set
	 */
	public void setAssignmentEndDate(Date assignmentEndDate);
	

	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus();
	

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus);
	

	/**
	 * Returns the kind.
	 *
	 * @return 
	 *		the kind to return
	 */
	public Kind getKind();
	

	/**
	 * Sets the kind.
	 *
	 * @param kind 
	 *			the kind to set
	 */
	public void setKind(Kind kind);

	/**
	 * Returns the kindId.
	 *
	 * @return 
	 *		the kindId to return
	 */
	public Long getKindId();

	/**
	 * Sets the kindId.
	 *
	 * @param kindId 
	 *			the kindId to set
	 */
	public void setKindId(Long kindId);

	/**
	 * Returns the resourceClassification.
	 *
	 * @return 
	 *		the resourceClassification to return
	 */
	public ResourceClassificationEnum getResourceClassification();

	/**
	 * Sets the resourceClassification.
	 *
	 * @param resourceClassification 
	 *			the resourceClassification to set
	 */
	public void setResourceClassification(ResourceClassificationEnum resourceClassification);
	
	/**
	 * Returns the nameOnPictureId.
	 *
	 * @return 
	 *		the nameOnPictureId to return
	 */
	public String getNameOnPictureId();
	/**
	 * Sets the nameOnPictureId.
	 *
	 * @param nameOnPictureId 
	 *			the nameOnPictureId to set
	 */
	public void setNameOnPictureId(String nameOnPictureId);
}