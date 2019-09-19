package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.CostGroupVo;

public class CostGroupData extends DialogueData {

	private CostGroupVo costGroupVo;
	
	public CostGroupVo getCostGroupVo() {
		return costGroupVo;
	}
	
	public void setCostGroupVo(CostGroupVo costGroupVo) {
		this.costGroupVo = costGroupVo;
	}
}
