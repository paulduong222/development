package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import java.util.Date;

import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class CheckPostStartIncidentDateRules extends AbstractTimeAssignAdjustSaveBatchRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.CHECK_POST_START_INCIDENT_DATE.name();

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
	private int runRuleCheck(DialogueVo dialogueVo) {
		try{
			/*
			 * B.R. 
			 * 	Time Posting start date cannot be before incident start date.
			 */
			Date startDate = null;
			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			
			if(DateTransferVo.hasDateString(timeAssignAdjustVo.getActivityDateVo())){
				startDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
			}
			
			if(null != startDate) {
				//Date incidentStartDate=super.taaDao.getIncidentStartDate(super.timeAssignAdjustVo.getAssignmentId());
				Date incidentStartDate=null;
				
				try{
					incidentStartDate=incidentDao.getEarliestIncStartDateResource(
							irvo.getIncidentVo().getId(),null);
				}catch(Exception e){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{"Error validating earliest incident start date. AdjustBatch - CheckPostStartIncidentDateRules.java"}));	
					return _FAIL;
				}
				
					String sDate=DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					String iDate=DateUtil.toDateString(incidentStartDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					startDate=DateUtil.toDate(sDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					incidentStartDate=DateUtil.toDate(iDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					if(startDate.before(incidentStartDate)){
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.time"
													  ,"validationerror"
													  ,"error.800000"
													  , new String[]{"Adjustment date cannot be before incident start date."}));	
						return _FAIL;
					}
				
			}
			
		}catch(Exception e){
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.800000"
										  , new String[]{e.getMessage()}));	
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
