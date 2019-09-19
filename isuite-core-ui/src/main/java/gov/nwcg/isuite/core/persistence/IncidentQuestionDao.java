package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.Collection;

/**
 * @author mpoll
 *
 */
public interface IncidentQuestionDao extends TransactionSupport, CrudDao<IncidentQuestion> {
   
   /**
    * Retrieve All Travel Questions (VOs) in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentQuestionVo} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentQuestionVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentQuestionVo> getAirTravelQuestions(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;
   
   /**
    * Retrieve All Non-Standard Questions for the given {@link Incident} id.
    * 
    * @param incidentId
    * @return {@link Collection} of {@link IncidentQuestion} entities.
    * @throws PersistenceException
    */
   public Collection<IncidentQuestion> getNonStandardQuestionEntities(Long incidentId, String questionType) throws PersistenceException;

   /**
    * Retrieve All Check In Questions in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentQuestionVo} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentQuestionVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentQuestionVo> getCheckInQuestions(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;
   
   public int getNextQuestionPosition(Long id, Boolean isGroup) throws PersistenceException;
   
   /**
    * Determine if this {@link Incident} has Non-Standard Travel questions associated with it.
    * 
    * @param incidentId
    * @return {@link Boolean}
    * @throws PersistenceException
    */
   public Boolean checkForNonStandardTravelQuestions(Long incidentId) throws PersistenceException;

   /**
    * Determine if this {@link Incident} has Non-Standard Checkin questions associated with it.
    * 
    * @param incidentId
    * @return {@link Boolean}
    * @throws PersistenceException
    */
   public Boolean checkForNonStandardCheckinQuestions(Long incidentId) throws PersistenceException;
   
   /**
    * 
    * @param questionId
    * @param incidentId
    * @return {@link IncidentQuestion}
    * @throws PersistenceException
    */
   public IncidentQuestion getByQuestionIdAndIncidentId(Long questionId, Long incidentId) throws PersistenceException;
   
   /**
    * 
    * @param question
    * @param incidentId
    * @param questionType
    * @return
    * @throws PersistenceException
    */
   public IncidentQuestion getByQuestion(String question, Long incidentId, QuestionTypeEnum questionType) throws PersistenceException;
}
