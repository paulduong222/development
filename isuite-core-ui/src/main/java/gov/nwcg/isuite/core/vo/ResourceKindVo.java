package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ResourceKindVo extends AbstractVo implements PersistableVo {
	private ResourceVo resourceVo=null;
	private Long resourceId;
	private KindVo kindVo=null;
	private Long kindId;
	private Boolean training=false;
	private Boolean primary = false;

	public ResourceKindVo(){
		
	}
	
    /**
     * Returns a ResourceKindVo instance from a ResourceKind entity.
     * 
     * @param entity
     * 			the source ResourceKind entity
     * @param cascadable
     * 			flag indicating whether the instance should created as a cascadable vo
     * @return
     * 		instance of ResourceKindVo
     * @throws Exception
     */
	public static ResourceKindVo getInstance(ResourceKind entity,boolean cascadable) throws Exception {
    	ResourceKindVo vo = new ResourceKindVo();
    	
    	if(null == entity)
    		throw new Exception("Unable to create ResourceKindVo from null ResourceKind entity.");
    	
    	vo.setId(entity.getId());

    	/*
    	 * Only populate fields outside of the entity Id if needed
    	 */
    	if(cascadable){
    		/*
    	     * ResourceKind <--> Resource (bidirectional relationship)
    	     * The relationship from resourceKind to Resource
    	     * should not cascade up to Resource (only need to set the resourceId).
    	     * 
    	     * TODO: determine need to setResourceVo ?
    	     * 		vo.setResourceVo(ResourceVo.getInstance(entity.getResource(),false));
    		 */
    		vo.setResourceId(entity.getResourceId());

    	    /*
    	     * ResourceKind <--> Kind (one way directional relationship)
    	     * The relationship from resourceKind to Kind
    	     * should cascade (create new kind if not there) (cascadable = true).
    	     */
    	    vo.setKindId(entity.getKindId());
    	    if(null!=entity.getKind()){
    	    	vo.setKindVo(KindVo.getInstance(entity.getKind(),true));
    	    }
    	    
    		vo.setTraining(entity.getTraining());
    		vo.setPrimary(entity.getPrimary());
    	}
    	
    	return vo;
	}

    /**
     * Returns a collection of ResourceKindVo's.
     * 
     * @param entities
     * 			the source collection of resourceKind entities
     * @param cascadable
     * 			flag indicating whether the instance should created as a cascadable vo
     * @return
     * 		collection of resourceKindVo's
     * @throws Exception
     */
	public static Collection<ResourceKindVo> getInstances(Collection<ResourceKind> entities,boolean cascadable) throws Exception {
		Collection<ResourceKindVo> vos = new ArrayList<ResourceKindVo>();
		
		for(ResourceKind entity : entities){
			vos.add(ResourceKindVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

    /**
     * Returns a ResourceKind entity from a ResourceKind vo.
     * 
     * @param vo
     * 			the source ResourceKind vo
     * @param cascadable
     * 			flag indicating whether the entity instance should created as a cascadable entity
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 			ResourceKind entity
     * @throws Exception
     */
	public static ResourceKind toEntity(ResourceKindVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		ResourceKind entity = new ResourceKindImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			
        	/*
        	 * ResourceKind --> Kind (one way directional relationship)
        	 * Only need to setKind with just the entity id (set cascadable false).
        	 */
			if(null!=vo.getKindVo()){
				entity.setKind(KindVo.toEntity(null,vo.getKindVo(),false));
			}else{
				if(null!=vo.getKindId()){
					KindVo kindVo = new KindVo();
					kindVo.setId(vo.getKindId());
					entity.setKind(KindVo.toEntity(null,kindVo,false));
				}else
					throw new Exception("Unable to create ResourceKind entity with unknown Kind association.");
			}
			
			/*
			 * Set the resource from the resource instance
			 * in the persistables array if its there.
			 * We need the resource instance from persistables array for
			 * scenarios when this resourcekind is being created along
			 * with a new Resource.
			 */
			Resource resource = (Resource)getPersistableObject(persistables, ResourceImpl.class);
			if(null!=resource)
				entity.setResource(resource);
			else
				throw new Exception("Unable to create ResourceKind entity with null Resource reference.");
			
			entity.setTraining(vo.getTraining());
			
			entity.setPrimary(vo.getPrimary());
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}
		
		return entity;
	}

    /**
     * Returns a collection of ResourceKind entities from a colleciton of ResourceKind vos.
     * 
     * @param vo
     * 			the source collection of ResourceKind vos
     * @param cascadable
     * 			flag indicating whether the entity instances should created as a cascadable entities
     * @param persistables
     * 			Optional array of referenced persistable entities 
     * @return
     * 			collection of ResourceKind entities
     * @throws Exception
     */
	public static Collection<ResourceKind> toEntityList(Collection<ResourceKindVo> vos, boolean cascadable, Persistable... persistables) throws Exception {
		Collection<ResourceKind> entities = new ArrayList<ResourceKind>();
		
		for(ResourceKindVo vo : vos){
			entities.add(ResourceKindVo.toEntity(vo,cascadable,persistables));
		}
		
		return entities;
	}

   /**
    * Perform some validation on the resourcekind field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source resourceKind entity
    * @throws ValidationException
    */
   private static void validateEntity(ResourceKind entity) throws ValidationException {
	   
   }

	/**
	 * @param newList
	 * @param resourceKinds
	 * @return
	 */
	public static Collection<ResourceKind> toEntityRemoveList(Collection<ResourceKindVo> newList, Collection<ResourceKind> resourceKinds){
		Collection<ResourceKind> removeList = new ArrayList<ResourceKind>();

		if(!CollectionUtility.hasValue(resourceKinds))
			return removeList;

		for(ResourceKind rk : resourceKinds){
			Boolean found=false;

			for(ResourceKindVo rkVo : newList){
				if(rk.getKindId().compareTo(rkVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(rk);
			}
		}

		return removeList;
	}
   
	public static Collection<ResourceKind> toEntityAddList(Collection<ResourceKindVo> vos, Collection<ResourceKind> resourceKinds, Resource entity) throws Exception {
		Collection<ResourceKind> addList = new ArrayList<ResourceKind>();

		if(!CollectionUtility.hasValue(resourceKinds)){
			/*
			 * Add all
			 */
			for(ResourceKindVo rkVo : vos){
				ResourceKind rk = new ResourceKindImpl();
				rk.setResource(entity);
				rk.setKind(KindVo.toEntity(null, rkVo.getKindVo(), false));
				rk.setTraining(rkVo.getTraining());
				addList.add(rk);
			}
			return addList;
		}

		for(ResourceKindVo rkVo : vos){
			Boolean found=false;

			for(ResourceKind rk : resourceKinds){
				if(rk.getKindId().compareTo(rkVo.getKindVo().getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				ResourceKind rk = new ResourceKindImpl();
				rk.setResource(entity);
				rk.setKind(KindVo.toEntity(null, rkVo.getKindVo(), false));
				rk.setTraining(rkVo.getTraining());
				addList.add(rk);
			}
		}

		return addList;
	}

	public static Collection<ResourceKind> toEntityUpdateList(Collection<ResourceKindVo> vos, Collection<ResourceKind> resourceKinds, Resource entity) throws Exception {
		Collection<ResourceKind> updateList = new ArrayList<ResourceKind>();

		if(!CollectionUtility.hasValue(resourceKinds)){
			return updateList;
		}

		for(ResourceKindVo rkVo : vos){
			Boolean found=false;

			for(ResourceKind rk : resourceKinds){
				if(LongUtility.hasValue(rk.getId()) && LongUtility.hasValue(rkVo.getId())){
					if(rk.getId().compareTo(rkVo.getId())==0){
						rk.setTraining(rkVo.getTraining());
						updateList.add(rk);
					}
				}
			}

		}

		return updateList;
	}
	
	/**
	 * Returns the resourceVo.
	 *
	 * @return 
	 *		the resourceVo to return
	 */
	public ResourceVo getResourceVo() {
		return resourceVo;
	}
	
	/**
	 * Sets the resourceVo.
	 *
	 * @param resourceVo 
	 *			the resourceVo to set
	 */
	public void setResourceVo(ResourceVo resourceVo) {
		this.resourceVo = resourceVo;
	}
	
	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId() {
		return resourceId;
	}
	
	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	/**
	 * Returns the kindVo.
	 *
	 * @return 
	 *		the kindVo to return
	 */
	public KindVo getKindVo() {
		return kindVo;
	}
	
	/**
	 * Sets the kindVo.
	 *
	 * @param kindVo 
	 *			the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	@JsonIgnore
	public void setKind(Kind kind) throws Exception{
		if(null!=kind){
			this.setKindVo(KindVo.getInstance(kind, true));
		}
	}
	
	/**
	 * Returns the kindId.
	 *
	 * @return 
	 *		the kindId to return
	 */
	public Long getKindId() {
		return kindId;
	}
	
	/**
	 * Sets the kindId.
	 *
	 * @param kindId 
	 *			the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}
	
	/**
	 * Returns the training.
	 *
	 * @return 
	 *		the training to return
	 */
	public Boolean getTraining() {
		return training;
	}
	
	/**
	 * Sets the training.
	 *
	 * @param training 
	 *			the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	/**
	 * @return the primary
	 */
	public Boolean getPrimary() {
		return primary;
	}


}
