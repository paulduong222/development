package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.persistence.ContractorRateDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractorrate.delete.ContractorRateDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ContractorRateDeleteRulesHandler extends AbstractRuleHandler {
	
	public ContractorRateDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	/**
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(ContractorRate entity, Long assignmentTimeId,DialogueVo dialogueVo) throws Exception {
		
		try{

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, assignmentTimeId, "ASSIGNMENTTIME");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			ContractorRateDao crDao = (ContractorRateDao)context.getBean("contractorRateDao");

			for(ContractorRateDeleteRuleFactory.RuleEnum ruleEnum : ContractorRateDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorRateDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, crDao);
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
			coaVo.setMessageVo(new MessageVo("text.contractorRate", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public int executeProcessedActions(ContractorRate entity, DialogueVo dialogueVo) throws Exception {

		try{
			
			ContractorRateDao crDao = (ContractorRateDao)context.getBean("contractorRateDao");

			for(ContractorRateDeleteRuleFactory.RuleEnum ruleEnum : ContractorRateDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorRateDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, crDao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contractorRate", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}


}
