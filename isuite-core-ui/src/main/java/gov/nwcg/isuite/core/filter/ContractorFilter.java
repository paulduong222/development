package gov.nwcg.isuite.core.filter;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface ContractorFilter extends Filter {
	
	public Long getId();

	public void setId(Long id) ;
	
	/**
	 * Returns the groupByContractor.
	 *
	 * @return 
	 *		the groupByContractor to return
	 */
	public Boolean getGroupByContractor();


	/**
	 * Sets the groupByContractor.
	 *
	 * @param groupByContractor 
	 *			the groupByContractor to set
	 */
	public void setGroupByContractor(Boolean groupByContractor);


	/**
	 * Returns the groupByAgreement.
	 *
	 * @return 
	 *		the groupByAgreement to return
	 */
	public Boolean getGroupByAgreement();


	/**
	 * Sets the groupByAgreement.
	 *
	 * @param groupByAgreement 
	 *			the groupByAgreement to set
	 */
	public void setGroupByAgreement(Boolean groupByAgreement);


	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName();


	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name);


	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns();
	
	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns) ;

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() ;

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone);


	/**
	 * Returns the addressLine1.
	 *
	 * @return 
	 *		the addressLine1 to return
	 */
	public String getAddressLine1();

	/**
	 * Sets the addressLine1.
	 *
	 * @param addressLine1 
	 *			the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1);


	/**
	 * Returns the city.
	 *
	 * @return 
	 *		the city to return
	 */
	public String getCity();

	/**
	 * Sets the city.
	 *
	 * @param city 
	 *			the city to set
	 */
	public void setCity(String city);


	/**
	 * Returns the state.
	 *
	 * @return 
	 *		the state to return
	 */
	public String getState() ;

	/**
	 * Sets the state.
	 *
	 * @param state 
	 *			the state to set
	 */
	public void setState(String state);


	/**
	 * Returns the postalCode.
	 *
	 * @return 
	 *		the postalCode to return
	 */
	public String getPostalCode();


	/**
	 * Sets the postalCode.
	 *
	 * @param postalCode 
	 *			the postalCode to set
	 */
	public void setPostalCode(String postalCode) ;

	/**
	 * Returns the agreement.
	 *
	 * @return 
	 *		the agreement to return
	 */
	public String getAgreement();

	/**
	 * Sets the agreement.
	 *
	 * @param agreement 
	 *			the agreement to set
	 */
	public void setAgreement(String agreement);

	/**
	 * Returns the pointOfHire.
	 *
	 * @return 
	 *		the pointOfHire to return
	 */
	public String getPointOfHire() ;

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire);

	/**
	 * Returns the beginDate.
	 *
	 * @return 
	 *		the beginDate to return
	 */
	public Date getBeginDate() ;
	
	/**
	 * Sets the beginDate.
	 *
	 * @param beginDate 
	 *			the beginDate to set
	 */
	public void setBeginDate(Date beginDate);

	/**
	 * Returns the endDate.
	 *
	 * @return 
	 *		the endDate to return
	 */
	public Date getEndDate();

	/**
	 * Sets the endDate.
	 *
	 * @param endDate 
	 *			the endDate to set
	 */
	public void setEndDate(Date endDate);
	
	 /**
	  * @return deletable
	  */
	public Boolean getDeletable();
   
	/**
	 * @param deletable
	 */
	public void setDeletable(Boolean deletable);
	
	/**
	 * @return deletableString
	 */
	public String getDeletableString();
	
	/**
	 * @param deletableString
	 */
	public void setDeletableString(String deletableString);
	
	/**
	 * @param incidentIds
	 */
	public void setIncidentIds(Collection<Long> incidentIds);
	
	/**
	 * @return incidentIds
	 */
	public Collection<Long> getIncidentIds();
	
	/**
	 * @return the incidentIds as a Collection of Longs
	 * 
	 * @throws Exception
	 */
	public Collection<Long> getIncidentIdsAsLongs() throws Exception;


	public Long getIncidentGroupId();
	
	public void setIncidentGroupId(Long id);
}
