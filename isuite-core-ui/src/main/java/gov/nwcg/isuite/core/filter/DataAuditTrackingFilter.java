/**
 * 
 */
package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.*;

/**
 * Generic filter for users
 * @author jbrose
 *
 */
public interface DataAuditTrackingFilter extends Filter {
	/**
	* Retrieve the state date
	* @return startDate
	*/
	public Date getStartDate();
	   
	/**
	* Set the state date
	* @param startDate
	*/
	public void setStartDate(Date startDate);

	/**
	* Retrieve the end date
	* @return endDate
	*/
	public Date getEndDate();

	/**
	* Set the end date
	* @param endDate
	*/
	public void setEndDate(Date endDate);

	public void setAuditEventType(String[] auditEventType);
	public String[] getAuditEventType();

	public String getAuditEvent() ;

	public void setAuditEvent(String auditEvent) ;
	public String getHomeUnit() ;
	public void setHomeUnit(String homeUnit) ;

	public String getPrimaryDispatchCenter();
	public void setPrimaryDispatchCenter(String primaryDispatchCenter) ;
	public String getLastName() ;

	public void setLastName(String lastName);

	public String getFirstName() ;
	public void setFirstName(String firstName) ;
	public String getLoginName();

	public void setLoginName(String loginName);


	public Date getCreatedDate() ;
	public void setCreatedDate(Date createdDate);

	public String getCrypticDateFilterCode();

	public void setCrypticDateFilterCode(String crypticDateFilterCode);
	
	public void setModifiedBy(String modifiedBy);
	public String getModifiedBy();

	/**
	 * @return the backupFilename
	 */
	public String getBackupFilename();

	/**
	 * @param backupFilename the backupFilename to set
	 */
	public void setBackupFilename(String backupFilename);

	/**
	 * @return the backupFilepath
	 */
	public String getBackupFilepath();

	/**
	 * @param backupFilepath the backupFilepath to set
	 */
	public void setBackupFilepath(String backupFilepath);
	/**
	 * @return the backupType
	 */
	public String getBackupType();

	/**
	 * @param backupType the backupType to set
	 */
	public void setBackupType(String backupType);

}

