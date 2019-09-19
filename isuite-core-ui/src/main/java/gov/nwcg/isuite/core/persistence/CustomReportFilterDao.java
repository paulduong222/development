package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CustomReportFilter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface CustomReportFilterDao extends TransactionSupport, CrudDao<CustomReportFilter> {
	
}
