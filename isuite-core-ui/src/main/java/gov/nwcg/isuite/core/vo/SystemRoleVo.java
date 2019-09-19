package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.xml.SystemRoleType;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class SystemRoleVo extends AbstractVo {
	private String roleName;
	private Boolean privilegedRole;
	private String displayName;

	public SystemRoleVo() {
		
	}

	public static SystemRoleVo getInstance(SystemRole entity, Boolean cascadable) throws Exception {
		SystemRoleVo vo = new SystemRoleVo();
		
		if(null==entity)
			throw new Exception("Unable to create vo from null systemrole entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setRoleName(entity.getRoleName());
			vo.setPrivilegedRole(entity.getPrivilegedRole());
			if(null != entity.getRoleName()){
				//if(entity.getRoleName().equals("ROLE_CHECKIN_DEMOB"))
				//	vo.setDisplayName("Check-In / Demob");
				//else
					vo.setDisplayName(entity.getDisplayName());
			}else
				vo.setDisplayName(entity.getDisplayName());
		}
		
		return vo;
	}
	
	public static Collection<SystemRoleVo> getInstances(Collection<SystemRole> entities, Boolean cascadable) throws Exception {
		Collection<SystemRoleVo> vos = new ArrayList<SystemRoleVo>();
		
		for(SystemRole entity : entities){
			vos.add(SystemRoleVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SystemRole toEntity(SystemRoleVo vo, Boolean cascadable) throws Exception {
		SystemRole entity = new SystemRoleImpl();
		
		if(null == vo)
			throw new Exception("Unable to create systemrole entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setPrivilegedRole(vo.getPrivilegedRole());
			if(vo.getDisplayName() != null)
				entity.setDisplayName(vo.getDisplayName());
			if(vo.getRoleName() != null)
				entity.setRoleName(vo.getRoleName());
		}
		return entity;
	}
	
	public static Collection<SystemRole> toEntityList(Collection<SystemRoleVo> vos, Boolean cascadable) throws Exception {
		Collection<SystemRole> entities = new ArrayList<SystemRole>();
		
		for(SystemRoleVo vo : vos){
			entities.add(SystemRoleVo.toEntity(vo, cascadable));
		}
		
		return entities;
	}

	/**
	 * Transforms a SystemRole entity into a SystemRoleType xml object.
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static SystemRoleType toXmlObject(SystemRole entity) throws Exception {
		SystemRoleType xmlObject = SystemRoleType.valueOf(entity.getRoleName());
		
		return xmlObject;
	}

	/**
	 * @param srType
	 * @return
	 * @throws Exception
	 */
	public static SystemRole toEntity(SystemRoleType srType) throws Exception {
		SystemRole entity = new SystemRoleImpl();

		entity.setRoleName(srType.name());
		
		return entity;
	}
	
	/**
	 * @param srTypes
	 * @return
	 * @throws Exception
	 */
	public static Collection<SystemRole> toEntityList(Collection<SystemRoleType> srTypes) throws Exception {
		Collection<SystemRole> entities = new ArrayList<SystemRole>();
		
		for(SystemRoleType srType : srTypes){
			entities.add(SystemRoleVo.toEntity(srType));
		}
		
		return entities;
	}
	
	/**
	 * Converts a collection of system roles with only role names set to 
	 * a collection of system roles with the id set.
	 * 
	 * @param srEntities
	 * @return
	 * @throws Exception
	 */
	public static Collection<SystemRole> toEntityList(Collection<SystemRole> srEntities, Collection<SystemRoleVo> vos) throws Exception {
		Collection<SystemRole> newEntities = new ArrayList<SystemRole>();
		
		for(SystemRole entity : srEntities){
			for(SystemRoleVo vo : vos){
				if(entity.getRoleName().equals(vo.getRoleName())){
					SystemRole sr = new SystemRoleImpl();
					sr.setId(vo.getId());
					//entity.setId(vo.getId());
					newEntities.add(sr);
				}
			}
		}
		
		return newEntities;
	}
	
	public static boolean hasRole(Long roleId,Collection<SystemRoleVo> vos) throws Exception {
		if(null != vos){
			for(SystemRoleVo vo : vos){
				if(vo.getId().compareTo(roleId)==0)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the roleName.
	 *
	 * @return 
	 *		the roleName to return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the roleName.
	 *
	 * @param roleName 
	 *			the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Returns the privilegedRole.
	 *
	 * @return 
	 *		the privilegedRole to return
	 */
	public Boolean getPrivilegedRole() {
		return privilegedRole;
	}

	/**
	 * Sets the privilegedRole.
	 *
	 * @param privilegedRole 
	 *			the privilegedRole to set
	 */
	public void setPrivilegedRole(Boolean privilegedRole) {
		this.privilegedRole = privilegedRole;
	}
	
	/**
	 * Returns the displayName.
	 *
	 * @return 
	 *		the displayName to return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the displayName.
	 *
	 * @param displayName 
	 *			the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
