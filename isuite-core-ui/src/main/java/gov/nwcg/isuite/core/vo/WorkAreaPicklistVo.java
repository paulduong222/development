package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Work Area Picklist objects.
 * 
 * @author bsteiner
 */
public class WorkAreaPicklistVo extends AbstractVo {
   private String waName;
   private String waDesc;
   private String createdByWhom;
   private Collection<WorkAreaIncidentVo> workAreaIncidents = new ArrayList<WorkAreaIncidentVo>();
   
   
   public WorkAreaPicklistVo() {}
   
   public WorkAreaPicklistVo(WorkArea wa) {
      super();
      setId(wa.getId());
      setWaName(wa.getName());
      setWaDesc(wa.getDescription());
      setCreatedByWhom(wa.getCreatedBy());
   }
   
   /**
    * @return the waName
    */
   public String getWaName() {
      return waName;
   }
   
   /**
    * @param waName the waName to set
    */
   public void setWaName(String waName) {
      this.waName = waName;
   }

   public String getWaDesc() {
	   return waDesc;
   }
   
   public void setWaDesc(String desc){
	   this.waDesc=desc;
   }
   
   /**
    * @return the createdByWhom
    */
   public String getCreatedByWhom() {
      return createdByWhom;
   }

   /**
    * @param createdByWhom the createdByWhom to set
    */
   public void setCreatedByWhom(String createdByWhom) {
      this.createdByWhom = createdByWhom;
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
        
        retValue.append("<WorkAreaPicklistVo>").append(nl)
    	     .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<waName>").append(this.waName).append("</waName>").append(nl)   
           .append(tab).append("<createdByWhom>").append(this.createdByWhom).append("</createdByWhom>").append(nl)   
           .append("</WorkAreaPicklistVo>");
            
        return retValue.toString();
    }

	/**
	 * @return the workAreaIncidents
	 */
	public Collection<WorkAreaIncidentVo> getWorkAreaIncidents() {
		if(null==workAreaIncidents)
			workAreaIncidents=new ArrayList<WorkAreaIncidentVo>();
		
		return workAreaIncidents;
	}
	
	/**
	 * @param workAreaIncidents the workAreaIncidents to set
	 */
	public void setWorkAreaIncidents(Collection<WorkAreaIncidentVo> workAreaIncidents) {
		this.workAreaIncidents = workAreaIncidents;
	}

	public void addWorkAreaIncidentVo(WorkAreaIncidentVo waiv){
		this.getWorkAreaIncidents().add(waiv);
	}
}
