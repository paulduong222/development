package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.domain.impl.UserImportFailureImpl;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.filter.impl.UserImportFailureFilterImpl;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserImportFailureQuery;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;

@SuppressWarnings("unchecked")
public class UserImportFailureDaoHibernate extends TransactionSupportImpl implements UserImportFailureDao{
	private final CrudDao<UserImportFailure>         crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao
	 *           can't be null
	 */
	public UserImportFailureDaoHibernate(final CrudDao<UserImportFailure> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	protected CrudDao<UserImportFailure> getCrudDao() {
		return crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserDao#save(gov.nwcg.isuite.core.domain.User)
	 */
	public void save(UserImportFailure entity) throws PersistenceException{
		getCrudDao().save(entity);
	}

	public void delete(UserImportFailure entity) throws PersistenceException{
		getCrudDao().delete(entity);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserImportFailureDao#deleteAll()
	 */
	public void deleteAll() throws PersistenceException {
		Query q = getHibernateSession().getNamedQuery(UserImportFailureQuery.DELETE_ALL);
		q.executeUpdate();
	}

	public UserImportFailure getById(Long id,Class clazz) throws PersistenceException {
		return crudDao.getById(id, UserImportFailureImpl.class);
	}

	public void saveAll(Collection<UserImportFailure> entities) throws PersistenceException {
		getCrudDao().saveAll(entities);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserImportFailureDao#getGrid()
	 */
	public Collection<UserImportFailureVo> getGrid(UserImportFailureFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(UserImportFailureImpl.class);

		crit.setFetchMode("roles", FetchMode.JOIN);
		
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		if(null != filter){
			try{
				Collection<FilterCriteria> filterCriteria = UserImportFailureFilterImpl.getFilterCriteria(filter);
			
				CriteriaBuilder.addCriteria(crit, filterCriteria);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}
		
		Collection<UserImportFailure> entities = crit.list();
		
		try{
			Collection<UserImportFailureVo> vos = UserImportFailureVo.getIntances(entities, true);
			return vos;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}


}