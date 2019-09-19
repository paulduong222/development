package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractor.delete.ContractorDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ContractorDeleteRulesHandler extends AbstractRuleHandler {
	
	public ContractorDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(Contractor entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long incId=0L;
			
			if(null != entity && CollectionUtility.hasValue(entity.getIncidents())){
				Incident inc = (Incident)entity.getIncidents().iterator().next();
				if(null != inc && LongUtility.hasValue(inc.getId()))
					incId=inc.getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incId, "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			ContractorDao cDao = (ContractorDao)context.getBean("contractorDao");

			for(ContractorDeleteRuleFactory.RuleEnum ruleEnum : ContractorDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, cDao);
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
			coaVo.setMessageVo(new MessageVo("text.contractor", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public int executeProcessedActions(Contractor entity, DialogueVo dialogueVo) throws Exception {

		try{
			
			ContractorDao cDao = (ContractorDao)context.getBean("contractorDao");

			for(ContractorDeleteRuleFactory.RuleEnum ruleEnum : ContractorDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, cDao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contractor", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

}
