package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class UnmatchedResourcesNoReqNumWizardVo {
	private Collection<RossImportProcessResourceVo> rossUnmatchedVos = new ArrayList<RossImportProcessResourceVo>();
	private Collection<RossImportProcessEISuiteResourceVo> eisuiteUnmatchedVos = new ArrayList<RossImportProcessEISuiteResourceVo>();

	public UnmatchedResourcesNoReqNumWizardVo(){
		
	}

	public static Boolean hasVo(Collection<RossImportProcessResourceVo> vos, RossImportProcessResourceVo vo){
		for(RossImportProcessResourceVo rvo : vos){
			String rvoReqNum=(StringUtility.hasValue(rvo.getRequestNumber()) ? rvo.getRequestNumber() : "" );
			String voReqNum=(StringUtility.hasValue(vo.getRequestNumber()) ? vo.getRequestNumber() : "" );
			
			if(rvoReqNum.equals(voReqNum)){
				return true;
			}
		}
		return false;
	}

	public static Boolean hasEisuiteVo(Collection<RossImportProcessEISuiteResourceVo> vos, RossImportProcessEISuiteResourceVo vo){
		for(RossImportProcessEISuiteResourceVo rvo : vos){
			if(rvo.getResourceId().compareTo(vo.getResourceId())==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return the rossUnmatchedVos
	 */
	public Collection<RossImportProcessResourceVo> getRossUnmatchedVos() {
		return rossUnmatchedVos;
	}

	/**
	 * @param rossUnmatchedVos the rossUnmatchedVos to set
	 */
	public void setRossUnmatchedVos(
			Collection<RossImportProcessResourceVo> rossUnmatchedVos) {
		this.rossUnmatchedVos = rossUnmatchedVos;
	}

	/**
	 * @return the eisuiteUnmatchedVos
	 */
	public Collection<RossImportProcessEISuiteResourceVo> getEisuiteUnmatchedVos() {
		return eisuiteUnmatchedVos;
	}

	/**
	 * @param eisuiteUnmatchedVos the eisuiteUnmatchedVos to set
	 */
	public void setEisuiteUnmatchedVos(
			Collection<RossImportProcessEISuiteResourceVo> eisuiteUnmatchedVos) {
		this.eisuiteUnmatchedVos = eisuiteUnmatchedVos;
	}
	
}
