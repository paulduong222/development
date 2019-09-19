package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractor.saveagreement.ContractorSaveAgreementRuleFactory;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ContractorSaveAgreementRulesHandler extends AbstractRuleHandler {
	
	public ContractorSaveAgreementRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(ContractorAgreementVo vo, ContractorAgreement entity, ContractorAgreementVo originalVo, DialogueVo dialogueVo) throws Exception {
		
		try{

			Long id=0L;
			
			if(null != entity && LongUtility.hasValue(entity.getContractorId())){
				id=entity.getContractorId();
			}else if(null != entity && null != entity.getContractor() && LongUtility.hasValue(entity.getContractor().getId())){
				id=entity.getContractor().getId();
			}else if(null != vo && null != vo.getContractorVo() && LongUtility.hasValue(vo.getContractorVo().getId())){
				id=vo.getContractorVo().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, "CONTRACTOR");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			ContractorAgreementDao dao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");
			
			for(ContractorSaveAgreementRuleFactory.RuleEnum ruleEnum : ContractorSaveAgreementRuleFactory.RuleEnum.values()){
				IRule rule = ContractorSaveAgreementRuleFactory.getInstance(ruleEnum, context, vo, entity, dao, originalVo);
				
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
			coaVo.setMessageVo(new MessageVo("text.adminOffice", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param vo
	 * @param entity
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(ContractorAgreementVo vo, ContractorAgreement entity, ContractorAgreementVo originalVo, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			ContractorAgreementDao dao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");
			
			for(ContractorSaveAgreementRuleFactory.RuleEnum ruleEnum : ContractorSaveAgreementRuleFactory.RuleEnum.values()){
				IRule rule = ContractorSaveAgreementRuleFactory.getInstance(ruleEnum, context, vo, entity, dao, originalVo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
