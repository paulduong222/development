package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.SysCostRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SysCostRateDao extends TransactionSupport, CrudDao<SysCostRate>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRate getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SysCostRate persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRate> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SysCostRate persistable) throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<SysCostRateGridVo> getDefaultRatesGrid(CostRateFilter filter) throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public SysCostRate getSysCostRate(CostRateFilter filter) throws PersistenceException ;
	
	public Collection<SysCostRateKind> getSysCostRateKinds(CostRateFilter filter) throws PersistenceException;

	public SysCostRateOvhd getSysCostRateOvhd(CostRateFilter filter) throws PersistenceException ;

}
