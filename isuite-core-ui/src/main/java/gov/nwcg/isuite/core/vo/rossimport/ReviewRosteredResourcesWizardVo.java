package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessReviewRosterVo;

import java.util.ArrayList;
import java.util.Collection;

public class ReviewRosteredResourcesWizardVo {

	private int idx=0;
	
	private Collection<String> parentNames = new ArrayList<String>();
	
	private RossImportProcessResourceVo parentResourceVo;
	
	private RossImportProcessReviewRosterVo currentRosterVo;

	private Collection<RossImportProcessReviewRosterVo> rosterVos=new ArrayList<RossImportProcessReviewRosterVo>();
	
	public ReviewRosteredResourcesWizardVo(){
		
	}

	/**
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx the idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
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

	/**
	 * @return the parentResourceVo
	 */
	public RossImportProcessResourceVo getParentResourceVo() {
		return parentResourceVo;
	}

	/**
	 * @param parentResourceVo the parentResourceVo to set
	 */
	public void setParentResourceVo(RossImportProcessResourceVo parentResourceVo) {
		this.parentResourceVo = parentResourceVo;
	}

	/**
	 * @return the currentRosterVo
	 */
	public RossImportProcessReviewRosterVo getCurrentRosterVo() {
		return currentRosterVo;
	}

	/**
	 * @param currentRosterVo the currentRosterVo to set
	 */
	public void setCurrentRosterVo(RossImportProcessReviewRosterVo currentRosterVo) {
		this.currentRosterVo = currentRosterVo;
	}

	/**
	 * @return the rosterVos
	 */
	public Collection<RossImportProcessReviewRosterVo> getRosterVos() {
		return rosterVos;
	}

	/**
	 * @param rosterVos the rosterVos to set
	 */
	public void setRosterVos(Collection<RossImportProcessReviewRosterVo> rosterVos) {
		this.rosterVos = rosterVos;
	}
	
}
