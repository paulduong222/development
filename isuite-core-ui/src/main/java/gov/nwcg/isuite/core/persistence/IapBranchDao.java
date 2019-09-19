package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapBranchDao extends TransactionSupport, CrudDao<IapBranch> {

	public IapBranch getByBranchDivision(String branch, String division, Long planId, Long excludeId) throws PersistenceException;
	
	public Collection<IapBranch> getByPlanId(Long planId) throws PersistenceException;
	
	
}
