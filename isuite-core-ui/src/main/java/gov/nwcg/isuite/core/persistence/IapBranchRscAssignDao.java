package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapBranchRscAssignDao extends TransactionSupport, CrudDao<IapBranchRscAssign> {
	/**
	 * @param IapBranchRscAssignVo vo
	 * @return void
	 * @throws PersistenceException
	 */	
	public void autoFill(IapBranchRscAssignVo vo) throws PersistenceException;	
	
	/**
	 * @param IapBranchRscAssignVo vo
	 * @return Collection<IapBranchRscAssignVo>
	 * @throws PersistenceException
	 */	
	public Collection<IapBranchRscAssignVo> getRscAssignGrid(IapBranchRscAssignVo vo) throws PersistenceException;
	
	/**
	 * @param Collection<IapBranchRscAssignVo> vos
	 * @param Long iapPlanId
	 * @return IapBranchRscAssignVo
	 * @throws PersistenceException
	 */
	public List<Long> getResourcesAlreadyAssigned(Collection<IapBranchRscAssignVo> vos, Long iapPlanId) throws PersistenceException;
	
}
