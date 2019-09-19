package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SystemRoleDao extends TransactionSupport, CrudDao<SystemRole> {

	/**
	 * Returns all system roles defined.
	 * 
	 * @return
	 * 		collection of system roles.
	 * @throws PersistenceException
	 */
	public Collection<SystemRole> getAllRoles() throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemRoleDao#getGrid()
	 */
	public Collection<SystemRole> getGrid() throws PersistenceException;
	
	public SystemRole getById(Long id, Class clazz) throws PersistenceException;

	public void save(SystemRole persistable) throws PersistenceException;
}
