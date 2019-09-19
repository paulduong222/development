package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ComplexityDao extends TransactionSupport, CrudDao<Complexity> {
	
	public Collection<ComplexityVo> getPicklist() throws PersistenceException;

}
