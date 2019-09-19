package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentQuestionVo extends AbstractVo  {
	private QuestionVo questionVo;
	private IncidentVo incidentVo;
	private Integer position;
	private Boolean visible = false;

	// adding as a convenience for the flex grid
	private WorkPeriodQuestionValueVo workPeriodQuestionValueVo;

	public IncidentQuestionVo(){

	}

	/**
	 * @param questionType
	 * @param entities
	 * @return
	 */
	public static Integer getNextPosition(QuestionTypeEnum questionType,Collection<IncidentQuestion> entities){
		int i=0;

		/*
		 * Loop through entities and get highest position
		 * based on question type
		 */
		for(IncidentQuestion entity : entities){
			if(entity.getQuestion().getQuestionType()==questionType){
				if(entity.getPosition().intValue() > i)
					i=entity.getPosition().intValue();
			}
		}
		
		return new Integer(i+1);
	}
	
	/**
	 * Returns a IncidentQuestionVo instance from a IncidentQuestion entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of IncidentQuestionVo
	 * @throws Exception
	 */
	public static IncidentQuestionVo getInstance(IncidentQuestion entity,boolean cascadable) throws Exception {
		IncidentQuestionVo vo = new IncidentQuestionVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentQuestionVo from null IncidentQuestion entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
		   vo.setQuestionVo(QuestionVo.getInstance(entity.getQuestion(), true));
		   if(null != entity.getIncident()){
		      IncidentVo incidentVo = new IncidentVo();
		      incidentVo.setId(entity.getIncident().getId());
		      vo.setIncidentVo(incidentVo);
		   }
		   
		   vo.setPosition(entity.getPosition());
		   vo.setVisible(entity.isVisible());
		}

		return vo;
	}

	/**
	 * Returns a Collection of IncidentQuestionVo instances from a Collection of IncidentQuestion entities.
	 * 
	 * @param entities
	 *          the source entities
	 * @param cascadable
	 *          flag indicating whether the instances should created as a cascadable Collection of vo's
	 * @return
	 *       Collection of IncidentQuestionVo objects
	 * @throws Exception
	 */
	public static Collection<IncidentQuestionVo> getInstances(Collection<IncidentQuestion> entities, boolean cascadable) throws Exception {
		Collection<IncidentQuestionVo> vos = new ArrayList<IncidentQuestionVo>();

		for(IncidentQuestion entity : entities){
			vos.add(IncidentQuestionVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a IncidentQuestion entity from an IncidentQuestionVo.
	 * 
	 * @param vo
	 * 			the source IncidentQuestionVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			IncidentQuestion entity
	 * @throws Exception
	 */
	public static IncidentQuestion toEntity(IncidentQuestion entity,IncidentQuestionVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		if(null==entity)
			entity= new IncidentQuestionImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setQuestion(QuestionVo.toEntity(vo.getQuestionVo(), true,entity));

			Incident incident = (Incident)getPersistableObject(persistables,IncidentImpl.class);
			if(null != incident){
				entity.setIncident(incident);
			}else{
				if (null != vo.getIncidentVo()) {
					incident = new IncidentImpl();
					incident.setId(vo.getIncidentVo().getId());
					entity.setIncident(incident);
				} 
			}
			
			entity.setPosition(vo.getPosition());
			entity.setVisible(vo.getVisible());

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

   /**
    * Returns a collection of IncidentQuestion entities from a collection of IncidentQuestion vos.
    * 
    * @param vos
    *          the source collection of IncidentQuestion vos
    * @param cascadable
    *          flag indicating whether the entity instances should created as a cascadable entities
    * @return
    *          collection of IncidentQuestion entities
    * @throws Exception
    */
   public static Collection<IncidentQuestion> toEntityList(Collection<IncidentQuestionVo> vos,boolean cascadable) throws Exception {
      Collection<IncidentQuestion> entities = new ArrayList<IncidentQuestion>();
      
      for(IncidentQuestionVo vo : vos){
         entities.add(IncidentQuestionVo.toEntity(null,vo, cascadable));
      }
      
      return entities;
   }
   
	/**
	 * Perform some validation on the IncidentQuestion field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentQuestion entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentQuestion entity) throws ValidationException {
		if(null == entity.getQuestion())
			throw new ValidationException("IncidentQuestion.question cannot be null");
		//Validator.validateLongField("questionId",entity.getQuestion().getId(), false);
		
		if ((null != entity.getIncident()) && (entity.getIncident().getId().compareTo(0L) > 0)) {
			Validator.validateLongField("incidentId",entity.getIncident().getId(),true);
		} 
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
	 * Convenience method to set incident entity.
	 * 
	 * @param entity
	 * 			the incident entity to set
	 * @throws Exception
	 */
	public void setIncident(Incident entity) throws Exception {
		if(null!=entity){
			this.setIncidentVo(IncidentVo.getInstance(entity, false));
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

}
