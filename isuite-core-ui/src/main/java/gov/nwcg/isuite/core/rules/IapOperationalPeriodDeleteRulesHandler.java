package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.rules.iap.deleteoperationalperiod.OperationalPeriodDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapOperationalPeriodDeleteRulesHandler extends AbstractRuleHandler {

	public IapOperationalPeriodDeleteRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	/**
	 * @param incidentShiftVo
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentShiftVo incidentShiftVo, IncidentShift entity, DialogueVo dialogueVo) throws Exception {
		
		try {

			IncidentShiftDao incidentShiftDao = (IncidentShiftDao)context.getBean("incidentShiftDao");
			
			for(OperationalPeriodDeleteRuleFactory.RuleEnum ruleEnum : OperationalPeriodDeleteRuleFactory.RuleEnum.values()) {
				IRule rule = OperationalPeriodDeleteRuleFactory.getInstance(
						ruleEnum, context, incidentShiftVo, entity, incidentShiftDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.operationalPeriod", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(IncidentShiftVo incidentShiftVo, IncidentShift entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentShiftDao incidentShiftDao = (IncidentShiftDao)context.getBean("incidentShiftDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(OperationalPeriodDeleteRuleFactory.RuleEnum ruleEnum : OperationalPeriodDeleteRuleFactory.RuleEnum.values()) {
				IRule rule = OperationalPeriodDeleteRuleFactory.getInstance(
						ruleEnum, context, incidentShiftVo, entity, incidentShiftDao);
				
				if(null != rule) {
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
