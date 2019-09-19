package gov.nwcg.isuite.core.reports.data;



/**
 * Report data object for UserPasswordExpired.jrxml.
 */
public class PasswordExpiredReportData {
	
	private String firstName;
	private String lastName;
	private Integer numberOfExpiredPasswords;
	

	public PasswordExpiredReportData(){
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
	 * @return the numberOfExpiredPasswords
	 */
	public Integer getNumberOfExpiredPasswords() {
		return numberOfExpiredPasswords;
	}


	/**
	 * @param numberOfExpiredPasswords the numberOfExpiredPasswords to set
	 */
	public void setNumberOfExpiredPasswords(Integer numberOfExpiredPasswords) {
		this.numberOfExpiredPasswords = numberOfExpiredPasswords;
	}

	
	
}
