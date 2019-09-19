package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class DisplayIncidentMessageRules extends AbstractIncidentGroupDeleteRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupDeleteRuleFactory.RuleEnum.DISPLAY_MESSAGE_INCIDENTS.name();

	public DisplayIncidentMessageRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{

			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					
				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
					
			}else{
				/*
				 * B.R. 21.0007
				 * 
				 *   When the user deletes an Incident Group, 
				 *   the system must display a message indicating that the Incidents 
				 *   within the group will not be deleted. 
				 *   Only the Incident Group will be deleted. 
				 *   [Message 0202] (Applies to Use Cases 21.0007 and 21.0008.)
				 *   
				 *   Slight deviation from the business rule. We are showing the
				 *   message just prior to deleting.
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incidentGroup","info.0202",new String[]{incidentGroupEntity.getGroupName()},PromptVo._YES ));
				coaVo.getPromptVo().setYesLabel("OK");
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
				
			}
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
