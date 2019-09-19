package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author bsteiner
 *
 */
public class UserGroupVo extends AbstractVo implements PersistableVo {
	private String groupName;
	private UserVo groupOwnerVo;
	private Collection<UserGroupUserVo> userGroupUserVos = new ArrayList<UserGroupUserVo>();

	/**
	 * Default constructor
	 */
	public UserGroupVo() {
	}

	/**
	 * Returns a UserGroupVo instance from a UserGroup entity.
	 * 
	 * @param entity
	 *          the source entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of UserGroupVo
	 * @throws Exception
	 */
	public static UserGroupVo getInstance(UserGroup entity,boolean cascadable) throws Exception {
		UserGroupVo vo = new UserGroupVo();

		if(null == entity)
			throw new Exception("Unable to create UserGroupVo from null UserGroup entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setGroupName(entity.getGroupName());
			vo.setCreatedDate(entity.getCreatedDate());
			vo.setCreatedBy(entity.getCreatedBy());

			if(null != entity.getGroupOwner()){
				vo.setGroupOwnerVo(UserVo.getInstance(entity.getGroupOwner(), true));
			}
			
			if(null != entity.getUserGroupUsers()){
				vo.setUserGroupUserVos(UserGroupUserVo.getInstances(entity.getUserGroupUsers(), true));
			}
			
		}

		return vo;
	}

	public static Collection<UserGroupVo> getInstances(Collection<UserGroup> entities, boolean cascadable) throws Exception {
		Collection<UserGroupVo> vos = new ArrayList<UserGroupVo>();

		for(UserGroup entity : entities){
			vos.add(UserGroupVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	public static UserGroup toEntity(UserGroupVo vo, Boolean cascadable) throws Exception {
		UserGroup entity = new UserGroupImpl();
		
		entity.setId(vo.getId());
		//entity.setCreatedBy(vo.getCreatedBy());
		//entity.setCreatedDate(vo.getCreatedDate());
		if(null != vo.getGroupOwnerVo() && null != vo.getGroupOwnerVo().getId())
			entity.setGroupOwnerId(vo.getGroupOwnerVo().getId());
		
		if(cascadable){
			entity.setGroupName(vo.getGroupName().toUpperCase());
			
			if(null != vo.getGroupOwnerVo())
				entity.setGroupOwner(UserVo.toEntity(null,vo.getGroupOwnerVo(),false));
		
			if(null != vo.getUserGroupUserVos()){
				entity.setUserGroupUsers(UserGroupUserVo.toEntityList(vo.getUserGroupUserVos(),true));
			}
		}
		
		return entity;
	}
	
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	} 

	/**
	 * Returns the groupOwnerVo.
	 *
	 * @return 
	 *		the groupOwnerVo to return
	 */
	public UserVo getGroupOwnerVo() {
		return groupOwnerVo;
	}

	/**
	 * Sets the groupOwnerVo.
	 *
	 * @param groupOwnerVo 
	 *			the groupOwnerVo to set
	 */
	public void setGroupOwnerVo(UserVo groupOwnerVo) {
		this.groupOwnerVo = groupOwnerVo;
	}

	/**
	 * Returns the userGroupUserVos.
	 *
	 * @return 
	 *		the userGroupUserVos to return
	 */
	public Collection<UserGroupUserVo> getUserGroupUserVos() {
		return userGroupUserVos;
	}

	/**
	 * Sets the userGroupUserVos.
	 *
	 * @param userGroupUserVos 
	 *			the userGroupUserVos to set
	 */
	public void setUserGroupUserVos(Collection<UserGroupUserVo> userGroupUserVos) {
		this.userGroupUserVos = userGroupUserVos;
	}

}
