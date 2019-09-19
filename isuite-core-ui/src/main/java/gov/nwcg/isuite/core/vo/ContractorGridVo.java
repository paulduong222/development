package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.ZipCodeUtility;

import java.util.ArrayList;
import java.util.Collection;

public class ContractorGridVo extends AbstractVo {
	private String name;
	private String duns;
	private String phone;
	private String addressLine1;
	private String city;
	private String countrySubAbbreviation;
	private String postalCode;
    private Boolean enabled;
    private Boolean deletable;
    private Long incidentId;
    
	public ContractorGridVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ContractorGridVo getInstance(Contractor entity, Boolean cascadable) throws Exception {
		ContractorGridVo vo = new ContractorGridVo();
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setName(entity.getName());
			vo.setDuns(entity.getDuns());
			vo.setPhone(entity.getPhone());
			if (null != entity.getAddress()) {
				vo.setAddressLine1(entity.getAddress().getAddressLine1());
				vo.setCity(entity.getAddress().getCity());
				if (null != entity.getAddress().getCountrySubdivision()) {
					vo.setCountrySubAbbreviation(entity.getAddress().getCountrySubdivision().getAbbreviation());
				}
				if(StringUtility.hasValue(entity.getAddress().getPostalCode()))
					vo.setPostalCode(ZipCodeUtility.formatZipCode(entity.getAddress().getPostalCode()));
			}
			
			vo.setEnabled(entity.isEnabled());
			
			//TODO: System must allow the user to delete a contractor/cooperator
			// that is not associated with any resources for which an original 
			//invoice was generated.
			vo.setDeletable(true);
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<ContractorGridVo> getInstances(Collection<Contractor> entities, Boolean cascadable) throws Exception {
		Collection<ContractorGridVo> vos = new ArrayList<ContractorGridVo>();
		
		for(Contractor entity : entities){
			vos.add(ContractorGridVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns) {
		this.duns = duns;
	}

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param deletable the deletable to set
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * @return the deletable
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param countrySubAbbreviation the countrySubAbbreviation to set
	 */
	public void setCountrySubAbbreviation(String countrySubAbbreviation) {
		this.countrySubAbbreviation = countrySubAbbreviation;
	}

	/**
	 * @return the countrySubAbbreviation
	 */
	public String getCountrySubAbbreviation() {
		return countrySubAbbreviation;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
}
