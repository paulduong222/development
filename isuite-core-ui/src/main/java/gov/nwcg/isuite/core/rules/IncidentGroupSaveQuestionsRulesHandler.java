package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.savequestions.IncidentGroupSaveQuestionsRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
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

public class IncidentGroupSaveQuestionsRulesHandler extends AbstractRuleHandler {
	
	public IncidentGroupSaveQuestionsRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param incidentGroupEntity
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentGroup incidentGroupEntity, Collection<IncidentGroupQuestionVo> vos, DialogueVo dialogueVo) throws Exception {
		
		try{
		
			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(IncidentGroupSaveQuestionsRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveQuestionsRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveQuestionsRuleFactory.getInstance(ruleEnum, context, incidentGroupEntity, vos, dao);
				
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
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param incidentGroupEntity
	 * @param vos
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IncidentGroup incidentGroupEntity, Collection<IncidentGroupQuestionVo> vos, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
				
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentGroupSaveQuestionsRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveQuestionsRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveQuestionsRuleFactory.getInstance(ruleEnum, context, incidentGroupEntity,vos,dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
