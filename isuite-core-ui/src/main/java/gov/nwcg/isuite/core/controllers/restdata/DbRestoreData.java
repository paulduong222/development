package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;

public class DbRestoreData extends DialogueData {

	private String filename;
	private String targetDbName;
	private String pwd;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTargetDbName() {
		return targetDbName;
	}

	public void setTargetDbName(String targetDbName) {
		this.targetDbName = targetDbName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
