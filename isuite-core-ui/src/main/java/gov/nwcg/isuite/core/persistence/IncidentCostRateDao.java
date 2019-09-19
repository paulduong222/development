package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.IncidentCostRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentCostRateDao extends TransactionSupport, CrudDao<IncidentCostRate>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRate getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentCostRate persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRate> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentCostRate persistable) throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentCostRateGridVo> getDefaultRatesGrid(CostRateFilter filter) throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public IncidentCostRate getIncidentCostRate(CostRateFilter filter) throws PersistenceException ;
	
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds(CostRateFilter filter) throws PersistenceException;

	public IncidentCostRateOvhd getIncidentCostRateOvhd(CostRateFilter filter) throws PersistenceException ;

}
