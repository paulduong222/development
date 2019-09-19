package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckForExistingTimePostingsRules extends AbstractTimeAssignAdjustSaveBatchRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.CHECK_FOR_EXISTING_TIME_POSTINGS_BATCH.name();

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
		try{
			Collection<TimeAssignAdjustVo> list = super.taaDao.getNoTimePostingsList(super.crewIds);
			if(CollectionUtility.hasValue(list) && !LongUtility.hasValue(super.timeAssignAdjustVo.getId())){
				CourseOfActionVo coaVo = new CourseOfActionVo();

				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
				coaVo.setCustomPromptVo(new CustomPromptVo("NO_TIME_POSTINGS_BATCH","text.adjustments" ,"action.0146crew",list));

				dialogueVo.setCourseOfActionVo(coaVo);

				return _FAIL;
			}
		}catch(Exception e){
			
		}
		
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getCustomPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getCustomPromptResult(dialogueVo) == PromptVo._NO){
			
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
