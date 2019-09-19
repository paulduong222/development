package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface RateClassRateDao extends TransactionSupport, CrudDao<RateClassRate> {
	
	/**
	 * @param adClass
	 * @param year
	 * @param excludeId
	 * @return
	 * @throws PersistenceException
	 */
	public int getDuplicateCodeCount(String adClass, Integer year, Long excludeId) throws PersistenceException;
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RateClassRateVo> getPicklist(Long rateClassId) throws PersistenceException;
	
	/**
	 * @param trainingRateId
	 * @return
	 * @throws PersistenceException
	 */
	public Boolean isTrainingRate(Long trainingRateId) throws PersistenceException;
	

}
