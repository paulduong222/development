/**
 * 
 */
package gov.nwcg.isuite.core.service;


import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author dbudge
 *
 */
public interface AgencyService {

   /**
    * Return all existing Agencies. (This is static data and only needs to be queried once).
    * @param agencyFilter
    * @return {@link Collection} of {@link AgencyVo} objects.
    * @throws ServiceException
    */
   public Collection<AgencyVo> getGrid(AgencyFilter agencyFilter) throws ServiceException;

   /**
    * Return all existing Agencies. (This is static data and only needs to be queried once).
    * @param agencyFilter
    * @return {@link Collection} of {@link AgencyVo} objects.
    * @throws ServiceException
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter agencyFilter) throws ServiceException;
   
   
   /**
    * Retrieve AgencyVo data
    * 
    * @param id The id of the {@link Agency} we are retrieving
    * @return The {@link AgencyVo} for the id specified
    * @throws ServiceException
    */
   public AgencyVo getById(Long id) throws ServiceException;
   
   /**
    * Saves the Agency information
    * 
    * @param vo The {@link AgencyVo} we are saving
    * @throws ServiceException
    */
   public void save(AgencyVo vo) throws ServiceException;

   /**
    * 
    * @param dialogueVo
    * @param agencyVo
    * @return
    * @throws ServiceException
    */
   public DialogueVo save(DialogueVo dialogueVo, AgencyVo agencyVo) throws ServiceException;
   
   /**
    * Deletes a {@link Collection} of {@link Agency} records
    * 
    * @param AgencyIds The ids of the {@link Agency} records
    * @throws ServiceException
    */
   public void delete(Collection<Long> agencyIds) throws ServiceException;

   /**
    * 
    * @param dialogueVo
    * @param agencyVo
    * @return
    * @throws ServiceException
    */
   public DialogueVo delete(DialogueVo dialogueVo, AgencyVo agencyVo) throws ServiceException;
}
