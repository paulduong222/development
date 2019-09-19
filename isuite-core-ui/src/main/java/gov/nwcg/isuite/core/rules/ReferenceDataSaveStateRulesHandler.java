package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.referencedata.states.save.ReferenceDataSaveStateRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveStateRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSaveStateRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			//TODO:  get dao
			
			for(ReferenceDataSaveStateRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveStateRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveStateRuleFactory.getInstance(ruleEnum, context);
				
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
			coaVo.setMessageVo(new MessageVo("text.incident", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			//TODO:  get dao
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSaveStateRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveStateRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveStateRuleFactory.getInstance(ruleEnum, context);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
