package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessOverheadGroupVo;

import java.util.ArrayList;
import java.util.Collection;

public class OverheadResourceGroupWizardVo {

	private int currentGroupIndex=0;

	private Collection<String> parentNames = new ArrayList<String>();
	
	private RossImportProcessOverheadGroupVo currentOverheadGroupVo;
	
	private Collection<RossImportProcessOverheadGroupVo> overheadGroupVos=new ArrayList<RossImportProcessOverheadGroupVo>();

	public OverheadResourceGroupWizardVo(){
		
	}

	/**
	 * @return the currentGroupIndex
	 */
	public int getCurrentGroupIndex() {
		return currentGroupIndex;
	}

	/**
	 * @param currentGroupIndex the currentGroupIndex to set
	 */
	public void setCurrentGroupIndex(int currentGroupIndex) {
		this.currentGroupIndex = currentGroupIndex;
	}

	/**
	 * @return the currentOverheadGroupVo
	 */
	public RossImportProcessOverheadGroupVo getCurrentOverheadGroupVo() {
		return currentOverheadGroupVo;
	}

	/**
	 * @param currentOverheadGroupVo the currentOverheadGroupVo to set
	 */
	public void setCurrentOverheadGroupVo(
			RossImportProcessOverheadGroupVo currentOverheadGroupVo) {
		this.currentOverheadGroupVo = currentOverheadGroupVo;
	}

	/**
	 * @return the overheadGroupVos
	 */
	public Collection<RossImportProcessOverheadGroupVo> getOverheadGroupVos() {
		return overheadGroupVos;
	}

	/**
	 * @param overheadGroupVos the overheadGroupVos to set
	 */
	public void setOverheadGroupVos(
			Collection<RossImportProcessOverheadGroupVo> overheadGroupVos) {
		this.overheadGroupVos = overheadGroupVos;
	}

	/**
	 * @return the parentNames
	 */
	public Collection<String> getParentNames() {
		return parentNames;
	}

	/**
	 * @param parentNames the parentNames to set
	 */
	public void setParentNames(Collection<String> parentNames) {
		this.parentNames = parentNames;
	}
}
