package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;

/**
 * @author bsteiner
 *
 */
public interface AgencyFilter extends Filter {

   /**
    * This filter is used on the Account Code Screen.  The agency list changes based 
    * upon whether or not the event type is a wildland fire or not.  If this 
    * filter is set, the dao will ignore the other filters in here and provide 
    * lists based on whether or not it is a wildland fire or not.
    * 
    * @return
    */
   public Long getEventTypeId();
   
   /**
    * Get agency code to filter by.
    * 
    * @return
    */
   public String getAgencyCode();
   
   /**
    * Get agency name to filter by.
    *  
    * @return
    */
   public String getAgencyName();
   
   /**
    * Get agency name to filter by.
    * 
    * @return
    */
   public AgencyTypeEnum getAgencyType();
   
}
