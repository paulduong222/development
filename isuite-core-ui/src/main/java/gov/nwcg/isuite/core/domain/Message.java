package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface Message extends Persistable {
	
	/**
	 * @return the type
	 */
	public String getType();
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type);
		
	/**
	 * @return the effictiveDate
	 */
	public Date getEffectiveDate();
	
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	 * @return the expireDate
	 */
	public Date getExpireDate();
	
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate);
	
	/**
	 * @return the status
	 */
	public String getStatus();
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
	
	/**
	 * @return the messageText
	 */
	public String getMessageText();
	
	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText);

}
