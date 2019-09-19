package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * Service layer area used for accessing accountCode functionality.
 * 
 * @author bsteiner
 */
public interface IncidentAccountCodeService {

   /**
    * Delete the {@link IncidentAccountCode} corresponding to this ID
    * @param incidentAccountCodeId
    * @throws ServiceException
    */
	public void delete(Long incidentAccountCodeId) throws ServiceException;

	/**
	 * Get all {@link IncidentAccountCode} objects from the DB based on the filter
	 * @param filter
	 * @return - A {@link Collection} of {@link IncidentAccountCodeVo} objects
	 * @throws ServiceException
	 */
   public Collection<IncidentAccountCodeVo> getAll(IncidentAccountCodeFilter filter) throws ServiceException;

   /**
    * Converts the {@link IncidentAccountCodeVo} to an {@link IncidentAccountCode} object
    * @param incidentAccountCodeVo
    * @return - The converted {@link IncidentAccountCode}
    * @throws ServiceException
    */
   public IncidentAccountCode voToDo(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException;

   /**
    * Save an {@link IncidentAccountCode} to the DB
    * @param incidentAccountCodeVo
    * @throws ServiceException
    */
   public void addIncidentAccountCode(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException;
   
   /**
    * Update an {@link IncidentAccountCode}
    * @param incidentAccountCodeVo
    * @throws ServiceException
    */
   public void updateIncidentAccountCode(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException;

   /**
    * Determine which account codes are associated with which incidents
    * @param accountCodeId
    * @return - A {@link Collection} of {@link IncidentAccountCodeVo} objects
    * @throws ServiceException
    */
   public Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(Long accountCodeId) throws ServiceException;
   
   /**
    * Determine which account codes are associated with which incidents
    * @param accountFireCode
    * @return - A {@link Collection} of {@link IncidentAccountCodeVo} objects
    * @throws ServiceException
    */
   public Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(String accountFireCode) throws ServiceException;
   
   /**
    * Get an account code by ID
    * @param accountCodeId
    * @return - String representation of an {@link AccountCode}
    * @throws ServiceException
    */
   public String getAccountFireCodeById(Long accountCodeId) throws ServiceException;

   /**
    * Save an {@link IncidentAccountCode} to the DB
    * @param vo
    * @throws ServiceException
    */
   public void save(IncidentAccountCodeVo vo) throws ServiceException;
   
   /**
    * Warns the user about existing {@link Incident} and {@link AccountCode} relationships that might be changed.
    * @param incidentAccountCodeVo
    * @return - <code>boolean</code> value determining if a dialog should be displayed to the user.
    * @throws ServiceException
    */
   public boolean shouldWarnUserAboutExistingRelationships(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException;
   
   /**
    * Return a list of {@link Incident}s that use this {@link AccountCode}
    * @param incidentAccountCodeVo
    * @return Comma separated {@link String} of existing {@link Incident}s that use this {@link AccountCode}
    * @throws ServiceException
    */
   public String getIncidentsInvolvedInPotentialMassUpdate(IncidentAccountCodeVo incidentAccountCodeVo) throws ServiceException;
   
   /**
    * Get the eventTypeId that corresponds to the incidentId from the isw_incident table
    * 
    * @param incidentId
    * @return the eventTypeId corresponding to the incidentId
    * @throws PersistenceException
    */
   public Long getEventTypeIdByIncidentId(Long incidentId) throws ServiceException;
   
   /**
    * 
    * @param filter
    * @return
    * @throws ServiceException
    */
   public Collection<IncidentAccountCodeVo> getGrid(IncidentAccountCodeFilter filter) throws ServiceException;

   /**
    * Populate a single {@link IncidentAccountCodeVo} by id
    * @param id
    * @return A single {@link IncidentAccountCodeVo}
    * @throws ServiceException
    */
   public IncidentAccountCodeVo getIacVoById(Long id) throws ServiceException;
}
