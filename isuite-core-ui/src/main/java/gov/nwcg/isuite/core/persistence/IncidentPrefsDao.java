/**
 * 
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

/**
 * @author mpoll
 *
 */
public interface IncidentPrefsDao extends TransactionSupport, CrudDao<IncidentPrefs> {

   /**
    * Retrieve Other Labels on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getOtherLabels(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;
   
   /**
    * Retrieve Logistics Fields on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getLogisticsFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;

   /**
    * Retrieve Planning Fields on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getPlanningFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;

   /**
    * Retrieve Finance Fields on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getFinanceFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;

   /**
    * Retrieve ICS221 Other Fields on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getICS221OtherFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;

   /**
    * Retrieve All ICS221 Fields on IncidentPrefs page in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws PersistenceException
    */
   public Collection<IncidentPrefsVo> getICS221Fields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;
  
//   /**
//    * 
//    * @param incidentGroupId
//    * @return
//    * @throws PersistenceException
//    */
//   public Collection<IncidentPrefs> getByIncidentGroupId(Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public List<IncidentPrefs> getByIncidentId(Long incidentId) throws PersistenceException;
   

   public IncidentPrefsOtherFields getPrefsOtherFields(Long incidentId) throws PersistenceException;
   
   /**
    * Retrieves all ICS204 Block 5 Fields for an incident, sorted by position. 
    * @param incidentId The Incident ID.
    * @param selected Leave null to return all fields; Set to true or false to return on selected or unselected fields, respectively. 
    * @return List of IncidentPrefs objects.
    * @throws PersistenceException
    */
   public Collection<IncidentPrefs> getICS204Block5FieldsForIncident(Long incidentId, Boolean selected) throws PersistenceException;
   
   /**
    * Retrieves all ICS204 Block 5 Fields for an incident group, sorted by position. 
    * @param incidentGroupId The Incident Group ID.
    * @param selected Leave null to return all fields; Set to true or false to return on selected or unselected fields, respectively. 
    * @return List of IncidentGroupPrefs objects.
    * @throws PersistenceException
    */
   public Collection<IncidentGroupPrefs> getICS204Block5FieldsForIncidentGroup(Long incidentGroupId, Boolean selected) throws PersistenceException;
}
