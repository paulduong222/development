package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface SystemDao extends TransactionSupport {

	public String getRevisionLevel() throws PersistenceException;
}
