package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

/**
 * Object Relational Mapping for an Incident Group.
 * 
 * @author bsteiner
 */
public interface IncidentGroup extends Persistable {
   
   /**
    * Retrieve the name of the group.
    * 
    * @return group name.
    */
   public String getGroupName();
   
   /**
    * Set the name of the group.
    * 
    * @param groupName the group name
    */
   public void setGroupName(String groupName);
   
   /**
    * Retrieve incidents associated to the incident group.
    * 
    * @return {@link Collection} of {@link Incident}s associated to group.
    */
   public Collection<Incident> getIncidents();
   
   /**
    * Set the {@link Incident}s this group is to be associated to.
    * 
    * @param incidents the incidents
    */
   public void setIncidents(Collection<Incident> incidents);

   /**
    * @return
    */
   public Collection<IncidentGroupUser> getIncidentGroupUsers();
  
   /**
    * @param incidentGroupUsers
    */
   public void setIncidentGroupUsers(Collection<IncidentGroupUser> incidentGroupUsers);
	   
	/**
	 * @return the incidentGroupQuestions
	 */
	public Collection<IncidentGroupQuestion> getIncidentGroupQuestions() ;
	
	/**
	 * @param incidentGroupQuestions the incidentGroupQuestions to set
	 */
	public void setIncidentGroupQuestions(Collection<IncidentGroupQuestion> incidentGroupQuestions) ;

	/**
	 * @return the incidentGroupPrefs
	 */
	public Collection<IncidentGroupPrefs> getIncidentGroupPrefs() ;

	/**
	 * @param incidentGroupPrefs the incidentGroupPrefs to set
	 */
	public void setIncidentGroupPrefs(Collection<IncidentGroupPrefs> incidentGroupPrefs);

	/**
	 * @return the incidentGroupQSKinds
	 */
	public Collection<IncidentGroupQSKind> getIncidentGroupQSKinds();

	/**
	 * @param incidentGroupQSKinds the incidentGroupQSKinds to set
	 */
	public void setIncidentGroupQSKinds(Collection<IncidentGroupQSKind> incidentGroupQSKinds) ;
	
	/**
	    * @return the incident cost default hours
	*/
	public Integer getIncidentGroupCostDefaultHours();
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentGroupCostDefaultHours(Integer incidentGroupCostDefaultHours);

	/**
	 * @return the costAutoRun
	 */
	public StringBooleanEnum getCostAutoRun();

	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(StringBooleanEnum costAutoRun) ;
	
	/**
	 * @return the incidentCostRates
	 */
	public Collection<IncidentCostRate> getIncidentCostRates();

	/**
	 * @param incidentCostRates the incidentCostRates to set
	 */
	public void setIncidentCostRates(Collection<IncidentCostRate> incidentCostRates);
	
	/**
	 * @param sit209s the sit209s to set
	 */
	public void setSit209s(Collection<Sit209> sit209s);

	/**
	 * @return the sit209s
	 */
	public Collection<Sit209> getSit209s();
	
	/**
	 * @param agencies the agencies to set
	 */
	public void setAgencies(Collection<Agency> agencies);

	/**
	 * @return the agencies
	 */
	public Collection<Agency> getAgencies();

	/**
	 * @param agencyGroups the agencyGroups to set
	 */
	public void setAgencyGroups(Collection<AgencyGroup> agencyGroups);

	/**
	 * @return the agencyGroups
	 */
	public Collection<AgencyGroup> getAgencyGroups();

	/**
	 * @param organizations the organizations to set
	 */
	public void setOrganizations(Collection<Organization> organizations);

	/**
	 * @return the organizations
	 */
	public Collection<Organization> getOrganizations();

	/**
	 * @param jetPorts the jetPorts to set
	 */
	public void setJetPorts(Collection<JetPort> jetPorts);

	/**
	 * @return the jetPorts
	 */
	public Collection<JetPort> getJetPorts();

	/**
	 * @param kinds the kinds to set
	 */
	public void setKinds(Collection<Kind> kinds);

	/**
	 * @return the kinds
	 */
	public Collection<Kind> getKinds();

	/**
	 * @param subGroupCategories the subGroupCategories to set
	 */
	public void setSubGroupCategories(Collection<SubGroupCategory> subGroupCategories);

	/**
	 * @return the subGroupCategories
	 */
	public Collection<SubGroupCategory> getSubGroupCategories();

	/**
	 * @return the projections
	 */
	public Collection<Projection> getProjections();

	/**
	 * @param projections the projections to set
	 */
	public void setProjections(Collection<Projection> projections);

	/**
	 * @return the iapPersonNameOrder
	 */
	public Short getIapPersonNameOrder();

	/**
	 * @param iapPersonNameOrder the iapPersonNameOrder to set
	 */
	public void setIapPersonNameOrder(Short iapPersonNameOrder);
	/**
	 * @return the includeFilled
	 */
	public StringBooleanEnum getIncludeFilled();

	/**
	 * @param includeFilled the includeFilled to set
	 */
	public void setIncludeFilled(StringBooleanEnum includeFilled);

	/**
	 * @return the iapResourceToDisplayFrom
	 */
	public Short getIapResourceToDisplayFrom() ;

	/**
	 * @param iapResourceToDisplayFrom the iapResourceToDisplayFrom to set
	 */
	public void setIapResourceToDisplayFrom(Short iapResourceToDisplayFrom);

	/**
	 * @return the iapMasterFrequencies
	 */
	public Collection<IapMasterFrequency> getIapMasterFrequencies();

	/**
	 * @param iapMasterFrequencies the iapMasterFrequencies to set
	 */
	public void setIapMasterFrequencies(
			Collection<IapMasterFrequency> iapMasterFrequencies);

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes() ;

	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(
			Collection<IapPositionItemCode> iapPositionItemCodes);	
	
	/**
	 * @return the costAccrualExtracts
	 */
	public Collection<CostAccrualExtract> getCostAccrualExtracts();

	/**
	 * @param costAccrualExtracts the costAccrualExtracts to set
	 */
	public void setCostAccrualExtracts(
			Collection<CostAccrualExtract> costAccrualExtracts);	
	
	/**
	 * @return the primaryIncidentId
	 */
	public Long getPrimaryIncidentId();

	/**
	 * @param primaryIncidentId the primaryIncidentId to set
	 */
	public void setPrimaryIncidentId(Long primaryIncidentId);

	/**
	 * @return the accrualExtractNumber
	 */
	public Short getAccrualExtractNumber();

	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Short accrualExtractNumber);

	/**
	 * @return the branchSettings
	 */
	public Collection<BranchSetting> getBranchSettings();

	/**
	 * @param branchSettings the branchSettings to set
	 */
	public void setBranchSettings(Collection<BranchSetting> branchSettings) ;

	/**
	 * @return the incidentPrefsOtherFields
	 */
	public IncidentPrefsOtherFields getIncidentPrefsOtherFields() ;

	/**
	 * @param incidentPrefsOtherFields the incidentPrefsOtherFields to set
	 */
	public void setIncidentPrefsOtherFields(
			IncidentPrefsOtherFields incidentPrefsOtherFields);

	public StringBooleanEnum getIsSiteManaged();

	public void setIsSiteManaged(StringBooleanEnum isSiteManaged);
	
	/**
	 * @param financialExports the financialExports to set
	 */
	public void setFinancialExports(Collection<FinancialExport> financialExports);

	/**
	 * @return the financialExports
	 */
	public Collection<FinancialExport> getFinancialExports();

	/**
	 * @return the iapTreeviewDisplay
	 */
	public Short getIapTreeviewDisplay();

	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(Short iapTreeviewDisplay);

	/**
	 * @return the byDate
	 */
	public Date getByDate();

	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate);

	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior() ;

	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior);
	
}