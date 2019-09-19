package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.trainingspecialist.savehomeunitcontact.HomeUnitContactSaveRuleFactory;
import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TnspSaveResourceHomeUnitContactRulesHandler extends AbstractRuleHandler {
	
	public TnspSaveResourceHomeUnitContactRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(ResourceHomeUnitContactVo homeUnitContactVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			//ResourceHomeUnitContactDao homeUnitContactDao = (ResourceHomeUnitContactDao)context.getBean("homeUnitContactDao");
			
			for(HomeUnitContactSaveRuleFactory.RuleEnum ruleEnum : HomeUnitContactSaveRuleFactory.RuleEnum.values()) {
				IRule rule = HomeUnitContactSaveRuleFactory.getInstance(ruleEnum, context, homeUnitContactVo);
				
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
			coaVo.setMessageVo(new MessageVo("text.homeUnitContact", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(ResourceHomeUnitContactVo homeUnitContactVo, DialogueVo dialogueVo) throws Exception {
	
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			//ResourceHomeUnitContactDao homeUnitContactDao = (ResourceHomeUnitContactDao)context.getBean("homeUnitContactDao");
			
			for(HomeUnitContactSaveRuleFactory.RuleEnum ruleEnum : HomeUnitContactSaveRuleFactory.RuleEnum.values()) {
				IRule rule = HomeUnitContactSaveRuleFactory.getInstance(ruleEnum, context, homeUnitContactVo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
