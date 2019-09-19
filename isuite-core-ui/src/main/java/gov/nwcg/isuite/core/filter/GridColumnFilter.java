package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.GridNameEnum;

/**
 * 
 * @author bsteiner
 */
public interface GridColumnFilter extends Filter {
	
	/**
	 * Sets the gridName to filter by.
	 * 
	 * @param gridName
	 * 			the gridName to set
	 */
	public void setGridName(GridNameEnum gridName);
	
	/**
	 * Returns the gridName.
	 * 
	 * @return
	 * 		the gridName to return
	 */
	public GridNameEnum getGridName();
	
	
}
