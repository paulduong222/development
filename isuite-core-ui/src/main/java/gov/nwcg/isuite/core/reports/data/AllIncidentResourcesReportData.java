package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;


/**
 * Report data object for AllIncidentResourcesReport.jrxml.
 */
public class AllIncidentResourcesReportData extends BaseReportData {
	
	private String requestCategory="";
	private String section="";
	private Object firstSort;
	private String subSectionSub="";
	
	private String requestNumber="";
	private String requestNumberSortValue="";
	
	private String leaderName;
	
	private String kind="";
	private String unit="";
	private String agency="";
	private String status="";

	private Date checkInDate=null;
	private Date mobDate;
	private Date firstWorkDate;
	private Long lngOfAssignment;
	private Date demobDate;
	
	private String returnTravelMethod="";
	private String contactPhone="";

	private Long numberOfPersonnel;
	
	private String demobCity="";
	private String demobState="";
	private String jetPort="";
	private String lwd=""; 
	
	private Boolean person=false;
	
	private int intPerson=0;
	
	// helper methods for query transformation
	private String sortName;
	
	public AllIncidentResourcesReportData(){
		super();
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
	 * Returns the jetPort.
	 *
	 * @return 
	 *		the jetPort to return
	 */
	public String getJetPort() {
		return jetPort;
	}

	/**
	 * Sets the jetPort.
	 *
	 * @param jetPort 
	 *			the jetPort to set
	 */
	public void setJetPort(String jetPort) {
		this.jetPort = jetPort;
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
	 * Returns the lwd.
	 *
	 * @return 
	 *		the lwd to return
	 */
	public String getLwd() {
		return lwd;
	}

	/**
	 * Sets the lwd.
	 *
	 * @param lwd 
	 *			the lwd to set
	 */
	public void setLwd(String lwd) {
		this.lwd = lwd;
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
	 * Returns the requestCategory.
	 *
	 * @return 
	 *		the requestCategory to return
	 */
	public String getRequestCategory() {
		return requestCategory;
	}

	/**
	 * Sets the requestCategory.
	 *
	 * @param requestCategory 
	 *			the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * Returns the contactPhone formatted.
	 *
	 * @return 
	 *		the contactPhone to return
	 */
	public String getContactPhoneFormatted() {
		return StringUtility.tenDigitPhoneNumberFormatter(contactPhone);
		
	}
	
	/**
	 * Returns the contactPhone.
	 *
	 * @return 
	 *		the contactPhone to return
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * Sets the contactPhone.
	 *
	 * @param contactPhone 
	 *			the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Date getMobDate() {
		return mobDate;
	}

	public void setMobDate(Date mobDate) {
		this.mobDate = mobDate;
	}

	public Date getFirstWorkDate() {
		return firstWorkDate;
	}

	public void setFirstWorkDate(Date firstWorkDate) {
		this.firstWorkDate = firstWorkDate;
	}

	public Long getLngOfAssignment() {
		return lngOfAssignment;
	}

	public void setLngOfAssignment(Long lngOfAssignment) {
		this.lngOfAssignment = lngOfAssignment;
	}

	public Date getDemobDate() {
		return demobDate;
	}

	public void setDemobDate(Date demobDate) {
		this.demobDate = demobDate;
	}

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * @return the numberOfPersonnel
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
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
		}else {
			return firstSort;
		}
	}

	public String getLeaderName() {
		if(StringUtility.hasValue(leaderName))
			return leaderName;
		else
			return "";
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return section;
	}

	/**
	 * @param subSectionSub the subSectionSub to set
	 */
	public void setSubSectionSub(String subSectionSub) {
		this.subSectionSub = subSectionSub;
	}

	/**
	 * @return the subSectionSub
	 */
	public String getSubSectionSub() {
		return subSectionSub;
	}

	public String getRequestNumberSortValue() {
		return requestNumberSortValue;
	}

	public void setRequestNumberSortValue(String requestNumberSortValue) {
		this.requestNumberSortValue = requestNumberSortValue;
	}
}
