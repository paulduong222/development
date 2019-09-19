package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;

public class CostAccrualGroupVo {
	private String accrualCode;
	private BigDecimal totalAmount;
	private BigDecimal changeAmount;
	private String accountCode;
	private Long accountCodeId;
	private Long incidentResourceId;
	private String drawdown;
	private String fiscalYear;
	
	/**
	 * 
	 */
	public CostAccrualGroupVo() {}

	/**
	 * @return the accrualCode
	 */
	public String getAccrualCode() {
		return accrualCode;
	}

	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(String accrualCode) {
		this.accrualCode = accrualCode;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountCodeId
	 */
	public Long getAccountCodeId() {
		return accountCodeId;
	}

	/**
	 * @param accountCodeId the accountCodeId to set
	 */
	public void setAccountCodeId(Long accountCodeId) {
		this.accountCodeId = accountCodeId;
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
	 * @return the drawdown
	 */
	public String getDrawdown() {
		return drawdown;
	}

	/**
	 * @param drawdown the drawdown to set
	 */
	public void setDrawdown(String drawdown) {
		this.drawdown = drawdown;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

}
