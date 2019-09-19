package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceKindImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.filter.impl.WorkAreaResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceKindDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.service.IncidentService;
import gov.nwcg.isuite.core.service.WorkAreaResourceService;
import gov.nwcg.isuite.core.service.WorkAreaService;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ResourceKindVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Level;

public class WorkAreaResourceServiceImpl extends BaseService implements WorkAreaResourceService {
	private WorkAreaDao workAreaDao;
	private ResourceDao resourceDao;

	public WorkAreaResourceServiceImpl(){

	}

	public WorkAreaResourceServiceImpl(WorkAreaDao workAreaDao, ResourceDao resourceDao){
		this.workAreaDao=workAreaDao;
		this.resourceDao=resourceDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#disableResources(java.util.Collection, java.lang.Boolean)
	 */
	public Collection<WorkAreaResourceGridVo> disableResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException {
		if( (null != resources) && (resources.size()>0)){
			// Build a collection of resource ids
			Collection<Long> resourceIds = new ArrayList<Long>();
			for(WorkAreaResourceGridVo vo : resources){
				resourceIds.add(vo.getPermanentResourceId());
			}

			try{
				int count=resourceDao.disableResources(resourceIds);
				if(count==0) {
					throw new ServiceException("Unable to disable resources, count returned 0.");
				} else {
					//unrosterChildren
					resourceDao.unRosterWorkAreaResourcesChildren(resourceIds);
				}
			}catch(PersistenceException pe){
				throw new ServiceException(pe);
			}

		}else{
			throw new ServiceException("Unable to disable resources with empty resources collection.");
		}
		return resources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#enableResources(java.util.Collection)
	 */
	public Collection<WorkAreaResourceGridVo> enableResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException {
		if( (null != resources) && (resources.size()>0)){
			// Build a collection of resource ids
			Collection<Long> resourceIds = new ArrayList<Long>();
			for(WorkAreaResourceGridVo vo : resources){
				resourceIds.add(vo.getPermanentResourceId());
			}

			try{
				int count=resourceDao.enableResources(resourceIds);
				if(count==0)
					throw new ServiceException("Unable to enable resources, count returned 0.");
			}catch(PersistenceException pe){
				throw new ServiceException(pe);
			}

		}else{
			throw new ServiceException("Unable to enable resources with empty resources collection.");
		}
		return resources;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#getGrid(gov.nwcg.isuite.core.filter.WorkAreaResourceFilter)
	 */
	@Override
	public Collection<WorkAreaResourceGridVo> getGrid(WorkAreaResourceFilter filter) throws ServiceException {
		Collection<WorkAreaResourceGridVo> vos = new ArrayList<WorkAreaResourceGridVo>();
		Collection<WorkAreaResourceGridVo> rtnVos=new ArrayList<WorkAreaResourceGridVo>();

		try{
			filter.setIncludeDemob(false);
			vos=workAreaDao.getResourceGrid(filter);

			super.log("Get WorkAreaResourceGrid returned count: " + vos.size(), Level.INFO);
			
			rtnVos = WorkAreaResourceGridVo.toHierarchyCollection(vos);
			
			for(WorkAreaResourceGridVo wargVo : rtnVos) {
				//TODO: Add additional criteria to determine if resource is deletable such as:
				// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
				if(null == wargVo.getIncidentName() || wargVo.getIncidentName().isEmpty()) {
					wargVo.setDeletable(true);
				} else {
					wargVo.setDeletable(false);
				}
			}			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}

		return rtnVos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#getGridItem(gov.nwcg.isuite.core.filter.WorkAreaResourceFilter)
	 */
	public WorkAreaResourceGridVo getGridItem(Long workAreaId, Long resourceId) throws ServiceException {
		
		try{
			WorkAreaResourceFilter filter = new WorkAreaResourceFilterImpl();
			filter.setWorkAreaId(workAreaId);
			filter.setWorkAreaResourceId(resourceId);
			
			Collection<WorkAreaResourceGridVo> vos = new ArrayList<WorkAreaResourceGridVo>();
			vos=workAreaDao.getResourceGrid(filter);
			
			if( (null != vos) && (vos.size()>0) ){
				WorkAreaResourceGridVo vo = (WorkAreaResourceGridVo)vos.iterator().next();
			
				return vo;
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#saveWorkAreaResource(java.lang.Long, gov.nwcg.isuite.core.vo.ResourceVo)
	 */
	public ResourceVo saveWorkAreaResource(Long workAreaId,ResourceVo vo) throws ServiceException {
		
		try{
			
			Resource resourceEntity = null;

			vo.setEnabled(Boolean.TRUE);
			
			if( (null != vo.getId()) && (vo.getId()>0)){
				//editing a resource
				resourceEntity=resourceDao.getById(vo.getId(), ResourceImpl.class);
				resourceEntity = ResourceVo.toEntity(resourceEntity,vo, true);
				resourceDao.save(resourceEntity);
				resourceDao.flushAndEvict(resourceEntity);
				resourceEntity = resourceDao.getById(resourceEntity.getId(), ResourceImpl.class);

				if(resourceEntity.getLeaderType().intValue() == 1 && null != resourceEntity.getParentResourceId()){
					// update any other primary children to 99
					workAreaDao.updateRosterChildrenLeaderType(resourceEntity.getParentResourceId(), resourceEntity.getId(), 1,99);
				}else if (resourceEntity.getLeaderType().intValue() == 2 && null != resourceEntity.getParentResourceId()){
					// update any other secondary children to 99
					workAreaDao.updateRosterChildrenLeaderType(resourceEntity.getParentResourceId(), resourceEntity.getId(), 2,99);
				}
				
				return ResourceVo.getInstance(resourceEntity, true);
				
				
				
			}else{
				//adding a new resource
				//create permanent resource
				resourceEntity = ResourceVo.toEntity(null,vo, true);
				resourceEntity.setPermanent(Boolean.TRUE);
				
				//create a copy of the permanent resource
				Resource cpyEntity = ResourceVo.toEntity(null,vo, true);
				//set the copy's permanent resource
				cpyEntity.setPermanentResource(resourceEntity);
				
				//save the copy
				resourceDao.save(cpyEntity);
				
				//add the copy to the work area
				workAreaDao.addResourceToWorkArea(workAreaId, cpyEntity.getId());
				
				resourceDao.flushAndEvict(resourceEntity);
				resourceDao.flushAndEvict(cpyEntity);
				
				Resource res = resourceDao.getById(cpyEntity.getId(), ResourceImpl.class);

				return ResourceVo.getInstance(res, true);
				
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public ResourceVo saveMatchWorkAreaResource(Long matchResourceId, Long workAreaId, ResourceVo vo) throws ServiceException {
		
		try{
			Resource matchResource = null;
			Boolean alreadyInWorkArea = false;
			
			// the matchResourceId is the identifier for the record the user selected as the match
			// try and get the resource with a permanent resource id first
			matchResource = resourceDao.getByIdInWorkArea(matchResourceId, workAreaId);
			
			if(null == matchResource){
				matchResource = resourceDao.getById(matchResourceId, ResourceImpl.class);
			}else
				alreadyInWorkArea=true;
			
			if(null != matchResource){
				// try and merge any data where the matchResource's data is null
				if(null == matchResource.getPhone() || matchResource.getPhone().length() < 1){
					// does the resourcevo have a phone value?
					if(null != vo.getPhone() && vo.getPhone().length() > 0){
						matchResource.setPhone(StringUtility.removeNonNumeric(vo.getPhone()));
					}
				}
				if(null == matchResource.getAgencyId()){
					// does the resorucevo have an agency value?
					if(null != vo.getAgencyVo() && vo.getAgencyVo().getId() > 0 ){
						matchResource.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
					}
				}
				// todo: other fields

				if(alreadyInWorkArea){
					resourceDao.save(matchResource);
					resourceDao.flushAndEvict(matchResource);
					
					Resource res = resourceDao.getById(matchResource.getId(), ResourceImpl.class);
					
					return ResourceVo.getInstance(res, true);
				}else{
					ResourceVo matchVo = ResourceVo.getInstance(matchResource, true);
					// create new one and add to workarea
					matchVo.setId(null);
					
					this.saveWorkAreaResource(workAreaId, matchVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		
		return null;
	}
	
	public Long addResourceToWorkArea(Long workAreaId, Long resourceId) throws ServiceException {
		
		try{
			// check if the resourceid is already in the workarea
			Long id = workAreaDao.getResourceIdInWorkArea(workAreaId, resourceId);
			
			if((null == id) || (id < 1)){
				
				
				// check if there is a resource with it's perm_resource_id set to resourceId
				// already in the work area
				id = workAreaDao.getPermanentResourceIdInWorkArea(workAreaId, resourceId);

				if( (null == id) || (id < 1)){
					Resource entity = resourceDao.getById(resourceId, ResourceImpl.class);

					// make a copy 
					Resource cpyEntity = this.copyResource(entity);
					cpyEntity.setPermanentResource(entity);
					
					resourceDao.save(cpyEntity);
					// add to work area
					workAreaDao.addResourceToWorkArea(workAreaId, cpyEntity.getId());

					return cpyEntity.getId();
				}else{
					return id;
				}
			}else{
				return id;
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return 0L;
	}
	
	public void removeQualifications(Long resourceId, Collection<ResourceKindVo> resourceKindVos) throws ServiceException {
		try{
			Collection<Long> ids = new ArrayList<Long>();
			
			for(ResourceKindVo vo : resourceKindVos){
				ids.add(vo.getId());
			}
			
			resourceDao.deleteQualifications(ids);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
	}
	
	public void addQualifications(Long resourceId, Collection<KindVo> kindVos) throws ServiceException {
		try{
			Resource entity = resourceDao.getById(resourceId, ResourceImpl.class);
			
			for(KindVo vo : kindVos){
				ResourceKind resourceKindEntity = new ResourceKindImpl();
				
				Kind kindEntity =new KindImpl();
				kindEntity.setId(vo.getId());
				 
				resourceKindEntity.setKind(kindEntity);
				resourceKindEntity.setResource(entity);
				resourceKindEntity.setTraining(false);
				
				entity.getResourceKinds().add(resourceKindEntity);
			}
			
			resourceDao.getHibernateSession().save(entity);
			//resourceDao.save(entity);
		}catch(Exception e){
			super.handleException(e);
		}
		
	}

	public Collection<WorkAreaResourceGridVo> getAvailableRosterResources(Long workAreaId,Long resourceId) throws ServiceException {

		try{

			Collection<Resource> entities = workAreaDao.getAvailableRosterResources(workAreaId, resourceId);		
			return WorkAreaResourceGridVo.getInstances(entities);
			
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}
	
	public void addNewResourceRoster(Long workAreaId,Long parentResourceId, ResourceVo vo) throws ServiceException {
		try{
			
			Resource parent = resourceDao.getById(parentResourceId, ResourceImpl.class);

			Resource child = ResourceVo.toEntity(null, vo, true);
			child.setEnabled(true);
			
			// save an original
			resourceDao.save(child);
			
			Resource childCopy = ResourceVo.toEntity(null, vo, true);
			childCopy.setEnabled(true);
			childCopy.setPermanentResource(child);
			childCopy.setParentResource(parent);
			
			resourceDao.save(childCopy);
			
			workAreaDao.addResourceToWorkArea(workAreaId, childCopy.getId());
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	public void addExistingResourceRoster(Long parentResourceId, Collection<Long> resourceIds, Long childId) throws ServiceException {
		try{

			Resource parent = resourceDao.getById(TypeConverter.convertToLong(parentResourceId), ResourceImpl.class);
			
			// convert to longs in case resourceIds came in as Integers
			Collection<Long> ids = new ArrayList<Long>();
			
			Iterator iter = resourceIds.iterator();
			
			while(iter.hasNext()){
				String s = String.valueOf(iter.next());
				Long l = TypeConverter.convertToLong(s);
				ids.add(l);
			}
			
			for(Long id : ids){
				Resource child = resourceDao.getById(id, ResourceImpl.class);
				child.setParentResource(parent);
				
				resourceDao.save(child);
				
			}
			
			// find the focus child
			if(null != childId && childId > 0){
				for(Long id : ids){
					if(id.compareTo(childId)==0){
						Resource child = resourceDao.getById(id, ResourceImpl.class);
						
						if(child.getLeaderType().intValue() == 1){
							// update any other primary children to 99
							workAreaDao.updateRosterChildrenLeaderType(parentResourceId, child.getId(), 1,99);
						}else if (child.getLeaderType().intValue() == 2){
							// update any other secondary children to 99
							workAreaDao.updateRosterChildrenLeaderType(parentResourceId, child.getId(), 2,99);
						}
					}
				}
			}

		}catch(Exception e){
			super.handleException(e);
		}
	}

	public ResourceVo getResourceAddToWorkArea(Long workAreaId,Long resourceId) throws ServiceException {
		
		try{
			workAreaDao.addResourceToWorkArea(workAreaId, resourceId);
			
			Resource entity = resourceDao.getById(resourceId, ResourceImpl.class);
			
			if(null!=entity)
				return ResourceVo.getInstance(entity,true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public Collection<WorkAreaResourceGridVo> getUnassignedResources(WorkAreaResourceFilter filter) throws ServiceException {
		Collection<WorkAreaResourceGridVo> vos = new ArrayList<WorkAreaResourceGridVo>();
		Collection<WorkAreaResourceGridVo> rtnVos = new ArrayList<WorkAreaResourceGridVo>();

		try{
			vos = this.workAreaDao.getUnassignedResources(filter);
			rtnVos = WorkAreaResourceGridVo.toHierarchyCollection(vos);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return rtnVos;
	}

	public Collection<IncidentGridVo> getWorkAreaIncidents(Long workAreaId) throws ServiceException {
		Collection<IncidentGridVo> vos = new ArrayList<IncidentGridVo>();

		try{
			IncidentFilter filter = new IncidentFilterImpl();
			filter.setWorkAreaId(workAreaId);
			
			vos=workAreaDao.getWorkAreaIncidents(filter, ((UserSessionVo)super.context.getBean("userSessionVo")).getUserId());
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vos;
	}

	public void addResourcesToIncident(Collection<WorkAreaResourceGridVo> vos) throws ServiceException {
		
		try{
			Collection<Resource> resourceEntities= new ArrayList<Resource>();

			if(null == vos || vos.size() < 1){
				throw new Exception("No resources selected to add to incident");
			}
			
			for(WorkAreaResourceGridVo vo : vos){
				Resource waResource = resourceDao.getById(vo.getResourceId(), ResourceImpl.class);
				Resource resEntity= this.copyResource(waResource);

				this.buildIncidentResource(resEntity, vo.getIncidentId());
				
				/*
				 * Per c.r
				 *  When adding wa resources to an incident, we only
				 *  add the included children if available.  Any orphaned
				 *  wa resource children will get unrostered from it
				 *  parent wa resource. 
				 */
				if(CollectionUtility.hasValue(vo.getChildren())){
					for(WorkAreaResourceGridVo childVo : vo.getChildren()){
						Resource child = resourceDao.getById(childVo.getResourceId(), ResourceImpl.class);
						
						Resource resChildEntity= this.copyResource(child);
						
						this.buildIncidentResource(resChildEntity, vo.getIncidentId());
						resChildEntity.setParentResource(resEntity);
						resEntity.getChildResources().add(resChildEntity);
					}
					resourceEntities.add(resEntity);
				}else
					resourceEntities.add(resEntity);
				
				/*
				if(null != waResource.getChildResources() && waResource.getChildResources().size() > 0){
					for(Resource child : waResource.getChildResources()){
						Resource resChildEntity= this.copyResource(child);
						
						this.buildIncidentResource(resChildEntity, vo.getIncidentId());
						resChildEntity.setParentResource(resEntity);
						resEntity.getChildResources().add(resChildEntity);
						resourceEntities.add(resEntity);
					}
				}else{
					resourceEntities.add(resEntity);
				}
				*/
				
			}
			
			resourceDao.saveAll(resourceEntities);
			
			/*
			 * update any top level orphaned work area resource records 
			 * as unrostered
			 */
			for(WorkAreaResourceGridVo vo : vos){
				if(LongUtility.hasValue(vo.getParentResourceId())){
					Resource orphan = resourceDao.getById(vo.getResourceId(), ResourceImpl.class);
					if(null != orphan){
						orphan.setParentResource(null);
						resourceDao.save(orphan);
					}
				}
			}
			
			/*
			 * update any other orpaned records not passed in
			 */
			for(WorkAreaResourceGridVo vo : vos){
				resourceDao.unrosterUnassignedOrphanedChildren(vo.getResourceId());
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
	}

	private void buildIncidentResource(Resource resEntity, Long incidentId) {
		Incident incEntity = new IncidentImpl();
		incEntity.setId(incidentId);
		
		IncidentResource irEntity = new IncidentResourceImpl();
		irEntity.setIncident(incEntity);
		irEntity.setResource(resEntity);
		irEntity.setNameAtIncident(resEntity.getResourceName());

		// set status to filled
		WorkPeriod wpEntity = new WorkPeriodImpl();
		Assignment assEntity = new AssignmentImpl();
		assEntity.setAssignmentStatus(AssignmentStatusTypeEnum.F);
		Kind kind = new KindImpl();
		
		for(ResourceKind rk : resEntity.getResourceKinds()){
			if(rk.getPrimary()){
				kind.setId(rk.getKind().getId());
			}
		}
		assEntity.setKind(kind);
		assEntity.setTraining(false);
		
		AssignmentTime atEntity = new AssignmentTimeImpl();
		atEntity.setEmploymentType(resEntity.getEmploymentType());
		atEntity.setAssignment(assEntity);
		assEntity.setAssignmentTime(atEntity);
		
		ContractorPaymentInfo cpiEntity = new ContractorPaymentInfoImpl();
		cpiEntity.setContractor(resEntity.getContractor());
		cpiEntity.setContractorAgreement(resEntity.getContractorAgreement());
		cpiEntity.setAssignmentTime(atEntity);
		atEntity.setContractorPaymentInfo(cpiEntity);
		
		wpEntity.setAssignments(new ArrayList<Assignment>());
		wpEntity.getAssignments().add(assEntity);
		wpEntity.setDMReAssignable(false);
		wpEntity.setDMCheckoutFormPrinted(false);
		wpEntity.setDMPlanningDispatchNotified(false);
		wpEntity.setDMReleaseDispatchNotified(false);
		wpEntity.setDMReleaseDispatchNotified(false);
		wpEntity.setDMRestOvernight(false);
		
		wpEntity.setIncidentResource(irEntity);
		irEntity.setWorkPeriod(wpEntity);
		
		resEntity.getIncidentResources().add(irEntity);
	}
	
	public void unrosterResource(Long resourceId) throws ServiceException {
		
		try{

			ResourceDao resourceDao = (ResourceDao)super.context.getBean("resourceDao");
			resourceDao.unrosterResource(resourceId);

		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	public IncidentVo addNewIncidentToWorkArea(Long workAreaId, IncidentVo incidentVo) throws ServiceException {
		
		try {
			
			IncidentService incidentService = (IncidentService)super.context.getBean("incidentService");
			incidentVo = incidentService.save(incidentVo);
			
			WorkAreaService workAreaService = (WorkAreaService)super.context.getBean("workAreaService");
			Collection<Integer> incidentIds = new ArrayList<Integer>();
			incidentIds.add(Integer.parseInt(incidentVo.getId().toString()));
			workAreaService.addIncidentsToWorkArea(incidentIds, workAreaId);
			
		} catch(Exception e) {
			super.handleException(e);
			return null;
		}
		
		return incidentVo;
	}

	/**
	 * Makes copy of resource if original
	 * 
	 * @param resource
	 * @return Resource
	 * @throws Exception
	 */
	private Resource copyResource(Resource resource) throws Exception {
		Resource copy = new ResourceImpl();
		copy.setAgency(resource.getAgency());
		copy.setContracted(resource.isContracted());
		copy.setPerson(resource.isPerson());
		copy.setEnabled(resource.isEnabled());
		copy.setPermanent(resource.isPermanent());
		copy.setLeader(resource.isLeader());
		copy.setContactName(resource.getContactName());
		copy.setEmail(resource.getEmail());
		copy.setFirstName(resource.getFirstName());
		copy.setLastName(resource.getLastName());
		copy.setResourceName(resource.getResourceName());
		copy.setOrganization(resource.getOrganization());
		copy.setPrimaryDispatchCenter(resource.getPrimaryDispatchCenter());
		copy.setNameOnPictureId(resource.getNameOnPictureId());
		copy.setOther1(resource.getOther1());
		copy.setOther2(resource.getOther2());
		copy.setOther3(resource.getOther3());
		copy.setPermanentResourceId(resource.getId());
		copy.setPermanentResource(resource);
		copy.setParentResourceId(resource.getParentResourceId());
		copy.setPhone(resource.getPhone());
		copy.setResourceClassification(resource.getResourceClassification());
		copy.setResourceStatus(resource.getResourceStatus());
		copy.setPermanent(true);
		copy.setLeaderType(resource.getLeaderType());
		copy.setEmploymentType(resource.getEmploymentType());
		copy.setContractor(resource.getContractor());
		copy.setContractorAgreement(resource.getContractorAgreement());
		
		if(null != resource.getResourceKinds()){
			Collection<ResourceKindVo> rkvos = new ArrayList<ResourceKindVo>();
			
			for(ResourceKind resKind : resource.getResourceKinds()){
				ResourceKindVo rkvo = new ResourceKindVo();
				KindVo kindVo = new KindVo();
				kindVo.setId(resKind.getKind().getId());
				rkvo.setKindVo(kindVo);
				rkvo.setPrimary(resKind.getPrimary());
				rkvo.setTraining(resKind.getTraining());
				
				rkvos.add(rkvo);
			}
			
    		Collection<ResourceKind> rks = ResourceKindVo.toEntityList(rkvos, true, copy);
    		for(ResourceKind rk : rks){
    			copy.addResourceKind(rk);
    		}
		}
		return copy;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#saveQualification(java.lang.Long, gov.nwcg.isuite.core.vo.ResourceKindVo)
	 */
	public Long saveQualification(Long resourceId, ResourceKindVo resourceKindVo) throws ServiceException {
		try{
			Resource entity = resourceDao.getById(resourceId, ResourceImpl.class);
			ResourceKind rkEntity = null;
			
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Resource[id="+resourceId+"]");
			
			//check for duplicate item codes
			for(ResourceKind rk : entity.getResourceKinds()){
				if(rk.getId().compareTo(resourceKindVo.getId())!=0){
					if((rk.getResource().getId().compareTo(resourceKindVo.getResourceVo().getId()) == 0) && (rk.getKind().getId().compareTo(resourceKindVo.getKindVo().getId()) == 0)) {
						super.handleException(ErrorEnum.DUPLICATE_ITEM_CODE);
					}
				}
			}
			
			rkEntity = ResourceKindVo.toEntity(resourceKindVo, true,entity);
			
			if(null != rkEntity)
			{
				ResourceKindDao rkDao = (ResourceKindDao)context.getBean("resourceKindDao");
				rkDao.save(rkEntity);
			}
			return rkEntity.getId();
			
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaResourceService#deleteResources(java.util.Collection)
	 */
	public Collection<WorkAreaResourceGridVo> deleteResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException {
		if( (null != resources) && (resources.size()>0)){
			// Build a collection of resource ids
			Collection<Long> resourceIds = new ArrayList<Long>();
			for(WorkAreaResourceGridVo vo : resources){
				resourceIds.add(vo.getPermanentResourceId());
			}

			try{
				int count=resourceDao.deleteWorkAreaResources(resourceIds);
				if(count==0) {
					throw new ServiceException("Unable to delete resources, count returned 0.");
				} else {
					//unrosterChildren) 
					resourceDao.unRosterWorkAreaResourcesChildren(resourceIds);
				}
			}catch(PersistenceException pe){
				throw new ServiceException(pe);
			}

		}else{
			throw new ServiceException("Unable to delete resources with empty resources collection.");
		}
		return resources;
	}	
	
	public Collection<ContractorVo> getContractorVos(ContractorFilter filter) throws ServiceException {

		if (filter.getWorkAreaId() == null ) {
			throw new ServiceException("WorkAreaId is required.");
		}
		
		try {
			ContractorDao dao = (ContractorDao)context.getBean("contractorDao");
			
			Collection<ContractorVo> vos = new ArrayList<ContractorVo>();
			
			vos = dao.getAll(filter);
				
			return vos;
			
		} catch(Exception ex) {
			super.handleException(ex);
		}
		return null;
	}
}
