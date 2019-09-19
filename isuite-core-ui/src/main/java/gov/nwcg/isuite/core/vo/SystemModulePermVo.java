package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.SystemModulePermImpl;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class SystemModulePermVo extends AbstractVo {
	private SystemModuleVo systemModuleVo;
	private Long systemModuleId;
	private String permissionKey;
	private String description;
	private Collection<SystemRolePermVo> systemRolePermVos = new ArrayList<SystemRolePermVo>();

	public SystemModulePermVo() {
		
	}

	/**
	 * Returns the systemModuleVo.
	 *
	 * @return 
	 *		the systemModuleVo to return
	 */
	public SystemModuleVo getSystemModuleVo() {
		return systemModuleVo;
	}

	/**
	 * Sets the systemModuleVo.
	 *
	 * @param systemModuleVo 
	 *			the systemModuleVo to set
	 */
	public void setSystemModuleVo(SystemModuleVo systemModuleVo) {
		this.systemModuleVo = systemModuleVo;
	}

	/**
	 * @return the systemModuleId
	 */
	public Long getSystemModuleId() {
		return systemModuleId;
	}

	/**
	 * @param systemModuleId the systemModuleId to set
	 */
	public void setSystemModuleId(Long systemModuleId) {
		this.systemModuleId = systemModuleId;
	}

	
	/**
	 * Returns the permissionKey.
	 *
	 * @return 
	 *		the permissionKey to return
	 */
	public String getPermissionKey() {
		return permissionKey;
	}

	/**
	 * Sets the permissionKey.
	 *
	 * @param permissionKey 
	 *			the permissionKey to set
	 */
	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	/**
	 * Returns the description.
	 *
	 * @return 
	 *		the description to return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the systemRolePermVos.
	 *
	 * @return 
	 *		the systemRolePermVos to return
	 */
	public Collection<SystemRolePermVo> getSystemRolePermVos() {
		return systemRolePermVos;
	}

	/**
	 * Sets the systemRolePermVos.
	 *
	 * @param systemRolePermVos 
	 *			the systemRolePermVos to set
	 */
	public void setSystemRolePermVos(Collection<SystemRolePermVo> systemRolePermVos) {
		this.systemRolePermVos = systemRolePermVos;
	}
	
	
	public static SystemModulePermVo getInstance(SystemModulePerm entity, Boolean cascadable) throws Exception {
		SystemModulePermVo vo = new SystemModulePermVo();
		
		if(null==entity)
			throw new Exception("Unable to create vo from null systemModulePerm entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(entity.getSystemModuleId() != null)
			{
				vo.setSystemModuleId(entity.getSystemModuleId());
				vo.setSystemModuleVo(SystemModuleVo.getInstance(entity.getSystemModule(),cascadable));
			}
			vo.setDescription(entity.getDescription());
			vo.setPermissionKey(entity.getPermissionKey());
		}
		
		return vo;
	}
	
	public static Collection<SystemModulePermVo> getInstances(Collection<SystemModulePerm> entities, Boolean cascadable) throws Exception {
		Collection<SystemModulePermVo> vos = new ArrayList<SystemModulePermVo>();
		
		for(SystemModulePerm entity : entities){
			vos.add(SystemModulePermVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SystemModulePerm toEntity(SystemModulePermVo vo, Boolean cascadable) throws Exception {
		SystemModulePerm entity = new SystemModulePermImpl();
		
		if(null == vo)
			throw new Exception("Unable to create SystemModulePerm entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(vo.getSystemModuleId() != null)
			{
				entity.setSystemModuleId(vo.getSystemModuleId());
				entity.setSystemModule(SystemModuleVo.toEntity(vo.getSystemModuleVo(), cascadable));
			}
			entity.setPermissionKey(vo.getPermissionKey());
			entity.setDescription(vo.getDescription());
		}
		return entity;
	}
	
	public static Collection<SystemModulePerm> toEntityList(Collection<SystemModulePermVo> vos, Boolean cascadable) throws Exception {
		Collection<SystemModulePerm> entities = new ArrayList<SystemModulePerm>();
		
		for(SystemModulePermVo vo : vos){
			entities.add(SystemModulePermVo.toEntity(vo, cascadable));
		}
		
		return entities;
	}
}
