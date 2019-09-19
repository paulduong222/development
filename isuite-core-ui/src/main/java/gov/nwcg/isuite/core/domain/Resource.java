package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.ResourceStatusTypeEnum;

import java.util.Collection;
import java.util.Date;

/**
 * The interface definition for a Resource.
 * 
 * @author bsteiner
 */
public interface Resource extends Persistable{
	
	/**
	 * Return the name of the resource.
	 * 
	 * @return
	 * 		the resource name to return
	 */
	public String getResourceName();
	
	/**
	 * Set the name of the resource
	 * 
	 * @param name
	 * 			the resource name to set
	 */
	public void setResourceName(String resourceName);
	
	/**
	 * Return the first name.
	 * 
	 * @return
	 * 		the first name to return
	 */
	public String getFirstName();
	
	/**
	 * Sets the first name.
	 * 
	 * @param fname
	 * 			the first name to set
	 */
	public void setFirstName(String fname);
	
	/**
	 * Returns the last name.
	 * 
	 * @return
	 * 		the last name to return
	 */
	public String getLastName();
	
	/**
	 * Sets the last name.
	 * 
	 * @param fname
	 * 			the last name to set
	 */
	public void setLastName(String lname);
	
	/**
	 * Get a Parent Resource if one is there.
	 * 
	 * @return a {@link Resource} if a parent exists.
	 */
	public Resource getParentResource();
	
	/**
	 * Set a parent resource.
	 * @param resource
	 */
	public void setParentResource(Resource resource);
   
   /**
    * Returns associated collection of child resources.
    * 
    * @return
    * 		collection of child resources to return
    */
   public Collection<Resource> getChildResources();
   
   /**
    * Sets the associated collection of child resources.
    * 
    * @param childResources
    * 			the collection of child resources to set
    */
   public void setChildResources(Collection<Resource> childResources);

	/**
	 * Get a Permanent Resource if one is there.
	 * 
	 * @return a {@link Resource} if a parent exists.
	 */
	public Resource getPermanentResource();
	
	/**
	 * Set a Permanent resource.
	 * @param resource
	 */
	public void setPermanentResource(Resource resource);
   
   /**
    * Returns the parent Resource Id.
    * 
    * @return
    * 		the parent resource id to return
    */
   public Long getParentResourceId();
   
   /**
    * Sets the parent resource id.
    * 
    * @param parentResourceId
    * 			the parent resource id to set
    */
   public void setParentResourceId(Long parentResourceId);

   /**
    * Returns the permanent Resource Id.
    * 
    * @return
    * 		the permanent resource id to return
    */
   public Long getPermanentResourceId();
   
   /**
    * Sets the permanent resource id.
    * 
    * @param id
    * 			the permanent resource id to set
    */
   public void setPermanentResourceId(Long id);
   
   /**
    * Returns the organization.
    * 
    * @return 
    * 		the organization to return
    */
   public Organization getOrganization();

   /**
    * Sets the organization.
    * 
    * @param organization 
    * 			the organization to set
    */
   public void setOrganization(Organization organization);
   
   /**
    * Returns the organization id.
    * 
    * @return 
    * 		the organizationId to return
    */
   public Long getOrganizationId();

   /**
    * Sets the organization id.
    * 
    * @param organizationId 
    * 			the organizationId to set
    */
   public void setOrganizationId(Long organizationId);
   
   /**
    * Returns the primary dispatch center (which is an Organization).
    * 
    * @return 
    * 		the organization to return
    */
   public Organization getPrimaryDispatchCenter();

   /**
    * Sets the primary dispatch center (which is an Organization).
    * 
    * @param organization 
    * 			the organization to set
    */
   public void setPrimaryDispatchCenter(Organization organization);
   
   /**
    * Returns the organization id.
    * 
    * @return 
    * 		the organizationId to return
    */
   public Long getPrimaryDispatchCenterId();

   /**
    * Sets the organization id.
    * 
    * @param organizationId 
    * 			the organizationId to set
    */
   public void setPrimaryDispatchCenterId(Long organizationId);
   
   /**
    * Returns the resource classification.
    * 
    * @return
    * 		the resource classification to return
    */
   public ResourceClassificationEnum getResourceClassification();
   
   /**
    * Sets the resource classification.
    * 
    * @param type
    * 			the resource classification type to set
    */
   public void setResourceClassification(ResourceClassificationEnum type);
   
   /**
    * Returns the agency.
    * 
    * @return
    * 		the agency to return
    */
   public Agency getAgency();
   
   /**
    * Sets the agency.
    * 
    * @param agency
    * 			the agency to set
    */
   public void setAgency(Agency agency);
   
   /**
    * Returns the agency id.
    * 
    * @return
    * 	the agency id to return
    */
   public Long getAgencyId();
   
   /**
    * Sets the agency id.
    * 
    * @param agencyId
    * 			the agency id to set
    */
   public void setAgencyId(Long agencyId);
   
   /**
    * Returns the name on picture id.
    * 
    * @return
    * 		the name of picture id to return
    */
   public String getNameOnPictureId();
   
   /**
    * Sets the name on picture id.
    * 
    * @param name
    * 			the name on picture id to set
    */
   public void setNameOnPictureId(String name);
   
   /**
    * Returns collection of incidents associated with resource.
    * 
    * @return
    * 		collection of incidents associated with resource
    */
   public Collection<Incident> getIncidents();

   /**
    * Sets the collection of incidents associated with resource.
    * 
    * @param incidents
    * 			the collection of incidents associated with resource to set
    */
	public void setIncidents(Collection<Incident> incidents);
   
   /**
    * Returns the resource mobilizations.
    * 
    * @return
    * 		the resource mobilizations to return
    */
	public Collection<ResourceMobilization> getResourceMobilizations();
   
	/**
	 * Sets the resource mobilizations.
	 * 
	 * @param list
	 * 		the resource mobilizations to set
	 */
	public void setResourceMobilizations(Collection<ResourceMobilization> list);
   
	/**
	 * Sets whether the resource is a contracted resource.
	 * 
	 * @param val
	 * 			the contracted status of the resource to set
	 */
	public void setContracted(Boolean val);
   
	/**
	 * Returns whether the resource is contracted.
	 * 
	 * @return
	 * 		whether the resource is a contracted resource
	 */
	public Boolean isContracted();

   /**
    * Sets whether the resource is a person resource.
    * 
    * @param person
    *          the person status of the resource to set
    */
   public void setPerson(Boolean person);
   
   /**
    * Returns whether the resource is a person.
    * 
    * @return
    *       whether the resource is a person resource
    */
   public Boolean isPerson();

   /**
    * Sets whether the resource is a leader resource.
    * 
    * @param leader
    *          the leader status of the resource to set
    */
   public void setLeader(Boolean leader);
   
   /**
    * Returns whether the resource is a leader.
    * 
    * @return
    *       whether the resource is a leader resource
    */
   public Boolean isLeader();
   
	/**
    * Sets the contact name.
    * 
    * @param name
    * 			the contact name to set
    */
	public void setContactName(String name);
   
   /**
    * Returns the contact name.
    * 
    * @return
    * 	the contact name to return
    */
   public String getContactName();
   
   /**
    * Sets the resource contact phone number.
    * 
    * @param phone
    * 			the resource contact phone number to set
    */
   public void setPhone(String phone);
   
   /**
    * Returns the contact resource phone number.
    * 
    * @return
    * 	the resource contact phone number to return
    */
   public String getPhone();
   
   /**
    * Sets the resource contact email.
    * 
    * @param email
    * 		the resource contact email to set
    */
   public void setEmail(String email);
   
   /**
    * Returns the resource contact email.
    * 
    * @return
    * 		the resource contact email to return
    */
   public String getEmail();
   
   /**
    * Sets the enabled status of the resource.
    * 
    * @param val
    * 			the enabled status of the resource to set
    */
   public void setEnabled(Boolean val);
   
   /**
    * Returns if the resource is enabled.
    * 
    * @return
    * 		the enabled status of the resource
    */
   public Boolean isEnabled();
   
   /**
    * Sets whether the resource is permanent.
    * 
    * @param val
    * 		 	the permanent status of the resource to set
    */
   public void setPermanent(Boolean val);
   
   /**
    * Returns if the resource is permanent.
    * 
    * @return
    * 		whether the resource is a permanent resource
    */
   public Boolean isPermanent();

   /**
    * Returns list of incident resources.
    * 
    * @return
    * 		list of incidentresources to return
    */
   public Collection<IncidentResource> getIncidentResources();
   
   /**
    * Sets the list of incidentResources.
    * 
    * @param incResources
    * 			the list of incidentResources to set
    */
   public void setIncidentResources(Collection<IncidentResource> incResources);

   /**
    * @return
    */
   public Collection<ResourceKind> getResourceKinds();
   
   /**
    * @param list
    */
   //public void setResourceKinds(Collection<ResourceKind> list);
   
   public void addResourceKind(ResourceKind rk);
   
   /**
    * Sets the resource other1.
    * 
    * @param val
    * 			the resource other1 to set
    */
   public void setOther1(String val);
   
   /**
    * Returns the other 1.
    * 
    * @return
    * 	the other 1 to return
    */
   public String getOther1();

   /**
    * Sets the resource other2.
    * 
    * @param val
    * 			the resource other2 to set
    */
   public void setOther2(String val);
   
   /**
    * Returns the other 2.
    * 
    * @return
    * 	the other 2 to return
    */
   public String getOther2();

   /**
    * Sets the resource other3.
    * 
    * @param val
    * 			the resource other3 to set
    */
   public void setOther3(String val);
   
   /**
    * Returns the other 3.
    * 
    * @return
    * 	the other 3 to return
    */
   public String getOther3();

   /**
    * Sets the resource status.
    * 
    * @param val
    * 		the resource status to set
    */
   public void setResourceStatus(ResourceStatusTypeEnum val);
   
   /**
    * Returns the resource status.
    * 
    * @return
    * 		the resource status to return
    */
   public ResourceStatusTypeEnum getResourceStatus();

   /**
    * Returns the deleted date.
    * 
    * @return
    * 		the date the resource is deleted
    */
   public Date getDeletedDate();
   
   /**
    * Sets the deleted date.
    * 
    * @param dt
    * 		the date the resource is deleted
    */
   public void setDeletedDate(Date dt);

	/**
	 * Sets the number of personnel
	 * 
	 * @param number
	 * 			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long number);
	
	/**
	 * Returns the number of personnel
	 * 
	 * @return
	 * 			the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel();

	/**
	 * Returns the leaderType.
	 *
	 * @return 
	 *		the leaderType to return
	 */
	public Integer getLeaderType();

	/**
	 * Sets the leaderType.
	 *
	 * @param leaderType 
	 *			the leaderType to set
	 */
	public void setLeaderType(Integer leaderType);

	/**
	 * @return the contractors
	 */
	public Collection<Contractor> getContractors() ;

	/**
	 * @param contractors the contractors to set
	 */
	public void setContractors(Collection<Contractor> contractors);	
	
	/**
	 * @return the employmentType
	 */
	public EmploymentTypeEnum getEmploymentType() ;

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(EmploymentTypeEnum employmentType);

	public Boolean getComponent() ;
	
	public void setComponent(Boolean component) ;

	/**
	 * @return the rossResId
	 */
	public Long getRossResId() ;

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) ;

	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() ;
	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) ;	
	
	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) ;

	/**
	 * @return the contractor
	 */
	public Contractor getContractor() ;

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) ;

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() ;
	
	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) ;

	/**
	 * @return the contractorAgreement
	 */
	public ContractorAgreement getContractorAgreement() ;

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) ;

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() ;
	
	/**
	 * @param nonStandardOrganizations
	 */
	public void setNonStandardOrganizations(Collection<Organization> nonStandardOrganizations);
	
	/**
	 * @return the nonStandardOrganizations
	 */
	public Collection<Organization> getNonStandardOrganizations();
	
	/**
	 * @param rossResourceName the rossResourceName to set
	 */
	public void setRossResourceName(String rossResourceName);

	/**
	 * @return the rossResourceName
	 */
	public String getRossResourceName();
	
	/**
	 * @param rossFirstName the rossFirstName to set
	 */
	public void setRossFirstName(String rossFirstName);

	/**
	 * @return the rossFirstName
	 */
	public String getRossFirstName();

	/**
	 * @param rossLastName the rossLastName to set
	 */
	public void setRossLastName(String rossLastName);

	/**
	 * @return the rossLastName
	 */
	public String getRossLastName();
	
	/**
	 * @param lastRossUpdatedDate the lastRossUpdatedDate to set
	 */
	public void setLastRossUpdatedDate(Date lastRossUpdatedDate);
		
	/**
	 * @return the lastRossUpdatedDate
	 */
	public Date getLastRossUpdatedDate();
	
	
	public String getRossGroupAssignment();

	public void setRossGroupAssignment(String rossGroupAssignment) ;

}
