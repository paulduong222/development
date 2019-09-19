package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.RossIncidentFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface RossImportService extends TransactionService {
	
	
	/**
	 * Method the client side can call to finalize the uploading of a ross xml file.
	 * Once the client side upload the physical file to the rossfiles folder,
	 * this method will create the isw_ross_xml_file and isw_ross_xml_file_data records.
	 * 
	 * @param filename String
	 * 			the name of the file in the rossfiles folder
	 * @return int
	 * @throws ServiceException
	 */
	public DialogueVo uploadFileComplete(String filename, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getUploadStatus(String uploadId,DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public DialogueVo getRossIncidentList(DialogueVo dialogueVo, RossIncidentFilter filter) throws ServiceException; 

	/**
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteRossIncidentFile(Long id) throws ServiceException;

	/**
	 * @param rossXmlFileId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteRossImportFile(Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException;
	
	
	/**
	 * @param action
	 * 			either "next" or "previous" or "complete" or "cancel"
	 * @param rossXmlFileId
	 * 
	 * @param dialogueVo
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 */
	public DialogueVo processRossImport(String action,Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param dialogueVo
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getRossImportedList(DialogueVo dialogueVo, RossIncidentFilter filter) throws ServiceException ;

	public DialogueVo getRossIncidents(DialogueVo dialogueVo, IncidentFilter filter) throws ServiceException;

	public DialogueVo getRossIncidentExcludedResources(DialogueVo dialogueVo, IncidentFilter filter) throws ServiceException ;
	
	public DialogueVo reimportProcess(String action,Long rossXmlFileId, Collection<Object> ids, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo processRossImportBegin(Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo processRossImportEnd(RossImportVo rossImportVo, DialogueVo dialogueVo) throws ServiceException ;	
}
