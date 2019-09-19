package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentSelectorDao extends TransactionSupport {

	public Collection<IncidentSelector2Vo> getIncidents(Long userId, Long incidentId,Boolean filterExcluded) throws PersistenceException;

	public Collection<IncidentSelector2Vo> getIncidentGroups(Long userId, Long incidentGroupId,Boolean filterExcluded) throws PersistenceException;

	public Collection<IncidentSelector2Vo> getIncidentGroupIncidents(Long groupId) throws PersistenceException;
	
	public Collection<IncidentSelector2Vo> getExcludedIncidents(Long userId,Long incidentId) throws PersistenceException;
	
	public Collection<IncidentSelector2Vo> getExcludedIncidentGroups(Long userId,Long incidentGroupId) throws PersistenceException;

	public int removeExcludedItems(Long userId,Collection<Long> incidentIds, Collection<Long> incidentGroupids) throws PersistenceException;
	
	public int createExcludedItems(Long userId,Collection<Long> incidentIds, Collection<Long> incidentGroupids) throws PersistenceException;
	
}
