package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.rules.referencedata.categorygroups.delete.ReferenceDataDeleteCategoryGroupRuleFactory;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ReferenceDataDeleteGroupCategoryRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataDeleteGroupCategoryRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(GroupCategoryVo vo, GroupCategory entity, DialogueVo dialogueVo) throws Exception {
	
		try {
			GroupCategoryDao groupCategoryDao = (GroupCategoryDao)context.getBean("groupCategoryDao");
			
			for(ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDeleteCategoryGroupRuleFactory.getInstance(ruleEnum, vo, entity, context, groupCategoryDao);
				
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
	
	public void executeProcessedActions(GroupCategoryVo vo, GroupCategory entity, DialogueVo dialogueVo) throws Exception {
		
		try {
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			GroupCategoryDao groupCategoryDao = (GroupCategoryDao)context.getBean("groupCategoryDao");
			
			for(ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum.values()) {
				IRule rule = ReferenceDataDeleteCategoryGroupRuleFactory.getInstance(ruleEnum, vo, entity, context, groupCategoryDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
