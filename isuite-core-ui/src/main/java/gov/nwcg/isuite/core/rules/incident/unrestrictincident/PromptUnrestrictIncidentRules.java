package gov.nwcg.isuite.core.rules.incident.unrestrictincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PromptUnrestrictIncidentRules extends AbstractRule implements IRule{
	//public static final String _RULE_NAME=UnrestrictIncidentRuleFactory.RuleEnum.PROMPT_UNRESTRICT_INCIDENT.name();
	public static final String _RULE_NAME="Unrestrict Prompt is being handled client side";

	private IncidentVo vo=null;
	private Incident incidentEntity=null;
	
	public PromptUnrestrictIncidentRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(UnrestrictIncidentRuleFactory.ObjectTypeEnum.INCIDENT_VO.name()))
			vo = (IncidentVo)object;
		if(objectName.equals(UnrestrictIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
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
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				/*  
				 * Check Prompt Result:
				 * 
				 *  1.1	When the user selects Yes in response to message 0167, 
				 *      the system must unrestrict the Incident.
				 *      
				 *	1.2	When the user selects No in response to message 0167, 
				 *      the system must not unrestrict the Incident.
				 */
				if(getPromptResult(dialogueVo) == PromptVo._YES) {
					
					if(incidentEntity.getRestricted() == false){
						throw new ServiceException("Incident is already Unrestricted");
					}
					
					incidentEntity.setRestricted(false);
					
				}else if(getPromptResult(dialogueVo) == PromptVo._NO){
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"unrestricting incident"}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
						
					dialogueVo.setCourseOfActionVo(coaVo);
						
					return _FAIL;
				}
					
					
			}else{
				/*
				 * B.R. 3.0011 
				 *  When the user unrestricts an Incident, 
				 *  the user must confirm the unrestriction. 
				 *  [Message 0167]
				 */
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incident","action.0167",new String[]{vo.getIncidentName().toUpperCase()},PromptVo._YES | PromptVo._NO ));
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
