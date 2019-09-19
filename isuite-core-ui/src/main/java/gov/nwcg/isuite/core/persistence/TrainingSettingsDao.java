package gov.nwcg.isuite.core.persistence;


import java.util.Collection;

import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface TrainingSettingsDao extends TransactionSupport, CrudDao<TrainingSettings> {
	
//	public Collection<FuelTypeVo> getSelectedFuelTypes(Long trainingSettingsId) throws PersistenceException;
	
//	public Collection<FuelTypeVo> getAvailableFuelTypes(Long trainingSettingsId) throws PersistenceException;
	
//	public TrainingSettings getTrainingSettings(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public Collection<TrainingSettingsVo> getTrainingSettings(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public TrainingSettings getByIncidentGroupId (Long incidentGroupId) throws PersistenceException;
	
	public Collection<TrainingSettings> getByIncidentIds(Collection<Long> incidentIds) throws PersistenceException;

}
