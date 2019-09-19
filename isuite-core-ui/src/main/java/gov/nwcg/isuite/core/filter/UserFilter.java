/**
 * 
 */
package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Collection;
import java.util.Date;

/**
 * Generic filter for users
 * @author doug
 *
 */
public interface UserFilter extends Filter {

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() ;

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) ;

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() ;

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) ;

	/**
	 * Returns the loginName.
	 *
	 * @return 
	 *		the loginName to return
	 */
	public String getLoginName() ;

	/**
	 * Sets the loginName.
	 *
	 * @param loginName 
	 *			the loginName to set
	 */
	public void setLoginName(String loginName) ;

	/**
	 * Returns the homeUnitId.
	 *
	 * @return 
	 *		the homeUnitId to return
	 */
	public String getHomeUnitId() ;

	/**
	 * Sets the homeUnitId.
	 *
	 * @param homeUnitId 
	 *			the homeUnitId to set
	 */
	public void setHomeUnitId(String homeUnitId) ;

	/**
	 * Returns the homeUnitCode.
	 *
	 * @return 
	 *		the homeUnitCode to return
	 */
	public String getHomeUnitCode() ;

	/**
	 * Sets the homeUnitCode.
	 *
	 * @param homeUnitCode 
	 *			the homeUnitCode to set
	 */
	public void setHomeUnitCode(String homeUnitCode) ;

	/**
	 * Returns the homeUnitName.
	 *
	 * @return 
	 *		the homeUnitName to return
	 */
	public String getHomeUnitName() ;

	/**
	 * Sets the homeUnitName.
	 *
	 * @param homeUnitName 
	 *			the homeUnitName to set
	 */
	public void setHomeUnitName(String homeUnitName) ;

	/**
	 * Returns the pdcUnitId.
	 *
	 * @return 
	 *		the pdcUnitId to return
	 */
	public String getPdcUnitId() ;

	/**
	 * Sets the pdcUnitId.
	 *
	 * @param pdcUnitId 
	 *			the pdcUnitId to set
	 */
	public void setPdcUnitId(String pdcUnitId) ;

	/**
	 * Returns the pdcUnitCode.
	 *
	 * @return 
	 *		the pdcUnitCode to return
	 */
	public String getPdcUnitCode() ;

	/**
	 * Sets the pdcUnitCode.
	 *
	 * @param pdcUnitCode 
	 *			the pdcUnitCode to set
	 */
	public void setPdcUnitCode(String pdcUnitCode) ;

	/**
	 * Returns the pdcUnitName.
	 *
	 * @return 
	 *		the pdcUnitName to return
	 */
	public String getPdcUnitName() ;

	/**
	 * Sets the pdcUnitName.
	 *
	 * @param pdcUnitName 
	 *			the pdcUnitName to set
	 */
	public void setPdcUnitName(String pdcUnitName) ;

	/**
	 * Returns the userRoleVos.
	 *
	 * @return 
	 *		the userRoleVos to return
	 */
	public Collection<SystemRoleVo> getUserRoleVos() ;

	/**
	 * Sets the userRoleVos.
	 *
	 * @param userRoleVos 
	 *			the userRoleVos to set
	 */
	public void setUserRoleVos(Collection<SystemRoleVo> userRoleVos) ;

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() ;

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) ;

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() ;

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable); 

	/**
	 * Returns the excludeRoles.
	 *
	 * @return 
	 *		the excludeRoles to return
	 */
	public Collection<String> getExcludeRoles();

	/**
	 * Sets the excludeRoles.
	 *
	 * @param excludeRoles 
	 *			the excludeRoles to set
	 */
	public void setExcludeRoles(Collection<String> excludeRoles);

	/**
	 * Returns the includeRoles.
	 *
	 * @return 
	 *		the includeRoles to return
	 */
	public Collection<String> getIncludeRoles() ;

	/**
	 * Sets the includeRoles.
	 *
	 * @param includeRoles 
	 *			the includeRoles to set
	 */
	public void setIncludeRoles(Collection<String> includeRoles);

	/**
	 * Returns the enabledString.
	 *
	 * @return 
	 *		the enabledString to return
	 */
	public String getEnabledString();
	/**
	 * Sets the enabledString.
	 *
	 * @param enabledString 
	 *			the enabledString to set
	 */
	public void setEnabledString(String enabledString);	
	
   /**
    * @return the deletableString
    */
   public String getDeletableString();

   /**
    * @param deletableString the deletableString to set
    */
   public void setDeletableString(String deletableString);
   
   /**
    * 
    * @return
    */
   public String getCrypticDateFilterCode();
   
   /**
    * 
    * @param crypticDateFilterCode
    */
   public void setCrypticDateFilterCode(String crypticDateFilterCode);
}
