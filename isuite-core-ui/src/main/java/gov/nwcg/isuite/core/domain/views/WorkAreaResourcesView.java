package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;


/**
 * This represents the view for resources tied to a Work Area represented by the 
 * ISWV_WORK_AREA_RESOURCES view.
 * 
 * @author bsteiner
 */
public interface WorkAreaResourcesView {

   public Long getWorkAreaId();
   public WorkArea getWorkArea();
   public Long getResourceId();
   public Resource getResource();
   public String getResourceName();
   public String getUnitCode();
   public String getRequestCategory();
   public String getRequestCategoryCode();
   public String getIncidentName();
   public String getAssignment();
   public AssignmentStatusTypeEnum getAssignmentStatus();
   
   /**
    * @param resource the resource to set
    */
   public void setResource(Resource resource);

   /**
    * @param resourceId the resourceId to set
    */
   public void setResourceId(Long resourceId);

   /**
    * @param workArea the workArea to set
    */
   public void setWorkArea(WorkArea workArea);

   /**
    * @param workAreaId the workAreaId to set
    */
   public void setWorkAreaId(Long workAreaId);
   
   /**
    * 
    * @param resourceName the resourceName to set
    */
   public void setResourceName(String resourceName);
   
   /**
    * 
    * @param unitCode the unitCode to set
    */
   public void setUnitCode(String unitCode);
   
   /**
    * 
    * @param requestCategory the requestCategory to set
    */
   public void setRequestCategory(String requestCategory);
   
   /**
    * @param requestCategoryCode the requestCategoryCode to set
    */
   public void setRequestCategoryCode(String requestCategoryCode);
   
   /**
    * 
    * @param incidentName the incidentName to set
    */
   public void setIncidentName(String incidentName);
   
   /**
    * 
    * @param assignment the assignment to set
    */
   public void setAssignment(String assignment);
   
   /**
    * 
    * @param assignmentStatus the assignmentStatus to set
    */
   public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus);
}
