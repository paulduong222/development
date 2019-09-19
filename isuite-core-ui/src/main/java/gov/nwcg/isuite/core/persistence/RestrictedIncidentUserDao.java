package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;

import java.util.Collection;

public interface RestrictedIncidentUserDao extends TransactionSupport, CrudDao<RestrictedIncidentUser> {

   /**
    * Deletes the record associated to the specified id.
    * 
    * @param id
    * @throws PersistenceException
    */
   public void delete(Long id) throws PersistenceException;

   /**
    * Get the record by its ID.
    * 
    * @param id
    * @return
    * @throws PersistenceException
    */
   public RestrictedIncidentUser getById(Long id) throws PersistenceException;

   /**
    * Retrieves users assigned to a restricted incident.
    * 
    * @param restrictedIncidentId
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<RestrictedIncidentUserVo> getUsersInRestrictedIncident(Long restrictedIncidentId, UserFilter filter) throws PersistenceException;
   
   /**
    * Set the "access end date" to now for any users/owners associated to the specified incident 
    * who currently have a null "access end date". 
    * 
    * @param incidentId - id of the incident to unrestrict.
    * @return number of rows affected.
    */
   public int unRestrictIncidentUsersAndOwners(Long incidentId) throws PersistenceException;

   /**
    * Retrieve a count of how many active owners are associated to the specified
    * incident.
    * 
    */
   public int getRestrictedIncidentOwnerCount(Long incidentId) throws PersistenceException;
   
   /**
    * Retrieves all Users that are available for Restricted Incident Assignment
    * @param incidentId Id of selected Incident
    * @param filter UserFilter object
    * @param type - a {@link RestrictedIncidentUserTypeEnum} selected as either 
    * USER or OWNER.  USER returns owners and users.  OWNER returns only owners.  
    * @return
    * @throws PersistenceException
    */
   public Collection<UserGridVo> getRestrictedIncidentAvailableUsersOrOwnersGrid(Long incidentId, UserFilter filter, Long currentWorkAreaId, RestrictedIncidentUserTypeEnum type) throws PersistenceException;
   
   /**
    * Get Restricted Incident Users and Owners from the criteria provided.
    * 
    * @param incidentId
    */
   public Collection<Long> getRestrictedIncidentUsersAndOwnersUserIds(Long incidentId) throws PersistenceException;

   public void deleteUsersById(Collection<Long> ids) throws PersistenceException;
   
   /**
    * 
    * @param userId
    * @param incIds
    * @return
    * @throws PersistenceException
    */
   public Collection<RestrictedIncidentUser> getRIUsersByUserIdAndIncidentIds(Long userId, Collection<Long> incIds) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public Collection<Long> getUserIdsByIncidentIdAndUserId(Long incidentId, Long userId) throws PersistenceException;

   public Collection<UserVo> getAvailableRestrictedIncidentUsers(Long incidentId,UserFilter filter) throws PersistenceException ;

   /**
    * @param userId
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public Long getRestrictedIncidentUserId(Long userId, Long incidentId) throws PersistenceException ;

   public Collection<UserGridVo> getAvailableIncidentUsers(Long incidentId) throws PersistenceException ;

   public void updateUserDefaultCheckIn(RestrictedIncidentUserVo riuVo) throws PersistenceException;
   
}
