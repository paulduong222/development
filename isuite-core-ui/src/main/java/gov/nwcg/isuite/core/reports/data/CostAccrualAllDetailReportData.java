package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;

public class CostAccrualAllDetailReportData {
	private String header1;
	private String header2;
	private String header3;
	private String header4;
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
	
	public CostAccrualAllDetailReportData(){
		super();
	}

	/**
	 * @return the header1
	 */
	public String getHeader1() {
		return header1;
	}

	/**
	 * @param header1 the header1 to set
	 */
	public void setHeader1(String header1) {
		this.header1 = header1;
	}

	/**
	 * @return the header2
	 */
	public String getHeader2() {
		return header2;
	}

	/**
	 * @param header2 the header2 to set
	 */
	public void setHeader2(String header2) {
		this.header2 = header2;
	}

	/**
	 * @return the header3
	 */
	public String getHeader3() {
		return header3;
	}

	/**
	 * @param header3 the header3 to set
	 */
	public void setHeader3(String header3) {
		this.header3 = header3;
	}

	/**
	 * @return the header4
	 */
	public String getHeader4() {
		return header4;
	}

	/**
	 * @param header4 the header4 to set
	 */
	public void setHeader4(String header4) {
		this.header4 = header4;
	}

	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public String getRcLineNumber() {
		return rcLineNumber;
	}

	public void setRcLineNumber(String rcLineNumber) {
		this.rcLineNumber = rcLineNumber;
	}

	public String getAccrualDescription() {
		return accrualDescription;
	}

	public void setAccrualDescription(String accrualDescription) {
		this.accrualDescription = accrualDescription;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Boolean getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
}
