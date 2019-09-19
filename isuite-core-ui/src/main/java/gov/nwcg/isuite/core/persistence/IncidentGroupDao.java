package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

/**
 * @author bsteiner
 *
 */
public interface IncidentGroupDao extends TransactionSupport,CrudDao<IncidentGroup> {

	/**
	 * Retrieve the Information for the Incident Group Grid.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter) throws PersistenceException;

	/**
	 * Retrieve the Information for the Incident Group Grid. 
	 * 
	 * Used with old Incidents > Incident Groups Setup view
	 * 
	 * @param filter
	 * @param isPrivilegedUser
	 * @return
	 * @throws PersistenceException
	 */
	@Deprecated
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter, Boolean isPrivilegedUser) throws PersistenceException;

	/**
	 * @param groupId
	 * @param filter
	 * @param privilegedUser
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentGridVo> getAssignedIncidents(Long groupId, IncidentFilter filter, Boolean privilegedUser) throws PersistenceException ;

	/**
	 * Retrieve all Incidents that would be available for addition to an Incident Group.
	 * 
	 * @param incidentGroupId
	 * @param workAreaId
	 * @param filter
	 * @param loggedInUserId
	 * @param privilegedUser
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long workAreaId, 
			IncidentFilter filter, Long loggedInUserId, Boolean privilegedUser) throws PersistenceException;

	/**
	 * @param groupName
	 * @return
	 * @throws PersistenceException
	 */
	public IncidentGroup getByGroupName(String groupName, Long workAreaId) throws PersistenceException;

	/**
	 * Get the record by its ID.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public IncidentGroup getById(Long id) throws PersistenceException;

	/**
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws PersistenceException
	 */
	public Long getIncidentGroupUserId(Long userId, Long groupId) throws PersistenceException;

	public Collection<UserGridVo> getAssignedUsers(Long incidentGroupId, UserFilter filter) throws PersistenceException;

	public Collection<UserGridVo> getAvailableUsers(Long workAreaId, Long incidentGroupId, UserFilter filter) throws PersistenceException;

	/**
	 * 
	 * @param incidentId
	 * @return
	 * @throws PersistenceException
	 */
	public Long getIncidentGroupsIncidentId(Long incidentId) throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<UserGroupGridVo> getAvailableUserGroups() throws PersistenceException;

	//   /**
	//    * @param filter
	//    * @return
	//    * @throws PersistenceException
	//    */
	//   public Collection<UserGroupGridVo> getAvailableUserGroups(UserGroupFilter filter) throws PersistenceException ;

	/**
	 * Return only the needed data for the WorkAreaIncidentSelector.incidentSelector component.
	 * @param workAreaId
	 * @return {@link Collection} of {@link IncidentSelectorVo} objects.
	 * @throws PersistenceException
	 */
	public Collection<IncidentSelectorVo> getWorkAreaIncidentGroupSelectorData(Long workAreaId) throws PersistenceException;

	/**
	 * Return all {@link IncidentGroup} objects within this {@link WorkArea}
	 * @param workAreaId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentGroup> getByWorkAreaId(Long workAreaId) throws PersistenceException;

	/**
	 * 
	 * @param incidentId
	 * @throws PersistenceException
	 */
	public void removeIncidentFromGroup(Long incidentId) throws PersistenceException;

	/**
	 * 
	 * @param incidentGroupId
	 * @return {@link Incident} ids in the {@link IncidentGroup}.
	 * @throws PersistenceException
	 */
	public Collection<Long> getIncidentIdsInGroup(Long incidentGroupId) throws PersistenceException;

	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long loggedInUserId, Boolean privilegedUser) throws PersistenceException;

	public Collection<UserGridVo> getAvailableUsers() throws PersistenceException;

	public void deleteFromUserCustomView(Long incidentGroupId) throws PersistenceException ;

	public void executeInsertIncidentGroupNewUser(Long userId) throws PersistenceException,Exception;

	public void executeInsertIncidentGroupNewIncident(Long incidentId) throws PersistenceException,Exception;	

	public int getCrossIncidentTimePostings(Long incidentId) throws PersistenceException;
	
	public int getCrossIncidentCostRecords(Long incidentId) throws PersistenceException;
	
	public int getCrossIncidentTimePostingsInverse(Long igId, Long incidentId) throws PersistenceException;
	
	public int getCrossIncidentCostRecordsInverse(Long incidentId) throws PersistenceException;
	
	public Date getEarliestIncidentStartDate(Long groupId) throws PersistenceException ;

	public void addIncidentUsers(Long incidentGroupId) throws PersistenceException ;	

	public void createDefaultIapSettings(Long incidentGroupId) throws PersistenceException;

	public Long getIncidentGroupIdByIncidentId(Long incidentId) throws PersistenceException;

	public void createDefaultGroupSettings(Long incidentGroupId, Long primaryIncidentId) throws PersistenceException;

	public void checkCreateSiteGroupIap() throws PersistenceException;

	public void restoreDefault203TemplateSettings(Long incidentGroupId, String sectionCode) throws PersistenceException;

	public void restoreDefault204TemplateSettings(Long incidentGroupId) throws PersistenceException ;

	public String getSiteGroupTransferableIdentity(Long groupId) throws PersistenceException;

	public Collection<IncidentGroupGridVo> getGroupsForDataTransfer() throws PersistenceException;

	public void updateSiteManaged(Long id, Boolean siteManaged) throws PersistenceException ;
	
	public void updateGroupIncidentsSiteManaged(Long id, Boolean siteManaged) throws PersistenceException;

	public void updateSiteManaged(String groupTi, Boolean siteManaged) throws PersistenceException;
	
	public void updateGroupIncidentsSiteManaged(String groupTi, Boolean siteManaged) throws PersistenceException;

	public void updateGroupName(String groupTi, String name) throws PersistenceException;

	public void propagatePrefs(Long incidentGroupId) throws PersistenceException,Exception;

	public void propagatePrefsFromIncident(Long incidentId,Long incidentGroupId) throws PersistenceException,Exception;

	public void removeQuestionFromGroup(Long groupId, Collection<String> questions) throws PersistenceException;

	public void addQuestionsToGroup(Long groupId, Collection<String> questions) throws PersistenceException;
	
	public void updateQuestionsToGroup(Long groupId, Collection<String> questions) throws PersistenceException;

	public Long getGroupQuestionId(String question, Long incidentGroupId) throws PersistenceException ;

	public Boolean isSyncedOnce(Long id) throws PersistenceException;

	public void updateIsSyncedOnce(Long id) throws PersistenceException;

	public Long getIncidentGroupIdByTi(String ti) throws PersistenceException;

	public void cleanUpDuplicateQuestionIssue(Long incidentGroupId) throws PersistenceException ;	

	public Collection<Long> getGroupIncidentIds(String groupTi) throws PersistenceException;
	
	public String getGroupNameByTi(String ti) throws PersistenceException;
	public void saveGroupName(String name, String ti) throws PersistenceException;
	public int getGroupNameCountByTi(String name, String ti) throws PersistenceException;

	public void preDeleteIncidentGroup(Long incidentGroupId) throws PersistenceException;	
}
