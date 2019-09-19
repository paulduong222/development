package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Auditable;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

public interface Question extends Persistable,Auditable {

   /**
    * Sets the questionType.
    * 
    * @param questionType 
    * 		the question type to set
    */
   public void setQuestionType(QuestionTypeEnum type);
   
   /**
    * Returns the question type.
    * 
    * @return 
    * 		the question type to return
    */
   public QuestionTypeEnum getQuestionType();

   /**
    * Sets the question.
    * 
    * @param question
    * 		the question to set
    */
   public void setQuestion(String question);
   
   /**
    * Returns the question.
    * 
    * @return 
    * 		the question to return
    */
   public String getQuestion();

   /**
    * Returns whether or not the question is a standard question.
    * 
    * @return 
    * 		the question standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the question is a standard question.
    * 
    * @param isStandard
    * 			the question standard status
    */
   public void setStandard(Boolean isStandard);

	/**
	 * Returns the incidentQuestions.
	 *
	 * @return 
	 *		the incidentQuestions to return
	 */
	public Collection<IncidentQuestion> getIncidentQuestions();


	/**
	 * Sets the incidentQuestions.
	 *
	 * @param incidentQuestions 
	 *			the incidentQuestions to set
	 */
	public void setIncidentQuestions(Collection<IncidentQuestion> incidentQuestions);
	
	/**
	 * @return the incidentGroupQuestions
	 */
	public Collection<IncidentGroupQuestion> getIncidentGroupQuestions();

	/**
	 * @param incidentGroupQuestions the incidentGroupQuestions to set
	 */
	public void setIncidentGroupQuestions(
			Collection<IncidentGroupQuestion> incidentGroupQuestions);
	
}
