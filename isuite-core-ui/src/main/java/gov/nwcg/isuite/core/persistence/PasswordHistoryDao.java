package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface PasswordHistoryDao extends TransactionSupport, CrudDao<PasswordHistory> {

	/**
	 * @param userId
	 * @param historyCount
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<PasswordHistory> getUserHistory(Long userId,int historyCount) throws PersistenceException;

}
