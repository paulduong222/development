package gov.nwcg.isuite.core.rules.iap.saveform204;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
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

public class Validate204DataRules extends AbstractSaveForm204Rule implements IRule {
	public static final String _RULE_NAME=SaveForm204RuleFactory.RuleEnum.VALIDATE_204_DATA.name();
	
	public Validate204DataRules(ApplicationContext ctx) {
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

		// validate branch
		error=Validator.validateStringField2("Branch", iapForm204Vo.getBranchName(), 50, false);
		if(null != error)errorObjects.add(error);
		
		// validate division
		error=Validator.validateStringField2("Division/Group", iapForm204Vo.getDivisionName(), 50, true);
		if(null != error)errorObjects.add(error);

		// validate uniqueness for branch / division
		String branch=(StringUtility.hasValue(super.iapForm204Vo.getBranchName()) ? super.iapForm204Vo.getBranchName() : "");
		String division=(StringUtility.hasValue(super.iapForm204Vo.getDivisionName()) ? super.iapForm204Vo.getDivisionName() : "");

		IapBranchDao dao = (IapBranchDao)context.getBean("iapBranchDao");
		IapBranch iapBranchEntity = dao.getByBranchDivision(StringUtility.toUpper(branch)
															, StringUtility.toUpper(division)
															, super.iapForm204Vo.getIapPlanId()
															, (LongUtility.hasValue(super.iapForm204Vo.getId()) ? iapForm204Vo.getId() : null));

		if(null != iapBranchEntity){
			dao.flushAndEvict(iapBranchEntity);
			
			// return error
			ErrorObject error2 = new ErrorObject("error.800000","Branch/Division Name must be unique to the IAP Plan.");
			errorObjects.add(error2);
		}
		
		// validate workassignments
		//error=Validator.validateStringField2("Work Assignments", iapForm204Vo.getWorkAssignment(), 4000, false);
		//if(null != error)errorObjects.add(error);
		
		// validate special instructions
		//error=Validator.validateStringField2("Special Instructions", iapForm204Vo.getSpecialInstructions(), 4000, false);
		//if(null != error)errorObjects.add(error);

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


