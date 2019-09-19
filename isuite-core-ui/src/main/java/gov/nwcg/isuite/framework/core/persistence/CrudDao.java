package gov.nwcg.isuite.framework.core.persistence;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;


/**
 * Represents the standard <i>crud</i> operations: create, retrieve, update,
 * delete.
 * 
 * @author dougAnderson
 */
public interface CrudDao<P extends Persistable> {

	/**
    * Retrieve single object from store.
    * 
    * @param id id of object to retrieve
    * @return object from store
    * @throws PersistenceException if problems
    */
	P getById(Long id, Class<?> clazz) throws PersistenceException;

	/**
    * Delete object from store.
    * 
    * @param transferable object to delete
    * @throws PersistenceException if problems
    */
	void delete(P persistable) throws PersistenceException;
	
	/**
	 * Save the object
	 * 
	 * @param transferable Object to save
	 * @throws PersistenceException on error.
	 */
	void save(P persistable) throws PersistenceException;
	
	/**
	 * Save the objects
	 * 
	 * @param transferable Objects to save
	 * @throws PersistenceException on error.
	 */
	void saveAll(Collection<P> persistables) throws PersistenceException;

	public void setSkipSetAuditInfo(Boolean val);
	
	public Boolean getSkipSetAuditInfo();

	public void setSkipFixCasing(Boolean val);
	
	public Boolean getSkipFixCasing();

}