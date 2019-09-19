package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;
import gov.nwcg.isuite.framework.types.ExternalSystemEnum;

/**
 * Represents the system associated with data.
 * @author doug
 *
 */
public interface ExternalSystem extends Persistable {
	
	/**
	 * Name of external system.
	 * @return name of external system
	 */
	public String getName();
	
	/**
	 * Type of external system.
	 * @return type of external system
	 */
	public ExternalSystemEnum getType();
	
	/**
	 * Direction of data flow from the perspective of the Enterprise.
	 * @return direction of data flow from the perspective of the Enterprise
	 */
	public DataFlowDirectionEnum getDataFlowDirection();
	
	/**
	 * Schedule of when data for the system should be processed.
	 * @return schedule of when data for the system should be processed
	 */
	public Schedule getSchedule();

}
