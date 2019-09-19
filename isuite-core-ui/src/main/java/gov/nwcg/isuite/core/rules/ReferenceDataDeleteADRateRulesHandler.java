package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.rules.referencedata.adrates.delete.ReferenceDataDeleteADRateRuleFactory;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ReferenceDataDeleteADRateRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataDeleteADRateRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(RateClassRateVo vo, RateClassRate entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			RateClassRateDao rateClassRateDao = (RateClassRateDao)context.getBean("rateClassRateDao");
			
			for(ReferenceDataDeleteADRateRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteADRateRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDeleteADRateRuleFactory.getInstance(ruleEnum, vo, entity, context, rateClassRateDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.adClass", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(RateClassRateVo vo, RateClassRate entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			RateClassRateDao rateClassRateDao = (RateClassRateDao)context.getBean("rateClassRateDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataDeleteADRateRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteADRateRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDeleteADRateRuleFactory.getInstance(ruleEnum, vo, entity, context, rateClassRateDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
