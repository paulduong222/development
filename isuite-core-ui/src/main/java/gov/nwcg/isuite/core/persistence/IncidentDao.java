package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.vo.GenericVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

/**
 * @author bsteiner
 *
 */
public interface IncidentDao extends TransactionSupport, CrudDao<Incident>{

	public void merge(Incident entity) throws PersistenceException;

	/**
	 * Deletes the record associated to the specified id.
	 * 
	 * @param id
	 * @throws PersistenceException
	 */
	public void delete(Long id) throws PersistenceException;

	/**
	 * A read-only Collection of Incidents for display on the grid.
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentGridVo> getGrid(IncidentFilter filter, Boolean privilegedUser) throws PersistenceException;

	/**
	 * Get the record by its ID.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public Incident getById(Long id) throws PersistenceException;

	/**
	 * Get only the Incident data needed for picklist
	 * 
	 * @return Collection of IncidentPickListVo
	 * @throws PersistenceException
	 */
	public Collection<IncidentPicklistVo> getIncidentPickList(IncidentFilter filter) throws PersistenceException;

	/**
	 * Retrieve all incidents by their ids.
	 * @param ids
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Incident> getAllByIds(Collection<Long> ids) throws PersistenceException;

	/**
	 * Get an {@link Incident} based on the incidentName
	 * @param incidentName
	 * @return A single {@link Incident} object
	 * @throws PersistenceException
	 */
	public Incident getByIncidentName(String incidentName) throws PersistenceException;

	/**
	 * Get an {@link Incident} object corresponding to the {@link Incident} <b>nbr</b> and <b>incidentYear</b>
	 * @param vo
	 * @param incidentYear
	 * @return A single {@link Incident} object
	 * @throws PersistenceException
	 */
	public Incident getByNbrAndIncidentYear(IncidentVo vo, Integer incidentYear) throws PersistenceException;

	public Incident getByIncNbrAndIncidentYear(IncidentVo vo, Integer incidentYear) throws PersistenceException ;

	public Collection<String> getPermissionsForSharedUser(Long incidentId, Long userId) throws PersistenceException;

	public Collection<IncidentVo> getPossibleRossMatches(IncidentFilter filter) throws PersistenceException ;


	public Collection<Incident> getByIncidentKey(String nbr, String name) throws PersistenceException;

	/**
	 * 
	 * @param incidentId
	 * @return
	 * @throws PersistenceException
	 */
	public int checkForTimePostings(Long incidentId) throws PersistenceException, Exception;

	public void deleteFromUserCustomView(Long incidentId) throws PersistenceException;

	public void executeInsertIncidentUser(Long incidentId) throws PersistenceException,Exception;   
	public void executeInsertIncidentNewUser(Long userId) throws PersistenceException,Exception;

	public Collection<Incident> getByRossIncidentId(Long id) throws PersistenceException ;	

	public Collection<Incident> getAutoCostRunIncidents() throws PersistenceException ;

	public void deleteCostRecords(Long incidentId) throws PersistenceException ;	

	public Collection<IncidentGridVo> getIncidentsForUser(Long userId) throws PersistenceException;

	public void createDefaultIapSettings(Long incidentId) throws PersistenceException;	

	public void preDeleteIncident(Long incidentId) throws PersistenceException;

	public Long getIncidentGroupId(Long incidentId) throws PersistenceException;

	public void restoreDefault203TemplateSettings(Long incidentId, String sectionCode) throws PersistenceException;

	public void restoreDefault204TemplateSettings(Long incidentId) throws PersistenceException;
	
	public Collection<Incident> getAllActiveIncidents() throws PersistenceException ;

	public Date getEarliestIncidentStartDate() throws PersistenceException;
	
	public Date getEarliestIncStartDateResource(Long incidentId, Long incidentResourceId) throws PersistenceException;

	public void updateSiteManaged(Long id, Boolean siteManaged) throws PersistenceException ;

	public void updateSiteManaged(String incidentTi, Boolean siteManaged) throws PersistenceException;

	public Long getIncidentQuestionId(String question, Long incidentId) throws PersistenceException;

	public Boolean isIncidentLocked(Long id, String idType) throws PersistenceException;

	public Boolean isSyncedOnce(Long id) throws PersistenceException;

	public void updateIsSyncedOnce(Long id) throws PersistenceException;

	public GenericVo getDataByIncAcctCodeId(Long iacId) throws PersistenceException;

	public Collection<Incident> getByIncNbrAndIncidentYear(Long homeUnitId, String incNumber,Integer incidentYear) throws PersistenceException;

	public Long getIncidentIdByTi(String ti) throws PersistenceException;

	public void cleanUpDuplicateQuestionIssue(Long incidentId) throws PersistenceException ;	
	
	public void createDefaultQuestionValues(Long incidentId) throws PersistenceException; 

}