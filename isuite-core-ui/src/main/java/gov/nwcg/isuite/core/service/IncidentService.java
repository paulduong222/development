package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * Service layer area used for accessing incident functionality. 
 * 
 * @author bsteiner
 */
public interface IncidentService {

	/**
	 * Retrieve Incident data
	 * 
	 * @param id
	 * @param dummyVal - Just a throw-away 
	 * @return
	 * @throws ServiceException
	 */
	public IncidentVo getById(Long id) throws ServiceException;

	/**
	 * Retrieves users assigned to a restricted incident.
	 * 
	 * @param restrictedIncidentId
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<RestrictedIncidentUserVo> getRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter) throws ServiceException;

	/**
	 * Save the incident data held in the incidentVo
	 * 
	 * @param incidentVo
	 * @throws ServiceException
	 */
	public IncidentVo save(IncidentVo incidentVo) throws ServiceException;

	/**
	 * Delete an incident based on the provided id.
	 * 
	 * @param incidentId the id of the incident to delete.
	 * @throws ServiceException
	 */
	public void deleteIncident(Long incidentId) throws ServiceException;

	/**
	 * A read-only Collection of Incidents for display on the grid.
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<IncidentGridVo> getGrid(IncidentFilter filter) throws ServiceException;

	/**
	 * @param incidentId
	 * @return
	 * @throws ServiceException
	 */
	public IncidentGridVo getIncidentGridVo(Long incidentId) throws ServiceException ;

	/**
	 * Restrict a specific incident based upon the incident Id passed in.
	 * 
	 * @param incidentId
	 * 
	 * @throws ServiceException
	 */
	public void restrictIncident(Long incidentId) throws ServiceException;

	/**
	 * Return a picklist of incidents.
	 * @param filter
	 * 
	 * @return {@link Collection} of {@link IncidentPicklistVo}s.
	 * @throws ServiceException
	 */
	public Collection<IncidentPicklistVo> getPicklist(IncidentFilter filter) throws ServiceException;

	/**
	 * Unrestrict an incident.  This will set the restricted flag on incident to false.  It will also need to 
	 * retrieve ALL {@link RestrictedIncidentUser} objects tied to this incident that have a null accessEndDate 
	 * and set their accessEndDate.
	 * 
	 * @param incidentId
	 */
	public void unrestrictIncident(Long incidentId) throws ServiceException;

	public void deleteIncidentAccountCode(Long incidentAccountCodeId) throws ServiceException;

	public RestrictedIncidentUserVo getRestrictedIncidentUserVo(Long riuId) throws ServiceException;

	/**
	 * Removes a restricted user from an incident.
	 * 
	 * @param vo
	 * 			the restrictedIncidentUserVo to remove
	 * @throws ServiceException
	 */
	public void removeRestrictedUser(RestrictedIncidentUserVo vo) throws ServiceException;

	/**
	 * Removes restricted users from an incident.
	 * 
	 * @param vos
	 * 			collection of restrictedIncidentUserVo's to remove
	 * @throws ServiceException
	 */
	public void removeRestrictedUsers(Collection<RestrictedIncidentUserVo> vos) throws ServiceException;

	/**
	 * Adds restricted users to an incident.
	 * 
	 * @param incidentId
	 * 			the id of the incident
	 * @param vos
	 * 			collection of restrictedIncidentUserVo's to add
	 * @return {@link User} as {@link UserVo} that already belongs to this {@link Incident}
	 * @throws ServiceException
	 */
	public UserVo addRestrictedUsers(Long incidentId,Collection<RestrictedIncidentUserVo> vos) throws ServiceException;

	/**
	 * Adds restricted users from a user group to an incident.
	 * 
	 * @param incidentId
	 * 			the id of the incident
	 * @param userGroupIds
	 * 			collection of userGroupId's to add users from
	 * @throws ServiceException
	 */
	public void addRestrictedUserGroupUsers(Long incidentId,Collection<UserGroupPicklistVo> vos) throws ServiceException;

	/**
	 * Saves a restricted user's roles.
	 * 
	 * @param restrictedIncidentUserId
	 * 			the id of the restricted incident user
	 * @param vos
	 * 			collection of roleVos to save for the restrictedIncidentUser
	 * @return
	 * 		the updated restricted incident user vo
	 * @throws ServiceException
	 */
	public RestrictedIncidentUserVo saveRestrictedUserRoles(Long restrictedIncidentUserId,Collection<SystemRoleVo> vos) throws ServiceException;   

	/**
	 * @param vo
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	public AccountCodeVo getAccountCode(AgencyVo vo, String code) throws ServiceException;

	/**
	 * @param vo
	 * @throws ServiceException
	 */
	public void removeIncidentAccountCode(IncidentAccountCodeVo vo) throws ServiceException;

	/**
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public IncidentAccountCodeVo getIncidentAccountCode(Long id) throws ServiceException;

	/**
	 * @param incidentId
	 * @return
	 * @throws ServiceException
	 */
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodes(Long incidentId, IncidentAccountCodeFilter filter) throws ServiceException;

	public IncidentAccountCodeVo saveIncidentAccountCode(Long incidentId, IncidentAccountCodeVo vo) throws ServiceException;

	public Boolean isAccountFireCodeInUse(Long incidentId, Long agencyId, String code ) throws ServiceException;

	public Boolean isAccountCodeAgencyInUse(Long incidentId, Long agencyId, String code ) throws ServiceException;

	public Boolean isAccountCodeAgencyInUseOther(Long excludeIncidentId, Long agencyId, String code ) throws ServiceException;
	
}
