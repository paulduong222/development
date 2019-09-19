package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;


public interface GlidePathReportService {

	public DialogueVo generateGlidePathReport(GlidePathReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
}
