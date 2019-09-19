package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IncidentGroupUserDao extends TransactionSupport, CrudDao<IncidentGroupUser> {

   /**
    * 
    * @param userId
    * @param incidentGroupId
    * @return
    * @throws PersistenceException
    */
   public IncidentGroupUser getIncidentGroupUserByUserIdAndIncidentGroupId(Long userId, Long incidentGroupId) throws PersistenceException;
}
