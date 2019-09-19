package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.DbAvailVo;

public class DbCopyData {

	private DbAvailVo sourceVo;
	private DbAvailVo targetVo;
	public DbAvailVo getSourceVo() {
		return sourceVo;
	}
	public void setSourceVo(DbAvailVo sourceVo) {
		this.sourceVo = sourceVo;
	}
	public DbAvailVo getTargetVo() {
		return targetVo;
	}
	public void setTargetVo(DbAvailVo targetVo) {
		this.targetVo = targetVo;
	}
	

}
