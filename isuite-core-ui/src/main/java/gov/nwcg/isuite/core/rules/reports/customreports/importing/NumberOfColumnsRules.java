package gov.nwcg.isuite.core.rules.reports.customreports.importing;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class NumberOfColumnsRules extends AbstractCustomReportImportRule implements IRule {

	private static final String EM_ATLEAST_ONE_COLUMN = 
		"Atleast one column must exist in the report.";
	
	private static final String EM_MAX_COLUMNS_EXCEEDED = 
		"The report cannot have more than " + CustomReportData.MAX_NUMBER_OF_COLUMNS + " columns.";
	
	public NumberOfColumnsRules(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
			    return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		// At least one column must exist
		String errorString = null;
		
		if(CollectionUtility.hasValue(reportVo.getCustomReportColumnVos())) {
			if(reportVo.getCustomReportColumnVos().size()>CustomReportData.MAX_NUMBER_OF_COLUMNS) {
				errorString = EM_MAX_COLUMNS_EXCEEDED;
			}
		}else {
			errorString = EM_ATLEAST_ONE_COLUMN;
		}
		
		if(errorString != null) {
			CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coa.setMessageVo(new MessageVo("text.customReports"
					, "info.generic"
					, new String[]{errorString}
					, MessageTypeEnum.CRITICAL));
		    coa.setIsDialogueEnding(true);
		    
		    dialogueVo.setCourseOfActionVo(coa);
			return _FAIL;
		}
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub
	}

}
