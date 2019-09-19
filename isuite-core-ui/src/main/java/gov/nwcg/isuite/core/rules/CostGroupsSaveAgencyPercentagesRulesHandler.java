package gov.nwcg.isuite.core.rules;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.costgroups.saveagencypercentages.CostGroupsSaveAgencyPercentagesRuleFactory;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CostGroupsSaveAgencyPercentagesRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsSaveAgencyPercentagesRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(Collection<CostGroupAgencyDaySharePercentageVo> vos, DialogueVo dialogueVo) throws Exception {
		try {
			
			for(CostGroupsSaveAgencyPercentagesRuleFactory.RuleEnum ruleEnum : CostGroupsSaveAgencyPercentagesRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveAgencyPercentagesRuleFactory.getInstance(ruleEnum, context, vos);
				
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
	
	public int executeProcessedActions(Collection<CostGroupAgencyDaySharePercentageVo> vos, CostGroupVo vo, DialogueVo dialogueVo) throws ServiceException {
		try{
			
			for(CostGroupsSaveAgencyPercentagesRuleFactory.RuleEnum ruleEnum : CostGroupsSaveAgencyPercentagesRuleFactory.RuleEnum.values()) {
				IRule rule = CostGroupsSaveAgencyPercentagesRuleFactory.getInstance(ruleEnum, context, vos);
				
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
