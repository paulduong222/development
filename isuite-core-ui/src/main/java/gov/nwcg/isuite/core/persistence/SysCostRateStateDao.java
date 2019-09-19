package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SysCostRateStateDao extends TransactionSupport, CrudDao<SysCostRateState>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRateState getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SysCostRateState persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRateState> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SysCostRateState persistable) throws PersistenceException;
	
	/**
	 * @param vo
	 * @throws PersistenceException
	 */
	public void overwriteStateOvhdKindRates(String rateType,SysCostRateStateVo vo) throws PersistenceException;

	public SysCostRateState getSysCostRateState(CostRateFilter filter) throws PersistenceException;

//	public Collection<SysCostRateStateKind> getSysCostRateStateKinds(CostRateFilter filter) throws PersistenceException;
}
