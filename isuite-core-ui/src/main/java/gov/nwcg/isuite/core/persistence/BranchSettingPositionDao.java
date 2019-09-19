package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface BranchSettingPositionDao extends TransactionSupport, CrudDao<BranchSettingPosition> {

	public Boolean isDuplicatePosition(Long branchSettingId, String position) throws PersistenceException;
	
	public Collection<BranchSettingPosition> getByBranchAndPosition(Long branchSettingId, String position) throws PersistenceException ;
	
	public void updateBranchSettingPosition(Long branchSettingId, String position, Collection<Long> kindIds) throws PersistenceException;
	
}
