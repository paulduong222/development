package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.TrainingContact;
import gov.nwcg.isuite.core.vo.TrainingContactVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface TrainingContactDao extends TransactionSupport, CrudDao<TrainingContact> {
	
	public Collection<TrainingContactVo> getContactResourcesGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public Collection<TrainingContactVo> getTrainingContactGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;

}
