package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;

import java.util.Date;

public class ActualDemobRestOverNightData extends BaseReportData {
	private Boolean restOverNight = false;
	private String restOverNightCity = "";
	private String restOverNightState = "";
	private String restOverNightCityState = "";
	private Date estimatedArrivalDate = null;
	private Integer recordCount;
	
	public ActualDemobRestOverNightData() {
	}
	
	/**
	 * Sets the restOverNight
	 * 
	 * @param restOverNight
	 * 		the restOverNight to set
	 */
	public void setRestOverNight(Boolean restOverNight) {
		this.restOverNight = restOverNight;
	}

	/**
	 * Returns the restOverNight
	 * 
	 * @return
	 * 		the restOverNight to return
	 */
	public Boolean isRestOverNight() {
		return this.restOverNight;
	}
	
	/**
	 * Returns the restOverNight
	 * 
	 * @return
	 * 		the restOverNight to return
	 */
	public Boolean getRestOverNight() {
		return this.restOverNight;
	}
	
	/**
	 * Sets the restOverNightCity
	 * 
	 * @param restOverNightCity
	 * 		the restOverNightCity to set
	 */
	public void setRestOverNightCity(String restOverNightCity) {
		this.restOverNightCity = restOverNightCity;
	}

	/**
	 * Returns the restOverNightCity
	 * 
	 * @return
	 * 		the restOverNightCity to return
	 */
	public String getRestOverNightCity() {
		return this.restOverNightCity;
	}
	
	/**
	 * Sets the restOverNightState
	 * 
	 * @param restOverNightState
	 * 		the restOverNightState to set
	 */
	public void setRestOverNightState(String restOverNightState) {
		this.restOverNightState = restOverNightState;
	}

	/**
	 * Returns the restOverNightState
	 * 
	 * @return
	 * 		the restOverNightState to return
	 */
	public String getRestOverNightState() {		
		return restOverNightState;
	}
	
	/**
	 * @param restOverNightCityState the restOverNightCityState to set
	 */
	public void setRestOverNightCityState(String restOverNightCityState) {
		this.restOverNightCityState = restOverNightCityState;
	}


	/**
	 * @return the restOverNightCityState
	 */
	public String getRestOverNightCityState() {
		return super.getCityState(restOverNightCity, restOverNightState);
	}
	
	/**
	 * Sets the estimatedArrivalDate
	 * 
	 * @param estimatedArrivalDate
	 * 		the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
		this.estimatedArrivalDate = estimatedArrivalDate;
	}

	/**
	 * Returns the estimatedArrivalDate
	 * 
	 * @return
	 * 		the estimatedArrivalDate to return
	 */
	public Date getEstimatedArrivalDate() {
		return estimatedArrivalDate;
	}

	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return the recordCount
	 */
	public Integer getRecordCount() {
		return recordCount;
	}
	
	
}
