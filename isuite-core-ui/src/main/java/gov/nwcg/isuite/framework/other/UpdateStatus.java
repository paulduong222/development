/**
 * 
 */
package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.framework.types.UpdateStateEnum;

/**
 * Status of a update item
 * @author doug
 *
 */
public interface UpdateStatus {
   
   /**
    * Accessor for the state of the item.
    * @return state of the item
    */
   public UpdateStateEnum getState();
   
   /**
    * Accessor for explaination of state, if known.
    * @return explaination of state, may be null
    */
   public String getExplaination();
   
   /**
    * Get identifier of item.
    * @return identifier of item
    */
   public Long getItemId();

}
