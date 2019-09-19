package gov.nwcg.isuite.core.rules.incident.restrictincident;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PromptRestrictIncidentRules extends AbstractRestrictIncidentRule implements IRule{
	//public static final String _RULE_NAME=RestrictIncidentRuleFactory.RuleEnum.PROMPT_RESTRICT_INCIDENT.name();
	public static final String _RULE_NAME="Restrict Prompt is being handled client side";

	public PromptRestrictIncidentRules(ApplicationContext ctx)
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
			

			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check prompt result
				 */
				if(this.checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
				
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
			}

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
		 * B.R. 3.0010 
		 * 	When a user restricts an Incident, 
		 *  the system must display an action message confirming 
		 *  the restriction. [Message 0175]
		 */
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME);
		coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
		coaVo.setPromptVo(new PromptVo("text.incident","action.0175",new String[]{vo.getIncidentName().toUpperCase()},PromptVo._YES | PromptVo._NO ));
		dialogueVo.setCourseOfActionVo(coaVo);
		
		return _FAIL;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {
	
		 /*  
		  * Check Prompt Result:
		  * 
		 *  1.1	When the user selects Yes in response to action message 0175, 
		 *      the system restricts the Incident and opens a Restrict Incident Access page.
		 *      
		 *	1.2	When the user selects No in response to action message 0175, 
		 *      the system does not restrict the Incident.
		 */
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"restricting incident"}, MessageTypeEnum.INFO));
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
