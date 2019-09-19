package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPostStartIncidentDateRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_POST_START_INCIDENT_DATE.name();

	public CheckPostStartIncidentDateRules(ApplicationContext ctx)
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
		 * 	Time Posting start date cannot be before incident start date.
		 */
		Date incidentStartDate=null;
		Date startDate = super.vo.getPostStartDate();
		IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		
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
		
		if(null != incident){
			//incidentStartDate = incident.getIncidentStartDate();
			try{
				incidentStartDate=incidentDao.getEarliestIncStartDateResource(
						incident.getId(),null);
			}catch(Exception e){
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.time"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{"Error validating earliest incident start date. Crew - CheckPostStartIncidentDateRules.java"}));	
				return _FAIL;
			}
			
		}
		
		if(null != startDate && null != incidentStartDate) {

			/*
			try{
				startDate=DateUtil.addMilitaryTimeToDate(startDate, "2359");
				incidentStartDate=DateUtil.addMilitaryTimeToDate(incidentStartDate, "2359");
			}catch(Exception e){
			}
			*/
			String sDate=DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String iDate=DateUtil.toDateString(incidentStartDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);

			startDate=DateUtil.toDate(sDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			incidentStartDate=DateUtil.toDate(iDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			
			if(startDate.before(incidentStartDate)){
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.time"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{"Post start date cannot be before incident start date."}));	
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
