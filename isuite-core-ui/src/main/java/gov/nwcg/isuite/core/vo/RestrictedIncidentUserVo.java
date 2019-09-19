package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Values representing a RestrictedIncidentUser.
 * 
 */
public class RestrictedIncidentUserVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo;
	private UserVo userVo;
	private RestrictedIncidentUserTypeEnum userType;
	private String accessGrantedBy;
	private Date accessEndDate;
	private Collection<SystemRoleVo> userRoleVos = new ArrayList<SystemRoleVo>();
    private Date defaultCheckinDate;
    private String defaultCheckinType;

	public RestrictedIncidentUserVo() {
	}

	/**
	 * Returns a RestrictedIncidentUserVo instance from a RestrictedIncidentUser entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of RestrictedIncidentUserVo
	 * @throws Exception
	 */
	public static RestrictedIncidentUserVo getInstance(RestrictedIncidentUser entity,boolean cascadable) throws Exception {
		RestrictedIncidentUserVo vo = new RestrictedIncidentUserVo();

		if(null == entity)
			throw new Exception("Unable to create RestrictedIncidentUserVo from null RestrictedIncidentUser entity.");

		vo.setId(entity.getId());

		
		if(cascadable){
			vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			vo.setAccessGrantedBy(entity.getAccessGrantedBy());
			vo.setAccessEndDate(entity.getAccessEndDate());
			vo.setUserType(entity.getUserType());
			vo.setDefaultCheckinDate(entity.getDefaultCheckinDate());
			vo.setDefaultCheckinType(entity.getDefaultCheckinType());
			
			if(null != entity.getUser()){
				vo.setUserVo(UserVo.getInstance(entity.getUser(), true));
			}
			
//			if(null != entity.getUserRoles()){
//				vo.setUserRoleVos(SystemRoleVo.getInstances(entity.getUserRoles(), true));
//			}
		}

		return vo;
	}


	public static Collection<RestrictedIncidentUserVo> getInstances(Collection<RestrictedIncidentUser> entities, boolean cascadable) throws Exception {
		Collection<RestrictedIncidentUserVo> vos = new ArrayList<RestrictedIncidentUserVo>();

		for(RestrictedIncidentUser entity : entities){
			if(entity.getUser().isEnabled() && entity.getAccessEndDate() == null)
				vos.add(RestrictedIncidentUserVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a RestrictedIncidentUser entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of RestrictedIncidentUser entity
	 * @throws Exception
	 */
	public static RestrictedIncidentUser toEntity(RestrictedIncidentUser entity, RestrictedIncidentUserVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new RestrictedIncidentUserImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setUserType(vo.getUserType());

			/*
			 * Get incident in the persistables array if available.
			 */
			Incident incidentEntity = (Incident)getPersistableObject(persistables,IncidentImpl.class);
			if(null != incidentEntity)
				entity.setIncident(incidentEntity);

			if(null != vo.getUserVo()){
				entity.setUser(UserVo.toEntity(null, vo.getUserVo(), false));
			}

			entity.setDefaultCheckinDate(vo.getDefaultCheckinDate());
			entity.setDefaultCheckinType(vo.getDefaultCheckinType());
			
//			if(null != vo.getUserRoleVos()){
//				if(null == entity.getUserRoles())
//					entity.setUserRoles(new ArrayList<SystemRole>());
//				entity.setUserRoles(SystemRoleVo.toEntityList(vo.getUserRoleVos(), true));
//			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);

		}

		return entity;
	}

	/**
	 * Returns a collection of RestrictedIncidentUser entities from a collection of RestrictedIncidentUser Vos.
	 * 
	 * @param vos
	 * 			the source collection of RestrictedIncidentUser vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			collection of RestrictedIncidentUser entities
	 * @throws Exception
	 */
	public static Collection<RestrictedIncidentUser> toEntityList(Collection<RestrictedIncidentUser> entities,Collection<RestrictedIncidentUserVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		if(null==entities)
			entities = new ArrayList<RestrictedIncidentUser>();

		for(RestrictedIncidentUserVo vo : vos){
			entities.add(RestrictedIncidentUserVo.toEntity(null,vo,cascadable,persistables));
		}

		return entities;
	}

	/**
	 * @param newList
	 * @param incidentUsers
	 * @return
	 */
	public static Collection<RestrictedIncidentUser> toEntityRemoveList(Collection<RestrictedIncidentUserVo> newList, Collection<RestrictedIncidentUser> incidentUsers){
		Collection<RestrictedIncidentUser> removeList = new ArrayList<RestrictedIncidentUser>();

		if(!CollectionUtility.hasValue(incidentUsers))
			return removeList;

		for(RestrictedIncidentUser riu : incidentUsers){
			Boolean found=false;

			for(RestrictedIncidentUserVo riuVo : newList){
				if(riu.getUserId().compareTo(riuVo.getUserVo().getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(riu);
			}
		}

		return removeList;
	}

	public static Collection<RestrictedIncidentUser> toEntityAddList(Collection<RestrictedIncidentUserVo> vos, Collection<RestrictedIncidentUser> incidentUsers, Incident entity) throws Exception {
		Collection<RestrictedIncidentUser> addList = new ArrayList<RestrictedIncidentUser>();

		if(!CollectionUtility.hasValue(incidentUsers)){
			/*
			 * Add all
			 */
			for(RestrictedIncidentUserVo riuVo : vos){
				RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
				riu.setUserType(riuVo.getUserType());
				riu.setIncident(entity);
				riu.setUser(UserVo.toEntity(null,riuVo.getUserVo(),false));
				addList.add(riu);
			}
			return addList;
		}

		for(RestrictedIncidentUserVo riuVo : vos){
			Boolean found=false;

			for(RestrictedIncidentUser riu : incidentUsers){
				if(riu.getUserId().compareTo(riuVo.getUserVo().getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
				riu.setUserType(RestrictedIncidentUserTypeEnum.USER);
				riu.setIncident(entity);
				riu.setUser(UserVo.toEntity(null,riuVo.getUserVo(),false));
				addList.add(riu);
			}
		}

		return addList;
	}
	
	
	/**
	 * Perform some validation on the restrictedIncidentUser entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source restrictedIncidentUser entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RestrictedIncidentUser entity) throws ValidationException {
		Validator.validateEntityField("user", entity.getUser(), true);
    	Validator.validateStringField("userType", entity.getUserType().toString(), 15, true);
	}

	/**
	 * Returns the incidentVo.
	 *
	 * @return 
	 *		the incidentVo to return
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * Sets the incidentVo.
	 *
	 * @param incidentVo 
	 *			the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
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
	 * Returns the userType.
	 *
	 * @return 
	 *		the userType to return
	 */
	public RestrictedIncidentUserTypeEnum getUserType() {
		return userType;
	}

	/**
	 * Sets the userType.
	 *
	 * @param userType 
	 *			the userType to set
	 */
	public void setUserType(RestrictedIncidentUserTypeEnum userType) {
		this.userType = userType;
	}

	/**
	 * Returns the accessGrantedBy.
	 *
	 * @return 
	 *		the accessGrantedBy to return
	 */
	public String getAccessGrantedBy() {
		return accessGrantedBy;
	}

	/**
	 * Sets the accessGrantedBy.
	 *
	 * @param accessGrantedBy 
	 *			the accessGrantedBy to set
	 */
	public void setAccessGrantedBy(String accessGrantedBy) {
		this.accessGrantedBy = accessGrantedBy;
	}

	/**
	 * Returns the accessEndDate.
	 *
	 * @return 
	 *		the accessEndDate to return
	 */
	public Date getAccessEndDate() {
		return accessEndDate;
	}

	/**
	 * Sets the accessEndDate.
	 *
	 * @param accessEndDate 
	 *			the accessEndDate to set
	 */
	public void setAccessEndDate(Date accessEndDate) {
		this.accessEndDate = accessEndDate;
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

	public Date getDefaultCheckinDate() {
		return defaultCheckinDate;
	}

	public void setDefaultCheckinDate(Date defaultCheckinDate) {
		this.defaultCheckinDate = defaultCheckinDate;
	}

	public String getDefaultCheckinType() {
		return defaultCheckinType;
	}

	public void setDefaultCheckinType(String defaultCheckinType) {
		this.defaultCheckinType = defaultCheckinType;
	}



}
