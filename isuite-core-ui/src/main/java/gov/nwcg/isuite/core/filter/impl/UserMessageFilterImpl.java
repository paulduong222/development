package gov.nwcg.isuite.core.filter.impl;


import gov.nwcg.isuite.core.filter.UserMessageFilter;

import java.util.Date;

/**
 * @author mgreen
 *
 */
@SuppressWarnings("serial")
public class UserMessageFilterImpl extends MessageFilterImpl implements UserMessageFilter {

   Long userId;
   Date clearedDate;
   
   public UserMessageFilterImpl() throws Exception {
      super();
//      super.setGlobal(false); //user messages are never global
   }
   
   public Date getClearedDate() {
      return this.clearedDate;
   }

   public Long getUserId() {
      return this.userId;
   }

   public void setClearedDate(Date clearedDate) {
      this.clearedDate = clearedDate;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }
   

   public void reset() {
      super.reset();
      this.userId = null;
      this.clearedDate = null;
   }
}
