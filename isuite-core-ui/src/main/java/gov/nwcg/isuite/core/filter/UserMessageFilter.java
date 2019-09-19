/**
 * 
 */
package gov.nwcg.isuite.core.filter;


import java.util.Date;


/**
 * @author mgreen
 *
 */
//public interface UserMessageFilter extends Filter {
public interface UserMessageFilter extends MessageFilter {

   public Long getUserId();
   public void setUserId(Long userId);
   
   public Date getClearedDate();
   public void setClearedDate(Date clearedDate);
   
}
