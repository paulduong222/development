package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.vo.QuickStatsVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.SessionFactory;

public interface QuickStatsDao extends TransactionSupport{

	public void setSessionFactory(SessionFactory sf);

	/**
	 * 
	 * @param incidentIds
	 * @param incidentOrGroupId
	 * @param isIncident
	 * @return a single {@link QuickStatsVo} object.
	 * @throws PersistenceException
	 */
	public QuickStatsVo getQuickStats(Long incidentId, Long incidentGroupId) throws PersistenceException;

}
