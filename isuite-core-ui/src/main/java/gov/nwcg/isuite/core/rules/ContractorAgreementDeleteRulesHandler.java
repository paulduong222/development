package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractor.deleteagreement.ContractorAgreementDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ContractorAgreementDeleteRulesHandler extends AbstractRuleHandler {
	
	public ContractorAgreementDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(ContractorAgreement entity, DialogueVo dialogueVo) throws Exception {
		try{

			Long id=0L;
			
			if(null != entity && LongUtility.hasValue(entity.getContractorId())){
				id=entity.getContractorId();
			}else if(null != entity && null != entity.getContractor() && LongUtility.hasValue(entity.getContractor().getId())){
				id=entity.getContractor().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, "CONTRACTOR");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			ContractorAgreementDao caDao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");

			for(ContractorAgreementDeleteRuleFactory.RuleEnum ruleEnum : ContractorAgreementDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorAgreementDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, caDao);
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
			coaVo.setMessageVo(new MessageVo("text.contractorAgreement", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public int executeProcessedActions(ContractorAgreement entity, DialogueVo dialogueVo) throws Exception {

		try{
			
			ContractorAgreementDao caDao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");

			for(ContractorAgreementDeleteRuleFactory.RuleEnum ruleEnum : ContractorAgreementDeleteRuleFactory.RuleEnum.values()){
				
				IRule rule = ContractorAgreementDeleteRuleFactory.getInstance(ruleEnum
																		, context
																		, entity
																		, caDao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contractorAgreement", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}


}
