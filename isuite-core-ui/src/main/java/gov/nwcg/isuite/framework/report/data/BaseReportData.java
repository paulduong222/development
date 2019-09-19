package gov.nwcg.isuite.framework.report.data;

public class BaseReportData {
	private Long incidentId;
	private String incidentName="";
	private String incidentNumber="";
	private String incidentUnit;
	private String incidentState;
	private String resourceName="";
	private String lastName="";
	private String firstName="";
	private Long workPeriodId;
	protected boolean omitCountryPrefix=false;
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * Sets the incidentName
	 * 
	 * @param incidentName
	 * 			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the incidentName
	 * 
	 * @return
	 * 		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentNumber
	 * 
	 * @param incidentNumber
	 * 			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * Returns the incidentNumber
	 * 
	 * @return
	 * 		the incidentNumber to return
	 */
	public String getIncidentNumber() {
		if(null != incidentNumber)
			return (!this.omitCountryPrefix ? "US-" : "" ) + incidentUnit+"-"+incidentNumber;
		else
			return incidentNumber;
	}

	/**
	 * Sets the resourceName
	 * 
	 * @param resourceName
	 * 		the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Returns the resourceName
	 * 
	 * @return
	 * 		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Sets the lastName
	 * 
	 * @param lastName
	 * 		the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the lastName
	 * 
	 * @return
	 * 		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the firstName
	 * 
	 * @param firstName
	 * 		the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the firstName
	 * 
	 * @return
	 * 		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the incidentUnit
	 */
	public String getIncidentUnit() {
		if(null==incidentUnit || incidentUnit.equals(""))
			return "[UNKNOWN_UNIT]";
		else
			return incidentUnit;
	}

	/**
	 * @param incidentUnit the incidentUnit to set
	 */
	public void setIncidentUnit(String incidentUnit) {
		this.incidentUnit = incidentUnit;
	}

	public String getIncidentState() {
		return incidentState;
	}

	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
	}

	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}
	
	public String getCityState(String city, String state) {
		   String rtn="";
		   if(null != city && !city.isEmpty()){
			   rtn=city;
		   }
		   
		   if(null != state && !state.isEmpty()){
			   if(rtn.length()>0)
				   rtn=rtn+", " + state;
			   else
				   rtn=state;
		   }
		   return rtn;
	   }
}
