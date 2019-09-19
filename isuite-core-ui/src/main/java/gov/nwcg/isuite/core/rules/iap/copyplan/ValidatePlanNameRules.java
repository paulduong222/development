package gov.nwcg.isuite.core.rules.iap.copyplan;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.rules.iap.saveplan.SavePlanRuleFactory;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ValidatePlanNameRules extends AbstractCopyPlanRule implements IRule {
	public static final String _RULE_NAME=SavePlanRuleFactory.RuleEnum.VALIDATE_PLAN_NAME.name();
	
	public ValidatePlanNameRules(ApplicationContext ctx) {
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
		
		// check if name is all ready exist
		try {
			IapPlanDao iapPlanDao = (IapPlanDao)context.getBean("iapPlanDao");
			IapPlanVo.fixDateTimes(iapPlanDestinationVo);
			
			Collection<IapPlan> iaps = iapPlanDao.getAllIapPlansByPlanName(iapPlanDestinationVo);
			
			
			
			if(iaps != null && !iaps.isEmpty()) {
				String dname = iapPlanDestinationVo.getFromDateString() + " - " + iapPlanDestinationVo.getToDateString() + "" +
				(StringUtility.hasValue(iapPlanDestinationVo.getOperationalPeriod()) ? " " + iapPlanDestinationVo.getOperationalPeriod().toUpperCase() : "") +" - "+iapPlanDestinationVo.getIncidentName();
				
				//error = new ErrorObject(ErrorEnum._0367_DUPLICATE_IAP_PLANNAME.getErrorName(),dname);
				error = new ErrorObject(ErrorEnum.DUPLICATE_IAP_PLANNAME,dname);
				errorObjects.add(error);
			}
			
		} catch (Exception e) {
			error = new ErrorObject("Verify plan name failed due to the database error.","");
			errorObjects.add(error);
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
