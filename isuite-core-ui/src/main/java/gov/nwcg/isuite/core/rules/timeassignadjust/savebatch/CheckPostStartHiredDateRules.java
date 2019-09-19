package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPostStartHiredDateRules extends AbstractTimeAssignAdjustSaveBatchRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.CHECK_POST_START_HIRED_DATE.name();

	public CheckPostStartHiredDateRules(ApplicationContext ctx)
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

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
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
				
			}
			
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
		
		/*
		 * Defect 4001
		 * 
			Scenario Testing March 2014
			The user should not be allowed to post equipment time with a date prior to the hired date.  
			Need a validation error message when is attempted.		
		 */
		Date hiredDate=null;
		Date startDate = null;
		Boolean isContractor=false;
		
		if(null != assignmentEntity)
		{
			if(null != assignmentEntity.getAssignmentTime()){
				
				if(null != assignmentEntity.getAssignmentTime().getEmploymentType()
						&& assignmentEntity.getAssignmentTime().getEmploymentType()==EmploymentTypeEnum.CONTRACTOR)
				{
					isContractor=true;
					
					if(null != assignmentEntity.getAssignmentTime().getContractorPaymentInfo()
							&& null != assignmentEntity.getAssignmentTime().getContractorPaymentInfo().getHiredDate()){
						hiredDate=assignmentEntity.getAssignmentTime().getContractorPaymentInfo().getHiredDate();
						if(DateUtil.hasValue(hiredDate))
							hiredDate=DateUtil.addMilitaryTimeToDate(hiredDate, "2359");
					}
				}
			}
		}
		
		if(isContractor==true){
			if(DateTransferVo.hasDateString(timeAssignAdjustVo.getActivityDateVo()))
				startDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
			//if(DateUtil.hasValue(timeAssignAdjustVo.getActivityDate())){
			//	startDate=super.timeAssignAdjustVo.getActivityDate();
				if(DateUtil.hasValue(startDate))
					startDate=DateUtil.addMilitaryTimeToDate(startDate, "2359");
			//}
			
			if(DateUtil.hasValue(startDate) && DateUtil.hasValue(hiredDate)){
				if(startDate.before(hiredDate)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{"Activity date cannot be before hired date."}));	
					return _FAIL;
				}
				
			}
		}
		
		return _OK;
	}
	
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			coaVo.setIsDialogueEnding(true);
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
