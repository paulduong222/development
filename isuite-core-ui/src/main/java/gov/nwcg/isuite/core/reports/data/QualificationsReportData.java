package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;


/**
 * Report data object for QualificationsReport.jrxml.
 */
public class QualificationsReportData {
	private Long resourceId;
	private String requestNumber="";
	private String resourceName="";
	private String agency="";
	private Boolean training=Boolean.FALSE;
	private String trainee="";
	private String kindDescription="";
	private String kindCode="";
	private String section="";
	private String sectionCode="";
	private Date demobDate=null;
	private String demobDateFlag="";
	private String currentPositionFlag="";
	private Date tentativeReleaseDate;
	private Integer lengthAtAssignment;
	private Date firstWorkDate;
	private String incidentName="";
	private String incidentNumber="";
	private Long incidentId;
	private String incidentUnit;
	
	public QualificationsReportData(){
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
		return resourceName;
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
	 * Returns the kindDescription.
	 *
	 * @return 
	 *		the kindDescription to return
	 */
	public String getKindDescription() {
		return kindDescription;
	}

	/**
	 * Sets the kindDescription.
	 *
	 * @param kindDescription 
	 *			the kindDescription to set
	 */
	public void setKindDescription(String kindDescription) {
		this.kindDescription = kindDescription;
	}

	/**
	 * Returns the kindCode.
	 *
	 * @return 
	 *		the kindCode to return
	 */
	public String getKindCode() {
		return kindCode;
	}

	/**
	 * Sets the kindCode.
	 *
	 * @param kindCode 
	 *			the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * Returns the section.
	 *
	 * @return 
	 *		the section to return
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section.
	 *
	 * @param section 
	 *			the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * Returns the demobDate.
	 *
	 * @return 
	 *		the demobDate to return
	 */
	public Date getDemobDate() {
		if(null != this.tentativeReleaseDate){
			return tentativeReleaseDate;
		}else{
			if(null != firstWorkDate && (null != lengthAtAssignment && lengthAtAssignment.intValue() > 0 )){
				Date dt = null;
				try{
					dt=DateUtil.addDaysToDate(firstWorkDate, lengthAtAssignment.intValue());
				}catch(Exception e){
					// smother
				}
				return dt;
			}else{
				return null;
			}
		}
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
	 * Returns the trainee.
	 *
	 * @return 
	 *		the trainee to return
	 */
	public String getTrainee() {
		return (training == Boolean.TRUE ? "T" : "");
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public void setTrainee(String trainee) {
		this.trainee = trainee;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getDemobDateFlag() {
		if(null != this.tentativeReleaseDate){
			return "(T)";
		}else{
			if(null != firstWorkDate && (null != lengthAtAssignment && lengthAtAssignment.intValue() > 0 )){
				return "(D)";
			}else{
				return "";
			}
		}
	}

	public void setDemobDateFlag(String demobDateFlag) {
		this.demobDateFlag = demobDateFlag;
	}

	public String getCurrentPositionFlag() {
		if(null != currentPositionFlag && currentPositionFlag.equals("yes")){
			return "*";
		}else
			return "";
	}

	public void setCurrentPositionFlag(String currentPositionFlag) {
		this.currentPositionFlag = currentPositionFlag;
	}

	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	public Integer getLengthAtAssignment() {
		return lengthAtAssignment;
	}

	public void setLengthAtAssignment(Integer lengthAtAssignment) {
		this.lengthAtAssignment = lengthAtAssignment;
	}

	public Date getFirstWorkDate() {
		return firstWorkDate;
	}

	public void setFirstWorkDate(Date firstWorkDate) {
		this.firstWorkDate = firstWorkDate;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return this.incidentUnit + "-" + this.incidentNumber;
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
	 * @param incidentUnit the incidentUnit to set
	 */
	public void setIncidentUnit(String incidentUnit) {
		this.incidentUnit = incidentUnit;
	}

	/**
	 * @return the incidentUnit
	 */
	public String getIncidentUnit() {
		return incidentUnit;
	}

	
}
