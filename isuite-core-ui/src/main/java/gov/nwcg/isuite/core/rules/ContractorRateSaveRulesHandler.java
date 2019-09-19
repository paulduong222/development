package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.contractorrate.save.ContractorRateSaveRuleFactory;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
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

public class ContractorRateSaveRulesHandler extends AbstractRuleHandler {
	
	public ContractorRateSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(ContractorRateVo vo, Long assignmentTimeId, DialogueVo dialogueVo) throws Exception {

		for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
			IRule rule = SiteManagedRuleFactory.getInstance(re, context, assignmentTimeId, "ASSIGNMENTTIME");
			
			if(null != rule){
				if(_OK != rule.executeRules(dialogueVo)){
					return _FAIL;
				}
			}
		}
		
		try{

			for(ContractorRateSaveRuleFactory.RuleEnum ruleEnum : ContractorRateSaveRuleFactory.RuleEnum.values()){
				IRule rule = ContractorRateSaveRuleFactory.getInstance(ruleEnum, context, vo);
				
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
	 * @param vo
	 * @param entity
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(ContractorRateVo vo, Long assignmentTimeId,DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			
			for(ContractorRateSaveRuleFactory.RuleEnum ruleEnum : ContractorRateSaveRuleFactory.RuleEnum.values()){
				IRule rule = ContractorRateSaveRuleFactory.getInstance(ruleEnum, context, vo);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
