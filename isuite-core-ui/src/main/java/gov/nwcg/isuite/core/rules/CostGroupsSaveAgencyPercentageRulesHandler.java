package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.rules.costgroups.saveagencypercentage.CostGroupsSaveAgencyPercentageRuleFactory;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CostGroupsSaveAgencyPercentageRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsSaveAgencyPercentageRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(CostGroupAgencyDaySharePercentageVo vo
						,CostGroupAgencyDayShare dayShareEntity 
						,CostGroupAgencyDaySharePercentage daySharePctEntity, DialogueVo dialogueVo) throws Exception {
		try {
			
			for(CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum ruleEnum : CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveAgencyPercentageRuleFactory.getInstance(ruleEnum, context, vo, dayShareEntity, daySharePctEntity, dialogueVo);
				
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
	
	public int executeProcessedActions(CostGroupAgencyDaySharePercentageVo vo
										,CostGroupAgencyDayShare dayShareEntity 
										,CostGroupAgencyDaySharePercentage daySharePctEntity, DialogueVo dialogueVo) throws Exception {
		try{
			
			for(CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum ruleEnum : CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveAgencyPercentageRuleFactory.getInstance(ruleEnum, context, vo, dayShareEntity, daySharePctEntity, dialogueVo);
				
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
