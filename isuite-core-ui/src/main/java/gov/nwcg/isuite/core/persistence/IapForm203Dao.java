package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapForm203Dao extends TransactionSupport, CrudDao<IapForm203> {
	
	public void deleteAllBranches(Long id) throws PersistenceException;	
	
	public void deleteAllBranchPositions(Long id) throws PersistenceException;
	
}
