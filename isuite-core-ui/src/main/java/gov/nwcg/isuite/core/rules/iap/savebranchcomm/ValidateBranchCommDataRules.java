package gov.nwcg.isuite.core.rules.iap.savebranchcomm;

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

public class ValidateBranchCommDataRules extends AbstractSaveBranchCommRule implements IRule {
	public static final String _RULE_NAME=SaveBranchCommRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
	public ValidateBranchCommDataRules(ApplicationContext ctx) {
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
		
		// check for function, only if not add blank line
		if (!super.iapBranchCommSummaryVo.getIsBlankLine()) {
			error=Validator.validateStringField2("Function", super.iapBranchCommSummaryVo.getSfunction(), 50, true);
		}
		
		if(null != error)errorObjects.add(error);
		
		/*
		if(!StringUtility.hasValue(super.iapBranchPersonnelVo.getRole())){
			ErrorObject error2 = new ErrorObject("error.800000","Position is a required field.");
			errorObjects.add(error2);
		}
		
		// check for incident resource
		if(null == super.iapBranchPersonnelVo.getIncidentResourceVo()
				&& !StringUtility.hasValue(super.iapBranchPersonnelVo.getResourceName())){
			ErrorObject error2 = new ErrorObject("error.800000","Select Resource or Resource Name is a required field.");
			errorObjects.add(error2);
		}
		*/
		
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


