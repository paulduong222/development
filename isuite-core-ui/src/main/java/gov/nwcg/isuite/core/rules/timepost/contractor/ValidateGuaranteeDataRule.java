package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateGuaranteeDataRule extends AbstractContractorRule implements IRule {

	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.VALIDATE_GUARANTEE_DATA.name();
	
	public ValidateGuaranteeDataRule(ApplicationContext ctx) {
		super(ctx,_RULE_NAME);
	}
	
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

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

		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		if(StringUtility.hasValue(super.postType)){
			if(postType.equals("GUARANTEE")){

				// check accounting code
				error=Validator.validateVoField2("Accounting Code",vo.getIncidentAccountCodeVo(), true);
				if(null != error)errorObjects.add(error);
				
				// check unitofmeasure
				String uom=vo.getUnitOfMeasure();

				error=Validator.validateStringField2("Guarantee Rate Type", uom, 50, true);
				if(null != error)errorObjects.add(error);

				// rate amount
				error=Validator.validateDecimalField2("Guarantee Amount", vo.getGuaranteeAmount(), true);
				if(null != error)errorObjects.add(error);
				
				// check for refContractorRate
				error=Validator.validateVoField2("Selected Contractor Rate", vo.getRefContractorRateVo(), true);
				if(null != error)errorObjects.add(error);
				
				// check specific data based on uom
				if(StringUtility.hasValue(uom)){
					errorObjects.addAll(validateDate(uom));	
					//errorObjects.addAll(validateHourly(uom));	
					//errorObjects.addAll(validateMileage(uom));	
				}
				
			}
		}else{
			error=Validator.validateStringField2("Unknown Post Type", "", 50, true);
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

	private Collection<ErrorObject> validateDate(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		//if(uom.equals("HOURLY")){
			// start date
			error=Validator.validateDateField2("Date", vo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

		//}
		
		return errorObjects;
	}
	
	private Collection<ErrorObject> validateHourly(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("HOURLY")){
			// start date
			error=Validator.validateDateField2("Date", vo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

		}
		
		return errorObjects;
	}

	private Collection<ErrorObject> validateMileage(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("MILEAGE")){
			// start date
			error=Validator.validateDateField2("Date", vo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

			
		}
		
		return errorObjects;
	}

}
