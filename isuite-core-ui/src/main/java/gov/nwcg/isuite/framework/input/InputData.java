package gov.nwcg.isuite.framework.input;

import java.io.Serializable;

/**
 * Represents data to be input into the system.
 * <p>
 * This is <b>not</b> data that is already in the system (ie. persisted) but
 * rather data that has come from an external source that is to be input into
 * the system
 * </p>
 * 
 * @author doug
 */
public interface InputData extends Serializable {

   /**
    * Accessor for the last time that this data was updated.
    * <p>
    * This is set whenever any data on this object is modified.
    * </p>
    * 
    * @return last time that this data was updated public abstract Date
    *         getLastUpdateDate();
    */

   /**
    * Accessor for the unique identifier.
    * <p>
    * The unique identifier is required for all data that is to be synchronized.
    * </p>
    * 
    * @return unique identifier, won't be null
    */
   public String getUniqueIdentifier();

   /**
    * Accessor for the source of the data to be sync'd.
    * 
    * @return source of data, won't be null
    */
   public InputDataSource getSource();

}
