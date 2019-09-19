package gov.nwcg.isuite.core.rules.incident.restrincdeleteuser;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckIfOwnerCanDeleteUserRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=RestrictIncidentDeleteUserRuleFactory.RuleEnum.CHECK_IF_OWNER_CAN_DELETE_USER.name();

	public CheckIfOwnerCanDeleteUserRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){
					
				if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					// add to processed
					dialogueVo.getCourseOfActionVo().setIsComplete(true);
					dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
						
				}else{
					/*
					 * 
					 * B.R. 3.0011 
					 *  
					 *     1.	The system must allow an Owner of a Restricted Incident 
					 *          to add or remove the User Accounts and owners that can 
					 *          access a restricted Incident. 
					 */

				}
			}else{
				
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
