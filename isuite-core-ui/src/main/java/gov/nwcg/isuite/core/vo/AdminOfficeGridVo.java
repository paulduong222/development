package gov.nwcg.isuite.core.vo;

import java.util.Date;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
/**
 * 
 * @author gdyer
 *
 */
public class AdminOfficeGridVo extends AbstractVo 
{
	public String officeName;
	public String addressLine1;
	public String addressLine2;
	public String city;
	public String countrySubdivision;
	public String postalCode;
	public String phone;
	public Date deletedDate;
	public Boolean deletable=true;
	
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
	

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
	  // TODO: Determine value of deletable after original invoice construct has been implemented.
	  return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }
	
}
