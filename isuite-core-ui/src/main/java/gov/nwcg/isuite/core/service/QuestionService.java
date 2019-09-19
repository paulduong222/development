package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.QuestionFilter;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;


public interface QuestionService extends TransactionService{

	/**
	 * Returns a collection of questionVo's based on the
	 * filter criteria.
	 * 
	 * @param filter
	 * 			the filter criteria 
	 * @return
	 * 		collection of questionVo's
	 * @throws ServiceException
	 */
	public Collection<QuestionVo> getByFilter(QuestionFilter filter) throws ServiceException;
	
	/**
	 * Returns the question vo matching the id supplied.
	 * 
	 * @param id
	 * 			the id to search for
	 * @return
	 * 		question vo
	 * @throws ServiceException
	 */
	public QuestionVo getById(Long id) throws ServiceException;
	
	/**
	 * Saves a question.
	 * 
	 * @param vo
	 * 		the question vo to save
	 * @throws ServiceException
	 */
	public void save(QuestionVo vo) throws ServiceException;
	
	/**
	 * Deletes a question.
	 * 
	 * @param vo
	 * 		the question vo to delete
	 * @throws ServiceException
	 */
	public void delete(QuestionVo vo) throws ServiceException;
	
	/**
	 * Saves a collection of questions.
	 * 
	 * @param vos
	 * 		collection of question vos to save
	 * @throws ServiceException
	 */
	public void saveAll(Collection<QuestionVo> vos) throws ServiceException;
	
}
