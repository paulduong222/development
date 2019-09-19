package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckAssignDateRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME="DISABLED"; //IncidentResourceSaveRuleFactory.RuleEnum.CHECK_ASSIGN_DATE.name();

	public CheckAssignDateRules(ApplicationContext ctx)
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
		 * defect 3922: Assign Date is not updating properly if assign date is changed after posted time.
		 * Steps to recreate
		 * Select overhead resource
		 * Check in date = 2/1
		 * First Post Date = 2/26
		 * ---Run Cost
		 * E records correctly display from 2/1 - 2/25
		 * --Manually edit resource and change checkin date and assign date to 2/27 
		 * --Run Cost Results
		 * Cost records start on 2/27 (2/26 is not displaying
		 * Assign date remains 2/27
		 * --Expected Cost Run Results
		 * Cost Records should start on the first date of the actual posting 2/26
		 * Assign date shoudl be updated to 2/26
		 * Bottom line is that even if I manualy change the assign date to a date AFTER the first posting, when cost is run, the assign date should automatically be updated to the First Post date (or the earliest date with activity).
		 * 
		 * See NM Group O-5.4  Howard, Donald
		 * 
		 * validate the Assign Date and Check-in Date against the Activity Date for the Actual --> should check-in date be included?
		 * validate the Assign Date against the Activity Date for the Actual 
		 */
		
		Date assignDate = null;
		if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
			assignDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
		}
		
		//Date checkInDate = DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());		
		Boolean isAssignCheckInDateValid=true;
		Date aDate;
		
		Collection<IncidentResourceDailyCost> irDailyCosts;
		//activity dates for actual posting
		if (super.irEntity != null) {
			irDailyCosts = irEntity.getIncidentResourceDailyCosts();
			if (irDailyCosts != null) {
                for(IncidentResourceDailyCost irdc : irDailyCosts){
										
					aDate=DateUtil.addMilitaryTimeToDate(irdc.getActivityDate(), "2359");
					
					if (assignDate != null) {
						if ((irdc.getCostLevel().toString().equals("A")) && (assignDate.after(aDate))) {
							isAssignCheckInDateValid=false;
						}
					}
					
					//if (checkInDate != null) {
						//if ((irdc.getCostLevel().toString().equals("A")) && (checkInDate.after(aDate))) {
							//isAssignCheckInDateValid=false;
						//}
					//}
				}
			}
		}
		
		Collection<AssignmentTimePostVo> atps = vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAssignmentTimePostVos();
		//time posting dates
		if (atps != null) {
			for(AssignmentTimePostVo atp : atps){
								
				aDate=DateUtil.addMilitaryTimeToDate(atp.getPostStartDate(), "2359");
				
				if (assignDate != null) {
					if (assignDate.after(aDate)) {
						isAssignCheckInDateValid=false;
					}
				}
				
				//if (checkInDate != null) {
					//if (checkInDate.after(aDate)) {
						//isAssignCheckInDateValid=false;
					//}
				//}
			}			
		}
		
        if (!isAssignCheckInDateValid) {
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.incidentResources"
								,"info.generic"
								,new String[]{"The assign date is after the actual time posting date. \n\nEdited resource can not be saved"}
								,PromptVo._OK));
				
			dialogueVo.setCourseOfActionVo(coaVo);
		
			return _FAIL;				
		}     
        else {
        	return _OK;	
        }
			
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		
		
        /*
		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			//dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			//dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
						
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			coaVo.setIsDialogueEnding(true);
			//coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			//coaVo.setMessageVo(new MessageVo("text.incidentResource", "text.abortProcess" , new String[]{"save resource"}, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
		}	
		
		return _OK;
		*/
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_MSG_FINISHED);
		coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
		coaVo.setIsDialogueEnding(true);
		//coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		//coaVo.setMessageVo(new MessageVo("text.incidentResource", "text.abortProcess" , new String[]{"save resource"}, MessageTypeEnum.INFO));
		dialogueVo.setCourseOfActionVo(coaVo);

		return _FAIL;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}

