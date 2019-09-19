package gov.nwcg.isuite.core.rules.trainingspecialist.save;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class ValidateDataRules extends AbstractSaveResourceTrainingRule implements IRule {
	public static final String _RULE_NAME=ResourceTrainingSaveRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
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
		
		error = Validator.validateVoField2("Resource", super.resourceTrainingVo.getIncidentResourceVo(), true);
		if(null != error)errorObjects.add(error);
		
		error = Validator.validateVoField2("Trainee Assignment", super.resourceTrainingVo.getKindVo(), true);
		if(null != error)errorObjects.add(error);
		
		error = Validator.validateDateField2("Assignment Start Date", DateTransferVo.getDate(resourceTrainingVo.getStartDateTransferVo()), true);
		if(null != error)errorObjects.add(error);
		
		if(BooleanUtility.isTrue(super.resourceTrainingVo.getFsPriorityTrainee()) 
				&& null == super.resourceTrainingVo.getPriorityProgramVo() ){
			error = new ErrorObject("text.requiredFieldError","Priority Program");
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
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
	}

}
