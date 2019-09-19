package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface BranchSettingDao extends TransactionSupport, CrudDao<BranchSetting> {

	public Collection<BranchSetting> getByIncidentorGroupId(Long incidentId, Long incidentGroupId) throws PersistenceException;

	public void createDefaultPositions(Long branchSettingId) throws PersistenceException;

	
}
