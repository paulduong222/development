package gov.nwcg.isuite.core.rules.user.saveuser;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class UserAcctChangesFromDisabledToEnabledRules extends AbstractSaveUserRule implements IRule {

	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.USER_ACCT_CHANGES_FROM_DISABLED_TO_ENABLED_RULE.name();

	public UserAcctChangesFromDisabledToEnabledRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
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
		/*A.	2.0000 – Manage User Accounts on Enterprise

			Validation on Save
			
			3.	If the User Account was in a disabled 
				state and that state is changed to ‘Enabled’ 
				when it is saved, the account is saved to the 
				e-ISuite System in an enabled state and a 
				message displays. [Message 0026]
				
				0026	You have enabled <username>.
		*/
		
		if(userEntity != null && !userEntity.isEnabled() && userVo.getEnabled()) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"info.0026",
							new String[]{userEntity.getLoginName()},
							MessageTypeEnum.INFO));
			courseOfActionVo.setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			
			return _OK;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(super.getRunMode().equals("SITE")){
				// add user back to incident/incident group access lists
				if(!UserVo.isPrivilegedUser(userVo)){
					if(LongUtility.hasValue(userVo.getId())){
						IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
						incidentDao.executeInsertIncidentNewUser(userVo.getId());
							
						// add user to group
						IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
						incidentGroupDao.executeInsertIncidentGroupNewUser(userVo.getId());
					}
				}
				
			}
		}

	}
	
	@Override
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {

		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != dialogueVo.getCourseOfActionVo().getMessageVo()){
				dialogueVo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos().add(coaVo.getMessageVo());
			}
		}
	}

}
