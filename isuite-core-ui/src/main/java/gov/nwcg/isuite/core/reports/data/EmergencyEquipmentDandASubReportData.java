package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

public class EmergencyEquipmentDandASubReportData {

  private String incidentName;
  private String accountingCode;
  private String agreementNumber;
  
	private Date activityDate;
	private String description;
	private String adjustmentType;
	private Double amount;
	
	public EmergencyEquipmentDandASubReportData() {}

	/**
	 * 
	 * @return
	 */
  public String getIncidentName() {
    return incidentName;
  }

  /**
   * 
   * @param incidentName
   */
  public void setIncidentName(String incidentName) {
    this.incidentName = incidentName;
  }

  /**
   * 
   * @return
   */
  public String getAccountingCode() {
    return accountingCode;
  }

  /**
   * 
   * @param accountingCode
   */
  public void setAccountingCode(String accountingCode) {
    this.accountingCode = accountingCode;
  }

  /**
   * 
   * @return
   */
  public String getAgreementNumber() {
    return agreementNumber;
  }

  /**
   * 
   * @param agreementNumber
   */
  public void setAgreementNumber(String agreementNumber) {
    this.agreementNumber = agreementNumber;
  }
  
	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the deduction
	 */
	public Double getDeduction() {
		if(adjustmentType.equalsIgnoreCase("deduction")) {
		  return amount;
		}
		return 0.0;
	}

	/**
	 * @param deduction the deduction to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the addition
	 */
	public Double getAddition() {
	  if(adjustmentType.equalsIgnoreCase("addition")) {
      return amount;
    }
    return 0.0;
	}

	/**
	 * @param addition the addition to set
	 */
	public void setAdjustmentType(String type) {
		this.adjustmentType = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
