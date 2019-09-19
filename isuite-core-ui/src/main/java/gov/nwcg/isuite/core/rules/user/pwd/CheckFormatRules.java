package gov.nwcg.isuite.core.rules.user.pwd;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

public class CheckFormatRules extends AbstractPasswordValidationRule implements IRule{
	public static final String _RULE_NAME=PasswordValidationRuleFactory.RuleEnum.CHECK_FORMAT.name();

	public CheckFormatRules(ApplicationContext ctx)
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
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		/*
		 * Password must:
		 * 
		 * error.0024.1=The password must contain at least 12 characters.
		 * error.0024.2=The password must contain at least 1 upper case letter.
		 * error.0024.3=The password must contain at least 1 lower case letter.
		 * error.0024.4=The password must contain at least 1 number.
		 * error.0024.5=The password must contain at least 1 of the following 
		 *              special characters: !#%&amp;^_*.
		 */
		
		//TODO:  Do we need to check for dictionary words within the password?
		
		newPassword = super.newPassword; //userVo.getPassword();
		
		Collection<ErrorObject> errorObjectVos = new ArrayList<ErrorObject>();
		if(newPassword.length() < MIN_PASSWORD_LENGTH) {
			errorObjectVos.add(new ErrorObject("error.0024.1"));
		}
		
	    Pattern pattern = Pattern.compile("[A-Z]");
	    Matcher matcher = pattern.matcher(newPassword);
	    if(!matcher.find()) {
	    	errorObjectVos.add(new ErrorObject("error.0024.2"));
	    }
		
	    pattern = Pattern.compile("[a-z]");
	    matcher = pattern.matcher(newPassword);
	    if(!matcher.find()) {
	    	errorObjectVos.add(new ErrorObject("error.0024.3"));
	    }
	    
	    pattern = Pattern.compile("[\\d]");
	    matcher = pattern.matcher(newPassword);
	    if(!matcher.find()) {
	    	errorObjectVos.add(new ErrorObject("error.0024.4"));
	    }
	    
	    pattern = Pattern.compile("[!#%&^_*.]");
	    matcher = pattern.matcher(newPassword);
	    if(!matcher.find()) {
	    	errorObjectVos.add(new ErrorObject("error.0024.5"));
	    }
	    
		if(errorObjectVos != null && errorObjectVos.size() > 0) {

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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
