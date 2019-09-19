package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface IapAircraftDao extends TransactionSupport, CrudDao<IapAircraft> {
	
}
