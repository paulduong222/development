package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.filter.AgencyGroupFilter;
import gov.nwcg.isuite.core.service.impl.AgencyGroupServiceImpl;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This is the interface for the {@link AgencyGroupServiceImpl} class and allows a user
 * to add/edit/and otherwise modify {@link AgencyGroup} records in the system
 * 
 * @author mpoll
 */
//@Secured({AuthorityStore.ROLE_DATA_STEWARD})
public interface AgencyGroupService {

   /**
    * Get the User Group grid populated based on the provided filter set.
    * 
    * @param filter {@link AgencyGroupFilter}
    * @return {@link Collection} of {@link AgencyGroupVo} objects.
    * @throws ServiceException
    */
   public Collection<AgencyGroupVo> getGrid(AgencyGroupFilter filter) throws ServiceException;

   /**
    * Retrieve AgencyGroupVo data
    * 
    * @param id The id of the {@link AgencyGroup} we are retrieving
    * @return The {@link AgencyGroupVo} for the id specified
    * @throws ServiceException
    */
   public AgencyGroupVo getById(Long id) throws ServiceException;
   
   /**
    * Saves the AgencyGroup information
    * 
    * @param vo The {@link AgencyGroupVo} we are saving
    * @throws ServiceException
    */
   public AgencyGroupVo save(AgencyGroupVo vo) throws ServiceException;
   
   /**
    * 
    * @param dialogueVo
    * @param agencyGroupVo
    * @return
    * @throws ServiceException
    */
   public DialogueVo save(DialogueVo dialogueVo, AgencyGroupVo agencyGroupVo) throws ServiceException;

   /**
    * Deletes a {@link Collection} of {@link AgencyGroup} records
    * 
    * @param agencyGroupIds The ids of the {@link AgencyGroup} records
    * @throws ServiceException
    */
   public void delete(Collection<Long> agencyGroupIds) throws ServiceException;
   
}
