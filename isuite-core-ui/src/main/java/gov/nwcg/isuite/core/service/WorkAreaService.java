package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.filter.SharedWorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaIncidentsVo;
import gov.nwcg.isuite.core.vo.WorkAreaInfoVo;
import gov.nwcg.isuite.core.vo.WorkAreaPicklistVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaUserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * API for e-ISuite Work Area functionality.  This provides service into the 
 * Work Areas which include management of resources and incidents that are to be 
 * displayable within areas of the system.
 * 
 * @author bsteiner
 */
public interface WorkAreaService extends TransactionService {
   
	/**
	 * Retrieves all organizations that the current user is tied to.
	 * 
	 * @param userId
	 * @return {@link Collection} of {@link OrganizationPicklistVo} objects.
	 * @throws ServiceException
	 */
	public Collection<OrganizationPicklistVo> getAllOrganizationsByUserId(Long userId) throws ServiceException; 
	
	public Collection<OrganizationVo> getAssociatedUserOrganizations(Long userId) throws ServiceException;
	
	/**
    * Get the Manage Work Area grid populated based on the provided filter set.
    * 
    * @param filter {@link WorkAreaFilter}
    * @return {@link Collection} of {@link WorkAreaGridVo} objects.
    * @throws ServiceException
    */
   public Collection<WorkAreaGridVo> getManageWorkAreaGrid(WorkAreaFilter filter) throws ServiceException;
   
   /**
    * Query to get the Work Area grid populated based on the provided filter set.
    * 
    * @param filter {@link WorkAreaFilter}
    * @return {@link Collection} of {@link WorkAreaGridVo} objects.
    * @throws ServiceException
    */
   public Collection<WorkAreaPicklistVo> getPicklist(WorkAreaFilter filter) throws ServiceException;

   /**
    * Save the Work Area information.  
    * 
    * <b>Important Note!</b>
    * @param vo {@link WorkAreaVo} containing the data to be saved.
    * @throws ServiceException
    */
   public WorkAreaVo save(WorkAreaVo vo) throws ServiceException;

   /**
    * Retrieve a Work Area by its ID.  This will also be called when copying the 
    * specified work area for the Copy Work Area screen but in this specific case, 
    * the screen will only display the selected organization filter options.  
    * 
    * The <b>work area id</b> will be null or 0 when creating a new work area. 
    * 
    * @param workAreaid
    * @param copy Is this a work area to copy
    * @return populated {@link WorkAreaVo}
    * @throws ServiceException
    */
   public WorkAreaInfoVo getWorkAreaInfo(Long workAreaId, Boolean copy) throws ServiceException;
   
   public WorkAreaVo getById(Long workAreaId) throws ServiceException;

   /**
    * Adds all the users in the user group to the work area
    *  
    * @param workAreaId
    * @param userGroupId
    * @throws ServiceException
    */
   public void addUserGroupToWorkArea(Long workAreaId, Long userGroupId) throws ServiceException;
   
   /**
    * Retrieve Incidents that are associated to the specified Work Area.
    * 
    * @param filter {@link IncidentFilter} with work area id populated at a minimum.
    * @return {@link WorkAreaIncidentsVo}
    * @throws ServiceException
    */
   public Collection<IncidentGridVo> getWorkAreaIncidents(IncidentFilter filter) 
      throws ServiceException;
   
   /**
    * Return only the needed data for the WorkAreaIncidentSelector.incidentSelector component.
    * @param workAreaId
    * @return {@link Collection} of {@link IncidentSelectorVo} objects.
    * @throws ServiceException
    */
   public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(Long workAreaId) throws ServiceException;
   
   /**
    * Retrieve Incidents Not already associated to a Work Area.  This would include 
    * incidents tied to organizations the user has access to.  It should 
    * not include incidents that are restricted which a user does not have access 
    * granted to.
    * 
    * @param filter {@link IncidentFilter} with work area id populated at a minimum.
    * @return {@link Collection} of {@link IncidentGridVo} objects.
    * @throws ServiceException
    */
   public Collection<IncidentGridVo> getAvailableWorkAreaIncidents(IncidentFilter filter) 
      throws ServiceException;
   
   /**
    * Add specified incidents to a work area.
    * 
    * @param incidentIds {@link Collection} of Incident ids to add to the work area.
    * @param workAreaId the id of the work area to add the incidents to.
    * @throws ServiceException
    */
   public void addIncidentsToWorkArea(Collection<Integer> incidentIds, Long workAreaId) throws ServiceException;
   
   /**
    * 
    * @param incidentId
    * @param workAreaIds
    * @throws ServiceException
    */
   public void removeIncidentsFromWorkArea(Collection<Integer> incidentIds, Long workAreaId) throws ServiceException;
   
   /**
    * Retrieve a list of Resources associated to the Work Area either directly or
    * by association via an Incident.
    * 
    * @param filter {@link ResourceFilter} with at least the work area id specified.
    * @return {@link Collection} of {@link ResourceGridVo} objects.
    * @throws ServiceException
    */
   public Collection<WorkAreaResourceGridVo> getWorkAreaResources(WorkAreaResourcesFilter filter) throws ServiceException;
   
   /**
    * Retrieve a list of Resources <b>NOT</b> associated to the Work Area <b>directly</b>.
    * Yes, indeed, this will include Resources already tied through the incident 
    * but not directly as they may wish to tie the Resource directly in case the 
    * Incident tie is removed.
    * 
    * @param filter {@link ResourceFilter} with at least the work area id specified.
    * @return {@link Collection} of {@link ResourceGridVo} objects.
    * @throws ServiceException
    */
   public Collection<WorkAreaResourceGridVo> getAvailableWorkAreaResources(WorkAreaResourcesFilter filter) throws ServiceException;
   
   /**
    * Add specified Resources to a Work Area.
    * 
    * @param resourceIds {@link Collection} of Resource ids to add to the work area.
    * @param workAreaId the id of the work area to add the resources to.
    * @throws ServiceException
    */
   public void addResourcesToWorkArea(Collection<Integer> resourceIds, Long workAreaId) throws ServiceException;
  
   /**
    * 
    * @param resourceIds
    * @param workAreaId
    * @throws ServiceException
    */
   public void removeResourcesFromWorkArea(Collection<Integer> resourceIds, Long workAreaId) throws ServiceException;
   
   /**
    * Retrieve users that are associated to a Shared Work Area.
    * 
    * @param filter {@link SharedWorkAreaFilter} with at least a Work Area Id.
    * @param workAreaId
    * @return {@link Collection} of {@link UserGridVo}s
    * @throws ServiceException
    */
   public Collection<UserGridVo> getUsersFromSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) 
      throws ServiceException;
   
   /**
    * Remove users associated to a shared work area.
    * 
    * @param workAreaUserIds ids of {@link WorkAreaUser}s to be deleted 
    * @throws ServiceException
    */
   public void removeUsersFromSharedWorkArea(Collection<Integer> workAreaUserIds, Long workAreaId) 
      throws ServiceException;
   
   /**
    * Remove a user's Work Area.  There are two situations that need to be 
    * accounted for here:
    * <ul>
    *    <li>A user is removing their own custom work area.  This would remove the 
    *    actual Work Area from the system.</li>
    *    <li>A user is removing their association to a custom work area someone 
    *    has shared to them.  This would not remove the actual work area.  Just 
    *    their association to it.</li>
    * </ul>
    *   
    * @param workAreaId id for the Work Area to be removed/disassociated.
    * @param userId id for the User to remove/disassociate the Work Area from.
    * @return the {@link WorkArea} id of the {@link WorkArea} that was deleted.
    * @throws ServiceException
    */
   public Long removeCustomWorkArea(Long workAreaId, Long userId) throws ServiceException;
   
   /**
    * The {@link UserGridVo} should have the Work Area ID as well as the user's 
    * Roles that in this case are the roles they have access to for this shared 
    * work area.
    * 
    * @param userVo populated {@link UserGridVo} with changes for the User's Work Area Roles.
    * @throws ServiceException
    */
   public void updateSharedWorkAreaUserRoles(UserGridVo userVo) throws ServiceException;
   
   /**
    * Retrieve a {@link Collection} of Users who are not associated to this Work Area.
    * This should be all users.
    * 
    * @param filter {@link SharedWorkArearFilter}
    * @param workAreaId Work Area Id.
    * @return {@link Collection} of {@link UserGridVo}
    * @throws ServiceException
    */
   public Collection<UserGridVo> getUsersAvailableForSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) throws ServiceException;
   
   /**
    * Associates specified Users to the Work Area.  This will also handle the 
    * User's roles that are provided within the UserGridVo.  Those must be set 
    * into the WorkAreaUserRoles.
    *  
    * @param userVos {@link Collection} of {@link UserGridVo}s.
    * @param workAreaId Work Area Id.
    * @throws ServiceException
    */
   public void addUsersToSharedWorkArea(Collection<UserGridVo> userVos, Collection<SystemRoleVo> roleVos, Long workAreaId) throws ServiceException;
   
   /**
    * Retrieve a {@link Collection} of all the {@link WorkAreaPicklistVo} that a user has access to.  This list will 
    * then be placed in a pickList so the user can switch betwixt their work areas.
    * @param userId
    * @return Collection of {@link WorkAreaPicklistVo}
    * @throws ServiceException
    */
   public Collection<WorkAreaPicklistVo> getWorkAreaPickListByUserId(Long userId) throws ServiceException;

   public WorkAreaUserVo getWorkAreaUserById(Long workAreaUserId) throws ServiceException;

   public void saveWorkAreaUserRoles(WorkAreaUserVo vo) throws ServiceException;

   public WorkAreaVo getCopyById(Long workAreaId) throws ServiceException;

   public WorkAreaGridVo getManageWorkAreaGridItem(Long userId, Long workAreaId) throws ServiceException ;
	

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveWorkArea(WorkAreaVo vo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getWorkAreaIncidentSelectorVos(IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getUserWorkAreaVos(WorkAreaFilter filter, DialogueVo dialogueVo) throws ServiceException ;
	
}
