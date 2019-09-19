package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.EntryError;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * Data describing an error in processing an entry.
 * 
 * @author doug
 */
@Entity
@Table(name = "isw_entry_error")
public class EntryErrorImpl extends PersistableImpl implements EntryError {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @Column(name = "ENTRY_ID")
   private Long                entryId                 = 0L;

   @Column(name = "CAUSE")
   private String              cause;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "ERROR_TIMESTAMP")
   private Calendar            errorTimeStamp;

   @Type(type = "org.springframework.orm.hibernate3.support.BlobByteArrayType")
   @Column(name = "ENTRY_DATA")
   private byte []             entryData;

   @Column(name = "ENTRY_UNIQUE_IDENTIFIER")
   private String              entryUniqueIdentifier;

   @Column(name = "ENTRY_UPDATE_DATATYPE_ENUM")
   @Enumerated(EnumType.STRING)
   private UpdateDataTypeEnum  entryUpdateDataTypeEnum = UpdateDataTypeEnum.UNKNOWN;

   @Column(name = "ENTRY_UPDATE_STATE_ENUM")
   @Enumerated(EnumType.STRING)
   private UpdateStateEnum     entryUpdateStateEnum    = UpdateStateEnum.INITIAL;

   @Embedded
   // must be impl for hibernate to work
   private InputDataSourceImpl entryInputDataSource    = new InputDataSourceImpl();

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "RESOLVED_TIMESTAMP")
   private Calendar            resolvedTimeStamp;

   @Column(name = "RESOLVED")
   private boolean             resolved                = false;

   @Column(name = "RESOLVED_COMMENT")
   private String              resolvedComment;

   public EntryErrorImpl() {
      errorTimeStamp = Calendar.getInstance();
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getCause()
    */
   public String getCause() {
      return cause;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getData()
    */
   public byte [] getEntryData() {
      return entryData;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getEntryId()
    */
   public Long getEntryId() {
      return entryId;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getErrorTimeStamp()
    */
   public Calendar getErrorTimeStamp() {
      return errorTimeStamp;
   }

   public void setErrorTimeStamp(Calendar timeStamp) {
      if ( timeStamp == null ) {
         throw new IllegalArgumentException("timeStamp can not be null");
      }
      this.errorTimeStamp = timeStamp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getInputDataSource()
    */
   public InputDataSource getEntryInputDataSource() {
      return entryInputDataSource;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getResolvedTimeStamp()
    */
   public Calendar getResolvedTimeStamp() {
      return resolvedTimeStamp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getUniqueIdentifier()
    */
   public String getEntryUniqueIdentifier() {
      return entryUniqueIdentifier;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getUpdateDataTypeEnum()
    */
   public UpdateDataTypeEnum getEntryUpdateDataTypeEnum() {
      return entryUpdateDataTypeEnum;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getUpdateStateEnum()
    */
   public UpdateStateEnum getEntryUpdateStateEnum() {
      return entryUpdateStateEnum;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#isResoved()
    */
   public boolean isResoved() {
      return resolved;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.EntryError#getResolvedComment()
    */
   public String getResolvedComment() {
      return resolvedComment;
   }

   /**
    * Accessor for cause of error.
    * 
    * @param cause
    *           the cause of the error
    * @see #getCause()
    */
   public final void setCause(String cause) {
      this.cause = cause;
   }

   /**
    * Accessor for the data in the entry that had an error.
    * 
    * @param data
    *           data in the entry that had an error
    * @see #getEntryData()
    */
   public final void setEntryData(byte [] data) {
      this.entryData = data;
   }

   /**
    * Accessor for entry database id.
    * 
    * @param entryId
    *           entry database id
    * @see #getEntryId()
    */
   public final void setEntryId(Long entryId) {
      this.entryId = entryId;
   }

   /**
    * Accessor for input data source of entry.
    * 
    * @param inputDataSource
    *           the inputDataSource to set
    * @see #getEntryInputDataSource()
    */
   public final void setEntryInputDataSource(InputDataSource inputDataSource) {
      this.entryInputDataSource = new InputDataSourceImpl(inputDataSource.getInputSourceEnum(), inputDataSource
               .getOriginator());
   }

   /**
    * Accessor whether or not the error has been resolved.
    * 
    * @param resolved
    *           true if the error has been resolved
    * @see #isResoved()
    */
   public final void setResolved(boolean resolved) {
      this.resolved = resolved;
   }

   /**
    * Accessor for when the error was resolved.
    * 
    * @param resolvedTimeStamp
    *           when the error was resolved
    * @see #getResolvedTimeStamp()
    */
   public final void setResolvedTimeStamp(Calendar resolvedTimeStamp) {
      this.resolvedTimeStamp = resolvedTimeStamp;
   }

   /**
    * Accessor for the uniqueIdentifier of the entry's data
    * 
    * @param uniqueIdentifier
    *           uniqueIdentifier of the entry's data
    * @see #getEntryUniqueIdentifier()
    */
   public final void setEntryUniqueIdentifier(String uniqueIdentifier) {
      this.entryUniqueIdentifier = uniqueIdentifier;
   }

   /**
    * Accessor for updateDataTypeEnum of entry data.
    * 
    * @param updateDataTypeEnum
    *           updateDataTypeEnum of the entry data
    * @see #getEntryUpdateDataTypeEnum()
    */
   public final void setEntryUpdateDataTypeEnum(UpdateDataTypeEnum updateDataTypeEnum) {
      this.entryUpdateDataTypeEnum = updateDataTypeEnum;
   }

   /**
    * Accessor for UpdateStateEnum of the entry at the time the error occured.
    * 
    * @param updateStateEnum
    *           updateStateEnum of the entry at the time the error occured
    * @see #getEntryUpdateStateEnum()
    */
   public final void setEntryUpdateStateEnum(UpdateStateEnum updateStateEnum) {
      this.entryUpdateStateEnum = updateStateEnum;
   }

   /**
    * Accessor for comment describing how or why the error was resolved.
    * 
    * @param resolvedComment
    *           comment describing how or why the error was resolved
    * @see #getResolvedComment()
    */
   public final void setResolvedComment(String resolvedComment) {
      this.resolvedComment = resolvedComment;
   }

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public Long getId() {
      return this.id;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   }
}
