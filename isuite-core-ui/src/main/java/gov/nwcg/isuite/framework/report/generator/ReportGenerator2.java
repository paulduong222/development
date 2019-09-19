package gov.nwcg.isuite.framework.report.generator;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public interface ReportGenerator2 {
	<E extends ReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
}
