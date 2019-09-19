package gov.nwcg.isuite.core.rules.reports.customreports.exporting;

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

public class MaxNumberOfColumnRules extends AbstractCustomReportExportRule
		implements IRule {
	
	private static final String MAX_COLUMNS_EXCEEDED = 
		"The report cannot have more than " + CustomReportData.MAX_NUMBER_OF_COLUMNS + " columns.";

	public MaxNumberOfColumnRules(ApplicationContext ctx, String rname) {
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
		
		if(CollectionUtility.hasValue(reportVo.getCustomReportColumnVos())) {
			if(reportVo.getCustomReportColumnVos().size()>CustomReportData.MAX_NUMBER_OF_COLUMNS) {
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.customReports"
						, "info.generic"
						, new String[]{MAX_COLUMNS_EXCEEDED}
						, MessageTypeEnum.CRITICAL));
				
			    coaVo.setIsDialogueEnding(Boolean.TRUE);
			    
			    dialogueVo.setCourseOfActionVo(coaVo);
			    
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
