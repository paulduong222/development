package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.filter.SharedWorkAreaFilter;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * DAO for Work Area User associations
 * 
 * @author bsteiner
 */
public interface WorkAreaUserDao extends TransactionSupport, CrudDao<WorkAreaUser> {
   
   /**
    * This method will fetch all the WorkAreaUser objects by a particular User id.
    * @param userId
    * @return Collection of the WorkAreaUser objects
    * @throws PersistenceException
    */
   public Collection<WorkAreaUser> getWorkAreaUsersByUserId(Long userId) throws PersistenceException;
   
   /**
    * This method will retrieve a WorkAreaUser by a particular Work Area Id and User Id combination.
    * @param workAreaId
    * @param userId
    * @return WorkAreaUser
    * @throws PersistenceException
    */
   public WorkAreaUser getByWorkAreaIdAndUserId(Long workAreaId, Long userId) throws PersistenceException;
   
   /**
    * This method will return the WorkAreaUser count for a particular Work Area Id
    * @param workAreaId
    * @return int
    * @throws PersistenceException
    */
   public int getWorkAreaUserCountByWorkAreaId(Long workAreaId) throws PersistenceException;
   
   /**
    * Retrieves Users that can be added to a Shared Work Area
    * @param {@link SharedWorkAreaFilter}
    * @param workAreaId
    * @return A {@link Collection} of {@link UserGridVo} objects
    * @throws PersistenceException
    */
   public Collection<UserGridVo> getUsersAvailableForSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) throws PersistenceException;
   
   /**
    * Get all {@link User}s associated with a particular {@link WorkArea}
    * 
    * @param {@link SharedWorkAreaFilter}
    * @param workAreaId
    * @return {@link UserGridVo}
    * @throws PersistenceException
    */
   public Collection<UserGridVo> getUsersByWorkAreaId(SharedWorkAreaFilter filter, Long workAreaId) throws PersistenceException;
 
   public void deleteUserFromSharedWorkAreas(Collection<Long> ids) throws PersistenceException;
   
}