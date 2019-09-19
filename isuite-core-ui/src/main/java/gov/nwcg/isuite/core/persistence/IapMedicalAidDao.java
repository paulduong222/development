package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface IapMedicalAidDao extends TransactionSupport, CrudDao<IapMedicalAid> {
	
}
