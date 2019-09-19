package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 * @author mpoll
 *
 */
public interface WorkPeriodDao extends TransactionSupport, CrudDao<WorkPeriod>{

	/**
	 * 
	 * @param incidentResourceId
	 * @return
	 * @throws PersistenceException
	 */
	public WorkPeriod getByIncidentResourceId(Long incidentResourceId) throws PersistenceException;
}
