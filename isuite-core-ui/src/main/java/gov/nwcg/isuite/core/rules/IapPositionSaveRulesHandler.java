package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.iap.saveposition.PositionSaveRuleFactory;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapPositionSaveRulesHandler extends AbstractRuleHandler {
	
	public IapPositionSaveRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	/**
	 * @param iapPositionVo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IapPositionVo iapPositionVo, DialogueVo dialogueVo) throws Exception {
		
		try {
//			IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
			
			for(PositionSaveRuleFactory.RuleEnum ruleEnum : PositionSaveRuleFactory.RuleEnum.values()) {
				IRule rule = PositionSaveRuleFactory.getInstance(
						ruleEnum, context, iapPositionVo);
				
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
	 * @param iapPositionVo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IapPositionVo iapPositionVo, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
//			IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(PositionSaveRuleFactory.RuleEnum ruleEnum : PositionSaveRuleFactory.RuleEnum.values()) {
				IRule rule = PositionSaveRuleFactory.getInstance(
						ruleEnum, context, iapPositionVo);
				
				if(null != rule) {
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
