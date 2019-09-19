package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.rules.resourceinventory.delete.ResourceInventoryDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ResourceInventoryDeleteRulesHandler extends AbstractRuleHandler {
	
	public ResourceInventoryDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(Resource resourceEntity, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(ResourceInventoryDeleteRuleFactory.RuleEnum ruleEnum : ResourceInventoryDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ResourceInventoryDeleteRuleFactory.getInstance(ruleEnum, context, resourceEntity);
			
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
			coaVo.setMessageVo(new MessageVo("Resource Inventory", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(Resource resourceEntity, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ResourceInventoryDeleteRuleFactory.RuleEnum ruleEnum : ResourceInventoryDeleteRuleFactory.RuleEnum.values()){
				IRule rule = ResourceInventoryDeleteRuleFactory.getInstance(ruleEnum, context, resourceEntity);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
