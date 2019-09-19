package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.referencedata.agencycodes.delete.ReferenceDataDeleteAgencyCodeRuleFactory;
import gov.nwcg.isuite.core.vo.AgencyVo;
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

public class ReferenceDataDeleteAgencyCodeRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataDeleteAgencyCodeRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(AgencyVo agencyVo, Agency agencyEntity,DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			String type="NONE";
			if(null != agencyVo && null != agencyVo.getIncidentVo() && LongUtility.hasValue(agencyVo.getIncidentVo().getId())){
				id=agencyVo.getIncidentVo().getId();
				type="INCIDENT";
			} else if(null != agencyVo && null != agencyVo.getIncidentGroupVo() && LongUtility.hasValue(agencyVo.getIncidentGroupVo().getId())){
				id=agencyVo.getIncidentGroupVo().getId();
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
			
			AgencyDao agencyDao = (AgencyDao)context.getBean("agencyDao");
			
			for(ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDeleteAgencyCodeRuleFactory.getInstance(
						ruleEnum, context, agencyVo, agencyEntity, agencyDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.agency", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(AgencyVo agencyVo, Agency agencyEntity,DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			AgencyDao agencyDao = (AgencyDao)context.getBean("agencyDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum ruleEnum : ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataDeleteAgencyCodeRuleFactory.getInstance(
						ruleEnum, context, agencyVo, agencyEntity, agencyDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
