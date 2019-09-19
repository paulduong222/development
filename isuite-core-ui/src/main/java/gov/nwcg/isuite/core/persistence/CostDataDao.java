package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface CostDataDao extends TransactionSupport, CrudDao<CostData>{

	
}