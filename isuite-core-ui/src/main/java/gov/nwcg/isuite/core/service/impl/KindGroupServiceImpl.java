package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.KindGroup;
import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.impl.KindGroupImpl;
import gov.nwcg.isuite.core.domain.impl.Sit209Impl;
import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.core.persistence.KindGroupDao;
import gov.nwcg.isuite.core.service.KindGroupService;
import gov.nwcg.isuite.core.vo.KindGroupVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Geoff Dyer
 */
public class KindGroupServiceImpl extends BaseService implements KindGroupService {
   private KindGroupDao kindGroupDao;
   
   public KindGroupServiceImpl(){
	   
   }
   
   public void initialization(){
	   kindGroupDao = (KindGroupDao)super.context.getBean("kindGroupDao");
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.KindGroupService#delete(gov.nwcg.isuite.core.vo.KindGroupVo)
    */
   public void delete(Collection<Long> kindGroupIds) throws ServiceException {
	   try {
			if ((kindGroupIds == null) || (kindGroupIds.size() < 1)) {
				throw new ServiceException("invalid or missing Ids");
			}
			for (Long kindGroupId : kindGroupIds) {
				KindGroup persistable = kindGroupDao.getById(kindGroupId, KindGroupVo.class);
				if (persistable != null) {
					if (!persistable.isStandard()) {
						kindGroupDao.delete(persistable);
					} else {
						super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
					}
				} else {
					throw new ServiceException("cannot find record with id: " + kindGroupId);
				}
			}
		} catch (ServiceException se){
			throw se;
		} catch ( Exception e ) {
			super.handleException(e);
		}
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.KindGroupService#save(gov.nwcg.isuite.core.vo.KindGroupVo)
    */
   @Override
   public KindGroupVo save(KindGroupVo vo) throws ServiceException,ValidationException {
	   KindGroup entity = null;
	   try
	   {
		   if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) 
		   {
				/*
				 *  Updating existing one
				 */
				entity = kindGroupDao.getById(vo.getId(), KindGroupImpl.class);
	
				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"KindGroup["+vo.getId()+"]");
				if (entity.isStandard()) {
					super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
				}
	
			} 
		   else 
		   {
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new KindGroupImpl();
				vo.setStandard(false);
			}
	
			if( (null == vo.getId()) || (vo.getId() < 1 ) ){
				if (!kindGroupDao.isCodeUnique(vo.getCode())) 
					super.handleException(ErrorEnum._0219_DUPLICATE_REFERENCE_DATA);
			}
			
			entity = KindGroupVo.toEntity(entity, vo, true);
			
			kindGroupDao.save(entity);
			
			//entity=kindGroupDao.getById(entity.getId(), KindGroupImpl.class);
			//KindGroupVo rtnVo = new KindGroupVo();	
			//rtnVo = this.getById(entity.getId());
			//return rtnVo;
			return KindGroupVo.getInstance(entity, true);
	
		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
   }
   
	
	
   /* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service#getById(java.lang.Long)
	 */
	@Override
	public KindGroupVo getById(Long id) throws ServiceException {
		
		try {
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"KindGroup[id=null]");

			KindGroup entity = kindGroupDao.getById(id, KindGroupImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"KindGroup[id=null]");
				
			return KindGroupVo.getInstance(entity, true);         
			
		} catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.KindGroupService#getGrid(gov.nwcg.isuite.core.filter.impl.KindGroupFilter)
	 */
	@Override
	public Collection<KindGroupVo> getGrid(KindGroupFilter filter) throws ServiceException {
		
		Collection<KindGroupVo> list = null;
		
		try{
			
			list = kindGroupDao.getGrid(filter);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return list;
	}
   
	
   
}
