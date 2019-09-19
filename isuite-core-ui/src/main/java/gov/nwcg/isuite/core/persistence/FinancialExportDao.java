package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.vo.FinancialExportResourceDataVo;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface FinancialExportDao extends TransactionSupport, CrudDao<FinancialExport> {
	
	public Collection<FinancialExportVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public FinancialExportResourceDataVo getFinancialExportResourceData(Long timeInvoiceId) throws PersistenceException;
	
}
