package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface UserNotification extends Persistable{

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId();

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) ;

	/**
	 * Returns the user.
	 *
	 * @return 
	 *		the user to return
	 */
	public User getUser() ;

	/**
	 * Sets the user.
	 *
	 * @param user 
	 *			the user to set
	 */
	public void setUser(User user) ;


	/**
	 * Returns the userId.
	 *
	 * @return 
	 *		the userId to return
	 */
	public Long getUserId();

	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *			the userId to set
	 */
	public void setUserId(Long userId);

	/**
	 * Returns the source.
	 *
	 * @return 
	 *		the source to return
	 */
	public String getSource();


	/**
	 * Sets the source.
	 *
	 * @param source 
	 *			the source to set
	 */
	public void setSource(String source);

	/**
	 * Returns the message.
	 *
	 * @return 
	 *		the message to return
	 */
	public String getMessage();

	/**
	 * Sets the message.
	 *
	 * @param message 
	 *			the message to set
	 */
	public void setMessage(String message) ;

	/**
	 * Returns the postedDate.
	 *
	 * @return 
	 *		the postedDate to return
	 */
	public Date getPostedDate();

	/**
	 * Sets the postedDate.
	 *
	 * @param postedDate 
	 *			the postedDate to set
	 */
	public void setPostedDate(Date postedDate) ;

	/**
	 * Returns the readDate.
	 *
	 * @return 
	 *		the readDate to return
	 */
	public Date getReadDate();


	/**
	 * Sets the readDate.
	 *
	 * @param readDate 
	 *			the readDate to set
	 */
	public void setReadDate(Date readDate);

	/**
	 * Returns the readFlag.
	 *
	 * @return 
	 *		the readFlag to return
	 */
	public Boolean getReadFlag();

	/**
	 * Sets the readFlag.
	 *
	 * @param readFlag 
	 *			the readFlag to set
	 */
	public void setReadFlag(Boolean readFlag);
}
