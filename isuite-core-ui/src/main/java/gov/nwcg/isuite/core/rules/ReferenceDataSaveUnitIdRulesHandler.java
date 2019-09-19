package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.referencedata.unitids.save.ReferenceDataSaveUnitIdRuleFactory;
import gov.nwcg.isuite.core.vo.OrganizationVo;
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

public class ReferenceDataSaveUnitIdRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSaveUnitIdRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(OrganizationVo organizationVo, Organization entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			String type="NONE";
			if(null != organizationVo && null != organizationVo.getIncidentVo() && LongUtility.hasValue(organizationVo.getIncidentVo().getId())){
				id=organizationVo.getIncidentVo().getId();
				type="INCIDENT";
			} else if(null != organizationVo && null != organizationVo.getIncidentGroupVo() && LongUtility.hasValue(organizationVo.getIncidentGroupVo().getId())){
				id=organizationVo.getIncidentGroupVo().getId();
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
			
			OrganizationDao organizationDao = (OrganizationDao)context.getBean("organizationDao");
			
			for(ReferenceDataSaveUnitIdRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveUnitIdRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveUnitIdRuleFactory.getInstance(
						ruleEnum, context, organizationVo, entity, organizationDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.incident", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(OrganizationVo organizationVo, Organization entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			OrganizationDao organizationDao = (OrganizationDao)context.getBean("organizationDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSaveUnitIdRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveUnitIdRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveUnitIdRuleFactory.getInstance(
						ruleEnum, context, organizationVo, entity, organizationDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
