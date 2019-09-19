package gov.nwcg.isuite.framework.report.data;

import java.util.Date;


/**
 * Time Invoice Report base details
 * 
 * @author aroundy
 * 
 */
public class TimeInvoiceDetail {

  protected final String boxChecked = "x";
  
  private String incidentName;
  private String incidentNumber;
  
  private Integer postStartDay;
  private Integer postStartMonth;
  private Integer postStartYear;
  
  private String accountingCode;
  private Boolean halfRate;
  
  /**
   * return boxChecked value for the report checkbox if the value is true
   */
  protected String getBoxChecked(Boolean value) {
    if (value != null && value == true) {
      return this.boxChecked;
    } else {
      return "";
    }
  }
  
  public String getHalfRateString() {
    if (this.halfRate) {
      return "HFRT";
    }
    return "";
  }

  public void setHalfRate(Boolean isHalfRate) {
    if (isHalfRate != null) {
      this.halfRate = isHalfRate;
    } else {
      this.halfRate = false;
    }
  }
  
  public Boolean getHalfRate() {
    return halfRate;
  }

  public String getAccountingCode() {
    return accountingCode;
  }
  
  public void setAccountingCode(String accountingCode) {
    this.accountingCode = accountingCode;
  }
  
  public String getIncidentName() {
    return incidentName;
  }
  
  public void setIncidentName(String incidentName) {
    this.incidentName = incidentName;
  }
  
  public String getIncidentNumber() {
    return incidentNumber;
  }
  
  public void setIncidentNumber(String incidentNumber) {
    this.incidentNumber = incidentNumber;
  }
  

  public int getPostStartYear() {
    return postStartYear;
  }
  
  public void setPostStartYear(int i) {
    this.postStartYear = i;
  }
      
  public Integer getPostStartMonth() {
    return postStartMonth;
  }

  public void setPostStartMonth(Integer postStartMonth) {
    this.postStartMonth = postStartMonth;
  }

  public Integer getPostStartDay() {
    return postStartDay;
  }

  public void setPostStartDay(Integer postStartDay) {
    this.postStartDay = postStartDay;
  }
    
}
