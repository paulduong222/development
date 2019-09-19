package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckSkippedDateRules extends AbstractRunExtractRule implements IRule {
	public static final String _RULE_NAME=""; //RunExtractRuleFactory.RuleEnum.CHECK_SKIPPED_DATE_PROMPT.name();
	
	public CheckSkippedDateRules(ApplicationContext ctx)
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
		 * Check if there are skipped dates
		 */
		int cnt=dao.getExtractCountBeforeDate(incidentId, null, extractDate);
		if(cnt > 0){
			Collection<CostAccrualExtract> extracts = dao.getExtractsBeforeDate(incidentId, null, extractDate, "ASC");
			int i =0;
			
			Date skippedDate=null;
			
			for(CostAccrualExtract cae : extracts){
				dao.flushAndEvict(cae);
				
				if(i==0){
					// set the first extract date for incident
					skippedDate=DateUtil.addDaysToDate(cae.getExtractDate(), 1);
				}else{
					Date caeDate=cae.getExtractDate();
					if(DateUtil.isSameDate(skippedDate, caeDate)){
						skippedDate=DateUtil.addDaysToDate(skippedDate, 1);
					}else{
						break;
					}
				}
				
				i++;
			}

			if(skippedDate != null && !DateUtil.isSameDate(extractDate,skippedDate)){
				String msg="The Cost Accrual Extract for " + DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + " cannot be processed yet.  The system detected " +
					" the Accrual Extract for " + DateUtil.toDateString(skippedDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + " "+
				   "was skipped and has not been completed.  \n\nWould you like the system to instead run the Accrual Extract for " + DateUtil.toDateString(skippedDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + "?";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setStoredObject(DateUtil.toDateString(skippedDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));
				coaVo.setPromptVo(new PromptVo("text.costAccruals"
						,"info.generic"
						,new String[]{msg}
						,PromptVo._YES | PromptVo._NO));
						
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
			
		}
		return _OK;
	}

	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costAccruals", "text.abortProcess" , new String[]{"Run Extract"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
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
