package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * API for Incident Groups
 * 
 * @author bsteiner
 */
public interface IncidentGroupService {

	/**
	 * Delete the specified {@link IncidentGroup} and remove associated {@link Incident} objects.
	 * 
	 * @param incidentGroupId
	 * @return Id of {@link IncidentGroup} that was deleted.
	 * @throws ServiceException
	 */
	public Long deleteIncidentGroup(Long incidentGroupId) throws ServiceException;

	/**
	 * Retrieve the Information for the Incident Group Grid.
	 * @param filter
	 * @param isPrivilegedUser
	 * @return
	 * @throws ServiceException
	 */
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter, Boolean isPrivilegedUser) throws ServiceException;

	/**
	 * Save or update any changes made to an Incident Group.  This includes any additions
	 * or removal of associated Incidents to the group.
	 * 
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public IncidentGroupVo save(IncidentGroupVo vo) throws ServiceException;

	/**
	 * @param incidentGroupId
	 * @return
	 * @throws ServiceException
	 */
	public IncidentGroupVo getById(Long incidentGroupId) throws ServiceException;
 
	/**
	 * Retrieve a {@link UserGridVo} object populated with the selected {@link IncidentGroupUser} object's roles.
	 * @param userId
	 * @param groupId
	 * @return a {@link UserGridVo} object 
	 * @throws ServiceException
	 */
	public UserGridVo getUserById(Long userId, Long groupId) throws ServiceException;
	
	/**
	 * @param incidentGroupId
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<IncidentGridVo> getAssignedIncidents(Long incidentGroupId, IncidentFilter filter) throws ServiceException;
	
	/**
	 * Retrieve all incidents that are available to be added to the specified 
	 * Incident Group.  These will come from the current work area (which should only 
	 * be a standard work area) to find available incidents.
	 * 
	 * @throws ServiceException
	 */
	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long workAreaId, IncidentFilter filter) throws ServiceException;

	/**
	 * If the {@link Incident} already exists in another {@link IncidentGroup}, return false.
	 * 
	 * @param groupId
	 * @param incidentId
	 * @param questionType
	 * @return
	 * @throws ServiceException
	 */
	public Boolean addIncident(Long groupId, Long incidentId, String questionType) throws ServiceException;

	/**
	 * @param groupId
	 * @param incidentId
	 * @throws ServiceException
	 */
	public void removeIncident(Long groupId, Long incidentId) throws ServiceException;
	
	/**
	 * @param groupId
	 * @return
	 */
	public Collection<UserGridVo> getAssignedUsers(Long groupId, UserFilter filter) throws ServiceException;

	/**
	 * @param groupId
	 * @param workAreaId
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<UserGridVo> getAvailableUsers(Long groupId, Long workAreaId, UserFilter filter) throws ServiceException;

	/**
	 * @param groupId
	 * @param vos
	 * @throws ServiceException
	 */
	public void addUsers(Long groupId, Collection<UserGridVo> vos/*, Collection<SystemRoleVo> roleVos*/) throws ServiceException;

	/**
	 * @param groupId
	 * @param vos
	 * @throws ServiceException
	 */
	public void removeUsers(Long groupId,Collection<UserGridVo> vos) throws ServiceException;

	/**
	 * @param groupId
	 * @param userId
	 * @param roleVos
	 */
	public void saveUserRoles(Long groupId, Long userId, Collection<SystemRoleVo> roleVos) throws ServiceException;

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Collection<UserGroupGridVo> getAvailableUserGroups() throws ServiceException;
	
	/**
	 * 
	 * @param incidentGroupId
	 * @param userGroupId
	 * @throws ServiceException
	 */
	public void assignUserGroupUsers(Long incidentGroupId, Long userGroupId) throws ServiceException;
	
//	/**
//	 * 
//	 * @param userId
//	 * @param incidentIds
//	 * @param incidentGroupId
//	 * @throws ServiceException
//	 */
//	public void assignIncidentGroupRoles(Long userId, Collection<Integer> incidentIds, Long incidentGroupId) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param incidentIds
	 * @return
	 * @throws ServiceException
	 */
	public Boolean isUserAssignedToIncidentsInTheGroup(Long userId, Collection<Integer> incidentIds) throws ServiceException;
	
//	/**
//	 * 
//	 * @param userId
//	 * @param restrictedIncidentId
//	 * @param workAreaId
//	 * @return
//	 * @throws ServiceException
//	 */
//	public Collection<String> pointToIGRolesIfNecessary(Long userId, Long restrictedIncidentId, Long workAreaId) throws ServiceException;

	public Boolean addIncidents(Long groupId, Collection<Long> incidentIds) throws ServiceException;

}

