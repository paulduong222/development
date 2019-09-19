package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.views.AvailableWorkAreaResourcesView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Data Object representative of Resources associated to a Work Area.  These
 * are both those associated directly and those associated via incident.
 * 
 * @author bsteiner
 */
@Entity
@Table(name="iswv_available_wa_resources")
public class AvailableWorkAreaResourcesViewImpl implements AvailableWorkAreaResourcesView {
   
   @Id
   @Column(name="WORK_AREA_ID")
   private Long workAreaId;
   
   @OneToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID")
   private WorkArea workArea;
   
   @Id
   @Column(name="RESOURCE_ID")
   private Long resourceId;
   
   @OneToOne(targetEntity=ResourceImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="RESOURCE_ID")
   private Resource resource;
   
   @Column(name="RESOURCE_NAME", insertable=false, updatable=false)
   private String resourceName;
   
   @Column(name="LAST_NAME", insertable=false, updatable=false)
   private String lastName;
   
   @Column(name="FIRST_NAME", insertable=false, updatable=false)
   private String firstName;
   
   @Column(name="PERMANENT_RESOURCE_ID", insertable=false, updatable=false)
   private Long permanentResourceId;
   
   @Column(name="ORGANIZATION_ID", insertable=false, updatable=false)
   private Long organizationId;
   
   @Column(name="UNIT_CODE", insertable=false, updatable=false)
   private String unitCode;
   
   @Column(name="REQUEST_CATEGORY", insertable=false, updatable=false)
   private String requestCategory;
   
   @Column(name="REQUEST_CATEGORY_CODE", insertable=false, updatable=false)
   private String requestCategoryCode;
   
   @Column(name="INCIDENT_NAME", insertable=false, updatable=false)
   private String incidentName;
   
   @Column(name="ASSIGNMENT", insertable=false, updatable=false)
   private String assignment;
   
   @Column(name="ASSIGNMENT_STATUS", insertable=false, updatable=false)
   private String assignmentStatus;

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getResource()
    */
   public Resource getResource() {
      return this.resource;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getResourceId()
    */
   public Long getResourceId() {
      return this.resourceId;
   }
   
    /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getWorkArea()
    */
   public WorkArea getWorkArea() {
      return this.workArea;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getWorkAreaId()
    */
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   public String getResourceName() {
	   return resourceName;
   }
   
   public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
   
   public String getUnitCode() {
		return unitCode;
	}
   
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getRequestCategory() {
		return requestCategory;
	}
	
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}
	
	public String getIncidentName() {
		return incidentName;
	}
	
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	public String getAssignment() {
		return assignment;
	}
	
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	public void setPermanentResourceId(Long parentResourceId) {
		this.permanentResourceId = parentResourceId;
	}

	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}

	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

}
