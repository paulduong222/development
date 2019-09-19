/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.domain.impl.SystemModulePermImpl;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.persistence.SystemModulePermDao;
import gov.nwcg.isuite.core.service.SystemModulePermService;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.SystemModulePermVo;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

/**
 * @author gdyer
 *
 */
public class SystemModulePermServiceImpl extends BaseService implements SystemModulePermService {
	private SystemModulePermDao systemModulePermDao;
	
	/**
	 * Default Constructor
	 */
	public SystemModulePermServiceImpl() {
	}
	
	public void initialization(){
		systemModulePermDao = (SystemModulePermDao)super.context.getBean("systemModulePermDao");
	}
	
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemModulePermService#getById(java.lang.Long)
	 */
	@Override
	public SystemModulePermVo getById(Long id) throws ServiceException {
		try 
		{
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemModulePerm[id=null]");

			SystemModulePerm entity = systemModulePermDao.getById(id, SystemModulePermImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemModulePerm[id=null]");
				
			return SystemModulePermVo.getInstance(entity, true);         
			
		} 
		catch ( Exception e ) 
		{
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemModulePermService#getGrid()
	 */
	@Override
	public Collection<SystemModulePermVo> getGrid() throws ServiceException {
		try 
		{
			Collection<SystemModulePermVo> rtnVal = SystemModulePermVo.getInstances(systemModulePermDao.getGrid(), true);
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
	 * @see gov.nwcg.isuite.core.service.SystemModulePermService#save(gov.nwcg.isuite.core.vo.SystemModulePermVo)
	 */
	@Override
	public SystemModulePermVo save(SystemModulePermVo vo) throws ServiceException {
		SystemModulePerm entity = null;
		try 
		{

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = systemModulePermDao.getById(vo.getId(), SystemModulePermImpl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemModulePerm["+vo.getId()+"]");
				
			} 
			else 
			{
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new SystemModulePermImpl();
			}

			entity = SystemModulePermVo.toEntity(vo, true);
			systemModulePermDao.save(entity);
			return SystemModulePermVo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	@Override
	public void delete(SystemModulePermVo vo) throws ServiceException {
		SystemModulePerm entity = null;
		try
		{
			entity = SystemModulePermVo.toEntity(vo,true);
			systemModulePermDao.delete(entity);
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
	public SystemModulePermVo getByModuleId(Long id) throws ServiceException {
		try 
			{
				return SystemModulePermVo.getInstance(systemModulePermDao.getByModuleId(id), true);

			} catch(ServiceException se){
				throw se;
			}catch ( Exception e ) {
				super.handleException(e);
			}
			return null;
	}

}
