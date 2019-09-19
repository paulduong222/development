package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.RequestCategory;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface RequestCategoryDao extends TransactionSupport, CrudDao<RequestCategory> {

	/**
	 * Retrieves a collection of Request Categories.
	 * @return  {@link Collection} of {@link ERequestCategoryVo}'s
	 * @throws PersistenceException
	 */
	public Collection<RequestCategoryVo> getPicklist() throws PersistenceException;

	public Collection<RequestCategoryVo> getAll() throws PersistenceException;

}
