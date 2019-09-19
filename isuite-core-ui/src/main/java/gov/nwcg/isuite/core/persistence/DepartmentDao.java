package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Department;
import gov.nwcg.isuite.core.vo.DepartmentVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface DepartmentDao extends TransactionSupport, CrudDao<Department> {
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<DepartmentVo> getPicklist() throws PersistenceException;

}
