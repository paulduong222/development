package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

public interface IapBranch extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);


	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName);

	/**
	 * @return the branchName
	 */
	public String getBranchName();

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName);

	/**
	 * @return the divisionName
	 */
	public String getDivisionName();

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName);

	/**
	 * @return the groupName
	 */
	public String getGroupName();

	/**
	 * @param controlOperations the controlOperations to set
	 */
	public void setControlOperations(String controlOperations);

	/**
	 * @return the controlOperations
	 */
	public String getControlOperations();

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions);

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions();

	/**
	 * @param stagingArea the stagingArea to set
	 */
	public void setStagingArea(String stagingArea);

	/**
	 * @return the stagingArea
	 */
	public String getStagingArea();

	/**
	 * @param workAssignment the workAssignment to set
	 */
	public void setWorkAssignment(String workAssignment);

	/**
	 * @return the workAssignment
	 */
	public String getWorkAssignment();

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy);

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy();

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy);

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy();

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate);

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate();
	
	/**
	 * @param iapBranchCommSummaries the iapBranchCommSummaries to set
	 */
	public void setIapBranchCommSummaries(Collection<IapBranchCommSummary> iapBranchCommSummaries);
	
	/**
	 * @return the iapBranchCommSummaries
	 */
	public Collection<IapBranchCommSummary> getIapBranchCommSummaries();
	
	/**
	 * @param iapBranchPersonnels the iapBranchPersonnels to set
	 */
	public void setIapBranchPersonnels(Collection<IapBranchPersonnel> iapBranchPersonnels);

	/**
	 * @return the iapBranchPersonnels
	 */
	public Collection<IapBranchPersonnel> getIapBranchPersonnels();
	
	/**
	 * @param iapBranchRscAssigns the iapBranchRscAssigns to set
	 */
	public void setIapBranchRscAssigns(Collection<IapBranchRscAssign> iapBranchRscAssigns);

	/**
	 * @return the iapBranchRscAssigns
	 */
	public Collection<IapBranchRscAssign> getIapBranchRscAssigns();

	
	/**
	 * @return the isForm204Locked
	 */
	public StringBooleanEnum getIsForm204Locked() ;

	/**
	 * @param isForm204Locked the isForm204Locked to set
	 */
	public void setIsForm204Locked(StringBooleanEnum isForm204Locked);

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition();

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition);
	
	
}
