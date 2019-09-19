package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.types.ResourceRequestCategoryEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Calendar;
import java.util.Date;

/**
 * Report data object for DemobPlanningReport.jrxml.
 */
public class DemobPlanningReportData {
	private Long incidentId;
	private String pagesubtitle;
	private String incidentName="";
	private String incidentNumber="";

	private String category="";
	private String section="";
	private String subsection="";

	private String requestNumber="";
	private String resourceName="";
	private String crewLeader="";
	private String qualifications="";

	private Integer trainee=0;
	
	private String itemCode="";
	private String status="";
	private String agency="";
	private String unit="";

	private String returnTravelMethod="";
	private String demobCity="";
	private String demobState="";

	//private Date firstWorkDay; //used to calculate lastWorkDay
	//private Integer lengthOfAssignment; // used to calculate lastWorkDay
	private Date lastWorkDay;
	private Double daysLeft;

	private Date releaseDate=null;
	private String releaseTime="";
	private Integer personCount;
	private Object firstSort;

	private String reassign;
	
	public DemobPlanningReportData(){
	}

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
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the incidentNumber.
	 *
	 * @return 
	 *		the incidentNumber to return
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * Sets the incidentNumber.
	 *
	 * @param incidentNumber 
	 *			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() {
		//if( (null==resourceName) || (resourceName.isEmpty())){
		//	return lastName + ", " + firstName;
		//}else{
		if(StringUtility.hasValue(resourceName)){
			if(resourceName.length()>26)
				return resourceName.substring(0,25);
			else
				return resourceName;
		}else
			return resourceName;
		//}
	}

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCrewLeader() {
		return crewLeader;
	}

	public void setCrewLeader(String crewLeader) {
		this.crewLeader = crewLeader;
	}

	/**
	 * Returns the qualifications.
	 *
	 * @return 
	 *		the qualifications to return
	 */
	public String getQualifications() {
		return qualifications;
	}

	/**
	 * Sets the qualifications.
	 *
	 * @param qualifications 
	 *			the qualifications to set
	 */
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	/**
	 * Set the Trainee integer value
	 * 
	 * @param trainee
	 */
	public void setTraining(Boolean val) {
		this.trainee = (val ? 1 : 0);
	}

	/**
	 * Return (T) if resource is a trainee, otherwise null
	 * 
	 * @return
	 */
	public String getTrainee() {
		if(trainee==1) {
			return "(T)";
		} else {
			return "";
		}
	}

	/**
	 * Return the kind/code for the resource's assignment
	 * 
	 * @return String
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Set the kind/code for the resource's assignment
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Returns the status.
	 *
	 * @return 
	 *		the status to return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status 
	 *			the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the agency.
	 *
	 * @return 
	 *		the agency to return
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * Sets the agency.
	 *
	 * @param agency 
	 *			the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * Returns the unit.
	 *
	 * @return 
	 *		the unit to return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit 
	 *			the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Returns the returnTravelMethod.
	 *
	 * @return 
	 *		the returnTravelMethod to return
	 */
	public String getReturnTravelMethod() {
		return returnTravelMethod;
	}


	/**
	 * Sets the returnTravelMethod.
	 *
	 * @param returnTravelMethod 
	 *			the returnTravelMethod to set
	 */
	public void setReturnTravelMethod(String returnTravelMethod) {
		this.returnTravelMethod = returnTravelMethod;
	}


	/**
	 * Returns the demobCity.
	 *
	 * @return 
	 *		the demobCity to return
	 */
	public String getDemobCity() {
		return demobCity;
	}

	/**
	 * Sets the demobCity.
	 *
	 * @param demobCity 
	 *			the demobCity to set
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
	}

	/**
	 * Returns the demobState.
	 *
	 * @return 
	 *		the demobState to return
	 */
	public String getDemobState() {
		return demobState;
	}

	/**
	 * Sets the demobState.
	 *
	 * @param demobState 
	 *			the demobState to set
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}

	/** 
	 * Return the last work day for a resource.
	 * Last work day = firstWorkDay + length of assignment - 1.
	 * 
	 * @return Date 
	 */
	public Date getLastWorkDay() {
		return lastWorkDay;
	}

	public void setLastWorkDay(Date day) {
		this.lastWorkDay = day;
	}

	/**
	 * Calculate the number of days between today and the DemobDate (lastWorkDay + 1)
	 * 
	 * @return Integer 
	 */
	

	/**
	 * set the Calendar time to one second after midnight
	 * 
	 * @param cal
	 * @return Calendar
	 */
	private Calendar makeMidnight(Calendar cal) {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * Return the number of days between today and the DemobDate (lastWorkDay + 1)
	 * 
	 * @return Integer 
	 */
	public Double getDaysLeft() {
		//calculateDaysLeft();
		return daysLeft;
	}
	
	/**
	 * Returns the releaseDate.
	 *
	 * @return 
	 *		the releaseDate to return
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the releaseDate.
	 *
	 * @param releaseDate 
	 *			the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
		if(DateUtil.hasValue(releaseDate)){
			String time=DateUtil.toMilitaryTime(releaseDate);
			this.releaseTime=time;
		}
	}
	
	/**
	 * Sets the daysLeft.
	 * 
	 * @param daysLeft
	 */
	public void setDaysLeft(Double daysLeft) {
		this.daysLeft = daysLeft;
	}

	public void setReassign(String val){
		if(StringUtility.hasValue(val) && val.equalsIgnoreCase("YES"))
			this.reassign="Y";
		else
			this.reassign="";
	}
	
	/**
	 * Returns the reassign.
	 *
	 * @return 
	 *		the reassign to return
	 */
	public String getReassign() {
		return this.reassign;
		// TODO: if status is R, then reassign is "Y"
		// is this right?
		// may not need this variable, only the getter.
		/*
		if(status == null) {
			throw new NullPointerException("status can not be null.");
		}
		if(status.equalsIgnoreCase("R")) {
			return "Y";
		} else {
			return "N";
		}
		*/
	}

	/**
	 * Returns the personCount.
	 *
	 * @return 
	 *		the person to return
	 */
	public Integer getPersonCount() {
		return personCount;
	}

	/**
	 * Sets the personCount.
	 *
	 * @param person 
	 *			the integer value of is_person
	 */
	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return section;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return ResourceRequestCategoryEnum.valueOf(category).getDescription() + " Resource";
	}

	/**
	 * @param firstSort the firstSort to set
	 */
	public void setFirstSort(Object firstSort) {
		this.firstSort = firstSort;
	}

	/**
	 * @return the firstSort
	 */
	public Object getFirstSort() {
		if (firstSort == null) {
			return "";
		} else if (firstSort instanceof Date) {
			return DateUtil.toDateString((Date)firstSort);
		} else {
			return firstSort;
		}
	}

	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}

	public String getSubsection() {
		return subsection;
	}

	public String getPagesubtitle() {
		return pagesubtitle;
	}

	public void setPagesubtitle(String pagesubtitle) {
		this.pagesubtitle = pagesubtitle;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public void setTrainee(Integer trainee) {
		this.trainee = trainee;
	}

}