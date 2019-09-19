package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;


import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.core.domain.impl.SystemModulePermImpl;
import gov.nwcg.isuite.core.domain.impl.SystemRolePermImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class SystemRolePermVo extends AbstractVo {
	private SystemModulePermVo systemModulePermVo;
	private SystemRoleVo systemRoleVo;
	private Long systemRoleId;
	private Long systemModulePermId;
	private Boolean roleFlag;

	public SystemRolePermVo() {
		
	}

	/**
	 * Returns the systemModulePermVo.
	 *
	 * @return 
	 *		the systemModulePermVo to return
	 */
	public SystemModulePermVo getSystemModulePermVo() {
		return systemModulePermVo;
	}

	/**
	 * Sets the systemModulePermVo.
	 *
	 * @param systemModulePermVo 
	 *			the systemModulePermVo to set
	 */
	public void setSystemModulePermVo(SystemModulePermVo systemModulePermVo) {
		this.systemModulePermVo = systemModulePermVo;
	}

	/**
	 * Returns the systemRoleVo.
	 *
	 * @return 
	 *		the systemRoleVo to return
	 */
	public SystemRoleVo getSystemRoleVo() {
		return systemRoleVo;
	}

	/**
	 * Sets the systemRoleVo.
	 *
	 * @param systemRoleVo 
	 *			the systemRoleVo to set
	 */
	public void setSystemRoleVo(SystemRoleVo systemRoleVo) {
		this.systemRoleVo = systemRoleVo;
	}

	/**
	 * Returns the roleFlag.
	 *
	 * @return 
	 *		the roleFlag to return
	 */
	public Boolean getRoleFlag() {
		return roleFlag;
	}

	/**
	 * Sets the roleFlag.
	 *
	 * @param roleFlag 
	 *			the roleFlag to set
	 */
	public void setRoleFlag(Boolean roleFlag) {
		this.roleFlag = roleFlag;
	}

	/**
	 * @return the systemRoleId
	 */
	public Long getSystemRoleId() {
		return systemRoleId;
	}

	/**
	 * @param systemRoleId the systemRoleId to set
	 */
	public void setSystemRoleId(Long systemRoleId) {
		this.systemRoleId = systemRoleId;
	}

	/**
	 * @return the systemModulePermId
	 */
	public Long getSystemModulePermId() {
		return systemModulePermId;
	}

	/**
	 * @param systemModulePermId the systemModulePermId to set
	 */
	public void setSystemModulePermId(Long systemModulePermId) {
		this.systemModulePermId = systemModulePermId;
	}
	
	
	public static SystemRolePermVo getInstance(SystemRolePerm entity, Boolean cascadable) throws Exception {
		SystemRolePermVo vo = new SystemRolePermVo();
		
		if(null==entity)
			throw new Exception("Unable to create vo from null systemModulePerm entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(entity.getSystemModulePermId() != null)
			{
				vo.setSystemModulePermId(entity.getSystemModulePermId());
				vo.setSystemModulePermVo(SystemModulePermVo.getInstance(entity.getSystemModulePerm(),cascadable));
			}
			if(entity.getSystemRoleId() != null)
			{
				vo.setSystemRoleId(entity.getSystemRoleId());
				vo.setSystemRoleVo(SystemRoleVo.getInstance(entity.getSystemRole(),cascadable));
			}
			
			vo.setRoleFlag(entity.getRoleFlag());
		}
		
		return vo;
	}
	
	public static Collection<SystemRolePermVo> getInstances(Collection<SystemRolePerm> entities, Boolean cascadable) throws Exception {
		Collection<SystemRolePermVo> vos = new ArrayList<SystemRolePermVo>();
		
		for(SystemRolePerm entity : entities){
			vos.add(SystemRolePermVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SystemRolePerm toEntity(SystemRolePermVo vo, Boolean cascadable) throws Exception {
		SystemRolePerm entity = new SystemRolePermImpl();
		
		if(null == vo)
			throw new Exception("Unable to create SystemRolePerm entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(vo.getSystemModulePermId() != null)
			{
				entity.setSystemModulePermId(vo.getSystemModulePermId());
				entity.setSystemModulePerm(SystemModulePermVo.toEntity(vo.getSystemModulePermVo(), cascadable));
			}
			if(vo.getSystemRoleId() != null)
			{
				entity.setSystemRoleId(vo.getSystemRoleId());
				entity.setSystemRole(SystemRoleVo.toEntity(vo.getSystemRoleVo(), cascadable));
			}
			entity.setRoleFlag(vo.getRoleFlag());
		}
		return entity;
	}
	
	public static Collection<SystemRolePerm> toEntityList(Collection<SystemRolePermVo> vos, Boolean cascadable) throws Exception {
		Collection<SystemRolePerm> entities = new ArrayList<SystemRolePerm>();
		
		for(SystemRolePermVo vo : vos){
			entities.add(SystemRolePermVo.toEntity(vo, cascadable));
		}
		
		return entities;
	}
}
