package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.FinancialExportVo;

public class FinancialExportData extends DialogueData {

	private FinancialExportVo financialExportVo;
	
	public FinancialExportVo getFinancialExportVo() {
		return financialExportVo;
	}
	
	public void setFinancialExportVo(FinancialExportVo financialExportVo) {
		this.financialExportVo = financialExportVo;
	}
}
