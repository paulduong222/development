package gov.nwcg.isuite.core.reports.data;


public class TaskUserPwdExpirationReportData {
	private String loginName;
	private String userName;
	private String expirationDate;
	

	public TaskUserPwdExpirationReportData(){
	}


	/**
	 * Returns the loginName.
	 *
	 * @return 
	 *		the loginName to return
	 */
	public String getLoginName() {
		return loginName;
	}


	/**
	 * Sets the loginName.
	 *
	 * @param loginName 
	 *			the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	/**
	 * Returns the userName.
	 *
	 * @return 
	 *		the userName to return
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * Sets the userName.
	 *
	 * @param userName 
	 *			the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * Returns the expirationDate.
	 *
	 * @return 
	 *		the expirationDate to return
	 */
	public String getExpirationDate() {
		return expirationDate;
	}


	/**
	 * Sets the expirationDate.
	 *
	 * @param expirationDate 
	 *			the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	
}
