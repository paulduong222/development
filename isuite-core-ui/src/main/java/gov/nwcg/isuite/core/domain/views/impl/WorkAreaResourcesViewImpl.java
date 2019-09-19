package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

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
 * The Data Object representative of Resources associated to a Work Area.  These
 * are both those associated directly and those associated via incident.
 * 
 * @author bsteiner
 */
@Entity
@Table(name="iswv_work_area_resources")
public class WorkAreaResourcesViewImpl implements WorkAreaResourcesView {
   
   @Column(name="WORK_AREA_ID", insertable=false, updatable=false)
   private Long workAreaId;
   
   @OneToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID", insertable=false, updatable=false)
   private WorkArea workArea;
   
   @Id
   @Column(name="RESOURCE_ID", insertable=false, updatable=false)
   private Long resourceId;
   
   @OneToOne(targetEntity=ResourceImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="RESOURCE_ID", insertable=false, updatable=false)
   private Resource resource;

   @Column(name="RESOURCE_NAME", insertable=false, updatable=false)
   private String resourceName;
   
   @Column(name="LAST_NAME", insertable=false, updatable=false)
   private String lastName;
   
   @Column(name="FIRST_NAME", insertable=false, updatable=false)
   private String firstName;
   
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
   @Enumerated(EnumType.STRING)
   private AssignmentStatusTypeEnum assignmentStatus;
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getResourceName()
    */
   public String getResourceName() {
	return resourceName;
   }

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setResourceName(java.lang.String)
	 */
   public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getUnitCode()
	 */
   public String getUnitCode() {
		return unitCode;
	}
	
   	/*
   	 * (non-Javadoc)
   	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setUnitCode(java.lang.String)
   	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getRequestCategory()
	 */
	public String getRequestCategory() {
		return requestCategory;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setRequestCategory(java.lang.String)
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setRequestCategoryCode(java.lang.String)
	 */
	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getRequestCategoryCode()
	 */
	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setIncidentName(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getAssignment()
	 */
	public String getAssignment() {
		return assignment;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setAssignment(java.lang.String)
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#getAssignmentStatus()
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.views.WorkAreaResourcesView#setAssignmentStatus(java.lang.String)
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

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

   /**
    * @param resource the resource to set
    */
   public void setResource(Resource resource) {
      this.resource = resource;
   }

   /**
    * @param resourceId the resourceId to set
    */
   public void setResourceId(Long resourceId) {
      this.resourceId = resourceId;
   }

   /**
    * @param workArea the workArea to set
    */
   public void setWorkArea(WorkArea workArea) {
      this.workArea = workArea;
   }

   /**
    * @param workAreaId the workAreaId to set
    */
   public void setWorkAreaId(Long workAreaId) {
      this.workAreaId = workAreaId;
   }
   
   /**
    * @param lastName
    */
   public void setLastName(String lastName) {
		this.lastName = lastName;
	}

   /**
    * @return the lastName
    */
   public String getLastName() {
		return lastName;
	}

	/**
	 * @param firstName
	 */
   	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the firstName
	 */
   	public String getFirstName() {
		return firstName;
	}

}
