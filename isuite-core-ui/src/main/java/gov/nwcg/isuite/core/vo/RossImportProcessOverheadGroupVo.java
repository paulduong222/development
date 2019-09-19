package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessOverheadGroupVo extends AbstractVo {
	private int groupIndex=0;

	private int idx=0;
	
	private Boolean rosterGroup = false;
	private Boolean singleGroup = false;
	private Boolean resolved=false;
	
	private RossImportProcessResourceVo parentResourceVo;
	private Collection<RossImportProcessResourceVo> childrenVos = new ArrayList<RossImportProcessResourceVo>();
	
	
	public RossImportProcessOverheadGroupVo(){
		
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
	 * @return the rosterGroup
	 */
	public Boolean getRosterGroup() {
		return rosterGroup;
	}


	/**
	 * @param rosterGroup the rosterGroup to set
	 */
	public void setRosterGroup(Boolean rosterGroup) {
		this.rosterGroup = rosterGroup;
	}


	/**
	 * @return the singleGroup
	 */
	public Boolean getSingleGroup() {
		return singleGroup;
	}


	/**
	 * @param singleGroup the singleGroup to set
	 */
	public void setSingleGroup(Boolean singleGroup) {
		this.singleGroup = singleGroup;
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
	 * @return the resolved
	 */
	public Boolean getResolved() {
		return resolved;
	}


	/**
	 * @param resolved the resolved to set
	 */
	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	
}
