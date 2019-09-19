package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapBranchPersonnel extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch);

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch();

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId);

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId();

	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource);

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource();

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId();

	/**
	 * @param role the role to set
	 */
	public void setRole(String role);

	/**
	 * @return the role
	 */
	public String getRole();

	/**
	 * @return the resourceName
	 */
	public String getResourceName();

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName);

	/**
	 * @return the phone1
	 */
	public String getPhone1() ;

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1);

	/**
	 * @return the phone2
	 */
	public String getPhone2() ;

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) ;

	/**
	 * @return the roleType
	 */
	public String getRoleType();

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType);

	/**
	 * @return the isTrainee
	 */
	public StringBooleanEnum getIsTrainee();

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(StringBooleanEnum isTrainee);

	/**
-	 * @return the iapBranchPersonnelResources
	 */
	public Collection<IapBranchPersonnelRes> getIapBranchPersonnelResources();

	/**
	 * @param iapBranchPersonnelResources the iapBranchPersonnelResources to set
	 */
	public void setIapBranchPersonnelResources(
			Collection<IapBranchPersonnelRes> iapBranchPersonnelResources);	

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine();

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine);

}
