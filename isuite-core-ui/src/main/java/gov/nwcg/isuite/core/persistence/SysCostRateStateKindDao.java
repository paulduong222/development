package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SysCostRateStateKindDao extends TransactionSupport, CrudDao<SysCostRateStateKind> {

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<SysCostRateStateKind> getSysCostRateStateKinds(CostRateFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(SysCostRateStateKind persistable) throws PersistenceException;
	
}
