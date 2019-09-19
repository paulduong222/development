package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckAdjustmentDateRules extends AbstractTimeAssignAdjustSaveBatchRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.CHECK_ADJUSTMENT_DATE_BATCH.name();

	public CheckAdjustmentDateRules(ApplicationContext ctx)
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
		 * B.R. 6.0011
		 *    1.  If the user enters an activity date for an adjustment posting 
		 *        that is the same as or prior to the last date included on the 
		 *        last Original Invoice that was generated for the selected Resource,
		 *        the system must display a message indicating that the invoice was 
		 *        already generated and asking whether the user would like to continue. 
		 *        The buttons Yes and No are available. 
		 *        [Message 0146]
		 *      
		 *   1.1.	When the user selects Yes in response to action message 0146, 
		 *          the system must save the Adjustment for the Resource.
		 *   1.2.	When the user selects No in response to action message 0146, 
		 *          the system must not save the Adjustment for the Resource.
		 *        
		 */
		Boolean needPrompt=false;
		String lastInvoiceDate="";
		Date chkDate=null;
		
		if(DateTransferVo.hasDateString(timeAssignAdjustVo.getActivityDateVo())){
			chkDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
		}
		
		Collection<TimeAssignAdjustVo> vos = taaDao.getLastInvoiceDateConflicts(chkDate,super.crewIds);
		if(CollectionUtility.hasValue(vos)){
			needPrompt=true;
		}
		
		if(needPrompt){
			CourseOfActionVo coaVo = new CourseOfActionVo();

			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
			coaVo.setCustomPromptVo(new CustomPromptVo("INV_DATE_BATCH_CONFLICT","text.adjustments" ,"action.0146crew",vos));

			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
	
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getCustomPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getCustomPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.adjustments", "text.abortProcess" , new String[]{"post adjustment"}, MessageTypeEnum.INFO));
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
