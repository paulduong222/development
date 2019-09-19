package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.filter.impl.RossResourceImportFilterImpl;
import gov.nwcg.isuite.core.vo.RossResourceXmlFileDataVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface RossResourceImportService extends TransactionService {
	
	
	public DialogueVo getImportHistory(RossResourceImportFilterImpl filter,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo uploadFileComplete(String filename, DialogueVo dialogueVo) throws ServiceException;
	
	public void processRossResourcesImport(Boolean isOverheadFile,Collection<RossResourceXmlFileDataVo> vos) throws ServiceException;
	
}
