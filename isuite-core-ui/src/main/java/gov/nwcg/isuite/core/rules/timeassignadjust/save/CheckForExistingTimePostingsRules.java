package gov.nwcg.isuite.core.rules.timeassignadjust.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckForExistingTimePostingsRules extends AbstractTimeAssignAdjustSaveRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveRuleFactory.RuleEnum.CHECK_FOR_EXISTING_TIME_POSTINGS.name();

	public CheckForExistingTimePostingsRules(ApplicationContext ctx)
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

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
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
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
	
		/*
		 * B.R. 6.0011
		 * 	 1.	If the user selects a Resource that does not have time postings
		 *      when posting an adjustment, the system must display a message indicating 
		 *      that no time postings exist for the Resource. 
		 *      The system may allow the user to post adjustments for the Resource 
		 *      but cannot create an invoice for those adjustments until time is posted. 
		 *      [Message 0145]
		 * 
		 */
		Boolean hasTimePostings=false;
		if(null != assignmentEntity && null != assignmentEntity.getAssignmentTime()){
			
			/*
			 * The rule doesn't specify 'active time postings', it only refers to time postings
			 * in general.  Development thinks the rule implies any time posting (invoiced or not).
			 */
			if(CollectionUtility.hasValue(assignmentEntity.getAssignmentTime().getAssignmentTimePosts())){
				hasTimePostings=true;
			}
		}

		if(!hasTimePostings){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.adjustments","action.0145a",null,PromptVo._YES | PromptVo._NO));
			coaVo.getPromptVo().setYesLabel("OK");
			coaVo.getPromptVo().setNoLabel("CANCEL POST");
			coaVo.getPromptVo().setButtonWidth(150);
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.adjustments", "text.abortProcess" , new String[]{"adjustment post"}, MessageTypeEnum.INFO));
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
