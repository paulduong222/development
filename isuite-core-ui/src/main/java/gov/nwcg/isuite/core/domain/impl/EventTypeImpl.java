/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;



import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Represents an event type.
 * @author mpoll
 *
 */
@Entity
@Table(name="iswl_event_type")
public class EventTypeImpl extends PersistableImpl implements EventType {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @Column(name="EVENT_TYPE_CODE", length=4)
	private String eventTypeCode;
	
   @Column(name="EVENT_TYPE", length=30)
	private String eventType;
	
   /**
    * Full constructor.
    * @param identity unique Identity, can't be null
    * @param eventTypeCode code of event type, can't be null
    * @param eventType description of event type, can't be null
    */
	public EventTypeImpl(String identity, String eventTypeCode, String eventType) {
      super();
		setEventTypeCode(eventTypeCode);
		setEventType(eventType);
	}
	
   /**
    * Constructor.
    * @param eventTypeCode code of event type, can't be null
    * @param eventType description of event type, can't be null
    */
   public EventTypeImpl(String eventTypeCode, String eventType) {
      super();
      setEventTypeCode(eventTypeCode);
      setEventType(eventType);
   }
   
   /**
    * Default Constructor
    */
   public EventTypeImpl() {
      super();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.EventType#getEventTypeCode()
    */
   public String getEventTypeCode() {
      return eventTypeCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.EventType#setEventTypeCode(java.lang.String)
    */
   public void setEventTypeCode(String eventTypeCode) {
      if (eventTypeCode == null) {
         throw new IllegalArgumentException("eventTypeCode can not be null");
      }
      this.eventTypeCode = eventTypeCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.EventType#getEventType()
    */
   public String getEventType() {
      return eventType;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.EventType#setEventType(java.lang.String)
    */
   public void setEventType(String eventType) {
      if (eventType == null) {
         throw new IllegalArgumentException("eventType can not be null");
      }
      this.eventType = eventType;
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
      EventTypeImpl o = (EventTypeImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,eventType,this.eventTypeCode},
      			new Object[]{o.id,o.eventType,o.eventTypeCode})
     	.appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(id)
	  	.append(eventType)
	  	.append(eventTypeCode)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("eventType", eventType)
	       .append("eventTypeCode", eventTypeCode)
	       .appendSuper(super.toString())
	       .toString();
   }   
   
}
