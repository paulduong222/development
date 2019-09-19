package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.domain.impl.SubGroupCategoryImpl;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class SubGroupCategoryDaoHibernate extends TransactionSupportImpl implements SubGroupCategoryDao {
	
	private final CrudDao<SubGroupCategory> crudDao;
	
	public SubGroupCategoryDaoHibernate(final CrudDao<SubGroupCategory> crudDao) {
	
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SubGroupCategoryDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<SubGroupCategoryVo> getPicklist() throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(SubGroupCategoryImpl.class);
		
		crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
	      
	    crit.add(Expression.isNull("i.incidentEndDate"));
		
		crit.addOrder(Order.asc("this.code"));
		
		Collection<SubGroupCategory> entities = crit.list();
		
		try {
			return SubGroupCategoryVo.getInstances(entities, true);
		}catch(Exception e) {
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SubGroupCategory persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SubGroupCategory getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, SubGroupCategoryImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SubGroupCategory persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SubGroupCategory> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SubGroupCategoryDao#getDuplicateCodeCount(java.lang.String, java.lang.Long)
	 */
	public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(SubGroupCategoryImpl.class);
		   
		 crit.add(Expression.eq("code", code.toUpperCase()));
		 crit.add(Expression.ne("id", excludeId));
		 crit.add(Expression.eq("standard", Boolean.TRUE));
	   
		 return crit.list().size();
	}

}
