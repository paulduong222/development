package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.util.Calendar;

/**
 * Stores an error about processing an entry.
 * @author doug
 *
 */
public interface EntryError extends Persistable {
   
   /**
    * Accessor for the id of the entry that had an error.
    * @return id of entry
    */
   public Long getEntryId();
   
   /**
    * Cause of error.
    * @return cause of error
    */
   public String getCause();
   
   /**
    * Time at which the error occured.
    * @return time at which the error occured
    */
   public Calendar getErrorTimeStamp();
   
   /**
    * Entry data.
    * @return entry data
    */
   public byte[] getEntryData();
   
   /**
    * Unique identifier of entry.
    * @return unique identifier of entry
    */
   public String getEntryUniqueIdentifier();
   
   /**
    * UpdateDataTypeEnum of entry.
    * @return updateDataTypeEnum of entry
    */
   public UpdateDataTypeEnum getEntryUpdateDataTypeEnum();
   
   /**
    * State of entry at time of error.
    * @return state of entry at time of error
    */
   public UpdateStateEnum getEntryUpdateStateEnum();
   
   /**
    * Source of entry.
    * @return source of entry
    */
   public InputDataSource getEntryInputDataSource();
   
   /**
    * Get time that the error was resolved.
    * @return time that the error was resolved
    */
   public Calendar getResolvedTimeStamp();
   
   /**
    * Has error been resolved.
    * @return true if error has been resolved
    */
   public boolean isResoved();
   
   /**
    * Comment on how or why the error was resolved.
    * @return comment on how or why the error was resolved
    */
   public String getResolvedComment();

}
