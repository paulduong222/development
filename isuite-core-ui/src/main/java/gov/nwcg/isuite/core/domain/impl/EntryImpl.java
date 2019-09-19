package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Entry;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

/**
 * @author doug
 */
@Entity
@Table(name = "isw_entry")
public class EntryImpl extends PersistableImpl implements Entry {


   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "COMPLETE_TIMESTAMP")
   private Calendar                  completeTimeStamp;                         // set
   // by
   // trigger
   // when
   // state
   // changes
   // to
   // complete
   @Type(type = "org.springframework.orm.hibernate3.support.BlobByteArrayType")
   @Column(name = "ENTRY_DATA")
   // @Transient
   private byte []                   data;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "INITIAL_TIMESTAMP")
   private Calendar                  initialTimeStamp;                          // set
   // by
   // trigger
   // when
   // object
   // saved

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "PROCESS_TIMESTAMP")
   private Calendar                  processTimeStamp;                          // set
   // by
   // trigger
   // when
   // state
   // changes
   // to
   // process

   @Column(name = "UNIQUE_IDENTIFIER")
   private String              uniqueIdentifier;

   @Column(name = "UPDATE_DATATYPE_ENUM")
   @Enumerated(EnumType.STRING)
   private UpdateDataTypeEnum  updateDataTypeEnum;

   @Column(name = "UPDATE_DATASTATE_ENUM")
   @Enumerated(EnumType.STRING)
   private UpdateStateEnum updateStateEnum  = UpdateStateEnum.INITIAL;

   @Embedded
   /*
    * embeddable objects must be classes, not interfaces. however, we want this
    * api of this class to work with interfaces so while this private member may
    * be an impl, the api of the class is an InputDataSource (the interface) and
    * we will convert from the interface to the impl in the constructor.
    */
   private InputDataSourceImpl source;

   /*
    * implementation note: the timestamps are set by the db when the object is
    * saved based on the updateState. Make sure that the timestamps are cleared
    * correctly when the state changes
    */
   
   public EntryImpl(){
	   
   }

   /**
    * This is used for testing and is not set to final.
    * 
    * @return Current System Time
    */
   Calendar getNow() {
      return Calendar.getInstance();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#setUpdateState(gov.nwcg.isuite.domain.update.enterprise.UpdateStateEnum)
    */
   public void setUpdateState(UpdateStateEnum updateState) {
      if ( updateState == null ) {
         throw new IllegalArgumentException("updateState can not be null");
      }
      this.updateStateEnum = updateState;
      switch ( updateState ) {
         case INITIAL:
            this.initialTimeStamp = getNow();
            this.processTimeStamp = null;
            this.completeTimeStamp = null;
            break;
         case PROCESSING:
            this.processTimeStamp = getNow();
            this.completeTimeStamp = null;
            break;
         case COMPLETED:
            this.completeTimeStamp = getNow();
            break;
      }
      /* be sure to adjust timeStamps via trigger correctly */
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getCompleteTimeStamp()
    */
   public Calendar getCompleteTimeStamp() {
      return completeTimeStamp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getData()
    */
   public byte [] getData() {
      return data;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getInitialTimeStamp()
    */
   public Calendar getInitialTimeStamp() {
      return initialTimeStamp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getProcessTimeStamp()
    */
   public Calendar getProcessTimeStamp() {
      return processTimeStamp;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getUniqueIdentifier()
    */
   public String getUniqueIdentifier() {
      return uniqueIdentifier;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getUpdateDataType()
    */
   public UpdateDataTypeEnum getUpdateDataType() {
      return updateDataTypeEnum;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getUpdateState()
    */
   public UpdateStateEnum getUpdateState() {
      return updateStateEnum;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.update.enterprise.Entry#getSource()
    */
   public InputDataSource getSource() {
      return source;
   }

   public void setProcessTimeStamp(Calendar timeStamp) {
      this.processTimeStamp = timeStamp;
   }

   public void setInitialTimeStamp(Calendar timeStamp) {
      this.initialTimeStamp = timeStamp;
   }

   public void setCompleteTimeStamp(Calendar timeStamp) {
      this.completeTimeStamp = timeStamp;
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

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      EntryImpl o = (EntryImpl)obj;
      return new EqualsBuilder()
      	  .append(new Object[]{id,completeTimeStamp,data,initialTimeStamp
      			  			   ,processTimeStamp,uniqueIdentifier,updateDataTypeEnum
      			  			   ,updateStateEnum},
      			  new Object[]{o.id,o.completeTimeStamp,o.data,o.initialTimeStamp
		  			   			,o.processTimeStamp,o.uniqueIdentifier,o.updateDataTypeEnum
		  			   			,o.updateStateEnum})
      	  .appendSuper(super.equals(o))
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,completeTimeStamp,data,initialTimeStamp
	  			   ,processTimeStamp,uniqueIdentifier,updateDataTypeEnum
		  			   ,updateStateEnum})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .appendSuper(super.toString())
	       .toString();
   }   


}
