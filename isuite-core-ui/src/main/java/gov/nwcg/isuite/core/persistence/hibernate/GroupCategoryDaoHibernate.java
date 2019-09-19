package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.impl.GroupCategoryImpl;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class GroupCategoryDaoHibernate extends TransactionSupportImpl implements GroupCategoryDao {

	private final CrudDao<GroupCategory> crudDao;
	
	public GroupCategoryDaoHibernate(final CrudDao<GroupCategory> crudDao) {
		
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.GroupCategoryDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<GroupCategoryVo> getPicklist() throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(GroupCategoryImpl.class);
		
		crit.addOrder(Order.asc("this.code"));
		
		Collection<GroupCategory> entities = crit.list();
		
		try {
			return GroupCategoryVo.getInstances(entities, true);
		}catch(Exception e) {
			throw new PersistenceException(e);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(GroupCategory persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public GroupCategory getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, GroupCategoryImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(GroupCategory persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<GroupCategory> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.GroupCategoryDao#getDuplicateCodeCount(java.lang.String, java.lang.Long)
	 */
	public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException {
		
		 Criteria crit = getHibernateSession().createCriteria(GroupCategoryImpl.class);
		   
		 crit.add(Expression.eq("code", code.toUpperCase()));
		 crit.add(Expression.ne("id", excludeId));
		 crit.add(Expression.eq("standard", Boolean.TRUE));
		   
		 return crit.list().size();
	}

}
