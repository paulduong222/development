package gov.nwcg.isuite.core.filter;


import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

/**
 * @author Geoff Dyer
 *
 */
public interface AdminOfficeFilter extends Filter {

	public Long getId();
	
	public void setId(Long id);
	
	/**
	 * @return the officeName
	 */
	public String getOfficeName();
	
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName);
	
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1();
	
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1);
	
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2();
	
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2);
	
	/**
	 * @return the city
	 */
	public String getCity();
	
	/**
	 * @param city the city to set
	 */
	public void setCity(String city);
	
	/**
	 * @return the countrySubdivision
	 */
	public String getCountrySubdivision();
	
	/**
	 * @param countrySubdivision the countrySubdivision to set
	 */
	public void setCountrySubdivision(String countrySubdivision);
	
	/**
	 * @return the postalCode
	 */
	public String getPostalCode();
	
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode);
	
	/**
	 * @return the phone
	 */
	public String getPhone();
	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);
	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate();
	
	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);
	
	/**
	 * @return deletable
	 */
	public Boolean getDeletable();

	/**
	 * @param deletable
	 */
	public void setDeletable(Boolean deletable);

	
}
