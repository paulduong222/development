package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessReviewRosterVo extends AbstractVo {
	private int groupIndex=0;

	private int idx=0;
	
	private RossImportProcessResourceVo parentResourceVo;
	
	private Collection<RossImportProcessResourceVo> childrenVos = new ArrayList<RossImportProcessResourceVo>();
	private Collection<RossImportProcessResourceVo> excludedChildrenVos = new ArrayList<RossImportProcessResourceVo>();
	
	
	public RossImportProcessReviewRosterVo(){
		
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
	 * @return the childrenVos
	 */
	public Collection<RossImportProcessResourceVo> getChildrenVos() {
		return childrenVos;
	}


	/**
	 * @param childrenVos the childrenVos to set
	 */
	public void setChildrenVos(Collection<RossImportProcessResourceVo> childrenVos) {
		this.childrenVos = childrenVos;
	}


	/**
	 * @return the groupIndex
	 */
	public int getGroupIndex() {
		return groupIndex;
	}


	/**
	 * @param groupIndex the groupIndex to set
	 */
	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
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
	 * @return the excludedChildrenVos
	 */
	public Collection<RossImportProcessResourceVo> getExcludedChildrenVos() {
		return excludedChildrenVos;
	}


	/**
	 * @param excludedChildrenVos the excludedChildrenVos to set
	 */
	public void setExcludedChildrenVos(
			Collection<RossImportProcessResourceVo> excludedChildrenVos) {
		this.excludedChildrenVos = excludedChildrenVos;
	}


	
}
