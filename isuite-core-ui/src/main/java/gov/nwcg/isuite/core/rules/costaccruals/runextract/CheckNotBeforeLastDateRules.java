package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckNotBeforeLastDateRules extends AbstractRunExtractRule implements IRule {
	public static final String _RULE_NAME=RunExtractRuleFactory.RuleEnum.CHECK_NOT_BEFORE_LAST_DATE.name();
	
	public CheckNotBeforeLastDateRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
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
		 * Check if there if this date is before last finalized date 
		 */
		CostAccrualExtract extract = dao.getLastFinalizedExtract(incidentId, incidentGroupId);
		if(null != extract){
			Date caeDate=extract.getExtractDate();
			caeDate=DateUtil.addMilitaryTimeToDate(caeDate, "2359");
			
			Date accDate=super.extractDate;
			extractDate=DateUtil.addMilitaryTimeToDate(accDate, "2359");
			
			if(accDate.before(caeDate)){
				String msg="The Cost Accrual Extract date cannot be before the last finalized Extract Date.";
			
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.accrualExtract"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{msg}));	

				return _FAIL;
			}
			
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
