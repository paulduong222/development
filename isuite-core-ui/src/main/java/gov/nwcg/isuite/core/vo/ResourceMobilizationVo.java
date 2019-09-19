package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ResourceMobilizationVo extends AbstractVo implements PersistableVo {
	private ResourceVo resourceVo;
	private Date startDate;
	private DateTransferVo startDateVo=new DateTransferVo();
	
	public Persistable toEntity(Persistable entity) throws Exception {
		return null;
	}

	public static ResourceMobilizationVo getInstance(ResourceMobilization entity,
														boolean cascadable,
															Persistable... persistables) throws Exception {
		ResourceMobilizationVo vo = new ResourceMobilizationVo();
		
		if(null == entity)
			throw new Exception("Unable to create ResourceMoblizationVo from null ResourceMobilization entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(null != entity.getResourceId())
				vo.setResourceVo(ResourceVo.getInstance(entity.getResource(),false));
			
			if(DateUtil.hasValue(entity.getStartDate()))
				DateTransferVo.populateDate(vo.getStartDateVo(), entity.getStartDate());
			//vo.setStartDate(entity.getStartDate());
		}
		
		return vo;	
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<ResourceMobilizationVo> getInstances(Collection<ResourceMobilization> entities,
																boolean cascadable,
																	Persistable... persistables) throws Exception {
		List<ResourceMobilizationVo> list = new ArrayList<ResourceMobilizationVo>();
		
		for(ResourceMobilization entity : entities){
			list.add(ResourceMobilizationVo.getInstance(entity,cascadable,persistables));
		}
		
		return list;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static ResourceMobilization toEntity(ResourceMobilizationVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		ResourceMobilization entity = new ResourceMobilizationImpl();
		
		if(null == vo)
			throw new Exception("Unable to create ResourceMobilization entity from null ResourceMobilizationVo.");

		entity.setId(vo.getId());
	
		if(cascadable){
			entity.setStartDate(vo.getStartDate());
			if(DateTransferVo.hasDateString(vo.getStartDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getStartDateVo());
				entity.setStartDate(dt);
			}
			
			/*
			 * When setting the resource entity, we user the resource instance
			 * from the persitables array and not the vo.getResource() instance.
			 * 
			 * In situations where a new Resource is being persisted with new resource mobilizations,
			 * we want the correct resource instance referenced from this resourcemobilization instance.
			 */
			Resource resource = (Resource)getPersistableObject(persistables,ResourceImpl.class);
			if(null != resource){
				entity.setResource(resource);
			}
		}
		
		return entity;
	}

	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static List<ResourceMobilization> toEntityList(Collection<ResourceMobilizationVo> vos,boolean cascadable,Persistable...persistables) throws Exception {
		List<ResourceMobilization> entities = new ArrayList<ResourceMobilization>();
		
		for(ResourceMobilizationVo vo : vos){
			entities.add(ResourceMobilizationVo.toEntity(vo, cascadable, persistables));
		}
		
		return entities;
	}

	public ResourceVo getResourceVo() {
		return resourceVo;
	}

	public void setResourceVo(ResourceVo resourceVo) {
		this.resourceVo = resourceVo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDateVo
	 */
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	/**
	 * @param startDateVo the startDateVo to set
	 */
	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}
}
