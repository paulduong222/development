package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.resourceinventory.save.ResourceInventorySaveRuleFactory;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ResourceInventorySaveRulesHandler extends AbstractRuleHandler {
	
	public ResourceInventorySaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(ResourceVo resourceVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(ResourceInventorySaveRuleFactory.RuleEnum ruleEnum : ResourceInventorySaveRuleFactory.RuleEnum.values()){
				IRule rule = ResourceInventorySaveRuleFactory.getInstance(ruleEnum, context, resourceVo);
				
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
							"Resource Inventory", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(ResourceVo resourceVo, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ResourceInventorySaveRuleFactory.RuleEnum ruleEnum : ResourceInventorySaveRuleFactory.RuleEnum.values()){
				IRule rule = ResourceInventorySaveRuleFactory.getInstance(ruleEnum, context, resourceVo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
