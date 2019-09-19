package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractSaveUserRule implements IRule{

	public ValidateDataRules(ApplicationContext ctx, String rName)
	{
		super(ctx, rName);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
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
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		// check userName field for required/length
		error=Validator.validateStringField2("User Name", userVo.getLoginName(), 30, true);
		if(null != error)errorObjects.add(error);
		
		// check firstName field for required/length
		error=Validator.validateStringField2("First Name", userVo.getFirstName(), 30, true);
		if(null != error)errorObjects.add(error);

		// check lastName field for required/length
		error=Validator.validateStringField2("Last Name", userVo.getLastName(), 35, true);
		if(null != error)errorObjects.add(error);
		
		/*
		 * only execute this rule if runmode==site or (runmode==enterprise && use_nap_auth==0)
		 */
		Boolean isUseNap=false;
		try{
			if(super.getRunMode().equals("ENTERPRISE") && super.getSystemParamValue(SystemParameterTypeEnum.USE_NAP_AUTH).equals("1"))
				isUseNap=true;
		}catch(Exception e){
		}
		
		if(isUseNap==false){
			// check password field for required/length
			error=Validator.validateStringField2("Password", userVo.getPassword(), 30, true);
			if(null != error)errorObjects.add(error);
	
			// check confirm password field for required/length
			error=Validator.validateStringField2("Confirm Password", userVo.getConfirmPassword(), 30, true);
			if(null != error)errorObjects.add(error);
		}
		
		// check unit id for required
		error=Validator.validateLongField2("Unit ID", userVo.getHomeUnitVo().getId(), true);
		if(null != error)errorObjects.add(error);
		
		// check dispatch center for required
//		error=Validator.validateLongField2("Dispatch Center", userVo.getPrimaryDispatchCenterVo().getId(), true);
//		if(null != error)errorObjects.add(error);
		
		// check work phone length and format
		if(StringUtility.hasValue(userVo.getWorkPhone())){
			userVo.setWorkPhone(PhoneNumberUtil.digitOnlyPhoneNumberFormat(userVo.getWorkPhone()));
			error=Validator.validatePhoneNumberField("Work Phone", userVo.getWorkPhone(), false);
			if(null != error)errorObjects.add(error);
		}
		
		// check cell phone length and format
		if(StringUtility.hasValue(userVo.getCellPhone())){
			userVo.setCellPhone(PhoneNumberUtil.digitOnlyPhoneNumberFormat(userVo.getCellPhone()));
			error=Validator.validatePhoneNumberField("Cell Phone", userVo.getCellPhone(), false);
			if(null != error)errorObjects.add(error);
		}
		
		// check email length and format
		if(StringUtility.hasValue(userVo.getEmail())){
			error=Validator.validateEmailField("Email", userVo.getEmail(), false);
			if(null != error)errorObjects.add(error);
		}
		
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
