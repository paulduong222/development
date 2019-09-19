package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

public interface CountrySubdivision extends Persistable{

	/**
	 * Accessor for abbreviation.
	 * @see #setAbbreviation(String)
	 * @return the abbreviation, will not be null
	 */
	public abstract String getAbbreviation();

	/**
	 * Accessor for abbreviation.
	 * @param abbreviation the abbreviation to set, will not be null
	 * @see #setAbbreviation(String)
	 */
	public abstract void setAbbreviation(String abbreviation);

	/**
	 * Accessor for name.
	 * @return the name, will not be null
	 * @see #setName(String)
	 */
	public abstract String getName();

	/**
	 * Accessor for name.
	 * @param name the name to set, can not be null
	 * @see #getName()
	 */
	public abstract void setName(String name);

   /**
    * Accessor for standard.
    * @return the standard flag, will not be null
    * @see #setStandard(Boolean)
    */
   public abstract Boolean isStandard();

   /**
    * Accessor for standard.
    * @param standard the flag to set if the item comes standard with the base application or not, can not be null
    * @see #isStandard()
    */
   public abstract void setStandard(Boolean standard);

   /**
    * Accessor countryCodeId
    * 
    * @see #setCountryCodeId(Long)
    * @return countryCodeId
    */
   public abstract Long getCountryCodeId();

   /**
    * Accessor countryCodeId
    * 
    * @see #getCountryCodeId()
    * @param countryCodeId id of country code object
    */
   public abstract void setCountryCodeId(Long id);

   /**
    * Accessor countryCode
    * 
    * @see #setCountryCode(CountryCode)
    * @return countryCode
    */
   public abstract CountryCode getCountryCode();

   /**
    * Accessor countryCode
    * 
    * @see #getCountryCode()
    * @param CountryCode country code object
    */
   public abstract void setCountryCode(CountryCode countryCode);

   public Collection<JetPort> getJetPorts() ;
   
   public void setJetPorts(Collection<JetPort> jetPorts) ;
   
}