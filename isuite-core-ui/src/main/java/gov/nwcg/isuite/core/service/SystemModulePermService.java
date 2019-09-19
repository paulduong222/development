/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.SystemModulePermVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author gdyer
 *
 */
public interface SystemModulePermService {

	public void delete(SystemModulePermVo vo) throws ServiceException;	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemModulePermVo getById(Long id) throws ServiceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public SystemModulePermVo save(SystemModulePermVo vo) throws ServiceException;
	
	public Collection<SystemModulePermVo> getGrid() throws ServiceException;
	
	public SystemModulePermVo getByModuleId(Long id) throws ServiceException;
}
