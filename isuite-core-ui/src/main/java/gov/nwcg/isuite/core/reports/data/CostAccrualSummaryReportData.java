package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;

public class CostAccrualSummaryReportData {
	private Long   incidentResourceId;
	private String fiscalYear;
	private String accountingCode;
	private String rcLineNumber;
	private String accrualDescription;
	private Double totalAmount;
	private Double previousAmount;
	private Double changeAmount;
	private Long   incidentId;
	private String incidentName;
	private String incidentNumber;
	
	public CostAccrualSummaryReportData(){
		super();
	}

	/**
	 * @return the accountingCode
	 */
	public String getAccountingCode() {
		return accountingCode + fiscalYear;
	}

	/**
	 * @param accountingCode the accountingCode to set
	 */
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	/**
	 * @return the rcLineNumber
	 */
	public String getRcLineNumber() {
		return rcLineNumber;
	}

	/**
	 * @param rcLineNumber the rcLineNumber to set
	 */
	public void setRcLineNumber(String rcLineNumber) {
		this.rcLineNumber = rcLineNumber;
	}

	/**
	 * @return the accrualDescription
	 */
	public String getAccrualDescription() {
		return accrualDescription;
	}

	/**
	 * @param accrualDescription the accrualDescription to set
	 */
	public void setAccrualDescription(String accrualDescription) {
		this.accrualDescription = accrualDescription;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the previousAmount
	 */
	public Double getPreviousAmount() {
		return previousAmount;
	}

	/**
	 * @param previousAmount the previousAmount to set
	 */
	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public Double getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public void setChangeAmountBigDecimal(BigDecimal val) {
		if(null != val){
			this.changeAmount=val.doubleValue();
		}else
			this.changeAmount = 0.0;
	}

	public void setTotalAmountBigDecimal(BigDecimal val) {
		if(null != val){
			this.totalAmount=val.doubleValue();
		}else
			this.totalAmount = 0.0;
	}

	public void setPreviousAmountBigDecimal(BigDecimal val) {
		if(null != val){
			this.previousAmount=val.doubleValue();
		}else
			this.previousAmount = 0.0;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @param fiscalYear the fiscalYear to set
	 */
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return the fiscalYear
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
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
}
