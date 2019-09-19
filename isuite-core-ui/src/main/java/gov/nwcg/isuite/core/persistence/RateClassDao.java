package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RateClassDao extends TransactionSupport, CrudDao<RateClass> {

	public RateClass getById(Long id) throws PersistenceException;
	
	public Collection<RateClassVo> getPicklist() throws PersistenceException;

    public Collection<RateClassVo> getByRateClassId(Long id) throws PersistenceException;

    public Collection<Integer> getRateYears() throws PersistenceException ;
  
}
