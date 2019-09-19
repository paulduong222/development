package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;

import java.util.ArrayList;
import java.util.Collection;

/*
 * This vo represents the objects needed to support the Data Conflicts Wizard.
 */
public class DataConflictWizardVo {

	private Collection<RossImportProcessDataErrorVo> rossImportProcessDataErrorVos
											= new ArrayList<RossImportProcessDataErrorVo>();
	
	public DataConflictWizardVo(){
		
	}

	public static RossImportProcessDataErrorVo getDataErrorVo(String conflictCode,Collection<RossImportProcessDataErrorVo> vos){
		RossImportProcessDataErrorVo vo = null;
		
		for(RossImportProcessDataErrorVo errVo : vos){
			if(errVo.getConflictCode().equals(conflictCode)){
				vo=errVo;
				break;
			}
				
		}
		return vo;
	}
	
	/**
	 * @return the rossImportProcessDataErrorVos
	 */
	public Collection<RossImportProcessDataErrorVo> getRossImportProcessDataErrorVos() {
		return rossImportProcessDataErrorVos;
	}

	/**
	 * @param rossImportProcessDataErrorVos the rossImportProcessDataErrorVos to set
	 */
	public void setRossImportProcessDataErrorVos(
			Collection<RossImportProcessDataErrorVo> rossImportProcessDataErrorVos) {
		this.rossImportProcessDataErrorVos = rossImportProcessDataErrorVos;
	}
}
