package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.rules.iap.savebranchcomm.SaveBranchCommRuleFactory;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IapSaveBranchCommRulesHandler extends AbstractRuleHandler {

	public IapSaveBranchCommRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	public int execute(IapBranchCommSummaryVo vo, IapBranchCommSummary entity, DialogueVo dialogueVo) throws Exception {
		
		try {

			for(SaveBranchCommRuleFactory.RuleEnum ruleEnum : SaveBranchCommRuleFactory.RuleEnum.values()) {
				IRule rule = SaveBranchCommRuleFactory.getInstance(ruleEnum, context, vo, entity);
				
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
	
	public void executeProcessedActions(IapBranchCommSummaryVo vo, IapBranchCommSummary entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

