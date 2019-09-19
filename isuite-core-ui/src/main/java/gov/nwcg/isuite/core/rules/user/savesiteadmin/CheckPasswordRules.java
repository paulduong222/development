package gov.nwcg.isuite.core.rules.user.savesiteadmin;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.PasswordUtility;

import org.springframework.context.ApplicationContext;

public class CheckPasswordRules extends AbstractSaveSiteAdminRule implements IRule {

	public static final String _RULE_NAME=SaveSiteAdminRuleFactory.RuleEnum.CHECK_PASSWORD_RULE.name();

	public CheckPasswordRules(ApplicationContext ctx) {
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
		
		// check password and confirm password are same
		if(!userVo.getPassword().equals(userVo.getConfirmPassword())) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"error.0023",
							null,
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			return _FAIL;
		}
		
		// check password format
		/*
		 * Password must:
		 * error.0024.1=The password must contain at least 12 characters.
		 * error.0024.2=The password must contain at least 1 upper case letter.
		 * error.0024.3=The password must contain at least 1 lower case letter.
		 * error.0024.4=The password must contain at least 1 number.
		 * error.0024.5=The password must contain at least 1 of the following 
		 *              special characters: !#%&amp;^_*.
		 */
		Collection<ErrorObject> errorObjectVos = new ArrayList<ErrorObject>();
		if(!PasswordUtility.isValidLength(userVo.getPassword()))
			errorObjectVos.add(new ErrorObject("error.0024.1"));

		if(!PasswordUtility.containsUpper(userVo.getPassword()))
	    	errorObjectVos.add(new ErrorObject("error.0024.2"));

		if(!PasswordUtility.containsLower(userVo.getPassword()))
	    	errorObjectVos.add(new ErrorObject("error.0024.3"));
		
		if(!PasswordUtility.containsNumber(userVo.getPassword()))
	    	errorObjectVos.add(new ErrorObject("error.0024.4"));

		if(!PasswordUtility.containsSpecial(userVo.getPassword()))
	    	errorObjectVos.add(new ErrorObject("error.0024.5"));
		
		if(CollectionUtility.hasValue(errorObjectVos)){
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ERROR);
			courseOfActionVo.setErrorObjectVos(errorObjectVos);
			courseOfActionVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
