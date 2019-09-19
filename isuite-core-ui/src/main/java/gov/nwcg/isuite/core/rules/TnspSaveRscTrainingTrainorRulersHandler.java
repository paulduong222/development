package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.trainingspecialist.savetrainingtrainer.TrainingTrainerSaveRuleFactory;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TnspSaveRscTrainingTrainorRulersHandler extends AbstractRuleHandler {
	
	public TnspSaveRscTrainingTrainorRulersHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws Exception {
		
		try {
			for(TrainingTrainerSaveRuleFactory.RuleEnum ruleEnum : TrainingTrainerSaveRuleFactory.RuleEnum.values()) {
				IRule rule = TrainingTrainerSaveRuleFactory.getInstance(ruleEnum, context, vo);
				
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
			coaVo.setMessageVo(new MessageVo("text.traineeAssignment", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			for(TrainingTrainerSaveRuleFactory.RuleEnum ruleEnum : TrainingTrainerSaveRuleFactory.RuleEnum.values()) {
				IRule rule = TrainingTrainerSaveRuleFactory.getInstance(ruleEnum, context, vo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

}
