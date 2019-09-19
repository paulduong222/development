package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 * Data Access for Resources.
 *  
 * @author bsteiner
 */
public interface ResourceKindDao extends TransactionSupport, CrudDao<ResourceKind>{
	
	/**
	 * Removes resourceKinds based on the ids
	 * 
	 * @param resourceKindIds
	 * @throws PersistenceException
	 */
	public void deleteQualifications(Collection<Long> resourceKindIds) throws PersistenceException;

}
