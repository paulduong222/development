package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.RateGroup;
import gov.nwcg.isuite.core.vo.RateGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface RateGroupDao extends TransactionSupport, CrudDao<RateGroup> {

	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RateGroupVo> getPicklist() throws PersistenceException;

}
