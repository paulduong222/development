package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 * Data Access for Assignments.
 *  
 * @author mpoll
 */
public interface AssignmentDao extends TransactionSupport, CrudDao<Assignment>{

   public void updateStatus(Long parentResourceId, String status) throws PersistenceException;

   public Long getIncidentResourceId(Long assignmentId) throws PersistenceException;
   
}
