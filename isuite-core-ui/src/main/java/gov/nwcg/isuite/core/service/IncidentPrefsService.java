package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author mpoll
 *
 */
public interface IncidentPrefsService {

   /**
    * Retrieve IncidentPrefsVo data
    * 
    * @param id The id of the {@link IncidentPrefs} we are retrieving
    * @return The {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public IncidentPrefsVo getById(Long id) throws ServiceException;
   
   /**
    * Retrieve collection of IncidentPrefsVo for a given Incident Id
    * @param incidentId
    * @return
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getByIncidentId(Long incidentId) throws ServiceException;
   
   /**
    * Retrieve Other Labels in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getOtherLabels(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;
   
   /**
    * Retrieve Logistics Fields in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getLogisticsFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve Planning Fields in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getPlanningFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve Finance Fields in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getFinanceFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve ICS221 Other Fields in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getICS221OtherFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve All ICS221 Fields in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentPrefs} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentPrefsVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getICS221Fields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve All Travel Questions in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentQuestionVo} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentQuestionVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentQuestionVo> getAirTravelQuestions(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;

   /**
    * Retrieve All Check In Questions in Tools section
    * 
    * @param incidentOrGroupId The id of the {@link IncidentQuestionVo} objects we are retrieving
    * @param isGroup true if incident group, false if regular incident
    * @return The {@link Collection} of {@link IncidentQuestionVo} for the id specified
    * @throws ServiceException
    */
   public Collection<IncidentQuestionVo> getCheckInQuestions(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;
   
//   /**
//    * 
//    * @param incidentOrGroupId
//    * @param isGroup
//    * @param vos
//    * @throws ServiceException
//    */
//   public void savePrefs(Long incidentOrGroupId, Boolean isGroup, Collection<IncidentPrefsVo> vos) throws ServiceException;
   
   /**
    * Saves Question information
    * 
    * @param vo The {@link IncidentQuestionVo} vo we are saving
    * @return {@link Collection} of all {@link IncidentQuestionVo} objects associated with that incident/group
    * @throws ServiceException
    */
   public Collection<IncidentQuestionVo> saveQuestion(IncidentQuestionVo vo) throws ServiceException;
   
   /**
    * Save multiple questions.
    * 
    * @param iqVos {@link Collection} of {@link IncidentQuestionVo} objects.
    * @return {@link Collection} of {@link IncidentQuestionVo} objects.
    * @throws ServiceException
    */
   public Collection<IncidentQuestionVo> saveQuestions(Collection<IncidentQuestionVo> iqVos) throws ServiceException;
   
   /**
    * Saves the position/order of the questions for the incident/group
    * @param vos {@link Collection} of all {@link IncidentQuestionVo} objects associated with that incident/group
    * @throws ServiceException
    */
   public void saveQuestionPositions(Collection<IncidentQuestionVo> vos) throws ServiceException;
   
   /**
    * Deletes a non-standard Question for a specific incident
    * 
    * @param incidentQuestionId The id of the incident question
    * @throws ServiceException
    */
   public void deleteQuestion(Long incidentQuestionId) throws ServiceException;
   
   /**
    * Retrieve available items ({@link KindImpl} objects).
    * 
    * @param incidentOrGroupId
    * @param isGroup
    * @param itemFilter
    * @return {@link Collection} of {@link KindVo} objects.
    * @throws ServiceException
    */
   public Collection<KindVo> getAvailablePrefKindCodes(Long incidentOrGroupId, Boolean isGroup, KindFilter itemFilter) throws ServiceException;
   
   /**
    * Retrieve selected items ({@link KindImpl} objects).
    * 
    * @param incidentOrGroupId
    * @param isGroup
    * @return {@link Collection} of {@link KindVo} objects.
    * @throws ServiceException
    */
   public Collection<KindVo> getSelectedPrefKindCodes(Long incidentOrGroupId, Boolean isGroup) throws ServiceException;
   
   /**
    * Saves Incident Kind Code information
    * 
    * @param incidentOrGroupId The id of the Incident
    * @param vos The {@link Collection} of {@link KindVo} objects we are saving
    * @param isGroup true if incident group, false if regular incident
    * @throws ServiceException
    */
   public void saveKindCodePrefs(Long incidentOrGroupId, Collection<KindVo> vos, Boolean isGroup) throws ServiceException;
   
   /**
    * Determine if this {@link Incident} has Non-Standard Travel questions associated with it.
    * 
    * @param incidentId
    * @return {@link Boolean}
    * @throws ServiceException
    */
   public Boolean checkForNonStandardTravelQuestions(Long incidentId) throws ServiceException;
   
   /**
    * Determine if this {@link Incident} has Non-Standard Checkin questions associated with it.
    * 
    * @param incidentId
    * @return
    * @throws ServiceException
    */
   public Boolean checkForNonStandardCheckinQuestions(Long incidentId) throws ServiceException;

   public IncidentPrefsOtherFieldsVo getIncidentPrefsOtherFields(Long incidentId) throws ServiceException;

   public IncidentPrefsOtherFieldsVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo) throws ServiceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Boolean} value indicating whether this {@link Incident} is assigned to an {@link IncidentGroup}
    * @throws ServiceException
    */
   public Boolean doesIncidentBelongToAGroup(Long incidentId) throws ServiceException;
   
   /**
    * 
    * @param incidentQuestionId
    * @return
    * @throws ServiceException
    */
   public IncidentQuestionVo getIncidentQuestionById(Long incidentQuestionId) throws ServiceException;
   
   /**
    * Retrieves all ICS204 Block 5 Fields for an incident, sorted by position. 
    * @param incidentId The Incident ID.
    * @param selected Leave null to return all fields; Set to true or false to return on selected or unselected fields, respectively. 
    * @return List of IncidentPrefsVo objects.
    * @throws ServiceException
    */
   public Collection<IncidentPrefsVo> getICS204Block5FieldsForIncident(Long incidentId, Boolean selected) throws ServiceException;
   
   /**
    * Retrieves all ICS204 Block 5 Fields for an incident group, sorted by position. 
    * @param incidentGroupId The Incident Group ID.
    * @param selected Leave null to return all fields; Set to true or false to return on selected or unselected fields, respectively. 
    * @return List of IncidentGroupPrefsVo objects.
    * @throws ServiceException
    */
   public Collection<IncidentGroupPrefsVo> getICS204Block5FieldsForIncidentGroup(Long incidentGroupId, Boolean selected) throws ServiceException;
   
}
