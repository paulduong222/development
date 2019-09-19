package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;

import java.util.ArrayList;
import java.util.Collection;

public class MatchByRequestNumberWizardVo {
	private Collection<RossImportProcessResourceVo> rossMatchReqNumVos = new ArrayList<RossImportProcessResourceVo>();
	private Collection<RossImportProcessEISuiteResourceVo> eisuiteMatchReqNumVos = new ArrayList<RossImportProcessEISuiteResourceVo>();

	public MatchByRequestNumberWizardVo(){
		
	}

	public static Boolean exists(RossImportProcessResourceVo vo, Collection<RossImportProcessResourceVo> vos ) {
		
		for(RossImportProcessResourceVo v : vos){
			if(v.getRossResReqId().compareTo(vo.getRossResReqId())==0)
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return the rossMatchReqNumVos
	 */
	public Collection<RossImportProcessResourceVo> getRossMatchReqNumVos() {
		return rossMatchReqNumVos;
	}

	/**
	 * @param rossMatchReqNumVos the rossMatchReqNumVos to set
	 */
	public void setRossMatchReqNumVos(
			Collection<RossImportProcessResourceVo> rossMatchReqNumVos) {
		this.rossMatchReqNumVos = rossMatchReqNumVos;
	}

	/**
	 * @return the eISuiteMatchReqNumVos
	 */
	public Collection<RossImportProcessEISuiteResourceVo> getEisuiteMatchReqNumVos() {
		return eisuiteMatchReqNumVos;
	}

	/**
	 * @param suiteMatchReqNumVos the eISuiteMatchReqNumVos to set
	 */
	public void setEisuiteMatchReqNumVos(
			Collection<RossImportProcessEISuiteResourceVo> suiteMatchReqNumVos) {
		eisuiteMatchReqNumVos = suiteMatchReqNumVos;
	}
}
