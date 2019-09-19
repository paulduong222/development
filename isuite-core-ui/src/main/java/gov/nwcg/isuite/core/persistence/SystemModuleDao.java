package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SystemModule;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SystemModuleDao extends TransactionSupport, CrudDao<SystemModule> {

	public Collection<SystemModule> getAll() throws PersistenceException;
	
}
