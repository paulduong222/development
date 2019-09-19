package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.SysCostRateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateVo;

public class SysCostRatesData extends DialogueData {

	private SysCostRateKindVo sysCostRateKindVo;
	private SysCostRateOvhdVo sysCostRateOvhdVo;
	private SysCostRateStateVo sysCostRateStateVo;
	private SysCostRateStateKindVo sysCostRateStateKindVo;

	public SysCostRateKindVo getSysCostRateKindVo() {
		return sysCostRateKindVo;
	}

	public void setSysCostRateKindVo(SysCostRateKindVo sysCostRateKindVo) {
		this.sysCostRateKindVo = sysCostRateKindVo;
	}

	public SysCostRateOvhdVo getSysCostRateOvhdVo() {
		return sysCostRateOvhdVo;
	}

	public void setSysCostRateOvhdVo(SysCostRateOvhdVo sysCostRateOvhdVo) {
		this.sysCostRateOvhdVo = sysCostRateOvhdVo;
	}

	public SysCostRateStateVo getSysCostRateStateVo() {
		return sysCostRateStateVo;
	}

	public void setSysCostRateStateVo(SysCostRateStateVo sysCostRateStateVo) {
		this.sysCostRateStateVo = sysCostRateStateVo;
	}

	public SysCostRateStateKindVo getSysCostRateStateKindVo() {
		return sysCostRateStateKindVo;
	}

	public void setSysCostRateStateKindVo(SysCostRateStateKindVo sysCostRateStateKindVo) {
		this.sysCostRateStateKindVo = sysCostRateStateKindVo;
	}

}
