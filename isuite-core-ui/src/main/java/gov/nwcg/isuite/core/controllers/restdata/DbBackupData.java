package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.DbAvailVo;

public class DbBackupData {

	private DbAvailVo vo;
	private String destFileName;
	private String altDestination;

	public DbAvailVo getVo() {
		return vo;
	}

	public void setVo(DbAvailVo vo) {
		this.vo = vo;
	}

	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}

	public String getAltDestination() {
		return altDestination;
	}

	public void setAltDestination(String altDestination) {
		this.altDestination = altDestination;
	}

}
