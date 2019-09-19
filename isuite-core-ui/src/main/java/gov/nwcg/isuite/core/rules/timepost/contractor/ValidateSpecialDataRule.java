package gov.nwcg.isuite.core.rules.timepost.contractor;

import java.util.ArrayList;
import java.util.Collection;

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

import org.springframework.context.ApplicationContext;

public class ValidateSpecialDataRule extends AbstractContractorRule implements IRule {

	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.VALIDATE_SPECIAL_DATA.name();
	
	public ValidateSpecialDataRule(ApplicationContext ctx) {
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
			
			if(super.postType.equals("BOTH")){
				// check if we can process it
				
				// if special is daily, and not primary is not, show error
				if(specialVo.getUnitOfMeasure().equals("DAILY")
						&& !vo.getUnitOfMeasure().equals("DAILY")){
					error = new ErrorObject("info.generic","When posting BOTH, the Special Rate Type cannot be DAILY when the Primary Rate Type is not DAILY");
					errorObjects.add(error);
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
			}
			
			if(postType.equals("SPECIAL")
					|| postType.equals("BOTH")){ 
				
				// check accounting code
				error=Validator.validateVoField2("Accounting Code",specialVo.getIncidentAccountCodeVo(), true);
				if(null != error)errorObjects.add(error);
				
				// check unitofmeasure
				String uom=specialVo.getUnitOfMeasure();

				error=Validator.validateStringField2("Special Rate Type", uom, 50, true);
				if(null != error)errorObjects.add(error);

				// rate amount
				error=Validator.validateDecimalField2("Rate Amount", specialVo.getRateAmount(), true);
				if(null != error)errorObjects.add(error);
				
				// check for refContractorRate
				error=Validator.validateVoField2("Selected Contractor Rate", specialVo.getRefContractorRateVo(), true);
				if(null != error)errorObjects.add(error);
				
				// check specific data based on uom
				if(StringUtility.hasValue(uom)){
					errorObjects.addAll(validateDaily(uom));	
					errorObjects.addAll(validateHourly(uom));	
					errorObjects.addAll(validateEach(uom));	
					errorObjects.addAll(validateMileage(uom));	
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

	private Collection<ErrorObject> validateDaily(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("DAILY")){
			// start date
			error=Validator.validateDateField2("Start Date", specialVo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);
			
			// stop date
			error=Validator.validateDateField2("End Date", specialVo.getPostStopDate(), true);
			if(null != error)errorObjects.add(error);
		}
		
		return errorObjects;
	}

	private Collection<ErrorObject> validateHourly(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("HOURLY")){
			// start date
			error=Validator.validateDateField2("Date", specialVo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

			// start time
			error = DateTimeValidator.validateTimeField2("Start Time", specialVo.getPostStartTime(),true);
			if(error!=null)errorObjects.add(error);
			
			// stop time
			error = DateTimeValidator.validateTimeField2("Stop Time", specialVo.getPostStopTime(),true);
			if(error!=null)errorObjects.add(error);
		}
		
		return errorObjects;
	}
	
	private Collection<ErrorObject> validateEach(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("EACH")){
			
			// start date
			error=Validator.validateDateField2("Date", specialVo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

			// quantity
			error=Validator.validateDecimalField2("Units", specialVo.getQuantity(), true);
			if(null != error)errorObjects.add(error);
		}
		
		return errorObjects;
	}

	private Collection<ErrorObject> validateMileage(String uom) {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		if(uom.equals("MILEAGE")){
			// start date
			error=Validator.validateDateField2("Date", specialVo.getPostStartDate(), true);
			if(null != error)errorObjects.add(error);

			// quantity
			error=Validator.validateDecimalField2("Miles", specialVo.getQuantity(), true);
			if(null != error)errorObjects.add(error);
			
		}
		
		return errorObjects;
	}
	
	
}
