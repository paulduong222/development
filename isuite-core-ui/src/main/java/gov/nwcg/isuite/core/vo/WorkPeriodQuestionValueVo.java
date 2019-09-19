package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodQuestionValueImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class WorkPeriodQuestionValueVo extends AbstractVo implements PersistableVo {
	private WorkPeriodVo workPeriodVo;
	private IncidentQuestionVo incidentQuestionVo;
	private Boolean questionValue;
	private Integer readOnlyPosition;
	
	public WorkPeriodQuestionValueVo(){
		   
	}

	/**
	 * Returns a WorkPeriodQuestionValueVo instance from a WorkPeriodQuestionValue entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of WorkPeriodQuestionValueVo
	 * @throws Exception
	 */
	public static WorkPeriodQuestionValueVo getInstance(WorkPeriodQuestionValue entity,boolean cascadable) throws Exception {
		WorkPeriodQuestionValueVo vo = new WorkPeriodQuestionValueVo();

		if(null == entity)
			throw new Exception("Unable to create WorkPeriodQuestionValueVo from null WorkPeriodQuestionValue entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setWorkPeriodVo(WorkPeriodVo.getInstance(entity.getWorkPeriod(), false));
			vo.setIncidentQuestionVo(IncidentQuestionVo.getInstance(entity.getIncidentQuestion(), true));
			vo.setQuestionValue(entity.getQuestionValue());
			if(null != vo.getIncidentQuestionVo()){
				vo.setReadOnlyPosition(vo.getIncidentQuestionVo().getPosition());
			}
		}

		return vo;
	}

	public static Collection<WorkPeriodQuestionValueVo> getInstances(Collection<WorkPeriodQuestionValue> entities, Boolean cascadable) throws Exception {
		Collection<WorkPeriodQuestionValueVo> vos = new ArrayList<WorkPeriodQuestionValueVo>();
		
		for(WorkPeriodQuestionValue entity : entities){
			vos.add(getInstance(entity,true));
		}
		
		return vos;
	}
	
	/**
	 * Returns a WorkPeriodQuestionValue entity from an WorkPeriodQuestionValueVo.
	 * 
	 * @param vo
	 * 			the source WorkPeriodQuestionValueVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			WorkPeriodQuestionValue entity
	 * @throws Exception
	 */
	public static WorkPeriodQuestionValue toEntity(WorkPeriodQuestionValueVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		WorkPeriodQuestionValue entity = new WorkPeriodQuestionValueImpl();

		entity.setId(vo.getId());

		if(cascadable){
			WorkPeriod wpEntity = (WorkPeriod)AbstractVo.getPersistableObject(persistables, WorkPeriodImpl.class);
			if(null != wpEntity)
				entity.setWorkPeriod(wpEntity);
			entity.setIncidentQuestion(IncidentQuestionVo.toEntity(null,vo.getIncidentQuestionVo(), false));
			entity.setQuestionValue(vo.getQuestionValue());
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the WorkPeriodQuestionValue field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source WorkPeriodQuestionValue entity
	 * @throws ValidationException
	 */
	private static void validateEntity(WorkPeriodQuestionValue entity) throws ValidationException {
		Validator.validateBooleanField("questionValue", entity.getQuestionValue(), true);
		Validator.validateLongField("incidentQuestionId", entity.getIncidentQuestion().getId(), true);
		Validator.validateLongField("workPeriodId", entity.getWorkPeriod().getId(), true);
	}

	/**
	 * Returns the workPeriodVo.
	 *
	 * @return 
	 *		the workPeriodVo to return
	 */
	public WorkPeriodVo getWorkPeriodVo() {
		return workPeriodVo;
	}

	/**
	 * Sets the workPeriodVo.
	 *
	 * @param workPeriodVo 
	 *			the workPeriodVo to set
	 */
	public void setWorkPeriodVo(WorkPeriodVo workPeriodVo) {
		this.workPeriodVo = workPeriodVo;
	}

	/**
	 * Returns the incidentQuestionVo.
	 *
	 * @return 
	 *		the incidentQuestionVo to return
	 */
	public IncidentQuestionVo getIncidentQuestionVo() {
		return incidentQuestionVo;
	}

	/**
	 * Sets the incidentQuestionVo.
	 *
	 * @param incidentQuestionVo 
	 *			the incidentQuestionVo to set
	 */
	public void setIncidentQuestionVo(IncidentQuestionVo incidentQuestionVo) {
		this.incidentQuestionVo = incidentQuestionVo;
	}

	/**
	 * Returns the questionValue.
	 *
	 * @return 
	 *		the questionValue to return
	 */
	public Boolean getQuestionValue() {
		return questionValue;
	}

	/**
	 * Sets the questionValue.
	 *
	 * @param questionValue 
	 *			the questionValue to set
	 */
	public void setQuestionValue(Boolean questionValue) {
		this.questionValue = questionValue;
	}

	/**
	 * @return the readOnlyPosition
	 */
	public Integer getReadOnlyPosition() {
		return readOnlyPosition;
	}

	/**
	 * @param readOnlyPosition the readOnlyPosition to set
	 */
	public void setReadOnlyPosition(Integer readOnlyPosition) {
		this.readOnlyPosition = readOnlyPosition;
	}
	
	   
}
