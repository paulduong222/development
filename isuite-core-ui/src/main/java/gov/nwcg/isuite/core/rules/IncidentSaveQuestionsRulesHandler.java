package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.rules.incident.savequestions.IncidentSaveQuestionsRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentSaveQuestionsRulesHandler extends AbstractRuleHandler {
	
	public IncidentSaveQuestionsRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param incidentEntity
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(Incident incidentEntity, Collection<IncidentQuestionVo> vos, DialogueVo dialogueVo) throws Exception {
		
		try{
		
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			
			for(IncidentSaveQuestionsRuleFactory.RuleEnum ruleEnum : IncidentSaveQuestionsRuleFactory.RuleEnum.values()){
				IRule rule = IncidentSaveQuestionsRuleFactory.getInstance(ruleEnum, context, incidentEntity,vos,dao);
				
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
	 * @param incidentEntity
	 * @param vos
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(Incident incidentEntity, Collection<IncidentQuestionVo> vos, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			
				
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentSaveQuestionsRuleFactory.RuleEnum ruleEnum : IncidentSaveQuestionsRuleFactory.RuleEnum.values()){
				IRule rule = IncidentSaveQuestionsRuleFactory.getInstance(ruleEnum, context, incidentEntity,vos,dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
