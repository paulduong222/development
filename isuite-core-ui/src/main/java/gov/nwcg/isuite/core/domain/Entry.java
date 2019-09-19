package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Entry tracks the data to be updated as it moves through the update process.
 * 
 * @author doug
 */
public interface Entry extends Persistable, Serializable {

   /**
    * Get the source of this entry data.
    * <p>
    * Source represents the external system that sent the data.
    * </p>
    * 
    * @return source of the entry data.
    */
   public InputDataSource getSource();

   /**
    * Access the updated State.
    * 
    * @param updateState, can't be null
    * @see #getUpdateState()
    */
   public void setUpdateState(UpdateStateEnum updateState);

   /**
    * Access for UpdateDataTypeEnum.
    * 
    * @return UpdateDataTypeEnum
    */
   public UpdateDataTypeEnum getUpdateDataType();

   /**
    * Access for UniqueIdentifier of the EntryData (not the Entry object).
    * 
    * @return UniqueIdentifier
    */

   public String getUniqueIdentifier();

   /**
    * Access for Data to be updated.
    * 
    * @return Data
    */
   public byte[] getData();

   /**
    * Access for state in the update process.
    * 
    * @return UpdateState, will not be null
    * @see #setUpdateState(UpdateStateEnum)
    */
   public UpdateStateEnum getUpdateState();

   /**
    * Access for the timestamp of when the initial object is placed in the
    * database.
    * 
    * @return InitialTimeStamp
    */
   public Calendar getInitialTimeStamp();

   /**
    * Access for the timestamp of when the process starts.
    * 
    * @return ProcessTimeStamp
    */
   public Calendar getProcessTimeStamp();

   /**
    * Access for the timestamp of when the process completes.
    * 
    * @return CompleteTimeStamp
    */
   public Calendar getCompleteTimeStamp();
}
