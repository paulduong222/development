/**
 * Dao for accessing persisted messages.
 */
package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 */
public interface MessageDao extends TransactionSupport, CrudDao<Message> {
	
	public MessageBoardVo getStaticMessage() throws PersistenceException;
   
	public Collection<MessageBoardVo> getPopUpGrid() throws PersistenceException;
	
	public Collection<MessageBoardVo> getPopUpMessages(String clientLocalDate) throws PersistenceException;

	public void createDefaultStaticMessage() throws PersistenceException;

}

