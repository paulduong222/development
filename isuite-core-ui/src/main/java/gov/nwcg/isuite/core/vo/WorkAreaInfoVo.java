package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.WorkArea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Used to hold information about an workarea, a moniker for an workarea.
 * <p>
 * This object is used to hold information about an workarea without traversing the
 * entire object graph. This object holds information only, it does not
 * represent the workarea.
 * </p>
 * <p>
 * This object is can be used to locate the actual workarea object. It is intended
 * to have a small and constant size memory footprint and to minimize calls to
 * retrieve the workarea (which has a large and because of lazy loading, variable
 * size memory footprint).
 * </p>
 * 
 * 
 * @author doug
 *
 */
public class WorkAreaInfoVo extends WorkAreaPicklistVo {
  
   private static final long serialVersionUID = 1212605360322317177L;
   private Date waCreatedDt;
   
   private List<OrganizationPicklistVo> availableOrganizations;
   
   /* Used to retrieve the "Selected Filter Options" organizations on the create/edit 
    * work area screens.  This is based on the organizations that are tied to work area
    * being modified and organizations that the current user is tied to.
    * */
   private List<OrganizationPicklistVo> selectedOrganizations;
   
   /* Used for copying an existing work area. */
   private Long workAreaIdToBeCopied;
   
   public WorkAreaInfoVo() {
      super();
   }
   
   public WorkAreaInfoVo(WorkArea wa) {
      super(wa);
      setWaCreatedDt(wa.getCreatedDate());
      setWaDesc(wa.getDescription());
   }
   
   /**
    * @return the availableOrganizations
    */
   public List<OrganizationPicklistVo> getAvailableOrganizations() {
      return availableOrganizations;
   }
   
   /**
    * @param availableOrganizations the availableOrganizations to set
    */
   public void setAvailableOrganizations(List<OrganizationPicklistVo> availableOrganizations) {
      this.availableOrganizations = availableOrganizations;
   }
   
   /**
    * @return the selectedOrganizations
    */
   public List<OrganizationPicklistVo> getSelectedOrganizations() {
	   if ( null==selectedOrganizations) {
		   selectedOrganizations = new ArrayList<OrganizationPicklistVo>();
	   }
      return selectedOrganizations;
   }
   
   /**
    * Convenience method to transform organizations to organizationPicklistVos
    * 
    * @param organizations
    */
   public void setSelectedOrganizations(Collection<Organization> organizations) {
	   if (null != organizations) {
		   for (Organization org : organizations) {
			   this.getSelectedOrganizations().add(new OrganizationPicklistVo(org));
		   }
	   }
   }
   
   /**
    * @param selectedOrganizations the selectedOrganizations to set
    */
   public void setSelectedOrganizations(List<OrganizationPicklistVo> selectedOrganizations) {
      this.selectedOrganizations = selectedOrganizations;
   }
   
   /**
    * @return the waCreatedDt
    */
   public Date getWaCreatedDt() {
      return waCreatedDt;
   }
   
   /**
    * @param waCreatedDt the waCreatedDt to set
    */
   public void setWaCreatedDt(Date waCreatedDt) {
      this.waCreatedDt = waCreatedDt;
   }
   
   /**
    * @return the workAreaIdToBeCopied
    */
   public Long getWorkAreaIdToBeCopied() {
      return workAreaIdToBeCopied;
   }

   /**
    * @param workAreaIdToBeCopied the workAreaIdToBeCopied to set
    */
   public void setWorkAreaIdToBeCopied(Long workAreaIdToBeCopied) {
      this.workAreaIdToBeCopied = workAreaIdToBeCopied;
   }

   /**
     * Constructs a <code>String</code> with all attributes in xml format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<WorkAreaInfoVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<waCreatedDt>").append(this.waCreatedDt).append("</waCreatedDt>").append(nl)   
           .append(tab).append("<availableOrganizations>").append(this.availableOrganizations).append("</availableOrganizations>").append(nl)   
           .append(tab).append("<selectedOrganizations>").append(this.selectedOrganizations).append("</selectedOrganizations>").append(nl)   
           .append(tab).append("<workAreaIdToBeCopied>").append(this.workAreaIdToBeCopied).append("</workAreaIdToBeCopied>").append(nl)   
           .append("</WorkAreaInfoVo>");
            
        return retValue.toString();
    }
}
