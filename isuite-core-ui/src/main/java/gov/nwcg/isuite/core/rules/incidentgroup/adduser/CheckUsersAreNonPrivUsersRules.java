package gov.nwcg.isuite.core.rules.incidentgroup.adduser;

import gov.nwcg.isuite.core.vo.IncidentGroupUserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckUsersAreNonPrivUsersRules extends AbstractAddUserToIGRule implements IRule{
	
	public CheckUsersAreNonPrivUsersRules(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _OK) {
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(ruleName,true));
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * B.R. 21.0003
		 * 
		 *   16.	The system must only allow a user to add non-privileged 
		 *          User Accounts to an Incident Group access list. 
		 *          (Applies to Use Cases 21.0003 and 21.0004.)
		 */
		
		StringBuffer privUsersString = new StringBuffer();
		
		/*
		 * Loop through the users being added to the group.
		 * Determine if any of them are privileged users.
		 */
		for(IncidentGroupUserVo vo : addedUsers){
			if(vo.getUserVo().getLoginName().toUpperCase().startsWith("AD.")){
				if(StringUtility.hasValue(privUsersString.toString()))
					privUsersString.append(", "+vo.getUserLoginName());
				else
					privUsersString.append(vo.getUserVo().getLoginName());
			}
		}
		
		/*
		 * if privUsersString has contents, then return error
		 */
		if(StringUtility.hasValue(privUsersString.toString())){
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName(ruleName);
		    coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentGroup", 
							   ErrorEnum._900017_NON_PRIV_USERS_ONLY_INC_GROUP.getErrorName() 
							   , new String[]{privUsersString.toString()}
							   , MessageTypeEnum.CRITICAL));

			dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
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
