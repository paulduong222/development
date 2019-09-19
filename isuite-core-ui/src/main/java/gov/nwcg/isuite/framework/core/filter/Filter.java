package gov.nwcg.isuite.framework.core.filter;

import java.io.Serializable;
import java.util.Date;

/**
 * Base for all filters.
 * 
 * @author dougAnderson
 * @author aroundy
 */
public interface Filter extends Serializable {
   
   /**
    * Reset filter fields to the default values.
    */
   public void reset();
   
   /**
    * Retrieve the work area id.
    * 
    * @return work area id.
    */
   public Long getWorkAreaId();
   
   /**
    * 
    * @param waId
    */
   public void setWorkAreaId(Long waId);
   
   /**
    * Retreive the current user id.
    * 
    * @return current user id.
    */
   public Long getCurrentUserId();
   
   /**
    * 
    * @param userId
    */
   public void setCurrentUserId(Long userId);
   
   /**
    * Convert a crypticDateString to a date if it is in format MM/DD/YYYY
    * 
    * @param dateString
    * @return Date
    */
   public Date crypticDateConverter(String dateString);
   
}
