package gov.nwcg.isuite.core.rules.login;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUserPasswordRules extends AbstractLoginRule implements IRule{
	public static final String _RULE_NAME=LoginRuleFactory.RuleEnum.CHECK_USER_PASSWORD.name();

	public CheckUserPasswordRules(ApplicationContext ctx)
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
		 * only execute this rule if runmode==site or (runmode==enterprise && use_nap_auth==0)
		 */
		Boolean isNapAuth=false;
		try{
			if(super.getRunMode().equals("ENTERPRISE") && super.getSystemParamValue(SystemParameterTypeEnum.USE_NAP_AUTH).equals("1"))
				isNapAuth=true;
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("AUTHENTICATION_ERROR");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.login",
							"info.generic",
							new String[]{"Unable to resolve system parameter USE_NAP_AUTH"},
							MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		
		if(isNapAuth==true)
			return _OK;
		
		FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
		String pwd = new String(enc.encrypt(password.getBytes()));
		
		if(!user.getPassword().equals(pwd)){
			
			// increase failed login attempts by 1
			user.setFailedLoginAttempts(user.getFailedLoginAttempts()+1);

			usms.manageUserSession(user
					, UserSessionActionTypeEnum.INVALID_LOGIN
					, UserSessionActionCauseEnum.USER_INITIATED
					, null);

			String lockCount=super.getSystemParamValue(SystemParameterTypeEnum.FAILED_LOGIN_ATTEMPTS_LOCK);

			if(null != lockCount && !lockCount.isEmpty()){
				int lockVal=Integer.parseInt(lockCount);
				if(user.getFailedLoginAttempts() >= lockVal){
					user.setEnabled(false);
					userDao.save(user);
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("ACCOUNT_NOW_LOCKED");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
					coaVo.setMessageVo(
							new MessageVo(
									"text.login",
									"info.0019",
									new String[]{},
									MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(true);
					
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}else{
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
					coaVo.setMessageVo(
							new MessageVo(
									"text.login",
									"error.0003",
									new String[]{},
									MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(true);
					
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}else{
				throw new ServiceException("Unable to get setting for failed login attempts.");
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
