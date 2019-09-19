package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface UserImportFailureDao extends TransactionSupport, CrudDao<UserImportFailure>{

	/**
	 * @throws PersistenceException
	 */
	public void deleteAll() throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<UserImportFailureVo> getGrid(UserImportFailureFilter filter) throws PersistenceException;
}
