package gov.nwcg.isuite.core.domain;

import java.util.Date;

public interface WorkPeriodOvernightStayInfo {

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId();

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) ;

	/**
	 * Returns the workPeriod.
	 *
	 * @return 
	 *		the workPeriod to return
	 */
	public WorkPeriod getWorkPeriod() ;


	/**
	 * Sets the workPeriod.
	 *
	 * @param workPeriod 
	 *			the workPeriod to set
	 */
	public void setWorkPeriod(WorkPeriod workPeriod) ;

	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId() ;

	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) ;

	/**
	 * Returns the countrySubdivision.
	 *
	 * @return 
	 *		the countrySubdivision to return
	 */
	public CountrySubdivision getCountrySubdivision() ;

	/**
	 * Sets the countrySubdivision.
	 *
	 * @param countrySubdivision 
	 *			the countrySubdivision to set
	 */
	public void setCountrySubdivision(CountrySubdivision countrySubdivision) ;

	/**
	 * Returns the stateId.
	 *
	 * @return 
	 *		the stateId to return
	 */
	public Long getStateId() ;

	/**
	 * Sets the stateId.
	 *
	 * @param stateId 
	 *			the stateId to set
	 */
	public void setStateId(Long stateId) ;

	/**
	 * Returns the city.
	 *
	 * @return 
	 *		the city to return
	 */
	public String getCity() ;


	/**
	 * Sets the city.
	 *
	 * @param city 
	 *			the city to set
	 */
	public void setCity(String city) ;

	/**
	 * Returns the estimatedArrivalDate.
	 *
	 * @return 
	 *		the estimatedArrivalDate to return
	 */
	public Date getEstimatedArrivalDate() ;

	/**
	 * Sets the estimatedArrivalDate.
	 *
	 * @param estimatedArrivalDate 
	 *			the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) ;

	/**
	 * Returns the lengthOfStay.
	 *
	 * @return 
	 *		the lengthOfStay to return
	 */
	public Long getLengthOfStay() ;

	/**
	 * Sets the lengthOfStay.
	 *
	 * @param lengthOfStay 
	 *			the lengthOfStay to set
	 */
	public void setLengthOfStay(Long lengthOfStay) ;

	/**
	 * Returns the remarks.
	 *
	 * @return 
	 *		the remarks to return
	 */
	public String getRemarks() ;

	/**
	 * Sets the remarks.
	 *
	 * @param remarks 
	 *			the remarks to set
	 */
	public void setRemarks(String remarks) ;

	
}
