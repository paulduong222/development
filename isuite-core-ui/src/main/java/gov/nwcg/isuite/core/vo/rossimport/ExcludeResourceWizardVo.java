package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;

import java.util.ArrayList;
import java.util.Collection;

public class ExcludeResourceWizardVo {

	private Collection<RossImportProcessResourceVo> excludedRossImportProcessResourceVos =
		new ArrayList<RossImportProcessResourceVo>();
	
	public ExcludeResourceWizardVo(){
		
	}

	public static Boolean isExcluded(Long rossResReqId, Collection<RossImportProcessResourceVo> vos){
		if(null == vos)
			return false;
		
		for(RossImportProcessResourceVo vo : vos){
			if(vo.getRossResReqId().compareTo(rossResReqId)==0){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return the excludedRossImportProcessResourceVos
	 */
	public Collection<RossImportProcessResourceVo> getExcludedRossImportProcessResourceVos() {
		return excludedRossImportProcessResourceVos;
	}

	/**
	 * @param excludedRossImportProcessResourceVos the excludedRossImportProcessResourceVos to set
	 */
	public void setExcludedRossImportProcessResourceVos(
			Collection<RossImportProcessResourceVo> excludedRossImportProcessResourceVos) {
		this.excludedRossImportProcessResourceVos = excludedRossImportProcessResourceVos;
	}

}
