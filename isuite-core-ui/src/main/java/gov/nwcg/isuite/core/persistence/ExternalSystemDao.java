/**
 * 
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ExternalSystem;
import gov.nwcg.isuite.framework.exceptions.NoSuchItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;

/**
 * manage ExternalSystem data.
 * @author doug
 *
 */
public interface ExternalSystemDao {
	
	/**
	 * Retrieve the ExternalSystem data for receiving from OIS.
	 * @return the ExternalSystem data for receiving from OIS, will throw NoSuchElementException if the ois system data is not found
	 * @throws PersistenceException if needed
	 */
	public ExternalSystem getOis() throws PersistenceException, NoSuchItemException;
	
	/**
	 * Retrieve the ExternalSystem data for ROSS.
    * @param direction direction of data flow.
	 * @return the ExternalSystem data for ROSS, will throw NoSuchElementException if the ross system data is not found
	 * @throws PersistenceException if needed
	 */
	public ExternalSystem getRoss(DataFlowDirectionEnum direction) throws PersistenceException, NoSuchItemException;

	
	/**
	 * Retrieve the ExternalSystem data for receiving data from the specified ISuite site.
	 * @param name name of site
    * @param direction direction of data flow.
	 * @return  externalSystem data for site, or null if no data is found
	 * @throws PersistenceException
	 */
	public ExternalSystem getISuiteSite(String name, DataFlowDirectionEnum direction) throws PersistenceException;
	

	/**
	 * Retrieve the default ExternalSystem data from the specified ISuite site.
    * @param direction direction of data flow.
	 * @return  externalSystem data for site, or null if no data is found
	 * @throws PersistenceException
	 */
	public ExternalSystem getDefaultISuiteSite(DataFlowDirectionEnum direction) throws PersistenceException;
	

}
