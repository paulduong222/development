package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckIfDataStewardUserCanDeleteRules extends AbstractIncidentGroupDeleteRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupDeleteRuleFactory.RuleEnum.CHECK_IF_DATA_STEWARD_USER_CAN_DELETE.name();

	public CheckIfDataStewardUserCanDeleteRules(ApplicationContext ctx)
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
				 *   3.	The system must allow a user with a Data Steward role 
				 *      to delete any Incident Groups they created that do not 
				 *      have any other User Accounts in the access list for the Incident Group.
				 *      (Applies to Use Cases 21.0007 and 21.0008.)
				 *      
				 * B.R. 21.0001
				 * 
				 *   6.	A user with a Data Steward role can view all Incident Groups 
				 *      but can only add, edit or delete Incident Groups to which no 
				 *      additional User Accounts are assigned. 
				 *      (Applies to Use Cases 21.0001 and 21.0002.)   
				 */
				
				/*
				 * Get the logged in user session
				 */
				UserSessionVo userSessionVo =  getUserSessionVo();
				
				
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
