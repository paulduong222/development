package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import gov.nwcg.isuite.core.domain.RequestCategory;
import gov.nwcg.isuite.core.domain.impl.RequestCategoryImpl;
import gov.nwcg.isuite.core.persistence.RequestCategoryDao;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class RequestCategoryDaoHibernate extends TransactionSupportImpl implements RequestCategoryDao {

	private final CrudDao<RequestCategory> crudDao;
	
	 /**
	 * Constructor.
	 * @param crudDao can't be null
	 */
	public RequestCategoryDaoHibernate(final CrudDao<RequestCategory> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
	    this.crudDao = crudDao;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<RequestCategoryVo> getPicklist() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(RequestCategoryImpl.class);
		crit.setProjection(Projections.projectionList()
	               .add(Projections.property("id"), "id")
	               .add(Projections.property("code"), "code")
	               .add(Projections.property("description"), "description")
	               );
		crit.addOrder(Order.asc("description"));
		crit.setResultTransformer(Transformers.aliasToBean(RequestCategoryVo.class));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<RequestCategoryVo> getAll() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(RequestCategoryImpl.class);
		crit.setProjection(Projections.projectionList()
	               .add(Projections.property("id"), "id")
	               .add(Projections.property("code"), "code")
	               .add(Projections.property("description"), "description")
	               );
		crit.addOrder(Order.asc("description"));
		crit.setResultTransformer(Transformers.aliasToBean(RequestCategoryVo.class));
		return crit.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(RequestCategory persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RequestCategory getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, RequestCategoryImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(RequestCategory persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RequestCategory> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
