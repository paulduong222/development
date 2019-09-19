package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.filter.ResourcePersonFilter;
import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.service.ResourceService;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.ResourcePersonVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.HibernateProperties;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * 
 * @author bsteiner
 */
public class ResourceServiceImpl extends BaseService implements ResourceService {
   private ResourceDao resourceDao;
   
   public ResourceServiceImpl(){
	   
   }
   
   public void initialization(){
	  resourceDao = (ResourceDao)super.context.getBean("resourceDao");
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.ResourceService#delete(gov.nwcg.isuite.core.vo.ResourceVo)
    */
   public void delete(ResourceVo persistableVo) throws ServiceException {
	   try {
		   if( null != persistableVo.getId()){
			   Resource resource = resourceDao.getById(persistableVo.getId(), ResourceImpl.class);
			   if(null!=resource){
				   
				   /*
				    * For resource entities, we only set the deleted_date field
				    * instead of physically deleting the entity record.
				    */
				   resource.setDeletedDate(Calendar.getInstance().getTime());
				   
				   resourceDao.save(resource);
			   }
		   }
	   } catch (PersistenceException pe) {
		   throw new ServiceException(pe);
	   } catch (Exception e){
		   throw new ServiceException(e);
	   }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.ResourceService#save(gov.nwcg.isuite.core.vo.ResourceVo)
    */
   public void save(ResourceVo persistableVo) throws ServiceException,ValidationException {
	   try {
		   
		   /*
		    * Build the referenced persistable objects
		    */
		   Collection<Persistable> persistables = new ArrayList<Persistable>();
		   
		   if(null != persistableVo.getParentResourceId()){
			   if(null != persistableVo.getParentResourceVo()){
				   Resource parentRes = ResourceVo.toEntity(null,persistableVo.getParentResourceVo(),false);
				   persistables.add(parentRes);
			   }else{
				   Resource parentRes = new ResourceImpl();
				   parentRes.setId(persistableVo.getParentResourceId());
				   persistables.add(parentRes);
			   }
		   }

		   
		   // create an entity from the vo object
		   Resource resource = ResourceVo.toEntity(null,persistableVo
				   									,true
				   									,toPersistableArray(persistables));

		   // try and persist the entity object
		   resourceDao.save(resource); 

		   // update the reference vo with the generated id
		   persistableVo.setId(resource.getId());
		   
	   } catch (PersistenceException pe) {
		   throw new ServiceException(pe);
	   } catch (ValidationException ve){
			throw ve;
	   } catch (Exception e){
		   throw new ServiceException(e);
	   }
   }
   
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceService#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ResourceVo> persistableList) throws ServiceException, ValidationException{
		Collection<Resource> resourceList = new ArrayList<Resource>();
		
		try {
			for(ResourceVo persistableVo : persistableList)
			{
			   /*
			    * Build the referenced persistable objects
			    */
			   Collection<Persistable> persistables = new ArrayList<Persistable>();
				   
			   if(null != persistableVo.getParentResourceId()){
				   Resource parentResource = resourceDao.getById(persistableVo.getParentResourceId(), ResourceImpl.class);
				   if(null != parentResource)
					   persistables.add(parentResource);
			   }

				// create an entity from the vo object
				Resource resource = ResourceVo.toEntity(null,persistableVo,true,toPersistableArray(persistables));
				
				resourceList.add(resource);
			}

			// try and persist the entity collection
			resourceDao.saveAll(resourceList); 

		} catch (PersistenceException pe) {
			throw new ServiceException(pe);
		} catch (ValidationException ve){
			throw ve;
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceService#getById(java.lang.Long)
	 */
	public ResourceVo getById(Long id) throws ServiceException {
		try{
			Resource resource = resourceDao.getById(id, ResourceImpl.class);

		   /*
		    * Build the referenced persistable objects
		    */
		   Collection<Persistable> persistables = new ArrayList<Persistable>();
		   //if(null != resource.getPermanentResourceId())
		   //	   persistables.add(resource.getPermanentResource());

		   if(null != resource.getParentResourceId())
			   persistables.add(resource.getParentResource());

		   if(null != resource.getChildResources()){
			   for(Resource child : resource.getChildResources()){
				   persistables.add(child);
			   }
		   }
		   
		   return ResourceVo.getInstance(resource,true,toPersistableArray(persistables));
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceService#getGrid(gov.nwcg.isuite.core.filter.impl.ResourceFilter)
	 */
	public Collection<ResourceGridVo> getGrid(ResourceFilter filter) throws ServiceException {
		
		Collection<ResourceGridVo> list = null;
		
		try{
			
			list = resourceDao.getGrid(filter);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return list;
	}
   
	public Collection<ResourceVo> getMatchingResources(ResourceDuplicateFilter filter) throws ServiceException {
		
		try{
		
			Collection<Resource> entities = resourceDao.getMatchingResources(filter);
			
			if( (null != entities) && (entities.size()>0))
				return ResourceVo.getInstances(entities, true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return new ArrayList<ResourceVo>();
	}

   public Collection<ResourcePersonVo> getPersonResources(ResourcePersonFilter filter) throws ServiceException {
      try {
         return resourceDao.getPersonResources(filter, (HibernateProperties)super.context.getBean("hibernateProperties"));
      }
      catch ( PersistenceException e ) {
         super.handleException(e);
      }
      
      return null;
   }
   
}
