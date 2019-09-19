package gov.nwcg.isuite.core.rules.incident.restrictincident;

import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class AddToUsersRestrictedWorkAreaRules extends AbstractRestrictIncidentRule implements IRule{
	public static final String _RULE_NAME=RestrictIncidentRuleFactory.RuleEnum.ADD_TO_USERS_RESTRICTED_WORK_AREA.name();

	
	public AddToUsersRestrictedWorkAreaRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){

				/*
				 * B.R. 30010 
				 *   12.	When an Incident is restricted, the system must 
				 *          automatically place the Incident in the Restricted Work Area 
				 *          for any User Account to which the Incident was restricted.
				 *          
				 */
				dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME, true));
					
				return _OK;
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
		try {
			if(super.isEnterpriseMode()) {
				WorkAreaDao waDao = (WorkAreaDao)context.getBean("workAreaDao");
				waDao.syncUserRestrictedWorkArea(super.getUserSessionVo().getUserId());
			}
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}

}
