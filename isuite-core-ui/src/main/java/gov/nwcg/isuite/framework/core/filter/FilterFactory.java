package gov.nwcg.isuite.framework.core.filter;

import org.springframework.beans.factory.FactoryBean;

import gov.nwcg.isuite.framework.types.FilterEnum;

/**
 * The filter factory is used to serve up correct versions of
 * filters based on screens that'll be viewed.
 * (ie. userFilter etc)
 * @author ncollette
 *
 */
public interface FilterFactory extends FactoryBean  {
	
	Filter getFilter(FilterEnum anEnum);

}
