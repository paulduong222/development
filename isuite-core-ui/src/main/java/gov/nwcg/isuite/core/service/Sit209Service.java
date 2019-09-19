package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.filter.Sit209Filter;
import gov.nwcg.isuite.core.service.impl.Sit209ServiceImpl;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This is the interface for the {@link Sit209ServiceImpl} class and allows a user
 * to add/edit/and otherwise modify {@link Sit209} records in the system
 * 
 * @author mpoll
 */
//@Secured({AuthorityStore.ROLE_DATA_STEWARD})
public interface Sit209Service {

   /**
    * Get the User Group grid populated based on the provided filter set.
    * 
    * @param filter {@link Sit209Filter}
    * @return {@link Collection} of {@link Sit209Vo} objects.
    * @throws ServiceException
    */
   public Collection<Sit209Vo> getGrid(Sit209Filter filter) throws ServiceException;

   /**
    * Retrieve Sit209Vo data
    * 
    * @param id The id of the {@link Sit209} we are retrieving
    * @return The {@link Sit209Vo} for the id specified
    * @throws ServiceException
    */
   public Sit209Vo getById(Long id) throws ServiceException;
   
   /**
    * Saves the Sit209 information
    * 
    * @param vo The {@link Sit209Vo} we are saving
    * @throws ServiceException
    */
   public Sit209Vo save(Sit209Vo vo) throws ServiceException;

   /**
    * 
    * @param dialogueVo
    * @param vo
    * @return
    * @throws ServiceException
    */
   public DialogueVo save(DialogueVo dialogueVo, Sit209Vo vo) throws ServiceException;
   
   /**
    * Deletes a {@link Collection} of {@link Sit209} records
    * 
    * @param sit209Ids The ids of the {@link Sit209} records
    * @throws ServiceException
    */
   public void delete(Collection<Long> sit209Ids) throws ServiceException;
   
}
