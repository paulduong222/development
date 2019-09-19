package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.DailyForm;
import gov.nwcg.isuite.core.vo.DailyFormVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface DailyFormDao extends TransactionSupport, CrudDao<DailyForm> {
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<DailyFormVo> getPicklist() throws PersistenceException;

}
