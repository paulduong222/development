package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentResourceOtherVo extends AbstractVo{

	private CostDataVo costDataVo;
	private ResourceOtherVo resourceOtherVo;
	private IncidentVo incidentVo;
	private AssignmentStatusVo assignmentStatusVo;

	public IncidentResourceOtherVo(){
		
	}

	/**
	 * Returns a IncidentResourceOtherVo from a IncidentResourceOther entity.
	 * 
	 * @param entity
	 * 			the source IncidentResourceOther entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentResourceOtherVo
	 * @throws Exception
	 */
	public static IncidentResourceOtherVo getInstance(IncidentResourceOther entity, boolean cascadable) throws Exception{
		IncidentResourceOtherVo vo = new IncidentResourceOtherVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentResourceOtherVo from null IncidentResourceOther entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if(null != entity.getResourceOther()){
				vo.setResourceOtherVo(ResourceOtherVo.getInstance(entity.getResourceOther(), true));
			}
			
			if(null != entity.getCostData()){
				vo.setCostDataVo(CostDataVo.getInstance(entity.getCostData(), true));
			}
			else {
				//This step is to ensure that cost data will be saved for resource records that were created prior to cost being implemented in the system 
				CostData cdEntity=null;
				cdEntity = new CostDataImpl();
				//cdEntity.setIncidentResourceOthers(incidentResourceOthers)(entity); // Collection?? If  One to Many
				//cdEntity.setIncidentResourceOther(entity); // One to One
				cdEntity.setGenerateCosts(true);
				vo.setCostDataVo(CostDataVo.getInstance(cdEntity, true));
			}
			
			if(null != entity.getIncident()){
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
			}
			
			if(null != entity.getAssignmentStatus()){
				vo.setAssignmentStatusVo(AssignmentStatusTypeEnum.getAssignmentVoByCode(entity.getAssignmentStatus().name()));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentResourceOtherVos from a collection of IncidentResourceOther entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentResourceOther entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentResourceOther vos
	 * @throws Exception
	 */
	public static Collection<IncidentResourceOtherVo> getInstances(Collection<IncidentResourceOther> entities, boolean cascadable) throws Exception {
		Collection<IncidentResourceOtherVo> vos = new ArrayList<IncidentResourceOtherVo>();

		for(IncidentResourceOther entity : entities){
			vos.add(IncidentResourceOtherVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentResourceOther entity from a IncidentResourceOther vo.
	 * 
	 * @param vo
	 * 			the source IncidentResourceOther vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentResourceOther entity
	 * @throws Exception
	 */
	public static IncidentResourceOther toEntity(IncidentResourceOther entity, IncidentResourceOtherVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null==entity){
			entity = new IncidentResourceOtherImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			if (null != vo.getCostDataVo()) {
				entity.setCostData(CostDataVo.toEntity(vo.getCostDataVo(), true, entity));
			}
			
			if (null != vo.getResourceOtherVo()) {
				entity.setResourceOther(ResourceOtherVo.toEntity(vo.getResourceOtherVo(), true,entity));
			}
			
			if (null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false)); // Keep cascadable false for Incident
				
			}
			
			if(null != vo.getAssignmentStatusVo()) {
				entity.setAssignmentStatus(AssignmentStatusTypeEnum.valueOf(vo.getAssignmentStatusVo().getCode()));
			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of IncidentResourceOther entities from a collection of IncidentResourceOther vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentResourceOther vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentResourceOther entities
	 * @throws Exception
	 */
	public static Collection<IncidentResourceOther> toEntityList(Collection<IncidentResourceOtherVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentResourceOther> entities = new ArrayList<IncidentResourceOther>();

		for(IncidentResourceOtherVo vo : vos){
			entities.add(IncidentResourceOtherVo.toEntity(null, vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentResourceOther field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentResourceOther entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentResourceOther entity) throws ValidationException {
	}

	/**
	 * @param resourceOtherVo the resourceOtherVo to set
	 */
	public void setResourceOtherVo(ResourceOtherVo resourceOtherVo) {
		this.resourceOtherVo = resourceOtherVo;
	}

	/**
	 * @return the resourceOtherVo
	 */
	public ResourceOtherVo getResourceOtherVo() {
		return resourceOtherVo;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	/**
	 * @return the assignmentStatusVo
	 */
	public AssignmentStatusVo getAssignmentStatusVo() {
		return assignmentStatusVo;
	}

	/**
	 * @param assignmentStatusVo the assignmentStatusVo to set
	 */
	public void setAssignmentStatusVo(AssignmentStatusVo assignmentStatusVo) {
		this.assignmentStatusVo = assignmentStatusVo;
	}

	/**
	 * @return the costDataVo
	 */
	public CostDataVo getCostDataVo() {
		return costDataVo;
	}

	/**
	 * @param costDataVo the costDataVo to set
	 */
	public void setCostDataVo(CostDataVo costDataVo) {
		this.costDataVo = costDataVo;
	}

}
