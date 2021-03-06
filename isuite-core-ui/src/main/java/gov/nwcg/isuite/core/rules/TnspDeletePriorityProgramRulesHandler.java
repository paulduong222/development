package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.rules.trainingspecialist.settings.deletepriorityprogram.PriorityProgramDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TnspDeletePriorityProgramRulesHandler extends AbstractRuleHandler {
	
	public TnspDeletePriorityProgramRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(PriorityProgramVo vo, PriorityProgram entity, DialogueVo dialogueVo) throws Exception {
		
		try {
			PriorityProgramDao priorityProgramDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
			
			for(PriorityProgramDeleteRuleFactory.RuleEnum ruleEnum : PriorityProgramDeleteRuleFactory.RuleEnum.values()){
				IRule rule = PriorityProgramDeleteRuleFactory.getInstance(ruleEnum, context, vo, entity, priorityProgramDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.error", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(PriorityProgramVo vo, PriorityProgram entity, DialogueVo dialogueVo) throws Exception {
		
		try {
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			PriorityProgramDao priorityProgramDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
			
			for(PriorityProgramDeleteRuleFactory.RuleEnum ruleEnum : PriorityProgramDeleteRuleFactory.RuleEnum.values()){
				IRule rule = PriorityProgramDeleteRuleFactory.getInstance(ruleEnum, context, vo, entity, priorityProgramDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

}
