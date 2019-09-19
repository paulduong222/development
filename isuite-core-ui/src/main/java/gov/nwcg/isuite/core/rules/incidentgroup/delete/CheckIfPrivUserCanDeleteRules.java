package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckIfPrivUserCanDeleteRules extends AbstractIncidentGroupDeleteRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupDeleteRuleFactory.RuleEnum.CHECK_IF_PRIV_USER_CAN_DELETE.name();

	public CheckIfPrivUserCanDeleteRules(ApplicationContext ctx)
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
				 *   1.	The system must allow a user with a privileged User Account role 
				 *      to delete any Incident Groups to which they have access, 
				 *      regardless of the user who created the Incident Group. 
				 *      (Applies to Use Cases 21.0007 and 21.0008.)
				 *      
				 *   5.	The system must allow any users with a privileged User Account role 
				 *      to edit and delete any Incident Groups to which they have access. 
				 */
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
