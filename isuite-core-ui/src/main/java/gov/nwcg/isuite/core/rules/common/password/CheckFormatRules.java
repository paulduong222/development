package gov.nwcg.isuite.core.rules.common.password;

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
	
	public CheckFormatRules(ApplicationContext ctx, String rName)
	{
		super(ctx,rName);
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
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
				
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
				
		Collection<ErrorObject> errorObjectVos = new ArrayList<ErrorObject>();
		if(newPassword.length() < MIN_PASSWORD_LENGTH) {
			errorObjectVos.add(new ErrorObject("error.0024.1"));
		}
		
	    if(!checkMatch("[A-Z]", newPassword)) {
	    	errorObjectVos.add(new ErrorObject("error.0024.2"));
	    }
		
	    if(!checkMatch("[a-z]", newPassword)) {
	    	errorObjectVos.add(new ErrorObject("error.0024.3"));
	    }
	    
	    if(!checkMatch("[\\d]", newPassword)) {
	    	errorObjectVos.add(new ErrorObject("error.0024.4"));
	    }
	    
	    if(!checkMatch("[!#%&^_*.]", newPassword)) {
	    	errorObjectVos.add(new ErrorObject("error.0024.5"));
	    }
	    
		if(errorObjectVos != null && errorObjectVos.size() > 0) {

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(ruleName);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ERROR);
			courseOfActionVo.setErrorObjectVos(errorObjectVos);
			courseOfActionVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	private Boolean checkMatch(String regEx, String password) {
		Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(password);
	    return matcher.find();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
