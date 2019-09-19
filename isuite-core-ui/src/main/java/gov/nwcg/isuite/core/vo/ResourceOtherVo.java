package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceOtherImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ResourceOtherVo extends AbstractVo {
	
	private KindVo kindVo;
	private IncidentAccountCodeVo incidentAccountCodeVo;
	private AgencyVo agencyVo;
	private String requestNumber;
	private String costDescription;
	private Date actualReleaseDate;
	private String actualReleaseTime;
	private Collection<CostGroupVo> costGroupVos = new ArrayList<CostGroupVo>();
	private Collection<IncidentResourceOtherVo> incidentResourceOtherVos = new ArrayList<IncidentResourceOtherVo>();
	
	public ResourceOtherVo(){
		
	}

	/**
	 * Returns a ResourceOtherVo from a ResourceOther entity.
	 * 
	 * @param entity
	 * 			the source ResourceOther entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of ResourceOtherVo
	 * @throws Exception
	 */
	public static ResourceOtherVo getInstance(ResourceOther entity,boolean cascadable) throws Exception{
		ResourceOtherVo vo = new ResourceOtherVo();

		if(null == entity)
			throw new Exception("Unable to create ResourceOtherVo from null ResourceOther entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if (null != entity.getKind()) {
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
			if (null != entity.getIncidentAccountCode()) {
				vo.setIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(entity.getIncidentAccountCode(), true));
			}
			if (null != entity.getAgency()) {
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}
			vo.setRequestNumber(entity.getRequestNumber());
			vo.setActualReleaseDate(entity.getActualReleaseDate());
			
			if(DateUtil.hasValue(vo.getActualReleaseDate())){
				String time=DateUtil.toMilitaryTime(vo.getActualReleaseDate());
				vo.setActualReleaseTime(time);
			}
			
			vo.setCostDescription(entity.getCostDescription());
			
			if (null != entity.getIncidentResourceOthers()) {
				vo.setIncidentResourceOtherVos(IncidentResourceOtherVo.getInstances(entity.getIncidentResourceOthers(), false));
				
				
			}
			
		}

		return vo;
	}

	/**
	 * Returns a collection of ResourceOtherVos from a collection of ResourceOther entities.
	 * 
	 * @param entities
	 * 			the source collection of ResourceOther entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of ResourceOther vos
	 * @throws Exception
	 */
	public static Collection<ResourceOtherVo> getInstances(Collection<ResourceOther> entities, boolean cascadable) throws Exception {
		Collection<ResourceOtherVo> vos = new ArrayList<ResourceOtherVo>();

		for(ResourceOther entity : entities){
			vos.add(ResourceOtherVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a ResourceOther entity from a ResourceOther vo.
	 * 
	 * @param vo
	 * 			the source ResourceOther vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of ResourceOther entity
	 * @throws Exception
	 */
	public static ResourceOther toEntity(ResourceOtherVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		ResourceOther entity = new ResourceOtherImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if (null != vo.getKindVo()) {
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			}
			
			if (null != vo.getIncidentAccountCodeVo()) {
				entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(), false));
			}
			
			if (null != vo.getAgencyVo()) {
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}
			
			if(null != vo.getCostDescription()) {
				entity.setCostDescription(StringUtility.toUpper(vo.getCostDescription()));
			}
			
			entity.setRequestNumber(vo.getRequestNumber());
			entity.setActualReleaseDate(vo.getActualReleaseDate());
			
			IncidentResourceOther incidentResourceOtherEntity=(IncidentResourceOther)getPersistableObject(persistables,IncidentResourceOtherImpl.class);
			if (null != incidentResourceOtherEntity) {
				entity.getIncidentResourceOthers().add(incidentResourceOtherEntity);
			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of ResourceOther entities from a collection of ResourceOther vos.
	 * 
	 * @param vos
	 * 			the source collection of ResourceOther vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of ResourceOther entities
	 * @throws Exception
	 */
	public static Collection<ResourceOther> toEntityList(Collection<ResourceOtherVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<ResourceOther> entities = new ArrayList<ResourceOther>();

		for(ResourceOtherVo vo : vos){
			entities.add(ResourceOtherVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the ResourceOther field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source ResourceOther entity
	 * @throws ValidationException
	 */
	private static void validateEntity(ResourceOther entity) throws ValidationException {
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param incidentAccountCodeVo the incidentAccountCodeVo to set
	 */
	public void setIncidentAccountCodeVo(IncidentAccountCodeVo incidentAccountCodeVo) {
		this.incidentAccountCodeVo = incidentAccountCodeVo;
	}

	/**
	 * @return the incidentAccountCodeVo
	 */
	public IncidentAccountCodeVo getIncidentAccountCodeVo() {
		return incidentAccountCodeVo;
	}

	/**
	 * @param agencyVo the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * @return the agencyVo
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}

	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	/**
	 * @param costGroupVos the costGroupVos to set
	 */
	public void setCostGroupVos(Collection<CostGroupVo> costGroupVos) {
		this.costGroupVos = costGroupVos;
	}

	/**
	 * @return the costGroupVos
	 */
	public Collection<CostGroupVo> getCostGroupVos() {
		return costGroupVos;
	}

	/**
	 * @param incidentResourceOtherVos the incidentResourceOtherVos to set
	 */
	public void setIncidentResourceOtherVos(Collection<IncidentResourceOtherVo> incidentResourceOtherVos) {
		this.incidentResourceOtherVos = incidentResourceOtherVos;
	}

	/**
	 * @return the incidentResourceOtherVos
	 */
	public Collection<IncidentResourceOtherVo> getIncidentResourceOtherVos() {
		return incidentResourceOtherVos;
	}

	/**
	 * @return the costDescription
	 */
	public String getCostDescription() {
		return costDescription;
	}

	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}

	/**
	 * @return the actualReleaseTime
	 */
	public String getActualReleaseTime() {
		return actualReleaseTime;
	}

	/**
	 * @param actualReleaseTime the actualReleaseTime to set
	 */
	public void setActualReleaseTime(String actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

}
