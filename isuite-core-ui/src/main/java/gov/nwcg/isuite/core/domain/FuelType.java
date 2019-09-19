package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface FuelType extends Persistable {
	
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
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings);

	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings();
	
	
}
