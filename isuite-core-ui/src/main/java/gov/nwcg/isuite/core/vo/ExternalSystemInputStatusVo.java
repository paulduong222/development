package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.types.ExternalSystemInputStateEnum;
import gov.nwcg.isuite.framework.types.ExternalSystemTypeEnum;

import java.util.Calendar;

/**
 * Value Object representing the status of inputs from an external system.
 * 
 * @author doug
 * 
 */
public class ExternalSystemInputStatusVo { //TODO:  Perhaps extend PersistableVo
   private String systemName;
   private ExternalSystemTypeEnum systemType = ExternalSystemTypeEnum.UNKNOWN;
   private long pendingJobCount;
   private Calendar mostRecentJobDate;
   private ExternalSystemInputStateEnum status = ExternalSystemInputStateEnum.UNKNOWN;

   /**
    * Accessor for MostRecentJobDate.
    * 
    * @return the mostRecentJobDate
    * @see #setMostRecentJobDate(Calendar)
    */
   public final Calendar getMostRecentJobDate() {
      return mostRecentJobDate;
   }

   /**
    * Accessor for MostRecentJobDate.
    * 
    * @param mostRecentJobDate
    *           the mostRecentJobDate to set
    * @see #getMostRecentJobDate()
    */
   public final void setMostRecentJobDate(Calendar mostRecentJobDate) {
      this.mostRecentJobDate = mostRecentJobDate;
   }

   /**
    * Accessor for PendingJobCount.
    * 
    * @return the pendingJobCount
    * @see #setPendingJobCount(long)
    */
   public final long getPendingJobCount() {
      return pendingJobCount;
   }

   /**
    * Accessor for PendingJobCount.
    * 
    * @param pendingJobCount
    *           the pendingJobCount to set
    * @see #getPendingJobCount()
    */
   public final void setPendingJobCount(long pendingJobCount) {
      this.pendingJobCount = pendingJobCount;
   }

   /**
    * Accessor for status of input from external system.
    * 
    * @return the status
    * @see #setStatus(ExternalSystemInputStateEnum)
    */
   public final ExternalSystemInputStateEnum getStatus() {
      return status;
   }

   /**
    * Accessor for status of input from external system.
    * 
    * @param status
    *           the status to set, can't be null
    * @see #getStatus()
    */
   public final void setStatus(ExternalSystemInputStateEnum status) {
      if (status == null) {
         throw new IllegalArgumentException("status can not be null");
      }
      this.status = status ;
      
   }

   /**
    * Accessor for name of external system.
    * 
    * @return the systemName
    * @see #setSystemName(String)
    */
   public final String getSystemName() {
      return systemName;
   }

   /**
    * Accessor for name of external system.
    * 
    * @param systemName
    *           the systemName to set
    * @see #getSystemName()
    */
   public final void setSystemName(String systemName) {
      this.systemName = systemName;
   }

   /**
    * Accessor for type of external system.
    * 
    * @return the systemType
    * @see #setSystemType(ExternalSystemTypeEnum)
    */
   public final ExternalSystemTypeEnum getSystemType() {
      return systemType;
   }

   /**
    * Accessor for type of external system.
    * 
    * @param systemType
    *           the systemType to set, can't be null
    * @see #getSystemType()
    */
   public final void setSystemType(ExternalSystemTypeEnum systemType) {
      if ( systemType == null ) {
         throw new IllegalArgumentException("systemType can not be null");
      }
      this.systemType = systemType;
   }

}
