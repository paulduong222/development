package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ValidateNonAircraftCostDataRules extends AbstractSaveDailyCostRule implements IRule {
	public static final String _RULE_NAME=SaveDailyCostRuleFactory.RuleEnum.VALIDATE_NONAIRCRAFT_COST_DATA.name();

	public ValidateNonAircraftCostDataRules(ApplicationContext ctx) {
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
		
		// only process if non-aircraft
		if(super.irdcVo.getResourceCostType().name().equals("AIRCRAFT"))
			return _OK;
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		// Activity Date
		Date activityDate = DateTransferVo.getDate(irdcVo.getActivityDateVo());
		error=Validator.validateDateField2("Activity Date", activityDate, true);
		if(null != error)errorObjects.add(error);

		// Cost UOM
		if(StringUtility.hasValue(irdcVo.getCostLevel())){
			error=Validator.validateStringField2("Cost UOM", irdcVo.getRateType(), 10, true);
			if(null != error)errorObjects.add(error);
		}
		
		// Units
		error=Validator.validateDecimalField3("Units", irdcVo.getUnits(), true);
		if(null != error)errorObjects.add(error);
	
		// Unit Cost Amount
		error=Validator.validateDecimalField3("Unit Cost", irdcVo.getUnitCostAmount(), true);
		if(null != error)errorObjects.add(error);
		
		// Account Code
		if(null == irdcVo.getIncidentAccountCodeVo()){
			error = new ErrorObject("text.requiredFieldError","Account Code");
			errorObjects.add(error);
		}
		
		// Accrual Code
		if(null == irdcVo.getAccrualCodeVo()){
			error = new ErrorObject("text.requiredFieldError","Accrual Code");
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
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
