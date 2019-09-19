package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.TimeInvoiceDetail;

import java.util.Date;


/**
 * OF288 Time Invoice Report time set details
 * 
 * @author aroundy
 * 
 */
public class OF288TimeDetail extends TimeInvoiceDetail {


  private String fireFighterClassification;
  private Double rate;
    
  private String incidentLocation;
  private String incidentState;
  
  private Date firstDateToIncludeOnReport;
  private Date lastDateToIncludeOnReport;
  
  private int year;
  
  private Integer postStartHour;
  private Integer postStartMinute;
  private Integer postStopHour;
  private Integer postStopMinute;
  private Double hours;
  private Boolean startTimeOnly;
  
  private String specialPayCode;
  private Long specialPayId;
  
  private Boolean showStartStop;
  private Boolean showHoursSpecial;
  private Boolean showHours;
  
  private String employeeType;
  private String groupIndex="a";

  private String adClass;
  private String kindCode;
  private String requestNumber;
  
  /**
   * This returns the concatenated string of header information. 
   * If any of the information is different, then an additional column on the OF288
   * is required for the data.
   * 
   * @return
   */
  public String getHeader() {
    //TODO (May 23, 2011): should this just be toString() ?
    return getIncidentName() + getIncidentNumber() + getIncidentLocation() + getIncidentState() +
      getAccountingCode() + getFireFighterClassification() + getRate(); // + getLastDateToIncludeOnReport();
  }
  
  
  /*
   * getters and setters
   */
  
  public String getFireFighterClassification() {
    return fireFighterClassification;
  }
  
  public void setFireFighterClassification(String fireFighterClassification) {
    this.fireFighterClassification = fireFighterClassification;
  }
  
  public Double getRate() {
    return rate;
  }
  
  public void setRate(Double rate) {
    this.rate = rate;
  }
  
  public String getIncidentLocation() {
    return incidentLocation;
  }
  
  public void setIncidentLocation(String incidentLocation) {
    this.incidentLocation = incidentLocation;
  }
  
  public String getIncidentState() {
    return incidentState;
  }
  
  public void setIncidentState(String incidentState) {
    this.incidentState = incidentState;
  }
  
  public Date getFirstDateToIncludeOnReport() {
    return firstDateToIncludeOnReport;
  }
  
  public void setFirstDateToIncludeOnReport(Date firstDateToIncludeOnReport) {
    this.firstDateToIncludeOnReport = firstDateToIncludeOnReport;
  }
  
  public Date getLastDateToIncludeOnReport() {
    return lastDateToIncludeOnReport;
  }
  
  public void setLastDateToIncludeOnReport(Date lastDateToIncludeOnReport) {
    this.lastDateToIncludeOnReport = lastDateToIncludeOnReport;
  }
  
  public int getYear() {
    return year;
  }
  
  public void setYear(int i) {
    this.year = i;
  }
      
  public Integer getPostStartHour() {
    return postStartHour;
  }

  public void setPostStartHour(Integer postStartHour) {
    this.postStartHour = postStartHour;
  }

  public Integer getPostStartMinute() {
    return postStartMinute;
  }

  public void setPostStartMinute(Integer postStartMinute) {
    this.postStartMinute = postStartMinute;
  }

  public Integer getPostStopHour() {
    return postStopHour;
  }

  public void setPostStopHour(Integer postStopHour) {
    this.postStopHour = postStopHour;
  }

  public Integer getPostStopMinute() {
    return postStopMinute;
  }

  public void setPostStopMinute(Integer postStopMinute) {
    this.postStopMinute = postStopMinute;
  }

  public Double getHours() {
    return hours;
  }

  public void setHours(Double hours) {
    this.hours = hours;
  }

  private String formatTime(Integer hour, Integer minute) {
    String rtn="";
    if(null != hour && !hour.toString().isEmpty()){
      if(hour < 10) {
        rtn="0"+hour.toString();
      } else {
        rtn=hour.toString();
      }
      if(null != minute && !minute.toString().isEmpty()){
        if(minute < 10) {
          rtn=rtn+":0"+minute.toString();
        } else {
          rtn=rtn+":"+minute.toString();
        }
      } else {
        //TODO (may 26, 2011): should we throw and error if hour has a value but minute is empty?
        rtn=rtn+":00";
      }
      return rtn;
    }
    return null;
  }
  /**
   * Create StartTime if StartHour or StartMinute are not empty or null, 
   * otherwise return null
   * @return
   */
  public String getStartTime() {
    return formatTime(this.postStartHour, this.postStartMinute);
//    String rtn="";
//    if(null != this.postStartHour && !this.postStartHour.toString().isEmpty()){
//      if(this.postStartHour < 10) {
//        rtn="0"+postStartHour.toString();
//      } else {
//        rtn=rtn+postStartHour.toString();
//      }
//      if(null != this.postStartMinute && !this.postStartMinute.toString().isEmpty()){
//        if(this.postStartMinute < 10) {
//          rtn=rtn+":0"+postStartMinute.toString();
//        } else {
//          rtn=rtn+":"+postStartMinute.toString();
//        }
//      } else {
//        //TODO (may 26, 2011): should we throw and error if hour has a value but minute is empty?
//        rtn=rtn+":00";
//      }
//      return rtn;
//    }
//    return null;
  }

  public void setStartTime(String startTime) {
  //TODO (may 20, 2011): use this method to set the postStart times? or delete?
  }

  public String getStopTime() {
    return formatTime(this.postStopHour, this.postStopMinute);
//    String rtn="";
//    if(null != this.postStopHour && !this.postStopHour.toString().isEmpty()){
//      if(this.postStopHour < 10 ){
//        rtn="0"+ postStopHour.toString();
//      } else {
//        rtn=postStopHour.toString();
//      }
//      if(null != this.postStopMinute && !this.postStopMinute.toString().isEmpty()){
//        if(this.postStopMinute < 10 ){
//          rtn=rtn+":0"+ postStopMinute.toString();
//        } else {
//          rtn=rtn+":"+postStopMinute.toString();
//        }
//      } else {
//        //TODO (may 26, 2011): should we throw and error if hour has a value but minute is empty?
//        rtn=rtn+":00";
//      }
//      return rtn;
//    }
//    return null;
  }

  public void setStopTime(String stopTime) {
    //TODO (may 20, 2011): use this method to set the postStop times? or delete?
  }

  public String getSpecialPayCode() {
    return specialPayCode;
  }

  public void setSpecialPayCode(String specialPayCode) {
    this.specialPayCode = specialPayCode;
  }
  
  public Long getSpecialPayId() {
    return specialPayId;
  }

  public void setSpecialPayId(Long specialPayId) {
    this.specialPayId = specialPayId;
  }

  public Boolean getStartTimeOnly() {
    return startTimeOnly;
  }
  
  public void setStartTimeOnly(Boolean startTimeOnly) {
    this.startTimeOnly = startTimeOnly;
  }


  
  public String getEmployeeType() {
    return employeeType;
  }
  


  public void setEmployeeType(String employeeType) {
    this.employeeType = employeeType;
  }


/**
 * @return the groupIndex
 */
public String getGroupIndex() {
	return groupIndex;
}


/**
 * @param groupIndex the groupIndex to set
 */
public void setGroupIndex(String groupIndex) {
	this.groupIndex = groupIndex;
}


/**
 * @return the showStartStop
 */
public Boolean getShowStartStop() {
	return showStartStop;
}


/**
 * @param showStartStop the showStartStop to set
 */
public void setShowStartStop(Boolean showStartStop) {
	this.showStartStop = showStartStop;
}


/**
 * @return the showHoursSpecial
 */
public Boolean getShowHoursSpecial() {
	return showHoursSpecial;
}


/**
 * @param showHoursSpecial the showHoursSpecial to set
 */
public void setShowHoursSpecial(Boolean showHoursSpecial) {
	this.showHoursSpecial = showHoursSpecial;
}


/**
 * @return the showHours
 */
public Boolean getShowHours() {
	return showHours;
}


/**
 * @param showHours the showHours to set
 */
public void setShowHours(Boolean showHours) {
	this.showHours = showHours;
}


public String getAdClass() {
	return adClass;
}


public void setAdClass(String adClass) {
	this.adClass = adClass;
}


public String getKindCode() {
	return kindCode;
}


public void setKindCode(String kindCode) {
	this.kindCode = kindCode;
}


public String getRequestNumber() {
	return requestNumber;
}


public void setRequestNumber(String requestNumber) {
	this.requestNumber = requestNumber;
}

  
}
