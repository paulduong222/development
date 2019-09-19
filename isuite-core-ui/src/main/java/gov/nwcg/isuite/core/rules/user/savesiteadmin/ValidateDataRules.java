package gov.nwcg.isuite.core.rules.user.savesiteadmin;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractSaveSiteAdminRule implements IRule{
	public static final String _RULE_NAME=SaveSiteAdminRuleFactory.RuleEnum.VALIDATE_DATA.name();

	public ValidateDataRules(ApplicationContext ctx)
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
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;

		if(StringUtility.hasValue(userVo.getDbinitial())){
			// check databaseName field for required/length
			error=Validator.validateStringField2("Database Name", userVo.getDbname(), 30, true);
			if(null != error)errorObjects.add(error);
			
			// check databasepwd field for required/length
			error=Validator.validateStringField2("Database Password", userVo.getDbpwd(), 30, true);
			if(null != error)errorObjects.add(error);

			// check databasepwd field for required/length
			error=Validator.validateStringField2("Confirm Database Password", userVo.getDbconfirmpwd(), 30, true);
			if(null != error)errorObjects.add(error);

			// check for matching pwd and confirm pwd
			if(StringUtility.hasValue(userVo.getDbpwd()) && StringUtility.hasValue(userVo.getDbconfirmpwd())){
				if(!userVo.getDbpwd().equals(userVo.getDbconfirmpwd())){
					ErrorObject error2 = new ErrorObject("error.800000","Database Password and Confirm Password do not match.");
					errorObjects.add(error2);
				}
			}
			
		}
		
		// check userName field for required/length
		error=Validator.validateStringField2("User Name", userVo.getLoginName(), 30, true);
		if(null != error)errorObjects.add(error);
		
		// check userName begins with 'ad.'
		if(StringUtility.hasValue(userVo.getLoginName())){
			if(!userVo.getLoginName().toUpperCase().startsWith("AD."))
				errorObjects.add(new ErrorObject("error.0228"));
		}
		
		// check firstName field for required/length
		error=Validator.validateStringField2("First Name", userVo.getFirstName(), 30, true);
		if(null != error)errorObjects.add(error);

		// check lastName field for required/length
		error=Validator.validateStringField2("Last Name", userVo.getLastName(), 35, true);
		if(null != error)errorObjects.add(error);
		
		// check password field for required/length
		error=Validator.validateStringField2("Password", userVo.getPassword(), 30, true);
		if(null != error)errorObjects.add(error);

		// check confirm password field for required/length
		error=Validator.validateStringField2("Confirm Password", userVo.getConfirmPassword(), 30, true);
		if(null != error)errorObjects.add(error);
		
		// check unit id for required
		error=Validator.validateLongField2("Unit ID", userVo.getHomeUnitVo().getId(), true);
		if(null != error)errorObjects.add(error);
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
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
