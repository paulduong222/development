package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costgroups.delete.CostGroupsDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class CostGroupsDeleteRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(CostGroup entity, CostGroupVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long incId=0L;
			
			if(null != entity && LongUtility.hasValue(entity.getIncidentId())){
				incId=entity.getIncidentId();
			}else if(null != entity && null != entity.getIncident() && LongUtility.hasValue(entity.getIncident().getId())){
				incId=entity.getIncident().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incId, "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");

			for(CostGroupsDeleteRuleFactory.RuleEnum ruleEnum : CostGroupsDeleteRuleFactory.RuleEnum.values()){
				IRule rule = CostGroupsDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
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
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int executeProcessedActions(CostGroup entity, CostGroupVo vo, DialogueVo dialogueVo) throws Exception {

		try{
			
			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");

			for(CostGroupsDeleteRuleFactory.RuleEnum ruleEnum : CostGroupsDeleteRuleFactory.RuleEnum.values()){
				IRule rule = CostGroupsDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
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
