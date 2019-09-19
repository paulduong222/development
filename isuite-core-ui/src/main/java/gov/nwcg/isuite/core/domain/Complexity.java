package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface Complexity extends Persistable {
	
	/**
	 * @return the code
	 */
	public String getCode();

	/**
	 * @param code
	 */
	public void setCode(String code);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description
	 */
	public void setDescription(String description);

	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
//	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers);

	/**
	 * @return the rscTrainingTrainers
	 */
//	public Collection<RscTrainingTrainer> getRscTrainingTrainers();

	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents);

	/**
	 * @return the incidents
	 */
	public Collection<Incident> getIncidents();

}
