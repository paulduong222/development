package gov.nwcg.isuite.core.rules;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costprojections.createprojections.RunCreateProjectionRuleFactory;

import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CostProjectionsCreateProjectionRulesHandler extends AbstractRuleHandler {
	
	public CostProjectionsCreateProjectionRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(ProjectionVo vo,CostProjectionDao dao, DialogueVo dialogueVo, Incident incident, IncidentGroup incidentGroup, String today) throws Exception {
		
		try {
			Long id=0L;
			String type="";
			if(null != incident){
				id=incident.getId();
				type="INCIDENT";
			}else if(null != incidentGroup){
				id=incidentGroup.getId();
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
			
			for(RunCreateProjectionRuleFactory.RuleEnum ruleEnum : RunCreateProjectionRuleFactory.RuleEnum.values()) {
				IRule rule = RunCreateProjectionRuleFactory.getInstance(ruleEnum, context, dao, vo, incident, incidentGroup, today);
				
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
			coaVo.setMessageVo(new MessageVo("text.costAccruals", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int executeProcessedActions() throws Exception{
		return _OK;
	}

}
