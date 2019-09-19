package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Resource;



/**
 * This represents the view for resources <b>ELIGIBLE TO BE</b> tied to a Work Area.  
 * Represented by the ISWV_AVAILABLE_WA_RESOURCES view.
 * 
 * @author bsteiner
 */
public interface AvailableWorkAreaResourcesView {

   public Long getResourceId();
   public Resource getResource();
   public String getResourceName();
   public String getUnitCode();
   public Long getOrganizationId();
   public String getRequestCategory();
   public String getRequestCategoryCode();
   public String getIncidentName();
   public String getAssignment();
   public String getAssignmentStatus();
   public String getLastName();
   public String getFirstName();
}
