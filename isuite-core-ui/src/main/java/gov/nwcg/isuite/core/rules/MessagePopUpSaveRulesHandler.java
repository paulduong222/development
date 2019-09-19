package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.mbadmin.savepopup.SaveMessagePopUpRuleFactory;
import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class MessagePopUpSaveRulesHandler extends AbstractRuleHandler {
	
	public MessagePopUpSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(MessageBoardVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{
			for(SaveMessagePopUpRuleFactory.RuleEnum ruleEnum : SaveMessagePopUpRuleFactory.RuleEnum.values()){
				IRule rule = SaveMessagePopUpRuleFactory.getInstance(ruleEnum, vo, context);
				
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
			coaVo.setMessageVo(new MessageVo("text.messageBoard", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

}
