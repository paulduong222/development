package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapForm205Dao extends TransactionSupport, CrudDao<IapForm205> {
	
	/**
	 * @param Long iapPlanId, Long iapForm205Id
	 * @return Collection<IapFrequency>
	 * @throws PersistenceException
	 */	
	public List<String> verifyFrequenciesFrom205To204(Long iapPlanId, Long iapForm205Id) throws PersistenceException;
	
	/**
	 * @param Long iapPlanId, Long iapForm205Id
	 * @return Collection<IapFrequency>
	 * @throws PersistenceException
	 */	
	public List<String> verifyFrequenciesFrom204To205(Long iapPlanId, Long iapForm205Id) throws PersistenceException;
	
}
