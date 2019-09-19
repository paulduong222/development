package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.UserMessage;
import gov.nwcg.isuite.core.filter.UserMessageFilter;
import gov.nwcg.isuite.core.vo.UserMessageVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * Service layer area used for user message functionality
 *
 * @author mgreen
 */
public interface UserMessageService {
   
   /**
    * Retrieve user message by id
    * 
    * @param id
    * @param dummyValue
    * @return
    * @throws ServiceException
    */
   public UserMessageVo getById(Long id, Boolean dummyValue) throws ServiceException;

   public Collection<UserMessage> getUserCurrentMessages(UserMessageFilter userMessageFilter) throws ServiceException, Exception;
   
   public Collection<UserMessage> getUserClearedMessages(UserMessageFilter userMessageFilter) throws ServiceException, Exception;
   
   public void save(UserMessageVo userMessageVo) throws ServiceException;
   
   public void delete(UserMessageVo userMessageVo) throws ServiceException;

}
