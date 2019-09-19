package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserMessage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



//@Entity
//@SequenceGenerator(name="SEQ_USER_MESSAGE", sequenceName="SEQ_USER_MESSAGE")
//@Table(name="isw_user_message")
public class UserMessageImpl implements UserMessage {

//   @Id
//   @GeneratedValue()
//   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "USER_ID", nullable = true)
   private User user;
   
   @Column(name="USER_ID", insertable = false, updatable = false, unique=false, nullable = true)
   private Long userId;
   
   @ManyToOne(targetEntity=MessageImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "MESSAGE_ID", nullable = false)
   private Message message;

   @Column(name="MESSAGE_ID", insertable = false, updatable = false, nullable = false)
   private Long messageId;
   
   @Column(name = "ACKNOWLEDGE_DATE")
   private Date acknowledgeDate;
  
   
   public UserMessageImpl() {

   }
   
   /**
	* @param id the id to set
	*/
   public Long getId() {
	   return this.id;
   }
   
   /**
    * @param userId the userId to set
    */
   public void setId(Long id) {
	   this.id = id;
   }
   
   /**
    * @return the user
    */
   public User getUser() {
      return this.user;
   }

   /**
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }   

   /**
    * return the message
    */
   public Message getMessage() {
      return this.message;
   }
   
   /**
    * @return the messageId
    */
   public Long getMessageId() {
      return this.messageId;
   }

   /**
    * @param message the message to set
    */
   public void setMessage(Message message) {
      this.message = message;
   }

   /**
    * @param messageId the messageId to set
    */
   public void setMessageId(Long messageId) {
      this.messageId = messageId;
   }

   /**
    * @return the userId
    */
   public Long getUserId() {
      return userId;
   }

   /**
    * @param userId the userId to set
    */
   public void setUserId(Long userId) {
      this.userId = userId;
   }

   /**
	* @return the acknowledgeDate
	*/
   public Date getAcknowledgeDate(){
	   return acknowledgeDate;
   }
	
   /**
	* @param acknowledgeDate the acknowledgeDate to set
	*/
   public void setAcknowledgeDate(Date acknowledgeDate){
	   this.acknowledgeDate = acknowledgeDate;
   }

}
