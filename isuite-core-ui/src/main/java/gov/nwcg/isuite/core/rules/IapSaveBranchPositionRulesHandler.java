package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.iap.savebranchposition.SaveBranchPositionRuleFactory;
import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IapSaveBranchPositionRulesHandler extends AbstractRuleHandler {

	public IapSaveBranchPositionRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	public int execute(BranchPositionVo vo, Long branchSettingId,DialogueVo dialogueVo) throws Exception {
		
		try {

			for(SaveBranchPositionRuleFactory.RuleEnum ruleEnum : SaveBranchPositionRuleFactory.RuleEnum.values()) {
				IRule rule = SaveBranchPositionRuleFactory.getInstance(ruleEnum, context, vo,branchSettingId);
				
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
	
	public void executeProcessedActions(BranchPositionVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

