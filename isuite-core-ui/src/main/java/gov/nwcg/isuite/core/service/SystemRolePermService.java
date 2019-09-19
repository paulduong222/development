/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.SystemRolePermVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author gdyer
 *
 */
public interface SystemRolePermService {

	public void delete(SystemRolePermVo vo) throws ServiceException;	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemRolePermVo getById(Long id) throws ServiceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public SystemRolePermVo save(SystemRolePermVo vo) throws ServiceException;
	
	public Collection<SystemRolePermVo> getGrid() throws ServiceException;
	
	public SystemRolePermVo getByModulePermId(Long id) throws ServiceException;
	
	public SystemRolePermVo getByRoleId(Long id) throws ServiceException;
}
