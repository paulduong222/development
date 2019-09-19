package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Filter for a user.
 * @author doug
 */
public class UserFilterImpl extends FilterImpl implements UserFilter {

	private static final long serialVersionUID = 6872321116677638024L;
	
	private String firstName;
	private String lastName;
	private String loginName;
	
	private String homeUnitId;
	private String homeUnitCode;
	private String homeUnitName;
	private String pdcUnitId;
	
	private String pdcUnitCode;
	private String pdcUnitName;
	
	private Collection<SystemRoleVo> userRoleVos = new ArrayList<SystemRoleVo>();

	private Boolean enabled;
	private String enabledString;
//	private Date createdDate;
	
	private Boolean deletable=false;
	private String deletableString;
	
	private Collection<String> excludeRoles = new ArrayList<String>();
	private Collection<String> includeRoles = new ArrayList<String>();
	
	private String crypticDateFilterCode;
	
	public UserFilterImpl() {
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the loginName.
	 *
	 * @return 
	 *		the loginName to return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the loginName.
	 *
	 * @param loginName 
	 *			the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Returns the homeUnitId.
	 *
	 * @return 
	 *		the homeUnitId to return
	 */
	public String getHomeUnitId() {
		return homeUnitId;
	}

	/**
	 * Sets the homeUnitId.
	 *
	 * @param homeUnitId 
	 *			the homeUnitId to set
	 */
	public void setHomeUnitId(String homeUnitId) {
		this.homeUnitId = homeUnitId;
	}

	/**
	 * Returns the homeUnitCode.
	 *
	 * @return 
	 *		the homeUnitCode to return
	 */
	public String getHomeUnitCode() {
		return homeUnitCode;
	}

	/**
	 * Sets the homeUnitCode.
	 *
	 * @param homeUnitCode 
	 *			the homeUnitCode to set
	 */
	public void setHomeUnitCode(String homeUnitCode) {
		this.homeUnitCode = homeUnitCode;
	}

	/**
	 * Returns the homeUnitName.
	 *
	 * @return 
	 *		the homeUnitName to return
	 */
	public String getHomeUnitName() {
		return homeUnitName;
	}

	/**
	 * Sets the homeUnitName.
	 *
	 * @param homeUnitName 
	 *			the homeUnitName to set
	 */
	public void setHomeUnitName(String homeUnitName) {
		this.homeUnitName = homeUnitName;
	}

	/**
	 * Returns the pdcUnitId.
	 *
	 * @return 
	 *		the pdcUnitId to return
	 */
	public String getPdcUnitId() {
		return pdcUnitId;
	}

	/**
	 * Sets the pdcUnitId.
	 *
	 * @param pdcUnitId 
	 *			the pdcUnitId to set
	 */
	public void setPdcUnitId(String pdcUnitId) {
		this.pdcUnitId = pdcUnitId;
	}

	/**
	 * Returns the pdcUnitCode.
	 *
	 * @return 
	 *		the pdcUnitCode to return
	 */
	public String getPdcUnitCode() {
		return pdcUnitCode;
	}

	/**
	 * Sets the pdcUnitCode.
	 *
	 * @param pdcUnitCode 
	 *			the pdcUnitCode to set
	 */
	public void setPdcUnitCode(String pdcUnitCode) {
		this.pdcUnitCode = pdcUnitCode;
	}

	/**
	 * Returns the pdcUnitName.
	 *
	 * @return 
	 *		the pdcUnitName to return
	 */
	public String getPdcUnitName() {
		return pdcUnitName;
	}

	/**
	 * Sets the pdcUnitName.
	 *
	 * @param pdcUnitName 
	 *			the pdcUnitName to set
	 */
	public void setPdcUnitName(String pdcUnitName) {
		this.pdcUnitName = pdcUnitName;
	}

	/**
	 * Returns the userRoleVos.
	 *
	 * @return 
	 *		the userRoleVos to return
	 */
	public Collection<SystemRoleVo> getUserRoleVos() {
		return userRoleVos;
	}

	/**
	 * Sets the userRoleVos.
	 *
	 * @param userRoleVos 
	 *			the userRoleVos to set
	 */
	public void setUserRoleVos(Collection<SystemRoleVo> userRoleVos) {
		this.userRoleVos = userRoleVos;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * Returns the excludeRoles.
	 *
	 * @return 
	 *		the excludeRoles to return
	 */
	public Collection<String> getExcludeRoles() {
		return excludeRoles;
	}

	/**
	 * Sets the excludeRoles.
	 *
	 * @param excludeRoles 
	 *			the excludeRoles to set
	 */
	public void setExcludeRoles(Collection<String> excludeRoles) {
		this.excludeRoles = excludeRoles;
	}

	/**
	 * Returns the includeRoles.
	 *
	 * @return 
	 *		the includeRoles to return
	 */
	public Collection<String> getIncludeRoles() {
		return includeRoles;
	}

	/**
	 * Sets the includeRoles.
	 *
	 * @param includeRoles 
	 *			the includeRoles to set
	 */
	public void setIncludeRoles(Collection<String> includeRoles) {
		this.includeRoles = includeRoles;
	}

	/**
	 * Returns the enabledString.
	 *
	 * @return 
	 *		the enabledString to return
	 */
	public String getEnabledString() {
		return enabledString;
	}

	/**
	 * Sets the enabledString.
	 *
	 * @param enabledString 
	 *			the enabledString to set
	 */
	public void setEnabledString(String enabledString) {
		this.enabledString = enabledString;
	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserFilter#getDeletableString()
    */
   public String getDeletableString() {
      return deletableString;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserFilter#setDeletableString(java.lang.String)
    */
   public void setDeletableString(String deletableString) {
      this.deletableString = deletableString;
      this.setDeletable(super.determineDeletableValue(this.deletableString));
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserFilter#getCrypticDateFilterCode()
    */
   @Override
   public String getCrypticDateFilterCode() {
	   return this.crypticDateFilterCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.UserFilter#setCrypticDateFilterCode(java.lang.String)
    */
   @Override
   public void setCrypticDateFilterCode(String crypticDateFilterCode) {
	   this.crypticDateFilterCode = crypticDateFilterCode;
   }
	
}
