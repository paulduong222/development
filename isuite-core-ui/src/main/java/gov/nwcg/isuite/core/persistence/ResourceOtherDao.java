package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResourceOtherDao extends TransactionSupport, CrudDao<ResourceOther> {
	
	/**
	 * @param ids
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ResourceOther> getResourceOthersByIds(Collection<Long> ids) throws PersistenceException;

}
