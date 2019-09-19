package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.incidentresource.delete.IncidentResourceDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentResourceDeleteRulesHandler extends AbstractRuleHandler {
	
	public IncidentResourceDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Collection<Long> incidentResourceIds, IncidentResourceGridVo gridVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long irid=0L;
			if(null != gridVo && LongUtility.hasValue(gridVo.getIncidentResourceId())){
				irid = gridVo.getIncidentResourceId();
			}else if(CollectionUtility.hasValue(incidentResourceIds)){
				irid = incidentResourceIds.iterator().next();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, irid, "INCIDENTRESOURCE");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			for(IncidentResourceDeleteRuleFactory.RuleEnum ruleEnum : IncidentResourceDeleteRuleFactory.RuleEnum.values()){
				IRule rule = IncidentResourceDeleteRuleFactory.getInstance(ruleEnum, context, incidentResourceIds, gridVo);
				
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
			coaVo.setMessageVo(new MessageVo("text.incidentResources", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(DialogueVo dialogueVo) throws ServiceException {
		
	}
	
	
}
