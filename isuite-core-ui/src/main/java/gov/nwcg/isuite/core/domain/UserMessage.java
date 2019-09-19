package gov.nwcg.isuite.core.domain;


import java.util.Date;

/**
 *
 */
public interface UserMessage {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the user
	 */
	public User getUser();
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user);
	
	/**
	 * @return the userId
	 */
	public Long getUserId();

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long id);
	
	/**
	 * @return the message
	 */
	public Message getMessage();
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message);
	
	/**
	 * @return the messageId
	 */
	public Long getMessageId();

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(Long id);
	
	/**
	 * @return the acknowledgeDate
	 */
	public Date getAcknowledgeDate();
	
	/**
	 * @param acknowledgeDate the acknowledgeDate to set
	 */
	public void setAcknowledgeDate(Date acknowledgeDate);
	
	
}
