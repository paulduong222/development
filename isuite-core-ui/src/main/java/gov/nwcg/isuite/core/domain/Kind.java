package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.Collection;

public interface Kind extends Persistable{

   /**
    * @param code the kind code
    */
   public void setCode(String code);
   
   /**
    * @return the kind code
    */
   public String getCode();

   /**
    * @param description the kind code description
    */
   public void setDescription(String description);
   
   /**
    * @return the kind code description
    */
   public String getDescription();
   
   /**
    * @return standard: if (standard = true) the record is part of static data, 
    * else it was entered by a user
    */
   public Boolean isStandard();
   
   /**
    * @param isStandard
    */
   public void setStandard(Boolean isStandard);

   /**
    * Returns the list of resource kinds (resource qualifications).
    * 
    * @return
    * 		the list of resource kinds
    */
   public Collection<ResourceKind> getResourceKinds();

   /**
    * Sets the resource kinds (resource qualifications).
    * 
    * @param list
    * 		the list of resource kinds to set
    */
	public void setResourceKinds(Collection<ResourceKind> list);
	
	public void setRequestCategoryId(Long id);
	
	public Long getRequestCategoryId();

	public void setRequestCategory(RequestCategory rc);
	
	public RequestCategory getRequestCategory();

	/**
	 * @return the sysCostRateKinds
	 */
	public Collection<SysCostRateKind> getSysCostRateKinds();
	
	/**
	 * @param sysCostRateKinds the sysCostRateKinds to set
	 */
	public void setSysCostRateKinds(Collection<SysCostRateKind> sysCostRateKinds) ;

	/**
	 * @return the incidentCostRateKinds
	 */
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds();
	
	/**
	 * @param incidentCostRateKinds the incidentCostRateKinds to set
	 */
	public void setIncidentCostRateKinds(Collection<IncidentCostRateKind> incidentCostRateKinds) ;
	
	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() ;
	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers);	
	
	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department);
	
	/**
	 * @return the department
	 */
	public Department getDepartment();

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId);
	
	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId();
	
	/**
	 * @param sit209 the sit209 to set
	 */
	public void setSit209(Sit209 sit209);

	/**
	 * @return the sit209
	 */
	public Sit209 getSit209();

	/**
	 * @param direct the direct to set
	 */
	public void setDirect(Boolean direct);

	/**
	 * @return the direct
	 */
	public Boolean getDirect();

	/**
	 * @param dailyForm the dailyForm to set
	 */
	public void setDailyForm(DailyForm dailyForm);

	/**
	 * @return the dailyForm
	 */
	public DailyForm getDailyForm();

	/**
	 * @param dailyFormId the dailyFormId to set
	 */
	public void setDailyFormId(Long dailyFormId);
	
	/**
	 * @return the dailyFormId
	 */
	public Long getDailyFormId();

	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units);

	/**
	 * @return the units
	 */
	public Integer getUnits();

	/**
	 * @param people the people to set
	 */
	public void setPeople(Integer people);

	/**
	 * @return the people
	 */
	public Integer getPeople();

	/**
	 * @param subGroupCategory the subGroupCategory to set
	 */
	public void setSubGroupCategory(SubGroupCategory subGroupCategory);
	
	/**
	 * @return the subGroupCategory
	 */
	public SubGroupCategory getSubGroupCategory();

	/**
	 * @param subGroupCategoryId the subGroupCategoryId to set
	 */
	public void setSubGroupCategoryId(Long subGroupCategoryId);

	/**
	 * @return the subGroupCategoryId
	 */
	public Long getSubGroupCategoryId();

	/**
	 * @param groupCategory the groupCategory to set
	 */
	public void setGroupCategory(GroupCategory groupCategory);

	/**
	 * @return the groupCategory
	 */
	public GroupCategory getGroupCategory();

	/**
	 * @param groupCategoryId the groupCategoryId to set
	 */
	public void setGroupCategoryId(Long groupCategoryId);

	/**
	 * @return the groupCategoryId
	 */
	public Long getGroupCategoryId();

	/**
	 * @param lineOverhead the lineOverhead to set
	 */
	public void setLineOverhead(Boolean lineOverhead);

	/**
	 * @return the lineOverhead
	 */
	public Boolean getLineOverhead();

	/**
	 * @param subordinate the subordinate to set
	 */
	public void setSubordinate(Boolean subordinate);

	/**
	 * @return the subordinate
	 */
	public Boolean getSubordinate();

	/**
	 * @param strikeTeam the strikeTeam to set
	 */
	public void setStrikeTeam(Boolean strikeTeam);

	/**
	 * @return the strikeTeam
	 */
	public Boolean getStrikeTeam();
	
	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignments(Collection<Assignment> assignments);

	/**
	 * @return the assignments
	 */
	public Collection<Assignment> getAssignments();

	public StringBooleanEnum getAircraft() ;
	
	public void setAircraft(StringBooleanEnum aircraft) ;	
	
	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes);
	
	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes();
	
	/**
	 * @param iapBranchPosItemCodes the iapBranchPosItemCodes to set
	 */
	public void setIapBranchPosItemCodes(Collection<IapBranchPosItemCode> iapBranchPosItemCodes);

	/**
	 * @return the iapBranchPosItemCodes
	 */
	public Collection<IapBranchPosItemCode> getIapBranchPosItemCodes();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();
	
	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();
	
   /**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active);
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive();

	/**
	 * @return the projectionItems
	 */
	public Collection<ProjectionItem> getProjectionItems();

	/**
	 * @param projectionItems the projectionItems to set
	 */
	public void setProjectionItems(Collection<ProjectionItem> projectionItems);
	
	/**
	 * @param standardCost the standardCost to set
	 */
	public void setStandardCost(BigDecimal standardCost);

	/**
	 * @return the standardCost
	 */
	public BigDecimal getStandardCost();
	
	/**
	 * @return the branchSettingPositions
	 */
	public Collection<BranchSettingPosition> getBranchSettingPositions();

	/**
	 * @param branchSettingPositions the branchSettingPositions to set
	 */
	public void setBranchSettingPositions(
			Collection<BranchSettingPosition> branchSettingPositions);

	/**
	 * @return the inDirect
	 */
	public Boolean getInDirect();

	/**
	 * @param inDirect the inDirect to set
	 */
	public void setInDirect(Boolean inDirect);
	
	/**
	 * @param kindAltDescs the kindAltDescs to set
	 */
	public void setKindAltDescs(Collection<KindAltDesc> kindAltDescs);

	/**
	 * @return the kindAltDescs
	 */
	public Collection<KindAltDesc> getKindAltDescs();
	
	/** 
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings);
	
	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings();
	
}
