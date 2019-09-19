package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import java.util.Collection;

public interface GridColumnUserDao extends TransactionSupport, CrudDao<GridColumnUser> {

	/**
	 * Returns a collection of GridColumnUser entities matching the filter criteria.
	 * 
	 * @param filter
	 * 			the filter to filter by
	 * @return
	 * 		collection of GridColumnUser records
	 * @throws PersistenceException
	 */
	public Collection<GridColumnUser> getGridColumns(GridColumnUserFilter filter) throws PersistenceException;

	/**
	 * Restore default grid columns.
	 * @param gridName
	 * @param userId
	 * @throws PersistenceException
	 */
	public void restoreDefaults(GridNameEnum gridName, Long userId) throws PersistenceException;
	
}
