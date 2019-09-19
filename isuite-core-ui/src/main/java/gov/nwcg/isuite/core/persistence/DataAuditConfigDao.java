package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface DataAuditConfigDao extends TransactionSupport, CrudDao<DataAuditConfig> {

	public DataAuditConfig getByEventName(String table, String event) throws PersistenceException;
	
 }
