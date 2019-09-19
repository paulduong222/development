package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPostEndIncidentDateRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_POST_END_INCIDENT_DATE.name();

	public CheckPostEndIncidentDateRules(ApplicationContext ctx)
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception{
		Boolean bPassed=false;
		
		/*
		 * B.R. 
		 * 	Time Posting end date cannot be after incident end date.
		 */
		Date incidentEndDate=null;
		Date endDate = super.vo.getPostStopDate();
		
		AssignmentTime at = null;
		for(AssignmentTime atEntity : super.entities){
			at = atEntity;
			break;
		}
		
		Incident incident =null;
		WorkPeriod wp=at.getAssignment().getWorkPeriods().iterator().next();
		if(null != wp){
			IncidentResource ir= wp.getIncidentResource();
			incident=ir.getIncident();
		}
		
		if(null != incident)
			incidentEndDate = incident.getIncidentEndDate();
		
		if(null == endDate)
			endDate = super.vo.getPostStartDate();
		
		if(null != endDate && null != incidentEndDate) {

			/*
			try{
				startDate=DateUtil.addMilitaryTimeToDate(startDate, "2359");
				incidentStartDate=DateUtil.addMilitaryTimeToDate(incidentStartDate, "2359");
			}catch(Exception e){
			}
			*/
			String sDate=DateUtil.toDateString(endDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String iDate=DateUtil.toDateString(incidentEndDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);

			endDate=DateUtil.toDate(sDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			incidentEndDate=DateUtil.toDate(iDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			
			if(endDate.after(incidentEndDate)){
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.time"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{"Post stop date cannot be after incident end date."}));	
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
