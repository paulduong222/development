package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.core.filter.QuestionFilter;
import gov.nwcg.isuite.core.persistence.QuestionDao;
import gov.nwcg.isuite.core.service.QuestionService;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public class QuestionServiceImpl extends BaseService implements QuestionService {
	protected QuestionDao questionDao;

	public QuestionServiceImpl(){
		
	}
	
	public void initialization(){
		questionDao = (QuestionDao)super.context.getBean("questionDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuestionService#getById(java.lang.Long)
	 */
	public QuestionVo getById(Long id) throws ServiceException {
		try{
			
			Question entity = questionDao.getById(id, QuestionImpl.class);

			QuestionVo vo = QuestionVo.getInstance(entity, true);
			
			return vo;
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuestionService#delete(gov.nwcg.isuite.core.vo.QuestionVo)
	 */
	public void delete(QuestionVo vo) throws ServiceException {
		try{
			
			Question entity = QuestionVo.toEntity(vo, false);

			questionDao.delete(entity);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuestionService#getByFilter(gov.nwcg.isuite.core.filter.QuestionFilter)
	 */
	public Collection<QuestionVo> getByFilter(QuestionFilter filter) throws ServiceException {
		try{
			return questionDao.getByFilter(filter);
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuestionService#save(gov.nwcg.isuite.core.vo.QuestionVo)
	 */
	public void save(QuestionVo vo) throws ServiceException {
		try{
			
			Question entity = QuestionVo.toEntity(vo, true);
			
			questionDao.save(entity);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuestionService#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<QuestionVo> vos) throws ServiceException {
		try{
			Collection<Question> entities = QuestionVo.toEntityList(vos,true);
			
			questionDao.saveAll(entities);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


}
