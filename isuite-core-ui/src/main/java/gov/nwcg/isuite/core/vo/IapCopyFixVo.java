package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class IapCopyFixVo {
	private String branchName;
	private String divisionName;
	private Collection<Integer> positionNumList = new ArrayList<Integer>();
	
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public Collection<Integer> getPositionNumList() {
		return positionNumList;
	}
	public void setPositionNumList(Collection<Integer> positionNumList) {
		this.positionNumList = positionNumList;
	}
	
}
