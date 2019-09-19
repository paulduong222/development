package gov.nwcg.isuite.core.rules.incident.restrincadduser;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckIfOwnerCanAddUserRules extends AbstractRestrIncAddUserRule implements IRule{
	public static final String _RULE_NAME=RestrictIncidentAddUserRuleFactory.RuleEnum.CHECK_IF_OWNER_CAN_ADD_USER.name();

	public CheckIfOwnerCanAddUserRules(ApplicationContext ctx)
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * 
		 * B.R. 3.0011 
		 *  
		 *     1.	The system must allow an Owner of a Restricted Incident 
		 *          to add or remove the User Accounts and owners that can 
		 *          access a restricted Incident. 
		 */
	
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
