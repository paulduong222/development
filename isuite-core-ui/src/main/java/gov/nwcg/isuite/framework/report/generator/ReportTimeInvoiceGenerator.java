package gov.nwcg.isuite.framework.report.generator;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public interface ReportTimeInvoiceGenerator extends ReportGenerator {
  
  DialogueVo deleteLastTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException;

  DialogueVo reprintTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
 
}
