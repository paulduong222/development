package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossImportProcessResourceError;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessResourceErrorImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.ShortUtil;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessResourceErrorVo extends AbstractVo {
	private Long rossXmlFileDataId;
	private String conflictCode;
	private String conflictDescription;
	private Object newValue;
	private String newValueType;
	private Boolean excludeFromImport=false;

	public enum ConflictCodeValueObjectType {
		UNKNOWN_RES_UNIT_CODE(OrganizationVo.class,0)
		,UNKNOWN_RES_ITEM_CODE(KindVo.class,1)
		,UNKNOWN_RES_AGENCY_CODE(AgencyVo.class,2)
		,UNKNOWN_RES_JETPORT_CODE(JetPortVo.class,3)
		,UNKNOWN_PERSON_DESIGNATION(Boolean.class,4)
		;
		
		Class targetObjectType=null;
		int idx=-1;
		
		ConflictCodeValueObjectType(Class targetType,int index){
			targetObjectType=targetType;
			idx=index;
		}
		
		public static int getIndex(String name){
			for(ConflictCodeValueObjectType type : ConflictCodeValueObjectType.values()){
				if(type.name().equals(name))
					return type.idx;
			}
			return -1;
		}

		public static String getTargetType(String name){
			for(ConflictCodeValueObjectType type : ConflictCodeValueObjectType.values()){
				if(type.name().equals(name))
					return type.targetObjectType.getSimpleName();
			}
			return "UNKNOWN";
		}
	
	}
	
	public RossImportProcessResourceErrorVo(){
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessResourceErrorVo getInstance(RossImportProcessResourceError entity, Boolean cascadable) throws Exception {
		RossImportProcessResourceErrorVo vo = new RossImportProcessResourceErrorVo();

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setRossXmlFileDataId(entity.getRossXmlFileDataId());
			vo.setConflictDescription(entity.getErrorDescription());
			vo.setNewValue(entity.getNewValue());
			vo.setNewValueType(entity.getNewValueType());
			vo.setExcludeFromImport(ShortUtil.toBoolean(entity.getExcludeFromImport()));
			if(null != entity.getRossResError()){
				vo.setConflictCode(entity.getRossResError().getCode());
			}
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcessResourceErrorVo> getInstances(Collection<RossImportProcessResourceError> entities, Boolean cascadable) throws Exception {
		Collection<RossImportProcessResourceErrorVo> vos = new ArrayList<RossImportProcessResourceErrorVo>();

		for(RossImportProcessResourceError entity : entities){
			vos.add(getInstance(entity,cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessResourceError toEntity(RossImportProcessResourceError entity, RossImportProcessResourceErrorVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new RossImportProcessResourceErrorImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setRossXmlFileDataId(vo.getRossXmlFileDataId());
			entity.setErrorDescription(vo.getConflictDescription());

			if(null != vo.getNewValue()){
				int targetIdx=ConflictCodeValueObjectType.getIndex(vo.getConflictCode());
				String targetType=ConflictCodeValueObjectType.getTargetType(vo.getConflictCode());
				
				entity.setNewValueType(targetType);
				
				switch(targetIdx)
				{
					case 0: // res unit code , OrganizationVo
						OrganizationVo orgVo = (OrganizationVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(orgVo.getId()));
						break;
					case 1: // res item code , KindVo
						KindVo kindVo = (KindVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(kindVo.getId()));
						break;
					case 2: // res agency code, AgencyVo
						AgencyVo agencyVo = (AgencyVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(agencyVo.getId()));
						break;
					case 3: // res jetport code, JetPortVo
						JetPortVo jetportVo = (JetPortVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(jetportVo.getId()));
						break;
					case 4: // res person designation
						Boolean val = (Boolean)vo.getNewValue();
						entity.setNewValue(String.valueOf(val));
						break;
				}
			}
			
			entity.setExcludeFromImport(ShortUtil.toShort(vo.getExcludeFromImport()));
			
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcessResourceError> toEntities(Collection<RossImportProcessResourceErrorVo> vos, Boolean cascadable, Persistable... persistables) throws Exception {
		Collection<RossImportProcessResourceError> entities = new ArrayList<RossImportProcessResourceError>();
		
		for(RossImportProcessResourceErrorVo vo : vos){
			entities.add(toEntity(null,vo,cascadable,persistables));
		}
		
		return entities;
	}

	/**
	 * @return the rossXmlFileDataId
	 */
	public Long getRossXmlFileDataId() {
		return rossXmlFileDataId;
	}

	/**
	 * @param rossXmlFileDataId the rossXmlFileDataId to set
	 */
	public void setRossXmlFileDataId(Long rossXmlFileDataId) {
		this.rossXmlFileDataId = rossXmlFileDataId;
	}

	/**
	 * @return the conflictDescription
	 */
	public String getConflictDescription() {
		return conflictDescription;
	}

	/**
	 * @param conflictDescription the conflictDescription to set
	 */
	public void setConflictDescription(String conflictDescription) {
		this.conflictDescription = conflictDescription;
	}

	/**
	 * @return the newValue
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the newValueType
	 */
	public String getNewValueType() {
		return newValueType;
	}

	/**
	 * @param newValueType the newValueType to set
	 */
	public void setNewValueType(String newValueType) {
		this.newValueType = newValueType;
	}

	/**
	 * @return the excludeFromImport
	 */
	public Boolean getExcludeFromImport() {
		return excludeFromImport;
	}

	/**
	 * @param excludeFromImport the excludeFromImport to set
	 */
	public void setExcludeFromImport(Boolean excludeFromImport) {
		this.excludeFromImport = excludeFromImport;
	}

	/**
	 * @return the conflictCode
	 */
	public String getConflictCode() {
		return conflictCode;
	}

	/**
	 * @param conflictCode the conflictCode to set
	 */
	public void setConflictCode(String conflictCode) {
		this.conflictCode = conflictCode;
	}
}
