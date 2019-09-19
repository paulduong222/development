/**
 * 
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.framework.other.UpdateStatus;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

/**
 * Implementation of the status of an update item.
 * @author doug
 *
 */
public class UpdateStatusImpl implements UpdateStatus {
   
   private String explaination;
   private final Long itemId;
   private final UpdateStateEnum state;
   

   /**
    * Constructor.
    * @param itemId
    * @param state state of item
    * @param explaination reason for status
    */
   public UpdateStatusImpl(final Long itemId, final UpdateStateEnum state, String explaination) {
      if ( itemId == null ) {
         throw new IllegalArgumentException("itemId can not be null");
      }
      this.itemId = itemId;
      
      if ( state == null ) {
         throw new IllegalArgumentException("state can not be null");
      }
      this.state = state;
      
      this.explaination = explaination;
   }
   

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.update.enterprise.UpdateStatus#getExplaination()
    */
   public String getExplaination() {
      return explaination;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.update.enterprise.UpdateStatus#getItemId()
    */
   public Long getItemId() {
      return itemId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.update.enterprise.UpdateStatus#getState()
    */
   public UpdateStateEnum getState() {
      return state;
   }

}
