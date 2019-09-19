package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractor.save.ContractorSaveRuleFactory;
import gov.nwcg.isuite.core.vo.ContractorVo;
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

public class ContractorSaveRulesHandler extends AbstractRuleHandler {
	
	public ContractorSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(ContractorVo vo, ContractorVo originalVo, Contractor entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long incId=0L;
			
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				incId=vo.getIncidentVo().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incId, "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			ContractorDao dao = (ContractorDao)context.getBean("contractorDao");
			
			for(ContractorSaveRuleFactory.RuleEnum ruleEnum : ContractorSaveRuleFactory.RuleEnum.values()){
				IRule rule = ContractorSaveRuleFactory.getInstance(ruleEnum, context, vo, originalVo, entity,dao);
				
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
	public void executeProcessedActions(ContractorVo vo, ContractorVo originalVo, Contractor entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			ContractorDao dao = (ContractorDao)context.getBean("contractorDao");
			
			for(ContractorSaveRuleFactory.RuleEnum ruleEnum : ContractorSaveRuleFactory.RuleEnum.values()){
				IRule rule = ContractorSaveRuleFactory.getInstance(ruleEnum, context, vo, originalVo, entity,dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
