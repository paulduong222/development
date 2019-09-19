package gov.nwcg.isuite.core.rules.iap.copyplan;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.iap.saveplan.SavePlanRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.Validator;

public class ValidateDataRules extends AbstractCopyPlanRule implements IRule {
	public static final String _RULE_NAME=SavePlanRuleFactory.RuleEnum.VALIDATE_PLAN_DATA.name();
	
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
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		// check plan source
		error = Validator.validateVoField2("Select Plan", iapPlanSourceVo, true);
		if(null != error)errorObjects.add(error);
		
		if(null != iapPlanDestinationVo) {
			// check incidentName
			error=Validator.validateStringField2("Incident Name", iapPlanDestinationVo.getIncidentName(), 200, true);
			if(null != error)errorObjects.add(error);
			
			// check fromDate
			error=Validator.validateDateField2("From Date",iapPlanDestinationVo.getFromDate(), true);
			if(null != error)errorObjects.add(error);
			
			// check toDate
			error=Validator.validateDateField2("To Date",iapPlanDestinationVo.getToDate(), true);
			if(null != error)errorObjects.add(error);

			// check from time
			error=DateTimeValidator.validateTimeField("From Time", iapPlanDestinationVo.getFromDateTime());
			if(null != error)errorObjects.add(error);
			
			// check to time
			error=DateTimeValidator.validateTimeField("To Time", iapPlanDestinationVo.getToDateTime());
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
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

	

}
