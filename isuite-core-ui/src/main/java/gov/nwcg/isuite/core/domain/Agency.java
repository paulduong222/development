package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;

public interface Agency extends Persistable{

   /**
    * @param agencyCode the 4 digit agency code definition
    */
   public void setAgencyCode(String agencyCode);

   /**
    * @return the agency code
    */
   public String getAgencyCode();

   /**
    * @param agencyDescription the description of the agency code
    */
   public void setAgencyName(String agencyName);

   /**
    * @return the agency description
    */
   public String getAgencyName();
   
   /**
    * The type of agency.  Federal, State, County, Other.
    * @param agencyType
    */
   public void setAgencyType(AgencyTypeEnum agencyType);
   
   /**
    * 
    * @return
    */
   public AgencyTypeEnum getAgencyType();
   
   /**
    * @return the costDatas
    */
   public Collection<CostData> getCostDatas() ;

   /**
    * @param costDatas the costDatas to set
    */
   public void setCostDatas(Collection<CostData> costDatas) ;

   /**
    * @return the costGroupAgencyDaySharePercentages
    */
   public Collection<CostGroupAgencyDaySharePercentage> getCostGroupAgencyDaySharePercentages() ;
   /**
    * @param costGroupAgencyDaySharePercentages the costGroupAgencyDaySharePercentages to set
    */
   public void setCostGroupAgencyDaySharePercentages(Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages);

   /**
    * @return standard
    */
   public Boolean getStandard();
   
   /**
    * @return standard
    */
   public Boolean isStandard();

   /**
    * @param standard
    */
   public void setStandard(Boolean standard) ;   
   
   /**
	* @param agencyGroup the agencyGroup to set
	*/
   public void setAgencyGroup(AgencyGroup agencyGroup);

   /**
	* @return the agencyGroup
	*/
   public AgencyGroup getAgencyGroup();

   /**
	* @param agencyGroupId the agencyGroupId to set
	*/
   public void setAgencyGroupId(Long agencyGroupId);

   /**
	* @return the agencyGroupId
	*/
   public Long getAgencyGroupId();
   
   /**
	* @param rateGroup the rateGroup to set
	*/
   public void setRateGroup(RateGroup rateGroup);

	/**
	 * @return the rateGroup
	 */
	public RateGroup getRateGroup();

	/**
	 * @param rateGroupId the rateGroupId to set
	 */
	public void setRateGroupId(Long rateGroupId);

	/**
	 * @return the rateGroupId
	 */
	public Long getRateGroupId();
	
	/**
	 * @param accountCodes the accountCodes to set
	 */
	public void setAccountCodes(Collection<AccountCode> accountCodes);

	/**
	 * @return the accountCodes
	 */
	public Collection<AccountCode> getAccountCodes();
	
	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents);

	/**
	 * @return the incidents
	 */
	public Collection<Incident> getIncidents();

	/**
	 * @param organizations the organizations to set
	 */
	public void setOrganizations(Collection<Organization> organizations);

	/**
	 * @return the organizations
	 */
	public Collection<Organization> getOrganizations();

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources);

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources();

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers);

	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers();

	/**
	 * @param incidentCostRateStates the incidentCostRateStates to set
	 */
	public void setIncidentCostRateStates(Collection<IncidentCostRateState> incidentCostRateStates);

	/**
	 * @return the incidentCostRateStates
	 */
	public Collection<IncidentCostRateState> getIncidentCostRateStates();

	/**
	 * @param sysCostRateStates the sysCostRateStates to set
	 */
	public void setSysCostRateStates(Collection<SysCostRateState> sysCostRateStates);

	/**
	 * @return the sysCostRateStates
	 */
	public Collection<SysCostRateState> getSysCostRateStates();
   
	/**
	 * @return the state
	 */
	public Boolean getState() ;

	/**
	 * @param state the state to set
	 */
	public void setState(Boolean state);
	
	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes);

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes();
	
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
	 * @return the costGroupDefaultAgencyDaySharePercentages
	 */
	public Collection<CostGroupAgencyDaySharePercentage> getCostGroupDefaultAgencyDaySharePercentages();

	/**
	 * @param costGroupDefaultAgencyDaySharePercentages the costGroupDefaultAgencyDaySharePercentages to set
	 */
	public void setCostGroupDefaultAgencyDaySharePercentages(
			Collection<CostGroupAgencyDaySharePercentage> costGroupDefaultAgencyDaySharePercentages);
	
}
