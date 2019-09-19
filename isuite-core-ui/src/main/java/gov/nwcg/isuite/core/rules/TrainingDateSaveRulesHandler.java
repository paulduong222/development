package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.training.TrainingDateSaveRuleFactory;
import gov.nwcg.isuite.core.rules.user.saveuser.SaveUserRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class TrainingDateSaveRulesHandler extends AbstractRuleHandler {
	
	public TrainingDateSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Date newDate, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(TrainingDateSaveRuleFactory.RuleEnum ruleEnum : TrainingDateSaveRuleFactory.RuleEnum.values()){
				IRule rule = TrainingDateSaveRuleFactory.getInstance(ruleEnum, context, newDate);
				
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
			coaVo.setMessageVo(
					new MessageVo(
							"text.training", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(Date newDate, DialogueVo dialogueVo) throws Exception {
		
		try{

			for(TrainingDateSaveRuleFactory.RuleEnum ruleEnum : TrainingDateSaveRuleFactory.RuleEnum.values()){
				IRule rule = TrainingDateSaveRuleFactory.getInstance(ruleEnum, context, newDate);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	

}
