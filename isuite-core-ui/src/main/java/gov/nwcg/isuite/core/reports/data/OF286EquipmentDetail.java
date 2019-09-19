package gov.nwcg.isuite.core.reports.data;

/**
 * OF286 Equipment details for subreport poplulation
 * 
 * @author aroundy
 * 
 */
public class OF286EquipmentDetail {

  private String contractorName;
  private String contractorCity;
  private String contractorStreet;
  private String contractorState;
  private String contractorZip;

  private String contractorTINOrSSN;
  
  private String resourceItemName;
  private String resourceUniqueID;
  private String make;
  private String model;

  private String requestNumber;
  
  private String adminOfficeForPaymentName;
  private String adminOfficeForPaymentStreet;
  private String adminOfficeForPaymentCity;
  private String adminOfficeForPaymentState;
  private String adminOfficeForPaymentZip;

  private String duns;
  
  public OF286EquipmentDetail() {}
  
  public String getContractorName() {
    return contractorName;
  }

  public void setContractorName(String contractorName) {
    this.contractorName = contractorName;
  }

  public String getContractorCity() {
    return contractorCity;
  }

  public void setContractorCity(String contractorCity) {
    this.contractorCity = contractorCity;
  }

  public String getContractorStreet() {
    return contractorStreet;
  }

  public void setContractorStreet(String contractorStreet) {
    this.contractorStreet = contractorStreet;
  }

  public String getContractorState() {
    return contractorState;
  }

  public void setContractorState(String contractorState) {
    this.contractorState = contractorState;
  }

  public String getContractorZip() {
    return contractorZip;
  }

  public void setContractorZip(String contractorZip) {
    this.contractorZip = contractorZip;
  }

  public String getContractorTINOrSSN() {
    return contractorTINOrSSN;
  }

  public void setContractorTINOrSSN(String contractorTINOrSSN) {
    this.contractorTINOrSSN = contractorTINOrSSN;
  }

  public String getResourceItemName() {
    return resourceItemName;
  }

  public void setResourceItemName(String resourceItemName) {
    this.resourceItemName = resourceItemName;
  }

  public String getResourceUniqueID() {
    return resourceUniqueID;
  }

  public void setResourceUniqueID(String resourceUniqueID) {
    this.resourceUniqueID = resourceUniqueID;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getRequestNumber() {
    return requestNumber;
  }

  public void setRequestNumber(String requestNumber) {
    this.requestNumber = requestNumber;
  }

  public String getAdminOfficeForPaymentName() {
    return adminOfficeForPaymentName;
  }

  public void setAdminOfficeForPaymentName(String adminOfficeForPaymentName) {
    this.adminOfficeForPaymentName = adminOfficeForPaymentName;
  }

  public String getAdminOfficeForPaymentStreet() {
    return adminOfficeForPaymentStreet;
  }

  public void setAdminOfficeForPaymentStreet(String adminOfficeForPaymentStreet) {
    this.adminOfficeForPaymentStreet = adminOfficeForPaymentStreet;
  }

  public String getAdminOfficeForPaymentCity() {
    return adminOfficeForPaymentCity;
  }

  public void setAdminOfficeForPaymentCity(String adminOfficeForPaymentCity) {
    this.adminOfficeForPaymentCity = adminOfficeForPaymentCity;
  }

  public String getAdminOfficeForPaymentState() {
    return adminOfficeForPaymentState;
  }

  public void setAdminOfficeForPaymentState(String adminOfficeForPaymentState) {
    this.adminOfficeForPaymentState = adminOfficeForPaymentState;
  }

  public String getAdminOfficeForPaymentZip() {
    return adminOfficeForPaymentZip;
  }

  public void setAdminOfficeForPaymentZip(String adminOfficeForPaymentZip) {
    this.adminOfficeForPaymentZip = adminOfficeForPaymentZip;
  }

/**
 * @return the duns
 */
public String getDuns() {
	return duns;
}

/**
 * @param duns the duns to set
 */
public void setDuns(String duns) {
	this.duns = duns;
}
  
  
}
