package gov.nwcg.isuite.core.rules.iap.savebranchposition;

import gov.nwcg.isuite.core.persistence.BranchSettingPositionDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateBranchPositionDataRules extends AbstractSaveBranchPositionRule implements IRule {
	public static final String _RULE_NAME=SaveBranchPositionRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
	public ValidateBranchPositionDataRules(ApplicationContext ctx) {
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;

		// check for branchSettingId
		if(!LongUtility.hasValue(super.branchSettingId)){
			// error, must select a branch
			ErrorObject error2 = new ErrorObject("error.800000","A Branch must be selected.");
			errorObjects.add(error2);
		}

		// check for position
		error=Validator.validateStringField2("Position", super.branchPositionVo.getPosition(), 200, true);
		if(null != error)errorObjects.add(error);

		// check for at least one kind
		if(!CollectionUtility.hasValue(super.branchPositionVo.getKindVos())){
			// error, at least one kind is required
			ErrorObject error2 = new ErrorObject("error.800000","At least 1 Item Code must be selected.");
			errorObjects.add(error2);
		}
			
		if(BooleanUtility.isTrue(super.branchPositionVo.getIsNew())){
			if(LongUtility.hasValue(super.branchSettingId) && StringUtility.hasValue(super.branchPositionVo.getPosition())){
				BranchSettingPositionDao dao = (BranchSettingPositionDao)context.getBean("branchSettingPositionDao");
				// check for duplicate Position
				Boolean isduplicate=dao.isDuplicatePosition(super.branchSettingId, super.branchPositionVo.getPosition());
				if(isduplicate==true){
					ErrorObject error2 = new ErrorObject("error.800000","The position is already defined for the branch.");
					errorObjects.add(error2);
				}
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
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}


