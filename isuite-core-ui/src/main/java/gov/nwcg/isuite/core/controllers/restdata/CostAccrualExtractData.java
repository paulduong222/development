package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;

public class CostAccrualExtractData extends DialogueData {

	private String extractDate;
	private CostAccrualExtractVo costAccrualExtractVo;
	
	public String getExtractDate() {
		return extractDate;
	}
	public void setExtractDate(String extractDate) {
		this.extractDate = extractDate;
	}
	
	public CostAccrualExtractVo getCostAccrualExtractVo() {
		return costAccrualExtractVo;
	}
	
	public void setCostAccrualExtractVo(CostAccrualExtractVo costAccrualExtractVo) {
		this.costAccrualExtractVo = costAccrualExtractVo;
	}
}
