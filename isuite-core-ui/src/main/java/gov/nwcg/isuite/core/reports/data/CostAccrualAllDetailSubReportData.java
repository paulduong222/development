package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;

public class CostAccrualAllDetailSubReportData {
	private Long incidentResourceId;
	private String fiscalYear;
	private String accountingCode;
	private String accountingCodeRcDesc;
	private String rcLineNumber;
	private String accrualDescription;
	private Double totalAmount;
	private Double previousAmount;
	private Double changeAmount;
	private String requestNumber;
	private Boolean isPerson;
	private String lastName;
	private String firstName;
	private String resourceName;
	private String itemCode;
	private String unitCode;
	private Long   incidentId;
	private String incidentName;
	private String incidentNumber;
	
	public CostAccrualAllDetailSubReportData(){
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
		if(null != val) {
			this.changeAmount=val.doubleValue();
		}else
			this.changeAmount = 0.0;
	}

	public void setTotalAmountBigDecimal(BigDecimal val) {
		if(null != val) {
			this.totalAmount=val.doubleValue();
		}else
			this.totalAmount = 0.0;
	}

	public void setPreviousAmountBigDecimal(BigDecimal val) {
		if(null != val) {
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
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the isPerson
	 */
	public Boolean getIsPerson() {
		return isPerson;
	}

	/**
	 * @param isPerson the isPerson to set
	 */
	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the accountingCodeRcDesc
	 */
	public String getAccountingCodeRcDesc() {
		return this.accountingCode + this.fiscalYear + " - " + this.accrualDescription;
		//return accountingCodeRcDesc;
	}

	/**
	 * @param accountingCodeRcDesc the accountingCodeRcDesc to set
	 */
	public void setAccountingCodeRcDesc(String accountingCodeRcDesc) {
		this.accountingCodeRcDesc = accountingCodeRcDesc;
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
	
}
