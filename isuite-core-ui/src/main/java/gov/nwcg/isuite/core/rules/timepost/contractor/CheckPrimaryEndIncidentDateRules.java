package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPrimaryEndIncidentDateRules extends AbstractContractorRule implements IRule{
	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.CHECK_PRIMARY_POST_END_INCIDENT_DATE.name();

	public CheckPrimaryEndIncidentDateRules(ApplicationContext ctx)
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
		/*
		 * B.R. 
		 * 	Time Posting end date cannot be after incident end date.
		 */
		if(postType.equals("PRIMARY") || postType.equals("BOTH") || postType.equals("GUARANTEE")){
							
			Date endDate=vo.getPostStopDate();
			Date incidentEndDate=null;
			
			if(null != incidentResourceEntity)
				incidentEndDate = incidentResourceEntity.getIncident().getIncidentEndDate();
			
			if(null != endDate && null != incidentEndDate) {
				
				String sDate=DateUtil.toDateString(endDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				String iDate=DateUtil.toDateString(incidentEndDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);

				endDate=DateUtil.toDate(sDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				incidentEndDate=DateUtil.toDate(iDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				
				
				if(endDate.after(incidentEndDate)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{"Primary stop date cannot be after incident end date."}));	
					return _FAIL;
				}
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
