package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.TimeInvoiceData;
import gov.nwcg.isuite.framework.report.data.TimeInvoiceDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for EmergencyEquipmentDandA.jrxml.
 */
public class EmergencyEquipmentDandAReportData extends TimeInvoiceData {

  private OF286EquipmentDetail equipmentDetail;
  private Collection<EmergencyEquipmentDandASubReportData> reportData = new ArrayList<EmergencyEquipmentDandASubReportData>();

  public EmergencyEquipmentDandAReportData() {
  }

  /**
   * @return the JRBeanCollectionDataSource dataSource
   */
  public JRBeanCollectionDataSource getDataSource() {
    return new JRBeanCollectionDataSource(this.getReportData());
  }

  public OF286EquipmentDetail getEquipmentDetail() {
    return equipmentDetail;
  }

  public void setEquipmentDetail(OF286EquipmentDetail equipmentDetail) {
    this.equipmentDetail = equipmentDetail;
  }

  public Collection<EmergencyEquipmentDandASubReportData> getReportData() {
    return reportData;
  }

  public void setReportData(
      Collection<EmergencyEquipmentDandASubReportData> reportData) {
    this.reportData = reportData;
  }

  @Override
  public Date getPostStartDate() {
    return postStartDate;
  }
  
  public void setPostStartDate(Date start) {
    this.postStartDate = start;
  }
  
  @Override
  public Date getPostStopDate() {
    return postStopDate;
  }

  public void setPostStopDate(Date stop) {
    this.postStopDate = stop;
  }
  
  public Double getTotalDeductions() {
    Double deductions = 0.0;
    for(EmergencyEquipmentDandASubReportData data : reportData) {
      deductions += data.getDeduction();
    }
    return deductions;
  }
  
  public Double getTotalAdditions() {
    Double additions = 0.0;
    for(EmergencyEquipmentDandASubReportData data : reportData) {
      additions += data.getAddition();
    }
    return additions;
  }

  @Override
  public Collection<? extends TimeInvoiceDetail> getTimeInvoiceDetails() {
    return null;
  }
  
}
