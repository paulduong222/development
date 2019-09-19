package gov.nwcg.isuite.core.rules.incident.restrincadduser;

import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUserNotAlreadyAddedRules extends AbstractRestrIncAddUserRule implements IRule{
	public static final String _RULE_NAME=RestrictIncidentAddUserRuleFactory.RuleEnum.CHECK_USER_NOT_ALREADY_ADDED.name();

	public CheckUserNotAlreadyAddedRules(ApplicationContext ctx)
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Verify the users being added are not already added as restricted incident users.
		 * 
		 * If loop doesn't fail, then the rule check passed.
		 */
		for(RestrictedIncidentUser riUser : entity.getRestrictedIncidentUsers()) {
			for(RestrictedIncidentUserVo riUserVo : riuVos) {
				if(riUserVo.getUserVo().getId().equals(riUser.getUser().getId())) {

					MessageVo messageVo = new MessageVo(
							"text.incidents"
							, "error.userAlreadyAddedToRestrictedIncident"
							, new String[]{riUserVo.getUserVo().getUserLoginName().toString()}
							, MessageTypeEnum.CRITICAL);
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
					coaVo.setMessageVo(messageVo);
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(coaVo);

					return _FAIL;
				}
			}
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
