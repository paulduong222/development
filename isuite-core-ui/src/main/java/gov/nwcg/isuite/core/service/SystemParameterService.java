/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

/**
 * @author gdyer
 *
 */
public interface SystemParameterService {

	/**
	 * Returns all system roles defined.
	 * 
	 * @return
	 * 		collection of system roles.
	 * @throws PersistenceException
	 */
	public DialogueVo getByParameterName(String nm,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo save(SystemParameterVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteSystemParameter (SystemParameterVo vo,DialogueVo dialogueVo) throws ServiceException;
}
