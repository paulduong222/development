package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.TimeInvoiceData;
import gov.nwcg.isuite.framework.report.data.TimeInvoiceDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class OF286TimeInvoiceReportData extends TimeInvoiceData {

  private OF286EquipmentDetail equipmentDetails = new OF286EquipmentDetail();
  private Collection<OF286TimeInvoiceSubReportData> OF286InvoiceDetails = new ArrayList<OF286TimeInvoiceSubReportData>();
  private EmergencyEquipmentDandAReportData adjustmentData;
  private Date startDate;
  private Date endDate;
  
  public OF286TimeInvoiceReportData() {
  }

  /**
   * Returns the dataSource based on the OF286InvioceDetails collection.
   * 
   * @return the JRBeanCollectionDataSourc dataSource to return
   */
  public JRBeanCollectionDataSource getDataSource() {
    return new JRBeanCollectionDataSource(getOF286InvoiceDetails());
  }

  @Override
  public Date getPostStartDate() {
	  return this.startDate;
	  /*
    if(postStartDate == null) {
      Calendar psd = Calendar.getInstance();
      Calendar time = Calendar.getInstance();
      for(OF286TimeInvoiceSubReportData id : OF286InvoiceDetails) {
        time.set((int)id.getPostStartYear(), (int)id.getPostStartMonth()-1, (int)id.getPostStartDay());
        if (time.before(psd)) {
          psd.setTime(time.getTime());
        }
      }
      this.postStartDate = psd.getTime();
    }
    return postStartDate;
    */
  }

  @Override
  public Date getPostStopDate() {
	  return this.endDate;
	  /*
    if(postStopDate == null) {
      Calendar psd = Calendar.getInstance();
      Calendar time = Calendar.getInstance();
      int count = 0;
      for(OF286TimeInvoiceSubReportData id : OF286InvoiceDetails) {
        time.set((int)id.getPostStartYear(), (int)id.getPostStartMonth()-1, (int)id.getPostStartDay());
        if(time.after(psd) || count==0) {
          psd.setTime(time.getTime());
        }
        count++;
      }
      this.postStopDate = psd.getTime();
    }
    return postStopDate;
    */
  }

  public Double getDeductions() {
    if(adjustmentData != null){
      return adjustmentData.getTotalDeductions();
    } 
    return 0.0;
  }
  
  public Double getAdditions(){
    if(adjustmentData != null){
      return adjustmentData.getTotalAdditions();
    } 
    return 0.0;
  }
  
  public Collection<? extends TimeInvoiceDetail> getTimeInvoiceDetails() {
    return getOF286InvoiceDetails();
  }
  
  /**
   * @return the subReportData
   */
  public Collection<OF286TimeInvoiceSubReportData> getOF286InvoiceDetails() {
    return OF286InvoiceDetails;
  }

  /**
   * @param subReportData
   *          the subReportData to set
   */
  public void setOF286InvoiceDetails(
      Collection<OF286TimeInvoiceSubReportData> OF286InvoiceDetails) {
    this.OF286InvoiceDetails = OF286InvoiceDetails;
  }

  public void setAdjustmentData(EmergencyEquipmentDandAReportData adjustmentData) {
    this.adjustmentData = adjustmentData;
  }

  public EmergencyEquipmentDandAReportData getAdjustmentReports() {
    return adjustmentData;
  }

  public OF286EquipmentDetail getEquipmentDetails() {
    return equipmentDetails;
  }

  /* * * * * * * * * * * * * * * * * * * * * * * * *
   * populate the equipmentDetail object, to be inserted into the adjustment
   * records
   * * * * * * * * * * * * * * * * * * * * * * * * */
  public void setContractorName(String contractorName) {
    this.equipmentDetails.setContractorName(contractorName);
  }

  public void setDuns(String duns) {
	    this.equipmentDetails.setDuns(duns);
  }
  
  public void setContractorCity(String contractorCity) {
    this.equipmentDetails.setContractorCity(contractorCity);
  }

  public void setContractorStreet(String contractorStreet) {
    this.equipmentDetails.setContractorStreet(contractorStreet);
  }

  public void setContractorState(String contractorState) {
    this.equipmentDetails.setContractorState(contractorState);
  }

  public void setContractorZip(String contractorZip) {
    this.equipmentDetails.setContractorZip(contractorZip);
  }

  public void setContractorTINOrSSN(String contractorTINOrSSN) {
    this.equipmentDetails.setContractorTINOrSSN(contractorTINOrSSN);
  }

  public void setResourceItemName(String resourceItemName) {
    this.equipmentDetails.setResourceItemName(resourceItemName);
  }

  public void setResourceUniqueID(String resourceUniqueID) {
    this.equipmentDetails.setResourceUniqueID(resourceUniqueID);
  }

  public void setMake(String make) {
    this.equipmentDetails.setMake(make);
  }

  public void setModel(String model) {
    this.equipmentDetails.setModel(model);
  }

  public void setRequestNumber(String requestNumber) {
    this.equipmentDetails.setRequestNumber(requestNumber);
  }

  /**
   * @param adminOfficeForPaymentName
   *          the adminOfficeForPaymentName to set
   */
  public void setAdminOfficeForPaymentName(String adminOfficeForPaymentName) {
    this.equipmentDetails
        .setAdminOfficeForPaymentName(adminOfficeForPaymentName);
  }

  /**
   * @param adminOfficeForPaymentStreet
   *          the adminOfficeForPaymentStreet to set
   */
  public void setAdminOfficeForPaymentStreet(String adminOfficeForPaymentStreet) {
    this.equipmentDetails
        .setAdminOfficeForPaymentStreet(adminOfficeForPaymentStreet);
  }

  /**
   * @param adminOfficeForPaymentCity
   *          the adminOfficeForPaymentCity to set
   */
  public void setAdminOfficeForPaymentCity(String adminOfficeForPaymentCity) {
    this.equipmentDetails
        .setAdminOfficeForPaymentCity(adminOfficeForPaymentCity);
  }

  /**
   * @param adminOfficeForPaymentState
   *          the adminOfficeForPaymentState to set
   */
  public void setAdminOfficeForPaymentState(String adminOfficeForPaymentState) {
    this.equipmentDetails
        .setAdminOfficeForPaymentState(adminOfficeForPaymentState);
  }

  /**
   * @param adminOfficeForPaymentZip
   *          the adminOfficeForPaymentZip to set
   */
  public void setAdminOfficeForPaymentZip(String adminOfficeForPaymentZip) {
    this.equipmentDetails.setAdminOfficeForPaymentZip(adminOfficeForPaymentZip);
  }

/**
 * @return the startDate
 */
public Date getStartDate() {
	return startDate;
}

/**
 * @param startDate the startDate to set
 */
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

/**
 * @return the endDate
 */
public Date getEndDate() {
	return endDate;
}

/**
 * @param endDate the endDate to set
 */
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
}
