package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.vo.IapPositionItemCodeVo;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IapPositionItemCodeDao extends TransactionSupport, CrudDao<IapPositionItemCode> {
	
	/**
	 * @param incidentId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IapPositionVo> getGrid(Long incidentId) throws PersistenceException;
	
	/**
	 * @param vo
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IapPositionItemCode> getByPosition(IapPositionVo vo) throws PersistenceException;
	
	/**
	 * @param vo
	 * @param positionName
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IapPositionItemCode> getByPosition(IapPositionVo vo,String positionName) throws PersistenceException;
	
	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void deleteAll(Collection<IapPositionItemCode> persistables) throws PersistenceException;
	
	public int get204PositionCount(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public Collection<IapPositionItemCodeVo> getAllForIncident(Long incidentId) throws PersistenceException;
	
	public Collection<IapPositionItemCodeVo> getAllForIncidentGroup(Long incidentGroupId) throws PersistenceException;
	
}
