package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.rules.iap.savebranchsetting.SaveBranchSettingRuleFactory;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IapSaveBranchSettingRulesHandler extends AbstractRuleHandler {

	public IapSaveBranchSettingRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	public int execute(BranchSettingVo vo, BranchSetting entity, DialogueVo dialogueVo) throws Exception {
		
		try {

			for(SaveBranchSettingRuleFactory.RuleEnum ruleEnum : SaveBranchSettingRuleFactory.RuleEnum.values()) {
				IRule rule = SaveBranchSettingRuleFactory.getInstance(ruleEnum, context, vo, entity);
				
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
	
	public void executeProcessedActions(BranchSettingVo vo, BranchSetting entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

