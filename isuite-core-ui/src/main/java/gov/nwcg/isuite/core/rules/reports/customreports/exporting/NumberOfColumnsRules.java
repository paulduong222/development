package gov.nwcg.isuite.core.rules.reports.customreports.exporting;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class NumberOfColumnsRules extends AbstractCustomReportExportRule implements IRule {
	
	private static final String EM_AT_LEAST_ONE_COLUMN = 
		"At least one column must exist in the report.";
	
	public NumberOfColumnsRules(ApplicationContext ctx, String rname){
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		if(!CollectionUtility.hasValue(reportVo.getCustomReportColumnVos())) {
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.customReports"
					, "info.generic"
					, new String[]{EM_AT_LEAST_ONE_COLUMN}
					, MessageTypeEnum.CRITICAL));
			
		    coaVo.setIsDialogueEnding(Boolean.TRUE);
		    
		    dialogueVo.setCourseOfActionVo(coaVo);
		    
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
