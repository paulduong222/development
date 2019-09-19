package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.rules.referencedata._209codes.delete.ReferenceDataDelete209CodeRuleFactory;
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

public class ReferenceDataDelete209CodeRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataDelete209CodeRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Sit209Vo vo, Sit209 entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			Sit209Dao sit209Dao = (Sit209Dao)context.getBean("sit209Dao");
			
			for(ReferenceDataDelete209CodeRuleFactory.RuleEnum ruleEnum : ReferenceDataDelete209CodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDelete209CodeRuleFactory.getInstance(ruleEnum, vo, entity, context, sit209Dao);
				
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
			coaVo.setMessageVo(new MessageVo("text.sit209Codes", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(Sit209Vo vo, Sit209 entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			Sit209Dao sit209Dao = (Sit209Dao)context.getBean("sit209Dao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataDelete209CodeRuleFactory.RuleEnum ruleEnum : ReferenceDataDelete209CodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDelete209CodeRuleFactory.getInstance(ruleEnum, vo, entity, context, sit209Dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
