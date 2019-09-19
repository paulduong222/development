package gov.nwcg.isuite.core.vo;

public class Duplicate204BranchDivisionVo {
	
	private Long id;
	private String newBranchName;
	private String newDivisionName;
	private Boolean isResolved=false;
	private String branchName;
	private String divisionName;
	
	public Duplicate204BranchDivisionVo(){
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}

	/**
	 * @return the isResolved
	 */
	public Boolean getIsResolved() {
		return isResolved;
	}

	/**
	 * @param newBranchName the newBranchName to set
	 */
	public void setNewBranchName(String newBranchName) {
		this.newBranchName = newBranchName;
	}

	/**
	 * @return the newBranchName
	 */
	public String getNewBranchName() {
		return newBranchName;
	}

	/**
	 * @param newDivisionName the newDivisionName to set
	 */
	public void setNewDivisionName(String newDivisionName) {
		this.newDivisionName = newDivisionName;
	}

	/**
	 * @return the newDivisionName
	 */
	public String getNewDivisionName() {
		return newDivisionName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

}
