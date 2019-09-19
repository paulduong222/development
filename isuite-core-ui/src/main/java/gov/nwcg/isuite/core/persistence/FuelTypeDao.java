package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.vo.FuelTypeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface FuelTypeDao extends TransactionSupport, CrudDao<FuelType> {
	
	public Collection<FuelTypeVo> getFuelTypes() throws PersistenceException;

}
