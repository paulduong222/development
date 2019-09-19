package gov.nwcg.isuite.core.rules.user.resolvefailedimports;

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

import org.springframework.context.ApplicationContext;

public class ValidateResolveFailedImportsFormDataRules extends AbstractFailedImportsRule implements IRule {

	public ValidateResolveFailedImportsFormDataRules(ApplicationContext ctx, String rName) {
		super(ctx, rName);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		error=Validator.validateStringField2("User Name", validUserVo.getLoginName(), 30, true);
		if(null != error)errorObjects.add(error);
		
		error=Validator.validateStringField2("First Name", validUserVo.getFirstName(), 30, true);
		if(null != error)errorObjects.add(error);
		
		error=Validator.validateStringField2("Last Name", validUserVo.getLastName(), 30, true);
		if(null != error)errorObjects.add(error);
		
		error=Validator.validateVoField2("Unit ID", validUserVo.getHomeUnitVo(), true);
		if(null != error)errorObjects.add(error);
		
		//error=Validator.validateVoField2("Primary Dispatch Center", validUserVo.getPrimaryDispatchCenterVo(), true);
		///if(null != error)errorObjects.add(error);
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.getErrorObjectVos().addAll(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
