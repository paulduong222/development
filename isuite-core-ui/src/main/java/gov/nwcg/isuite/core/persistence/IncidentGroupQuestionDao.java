package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

public interface IncidentGroupQuestionDao extends TransactionSupport, CrudDao<IncidentGroupQuestion> {
   
	/**
	 * 
	 * @param question
	 * @param incidentId
	 * @param questionType
	 * @return
	 * @throws PersistenceException
	 */
	public IncidentGroupQuestion getByQuestion(String question, Long incidentGroupId, QuestionTypeEnum questionType) throws PersistenceException;

	public void createDefaultGroupQuestions(Long incidentGroupId, Long primaryIncidentId) throws PersistenceException;
	
}
