package gov.nwcg.isuite.core.rules.iap.copyform;

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
import gov.nwcg.isuite.framework.util.Validator;

public class ValidateDataRules extends AbstractCopyFormRule implements IRule {
	public static final String _RULE_NAME=CopyFormRuleFactory.RuleEnum.VALIDATE_DATA.name();

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
		error = Validator.validateVoField2("Copy Form From", iapCopyVo.getIapPlanSourceVo(), true);
		if(null != error)errorObjects.add(error);
		
		//check forms
		Boolean hasForms = false;
		
		if(CollectionUtility.hasValue(iapCopyVo.getIapForm202Ids()) 
				|| CollectionUtility.hasValue(iapCopyVo.getIapForm203Ids())
				|| CollectionUtility.hasValue(iapCopyVo.getIapForm204Vos())
				|| CollectionUtility.hasValue(iapCopyVo.getIapForm205Ids())
				|| CollectionUtility.hasValue(iapCopyVo.getIapForm206Ids())
				|| CollectionUtility.hasValue(iapCopyVo.getIapForm220Ids())) {
			
			hasForms = true;
		}
		
		if(!hasForms) {
			errorObjects.add(new ErrorObject("info.generic","At least 1 form must be selected."));
		}
		
		
		// check plan destination
		error = Validator.validateVoField2("Copy Form To", iapCopyVo.getIapPlanDestinationVo(), true);
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
