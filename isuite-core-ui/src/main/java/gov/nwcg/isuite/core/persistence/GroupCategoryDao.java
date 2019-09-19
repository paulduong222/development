package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface GroupCategoryDao extends TransactionSupport, CrudDao<GroupCategory> {

	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<GroupCategoryVo> getPicklist() throws PersistenceException;
	
	/**
	 * @param code
	 * @param excludeId
	 * @return
	 * @throws PersistenceException
	 */
	public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException;

}
