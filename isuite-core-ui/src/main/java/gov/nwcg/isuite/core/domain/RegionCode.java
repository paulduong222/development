package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RegionCode extends Persistable {

	/**
	 * @return the code
	 */
	public String getCode();

	/**
	 * @param code the code to set
	 */
	public void setCode(String code);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description);

}
