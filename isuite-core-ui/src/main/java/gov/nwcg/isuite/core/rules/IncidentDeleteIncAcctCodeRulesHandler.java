package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.incident.deleteincacctcode.DeleteIncAcctCodeRuleFactory;
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

public class IncidentDeleteIncAcctCodeRulesHandler extends AbstractRuleHandler {
	
	public IncidentDeleteIncAcctCodeRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param entity
	 * @param incidentEntity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentAccountCode entity, Incident incidentEntity, DialogueVo dialogueVo) throws Exception {
		
		try{
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incidentEntity.getId(), "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			IncidentAccountCodeDao dao = (IncidentAccountCodeDao)context.getBean("incidentAccountCodeDao");
			
			for(DeleteIncAcctCodeRuleFactory.RuleEnum ruleEnum : DeleteIncAcctCodeRuleFactory.RuleEnum.values()){
				IRule rule = DeleteIncAcctCodeRuleFactory.getInstance(ruleEnum, context, entity, incidentEntity, dao);
				
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

	/**
	 * @parm entity
	 * @param incidentEntity
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IncidentAccountCode entity, Incident incidentEntity, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			IncidentAccountCodeDao dao = (IncidentAccountCodeDao)context.getBean("incidentAccountCodeDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(DeleteIncAcctCodeRuleFactory.RuleEnum ruleEnum : DeleteIncAcctCodeRuleFactory.RuleEnum.values()){
				IRule rule = DeleteIncAcctCodeRuleFactory.getInstance(ruleEnum, context, entity, incidentEntity, dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
