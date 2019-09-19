package gov.nwcg.isuite.core.rules.timeassignadjust.save;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractTimeAssignAdjustSaveRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveRuleFactory.RuleEnum.VALIDATE_DATA.name();

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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		// check for activityDate
		if(DateTransferVo.hasDateString(timeAssignAdjustVo.getActivityDateVo())){
			Date chkDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
			error=Validator.validateDateField2("Activity Date",chkDate, true);
			if(null != error)errorObjects.add(error);
		}
		
		// check for category
		if(super.assignmentEntity.getAssignmentTime().getEmploymentType()==EmploymentTypeEnum.AD){
			error=Validator.validateVoField2("Category",timeAssignAdjustVo.getAdjustCategoryVo(), true);
			if(null != error)errorObjects.add(error);
		}
		
		if(super.assignmentEntity.getAssignmentTime().getEmploymentType()!=EmploymentTypeEnum.AD){
			// check for adjustment type
			if(null == timeAssignAdjustVo.getAdjustmentType()
					|| !StringUtility.hasValue(timeAssignAdjustVo.getAdjustmentType().toString())){
				error = new ErrorObject("text.requiredFieldError","Adjustment Type");
				if(null != error)errorObjects.add(error);
			}
		}
		
		// check for description
		error=Validator.validateStringField2("Description", timeAssignAdjustVo.getCommodity(), 90, true);
		if(null != error)errorObjects.add(error);
		
		// check for amount
		error=Validator.validateDecimalField2("Amount", timeAssignAdjustVo.getAdjustmentAmount(), true);
		if(null != error)errorObjects.add(error);
		
		/// check for accounting code
		error=Validator.validateVoField2("Accounting Code",timeAssignAdjustVo.getIncidentAccountCodeVo(), true);
		if(null != error)errorObjects.add(error);
		
		// verify there is a checkin date
		/*
		if(!DateUtil.hasValue(irvo.getWorkPeriodVo().getCiCheckInDate())){
			error = new ErrorObject("info.generic","Resource must have a Check-In Time before posting Adjustments");
			errorObjects.add(error);
		}
		*/
		
		// check activity date not before checkin date
		/*
		if(DateUtil.hasValue(timeAssignAdjustVo.getActivityDate())){
			if(DateUtil.hasValue(irvo.getWorkPeriodVo().getCiCheckInDate())){
				error=DateTimeValidator.validateDate1NotBeforeDate2(
						timeAssignAdjustVo.getActivityDate()
						,irvo.getWorkPeriodVo().getCiCheckInDate()
						, "Adjustment Activity Date", "Check-In Date");
				if(error!=null)errorObjects.add(error);
			}
		}
		*/
		
		// check activity date not before incdient start date
		if(DateUtil.hasValue(timeAssignAdjustVo.getActivityDate())){
			if(DateUtil.hasValue(irvo.getIncStartDate())){
				error=DateTimeValidator.validateDate1NotBeforeDate2(
						timeAssignAdjustVo.getActivityDate()
						,irvo.getIncStartDate()
						, "Adjustment Activity Date", "Incident Start Date");
				if(error!=null)errorObjects.add(error);
			}
		}
		
		// check activity date not after demob date
		if(DateUtil.hasValue(timeAssignAdjustVo.getActivityDate())){
			String status="";
			if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo()
					&& null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo()
					&& StringUtility.hasValue(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode())){
				status=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode();
			}
			if(StringUtility.hasValue(status) && status.equals("D")){
				Date releaseDate=DateTransferVo.getDate(irvo.getWorkPeriodVo().getDmReleaseDateVo());
				if(DateUtil.hasValue(releaseDate)){
					error=DateTimeValidator.validateDate1NotAfterDate2(
							timeAssignAdjustVo.getActivityDate()
							,releaseDate
							, "Adjustment Activity Date", "Demob Date");
					if(error!=null)errorObjects.add(error);
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
