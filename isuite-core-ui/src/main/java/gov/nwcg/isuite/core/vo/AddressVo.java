package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.impl.AddressImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.ZipCodeUtility;

/**
 * 
 * @author Geoff Dyer
 */
public class AddressVo extends AbstractVo implements PersistableVo {
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String postalCode;
	private CountryCodeSubdivisionVo countrySubdivisionVo;

	/**
	 * Constructor
	 */
	public AddressVo() {
		super();
	}

	public static AddressVo getInstance(Address entity, boolean cascadable,Persistable...persistables) throws Exception {
		AddressVo vo = new AddressVo();

		if(null == entity)
			throw new Exception("Unable to build AddressVo instance from null Address entity");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setAddressLine1(StringUtility.toUpper(entity.getAddressLine1()));
			vo.setAddressLine2(StringUtility.toUpper(entity.getAddressLine2()));
			vo.setCity(StringUtility.toUpper(entity.getCity()));
			if(StringUtility.hasValue(entity.getPostalCode()))
				vo.setPostalCode(ZipCodeUtility.formatZipCode(entity.getPostalCode()));
			else
				vo.setPostalCode("");
			
			if (null != entity.getCountrySubdivision()) {
				vo.setCountrySubdivisionVo(CountryCodeSubdivisionVo.getInstance(entity.getCountrySubdivision(), cascadable));
			}
		}

		return vo;
	}

	public static Address toEntity(AddressVo sourceVo,boolean cascadable,Persistable... persistables) throws Exception {
		Address entity = new AddressImpl();

		entity.setId(sourceVo.getId());

		if(cascadable){
			entity.setAddressLine1(StringUtility.toUpper(sourceVo.getAddressLine1()));
			entity.setAddressLine2(StringUtility.toUpper(sourceVo.getAddressLine2()));
			entity.setCity(StringUtility.toUpper(sourceVo.getCity()));
			entity.setPostalCode(StringUtility.toUpper(sourceVo.getPostalCode()));
			if (null != sourceVo.getCountrySubdivisionVo() && sourceVo.getCountrySubdivisionVo().getId() > 0) {
				entity.setCountrySubdivision(CountryCodeSubdivisionVo.toEntity(sourceVo.getCountrySubdivisionVo(), false));
			}
			
			Contractor contractorEntity = (Contractor)getPersistableObject(persistables,ContractorImpl.class);
			if(null != contractorEntity)
				entity.getContractors().add(contractorEntity);
			
			AssignmentTime assignmentTimeEntity = 
				(AssignmentTime)getPersistableObject(persistables, AssignmentTimeImpl.class);
			if(null != assignmentTimeEntity)
				entity.getAssignmentTimes().add(assignmentTimeEntity);
		}

		return entity;
	}

	public static Address toEntity(Address entity, AddressVo sourceVo,boolean cascadable,Persistable... persistables) throws Exception {
		if(null==entity){
			entity = new AddressImpl();
		}
		
		entity.setId(sourceVo.getId());

		if(cascadable){
			entity.setAddressLine1(StringUtility.toUpper(sourceVo.getAddressLine1()));
			entity.setAddressLine2(StringUtility.toUpper(sourceVo.getAddressLine2()));
			entity.setCity(StringUtility.toUpper(sourceVo.getCity()));
			entity.setPostalCode(StringUtility.toUpper(sourceVo.getPostalCode()));
			if (null != sourceVo.getCountrySubdivisionVo() && sourceVo.getCountrySubdivisionVo().getId() > 0) {
				entity.setCountrySubdivision(CountryCodeSubdivisionVo.toEntity(sourceVo.getCountrySubdivisionVo(), false));
			}
			
			Contractor contractorEntity = (Contractor)getPersistableObject(persistables,ContractorImpl.class);
			if(null != contractorEntity)
				entity.getContractors().add(contractorEntity);
			
			AssignmentTime assignmentTimeEntity = 
				(AssignmentTime)getPersistableObject(persistables, AssignmentTimeImpl.class);
			if(null != assignmentTimeEntity)
				entity.getAssignmentTimes().add(assignmentTimeEntity);
		}

		return entity;
	}

	/**
	 * Returns true if the vo has had no properties set.
	 * NOTE: this should be overridden by subclasses.
	 * 
	 * @return
	 * 		whether the vo has had any properties set.
	 */
	@Override
    public Boolean isEmpty() {
		
		Boolean rv = true;
		
		if(StringUtility.hasValue(addressLine1)) rv = false;
		if(StringUtility.hasValue(addressLine2)) rv = false;
		if(StringUtility.hasValue(city)) rv = false;
		if(StringUtility.hasValue(postalCode)) rv = false;
		if(null != countrySubdivisionVo) rv = false;
		
    	return rv;
    }

    public static Boolean isEmpty(Address addr) {
		
		Boolean rv = true;
		
		if(StringUtility.hasValue(addr.getAddressLine1())) rv = false;
		if(StringUtility.hasValue(addr.getAddressLine2())) rv = false;
		if(StringUtility.hasValue(addr.getCity())) rv = false;
		if(StringUtility.hasValue(addr.getPostalCode())) rv = false;
		if(null != addr.getCountrySubdivision()) rv = false;
		
    	return rv;
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
	 * @return the countrySubdivisionVo
	 */
	public CountryCodeSubdivisionVo getCountrySubdivisionVo() {
		return countrySubdivisionVo;
	}

	/**
	 * @param countrySubdivisionVo the countrySubdivisionVo to set
	 */
	public void setCountrySubdivisionVo(
			CountryCodeSubdivisionVo countrySubdivisionVo) {
		this.countrySubdivisionVo = countrySubdivisionVo;
	}
}
