package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.filter.impl.IapPlanFilterImpl;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IapPlanDao extends TransactionSupport, CrudDao<IapPlan> {
	
	/**
	 * @param incidentId
	 * @param incidentGrouupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IapPlan> getAllIapPlans(Long incidentId, Long incidentGrouupId) throws PersistenceException;
	public Collection<IapPlan> getAllIapPlansByPlanName(IapPlanVo vo) throws PersistenceException;
	public void lockUnlockForms(Long iapPlanId, Boolean isLocked) throws PersistenceException ;
	public Collection<IapPlan> getAllIapPlans(Long incidentId,Long incidentGroupId, IapPlanFilterImpl filter) throws PersistenceException;
	public Long getIncidentId(Long planId) throws PersistenceException;
	public Long getIncidentGroupId(Long planId) throws PersistenceException;

}
