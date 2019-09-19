package gov.nwcg.isuite.framework.core.filter.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.framework.core.filter.Filter;

/**
 * A common filter class to use since we need current user id and work area id for most queries.
 * @author bsteiner
 * @author aroundy
 */
public class FilterImpl implements Filter {
   
   private static final long serialVersionUID = 7242796627406030226L;
   protected Long currentUserId;
   protected Long workAreaId;

   public FilterImpl() {
      this.reset();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#getCurrentUserId()
    */
   @JsonIgnore
   public Long getCurrentUserId() {
      return this.currentUserId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#getWorkAreaId()
    */
   @JsonIgnore
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#setCurrentUserId(java.lang.Long)
    */
   @JsonIgnore
   public void setCurrentUserId(Long userId) {
      this.currentUserId = userId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#setWorkAreaId(java.lang.Long)
    */
   @JsonIgnore
   public void setWorkAreaId(Long waId) {
      this.workAreaId = waId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   @JsonIgnore
   public void reset() {
      this.currentUserId = null;
      this.workAreaId = null;
   }
   
   @JsonIgnore
   public Boolean determineDeletableValue(String deletableString) {
      if(deletableString == null || deletableString.isEmpty()) {
         return null;
      } else if(deletableString.equalsIgnoreCase("YES")) {
         return true;
      } else {
         return false;
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.filter.Filter#crypticDateConverter(java.lang.String)
    */
   @JsonIgnore
   public Date crypticDateConverter(String dateString) {
     DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

     if(isFullDateFormat(dateString)) {
       try {
         return df.parse(dateString);
       } catch (ParseException e) {
         e.printStackTrace();
       }
     }
     return null;
   }
   
   @JsonIgnore
   private Boolean isFullDateFormat(String dateString) {
     String regEx = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
     Pattern pattern = Pattern.compile(regEx);
     return pattern.matches(regEx, (CharSequence)dateString);
     
     
   }
   
   
}
