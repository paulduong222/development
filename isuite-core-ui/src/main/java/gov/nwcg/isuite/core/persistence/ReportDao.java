package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ReportDao extends TransactionSupport, CrudDao<Report>{

	public Collection<String> getObsoleteReportFilenames(String beforeDate) throws PersistenceException;
	public void deleteObsoleteReports(String beforeDate) throws PersistenceException;
	
}
