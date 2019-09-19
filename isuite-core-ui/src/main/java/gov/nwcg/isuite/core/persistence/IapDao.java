package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IapDao extends TransactionSupport, CrudDao<Iap> {
	
	/**
	 * @param incidentId
	 * @param incidentGrouupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Iap> getAllIapPlans(Long incidentId, Long incidentGrouupId) throws PersistenceException;
	
}
