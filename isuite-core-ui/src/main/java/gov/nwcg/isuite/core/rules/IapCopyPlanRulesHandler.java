package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.iap.copyplan.CopyPlanRuleFactory;
import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapCopyPlanRulesHandler extends AbstractRuleHandler {
	
	public IapCopyPlanRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(DialogueVo dialogueVo, IapCopyVo iapCopyVo) throws ServiceException {
		
		try{
			
			for(CopyPlanRuleFactory.RuleEnum ruleEnum : CopyPlanRuleFactory.RuleEnum.values()){
				IRule rule = CopyPlanRuleFactory. getInstance(ruleEnum, context, iapCopyVo);
				
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
			coaVo.setMessageVo(new MessageVo("text.iap", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
		
		
	}
	
	public void executeProcessedActions(DialogueVo dialogueVo, IapCopyVo copyVo) throws ServiceException {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}		

}
