package gov.nwcg.isuite.framework.core.filter.impl;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.filter.FilterFactory;
import gov.nwcg.isuite.framework.types.FilterEnum;

import java.util.HashMap;


/**
 * Implementation that uses an EnumMap to correctly grab the right version
 * of filter needed for the page being viewed.
 * 
 * @author ncollette
 *
 */
public class FilterFactoryImpl implements FilterFactory {

	private HashMap<FilterEnum, Filter> filterMap;
	private FilterEnum key;
	
	public FilterFactoryImpl(FilterEnum anEnum) {
		key = anEnum;
	}

	/**
	 * @return the filterMap
	 */
	public HashMap getFilterMap() {
		if (filterMap == null) {
			throw new IllegalStateException("This filter map should've been set up in the " +
											"spring config");
		}
		return filterMap;
	}

	/**
	 * @param filterMap the filterMap to set
	 */
	public void setFilterMap(HashMap<FilterEnum, Filter> filterMap) {
		if (filterMap == null) {
			throw new IllegalArgumentException("You can't set the map to null.");
		}
		this.filterMap = filterMap;
	}
	
	public Filter getFilter(FilterEnum anEnum) {
		return this.filterMap.get(anEnum.toString());
	}

	public Object getObject() throws Exception {
		return this.filterMap.get(key);
	}

	public Class getObjectType() {
		return this.filterMap.get(key).getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
