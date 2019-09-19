/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.persistence.SystemRoleDao;
import gov.nwcg.isuite.core.service.SystemRoleService;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

/**
 * @author gdyer
 *
 */
public class SystemRoleServiceImpl extends BaseService implements SystemRoleService {
	private SystemRoleDao systemRoleDao;
	
	/**
	 * Default Constructor
	 */
	public SystemRoleServiceImpl() {
	}
	
	public void initialization(){
		systemRoleDao = (SystemRoleDao)super.context.getBean("systemRoleDao");
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRoleService#getAllRoles()
	 */
	@Override
	public Collection<SystemRoleVo> getAllRoles() throws ServiceException {
		return this.getGrid();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRoleService#getById(java.lang.Long)
	 */
	@Override
	public SystemRoleVo getById(Long id) throws ServiceException {
		try 
		{
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRole[id=null]");

			SystemRole entity = systemRoleDao.getById(id, SystemRoleImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRole[id=null]");
				
			return SystemRoleVo.getInstance(entity, true);         
			
		} 
		catch ( Exception e ) 
		{
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRoleService#getGrid()
	 */
	@Override
	public Collection<SystemRoleVo> getGrid() throws ServiceException {
		try 
		{
			Collection<SystemRoleVo> rtnVal = SystemRoleVo.getInstances(systemRoleDao.getGrid(), true);
			return rtnVal;
		}
		catch ( PersistenceException e ) 
		{
			throw new ServiceException(e);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRoleService#save(gov.nwcg.isuite.core.vo.SystemRoleVo)
	 */
	@Override
	public SystemRoleVo save(SystemRoleVo vo) throws ServiceException {
		SystemRole entity = null;
		try 
		{

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = systemRoleDao.getById(vo.getId(), SystemRoleImpl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRole["+vo.getId()+"]");
				
			} 
			else 
			{
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new SystemRoleImpl();
			}

			entity = SystemRoleVo.toEntity(vo, true);
			systemRoleDao.save(entity);
			return SystemRoleVo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

}
