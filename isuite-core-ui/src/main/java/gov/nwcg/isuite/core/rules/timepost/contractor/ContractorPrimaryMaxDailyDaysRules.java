package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ContractorPrimaryMaxDailyDaysRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_PRIMARY_MAX_DAILY_DAYS";

	public ContractorPrimaryMaxDailyDaysRules(ApplicationContext ctx){
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Determine if time posting daily days exceeds 5 days
		 */
		if((postType.equals("PRIMARY") || postType.equals("BOTH"))
				&& getPrimaryUnitOfMeasure().equals("DAILY")){
							
			Date startDate=vo.getPostStartDate();
			Date stopDate=vo.getPostStopDate();
							
			if(null != startDate && null != stopDate) {

				long diffDays=DateUtil.diffDays(startDate, stopDate)+1;
				if(diffDays > 5){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{"Primary daily days cannot exceed more than 5 days in a single time posting"}));	
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
