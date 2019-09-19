package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Auditable;
import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentGroupQuestion extends Persistable,Auditable {

	/**
	 * Sets the question.
	 * 
	 * @param question 
	 * 		the question to set
	 */
	public void setQuestion(Question question);

	/**
	 * Returns the question.
	 * 
	 * @return 
	 * 		the question to return
	 */
	public Question getQuestion();

	/**
	 * Sets the questionId.
	 * 
	 * @param questionId 
	 * 		the question id to set
	 */
	public void setQuestionId(Long questionId);

	/**
	 * Returns the question id.
	 * 
	 * @return 
	 * 		the question id to return
	 */
	public Long getQuestionId();

	/**
	 * Sets the incidentGroup.
	 * 
	 * @param incidentGroup
	 * 		the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);

	/**
	 * Returns the incidentGroup.
	 * 
	 * @return 
	 * 		the incidentGroup to return
	 */
	public IncidentGroup getIncidentGroup();

	/**
	 * Sets the incidentGroupId.
	 * 
	 * @param incidentGroupId 
	 * 		the incidentGroup id to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);

	/**
	 * Returns the incidentGroup id.
	 * 
	 * @return 
	 * 		the incidentGroup id to return
	 */
	public Long getIncidentGroupId();


   /**
    * Sets the positional order for the question.
    * 
    * @param position 
    *       the question position to set
    */
   public void setPosition(Integer position);
   
   /**
    * Returns the positional order of the question.
    * 
    * @return 
    *    the question position to return
    */
   public Integer getPosition();
   
	/**
	 * Returns whether or not the question is visible.
	 * 
	 * @return 
	 * 		the question visible status
	 */
	public Boolean isVisible();

	/**
	 * Sets whether or not the question is visible.
	 * 
	 * @param isVisible
	 * 			the question visible status
	 */
	public void setVisible(Boolean isVisible);

}