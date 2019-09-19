package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ResInvImportConflict;
import gov.nwcg.isuite.core.domain.impl.ResInvImportConflictImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;

public class ResInvImportConflictVo extends AbstractVo implements PersistableVo {
	private String description;
	private Boolean isResolved;
	private String rossResId;
	private String resourceName;
	private String firstName;
	private String lastName;
	private String gaccOrgUnitCodeName;
	private String gaccDispUnitCodeName;
	private String resProvUnitCodeName;

	public ResInvImportConflictVo() {
	}

	/**
	 * Returns a ResInvImportConflictVo instance from an ResInvImportConflict entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of ResInvImportConflictVo
	 * @throws Exception
	 */
	public static ResInvImportConflictVo getInstance(ResInvImportConflict entity,boolean cascadable) throws Exception {
		ResInvImportConflictVo vo = new ResInvImportConflictVo();

		if(null == entity)
			throw new Exception("Unable to create ResInvImportConflictVo from null ResInvImportConflict entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDescription(entity.getDescription());
			vo.setFirstName(entity.getFirstName());
			vo.setLastName(entity.getLastName());
			vo.setResourceName(entity.getResourceName());
			vo.setGaccDispUnitCodeName(entity.getGaccDispUnitCodeName());
			vo.setGaccOrgUnitCodeName(entity.getGaccOrgUnitCodeName());
			vo.setIsResolved(StringBooleanEnum.toBooleanValue(entity.getIsResolved()));
			vo.setResProvUnitCodeName(entity.getResProvUnitCodeName());
			vo.setRossResId(entity.getRossResId());
		}

		return vo;
	}

	public static Collection<ResInvImportConflictVo> getInstances(Collection<ResInvImportConflict> entities, boolean cascadable) throws Exception {
		Collection<ResInvImportConflictVo> vos = new ArrayList<ResInvImportConflictVo>();

		for(ResInvImportConflict entity : entities){
			vos.add(ResInvImportConflictVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a ResInvImportConflict entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of ResInvImportConflict entity
	 * @throws Exception
	 */
	public static ResInvImportConflict toEntity(ResInvImportConflict entity, ResInvImportConflictVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity){
			entity=new ResInvImportConflictImpl();
			entity.setId(vo.getId());
		}
		
		
		if(cascadable){
			entity.setDescription(vo.getDescription());
			entity.setFirstName(vo.getFirstName());
			entity.setLastName(vo.getLastName());
			entity.setResourceName(vo.getResourceName());
			entity.setGaccDispUnitCodeName(vo.getGaccDispUnitCodeName());
			entity.setGaccOrgUnitCodeName(vo.getGaccOrgUnitCodeName());
			entity.setIsResolved(StringBooleanEnum.toEnumValue(vo.getIsResolved()));
			entity.setResProvUnitCodeName(vo.getResProvUnitCodeName());
			entity.setRossResId(vo.getRossResId());
		}

		return entity;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isResolved
	 */
	public Boolean getIsResolved() {
		return isResolved;
	}

	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}

	/**
	 * @return the rossResId
	 */
	public String getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(String rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gaccOrgUnitCodeName
	 */
	public String getGaccOrgUnitCodeName() {
		return gaccOrgUnitCodeName;
	}

	/**
	 * @param gaccOrgUnitCodeName the gaccOrgUnitCodeName to set
	 */
	public void setGaccOrgUnitCodeName(String gaccOrgUnitCodeName) {
		this.gaccOrgUnitCodeName = gaccOrgUnitCodeName;
	}

	/**
	 * @return the gaccDispUnitCodeName
	 */
	public String getGaccDispUnitCodeName() {
		return gaccDispUnitCodeName;
	}

	/**
	 * @param gaccDispUnitCodeName the gaccDispUnitCodeName to set
	 */
	public void setGaccDispUnitCodeName(String gaccDispUnitCodeName) {
		this.gaccDispUnitCodeName = gaccDispUnitCodeName;
	}

	/**
	 * @return the resProvUnitCodeName
	 */
	public String getResProvUnitCodeName() {
		return resProvUnitCodeName;
	}

	/**
	 * @param resProvUnitCodeName the resProvUnitCodeName to set
	 */
	public void setResProvUnitCodeName(String resProvUnitCodeName) {
		this.resProvUnitCodeName = resProvUnitCodeName;
	}


}
