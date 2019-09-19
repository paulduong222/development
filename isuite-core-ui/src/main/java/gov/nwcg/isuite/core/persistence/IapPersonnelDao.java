package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapPersonnelDao extends TransactionSupport, CrudDao<IapPersonnel> {

	public IapPersonnel getBranchByName(Long iapForm203Id, String name,Long excludeId) throws PersistenceException;
	
}
