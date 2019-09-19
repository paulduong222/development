/**
 * 
 */
package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class ValidateIRODataRules extends AbstractSaveIRORule implements IRule {
	
	public static final String _RULE_NAME = SaveIRORuleFactory.RuleEnum.VALIDATE_IRO_DATA.name();
	
	public ValidateIRODataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			// if rule check has been completed, return
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			// Run rule check
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			 // Rule check passed, mark as completed
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
		
		//Required Fields:
		//0. Safety check - Incident must already be associated with this IRO
		//1. Account code
		//2. Cost Description
		//3. Item Code
		//4. Status 
		//5. Item Name
		//6. Actual Release Time 
		
		// Safety check - Is incident associated?
		if(!AbstractVo.hasValue(vo.getIncidentVo())){
			error = new ErrorObject("info.generic","An incident must be associated with the Other Resource.");
			errorObjects.add(error);
		}
		
		// Account Code cannot be blank
		if(null == vo.getResourceOtherVo().getIncidentAccountCodeVo()){
			error = new ErrorObject("text.requiredFieldError","Accounting Code");
			errorObjects.add(error);
		}
		
		//Cost Description cannot be blank
		error=Validator.validateStringField2("Cost Description", vo.getResourceOtherVo().getCostDescription(), 50, true);
		if(null != error)errorObjects.add(error);

		//Item Code cannot be blank
		if(null == vo.getResourceOtherVo().getKindVo()){
			error = new ErrorObject("text.requiredFieldError","Item Code");
			errorObjects.add(error);
		} else { // Check for Item Name if Item Code, aka KindVo exists
			error=Validator.validateStringField2("Item Name", vo.getResourceOtherVo().getKindVo().getDescription(), 75, true);
			if(null != error)errorObjects.add(error);
		}
		
		//Status cannot be blank
		if(null == vo.getAssignmentStatusVo()){
			error = new ErrorObject("text.requiredFieldError","Status");
			errorObjects.add(error);
		} 
		
		//Actual Release Time, if entered by the user, must be valid
		if(null != vo.getResourceOtherVo().getActualReleaseDate() 
				&& StringUtility.hasValue(vo.getResourceOtherVo().getActualReleaseTime())) {
			if(!DateUtil.isMilitaryTime(vo.getResourceOtherVo().getActualReleaseTime())){
				error = new ErrorObject("error.invalidTimeFormat","Actual Release Time");
				errorObjects.add(error);
			}
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			CourseOfActionVo coaVo = buildValidationErrorCoaVo(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
