package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.filter.JetPortFilter;
import gov.nwcg.isuite.core.service.impl.JetPortServiceImpl;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This is the interface for the {@link JetPortServiceImpl} class and allows a user
 * to add/edit/and otherwise modify {@link JetPort} records in the system
 * 
 * @author mpoll
 */
//@Secured({AuthorityStore.ROLE_DATA_STEWARD})
public interface JetPortService {

   /**
    * Get the User Group grid populated based on the provided filter set.
    * 
    * @param filter {@link JetPortFilter}
    * @return {@link Collection} of {@link JetPortVo} objects.
    * @throws ServiceException
    */
   public Collection<JetPortVo> getGrid(JetPortFilter filter) throws ServiceException;

   /**
    * Retrieve JetPortVo data
    * 
    * @param id The id of the {@link Sit209} we are retrieving
    * @return The {@link Sit209Vo} for the id specified
    * @throws ServiceException
    */
   public JetPortVo getById(Long id) throws ServiceException;
   
   /**
    * Saves the JetPort information
    * 
    * @param vo The {@link JetPortVo} we are saving
    * @throws ServiceException
    */
   public void save(JetPortVo vo) throws ServiceException;

   /**
    * 
    * @param dialogueVo
    * @param jetPortVo
    * @return
    * @throws ServiceException
    */
   public DialogueVo save(DialogueVo dialogueVo, JetPortVo jetPortVo) throws ServiceException;
   
   /**
    * Deletes a {@link Collection} of {@link JetPort} records
    * 
    * @param jetPortIds The ids of the {@link JetPort} records
    * @throws ServiceException
    */
   public void delete(Collection<Long> jetPortIds) throws ServiceException;
   
}
