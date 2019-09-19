package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapBranchPersonnelDao extends TransactionSupport, CrudDao<IapBranchPersonnel> {
	
	public int get204PositionCount(Long branchId) throws PersistenceException;	
}
