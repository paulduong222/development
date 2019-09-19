/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.UserMessage;
import gov.nwcg.isuite.core.domain.impl.UserMessageImpl;
import gov.nwcg.isuite.core.filter.UserMessageFilter;
import gov.nwcg.isuite.core.persistence.MessageDao;
import gov.nwcg.isuite.core.persistence.UserMessageDao;
import gov.nwcg.isuite.core.service.UserMessageService;
import gov.nwcg.isuite.core.vo.UserMessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.other.UserSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.context.SecurityContextHolder;

/**
 * Service layer area for accessing user message functionality
 * 
 * @author mgreen
 *
 */
public class UserMessageServiceImpl extends BaseService implements UserMessageService {
   private UserMessageDao userMsgDao;
   private MessageDao msgDao;

   public UserMessageServiceImpl(){
	   
   }
   
   public void initialization(){
	   userMsgDao = (UserMessageDao)super.context.getBean("userMessageDao");
	   msgDao = (MessageDao)super.context.getBean("messageDao");
   }

   public Collection<UserMessage> getUserCurrentMessages(UserMessageFilter userMessageFilter) throws ServiceException, Exception {
      UserSession session = (UserSession)SecurityContextHolder.getContext().getAuthentication().getDetails();
      userMessageFilter.setUserId(session.getUserId());
      userMessageFilter.setActive(true);
      return this.userMsgDao.getAll(userMessageFilter);
   }
   
   public Collection<UserMessage> getUserClearedMessages(UserMessageFilter userMessageFilter) throws ServiceException, Exception {
      
//      UserMessageFilter filter = new UserMessageFilterImpl();
//    filter.setUserId(1L); //TODO: get the user id from the session
      UserSession session = (UserSession)SecurityContextHolder.getContext().getAuthentication().getDetails();
      userMessageFilter.setUserId(session.getUserId());
      userMessageFilter.setActive(true);
      userMessageFilter.setClearedDate(new Date());
      return this.userMsgDao.getAll(userMessageFilter);
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.admin.UserMessageService#delete(gov.nwcg.isuite.domain.admin.impl.UserMessageVo)
    */
   public void delete(UserMessageVo userMessageVo) throws ServiceException {
      
      UserMessage userMsg = new UserMessageImpl();
      //userMsg = super.voToDo(userMessageVo, userMsg);
//      try { userMsgDao.delete(userMsg); }
//      catch(Exception ex) { throw new ServiceException(ex); }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.admin.UserMessageService#getById(java.lang.Long, java.lang.Boolean)
    */
   public UserMessageVo getById(Long id, Boolean dummyValue) throws ServiceException {
      return new UserMessageVo(this.getById(id));
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.admin.UserMessageService#save(gov.nwcg.isuite.domain.admin.impl.UserMessageVo)
    */
   public void save(UserMessageVo userMessageVo) throws ServiceException {
      UserMessage userMsg = null; //super.voToDo(userMessageVo, new UserMessageImpl());
//      try { userMsgDao.save(userMsg); }
//      catch(Exception ex) { throw new ServiceException(ex); }
   }

   public void delete(UserMessage persistable) throws ServiceException {
//      try { userMsgDao.delete(persistable); }
//      catch(PersistenceException ex) { throw new ServiceException(ex);  }
   }

   public Collection<UserMessage> getAll(UserMessageFilter filter) throws ServiceException {
      Collection<UserMessage> userMsgs = null;
      try { userMsgs = userMsgDao.getAll(filter); }
      catch(Exception ex) { new ServiceException(ex); }
      return userMsgs;
   }

   public UserMessage getById(Long id) throws ServiceException {

      UserMessage userMsg = null;
//      try {
//         userMsg = userMsgDao.getById(id, UserMessageImpl.class);
//      } catch (PersistenceException ex) {
//         throw new ServiceException(ex);
//      }
      
      return userMsg;
   }

   public void save(UserMessage persistable) throws ServiceException {
//      try { userMsgDao.save(persistable); }
//      catch (Exception ex) { throw new ServiceException(ex); }
   }

   public Collection<UserMessage> vosToDos(Collection<UserMessageVo> theVos) throws ServiceException {
      Collection<UserMessage> doList = new ArrayList<UserMessage>();

      for(UserMessageVo vo : theVos) {
         //doList.add(voToDo(vo, new UserMessageImpl()));
      }
      return doList;
   }

}
