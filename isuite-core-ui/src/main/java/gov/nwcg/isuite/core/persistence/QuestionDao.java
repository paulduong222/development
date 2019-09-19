package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.filter.QuestionFilter;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface QuestionDao extends TransactionSupport, CrudDao<Question> {


   /**
    * Returns collection of questions matching the filter criteria.
    * 
    * @return
    * 		collection of questions
    * @throws PersistenceException
    */
   public Collection<QuestionVo> getByFilter(QuestionFilter filter) throws PersistenceException;

   /**
    * Return the set of standard questions
    * 
    * @return
    * @throws PersistenceException
    */
   public Collection<QuestionVo> getDefaultQuestions() throws PersistenceException;
}
