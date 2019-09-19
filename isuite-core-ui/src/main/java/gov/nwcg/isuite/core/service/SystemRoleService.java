/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author gdyer
 *
 */
public interface SystemRoleService {

	/**
	 * Returns all system roles defined.
	 * 
	 * @return
	 * 		collection of system roles.
	 * @throws PersistenceException
	 */
	public Collection<SystemRoleVo> getAllRoles() throws ServiceException;
	
	public Collection<SystemRoleVo> getGrid() throws ServiceException;
	
	public SystemRoleVo getById(Long id) throws ServiceException;

	public SystemRoleVo save(SystemRoleVo persistable) throws ServiceException;
	
	
}
