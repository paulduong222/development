package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Department;
import gov.nwcg.isuite.core.domain.impl.DepartmentImpl;
import gov.nwcg.isuite.core.persistence.DepartmentDao;
import gov.nwcg.isuite.core.vo.DepartmentVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import org.hibernate.Criteria;

public class DepartmentDaoHibernate extends TransactionSupportImpl implements DepartmentDao {

	private final CrudDao<Department> crudDao;
	
	/**
    * Constructor.
    * @param crudDao can't be null
    */
	public DepartmentDaoHibernate(final CrudDao<Department> crudDao) {
		
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.hibernate.DepartmentDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<DepartmentVo> getPicklist() throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(DepartmentImpl.class);
		
		Collection<Department> entities = crit.list();
		
		try {
			return DepartmentVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Department persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Department getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, DepartmentImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Department persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Department> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
