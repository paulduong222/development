package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceCheckboxFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceGetFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.ReportSelectVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCheckInDemobVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCommonVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceInWorkVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodQuestionValueVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.security.AuthorityStore;

import java.util.Collection;

import org.springframework.security.annotation.Secured;


public interface IncidentResourceService extends TransactionService{

	/**
	 * Returns a collection of incidentResourceGridVo's based on the
	 * filter criteria.
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
	 * 		collection of incidentresourceGridVo's
	 * @throws ServiceException
	 */
	@Secured("ROLE_CHECK_IN_DEMOB")
	public Collection<IncidentResourceGridVo> getGrid(IncidentResourceFilter filter) throws ServiceException;
	
	/**
	 * Returns a collection of incidentResourceGridVo's based on the
	 * filter criteria.
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
	 * 		collection of incidentresourceGridVo's
	 * @throws ServiceException
	 */
	@Secured("ROLE_CHECK_IN_DEMOB")
	public DialogueVo getGrid2(DialogueVo dialogVo, IncidentResourceFilter incidentResourceFilter, Collection<String> sortFields) throws ServiceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public IncidentResourceGridVo getGridItem(IncidentResourceFilter filter) throws ServiceException;
	
	/**
	 * Returns an incidentResourceCommonVo for the supplied resourceId.
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
	 * @param resourceId
	 * 			the resourceId to filter by
	 * @param assignmentId
	 * 			the assignmentId to filter by
	 * @return
	 * 		incidentResourceCommonVo for the supplied resourceId
	 * @throws ServiceException
	 */
	@Secured({"ROLE_CHECK_IN_DEMOB"})
	public IncidentResourceCommonVo getIncidentResourceCommonData(Long resourceId,Long assignmentId) throws ServiceException;
	   

	/**
	 * Returns an incidentResourceCheckInDebmoVo for the supplied incidentResourceId.
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
	 * @param incidentResourceId
	 * 		the incidentResourceId to filter by
	 * @return
	 * 		IncidentResourceCheckInDemobVo
	 * @throws ServiceException
	 */
	@Secured({"ROLE_CHECK_IN_DEMOB"})
	public IncidentResourceCheckInDemobVo getIncidentResourceCheckInDemobData(Long incidentResourceId) throws ServiceException;

	/**
	 * Generates the AllIncidentResourcesReport.
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
	 * 			the report filter criteria
	 * @return
	 * 		the path to the report
	 * @throws ServiceException
	 */
	@Secured({"ROLE_CHECK_IN_DEMOB"})
	public String generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter) throws ServiceException;

	
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
	 * @param removeParentAssociations
	 * 			flag to indicate whether or not also remove parent associations
	 * @throws ServiceException
	 */
	@Secured({AuthorityStore.ROLE_CHECK_IN_DEMOB,AuthorityStore.ROLE_DATA_STEWARD
		,AuthorityStore.ROLE_COST, AuthorityStore.ROLE_TIME})
   public Collection<IncidentResourceGridVo> deleteResources(Collection<IncidentResourceGridVo> resources, boolean removeParentAssociations) throws ServiceException;

	
	/**
	 * @param dialogueVo
	 * @param resources
	 * @param removeParentAssociations
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteResourcesDialogueVo(DialogueVo dialogueVo, Collection<IncidentResourceGridVo> resources, boolean removeParentAssociations) throws ServiceException;
	
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
	 * @param removeParentAssociations
	 * 			flag to indicate whether or not also remove parent associations
	 * @throws ServiceException
	 */
	@Secured({AuthorityStore.ROLE_CHECK_IN_DEMOB,AuthorityStore.ROLE_INJURY_ILLNESS
		,AuthorityStore.ROLE_MANAGER, AuthorityStore.ROLE_SUPPLY_SUPERVISOR
		,AuthorityStore.ROLE_SYNC_MANAGER,AuthorityStore.ROLE_TIME})
   public void disableResources(Collection<Integer> resourceIds, Boolean removeParentAssociations) throws ServiceException;

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
   public void enableResources(Collection<Integer> resourceIds) throws ServiceException;

	/**
	 * @param vo
	 * @throws ServiceException
	 */
	public void saveIncidentResource(IncidentResourceInWorkVo vo) throws ServiceException;
   
   
	/**
	 * @param vos
	 * @throws ServiceException
	 */
	public void saveIncidentResources(Collection<IncidentResourceInWorkVo> vos) throws ServiceException;
   
	/**
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public IncidentResourceVo getById(Long id) throws ServiceException;
	
	public IncidentResourceVo getById(Long id,Long assignmentId) throws ServiceException;
	
	/**
	 * 
	 * @param vo
	 * @param addResourceToWorkArea
	 * @param workAreaId
	 * @return
	 * @throws ServiceException
	 */
    public IncidentResourceVo save(IncidentResourceVo vo, Boolean propagateToChildren,Boolean addResourceToWorkArea, Long workAreaId) throws ServiceException;

	public DialogueVo save2(IncidentResourceVo vo, Boolean propagateToChildren, Boolean addResourceToWorkArea, Long workAreaId, DialogueVo dialogueVo) throws ServiceException;
    
	public void addNewResourceRoster(Long workAreaId,Long parentResourceId, ResourceVo vo) throws ServiceException;

	public Collection<IncidentResourceGridVo> getAvailableIncidentResourcesForRoster(IncidentResourceFilter filter) throws ServiceException;

	public Collection<IncidentResourceGridVo> getAvailableWorkAreaResourcesForRoster(IncidentResourceFilter filter) throws ServiceException;

	public void rosterIncidentResources(Long parentResourceId, Collection<Integer> childIds) throws ServiceException;
	
	public void rosterWorkAreaResources(Long parentResourceId, Long incidentId, Collection<Integer> childIds) throws ServiceException;

	public IncidentResourceVo saveAndRosterNewResource(Long parentResourceId, IncidentResourceVo vo) throws ServiceException;
	
	public Collection<IncidentResourceGridVo> getRosterResourceItem(Long workAreaId, Long parentResourceId) throws ServiceException;
	
	public Collection<WorkPeriodQuestionValueVo> getDefaultIncidentQuestions(Long incidentId) throws ServiceException;

	public void unrosterResources(Collection<Integer> ids) throws ServiceException;
	
	public void reassignResource(Long assignmentId, Long targetIncidentId, KindVo kindVo, String requestNumber) throws ServiceException;

	public Collection<IncidentAccountCodeVo> getIncidentAccountCodes(Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public Collection<ContractorGridVo> getContractors(ContractorFilter filter) throws ServiceException;

	public Collection<IncidentResourceGridVo> checkRequestNumber(Long workAreaId, IncidentResourceVo vo) throws ServiceException;

	public Collection<ContractorVo> getContractorVos(ContractorFilter filter) throws ServiceException ;

	public Collection<IncidentResourceGridVo> getStrikeTeams(Long workAreaId,Long incidentId, Long incidentGroupId) throws ServiceException ;
	
	public IncidentResourceVo reassignResourceOther(IncidentResourceVo vo) throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @return {@link Collection} of {@link IncidentResourceVo} objects.
	 * @throws ServiceException
	 */
	public Collection<IncidentResourceVo> getByIncidentId(Long incidentId) throws ServiceException;
	
	public ResourceVo getParentLeaderResource(Long parentResourceId, String leaderType) throws ServiceException;
	
	public String checkUniqueVinName(String vinName, Long assignmentTimeId) throws ServiceException ;

	public DialogueVo testDialogue(DialogueVo dvo) throws ServiceException;

	/**
	 * @param employmentType
	 * @param incidentResourceId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo propagateCrewEmploymentType(String employmentType,Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param incidentResourceId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo propagateCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param incidentResourceId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo clearAllCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo reassignResource2(Long assignmentId, Long targetIncidentId, KindVo kindVo, String requestNumber, DialogueVo dialogueVo) throws ServiceException;

	public Collection<IncidentResourceVo> getAllByFilter(Collection<IncidentResourceGetFilter> filter) throws ServiceException ;
	
}
