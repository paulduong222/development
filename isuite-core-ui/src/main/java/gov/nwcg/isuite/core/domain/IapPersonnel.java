package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapPersonnel  extends Persistable{
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

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
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param form the form to set
	 */
	public void setForm(String form);

	/**
	 * @return the form
	 */
	public String getForm();

	/**
	 * @param section the section to set
	 */
	public void setSection(String section);

	/**
	 * @return the section
	 */
	public String getSection();

	/**
	 * @return the iapForm203
	 */
	public IapForm203 getIapForm203() ;

	/**
	 * @param iapForm203 the iapForm203 to set
	 */
	public void setIapForm203(IapForm203 iapForm203) ;

	/**
	 * @return the iapForm203Id
	 */
	public Long getIapForm203Id() ;

	/**
	 * @param iapForm203Id the iapForm203Id to set
	 */
	public void setIapForm203Id(Long iapForm203Id);

	/**
	 * @return the iapForm220
	 */
	public IapForm220 getIapForm220() ;

	/**
	 * @param iapForm220 the iapForm220 to set
	 */
	public void setIapForm220(IapForm220 iapForm220);

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id();

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id);

	/**
	 * @return the roleType
	 */
	public String getRoleType();

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType);

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

	/**
	 * @return the branchSetting
	 */
	public BranchSetting getBranchSetting() ;

	/**
	 * @param branchSetting the branchSetting to set
	 */
	public void setBranchSetting(BranchSetting branchSetting);

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId();

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId);

	/**
	 * @return the agencyName
	 */
	public String getAgencyName();

	/**
	 * @param agencyName the agencyName to set
	 */
	public void setAgencyName(String agencyName);

	/**
	 * @return the isTrainee
	 */
	public StringBooleanEnum getIsTrainee() ;
	
	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(StringBooleanEnum isTrainee);

	/**
	 * @return the isBranchName
	 */
	public StringBooleanEnum getIsBranchName();

	/**
	 * @param isBranchName the isBranchName to set
	 */
	public void setIsBranchName(StringBooleanEnum isBranchName);

	/**
	 * @return the iapBranchPersonnelParent
	 */
	public IapPersonnel getIapBranchPersonnelParent();

	/**
	 * @param iapBranchPersonnelParent the iapBranchPersonnelParent to set
	 */
	public void setIapBranchPersonnelParent(IapPersonnel iapBranchPersonnelParent) ;

	/**
	 * @return the iapBranchPersonnelParentId
	 */
	public Long getIapBranchPersonnelParentId();

	/**
	 * @param iapBranchPersonnelParentId the iapBranchPersonnelParentId to set
	 */
	public void setIapBranchPersonnelParentId(Long iapBranchPersonnelParentId);

	/**
	 * @return the branchPersonnel
	 */
	public Collection<IapPersonnel> getBranchPersonnel() ;

	/**
	 * @param branchPersonnel the branchPersonnel to set
	 */
	public void setBranchPersonnel(Collection<IapPersonnel> branchPersonnel);

	/**
	 * @return the isBlankBranch
	 */
	public StringBooleanEnum getIsBlankBranch();

	/**
	 * @param isBlankBranch the isBlankBranch to set
	 */
	public void setIsBlankBranch(StringBooleanEnum isBlankBranch);

	/**
	 * @return the divisionGroupName
	 */
	public String getDivisionGroupName();

	/**
	 * @param divisionGroupName the divisionGroupName to set
	 */
	public void setDivisionGroupName(String divisionGroupName);

	/**
	 * @return the iapPersonnelResources
	 */
	public Collection<IapPersonnelRes> getIapPersonnelResources();

	/**
	 * @param iapPersonnelResources the iapPersonnelResources to set
	 */
	public void setIapPersonnelResources(
			Collection<IapPersonnelRes> iapPersonnelResources);
	
}
