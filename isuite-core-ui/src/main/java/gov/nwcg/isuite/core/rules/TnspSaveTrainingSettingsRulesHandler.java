package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.trainingspecialist.settings.savetrainingsettings.TrainingSettingsSaveRuleFactory;
import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TnspSaveTrainingSettingsRulesHandler extends AbstractRuleHandler {
	
	public TnspSaveTrainingSettingsRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(TrainingSettingsVo trainingSettingsVo, DialogueVo dialogueVo) throws Exception {
		try{
//			TrainingSettingsDao trainingSettingsDao = (TrainingSettingsDao)context.getBean("trainingSettingsDao");
			
			for(TrainingSettingsSaveRuleFactory.RuleEnum ruleEnum : TrainingSettingsSaveRuleFactory.RuleEnum.values()){
				IRule rule = TrainingSettingsSaveRuleFactory.getInstance(ruleEnum, context, trainingSettingsVo);
				
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
			coaVo.setMessageVo(new MessageVo("text.trainingSettings", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(TrainingSettingsVo trainingSettingsVo, DialogueVo dialogueVo) throws Exception {
		try {
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
//			TrainingSettingsDao trainingSettingsDao = (TrainingSettingsDao)context.getBean("trainingSettingsDao");
			
			for(TrainingSettingsSaveRuleFactory.RuleEnum ruleEnum : TrainingSettingsSaveRuleFactory.RuleEnum.values()){
				IRule rule = TrainingSettingsSaveRuleFactory.getInstance(ruleEnum, context, trainingSettingsVo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
