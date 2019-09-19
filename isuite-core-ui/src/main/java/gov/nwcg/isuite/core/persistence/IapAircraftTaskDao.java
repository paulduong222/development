package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface IapAircraftTaskDao extends TransactionSupport, CrudDao<IapAircraftTask> {
	
}
