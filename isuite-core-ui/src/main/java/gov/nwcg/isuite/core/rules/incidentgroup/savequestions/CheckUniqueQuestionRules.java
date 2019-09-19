package gov.nwcg.isuite.core.rules.incidentgroup.savequestions;

import java.util.ArrayList;

import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUniqueQuestionRules extends AbstractIncidentGroupSaveQuestionsRule implements IRule{
	
	public CheckUniqueQuestionRules(ApplicationContext ctx, String rName)
	{
		super(ctx, rName);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));

				
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
		/*
		 * Rule is:
		 *   Verify the questions are unique to the incident group.
		 *   
		 */	
		IncidentGroupQuestionDao incidentGroupQuestionDao = (IncidentGroupQuestionDao)super.context.getBean("incidentGroupQuestionDao");
		IncidentGroupQuestion entity = null;
		
		for(IncidentGroupQuestionVo question : vos) {
			String q=question.getQuestionVo().getQuestion();
			if(!q.endsWith("?"))
				q=question.getQuestionVo().getQuestion()+"?";
			
			entity = incidentGroupQuestionDao.getByQuestion(
					q,
					question.getIncidentGroupId(), 
					question.getQuestionVo().getQuestionType());
			
			if(entity != null) {

				if(!entity.getId().equals(question.getId())) {
				
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.incidentSettings",
									"text.duplicateQuestionsAreNotPermitted",
									null,
									MessageTypeEnum.CRITICAL));
					dialogueVo.setCourseOfActionVo(courseOfActionVo);
		
					return _FAIL;
				} else {
					entity = null;
				}
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
