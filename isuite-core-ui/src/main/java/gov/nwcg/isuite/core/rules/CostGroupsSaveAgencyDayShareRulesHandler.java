package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costgroups.saveagencyshare.SaveAgencyShareRuleFactory;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CostGroupsSaveAgencyDayShareRulesHandler extends AbstractRuleHandler {
	
	public CostGroupsSaveAgencyDayShareRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(CostGroupAgencyDayShareVo vo
						,CostGroupAgencyDayShare dayShareEntity 
						,DialogueVo dialogueVo) throws Exception {
		try {
			
			Long costGroupId=0L;
			
			if(null != dayShareEntity && LongUtility.hasValue(dayShareEntity.getCostGroupId())){
				costGroupId=dayShareEntity.getCostGroupId();
			}else if (null != dayShareEntity && null != dayShareEntity.getCostGroup() && LongUtility.hasValue(dayShareEntity.getCostGroup().getId())){
				costGroupId=dayShareEntity.getCostGroup().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, costGroupId, "COSTGROUP");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			CostGroupDao dao = (CostGroupDao)context.getBean("costGroupDao");
			
			for(SaveAgencyShareRuleFactory.RuleEnum ruleEnum : SaveAgencyShareRuleFactory.RuleEnum.values()) {
				IRule rule = SaveAgencyShareRuleFactory.getInstance(ruleEnum, context, vo, dayShareEntity, dao,dialogueVo);
				
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
	
	public int executeProcessedActions(CostGroupAgencyDayShareVo vo
										,CostGroupAgencyDayShare dayShareEntity 
										,DialogueVo dialogueVo) throws Exception {
		try{
			
			CostGroupDao dao = (CostGroupDao)context.getBean("costGroupDao");
			
			for(SaveAgencyShareRuleFactory.RuleEnum ruleEnum : SaveAgencyShareRuleFactory.RuleEnum.values()) {
				IRule rule = SaveAgencyShareRuleFactory.getInstance(ruleEnum, context, vo, dayShareEntity, dao,dialogueVo);
				
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
