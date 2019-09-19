package gov.nwcg.isuite.core.rules.incidentgroup.deletequestion;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckNonStandardQuestionRules extends AbstractIncidentGroupDeleteQuestionRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupDeleteQuestionRuleFactory.RuleEnum.CHECK_NON_STANDARD_QUESTION.name();

	public CheckNonStandardQuestionRules(ApplicationContext ctx)
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

		/*
		 * Rule is:
		 *   Verify question to be deleted is non standard question.
	     *  
	     *  error.0188=You cannot delete a standard travel question.
		 *  error.0199=You cannot delete a standard check-in question.
		 *   
		 */
		if(entity.getQuestion().isStandard()){
			String errorMsg="";
			
			if(entity.getQuestion().getQuestionType()==QuestionTypeEnum.AIRTRAVEL)
				errorMsg="error.0188";
			else
				errorMsg="error.0199";

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_INCIDENT_GROUP_QUESTION");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
			coaVo.setMessageVo(new MessageVo("text.incident"
												, errorMsg
												, null
												, MessageTypeEnum.CRITICAL));

			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;

		}

		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
