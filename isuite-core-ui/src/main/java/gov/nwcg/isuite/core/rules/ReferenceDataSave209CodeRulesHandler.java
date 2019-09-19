package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.rules.referencedata._209codes.save.ReferenceDataSave209CodeRuleFactory;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSave209CodeRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSave209CodeRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Sit209Vo vo, Sit209 entity,DialogueVo dialogueVo) throws Exception {
		
		try{
			
			Sit209Dao dao = (Sit209Dao)context.getBean("sit209Dao");
			
			for(ReferenceDataSave209CodeRuleFactory.RuleEnum ruleEnum : ReferenceDataSave209CodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSave209CodeRuleFactory.getInstance(
						ruleEnum, context, vo, entity, dao);
				
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

	public void executeProcessedActions( Sit209Vo vo, Sit209 entity,DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			Sit209Dao dao = (Sit209Dao)context.getBean("sit209Dao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSave209CodeRuleFactory.RuleEnum ruleEnum : ReferenceDataSave209CodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSave209CodeRuleFactory.getInstance(
						ruleEnum, context, vo, entity, dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
