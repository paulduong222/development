package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.costgroups.deleteallocationpercent.CostGroupsDeleteAllocationPctRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CostGroupsDeleteAllocationPctRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsDeleteAllocationPctRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	/**
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(CostGroupAgencyDaySharePercentage entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");

			for(CostGroupsDeleteAllocationPctRuleFactory.RuleEnum ruleEnum : CostGroupsDeleteAllocationPctRuleFactory.RuleEnum.values()){
				IRule rule = CostGroupsDeleteAllocationPctRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, cgDao);
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
			coaVo.setMessageVo(new MessageVo("text.costGroups", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/**
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int executeProcessedActions(CostGroupAgencyDaySharePercentage entity, DialogueVo dialogueVo) throws Exception {
		try{
			
			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");

			for(CostGroupsDeleteAllocationPctRuleFactory.RuleEnum ruleEnum : CostGroupsDeleteAllocationPctRuleFactory.RuleEnum.values()){
				IRule rule = CostGroupsDeleteAllocationPctRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, cgDao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costGroups", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

}
