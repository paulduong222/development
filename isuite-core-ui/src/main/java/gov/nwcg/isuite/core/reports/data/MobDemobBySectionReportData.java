package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

/**
 * Report data object for MobDemobBySectionReport.jrxml.
 */
public class MobDemobBySectionReportData {
	private String incidentName="";
	private String incidentNumber="";
	private String requestNumber="";
	private String resourceName="";
	private String lastName="";
	private String firstName="";
	private Date demobDate=null;
	private String kind="";
	private Boolean training=Boolean.FALSE;
	private String trainee="";
	private Date mobilizationDate=null;
	private Date checkInDate=null;
	private Date firstWorkDay=null;
	private Boolean person=false;
	private int intPerson=0;
	
	public MobDemobBySectionReportData(){
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
		if( (null==resourceName) || (resourceName.isEmpty())){
			return lastName + ", " + firstName;
		}else{
			return resourceName;
		}
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

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the person.
	 *
	 * @return 
	 *		the person to return
	 */
	public Boolean getPerson() {
		return person;
	}

	/**
	 * Sets the person.
	 *
	 * @param person 
	 *			the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
		if(person)intPerson=1;
	}

	/**
	 * Returns the intPerson.
	 *
	 * @return 
	 *		the intPerson to return
	 */
	public int getIntPerson() {
		return intPerson;
	}

	/**
	 * Sets the intPerson.
	 *
	 * @param intPerson 
	 *			the intPerson to set
	 */
	public void setIntPerson(int intPerson) {
		this.intPerson = intPerson;
	}

	/**
	 * Returns the demobDate.
	 *
	 * @return 
	 *		the demobDate to return
	 */
	public Date getDemobDate() {
		return demobDate;
	}

	/**
	 * Sets the demobDate.
	 *
	 * @param demobDate 
	 *			the demobDate to set
	 */
	public void setDemobDate(Date demobDate) {
		this.demobDate = demobDate;
	}

	/**
	 * Returns the kind.
	 *
	 * @return 
	 *		the kind to return
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind 
	 *			the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Returns the training.
	 *
	 * @return 
	 *		the training to return
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * Sets the training.
	 *
	 * @param training 
	 *			the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * Returns the trainee.
	 *
	 * @return 
	 *		the trainee to return
	 */
	public String getTrainee() {
		return (training == Boolean.TRUE ? "YES" : "NO");
	}

	/**
	 * Returns the mobilizationDate.
	 *
	 * @return 
	 *		the mobilizationDate to return
	 */
	public Date getMobilizationDate() {
		return mobilizationDate;
	}

	/**
	 * Sets the mobilizationDate.
	 *
	 * @param mobilizationDate 
	 *			the mobilizationDate to set
	 */
	public void setMobilizationDate(Date mobilizationDate) {
		this.mobilizationDate = mobilizationDate;
	}

	/**
	 * Returns the checkInDate.
	 *
	 * @return 
	 *		the checkInDate to return
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}

	/**
	 * Sets the checkInDate.
	 *
	 * @param checkInDate 
	 *			the checkInDate to set
	 */
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	/**
	 * Returns the firstWorkDay.
	 *
	 * @return 
	 *		the firstWorkDay to return
	 */
	public Date getFirstWorkDay() {
		return firstWorkDay;
	}

	/**
	 * Sets the firstWorkDay.
	 *
	 * @param firstWorkDay 
	 *			the firstWorkDay to set
	 */
	public void setFirstWorkDay(Date firstWorkDay) {
		this.firstWorkDay = firstWorkDay;
	}
	
}
