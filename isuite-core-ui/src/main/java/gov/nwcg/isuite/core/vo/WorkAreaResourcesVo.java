package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.WorkArea;

import java.util.Collection;

/**
 * Retrieves information for the Manage Resources tied to a Work Area.
 * 
 * @author bsteiner
 */
public class WorkAreaResourcesVo extends WorkAreaPicklistVo {

   private static final long serialVersionUID = 8385965346792591304L;
   private Collection<ResourceGridVo> workAreaResources;
   
   public WorkAreaResourcesVo() {}
   
   public WorkAreaResourcesVo(WorkArea wa) {
      super(wa);
   }

   /**
    * @return the workAreaResources
    */
   public Collection<ResourceGridVo> getWorkAreaResources() {
      return workAreaResources;
   }

   /**
    * @param workAreaResources the workAreaResources to set
    */
   public void setWorkAreaResources(Collection<ResourceGridVo> workAreaResources) {
      this.workAreaResources = workAreaResources;
   }

   /**
     * Constructs a <code>String</code> with all attributes
     * in xml format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString()
    {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<WorkAreaResourcesVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<workAreaResources>").append(this.workAreaResources).append("</workAreaResources>").append(nl)   
           .append("</WorkAreaResourcesVo>");
            
        return retValue.toString();
    }
   
}
