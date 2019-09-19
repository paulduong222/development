package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;


public interface IncidentResource extends Persistable{

	/**
	 * Returns the Incident.
	 * 
	 * @return
	 * 		the incident to return
	 */
	public Incident getIncident();

	/**
	 * Sets the Incident.
	 * 
	 * @param incident
	 * 			the incident to set
	 */
	public void setIncident(Incident incident);

	/**
	 * Returns the id of the incident.
	 * 
	 * @return
	 * 		the id to return
	 */
	public Long getIncidentId();

	/**
	 * Sets the id of the incident.
	 * 
	 * @param id
	 * 		the id to set
	 */
	public void setIncidentId(Long id);

	/**
	 * Returns the resource.
	 * 
	 * @return
	 * 		the resource to return
	 */
	public Resource getResource();

	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 * 			the resource to set
	 */
	public void setResource(Resource resource);

	/**
	 * Returns the id of the resource.
	 * 
	 * @return
	 * 		the id to return
	 */
	public Long getResourceId();

	/**
	 * Sets the id of the resource.
	 * 
	 * @param id
	 * 		the id to set
	 */
	public void setResourceId(Long id);


	/**
	 * Return the name used at the incident.
	 * 
	 * @return
	 * 		the name used at the incident to return
	 */
	public String getNameAtIncident();

	/**
	 * Sets the name used at the incident.
	 * 
	 * @param name
	 * 			the name used at the incident to set
	 */
	public void setNameAtIncident(String name);

	/**
	 * Returns the work period.
	 * 
	 * @return
	 * 		the work period to return
	 */
	public WorkPeriod getWorkPeriod();

	/**
	 * Sets the work period.
	 * 
	 * @param impl
	 * 		the work period to set
	 */
	public void setWorkPeriod(WorkPeriod impl);

	/**
	 * @return the active
	 */
	public Boolean getActive();

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active);

	/**
	 * @return the costData
	 */
	public CostData getCostData() ;
	
	/**
	 * @param costData the costData to set
	 */
	public void setCostData(CostData costData);

	public Long getRossResReqId();
	
	public void setRossResReqId(Long rossResReqId);

	/**
	 * @return the incidentResourceDailyCosts
	 */
	public Collection<IncidentResourceDailyCost> getIncidentResourceDailyCosts() ;

	/**
	 * @param incidentResourceDailyCosts the incidentResourceDailyCosts to set
	 */
	public void setIncidentResourceDailyCosts(
			Collection<IncidentResourceDailyCost> incidentResourceDailyCosts) ;	
	
	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels);

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels();
	
	/**
	 * @param iapBranchPersonnels the iapBranchPersonnels to set
	 */
	public void setIapBranchPersonnels(Collection<IapBranchPersonnel> iapBranchPersonnels);

	/**
	 * @return the iapBranchPersonnels
	 */
	public Collection<IapBranchPersonnel> getIapBranchPersonnels();

	/**
	 * @return the costDataId
	 */
	public Long getCostDataId();

	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId) ;

	/**
	 * @return the dailyCostException
	 */
	public String getDailyCostException();

	/**
	 * @param dailyCostException the dailyCostException to set
	 */
	public void setDailyCostException(String dailyCostException) ;

	/**
	 * @return the resNumId
	 */
	public Long getResNumId();

	/**
	 * @param resNumId the resNumId to set
	 */
	public void setResNumId(Long resNumId);
	
	/** 
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings);
	
	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings();
	
	/**
	 * @param resourceHomeUnitContacts the resourceHomeUnitContacts to set
	 */
	public void setResourceHomeUnitContacts(Collection<ResourceHomeUnitContact> resourceHomeUnitContacts);

	/**
	 * @return the resourceHomeUnitContacts
	 */
	public Collection<ResourceHomeUnitContact> getResourceHomeUnitContacts();
	
	/**
	 * @param trainingContacts the trainingContacts to set
	 */
	public void setTrainingContacts(Collection<TrainingContact> trainingContacts);

	/**
	 * @return the trainingContacts
	 */
	public Collection<TrainingContact> getTrainingContacts();
	
	/**
	 * @param rscTrainingTrainer the rscTrainingTrainer to set
	 */
	//public void setRscTrainingTrainer(RscTrainingTrainer rscTrainingTrainer);

	/**
	 * @return the rscTrainingTrainer
	 */
	//public RscTrainingTrainer getRscTrainingTrainer();
	
	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers);

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers();
	
}