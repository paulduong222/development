package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface IapHospitalDao extends TransactionSupport, CrudDao<IapHospital> {
	
}
