package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.core.filter.QuestionFilter;
import gov.nwcg.isuite.core.filter.impl.QuestionFilterImpl;
import gov.nwcg.isuite.core.persistence.QuestionDao;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

public class QuestionDaoHibernate extends TransactionSupportImpl implements QuestionDao {
	private final CrudDao<Question> crudDao;

	public QuestionDaoHibernate(final CrudDao<Question> crudDao) {

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(Question persistable) throws PersistenceException {
		crudDao.save(persistable);
	}   

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Question> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Question persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}   

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.QuestionDao#getByFilter(gov.nwcg.isuite.core.filter.QuestionFilter)
	 */
	@SuppressWarnings("unchecked")
   public Collection<QuestionVo> getByFilter(QuestionFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(QuestionImpl.class);
		
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("questionType"), "questionType")
				.add(Projections.property("question"), "question")
				.add(Projections.property("standard"), "standard")
		);

		crit.setResultTransformer(Transformers.aliasToBean(QuestionVo.class));
			
		if (filter != null) {
			try{
				/*
				 * Add the criteria
				 */
				Collection<FilterCriteria> filterCriteria = QuestionFilterImpl.getFilterCriteria(filter);
					
				CriteriaBuilder.addCriteria(crit, filterCriteria);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}
		
		return crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
   public Question getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, QuestionImpl.class);
	}   

	public Collection<QuestionVo> getDefaultQuestions() throws PersistenceException {
		Collection<QuestionVo> vos = null;
		try{
			Criteria crit = getHibernateSession().createCriteria(QuestionImpl.class);
		
			crit.setProjection(Projections.projectionList()
					.add(Projections.property("id"), "id")
					.add(Projections.property("questionType"), "questionType")
					.add(Projections.property("question"), "question")
					.add(Projections.property("standard"), "standard")
			);
	
			crit.setResultTransformer(Transformers.aliasToBean(QuestionVo.class));
			if(super.isOracleDialect())
				crit.add(Expression.sql("is_standard = 1"));
			else
				crit.add(Expression.sql("is_standard = true"));
			
			vos = crit.list();
			
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return vos;
	}
}
