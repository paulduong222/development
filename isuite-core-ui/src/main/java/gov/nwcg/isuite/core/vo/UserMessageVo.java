/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.UserMessage;

import java.util.Date;


/**
 * @author mgreen
 *
 */
public class UserMessageVo extends MessageVo {  

   long userId;
   Date clearedDate;
   
   /**
    * 
    */
   public UserMessageVo() {

   }

   public UserMessageVo(UserMessage msg){
	   
   }
   
   /**
    * @return the userId
    */
   public long getUserId() {
      return userId;
   }

   /**
    * @param userId the userId to set
    */
   public void setUserId(long userId) {
      this.userId = userId;
   }
   
   public Date getClearedDate() {
      return this.clearedDate;
   }
   
   public void setClearedDate(Date clearedDate) {
      this.clearedDate = clearedDate;
   }
   
   @Override
   public String toString() {
      
      final String nl = System.getProperty("line.separator");
      StringBuffer retValue = new StringBuffer();
      String tab = "\t";
      
      retValue
         .append("<UserMessageVo>")
         .append(tab).append("<cause>").append(super.getCause()).append("</cause>").append(nl)
         .append(tab).append("<details>").append(super.getDetails()).append("</details>").append(nl)
         .append(tab).append("<occurrenceDate>").append(super.getOccurrenceDate().toString()).append("</occurranceDate>").append(nl)
         .append(tab).append("<severity>").append(super.getSeverity()).append("</severity>").append(nl)
         .append(tab).append("<title>").append(super.getTitle()).append("</title>").append(nl)
         .append(tab).append("<userId>").append(this.userId).append("</userId>").append(nl)
         .append(tab).append("<clearedDate>").append(this.clearedDate.toString()).append("</clearedDate>").append(nl)
         .append("</UserMessageVo>");
      
      return retValue.toString();
   }
}
