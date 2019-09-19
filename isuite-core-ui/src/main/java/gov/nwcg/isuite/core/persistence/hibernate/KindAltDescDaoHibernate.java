package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;

import gov.nwcg.isuite.core.domain.KindAltDesc;
import gov.nwcg.isuite.core.domain.impl.KindAltDescImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.persistence.KindAltDescDao;
import gov.nwcg.isuite.core.vo.KindAltDescVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class KindAltDescDaoHibernate extends TransactionSupportImpl implements KindAltDescDao {
	private final CrudDao<KindAltDesc> crudDao;
	 
	/*
	 * Constructor
	 */
	public KindAltDescDaoHibernate(final CrudDao<KindAltDesc> crudDao) {

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
      
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(KindAltDesc persistable) throws PersistenceException {
		 crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public KindAltDesc getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, KindImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(KindAltDesc persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<KindAltDesc> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.KindAltDescDao#getGrid()
	 */
	@SuppressWarnings("unchecked")
	public Collection<KindAltDescVo> getGrid() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(KindAltDescImpl.class);
		
		Collection<KindAltDesc> entities = crit.list();
		
		try{
			return KindAltDescVo.getInstances(entities, true);
			
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
		
	}

}
