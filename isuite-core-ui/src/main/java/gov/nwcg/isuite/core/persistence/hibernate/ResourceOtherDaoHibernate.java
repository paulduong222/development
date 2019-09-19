package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.impl.ResourceOtherImpl;
import gov.nwcg.isuite.core.persistence.ResourceOtherDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class ResourceOtherDaoHibernate extends TransactionSupportImpl implements ResourceOtherDao {

	private final CrudDao<ResourceOther> crudDao;
	
	public ResourceOtherDaoHibernate(final CrudDao<ResourceOther> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceOtherDao#getResourceOthersByIds(java.util.Collection)
	 */
	public Collection<ResourceOther> getResourceOthersByIds(Collection<Long> ids) throws PersistenceException {
		Collection<ResourceOther> resourceOthers = new ArrayList<ResourceOther>();
		
		if(ids != null && ids.size() > 0) {
			Criteria crit = (Criteria) getHibernateSession().createCriteria(ResourceOtherImpl.class);
			crit.add(Expression.in("id", ids));
			resourceOthers = crit.list();
		}
		
		return resourceOthers; 
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ResourceOther persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ResourceOther getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ResourceOther persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ResourceOther> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
