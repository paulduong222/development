package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.rules.financial.delete.TimeInvoiceDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TimeInvoiceDeleteRulesHandler extends AbstractRuleHandler {
	
	public TimeInvoiceDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(TimeInvoice entity, DialogueVo dialogueVo) throws Exception {
		try{
			for(TimeInvoiceDeleteRuleFactory.RuleEnum ruleEnum : TimeInvoiceDeleteRuleFactory.RuleEnum.values()) {
				IRule rule = TimeInvoiceDeleteRuleFactory.getInstance(ruleEnum, entity, context, null);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.timeReports", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int executeProcessedActions(TimeInvoice entity, DialogueVo dialogueVo) throws ServiceException {
		try{
			for(TimeInvoiceDeleteRuleFactory.RuleEnum ruleEnum : TimeInvoiceDeleteRuleFactory.RuleEnum.values()) {
				IRule rule = TimeInvoiceDeleteRuleFactory.getInstance(ruleEnum, entity, context, null);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.timeReports", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

}
