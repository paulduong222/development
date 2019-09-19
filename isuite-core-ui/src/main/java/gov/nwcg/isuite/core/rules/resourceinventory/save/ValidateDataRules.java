package gov.nwcg.isuite.core.rules.resourceinventory.save;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

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

public class ValidateDataRules extends AbstractResourceInventorySaveRule implements IRule {
	public static final String _RULE_NAME=ResourceInventorySaveRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
	public ValidateDataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
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
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		if(resourceVo.isPerson()) {
			// check firstName field for required/length
			error=Validator.validateStringField2("First Name", resourceVo.getFirstName(), 35, true);
			if(null != error)errorObjects.add(error);
			
			// check lastName field for required/length
			error=Validator.validateStringField2("Last Name", resourceVo.getLastName(), 35, true);
			if(null != error)errorObjects.add(error);
			
		} else {
			// check resourceName field for required/length
			error=Validator.validateStringField2("Resource Name", resourceVo.getResourceName(), 55, true);
			if(null != error)errorObjects.add(error);
			
			if(null == resourceVo.getPrimaryQual().getKindVo()) {
				errorObjects.add(new ErrorObject("text.requiredFieldError","Item Code"));
			}
		}
		
//		if(StringUtility.hasValue(resourceVo.getPhone())) {
//			resourceVo.setPhone(PhoneNumberUtil.digitOnlyPhoneNumberFormat(resourceVo.getPhone()));
//			error=Validator.validatePhoneNumberField("Cell Phone Number", resourceVo.getPhone(), false);
//			if(null != error)errorObjects.add(error);
//		}
		
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
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
}
