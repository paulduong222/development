package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costgroups.save.CostGroupsSaveRuleFactory;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CostGroupsSaveRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(CostGroup entity, CostGroupVo vo, DialogueVo dialogueVo) throws Exception {
		try {

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, vo.getIncidentVo().getId(), "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");
			
			for(CostGroupsSaveRuleFactory.RuleEnum ruleEnum : CostGroupsSaveRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveRuleFactory.getInstance(ruleEnum, context, vo, entity, cgDao);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costGroups", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int executeProcessedActions(CostGroup entity, CostGroupVo vo, DialogueVo dialogueVo) throws ServiceException {
		try{
			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");
			
			for(CostGroupsSaveRuleFactory.RuleEnum ruleEnum : CostGroupsSaveRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveRuleFactory.getInstance(ruleEnum, context, vo, entity, cgDao);
				
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
