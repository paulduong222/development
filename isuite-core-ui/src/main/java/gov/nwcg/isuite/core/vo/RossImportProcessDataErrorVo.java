package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessDataErrorImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileDataImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.ShortUtil;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessDataErrorVo extends AbstractVo {
	private Long rossXmlFileDataId;
	private Long rossXmlFileId;
	private String conflictCode;
	private String conflictDescription;
	private Object newValue;
	private String newValueType;
	private Boolean excludeFromImport=false;

	private Boolean rossAssignment=false;
	private Long rossResId;
	
	private Collection<OrganizationVo> pdcs = new ArrayList<OrganizationVo>();
	
	public enum ConflictCodeValueObjectType {
		UNKNOWN_RES_UNIT_CODE(OrganizationVo.class,0)
		,UNKNOWN_RES_ITEM_CODE(KindVo.class,1)
		,UNKNOWN_RES_AGENCY_CODE(AgencyVo.class,2)
		,UNKNOWN_RES_JETPORT_CODE(JetPortVo.class,3)
		,UNKNOWN_PERSON_DESIGNATION(Boolean.class,4)
		,UNKNOWN_INC_EVENTTYPE_CODE(EventTypeVo.class,5)
		,UNKNOWN_INC_AGENCY_CODE(AgencyVo.class,6)
		,UNKNOWN_INC_UNIT_CODE(OrganizationVo.class,7)
		,UNKNOWN_INC_PDC(OrganizationVo.class,8)
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
	
	public RossImportProcessDataErrorVo(){
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessDataErrorVo getInstance(RossImportProcessDataError entity, Boolean cascadable) throws Exception {
		RossImportProcessDataErrorVo vo = new RossImportProcessDataErrorVo();

		vo.setId(entity.getId());
		
		if(cascadable){
			if(null != entity.getRossXmlFileData()){
				vo.setRossAssignment(ShortUtil.toBoolean(entity.getRossXmlFileData().getRossAssignment()));
				vo.setRossResId(entity.getRossXmlFileData().getResId());
			}
			
			vo.setRossXmlFileDataId(entity.getRossXmlFileDataId());
			vo.setRossXmlFileId(entity.getRossXmlFileId());
			vo.setConflictDescription(entity.getErrorDescription());
			vo.setNewValue(entity.getNewValue());
			vo.setNewValueType(entity.getNewValueType());
			//vo.setExcludeFromImport(entity.getExcludeFromImport());
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
	public static Collection<RossImportProcessDataErrorVo> getInstances(Collection<RossImportProcessDataError> entities, Boolean cascadable) throws Exception {
		Collection<RossImportProcessDataErrorVo> vos = new ArrayList<RossImportProcessDataErrorVo>();

		for(RossImportProcessDataError entity : entities){
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
	public static RossImportProcessDataError toEntity(RossImportProcessDataError entity, RossImportProcessDataErrorVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new RossImportProcessDataErrorImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(null != vo.getRossXmlFileDataId() && vo.getRossXmlFileDataId() > 0){
				RossXmlFileData rxfd = new RossXmlFileDataImpl();
				rxfd.setId(vo.getRossXmlFileDataId());
				entity.setRossXmlFileData(rxfd);
			}
			if(null != vo.getRossXmlFileId() && vo.getRossXmlFileId() > 0){
				RossXmlFile rxf = new RossXmlFileImpl();
				//rxf.getRossImportProcessDataErrors().add(entity);
				rxf.setId(vo.getRossXmlFileId());
				entity.setRossXmlFile(rxf);
			}
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
					case 5: // inc event type code
						EventTypeVo etvo  = (EventTypeVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(etvo.getId()));
						break;
					case 6: // inc agency code
						AgencyVo avo  = (AgencyVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(avo.getId()));
						break;
					case 7: // inc home unit
						OrganizationVo incOrgVo  = (OrganizationVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(incOrgVo.getId()));
						break;
					case 8: // inc pdc
						OrganizationVo incPdcVo  = (OrganizationVo)vo.getNewValue();
						entity.setNewValue(String.valueOf(incPdcVo.getId()));
						break;
				}
			}
			
			//entity.setExcludeFromImport(vo.getExcludeFromImport());
			
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
	public static Collection<RossImportProcessDataError> toEntities(Collection<RossImportProcessDataErrorVo> vos, Boolean cascadable, Persistable... persistables) throws Exception {
		Collection<RossImportProcessDataError> entities = new ArrayList<RossImportProcessDataError>();
		
		for(RossImportProcessDataErrorVo vo : vos){
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

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	/**
	 * @return the rossAssignment
	 */
	public Boolean getRossAssignment() {
		return rossAssignment;
	}

	/**
	 * @param rossAssignment the rossAssignment to set
	 */
	public void setRossAssignment(Boolean rossAssignment) {
		this.rossAssignment = rossAssignment;
	}

	/**
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
	}

	public Collection<OrganizationVo> getPdcs() {
		return pdcs;
	}

	public void setPdcs(Collection<OrganizationVo> pdcs) {
		this.pdcs = pdcs;
	}
}
