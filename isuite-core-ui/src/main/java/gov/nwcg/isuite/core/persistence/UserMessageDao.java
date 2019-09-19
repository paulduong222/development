/**
 * 
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserMessage;
import gov.nwcg.isuite.core.filter.UserMessageFilter;
import gov.nwcg.isuite.core.vo.UserMessageVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author mgreen
 *
 */
public interface UserMessageDao  extends TransactionSupport {

   public Collection<UserMessageVo> getAllUserMessageVos(UserMessageFilter filter) throws PersistenceException;
   public Collection<UserMessage> getAllUserMessages(UserMessageFilter filter) throws PersistenceException;
//   public Collection<UserMessageVo> getUserMessagesByUserId(Long userId) throws Exception;
   public Collection<UserMessage> getUserMessagesByUserId(Long userId) throws Exception;
   @Deprecated
   public Collection<UserMessage> getAll(UserMessageFilter filter) throws PersistenceException;
   
}
