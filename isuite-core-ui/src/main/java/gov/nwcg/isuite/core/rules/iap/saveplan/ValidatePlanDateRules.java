package gov.nwcg.isuite.core.rules.iap.saveplan;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;


public class ValidatePlanDateRules extends AbstractSavePlanRule implements IRule {
	public static final String _RULE_DATE=SavePlanRuleFactory.RuleEnum.VALIDATE_PLAN_DATE.name();
	
	public ValidatePlanDateRules(ApplicationContext ctx) {
		super(ctx, _RULE_DATE);
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
		
		// check if end date is before start date
		
		Date startDate = null;
		Date endDate = null;
		
		startDate = this.iapPlanVo.getFromDate();
		endDate = this.iapPlanVo.getToDate();
		
		if (null != startDate && null != endDate){
			if(endDate.before(startDate)){
				error = new ErrorObject("error.800000"
										,"The plan end date (" + DateUtil.toDateString(endDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM) + ") cannot be before the plan start date (" + DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM) + ")" );
				//error = new ErrorObject("error.800000","The plan end date\time (" + endDate.toString() + ") can not be before the plan start date\time (" + startDate.toString() + ")" );
				//error = new ErrorObject(ErrorEnum.IAP_PLAN_UNABLE_TO_SAVE_SELECTED_DATES,);
				errorObjects.add(error);
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


