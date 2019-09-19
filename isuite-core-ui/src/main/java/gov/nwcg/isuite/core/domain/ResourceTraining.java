package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface ResourceTraining extends Persistable {
	
	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind);

	/**
	 * @return the kindId
	 */
	public Long getKindId();
	
	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId);

	/**
	 * @param priorityProgram the priorityProgram to set
	 */
	public void setPriorityProgram(PriorityProgram priorityProgram);

	/**
	 * @return the priorityProgram
	 */
	public PriorityProgram getPriorityProgram();

	/**
	 * @param priorityProgramId the priorityProgramId to set
	 */
	public void setPriorityProgramId(Long priorityProgramId);

	/**
	 * @return the priorityProgramId
	 */
	public Long getPriorityProgramId();
	
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
	 * @param the initialAssignment to set
	 */
	public void setInitialAssignment(StringBooleanEnum initialAssignment);
	
	/**
	 * @return the initialAssignment
	 */
	public StringBooleanEnum isInitialAssignment();
	
	/**
	 * @param the fsPriorityTrainee to set
	 */
	public void setFsPriorityTrainee(StringBooleanEnum fsPriorityTrainee);
	
	/**
	 * @return the fsPriorityTrainee
	 */
	public StringBooleanEnum isFsPriorityTrainee();
	
	/**
	 * @param the validRedCard to set
	 */
	public void setValidRedCard(StringBooleanEnum validRedCard);
	
	/**
	 * @return the validRedCard
	 */
	public StringBooleanEnum isValidRedCard();

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate);

	/**
	 * @return the startDate
	 */
	public Date getStartDate();

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate);

	/**
	 * @return the endDate
	 */
	public Date getEndDate();

	/**
	 * @param objectiveIssuer the objectiveIssuer to set
	 */
	public void setObjectiveIssuer(String objectiveIssuer);

	/**
	 * @return the objectiveIssuer
	 */
	public String getObjectiveIssuer();
	
	/**
	 * @param ptbPercentage the ptbPercentage to set
	 */
	public void setPtbPercentage(Integer ptbPercentage);

	/**
	 * @return the ptbPercentage
	 */
	public Integer getPtbPercentage();

	/**
	 * @param numberOfAcres the numberOfAcres to set
	 */
	public void setNumberOfAcres(Long numberOfAcres);

	/**
	 * @return the numberOfAcres
	 */
	public Long getNumberOfAcres();

	/**
	 * @param tnspComments the tnspComments to set
	 */
	public void setTnspComments(String tnspComments);

	/**
	 * @return the tnspComments
	 */
	public String getTnspComments();

	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers);

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers();

	/**
	 * @param rscTrainingObjectives the rscTrainingObjectives to set
	 */
	public void setRscTrainingObjectives(Collection<RscTrainingObjective> rscTrainingObjectives);

	/**
	 * @return the rscTrainingObjectives
	 */
	public Collection<RscTrainingObjective> getRscTrainingObjectives();
	
	/**
	 * @param incidentTaskBook the incidentTaskBook to set
	 */
	public void setIncidentTaskBook(StringBooleanEnum incidentTaskBook);

	/**
	 * @return the incidentTaskBook
	 */
	public StringBooleanEnum isIncidentTaskBook();
	
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
	
	/**
	 * @param fuelTypes the fuelTypes to set
	 */
	public void setFuelTypes(Collection<FuelType> fuelTypes);

	/**
	 * @return the fuelTypes
	 */
	public Collection<FuelType> getFuelTypes();
	
	/**
	 * @param recommendation the recommendation to set
	 */
	public void setRecommendation(Recommendation recommendation);

	/**
	 * @return the recommendation
	 */
	public Recommendation getRecommendation();

	/**
	 * @param recommendationId the recommendationId to set
	 */
	public void setRecommendationId(Long recommendationId);

	/**
	 * @return the recommendationId
	 */
	public Long getRecommendationId();

}
