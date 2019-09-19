package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface CostGroupAgencyDaySharePercentageDao extends TransactionSupport, CrudDao<CostGroupAgencyDaySharePercentage> {
	
	/**
	 * @param costGroupAgencyDayShareFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupAgencyDaySharePercentageVo> getGrid(CostGroupAgencyDayShareFilter costGroupAgencyDayShareFilter) throws PersistenceException;

}
