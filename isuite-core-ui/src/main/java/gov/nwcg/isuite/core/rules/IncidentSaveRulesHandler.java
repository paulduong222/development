package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.incident.saveincident.SaveIncidentRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
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

public class IncidentSaveRulesHandler extends AbstractRuleHandler {
	
	public IncidentSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Incident incidentEntity, IncidentVo vo, WorkAreaVo workAreaVo, DialogueVo dialogueVo
							,OrganizationVo originalPdc, OrganizationVo newPdc) throws Exception {
		
		try{

			if(LongUtility.hasValue(vo.getId())){
				for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
					IRule rule = SiteManagedRuleFactory.getInstance(re, context, vo.getId(), "INCIDENT");
					
					if(null != rule){
						if(_OK != rule.executeRules(dialogueVo)){
							return _FAIL;
						}
					}
				}
			}
			
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			
			for(SaveIncidentRuleFactory.RuleEnum ruleEnum : SaveIncidentRuleFactory.RuleEnum.values()){
				IRule rule = SaveIncidentRuleFactory.getInstance(ruleEnum, context, vo, workAreaVo, incidentEntity, dao,originalPdc, newPdc);
				
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

	public void executeProcessedActions(Incident incidentEntity, IncidentVo vo, WorkAreaVo workAreaVo,DialogueVo dialogueVo
			,OrganizationVo originalPdc, OrganizationVo newPdc) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(SaveIncidentRuleFactory.RuleEnum ruleEnum : SaveIncidentRuleFactory.RuleEnum.values()){
				IRule rule = SaveIncidentRuleFactory.getInstance(ruleEnum, context, vo, workAreaVo, incidentEntity, dao,originalPdc,newPdc);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
