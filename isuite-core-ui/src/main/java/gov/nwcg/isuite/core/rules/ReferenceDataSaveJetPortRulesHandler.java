package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.referencedata.jetports.save.ReferenceDataSaveJetPortRuleFactory;
import gov.nwcg.isuite.core.vo.JetPortVo;
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

public class ReferenceDataSaveJetPortRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSaveJetPortRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(JetPortVo jetPortVo, JetPort entity,DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			String type="NONE";
			if(null != jetPortVo && null != jetPortVo.getIncidentVo() && LongUtility.hasValue(jetPortVo.getIncidentVo().getId())){
				id=jetPortVo.getIncidentVo().getId();
				type="INCIDENT";
			} else if(null != jetPortVo && null != jetPortVo.getIncidentGroupVo() && LongUtility.hasValue(jetPortVo.getIncidentGroupVo().getId())){
				id=jetPortVo.getIncidentGroupVo().getId();
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
			
			JetPortDao jetPortDao = (JetPortDao)context.getBean("jetPortDao");
			
			for(ReferenceDataSaveJetPortRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveJetPortRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveJetPortRuleFactory.getInstance(
						ruleEnum, context, jetPortVo, entity, jetPortDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.jetPort", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(JetPortVo jetPortVo, JetPort entity,DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			JetPortDao jetPortDao = (JetPortDao)context.getBean("jetPortDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSaveJetPortRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveJetPortRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveJetPortRuleFactory.getInstance(
						ruleEnum, context, jetPortVo, entity, jetPortDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
