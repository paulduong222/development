package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;

import java.util.Date;

/**
 * This Vo will be used to represent a Message Domain Object.
 * @author ncollette
 *
 */
public class MessageVo extends AbstractVo implements PersistableVo { 

   private MessageCauseEnum cause;
   private String details;
   private Date occurrenceDate;
   private MessageSeverityEnum severity;
   private String title;
   private boolean active;
   private boolean global;
   private boolean selected = false;
   
   public MessageVo() {
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Message toEntity(Persistable entity) throws Exception {
	   return populateEntity(this, ((Message)entity));
   }
   
   /**
    * Populates and returns an entity object with the values from the vo object.
    * 
    * @param vo
    * 			the source vo object
    * @param entity
    * 			the entity to populate and return
    * @return
    * 		the entity to return
    */
   private static Message populateEntity(MessageVo vo, Message entity) {
	   if(null==entity){
		   entity = new MessageImpl();
	   }
	   
	   entity.setId(vo.getId());
	   
	   return entity;
   }
   
   public MessageCauseEnum getCause() {
      return cause;
   }
   public void setCause(MessageCauseEnum cause) {
      this.cause = cause;
   }
   public String getDetails() {
      return details;
   }
   public void setDetails(String details) {
      this.details = details;
   }
   public Date getOccurrenceDate() {
      return occurrenceDate;
   }
   public void setOccurrenceDate(Date occurrenceDate) {
      this.occurrenceDate = occurrenceDate;
   }
   public MessageSeverityEnum getSeverity() {
      return severity;
   }
   public void setSeverity(MessageSeverityEnum severity) {
      this.severity = severity;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public boolean isActive() {
      return this.active;
   }
   public void setActive(boolean isActive) {
      this.active = isActive;
   }
   public boolean isGlobal() {
      return this.global;
   }
   public void setGlobal(boolean isGlobal) {
      this.global = isGlobal;
   }
   public boolean isSelected() {
      return selected;
   }
   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
