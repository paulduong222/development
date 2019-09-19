/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import java.util.Collection;


import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.core.domain.impl.SystemRolePermImpl;
import gov.nwcg.isuite.core.persistence.SystemRolePermDao;
import gov.nwcg.isuite.core.service.SystemRolePermService;
import gov.nwcg.isuite.core.vo.SystemRolePermVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

/**
 * @author gdyer
 *
 */
public class SystemRolePermServiceImpl extends BaseService implements SystemRolePermService {
	private SystemRolePermDao systemRolePermDao;
	
	/**
	 * Default Constructor
	 */
	public SystemRolePermServiceImpl() {
	}
	
	public void initialization(){
		systemRolePermDao = (SystemRolePermDao)super.context.getBean("systemRolePermDao");
	}
	
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRolePermService#getById(java.lang.Long)
	 */
	@Override
	public SystemRolePermVo getById(Long id) throws ServiceException {
		try 
		{
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRolePerm[id=null]");

			SystemRolePerm entity = systemRolePermDao.getById(id, SystemRolePermImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRolePerm[id=null]");
				
			return SystemRolePermVo.getInstance(entity, true);         
			
		} 
		catch ( Exception e ) 
		{
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemRolePermService#getGrid()
	 */
	@Override
	public Collection<SystemRolePermVo> getGrid() throws ServiceException {
		try 
		{
			Collection<SystemRolePermVo> rtnVal = SystemRolePermVo.getInstances(systemRolePermDao.getGrid(), true);
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
	 * @see gov.nwcg.isuite.core.service.SystemRolePermService#save(gov.nwcg.isuite.core.vo.SystemRolePermVo)
	 */
	@Override
	public SystemRolePermVo save(SystemRolePermVo vo) throws ServiceException {
		SystemRolePerm entity = null;
		try 
		{

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = systemRolePermDao.getById(vo.getId(), SystemRolePermImpl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemRolePerm["+vo.getId()+"]");
				
			} 
			else 
			{
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new SystemRolePermImpl();
			}

			entity = SystemRolePermVo.toEntity(vo, true);
			systemRolePermDao.save(entity);
			return SystemRolePermVo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	@Override
	public void delete(SystemRolePermVo vo) throws ServiceException {
		SystemRolePerm entity = null;
		try
		{
			entity = SystemRolePermVo.toEntity(vo,true);
			systemRolePermDao.delete(entity);
		} 
		catch(ServiceException se)
		{
			throw se;
		}
		catch ( Exception e ) 
		{
			super.handleException(e);
		}
	}

	@Override
	public SystemRolePermVo getByModulePermId(Long id) throws ServiceException {
		try 
		{
			return SystemRolePermVo.getInstance(systemRolePermDao.getByModulePermId(id), true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	@Override
	public SystemRolePermVo getByRoleId(Long id) throws ServiceException {
		try 
		{
			return SystemRolePermVo.getInstance(systemRolePermDao.getByRoleId(id), true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

}
