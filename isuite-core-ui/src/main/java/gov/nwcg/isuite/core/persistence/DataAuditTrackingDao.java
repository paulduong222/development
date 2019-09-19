package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.filter.DataAuditTrackingFilter;
import gov.nwcg.isuite.core.filter.impl.DataAuditTrackingFilterImpl;
import gov.nwcg.isuite.core.vo.DataAuditTrackingGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * DAO for DataAuditTracking
 * 
 * @author jbrose
 */
public interface DataAuditTrackingDao extends TransactionSupport, CrudDao<DataAuditTracking>  {

	/**
	 * Retrieve a {@link Collection} of {@link DataAuditTrackingVo} objects based on the
	 * provided filter.
	 * @param filter {@link DataAuditTrackingFilter}
	 * @return A {@link Collection} of {@link DataAuditTrackingVo} objects
	 * @throws PersistenceException
	 */
	public Collection<DataAuditTrackingGridVo> getGrid(DataAuditTrackingFilter filter) throws PersistenceException;

}
