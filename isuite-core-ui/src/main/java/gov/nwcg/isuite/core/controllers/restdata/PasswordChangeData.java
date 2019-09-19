package gov.nwcg.isuite.core.controllers.restdata;

public class PasswordChangeData {

	private String curPwd;
	private String newPwd;
	private String confPwd;

	public String getCurPwd() {
		return curPwd;
	}

	public void setCurPwd(String curPwd) {
		this.curPwd = curPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfPwd() {
		return confPwd;
	}

	public void setConfPwd(String confPwd) {
		this.confPwd = confPwd;
	}

	@Override
	public String toString() {
		return "PasswordChangeData [curPwd=" + curPwd + ", newPwd=" + newPwd + ", confPwd=" + confPwd + "]";
	}
}
