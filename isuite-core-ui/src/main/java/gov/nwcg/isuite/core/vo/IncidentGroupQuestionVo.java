package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQuestionImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentGroupQuestionVo extends AbstractVo implements PersistableVo {
	private QuestionVo questionVo;
	private Long incidentGroupId;
	private Integer position;
	private Boolean visible = false;

	// adding as a convenience for the flex grid
	private WorkPeriodQuestionValueVo workPeriodQuestionValueVo;

	public IncidentGroupQuestionVo(){

	}

	/**
	 * @param questionType
	 * @param entities
	 * @return
	 */
	public static Integer getNextPosition(QuestionTypeEnum questionType,Collection<IncidentGroupQuestion> entities){
		int i=0;

		/*
		 * Loop through entities and get highest position
		 * based on question type
		 */
		for(IncidentGroupQuestion entity : entities){
			if(entity.getQuestion().getQuestionType()==questionType){
				if(entity.getPosition().intValue() > i)
					i=entity.getPosition().intValue();
			}
		}
		
		return new Integer(i+1);
	}
	
	/**
	 * Returns a IncidentGroupQuestionVo instance from a IncidentGroupQuestion entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of IncidentGroupQuestionVo
	 * @throws Exception
	 */
	public static IncidentGroupQuestionVo getInstance(IncidentGroupQuestion entity,boolean cascadable) throws Exception {
		IncidentGroupQuestionVo vo = new IncidentGroupQuestionVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentGroupQuestionVo from null IncidentGroupQuestion entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
		   vo.setQuestionVo(QuestionVo.getInstance(entity.getQuestion(), true));
		   vo.setIncidentGroupId(entity.getIncidentGroupId());
		   vo.setPosition(entity.getPosition());
		   vo.setVisible(entity.isVisible());
		}

		return vo;
	}

	/**
	 * Returns a Collection of IncidentGroupQuestionVo instances from a Collection of IncidentGroupQuestion entities.
	 * 
	 * @param entities
	 *          the source entities
	 * @param cascadable
	 *          flag indicating whether the instances should created as a cascadable Collection of vo's
	 * @return
	 *       Collection of IncidentGroupQuestionVo objects
	 * @throws Exception
	 */
	public static Collection<IncidentGroupQuestionVo> getInstances(Collection<IncidentGroupQuestion> entities, boolean cascadable) throws Exception {
		Collection<IncidentGroupQuestionVo> vos = new ArrayList<IncidentGroupQuestionVo>();

		for(IncidentGroupQuestion entity : entities){
			vos.add(IncidentGroupQuestionVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a IncidentGroupQuestion entity from an IncidentGroupQuestionVo.
	 * 
	 * @param vo
	 * 			the source IncidentGroupQuestionVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			IncidentGroupQuestion entity
	 * @throws Exception
	 */
	public static IncidentGroupQuestion toEntity(IncidentGroupQuestion entity,IncidentGroupQuestionVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		if(null==entity)
			entity= new IncidentGroupQuestionImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setQuestion(QuestionVo.toEntity(vo.getQuestionVo(), true,entity));
			entity.setPosition(vo.getPosition());
			entity.setVisible(vo.getVisible());

			IncidentGroup incidentGroupEntity = (IncidentGroup)getPersistableObject(persistables,IncidentGroupImpl.class);
			if(null != incidentGroupEntity)
				entity.setIncidentGroup(incidentGroupEntity);
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

   /**
    * Returns a collection of IncidentGroupQuestion entities from a collection of IncidentGroupQuestion vos.
    * 
    * @param vos
    *          the source collection of IncidentGroupQuestion vos
    * @param cascadable
    *          flag indicating whether the entity instances should created as a cascadable entities
    * @return
    *          collection of IncidentGroupQuestion entities
    * @throws Exception
    */
   public static Collection<IncidentGroupQuestion> toEntityList(Collection<IncidentGroupQuestionVo> vos,boolean cascadable) throws Exception {
      Collection<IncidentGroupQuestion> entities = new ArrayList<IncidentGroupQuestion>();
      
      for(IncidentGroupQuestionVo vo : vos){
         entities.add(IncidentGroupQuestionVo.toEntity(null,vo, cascadable));
      }
      
      return entities;
   }
   
	/**
	 * Perform some validation on the IncidentGroupQuestion field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentGroupQuestion entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentGroupQuestion entity) throws ValidationException {
		if(null == entity.getQuestion())
			throw new ValidationException("IncidentQuestion.question cannot be null");
		//Validator.validateLongField("questionId",entity.getQuestion().getId(), false);
		
		Validator.validateIntegerField("position", entity.getPosition(), true);
		Validator.validateBooleanField("visible", entity.isVisible() , true);
	}

	/**
	 * Returns the questionVo.
	 *
	 * @return 
	 *		the questionVo to return
	 */
	public QuestionVo getQuestionVo() {
		return questionVo;
	}

	/**
	 * Sets the questionVo.
	 *
	 * @param questionVo 
	 *			the questionVo to set
	 */
	public void setQuestionVo(QuestionVo questionVo) {
		this.questionVo = questionVo;
	}

	/**
	 * Returns the position.
	 *
	 * @return 
	 *    the position to return
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position 
	 *       the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * Returns the visible.
	 *
	 * @return 
	 *		the visible to return
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * Sets the visible.
	 *
	 * @param visible 
	 *			the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * Convenience method to set question entity.
	 * 
	 * @param entity
	 * 			the question entity to set
	 * @throws Exception
	 */
	public void setQuestion(Question entity) throws Exception {
		if(null!=entity){
			this.setQuestionVo(QuestionVo.getInstance(entity, true));
		}
	}

	/**
	 * Returns the workPeriodQuestionValueVo.
	 *
	 * @return 
	 *		the workPeriodQuestionValueVo to return
	 */
	public WorkPeriodQuestionValueVo getWorkPeriodQuestionValueVo() {
		return workPeriodQuestionValueVo;
	}

	/**
	 * Sets the workPeriodQuestionValueVo.
	 *
	 * @param workPeriodQuestionValueVo 
	 *			the workPeriodQuestionValueVo to set
	 */
	public void setWorkPeriodQuestionValueVo(
			WorkPeriodQuestionValueVo workPeriodQuestionValueVo) {
		this.workPeriodQuestionValueVo = workPeriodQuestionValueVo;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
