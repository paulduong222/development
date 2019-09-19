package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.impl.DataAuditConfigImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class DataAuditConfigDaoHibernate extends TransactionSupportImpl implements DataAuditConfigDao {

	private final CrudDao<DataAuditConfig> crudDao;

	public DataAuditConfigDaoHibernate(final CrudDao<DataAuditConfig> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void delete(DataAuditConfig persistable) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@Override
	public DataAuditConfig getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, DataAuditConfigImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void save(DataAuditConfig persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	@Override
	public void saveAll(Collection<DataAuditConfig> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.DataAuditConfigDao#getByEventName(java.lang.String, java.lang.String)
	 */
	public DataAuditConfig getByEventName(String table, String event) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(DataAuditConfigImpl.class);
		
		if(StringUtility.hasValue(table)){
			crit.add(Restrictions.eq("tableName", table));
		}
		
		if(StringUtility.hasValue(event)){
			crit.add(Restrictions.eq("auditEventType", event));
		}
		
		DataAuditConfig entity = (DataAuditConfig)crit.uniqueResult();
		
		return entity;
	}
	
	
}
