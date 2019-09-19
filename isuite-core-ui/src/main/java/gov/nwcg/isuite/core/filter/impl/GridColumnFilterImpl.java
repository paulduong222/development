package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.GridColumnFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class GridColumnFilterImpl extends FilterImpl implements GridColumnFilter {
	private GridNameEnum gridName;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnFilter#getGridName()
	 */
	public GridNameEnum getGridName() {
		return gridName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnFilter#setGridName(gov.nwcg.isuite.framework.types.GridNameEnum)
	 */
	public void setGridName(GridNameEnum gridName) {
		this.gridName=gridName;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(GridColumnFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getGridName() ?  new FilterCriteria("this.gridName",filter.getGridName(),FilterCriteria.TYPE_LIKE): null);
		
		return criteria;
	}
	
}
