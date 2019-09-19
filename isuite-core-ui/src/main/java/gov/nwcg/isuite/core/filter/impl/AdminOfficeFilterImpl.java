package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Date;

/**
 * @author Geoff Dyer
 *
 */
public class AdminOfficeFilterImpl extends FilterImpl implements AdminOfficeFilter {

	private Long id;
	
	private String officeName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String countrySubdivision;
	private String postalCode;
	private String phone;
	private Date deletedDate;
	private Boolean deletable;
	private String deletableString;
	
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the countrySubdivision
	 */
	public String getCountrySubdivision() {
		return countrySubdivision;
	}
	/**
	 * @param countrySubdivision the countrySubdivision to set
	 */
	public void setCountrySubdivision(String countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}
	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#setDeletable(java.lang.Boolean)
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#getDeletable()
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * @return the deletableString
	 */
	public String getDeletableString() {
		return deletableString;
	}

	/**
	 * @param deletableString the deletableString to set
	 */
	public void setDeletableString(String deletableString) {
		this.deletableString = deletableString;
		this.setDeletable(super.determineDeletableValue(this.deletableString));
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
