package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.rules.iap.savesetting.SettingSaveRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapSettingSaveRulesHandler extends AbstractRuleHandler {

	public IapSettingSaveRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	/**
	 * @param incidentShiftVo
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentVo incidentVo, Incident entity, DialogueVo dialogueVo) throws Exception {
		
		try {

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			
			for(SettingSaveRuleFactory.RuleEnum ruleEnum : SettingSaveRuleFactory.RuleEnum.values()) {
				IRule rule = SettingSaveRuleFactory.getInstance(
						ruleEnum, context, incidentVo, entity, incidentDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.iapSettings", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/**
	 * @param incidentShiftVo
	 * @param entity
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IncidentVo incidentVo, Incident entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(SettingSaveRuleFactory.RuleEnum ruleEnum : SettingSaveRuleFactory.RuleEnum.values()) {
				IRule rule = SettingSaveRuleFactory.getInstance(
						ruleEnum, context, incidentVo, entity, incidentDao);
				
				if(null != rule) {
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

