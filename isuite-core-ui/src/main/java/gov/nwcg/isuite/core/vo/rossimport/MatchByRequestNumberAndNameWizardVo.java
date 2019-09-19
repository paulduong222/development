package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;

import java.util.ArrayList;
import java.util.Collection;

public class MatchByRequestNumberAndNameWizardVo {
	private Collection<RossImportProcessResourceVo> rossMatchReqNumNameVos = new ArrayList<RossImportProcessResourceVo>();
	private Collection<RossImportProcessEISuiteResourceVo> eisuiteMatchReqNumNameVos = new ArrayList<RossImportProcessEISuiteResourceVo>();
	private Boolean confirmMatches=false;
	
	public MatchByRequestNumberAndNameWizardVo(){
		
	}

	public static Boolean exists(RossImportProcessResourceVo vo, Collection<RossImportProcessResourceVo> vos ) {
		
		for(RossImportProcessResourceVo v : vos){
			if(v.getRossResReqId().compareTo(vo.getRossResReqId())==0)
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return the rossMatchReqNumNameVos
	 */
	public Collection<RossImportProcessResourceVo> getRossMatchReqNumNameVos() {
		return rossMatchReqNumNameVos;
	}

	/**
	 * @param rossMatchReqNumNameVos the rossMatchReqNumNameVos to set
	 */
	public void setRossMatchReqNumNameVos(
			Collection<RossImportProcessResourceVo> rossMatchReqNumNameVos) {
		this.rossMatchReqNumNameVos = rossMatchReqNumNameVos;
	}

	/**
	 * @return the eISuiteMatchReqNumNameVos
	 */
	public Collection<RossImportProcessEISuiteResourceVo> getEisuiteMatchReqNumNameVos() {
		return eisuiteMatchReqNumNameVos;
	}

	/**
	 * @param suiteMatchReqNumNameVos the eISuiteMatchReqNumNameVos to set
	 */
	public void setEisuiteMatchReqNumNameVos(
			Collection<RossImportProcessEISuiteResourceVo> suiteMatchReqNumNameVos) {
		eisuiteMatchReqNumNameVos = suiteMatchReqNumNameVos;
	}

	/**
	 * @return the confirmMatches
	 */
	public Boolean getConfirmMatches() {
		return confirmMatches;
	}

	/**
	 * @param confirmMatches the confirmMatches to set
	 */
	public void setConfirmMatches(Boolean confirmMatches) {
		this.confirmMatches = confirmMatches;
	}
}
