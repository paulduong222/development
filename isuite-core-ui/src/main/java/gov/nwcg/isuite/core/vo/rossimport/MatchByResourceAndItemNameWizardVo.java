package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;

import java.util.ArrayList;
import java.util.Collection;

public class MatchByResourceAndItemNameWizardVo {
	private Collection<RossImportProcessResourceVo> rossMatchNameKindVos=new ArrayList<RossImportProcessResourceVo>();
	private Collection<RossImportProcessEISuiteResourceVo> eisuiteMatchNameKindVos = new ArrayList<RossImportProcessEISuiteResourceVo>();

	public MatchByResourceAndItemNameWizardVo(){
		
	}

	public static Boolean exists(RossImportProcessResourceVo vo, Collection<RossImportProcessResourceVo> vos ) {
		
		for(RossImportProcessResourceVo v : vos){
			if(v.getRossResReqId().compareTo(vo.getRossResReqId())==0)
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return the rossMatchNameKindVos
	 */
	public Collection<RossImportProcessResourceVo> getRossMatchNameKindVos() {
		return rossMatchNameKindVos;
	}

	/**
	 * @param rossMatchNameKindVos the rossMatchNameKindVos to set
	 */
	public void setRossMatchNameKindVos(
			Collection<RossImportProcessResourceVo> rossMatchNameKindVos) {
		this.rossMatchNameKindVos = rossMatchNameKindVos;
	}

	/**
	 * @return the eISuiteMatchNameKindVos
	 */
	public Collection<RossImportProcessEISuiteResourceVo> getEisuiteMatchNameKindVos() {
		return eisuiteMatchNameKindVos;
	}

	/**
	 * @param suiteMatchNameKindVos the eISuiteMatchNameKindVos to set
	 */
	public void setEisuiteMatchNameKindVos(
			Collection<RossImportProcessEISuiteResourceVo> suiteMatchNameKindVos) {
		eisuiteMatchNameKindVos = suiteMatchNameKindVos;
	}
}
