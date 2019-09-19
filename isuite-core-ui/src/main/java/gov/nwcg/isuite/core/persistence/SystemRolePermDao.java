package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SystemRolePermDao extends TransactionSupport, CrudDao<SystemRolePerm> {

	public void delete(SystemRolePerm persistable) throws PersistenceException;	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemRolePerm getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemRolePerm persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemRolePerm> persistables) throws PersistenceException;
	
	public Collection<SystemRolePerm> getGrid() throws PersistenceException;
	
	public SystemRolePerm getByModulePermId(Long id) throws PersistenceException;
	
	public SystemRolePerm getByRoleId(Long id) throws PersistenceException;
	
	/**
	 * 
	 * @param roles
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<String> getBySystemRoles(Collection<SystemRole> roles) throws PersistenceException;
	
}
