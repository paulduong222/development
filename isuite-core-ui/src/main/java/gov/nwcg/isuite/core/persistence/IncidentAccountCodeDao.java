package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author bsteiner
 *
 */
public interface IncidentAccountCodeDao extends TransactionSupport, CrudDao<IncidentAccountCode>{
   
   /**
    * @param incidentId The Incident Id we are retrieving IncidentAccountCodes for
    * @return All IncidentAccountCodes associated with a specific incident Id
    * @throws PersistenceException
    */
   public Collection<IncidentAccountCodeVo> getIncidentAccountCodesByIncidentId(Long incidentId,IncidentAccountCodeFilter filter) throws PersistenceException;
   
   /**
    * @param accountCodeId The Account Code Id we are retrieving IncidentAccountCodes for
    * @return All IncidentAccountCodes associated with a specific accountCode Id
    * @throws PersistenceException
    */
   public Collection<IncidentAccountCode> getIncidentAccountCodesByAccountCodeId(Long accountCodeId) throws PersistenceException;
   
   /**
    * @param overrideAccountCodeId The Override Account Code Id we are retrieving IncidentAccountCodes for
    * @return All IncidentAccountCodes associated with a specific overrideAccountCodeId
    * @throws PersistenceException
    */
   public Collection<IncidentAccountCode> getIncidentAccountCodesByAccountCodeOrOverrideAccountCodeId(Long accountCodeId) throws PersistenceException;
   
   /**
    * @param incidentId The Incident Id we are retrieving the default IncidentAccountCode for
    * @return The default IncidentAccountCode associated with a specific incident Id
    * @throws PersistenceException
    */
   public IncidentAccountCode getDefaultIncidentAccountCodeByIncidentId(Long incidentId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<IncidentAccountCode> getGrid(Long incidentId, IncidentAccountCodeFilter filter) throws PersistenceException;
//   public Collection<IncidentAccountCodeGridVo> getGrid(IncidentAccountCodeFilter filter) throws PersistenceException;
   
   /**
    * Get the eventTypeId that corresponds to the incidentId from the isw_incident table
    * 
    * @param incidentId
    * @return the eventTypeId corresponding to the incidentId
    * @throws PersistenceException
    */
   public Long getEventTypeIdByIncidentId(Long incidentId) throws PersistenceException;
   
   /**
    * Return an IAC based on the accountCodeId and IncidentId passed in.
    * 
    * @param accountCodeId
    * @param IncidentId
    * @return {@link IncidentAccountCode}
    * @throws PersistenceException
    */
   public IncidentAccountCode getIncidentAccountCodeByAccountCodeIdAndIncidentId(Long accountCodeId, Long incidentId) throws PersistenceException;

   /**
    * Get an {@link IncidentAccountCode} and convert it to an {@link IncidentAccountCodeVo}
    * @param id
    * @return A single {@link IncidentAccountCodeVo}
    * @throws PersistenceException
    */
   public IncidentAccountCodeVo getIacVoById(Long id) throws PersistenceException;
   
   /**
    * Get a single {@link IncidentAccountCode} object based on the given accountCode number
    * @param accountCode
    * @return A single {@link IncidentAccountCode} object
    * @throws PersistenceException
    */
   public IncidentAccountCode getIACByAccountCode(String accountCode) throws PersistenceException;
   
   /**
    * Get an {@link IncidentAccountCode} object based on the given <b>accountCode</b> and <b>incidentId</b>
    * @param accountCode
    * @param incidentId
    * @return A single {@link IncidentAccountCode} object
    * @throws PersistenceException
    */
   public IncidentAccountCode getIACByAccountCodeAndIncidentId(String accountCode, Long incidentId, Long iacId) throws PersistenceException;

   /**
    * @param incidentId
    * @param incidentAccountCode
    * @throws PersistenceException
    */
   public void removeOtherDefault(Long incidentId, Long incidentAccountCodeId) throws PersistenceException;

   public void deleteBySql(Long icnidentAccountCodeId) throws PersistenceException;

	public Collection<IncidentAccountCode> getByAgencyAndCode(Long agencyId, String code, Long excludeIncidentId) throws PersistenceException;
   
	/**
	 * 
	 * @param accountCode
	 * @param agencyId
	 * @param incidentId
	 * @return 
	 * @throws PersistenceException
	 */
	public Collection<IncidentAccountCode> getByAgencyAndIncident(Long agencyId, Long incidentId,Long excludeIacId) throws PersistenceException;

	public Collection<IncidentAccountCode> getByIncidentId(Long incidentId) throws PersistenceException;

	public Collection<IncidentAccountCode> getByIncidentGroupId(Long incidentGroupId) throws PersistenceException;

	public int getTimePostingCount(Long id, Long incidentId) throws PersistenceException ;

	public int getResourceCount(Long id, Long incidentId) throws PersistenceException;	
	
	public void updateNonDefaults(Long incidentId,Long excludeIacId) throws PersistenceException;
	
	public int getTimeAdjustmentsCount(Long id, Long incidentId) throws PersistenceException;
}
