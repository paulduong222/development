package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

/**
 * Domain object used to represent an Incident.
 * 
 * @author bsteiner
 *
 */
public interface Incident extends Persistable {
   
   /**
    * @return the incidentName
    */
   public String getIncidentName();
   
   /**
    * @param incidentName the incidentName to set
    */
   public void setIncidentName(String incidentName);

   /**
    * @return the incident description
    */
   public String getIncidentDescription();
   
   /**
    * @param incidentDescription
    */
   public void setIncidentDescription(String incidentDescription);
   
   /**
    * @return the incidentNumber
    */
   public String getIncidentNumber();
   
   /**
    * @return the countrySubdivisionId
    */
   public Long getCountrySubdivisionId();
   
   /**
    * @param countrySubdivisionId the countrySubdivisionId to set
    */
   public void setCountrySubdivisionId(Long countrySubdivisionId);
   
   /**
    * @return the countrySubdivision
    */
   public CountrySubdivision getCountrySubdivision();

   /**
    * @param countrySubdivision the countrySubdivision to set
    */
   public void setCountrySubdivision(CountrySubdivision countrySubdivision);

   /**
    * 
    * @return the homeUnit
    */
   public Organization getHomeUnit();
   
   /**
    * 
    * @param homeUnit
    */
   public void setHomeUnit(Organization homeUnit);
   
   /**
    * @return the homeUnitId
    */
   public Long getHomeUnitId();
   
   /**
    * @param homeUnitId the homeUnitId to set
    */
   public void setHomeUnitId(Long homeUnitId);

   /**
    * @return the nbr
    */
   public String getNbr();
   
   /**
    * @param nbr the nbr to set
    */
   public void setNbr(String nbr);
   
   /**
    * @return the agencyId
    */
   public Long getAgencyId();
   
   /**
    * @param agencyId the agencyId to set
    */
   public void setAgencyId(Long agencyId);
   
   /**
    * @return the agency(incidentJurisdiction)
    */
   public Agency getAgency();
   
   /**
    * @param agency the agency to set
    */
   public void setAgency(Agency agency);
   
   /**
    * @return the location
    */
   public String getLocation();
   
   /**
    * @param location the location to set
    */
   public void setLocation(String location);
   
   /**
    * @return the latitude
    */
   public String getLatitude();
   
   /**
    * @param latitude the latitude of the incident to set
    */
   public void setLatitude(String latitude);
   
   /**
    * @return the longitude
    */
   public String getLongitude();
   
   /**
    * @param longitude the longitude of the incident to set
    */
   public void setLongitude(String longitude);
   
   /**
    * @return the incidentStartDate
    */
   public Date getIncidentStartDate();
   
   /**
    * @param incidentStartDate the incidentStartDate to set
    * NOTE: Need to also set the IncidentYear here
    */
   public void setIncidentStartDate(Date incidentStartDate);
   
   /**
    * @return the incidentYear
    */
   public Integer getIncidentYear();

   public void setIncidentYear(Integer val);
   
   /**
    * @return the eventTypeId
    */
   public Long getEventTypeId();
   
   /**
    * @param eventTypeId the eventTypeId of the incident to set
    */
   public void setEventTypeId(Long eventType);

   /**
    * @return the eventType
    */
   public EventType getEventType();
   
   /**
    * @param eventType the eventType of the incident to set
    */
   public void setEventType(EventType eventType);
   
   /**
    * 
    * @return 
    */
   public Collection<IncidentAccountCode> getIncidentAccountCodes();

   /**
    * 
    * @param 
    */
   public void setIncidentAccountCodes(Collection<IncidentAccountCode> incidentAccountCodes);
   


   /**
    * @return the resources
    */
   public Collection<Resource> getResources();
   
   /**
    * @param resources the resources to set
    */
   public void setResources(Collection<Resource> resources); 
   
   /**
    * Find if the incident is restricted or not.
    * 
    * @return
    */
   public Boolean getRestricted();   
   
   /**
    * Set the restricted flag for the incident.
    * 
    * @param isRestricted
    */
   public void setRestricted(Boolean isRestricted);

   /**
    * Returns the restrictedIncidentUsers.
    *
    * @return 
    *		the restrictedIncidentUsers to return
    */
   public Collection<RestrictedIncidentUser> getRestrictedIncidentUsers();

   /**
    * Sets the restrictedIncidentUsers.
    *
    * @param restrictedIncidentUsers 
    *			the restrictedIncidentUsers to set
    */
   public void setRestrictedIncidentUsers(Collection<RestrictedIncidentUser> restrictedIncidentUsers);

	/**
	 * Returns the incidentEndDate.
	 *
	 * @return 
	 *		the incidentEndDate to return
	 */
	public Date getIncidentEndDate();

	/**
	 * Sets the incidentEndDate.
	 *
	 * @param incidentEndDate 
	 *			the incidentEndDate to set
	 */
	public void setIncidentEndDate(Date incidentEndDate);
	
	/**
	 * @return the incidentPrefsOtherFields
	 */
	public IncidentPrefsOtherFields getIncidentPrefsOtherFields();

	/**
	 * @param incidentPrefsOtherFields the incidentPrefsOtherFields to set
	 */
	public void setIncidentPrefsOtherFields(IncidentPrefsOtherFields incidentPrefsOtherFields);

	public RateClass getRateClass();

	public void setRateClass(RateClass rateClass);

	public Long getRateClassId();

	public void setRateClassId(Long rateClassId);	

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId();

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId);

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId();

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId);

	/**
	 * @return the incidentCostRate
	 */
	public Collection<IncidentCostRate> getIncidentCostRates();

	/**
	 * @param incidentCostRate the incidentCostRate to set
	 */
	public void setIncidentCostRates(Collection<IncidentCostRate> incidentCostRate);
	
	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() ;

	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) ;
	
	/**
	 * @param incidentShifts the incidentShifts to set
	 */
	public void setIncidentShifts(Collection<IncidentShift> incidentShifts);

	/**
	 * @return the incidentShifts
	 */
	public Collection<IncidentShift> getIncidentShifts();

	/**
	 * @return the incidentQSKinds
	 */
	public Collection<IncidentQSKind> getIncidentQSKinds();
	
	/**
	 * @param incidentQSKinds the incidentQSKinds to set
	 */
	public void setIncidentQSKinds(Collection<IncidentQSKind> incidentQSKinds);

	/**
	 * @return the incidentQuestions
	 */
	public Collection<IncidentQuestion> getIncidentQuestions() ;

	/**
	 * @param incidentQuestions the incidentQuestions to set
	 */
	public void setIncidentQuestions(Collection<IncidentQuestion> incidentQuestions) ;

	/**
	 * @return the incidentCostDefaults
	 */
	public IncidentCostDefaults getIncidentCostDefaults() ;

	/**
	 * @param incidentCostDefaults the incidentCostDefault to set
	 */
	public void setIncidentCostDefaults(IncidentCostDefaults incidentCostDefaults);
	
	/**
	 * 
	 * @return
	 */
	public Collection<IncidentPrefs> getIncidentPrefs();

	/**
	 * 
	 * @param incidentPrefs
	 */
	public void setIncidentPrefs(Collection<IncidentPrefs> incidentPrefs);

	/**
	 * @return the pdc
	 */
	public Organization getPdc() ;

	/**
	 * @param pdc the pdc to set
	 */
	public void setPdc(Organization pdc) ;

	/**
	 * @return the pdcId
	 */
	public Long getPdcId() ;
	
	/**
	 * @param pdcId the pdcId to set
	 */
	public void setPdcId(Long pdcId) ;	

	/**
	 * @return the incidentGroups
	 */
	public Collection<IncidentGroup> getIncidentGroups() ;

	/**
	 * @param incidentGroups the incidentGroups to set
	 */
	public void setIncidentGroups(Collection<IncidentGroup> incidentGroups) ;
	
	/**
	 * @return theIapPersonNameOrder
	 */
	public Short getIapPersonNameOrder();
	
	/**
	 * @param iapPersonNameOrder the iapPersonNameOrder to set
	 */
	public void setIapPersonNameOrder(Short iapPersonNameOrder);
	
	/**
	 * @return includeFilled
	 */
	public StringBooleanEnum getIncludeFilled();
	
	/**
	 * @param includeFilled the includeFilled to set
	 */
	public void setIncludeFilled(StringBooleanEnum includeFilled);
	
	/**
	 * @return the iapResourceToDisplayFrom
	 */
	public Short getIapResourceToDisplayFrom();
	
	/**
	 * @param iapResourceToDisplayFrom the iapResourceToDisplayFrom to set
	 */
	public void setIapResourceToDisplayFrom(Short iapResourceToDisplayFrom);
	
	/**
	 * @return the iapTreeviewDisplay
	 */
	public Short getIapTreeviewDisplay();
	
	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(Short iapTreeviewDisplay);
	
	/**
	 * @return byDate
	 */
	public Date getByDate();
	
	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate);
	
	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior();
	
	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior);
	
	/**
	 * @param iapMasterFrequencies the iapMasterFrequencies to set
	 */
	public void setIapMasterFrequencies(Collection<IapMasterFrequency> iapMasterFrequencies);

	/**
	 * @return the iapMasterFrequencies
	 */
	public Collection<IapMasterFrequency> getIapMasterFrequencies();
	
	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes);

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes();
	
	/**
	 * @param incidentBranches the incidentBranches to set
	 */
	public void setIncidentBranches(Collection<IncidentBranch> incidentBranches);

	/**
	 * @return the incidentBranches
	 */
	public Collection<IncidentBranch> getIncidentBranches();
	
	/**
	    * @return the incident cost default hours
	*/
	public Integer getIncidentCostDefaultHours();
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentCostDefaultHours(Integer incidentCostDefaultHours);

	/**
	 * @return the costAutoRun
	 */
	public StringBooleanEnum getCostAutoRun();

	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(StringBooleanEnum costAutoRun) ;
	
	/**
	 * @return the costAccrualExtracts
	 */
	public Collection<CostAccrualExtract> getCostAccrualExtracts();

	/**
	 * @param costAccrualExtracts the costAccrualExtracts to set
	 */
	public void setCostAccrualExtracts(Collection<CostAccrualExtract> costAccrualExtracts);

	/**
	 * @return the accrualExtractNumber
	 */
	public Integer getAccrualExtractNumber() ;

	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Integer accrualExtractNumber) ;
	
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
	 * @param incidentOrgs the incidentOrgs to set
	 */
	public void setIncidentOrgs(Collection<IncidentOrg> incidentOrgs);

	/**
	 * @return the incidentOrgs
	 */
	public Collection<IncidentOrg> getIncidentOrgs();
	
	/**
	 * @return the projections
	 */
	public Collection<Projection> getProjections();

	/**
	 * @param projections the projections to set
	 */
	public void setProjections(Collection<Projection> projections);

	/**
	 * @return the branchSettings
	 */
	public Collection<BranchSetting> getBranchSettings();

	/**
	 * @param branchSettings the branchSettings to set
	 */
	public void setBranchSettings(Collection<BranchSetting> branchSettings) ;

	public String getIncidentNumber2(); 
	
	//irwin
	
	public String getIrwinIrwinId();

	public void setIrwinIrwinId(String irwinIrwinId);

	public String getIrwinFireDiscoveryDateTime(); 

	public void setIrwinFireDiscoveryDateTime(String irwinFireDiscoveryDateTime); 

	public String getIrwinPooResponsibleUnit(); 

	public void setIrwinPooResponsibleUnit(String irwinPooResponsibleUnit);
	
	public String getIrwinLocalIncidentIdentifier(); 

	public void setIrwinLocalIncidentIdentifier(String irwinLocalIncidentIdentifier);

	public String getIrwinIncidentName();

	public void setIrwinIncidentName(String irwinIncidentName);

	public String getIrwinIncidentTypeKind();

	public void setIrwinIncidentTypeKind(String irwinIncidentTypeKind);

	public String getIrwinIncidentTypeCategory(); 

	public void setIrwinIncidentTypeCategory(String irwinIncidentTypeCategory);

	public String getIrwinFireCode(); 

	public void setIrwinFireCode(String irwinFireCode);

	public String getIrwinFsJobCode();

	public void setIrwinFsJobCode(String irwinFsJobCode); 

	public String getIrwinFsOverrideCode();

	public void setIrwinFsOverrideCode(String irwinFsOverrideCode);
	
	public String getIrwinIsActive();

	public void setIrwinIsActive(String irwinIsActive);

	public String getIrwinRecordSource(); 

	public void setIrwinRecordSource(String irwinRecordSource); 

	public String getIrwinCreatedBySystem(); 

	public void setIrwinCreatedBySystem(String irwinCreatedBySystem);

	public String getIrwinCreatedOnDateTime(); 

	public void setIrwinCreatedOnDateTime(String irwinCreatedOnDateTime);

	public String getIrwinModifiedBySystem(); 

	public void setIrwinModifiedBySystem(String irwinModifiedBySystem); 

	public String getIrwinModifiedOnDateTime(); 

	public void setIrwinModifiedOnDateTime(String irwinModifiedOnDateTime); 
	
	public String getIrwinPooProtectingUnit(); 

	public void setIrwinPooProtectingUnit(String irwinPOOProtectingUnit);
	
	public String getIrwinUniqueFireIdentifier();
	
	public void setIrwinUniqueFireIdentifier(String irwinUniqueFireIdentifier);
	
	public String getIrwinIsComplex();
	
	public void setIrwinIsComplex(String irwinIsComplex);
	
	public String getIrwinComplexParentIrwinId();
	
	public void setIrwinComplexParentIrwinId(String irwinComplexParentIrwinId);
	
	public String getIrwinAbcdMisc();
	
	public void setIrwinAbcdMisc(String irwinAbcdMisc);
	
    public String getIrwinStatus();
	
	public void setIrwinStatus(String irwinStatus);

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
	
	public String getIrwinIsValid();

	public void setIrwinIsValid(String irwinIsValid);
	
	/**
	 * @param complexity the complexity to set
	 */
	public void setComplexity(Complexity complexity);

	/**
	 * @return the complexity
	 */
	public Complexity getComplexity();

	/**
	 * @param complexityId the complexityId to set
	 */
	public void setComplexityId(Long complexityId);

	/**
	 * @return the complexityId
	 */
	public Long getComplexityId();

}