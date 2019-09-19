package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.core.vo.RscTrainingObjectiveVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResourceTrainingObjectiveDao extends TransactionSupport, CrudDao<RscTrainingObjective> {
	
	public Collection<RscTrainingObjectiveVo> getObjectives(Long resourceTrainingId) throws PersistenceException;

}
