package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

public interface CostGroupAgencyDayShareDao extends TransactionSupport, CrudDao<CostGroupAgencyDayShare> {
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupAgencyDayShare> getGrid(Long costGroupId) throws PersistenceException;

	public CostGroupAgencyDayShare getByShareDate(Date shareDate, Long costGroupId) throws PersistenceException ;

	public CostGroupAgencyDayShare getLastByCostGroup(Long costGroupId) throws PersistenceException ;

	public void deleteAllBeforeDate(Long costGroupId, Date startDate) throws PersistenceException;

	public void deleteAllAfterDate(Date cutoffDate) throws PersistenceException;
}
