package gov.nwcg.isuite.core.rules.financial.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckInvoiceIncludedInFinancialExportRules extends AbstractTimeInvoiceDeleteRule implements IRule {

	public static final String _RULE_NAME=TimeInvoiceDeleteRuleFactory.RuleEnum.CHECK_IF_INCLUDED_IN_FINANCIAL_EXPORT.name();
	
	public CheckInvoiceIncludedInFinancialExportRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	
	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;


			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;


			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}
	
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(null != timeInvoice) {
			if(null != timeInvoice.getExportDate()) {
				MessageVo messageVo = new MessageVo();
				messageVo.setMessageProperty("info.generic");
				messageVo.setTitleProperty("text.timeReports");
				messageVo.setMessageType(MessageTypeEnum.CRITICAL);
			    messageVo.setParameters(new String[]{"A user cannot delete an invoice that was included in a Financial Export."});
				
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(ruleName);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(messageVo);
				courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}


}
