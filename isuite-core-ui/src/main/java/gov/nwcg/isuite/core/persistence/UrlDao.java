package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Url;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * DAO for URLs
 * 
 * @author bsteiner
 */
public interface UrlDao extends TransactionSupport {

	/**
	 * Retrieves all pages from data store.
	 * @return collection of all pages from the data store.
	 * @throws ServiceException
	 */
	public Collection<Url> getAll() throws PersistenceException;
}
