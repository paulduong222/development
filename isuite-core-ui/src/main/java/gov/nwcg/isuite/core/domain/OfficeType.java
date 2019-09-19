package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;


public interface OfficeType extends Persistable{

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name the name to set
	 */
	public abstract void setName(String name);

}