package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckSpecialStartIncidentDateRules extends AbstractContractorRule implements IRule{
	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.CHECK_SPECIAL_POST_START_INCIDENT_DATE.name();

	public CheckSpecialStartIncidentDateRules(ApplicationContext ctx)
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
		/*
		 * B.R. 
		 * 	Time Posting start date cannot be before incident start date.
		 */
		if(postType.equals("SPECIAL")){
							
			Date startDate=specialVo.getPostStartDate();
			Date incidentStartDate=null;

			if(null != incidentResourceEntity)
				incidentStartDate = incidentResourceEntity.getIncident().getIncidentStartDate();
			
			if(null != startDate && null != incidentStartDate) {
				try{
					startDate = DateUtil.addMilitaryTimeToDate(startDate, "2359");
					incidentStartDate = DateUtil.addMilitaryTimeToDate(incidentStartDate, "2359");
				}catch(Exception e){
					
				}

				if(startDate.before(incidentStartDate)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{"Special start date cannot be before incident start date."}));	
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
