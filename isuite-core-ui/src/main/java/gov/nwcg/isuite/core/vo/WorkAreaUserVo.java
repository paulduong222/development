package gov.nwcg.isuite.core.vo;


import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaUserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class WorkAreaUserVo extends AbstractVo {
	private Long workAreaId;
	private UserVo userVo;
	private WorkAreaUserTypeEnum userType;
	private Collection<SystemRoleVo> workAreaUserRoleVos = new ArrayList<SystemRoleVo>();
	
	public WorkAreaUserVo(){
	}
	
	public static WorkAreaUserVo getInstance(WorkAreaUser entity,boolean cascadable,Persistable... persistables) throws Exception {
		WorkAreaUserVo vo = new WorkAreaUserVo();
		
		if(null == entity)
			throw new Exception("Unable to create WorkAreaUserVo from null WorkAreaUser entity");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setWorkAreaId(entity.getWorkAreaId());
			vo.setUserType(entity.getUserType());
			
			if(null != entity.getUser()){
				vo.setUserVo(UserVo.getInstance(entity.getUser(),true));
			}
			
//			if(null != entity.getWorkAreaUserRoles()){
//				vo.setWorkAreaUserRoleVos(SystemRoleVo.getInstances(entity.getWorkAreaUserRoles(),true));
//			}
			
		}
		
		return vo;
	}
	
	public static Collection<WorkAreaUserVo> getInstances(Collection<WorkAreaUser> entities, boolean cascadable) throws Exception {
		Collection<WorkAreaUserVo> vos = new ArrayList<WorkAreaUserVo>();
		
		for(WorkAreaUser entity : entities){
			vos.add(WorkAreaUserVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * Returns a WorkAreaUser entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of WorkAreaUser entity
	 * @throws Exception
	 */
	public static WorkAreaUser toEntity(WorkAreaUser entity, WorkAreaUserVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new WorkAreaUserImpl();

		entity.setId(vo.getId());

		if(cascadable){

			WorkArea waEntity  = (WorkArea)getPersistableObject(persistables,WorkAreaImpl.class);
			if(null == waEntity){
				waEntity = new WorkAreaImpl();
				waEntity.setId(vo.getWorkAreaId());
			}
			entity.setWorkArea(waEntity);
			
			if(null != vo.getUserVo()){
				entity.setUser(UserVo.toEntity(null,vo.getUserVo(), false));
			}
			
//			if(null != vo.getWorkAreaUserRoleVos()){
//				entity.setWorkAreaUserRoles(SystemRoleVo.toEntityList(vo.getWorkAreaUserRoleVos(),false));
//			}

			entity.setUserType(vo.getUserType());
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);

		}

		return entity;
	}

    /**
     * Returns a collection of WorkAreaUser entity instances from a collection of WorkAreaUserVos.
     * 
     * @param vos
     * 			collection of source WorkAreaUserVos
     * @param cascadable
     * 			flag indicating whether the instances should created as a cascadable entity
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 		collection of WorkAreaUser entities
     * @throws Exception
     */
	public static Collection<WorkAreaUser> toEntityList(Collection<WorkAreaUserVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		Collection<WorkAreaUser> entities = new ArrayList<WorkAreaUser>();

		for(WorkAreaUserVo vo : vos){
			entities.add(WorkAreaUserVo.toEntity(null,vo,cascadable,persistables));
		}
		
		return entities;
	}
	
	
	/**
	 * Perform some validation on the WorkAreaUser entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source workAreaUser entity
	 * @throws ValidationException
	 */
	private static void validateEntity(WorkAreaUser entity) throws ValidationException {
	}
	
	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}
	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
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
	 * Returns the workAreaUserRoleVos.
	 *
	 * @return 
	 *		the workAreaUserRoleVos to return
	 */
	public Collection<SystemRoleVo> getWorkAreaUserRoleVos() {
		return workAreaUserRoleVos;
	}

	/**
	 * Sets the workAreaUserRoleVos.
	 *
	 * @param workAreaUserRoleVos 
	 *			the workAreaUserRoleVos to set
	 */
	public void setWorkAreaUserRoleVos(Collection<SystemRoleVo> workAreaUserRoleVos) {
		this.workAreaUserRoleVos = workAreaUserRoleVos;
	}

	/**
	 * Returns the userType.
	 *
	 * @return 
	 *		the userType to return
	 */
	public WorkAreaUserTypeEnum getUserType() {
		return userType;
	}

	/**
	 * Sets the userType.
	 *
	 * @param userType 
	 *			the userType to set
	 */
	public void setUserType(WorkAreaUserTypeEnum userType) {
		this.userType = userType;
	}

}
