package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ContractorNameHistory;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface ContractorNameHistoryDao extends TransactionSupport, CrudDao<ContractorNameHistory> {

}
