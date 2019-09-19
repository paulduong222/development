package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.VALIDATE_DATA.name();

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
		
		// check for event type
		error=Validator.validateVoField2("Event Type",vo.getEventTypeVo(), true);
		if(null != error)errorObjects.add(error);
		
		// check incidentName field for required/length
		error=Validator.validateStringField2("Incident Name", vo.getIncidentName(), 50, true);
		if(null != error)errorObjects.add(error);

		// check for unit id
		error=Validator.validateVoField2("Unit ID",vo.getHomeUnitVo(), true);
		if(null != error)errorObjects.add(error);

		// check for number
		error=Validator.validateStringField2("Incident Number", vo.getIncidentNumber(), 10, true);
		if(null != error)errorObjects.add(error);

		// check for Incident Jurisdiction
		error=Validator.validateVoField2("Incident Jurisdiction",vo.getAgencyVo(), true);
		if(null != error)errorObjects.add(error);

		// check for Incident State
		error=Validator.validateVoField2("State",vo.getCountryCodeSubdivisionVo(), true);
		if(null != error)errorObjects.add(error);
		
		// check for start date
		Date incStartDate=null;
		if(DateTransferVo.hasDateString(vo.getIncStartDateTransferVo())){
			incStartDate=DateTransferVo.getDate(vo.getIncStartDateTransferVo());
		}
		error=Validator.validateDateField2("Start Date",incStartDate, true);
		if(null != error)errorObjects.add(error);

		// check for description
		error=Validator.validateStringField2("Description", vo.getIncidentDescription(), 1024, false);
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
