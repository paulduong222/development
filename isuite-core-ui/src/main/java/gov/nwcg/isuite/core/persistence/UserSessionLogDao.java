package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface UserSessionLogDao extends TransactionSupport, CrudDao<UserSessionLog>{

	public int getUserLoginActivityCount(Long userId) throws PersistenceException;
	
}
