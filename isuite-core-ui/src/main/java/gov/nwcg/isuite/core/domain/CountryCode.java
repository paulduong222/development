package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CountryCode extends Persistable{

	/**
	 * Sets the abbreviation of the country.
	 * 
	 * @param abbreviation 
	 * 			the abbreviation to set
	 */
   public void setAbbreviation(String abbreviation);

	/**
	 * Returns the abbreviation of the country.
	 * 
	 * @return 
	 * 		the abbreviation of the country
	 */
   public String getAbbreviation();

	/**
	 * Sets the name of country code.
	 * 
	 * @param name 
	 * 		the name of country to set
	 */
   public void setName(String name);

	/**
	 * Returns the name of country code.
	 * 
	 * @return 
	 * 		the country code name to return
	 */
   public String getName();

}