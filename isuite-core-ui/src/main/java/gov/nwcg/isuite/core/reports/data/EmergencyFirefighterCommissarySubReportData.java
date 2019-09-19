package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

public class EmergencyFirefighterCommissarySubReportData {

	private Date purchaseDate;
	private String categoryName;
	private String item;
	private Double amount;

	// new form fields 12/2015
	private String accountingCode;
	private String month;
	private String day;


	public EmergencyFirefighterCommissarySubReportData() {}

	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param date
	 *          the date to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *          the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *          the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *          the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public String getMonth() {
		if(DateUtil.hasValue(this.purchaseDate)){
			String d = DateUtil.toDateString(this.purchaseDate, DateUtil.MM);
			return d;
		}else
			return "";
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		if(DateUtil.hasValue(this.purchaseDate)){
			String d = DateUtil.toDateString(this.purchaseDate, DateUtil.DD);
			return d;
		}else
			return "";
	}

	public void setDay(String day) {
		this.day = day;
	}

}
