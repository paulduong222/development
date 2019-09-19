package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class UserGridVo extends AbstractVo {
	private String loginName;
	private String firstName;
	private String lastName;
	private Long homeUnitId;
	private String homeUnitCode;
	private String homeUnitName;
	private Long pdcUnitId;
	private String pdcUnitCode;
	private String pdcUnitName;
	private Date createdDate;
	private Boolean enabled;
	private Boolean deletable;
	private Collection<SystemRoleVo> userRoles = new ArrayList<SystemRoleVo>();
	private String roleNames;
	
	// convenience properties
	private Long workAreaUserId;
	
	public UserGridVo() {
		
	}
	
	public static UserGridVo getInstance(User entity,Boolean cascadable) throws Exception {
		UserGridVo vo = new UserGridVo();

		if(null == entity)
			throw new Exception("Unable to create userGridVo from null user entity");
		
		if(cascadable){
			vo.setId(entity.getId());
			vo.setLoginName(entity.getLoginName());
			vo.setFirstName(entity.getFirstName().toUpperCase());
			vo.setLastName(entity.getLastName().toUpperCase());
			if(null != entity.getCreatedDate())
				vo.setCreatedDate(entity.getCreatedDate());
			vo.setEnabled(entity.isEnabled());
			
			if(entity.getLastLoginDate() == null) {
			   vo.setDeletable(true);
			} else {
			   vo.setDeletable(false);
			}
			
			vo.setHomeUnitId(entity.getHomeUnitId());
			if(null != entity.getHomeUnit()){
				vo.setHomeUnitCode(entity.getHomeUnit().getUnitCode());
				vo.setHomeUnitName(entity.getHomeUnit().getName());
			}
			
			vo.setPdcUnitId(entity.getPrimaryDispatchCenterId());
			if(null != entity.getPrimaryDispatchCenter()){
				vo.setPdcUnitCode(entity.getPrimaryDispatchCenter().getUnitCode());
				vo.setPdcUnitName(entity.getPrimaryDispatchCenter().getName());
			}
			
			String val="";
			if(null != entity.getSystemRoles()){
				vo.setUserRoles(SystemRoleVo.getInstances(entity.getSystemRoles(), true));
				
				for(SystemRoleVo srvo : vo.getUserRoles()){
					if(val=="")
						val=srvo.getDisplayName();
					else
						val=val + ", " + srvo.getDisplayName();
				}
			}
			vo.setRoleNames(val);
			
		}
		
		return vo;
	}

	public static Collection<UserGridVo> getInstances(Collection<User> entities,Boolean cascadable) throws Exception {	
		Collection<UserGridVo> vos = new ArrayList<UserGridVo>();
		
		for(User entity : entities){
			vos.add(UserGridVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	public static Collection<UserGridVo> getInstancesFromWorkAreaUser(Collection<WorkAreaUser> entities,Boolean cascadable) throws Exception {
		Collection<UserGridVo> vos = new ArrayList<UserGridVo>();
		
		for(WorkAreaUser entity : entities){
			UserGridVo vo = new UserGridVo();
				vo.setWorkAreaUserId(entity.getId());
				vo.setId(entity.getUserId());
				if(null != entity.getUser()){
					vo.setFirstName(entity.getUser().getFirstName().toUpperCase());
					vo.setLoginName(entity.getUser().getLoginName());
					vo.setLastName(entity.getUser().getLastName().toUpperCase());
					
					if(null != entity.getUser().getHomeUnit())
						vo.setHomeUnitCode(entity.getUser().getHomeUnit().getUnitCode());

					if(null != entity.getUser().getPrimaryDispatchCenter())
						vo.setPdcUnitCode(entity.getUser().getPrimaryDispatchCenter().getUnitCode());
					
					if(null != entity.getUser().getSystemRoles()){
						for(SystemRole sr : entity.getUser().getSystemRoles()){
							vo.getUserRoles().add(SystemRoleVo.getInstance(sr, true));
						}
					}
				}
				
//				if(null != entity.getWorkAreaUserRoles()){
//					vo.setUserRoles(SystemRoleVo.getInstances(entity.getWorkAreaUserRoles(), true));
//				}
				
			vos.add(vo);
		}
		
		return vos;
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
	 * Returns the createdDate.
	 *
	 * @return 
	 *		the createdDate to return
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * Sets the createdDate.
	 *
	 * @param createdDate 
	 *			the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * Returns the userRoles.
	 *
	 * @return 
	 *		the userRoles to return
	 */
	public Collection<SystemRoleVo> getUserRoles() {
		return userRoles;
	}
	
	/**
	 * Sets the userRoles.
	 *
	 * @param userRoles 
	 *			the userRoles to set
	 */
	public void setUserRoles(Collection<SystemRoleVo> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * Returns the pdcUnitId.
	 *
	 * @return 
	 *		the pdcUnitId to return
	 */
	public Long getPdcUnitId() {
		return pdcUnitId;
	}

	/**
	 * Sets the pdcUnitId.
	 *
	 * @param pdcUnitId 
	 *			the pdcUnitId to set
	 */
	public void setPdcUnitId(Long pdcUnitId) {
		this.pdcUnitId = pdcUnitId;
	}

	/**
	 * Returns the homeUnitId.
	 *
	 * @return 
	 *		the homeUnitId to return
	 */
	public Long getHomeUnitId() {
		return homeUnitId;
	}

	/**
	 * Sets the homeUnitId.
	 *
	 * @param homeUnitId 
	 *			the homeUnitId to set
	 */
	public void setHomeUnitId(Long homeUnitId) {
		this.homeUnitId = homeUnitId;
	}

	/**
	 * Returns the workAreaUserId.
	 *
	 * @return 
	 *		the workAreaUserId to return
	 */
	public Long getWorkAreaUserId() {
		return workAreaUserId;
	}

	/**
	 * Sets the workAreaUserId.
	 *
	 * @param workAreaUserId 
	 *			the workAreaUserId to set
	 */
	public void setWorkAreaUserId(Long workAreaUserId) {
		this.workAreaUserId = workAreaUserId;
	}

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
      return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }

public String getRoleNames() {
	return roleNames;
}

public void setRoleNames(String roleNames) {
	this.roleNames = roleNames;
}

}
