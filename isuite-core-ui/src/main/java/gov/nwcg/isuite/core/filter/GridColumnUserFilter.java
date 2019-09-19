package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.GridNameEnum;

/**
 * 
 * @author bsteiner
 */
public interface GridColumnUserFilter extends Filter {
	
	/**
	 * Sets the userId to filter by.
	 * 
	 * @param userId
	 * 			the userId to set
	 */
	public void setUserId(Long userId);
	
	/**
	 * Returns the userId to filter by.
	 * 
	 * @return
	 * 		the userId to return
	 */
	public Long getUserId();
	
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
