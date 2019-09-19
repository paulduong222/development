package gov.nwcg.isuite.core.rules.datatransfer.exportdata;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractExportDataRule implements IRule {
	public static final String _RULE_NAME = ExportDataRuleFactory.RuleEnum.VALIDATE_DATA.name();

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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;

		// check for incident or group id
		if(!LongUtility.hasValue(vo.getIncidentId()) && !LongUtility.hasValue(vo.getIncidentGroupId())){
			ErrorObject error2 = new ErrorObject("error.800000","Incident or Incident Group selection is required.");
			errorObjects.add(error2);
		}
		
		// check filename
		error=Validator.validateStringField2("Transition Filename", super.vo.getFilename(), 200, true);
		if(null!=error)errorObjects.add(error);
		
		// check password
		error=Validator.validateStringField2("Password", super.vo.getFilePassword(), 200, true);
		if(null!=error)errorObjects.add(error);
		
		// check confirm password
		error=Validator.validateStringField2("Confirm Password", super.vo.getConfirmFilePassword(), 200, true);
		if(null!=error)errorObjects.add(error);
		
		// check password same as confirm password
		if(StringUtility.hasValue(vo.getFilePassword()) && StringUtility.hasValue(vo.getConfirmFilePassword())){
			if(!vo.getFilePassword().equals(vo.getConfirmFilePassword())){
				ErrorObject error2 = new ErrorObject("error.800000","Password and Confirm password do not match.");
				errorObjects.add(error2);
			}
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
