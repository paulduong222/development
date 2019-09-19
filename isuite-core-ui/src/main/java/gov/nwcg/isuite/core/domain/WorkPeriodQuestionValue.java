package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Auditable;
import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface WorkPeriodQuestionValue extends Persistable,Auditable {

   /**
    * Sets the work period.
    * 
    * @param workPeriod 
    * 		the work period to set
    */
   public void setWorkPeriod(WorkPeriod workPeriod);
   
   /**
    * Returns the work period.
    * 
    * @return 
    * 		the work period to return
    */
   public WorkPeriod getWorkPeriod();

   /**
    * Returns the work period id.
    * 
    * @return 
    * 		the work period id to return
    */
   public Long getWorkPeriodId();
   
   /**
    * Sets the work period id.
    * 
    * @param workPeriodId
    * 		the work period id to set
    */
   public void setWorkPeriodId(Long workPeriodId);
   
   /**
    * Sets the incident question.
    * 
    * @param incidentQuestion
    * 		the incident question to set
    */
   public void setIncidentQuestion(IncidentQuestion incidentQuestion);
   
   /**
    * Returns the incident question.
    * 
    * @return 
    * 		the incident question to return
    */
   public IncidentQuestion getIncidentQuestion();

   /**
    * Sets the incident question id.
    * 
    * @param incidentQuestionId
    * 		the incident question id to set
    */
   public void setIncidentQuestionId(Long incidentQuestionId);
   
   /**
    * Returns the incident question id.
    * 
    * @return 
    * 		the incident question id to return
    */
   public Long getIncidentQuestionId();
 
   /**
    * Sets the question value.
    * 
    * @param value
    * 		the question value to set
    */
   public void setQuestionValue(Boolean value);
   
   /**
    * Returns the question value.
    * 
    * @return 
    * 		the question value to return
    */
   public Boolean getQuestionValue();
   
}