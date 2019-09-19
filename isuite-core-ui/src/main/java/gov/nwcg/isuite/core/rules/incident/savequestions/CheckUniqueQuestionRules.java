package gov.nwcg.isuite.core.rules.incident.savequestions;

import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUniqueQuestionRules extends AbstractIncidentSaveQuestionsRule implements IRule{
	public static final String _RULE_NAME=IncidentSaveQuestionsRuleFactory.RuleEnum.CHECK_UNIQUE_QUESTION.name();

	public CheckUniqueQuestionRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));

				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(dialogueVo.getResultObject() != null) {
			IncidentQuestion entity = null;

			/*
			 * Rule is:
			 *   Verify questions are unique to the incident.
			 *   
			 */
			IncidentQuestionDao incidentQuestionDao = (IncidentQuestionDao)super.context.getBean("incidentQuestionDao");
			IncidentQuestionVo newQuestion = (IncidentQuestionVo)dialogueVo.getResultObject();
			String q=newQuestion.getQuestionVo().getQuestion();
			if(!q.endsWith("?"))
				q=newQuestion.getQuestionVo().getQuestion()+"?";
			
			entity = incidentQuestionDao.getByQuestion(
					q,
					newQuestion.getIncidentVo().getId(), 
					newQuestion.getQuestionVo().getQuestionType());
			
			if(entity != null) {
	
				//If we do an EDIT and change the question text to a question that already exists
				//for this incident, return _OK.
				if(entity.getId().equals(newQuestion.getId())) {
					return _OK;
				}
				
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.incidentSettings",
								"text.duplicateQuestionsAreNotPermitted",
								null,
								MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				vos.remove(newQuestion);
				dialogueVo.setRecordset(vos);
	
				return _FAIL;
			}
		}
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
