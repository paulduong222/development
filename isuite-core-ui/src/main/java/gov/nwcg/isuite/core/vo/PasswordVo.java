package gov.nwcg.isuite.core.vo;

public class PasswordVo {
	// only used for display purposes
	private String basePassword="Ww!2E4@m9y89";
	
	// only used when password changes
	private String newPassword="Ww!2E4@m9y89";
	

	public PasswordVo(){
		
	}


	public String getBasePassword() {
		return basePassword;
	}


	public void setBasePassword(String basePassword) {
		this.basePassword = basePassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
