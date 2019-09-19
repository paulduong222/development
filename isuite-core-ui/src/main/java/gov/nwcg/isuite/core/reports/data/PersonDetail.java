package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

/**
 * Person contact details
 * 
 * @author aroundy
 * 
 */
public class PersonDetail {

  private String firstName;
  private String lastName;
  private String SSN;
  private String city;
  private String state;
  private String streetAddress1;
  private String streetAddress2;
  private String zipCode;
  private String phoneNumber;
  private String faxNumber;
  private String requestNumber;
  
  private String accountingCode;

  private String inCaseOfAccidentNotifyName = "";
  private String inCaseOfAccidentNotifyStreetAddress = "";
  private String inCaseOfAccidentNotifyCity = "";
  private String inCaseOfAccidentNotifyState = "";
  private String inCaseOfAccidentNotifyPhoneNumber = "";

  public PersonDetail() {
  }
  
  /*
   * getters and setters
   */
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String resourceFirstName) {
    this.firstName = resourceFirstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String resourceLastName) {
    this.lastName = resourceLastName;
  }

  public String getSSN() {
    return SSN;
  }

  public void setSSN(String resourceSSN) {
    this.SSN = resourceSSN;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String resourceCity) {
    this.city = resourceCity;
  }

  public String getState() {
    return state;
  }

  public void setState(String resourceState) {
    this.state = resourceState;
  }

  public String getStreetAddress1() {
    return streetAddress1 + (StringUtility.hasValue(streetAddress2) ? " / " + streetAddress2 : "");
  }

  public void setStreetAddress1(String resourceStreetAddress1) {
    this.streetAddress1 = resourceStreetAddress1;
  }

  public String getStreetAddress2() {
    return streetAddress2;
  }

  public void setStreetAddress2(String resourceStreetAddress2) {
    this.streetAddress2 = resourceStreetAddress2;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String resourceZipCode) {
    this.zipCode = resourceZipCode;
  }

  public String getPhoneNumber() {
	  return PhoneNumberUtil.formatNumber(phoneNumber);
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getFaxNumber() {
	  return PhoneNumberUtil.formatNumber(faxNumber);
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }
 
  
  public String getAccountingCode() {
    return accountingCode;
  }
  

  
  public void setAccountingCode(String accountingCode) {
    this.accountingCode = accountingCode;
  }
  

  /**
   * @return the inCaseOfAccidentNotifyName
   */
  public String getInCaseOfAccidentNotifyName() {
    return inCaseOfAccidentNotifyName;
  }

  /**
   * @param inCaseOfAccidentNotifyName
   *          the inCaseOfAccidentNotifyName to set
   */
  public void setInCaseOfAccidentNotifyName(String inCaseOfAccidentNotifyName) {
    this.inCaseOfAccidentNotifyName = inCaseOfAccidentNotifyName;
  }

  /**
   * @return the inCaseOfAccidentNotifyStreetAddress
   */
  public String getInCaseOfAccidentNotifyStreetAddress() {
    return inCaseOfAccidentNotifyStreetAddress;
  }

  /**
   * @param inCaseOfAccidentNotifyStreetAddress
   *          the inCaseOfAccidentNotifyStreetAddress to set
   */
  public void setInCaseOfAccidentNotifyStreetAddress(String inCaseOfAccidentNotifyStreetAddress) {
    this.inCaseOfAccidentNotifyStreetAddress = inCaseOfAccidentNotifyStreetAddress;
  }

  /**
   * @return the inCaseOfAccidentNotifyCity
   */
  public String getInCaseOfAccidentNotifyCity() {
    return inCaseOfAccidentNotifyCity;
  }

  /**
   * @param inCaseOfAccidentNotifyCity
   *          the inCaseOfAccidentNotifyCity to set
   */
  public void setInCaseOfAccidentNotifyCity(String inCaseOfAccidentNotifyCity) {
    this.inCaseOfAccidentNotifyCity = inCaseOfAccidentNotifyCity;
  }

  /**
   * @return the inCaseOfAccidentNotifyState
   */
  public String getInCaseOfAccidentNotifyState() {
    return inCaseOfAccidentNotifyState;
  }

  /**
   * @param inCaseOfAccidentNotifyState
   *          the inCaseOfAccidentNotifyState to set
   */
  public void setInCaseOfAccidentNotifyState(String inCaseOfAccidentNotifyState) {
    this.inCaseOfAccidentNotifyState = inCaseOfAccidentNotifyState;
  }

  /**
   * @return the inCaseOfAccidentNotifyPhoneNumber
   */
  public String getInCaseOfAccidentNotifyPhoneNumber() {
    return inCaseOfAccidentNotifyPhoneNumber;
  }

  /**
   * @param inCaseOfAccidentNotifyPhoneNumber
   *          the inCaseOfAccidentNotifyPhoneNumber to set
   */
  public void setInCaseOfAccidentNotifyPhoneNumber(String inCaseOfAccidentNotifyPhoneNumber) {
    this.inCaseOfAccidentNotifyPhoneNumber = inCaseOfAccidentNotifyPhoneNumber;
  }

public String getRequestNumber() {
	return requestNumber;
}

public void setRequestNumber(String requestNumber) {
	this.requestNumber = requestNumber;
}
  
}
