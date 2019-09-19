/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.ExternalSystem;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;
import gov.nwcg.isuite.framework.types.ExternalSystemEnum;

/**
 * Manages External Systems.
 * @author doug
 *
 */
public interface ExternalSystemService {
	
	ExternalSystem getSystem(ExternalSystemEnum type, String name, DataFlowDirectionEnum direction) throws ServiceException;

}
