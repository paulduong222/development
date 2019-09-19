package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.iap.saveplan.SavePlanRuleFactory;
import gov.nwcg.isuite.core.vo.IapPlanVo;
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

public class IapSavePlanRulesHandler extends AbstractRuleHandler {

	public IapSavePlanRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	public int execute(IapPlanVo iapPlanVo, IapPlan entity, DialogueVo dialogueVo) throws Exception {
		
		try {
			Long id=0L;
			String type="";
			
			if(LongUtility.hasValue(iapPlanVo.getIncidentId())){
				id=iapPlanVo.getIncidentId();
				type="INCIDENT";
			}
			if(LongUtility.hasValue(iapPlanVo.getIncidentGroupId())){
				id=iapPlanVo.getIncidentGroupId();
				type="INCIDENTGROUP";
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			for(SavePlanRuleFactory.RuleEnum ruleEnum : SavePlanRuleFactory.RuleEnum.values()) {
				IRule rule = SavePlanRuleFactory.getInstance(ruleEnum, context, iapPlanVo, entity);
				
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
			coaVo.setMessageVo(new MessageVo("text.iap", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(IapPlanVo iapVo, IapPlan entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

