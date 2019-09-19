package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class QuestionVo extends AbstractVo implements PersistableVo {
	private QuestionTypeEnum questionType;
	private String question;
	private Boolean standard = false;

	public QuestionVo(){
		   
	}

	/**
	 * Returns a QuestionVo instance from a Question entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of QuestionVo
	 * @throws Exception
	 */
	public static QuestionVo getInstance(Question entity,boolean cascadable) throws Exception {
		QuestionVo vo = new QuestionVo();

		if(null == entity)
			throw new Exception("Unable to create QuestionVo from null Question entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setQuestion(entity.getQuestion());
			vo.setQuestionType(entity.getQuestionType());
			vo.setStandard(entity.isStandard());
		}

		return vo;
	}

	/**
	 * Returns a Question entity from an QuestionVo.
	 * 
	 * @param vo
	 * 			the source QuestionVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			Question entity
	 * @throws Exception
	 */
	public static Question toEntity(QuestionVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		Question entity = new QuestionImpl();

		entity.setId(vo.getId());

		if(cascadable){

			
			entity.setQuestion(vo.getQuestion());
			if(!vo.getQuestion().endsWith("?")){
				entity.setQuestion(vo.getQuestion()+"?");
			}
			entity.setQuestionType(vo.getQuestionType());
			entity.setStandard(vo.getStandard());

			IncidentQuestion incidentQuestion = (IncidentQuestion)getPersistableObject(persistables, IncidentQuestionImpl.class);
			if(null != incidentQuestion)
				entity.getIncidentQuestions().add(incidentQuestion);
			
			IncidentGroupQuestion incidentGroupQuestion = (IncidentGroupQuestion)getPersistableObject(persistables, IncidentGroupQuestionImpl.class);
			if(null != incidentGroupQuestion)
				entity.getIncidentGroupQuestions().add(incidentGroupQuestion);
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

    /**
     * Returns a collection of Question entities from a collection of Question vos.
     * 
     * @param vo
     * 			the source collection of Question vos
     * @param cascadable
     * 			flag indicating whether the entity instances should created as a cascadable entities
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 			collection of Question entities
     * @throws Exception
     */
	public static Collection<Question> toEntityList(Collection<QuestionVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		Collection<Question> entities = new ArrayList<Question>();
		
		for(QuestionVo vo : vos){
			entities.add(QuestionVo.toEntity(vo,cascadable,persistables));
		}
		
		return entities;
	}

	/**
	 * Perform some validation on the Question field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source Question entity
	 * @throws ValidationException
	 */
	private static void validateEntity(Question entity) throws ValidationException {
    	Validator.validateStringField("questionType", entity.getQuestionType().toString(), 15, true);
    	Validator.validateStringField("question", entity.getQuestion(), 65, true);
    	Validator.validateBooleanField("standard", entity.isStandard(), true);
	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the questionType.
	 *
	 * @return 
	 *		the questionType to return
	 */
	public QuestionTypeEnum getQuestionType() {
		return questionType;
	}

	/**
	 * Sets the questionType.
	 *
	 * @param questionType 
	 *			the questionType to set
	 */
	public void setQuestionType(QuestionTypeEnum questionType) {
		this.questionType = questionType;
	}

	/**
	 * Returns the question.
	 *
	 * @return 
	 *		the question to return
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Sets the question.
	 *
	 * @param question 
	 *			the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Returns the standard.
	 *
	 * @return 
	 *		the standard to return
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * Sets the standard.
	 *
	 * @param standard 
	 *			the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}
	
	   
}
