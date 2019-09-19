package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface PasswordHistory extends Persistable{
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
	public void setId(Long id);


	/**
	 * Returns the user.
	 *
	 * @return 
	 *		the user to return
	 */
	public User getUser();

	/**
	 * Sets the user.
	 *
	 * @param user 
	 *			the user to set
	 */
	public void setUser(User user);

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
	public void setUserId(Long userId) ;

	/**
	 * Returns the userPassword.
	 *
	 * @return 
	 *		the userPassword to return
	 */
	public String getUserPassword();

	/**
	 * Sets the userPassword.
	 *
	 * @param userPassword 
	 *			the userPassword to set
	 */
	public void setUserPassword(String userPassword);

	/**
	 * Returns the userPasswordCreatedDate.
	 *
	 * @return 
	 *		the userPasswordCreatedDate to return
	 */
	public Date getUserPasswordCreatedDate();

	/**
	 * Sets the userPasswordCreatedDate.
	 *
	 * @param userPasswordCreatedDate 
	 *			the userPasswordCreatedDate to set
	 */
	public void setUserPasswordCreatedDate(Date userPasswordCreatedDate);


}
