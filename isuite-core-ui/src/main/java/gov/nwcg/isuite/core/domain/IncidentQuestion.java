package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Auditable;
import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentQuestion extends Persistable,Auditable {

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
	 * Sets the incident.
	 * 
	 * @param incident 
	 * 		the incident to set
	 */
	public void setIncident(Incident incident);

	/**
	 * Returns the incident.
	 * 
	 * @return 
	 * 		the incident to return
	 */
	public Incident getIncident();

	/**
	 * Sets the incidentId.
	 * 
	 * @param incidentId 
	 * 		the incident id to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * Returns the incident id.
	 * 
	 * @return 
	 * 		the incident id to return
	 */
	public Long getIncidentId();


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