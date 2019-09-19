package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.core.filter.GridColumnFilter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface GridColumnDao extends TransactionSupport, CrudDao<GridColumn> {


	/**
	 * Returns a collection of gridColumns.
	 * 
	 * @param filter
	 * 		the gridColumnFilter to filter results by
	 * @return
	 * 		collection of gridColumns to return
	 * @throws PersistenceException
	 */
	public Collection<GridColumn> getGridColumns(GridColumnFilter filter) throws PersistenceException;
	
}
