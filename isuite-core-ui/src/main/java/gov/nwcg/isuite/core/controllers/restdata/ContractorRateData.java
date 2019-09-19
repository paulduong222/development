package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.filter.impl.ContractorRateFilterImpl;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;

public class ContractorRateData extends DialogueData {

	private ContractorRateVo contractorRateVo;
	private WorkPeriodVo workPeriodVo;
	private ContractorRateFilterImpl contractorRateFilter;

	public ContractorRateVo getContractorRateVo() {
		return contractorRateVo;
	}

	public void setContractorRateVo(ContractorRateVo contractorRateVo) {
		this.contractorRateVo = contractorRateVo;
	}

	public WorkPeriodVo getWorkPeriodVo() {
		return workPeriodVo;
	}

	public void setWorkPeriodVo(WorkPeriodVo workPeriodVo) {
		this.workPeriodVo = workPeriodVo;
	}
	
	public ContractorRateFilterImpl getContractorRateFilter() {
		return contractorRateFilter;
	}
	
	public void setContractorRateFilter(ContractorRateFilterImpl contractorRateFilter) {
		this.contractorRateFilter = contractorRateFilter;
	}

}
