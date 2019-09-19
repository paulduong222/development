package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import java.util.ArrayList;
import java.util.Collection;

public class GridColumnUserFilterImpl extends FilterImpl implements GridColumnUserFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3397284647022350152L;
	private GridNameEnum gridName;
	private Long userId;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnUserFilter#getGridName()
	 */
	public GridNameEnum getGridName() {
		return gridName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnUserFilter#getUserId()
	 */
	public Long getUserId() {
		return userId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnUserFilter#setGridName(gov.nwcg.isuite.framework.types.GridNameEnum)
	 */
	public void setGridName(GridNameEnum gridName) {
		this.gridName=gridName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.GridColumnUserFilter#setUserId(java.lang.Long)
	 */
	public void setUserId(Long userId) {
		this.userId=userId;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(GridColumnUserFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getUserId() ?  new FilterCriteria("this.userId",filter.getUserId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getGridName() ?  new FilterCriteria("gc.gridName",filter.getGridName(),FilterCriteria.TYPE_EQUAL): null);
		
		return criteria;
	}
	
}
