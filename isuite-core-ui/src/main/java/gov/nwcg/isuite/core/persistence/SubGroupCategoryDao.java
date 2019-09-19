package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface SubGroupCategoryDao extends TransactionSupport, CrudDao<SubGroupCategory> {
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<SubGroupCategoryVo> getPicklist() throws PersistenceException;
	
	/**
	 * @param code
	 * @param excludeId
	 * @return
	 * @throws PersistenceException
	 */
	public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException;
	
}
