package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;


public class IncidentPicklistVo extends AbstractVo {
   private String name;
   private Long eventTypeId;

   public IncidentPicklistVo() {}
   
   public IncidentPicklistVo(Long id, String name) {
      setId(id);
      setName(name);
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return the eventTypeId
    */
   public Long getEventTypeId() {
      return eventTypeId;
   }

   /**
    * @param eventTypeId the eventTypeId to set
    */
   public void setEventTypeId(Long eventTypeId) {
      this.eventTypeId = eventTypeId;
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
        
        retValue.append("<IncidentPicklistVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<name>").append(this.name).append("</name>").append(nl)   
           .append("</IncidentPicklistVo>");
            
        return retValue.toString();
    }
   
}
