package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.impl.IapAttachmentImpl;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;

public class IapAttachmentDaoHibernate extends TransactionSupportImpl implements IapAttachmentDao {
	private final CrudDao<IapAttachment> crudDao;
	
	public IapAttachmentDaoHibernate(final CrudDao<IapAttachment> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapAttachment persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapAttachment getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapAttachment persistable) throws PersistenceException {
		crudDao.setSkipFixCasing(true);
		crudDao.save(persistable);
		crudDao.setSkipFixCasing(false);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapAttachment> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapAttachmentDao#getByPlanId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapAttachment> getByPlanId(Long planId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapAttachmentImpl.class);
		
		crit.add(Expression.eq("iapPlanId", planId));
		
		return crit.list();
	}

	public Collection<String> getIapAttachmentFilenames() throws PersistenceException {
		String sql = "select filename from isw_iap_attachment";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		Collection<String> list = (Collection<String>)q.list();
		return list;
	}
}
