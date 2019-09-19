package gov.nwcg.isuite.core.persistence;


import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface IncidentShiftDao extends TransactionSupport, CrudDao<IncidentShift> {
	
}
