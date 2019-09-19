/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;


/**
 * Manages InputServices based on <code>UpdateDataTypeEnum</code>.
 * 
 * @author doug
 */
public interface InputServiceFactory {

   /**
    * Retreive the appropriate InputService for the given dataType.
    * 
    * @param dataType
    *           type of data to be input
    * @return the appropriate InputService for the given dataType, can be null
    *         if no input service exists for that data type
    */
   public InputService getInputService(UpdateDataTypeEnum dataType);

}
