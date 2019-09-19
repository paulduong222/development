package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemModule;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.SystemModuleImpl;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class SystemModuleVo extends AbstractVo {
	private String name;
	private Collection<SystemModulePermVo> systemModulePermVos = new ArrayList<SystemModulePermVo>();

	public SystemModuleVo() {
		
	}

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the systemModulePermVos.
	 *
	 * @return 
	 *		the systemModulePermVos to return
	 */
	public Collection<SystemModulePermVo> getSystemModulePermVos() {
		return systemModulePermVos;
	}

	/**
	 * Sets the systemModulePermVos.
	 *
	 * @param systemModulePermVos 
	 *			the systemModulePermVos to set
	 */
	public void setSystemModulePermVos(
			Collection<SystemModulePermVo> systemModulePermVos) {
		this.systemModulePermVos = systemModulePermVos;
	}
	
	
	
	public static SystemModuleVo getInstance(SystemModule entity, Boolean cascadable) throws Exception {
		SystemModuleVo vo = new SystemModuleVo();
		
		if(null==entity)
			throw new Exception("Unable to create vo from null SystemModule entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setName(entity.getName());
		}
		
		return vo;
	}
	
	public static Collection<SystemModuleVo> getInstances(Collection<SystemModule> entities, Boolean cascadable) throws Exception {
		Collection<SystemModuleVo> vos = new ArrayList<SystemModuleVo>();
		
		for(SystemModule entity : entities){
			vos.add(SystemModuleVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SystemModule toEntity(SystemModuleVo vo, Boolean cascadable) throws Exception {
		SystemModule entity = new SystemModuleImpl();
		
		if(null == vo)
			throw new Exception("Unable to create SystemModule entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setName(vo.getName());
		}
		return entity;
	}
	
	public static Collection<SystemModule> toEntityList(Collection<SystemModuleVo> vos, Boolean cascadable) throws Exception {
		Collection<SystemModule> entities = new ArrayList<SystemModule>();
		
		for(SystemModuleVo vo : vos){
			entities.add(SystemModuleVo.toEntity(vo, cascadable));
		}
		
		return entities;
	}
	
}
