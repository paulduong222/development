package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.vo.GridColumnUserVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface UserPreferencesService extends TransactionService {

	/**
	 * Returns a collection of GridColumnUserVo's.  GridColumnUserVo's are grid column vo's 
	 * for a specific user.
	 * 
	 * @param filter
	 * 			the GridColumnUserFilter to set
	 * @return
	 * 		collection of GridColumnUserVo's.
	 * @throws ServiceException
	 */
	public Collection<GridColumnUserVo> getUserGridColumns(GridColumnUserFilter filter) throws ServiceException;
	
	
	/**
	 * Saves the user's grid column preferences.
	 * 
	 * @param vos
	 * 			collection of GridColumnUserVo's
	 * @throws ServiceException
	 */
	public Collection<GridColumnUserVo>  saveAll(Collection<GridColumnUserVo> vos) throws ServiceException;

    public DialogueVo saveAll2(Collection<GridColumnUserVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Restore the default system columns for the Resources and Demob grids.
	 * @param gridName
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public Collection<GridColumnUserVo> restoreDefaults(String gridName, Long userId) throws ServiceException;

	/**
	 * 
	 * @param userId
	 * @param currentPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo updateShowDataSavedMsg(Long userId, Boolean showDataSavedMsg, DialogueVo dialogueVo) throws ServiceException;
}
