package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ResourceKindVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.security.AuthorityStore;

import java.util.Collection;

import org.springframework.security.annotation.Secured;


public interface WorkAreaResourceService extends TransactionService{

	/**
	 * Returns a collection of workAreaResourceGridVo's based on the
	 * criteria.
	 * 
	 * Security: Requires one of the following:
	 * 			 ROLE_CHECK_IN_DEMOB
	 * 			 ROLE_INJURY_ILLNESSS
	 * 			 ROLE_MANAGER
	 * 			 ROLE_SUPPLY_SUPERVISOR
	 * 			 ROLE_SYNC_MANAGER
	 * 			 ROLE_TIME
	 * 			(ref: GR.B-Preliminary Roles Analysis-00.01.01-Enterprise Spreadsheet)
	 * 
	 * @param filter
	 * 			the filter criteria 
	 * @return
	 * 		collection of workAreaResourceGridVo's
	 * @throws ServiceException
	 */
	@Secured("ROLE_CHECK_IN_DEMOB")
	public Collection<WorkAreaResourceGridVo> getGrid(WorkAreaResourceFilter filter) throws ServiceException;
	
	public WorkAreaResourceGridVo getGridItem(Long workAreaId, Long resourceId) throws ServiceException ;
	
	
	/**
	 * Deletes the resources.
	 * 
	 * Security: Requires one of the following:
	 * 			 ROLE_CHECK_IN_DEMOB (SITE)
	 * 			 ROLE_DATA_STEWARD (ENTERPRISE)
	 * 			 ROLE_COST
	 * 			 ROLE_TIME
	 * 			(ref: Use Cases J 5.0009 - Delete a Resource)
	 * 
	 * @param resources
	 * 			collection of ResourceVo's to delete
	 * @throws ServiceException
	 */
	@Secured({AuthorityStore.ROLE_CHECK_IN_DEMOB,AuthorityStore.ROLE_DATA_STEWARD
		,AuthorityStore.ROLE_COST, AuthorityStore.ROLE_TIME})
   public Collection<WorkAreaResourceGridVo> deleteResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException;
	
	
	/**
	 * Disables the resources.
	 * 
	 * Security: Requires one of the following:
	 * 			 ROLE_CHECK_IN_DEMOB
	 * 			 ROLE_INJURY_ILLNESSS
	 * 			 ROLE_MANAGER
	 * 			 ROLE_SUPPLY_SUPERVISOR
	 * 			 ROLE_SYNC_MANAGER
	 * 			 ROLE_TIME
	 * 			(ref: GR.B-Preliminary Roles Analysis-00.01.01-Enterprise Spreadsheet)
	 * 
	 * @param resources
	 * 			collection of ResourceVo's to disable
	 * @throws ServiceException
	 */
	@Secured({AuthorityStore.ROLE_CHECK_IN_DEMOB,AuthorityStore.ROLE_INJURY_ILLNESS
		,AuthorityStore.ROLE_MANAGER, AuthorityStore.ROLE_SUPPLY_SUPERVISOR
		,AuthorityStore.ROLE_SYNC_MANAGER,AuthorityStore.ROLE_TIME})
   public Collection<WorkAreaResourceGridVo> disableResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException;

	/**
	 * Enables the resources.
	 * 
	 * Security: Requires one of the following:
	 * 			 ROLE_CHECK_IN_DEMOB
	 * 			 ROLE_INJURY_ILLNESSS
	 * 			 ROLE_MANAGER
	 * 			 ROLE_SUPPLY_SUPERVISOR
	 * 			 ROLE_SYNC_MANAGER
	 * 			 ROLE_TIME
	 * 			(ref: GR.B-Preliminary Roles Analysis-00.01.01-Enterprise Spreadsheet)
	 * 
	 * @param resources
	 * 			collection of ResourceVo's to enable
	 * @throws ServiceException
	 */
	@Secured({"ROLE_CHECK_IN_DEMOB"})
   public Collection<WorkAreaResourceGridVo> enableResources(Collection<WorkAreaResourceGridVo> resources) throws ServiceException;

	/**
	 * Saves a new resource and attaches it to a work area.
	 * 
	 * @param workAreaId
	 * @param vo
	 * @throws ServiceException
	 */
	public ResourceVo saveWorkAreaResource(Long workAreaId, ResourceVo vo) throws ServiceException;

	public ResourceVo saveMatchWorkAreaResource(Long matchResourceId, Long workAreaId, ResourceVo vo) throws ServiceException;
	
	public void removeQualifications(Long resourceId, Collection<ResourceKindVo> resourceKindVos) throws ServiceException;
	
	public void addQualifications(Long resourceId, Collection<KindVo> kindVos) throws ServiceException;

	public void addNewResourceRoster(Long workAreaId,Long parentResourceId, ResourceVo vo) throws ServiceException;

	public void addExistingResourceRoster(Long parentResourceId, Collection<Long> resourceIds, Long childId) throws ServiceException ;
	
	public Collection<WorkAreaResourceGridVo> getAvailableRosterResources(Long workAreaId, Long parentResourceId) throws ServiceException;

	public ResourceVo getResourceAddToWorkArea(Long workAreaId,Long resourceId) throws ServiceException;
	
	public Collection<WorkAreaResourceGridVo> getUnassignedResources(WorkAreaResourceFilter filter) throws ServiceException;

	public Collection<IncidentGridVo> getWorkAreaIncidents(Long workAreaId) throws ServiceException;

	public void addResourcesToIncident(Collection<WorkAreaResourceGridVo> vos) throws ServiceException;

	public void unrosterResource(Long resourceId) throws ServiceException;

	public Long saveQualification(Long resourceId, ResourceKindVo resourceKindVo) throws ServiceException;
	
	public Long addResourceToWorkArea(Long WorkAreaId, Long resourceId) throws ServiceException;
	
	public Collection<ContractorVo> getContractorVos(ContractorFilter filter) throws ServiceException;
}
