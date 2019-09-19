package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.UserGroupUserImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;


public class UserGroupUserVo extends AbstractVo implements PersistableVo {
	private UserVo userVo;
    private Collection<SystemRoleVo> userGroupUserRoles = new ArrayList<SystemRoleVo>();
	
	/**
	 * Default constructor
	 */
	public UserGroupUserVo() {
	}

	/**
	 * Returns a UserGroupUserVo instance from a UserGroupUser entity.
	 * 
	 * @param entity
	 *          the source entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of UserGroupUserVo
	 * @throws Exception
	 */
	public static UserGroupUserVo getInstance(UserGroupUser entity,boolean cascadable) throws Exception {	
		UserGroupUserVo vo = new UserGroupUserVo();

		if(null == entity)
			throw new Exception("Unable to create UserGroupUserVo from null UserGroupUser entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if(null != entity.getUser()){
				vo.setUserVo(UserVo.getInstance(entity.getUser(), true));
			}
			
//			if(null != entity.getUserGroupUserRoles()){
//				vo.setUserGroupUserRoles(SystemRoleVo.getInstances(entity.getUserGroupUserRoles(), true));
//			}
		}

		return vo;
	}

	public static Collection<UserGroupUserVo> getInstances(Collection<UserGroupUser> entities, boolean cascadable) throws Exception {
		Collection<UserGroupUserVo> vos = new ArrayList<UserGroupUserVo>();

		for(UserGroupUser entity : entities){
			if (entity.getUser().isEnabled()) {
				vos.add(UserGroupUserVo.getInstance(entity, cascadable));
			}
		}
		return vos;
	}

	public static UserGroupUser toEntity(UserGroupUserVo vo, Boolean cascadable) throws Exception {
		UserGroupUser entity = new UserGroupUserImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			
			if(null != vo.getUserVo()){
				entity.setUser(UserVo.toEntity(null, vo.getUserVo(), false));
			}
			
//			if(null != vo.getUserGroupUserRoles()){
//				entity.setUserGroupUserRoles(SystemRoleVo.toEntityList(vo.getUserGroupUserRoles(), true));
//			}
			
		}
		
		return entity;
	}
	
	public static Collection<UserGroupUser> toEntityList(Collection<UserGroupUserVo> vos, Boolean cascadable) throws Exception {
		Collection<UserGroupUser> entities = new ArrayList<UserGroupUser>();
		
		for(UserGroupUserVo vo : vos){
			entities.add(UserGroupUserVo.toEntity(vo, cascadable));
		}
		return entities;
	}
	
	/**
	 * Returns the userVo.
	 *
	 * @return 
	 *		the userVo to return
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * Sets the userVo.
	 *
	 * @param userVo 
	 *			the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	/**
	 * Returns the userGroupUserRoles.
	 *
	 * @return 
	 *		the userGroupUserRoles to return
	 */
	public Collection<SystemRoleVo> getUserGroupUserRoles() {
		return userGroupUserRoles;
	}

	/**
	 * Sets the userGroupUserRoles.
	 *
	 * @param userGroupUserRoles 
	 *			the userGroupUserRoles to set
	 */
	public void setUserGroupUserRoles(Collection<SystemRoleVo> userGroupUserRoles) {
		this.userGroupUserRoles = userGroupUserRoles;
	}

}
