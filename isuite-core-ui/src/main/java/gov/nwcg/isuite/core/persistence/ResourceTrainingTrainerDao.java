package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResourceTrainingTrainerDao extends TransactionSupport, CrudDao<RscTrainingTrainer> {
	
	public Collection<RscTrainingTrainerVo> getEvalIncResGrid(Long incidentResourceId, Long incidentId, Long incidentGroupId) throws PersistenceException;
	

}
