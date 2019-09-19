package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RscTrainingObjective extends Persistable {
	
	/**
	 * @param resourceTraining the resourceTraining to set
	 */
	public void setResourceTraining(ResourceTraining resourceTraining);

	/**
	 * @return the resourceTraining
	 */
	public ResourceTraining getResourceTraining();

	/**
	 * @param resourceTrainingId the resourceTrainingId to set
	 */
	public void setResourceTrainingId(Long resourceTrainingId);

	/**
	 * @return the resourceTrainingId
	 */
	public Long getResourceTrainingId();

	/**
	 * @param objective the objective to set
	 */
	public void setObjective(String objective);

	/**
	 * @return the objective
	 */
	public String getObjective();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

}
