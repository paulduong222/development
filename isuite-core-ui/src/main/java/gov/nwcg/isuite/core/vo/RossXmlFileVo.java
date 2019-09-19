package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class RossXmlFileVo extends AbstractVo {
	private String fileName;
	private String incidentName;
	private String incidentNumber;
	private String incidentEventType;
	private Date incidentStartDate;
	private String importStatus;
	private Collection<RossXmlFileDataVo> rossXmlFileDataVos = new ArrayList<RossXmlFileDataVo>();
	private RossImportProcessVo rossImportProcessVo;
	private String rossIncidentId;
	private Date importedDate;
	private String unitCode;
	
	public RossXmlFileVo(){
		
	}
	
	public static String extractUnitCode(String val){
		String rtn="";

		if(StringUtility.hasValue(val)){
			String part1=StringUtility.getTokenValue(val, "-", 1);
			String part2=StringUtility.getTokenValue(val, "-", 2);
			rtn=part1+"-"+part2;
		}
		
		return rtn;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossXmlFileVo getInstance(RossXmlFile entity, Boolean cascadable) throws Exception {
		RossXmlFileVo vo = new RossXmlFileVo();

		if(null == entity)
			throw new Exception("Unable to create RossXmlFileVo from null RossXmlFile entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setFileName(entity.getFileName());
			vo.setIncidentName(entity.getIncidentName());
			vo.setIncidentNumber(entity.getIncidentNumber());
			vo.setIncidentEventType(entity.getIncidentEventType());
			vo.setImportStatus(entity.getImportStatus());
			vo.setIncidentStartDate(entity.getIncidentStartDate());
			vo.setRossIncidentId(entity.getRossIncId());
			vo.setImportedDate(entity.getImportedDate());
			vo.setUnitCode(entity.getUnitCode());
			
			if(null != entity.getRossXmlFileDatas()){
				vo.setRossXmlFileDataVos(RossXmlFileDataVo.getInstances(entity.getRossXmlFileDatas(), true));
			}
			
			if(null != entity.getRossImportProcess()){
				vo.setRossImportProcessVo(RossImportProcessVo.getInstance(entity.getRossImportProcess(), true));
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
	public static Collection<RossXmlFileVo> getInstances(Collection<RossXmlFile> entities, Boolean cascadable) throws Exception {
		Collection<RossXmlFileVo> vos = new ArrayList<RossXmlFileVo>();
		
		for(RossXmlFile entity : entities){
			vos.add(RossXmlFileVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossXmlFile toEntity(RossXmlFile entity,RossXmlFileVo vo, Boolean cascadable) throws Exception {
		if(null == entity)
			entity=new RossXmlFileImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			entity.setFileName(vo.getFileName());
			entity.setIncidentName(vo.getIncidentName());
			entity.setIncidentNumber(vo.getIncidentNumber());
			entity.setIncidentEventType(vo.getIncidentEventType());
			entity.setImportStatus(vo.getImportStatus());
			entity.setIncidentStartDate(vo.getIncidentStartDate());
			entity.setImportedDate(vo.getImportedDate());
			entity.setUnitCode(vo.getUnitCode());
			
			if(null != vo.getRossXmlFileDataVos()){
				entity.setRossXmlFileDatas(RossXmlFileDataVo.toEntities(vo.getRossXmlFileDataVos(), true, entity));
			}
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);

		}

		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossXmlFile> toEntities(Collection<RossXmlFileVo> vos, Boolean cascadable) throws Exception {
		Collection<RossXmlFile> entities = new ArrayList<RossXmlFile>();
		
		for(RossXmlFileVo vo : vos){
			entities.add(RossXmlFileVo.toEntity(null,vo,cascadable));
		}
		
		return entities;
	}

	/**
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RossXmlFile entity) throws ValidationException {
    	Validator.validateStringField("fileName", entity.getFileName(), 60, false);
    	Validator.validateStringField("importStatus", entity.getImportStatus(), 20, false);
		
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	/**
	 * @return the rossXmlFileDataVos
	 */
	public Collection<RossXmlFileDataVo> getRossXmlFileDataVos() {
		return rossXmlFileDataVos;
	}

	/**
	 * @param rossXmlFileDataVos the rossXmlFileDataVos to set
	 */
	public void setRossXmlFileDataVos(
			Collection<RossXmlFileDataVo> rossXmlFileDataVos) {
		this.rossXmlFileDataVos = rossXmlFileDataVos;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @return the incidentEventType
	 */
	public String getIncidentEventType() {
		return incidentEventType;
	}

	/**
	 * @param incidentEventType the incidentEventType to set
	 */
	public void setIncidentEventType(String incidentEventType) {
		this.incidentEventType = incidentEventType;
	}

	/**
	 * @return the rossImportProcessVo
	 */
	public RossImportProcessVo getRossImportProcessVo() {
		return rossImportProcessVo;
	}

	/**
	 * @param rossImportProcessVo the rossImportProcessVo to set
	 */
	public void setRossImportProcessVo(RossImportProcessVo rossImportProcessVo) {
		this.rossImportProcessVo = rossImportProcessVo;
	}

	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}

	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the importedDate
	 */
	public Date getImportedDate() {
		return importedDate;
	}

	/**
	 * @param importedDate the importedDate to set
	 */
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	
}
