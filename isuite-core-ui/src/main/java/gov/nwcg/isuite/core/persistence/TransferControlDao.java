package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.TransferControl;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface TransferControlDao extends TransactionSupport, CrudDao<TransferControl> {
	
	public int getStatusEmptyCount() throws PersistenceException;
	
	public void createRecord(Boolean isGroupTransfer, String groupTi, String incTi,Long dtUserId) throws PersistenceException;

	public String getGroupTi(Long id) throws PersistenceException;

	public String getIncidentTi(Long id) throws PersistenceException;
	
	public void generateTI() throws PersistenceException;
}
