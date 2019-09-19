package gov.nwcg.isuite.core.vo;


import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.Collection;

/**
 * Retrieves information for the Manage Incidents tied to a Work Area.
 * 
 * @author bsteiner
 */
public class WorkAreaIncidentsVo extends AbstractVo {
   private Collection<IncidentGridVo> workAreaIncidents;
   
   public WorkAreaIncidentsVo() {
      super();
   }
   
   public WorkAreaIncidentsVo(WorkArea wa) {
	  super();
	  setId(wa.getId());
   }

   /**
    * @return the workAreaIncidents
    */
   public Collection<IncidentGridVo> getWorkAreaIncidents() {
      return workAreaIncidents;
   }

   /**
    * @param workAreaIncidents the workAreaIncidents to set
    */
   public void setWorkAreaIncidents(Collection<IncidentGridVo> workAreaIncidents) {
      this.workAreaIncidents = workAreaIncidents;
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
        
        retValue.append("<WorkAreaIncidentsVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<workAreaIncidents>").append(this.workAreaIncidents).append("</workAreaIncidents>").append(nl)   
           .append("</WorkAreaIncidentsVo>");
            
        return retValue.toString();
    }
   
}
