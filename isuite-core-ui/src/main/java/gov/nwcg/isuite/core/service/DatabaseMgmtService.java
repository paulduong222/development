package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.io.IOException;

public interface DatabaseMgmtService {

	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 * @throws IOException 
	 */
	public DialogueVo saveDb(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException, IOException;

	/**
	 * @param sourceVo
	 * @param targetVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo copyDb(DbAvailVo sourceVo, DbAvailVo targetVo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo backupDb(DbAvailVo vo, String destFileName, String altDestination, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filename
	 * @param targetDbName
	 * @param pwd
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo restoreDb(String filename, String targetDbName, String pwd, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo removeDb(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo generateRecoverCode(String filename, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getDefaultBackupDir(DialogueVo dialogueVo) throws ServiceException;
	
}
