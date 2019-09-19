package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

public interface Address extends Persistable {

   /**
    * @param address line 1: Description of address line 1
    */
   public void setAddressLine1(String addressLine1);

   /**
    * @return the address line 1
    */
   public String getAddressLine1();

   /**
    * @param address line 2: Description of address line 2
    */
   public void setAddressLine2(String addressLine2);

   /**
    * @return the address line 2
    */
   public String getAddressLine2();
   
   /**
    * @param city the city of the address
    */
   public void setCity(String city);

   /**
    * @return the city
    */
   public String getCity();
   
   /**
    * @param postal code the postal code
    */
   public void setPostalCode(String postalCode);

   /**
    * @return the postal code
    */
   public String getPostalCode();
   
   
   /**
    * @param countrySubdivision the country code
    */
   public void setCountrySubdivision(CountrySubdivision countrySubdivision);

   /**
    * @return the countrySubdivision
    */
   public CountrySubdivision getCountrySubdivision();
   
   /**
    * Returns the contractors.
    *
    * @return 
    *		the contractors to return
    */
   public Collection<Contractor> getContractors();

   /**
    * Sets the contractors.
    *
    * @param contractors 
    *			the contractors to set
    */
   public void setContractors(Collection<Contractor> contractors);

	/**
	 * @return the assignmentTimes
	 */
	public Collection<AssignmentTime> getAssignmentTimes();

	/**
	 * @param assignmentTimes the assignmentTimes to set
	 */
	public void setAssignmentTimes(Collection<AssignmentTime> assignmentTimes);   
	
	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers);

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers();
	
	/**
	 * @param resourceHomeUnitContacts the resourceHomeUnitContacts to set
	 */
	public void setResourceHomeUnitContacts(Collection<ResourceHomeUnitContact> resourceHomeUnitContacts);
	
	/**
	 * @return the resourceHomeUnitContacts
	 */
	public Collection<ResourceHomeUnitContact> getResourceHomeUnitContacts();
}
