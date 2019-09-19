package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.iap.deleteposition.PositionDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapPositionDeleteRulesHandler extends AbstractRuleHandler {
	
	public IapPositionDeleteRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(DialogueVo dialogueVo) throws Exception {
		
		try {
			
			for(PositionDeleteRuleFactory.RuleEnum ruleEnum : PositionDeleteRuleFactory.RuleEnum.values()) {
				IRule rule =PositionDeleteRuleFactory.getInstance(ruleEnum, context);
			
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.position", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
		
	}
	
	/**
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			for(PositionDeleteRuleFactory.RuleEnum ruleEnum : PositionDeleteRuleFactory.RuleEnum.values()) {
				IRule rule =PositionDeleteRuleFactory.getInstance(ruleEnum, context);
			
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}	
		
	}

}
