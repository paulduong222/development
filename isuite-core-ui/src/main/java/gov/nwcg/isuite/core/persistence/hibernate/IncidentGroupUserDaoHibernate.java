package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupUserImpl;
import gov.nwcg.isuite.core.persistence.IncidentGroupUserDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class IncidentGroupUserDaoHibernate extends TransactionSupportImpl implements IncidentGroupUserDao {

	private final CrudDao<IncidentGroupUser> crudDao;

	public IncidentGroupUserDaoHibernate(final CrudDao<IncidentGroupUser> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentGroupUser persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
   public IncidentGroupUser getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentGroupUser persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentGroupUser> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupUserDao#getIncidentGroupUserByUserIdAndIncidentGroupId(java.lang.Long, java.lang.Long)
	 */
   public IncidentGroupUser getIncidentGroupUserByUserIdAndIncidentGroupId(Long userId, Long incidentGroupId) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentGroupUserImpl.class);
      crit.add(Restrictions.eq("userId", userId));
      crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
      crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      return (IncidentGroupUser)crit.uniqueResult();
   }

}
