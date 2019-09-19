package gov.nwcg.isuite.core.rules.timeassignadjust.save;

import java.util.Date;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class CheckAdjustmentDateRules extends AbstractTimeAssignAdjustSaveRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveRuleFactory.RuleEnum.CHECK_ADJUSTMENT_DATE.name();

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
		if(null != assignmentEntity && null != assignmentEntity.getAssignmentTime()){
			for(AssignmentTimePost atp : assignmentEntity.getAssignmentTime().getAssignmentTimePosts()){
				TimeInvoice lastInvoice = null;
				for(TimeInvoice t : atp.getTimeInvoices()){
					if(!DateUtil.hasValue(t.getDeletedDate())){
						if(null == lastInvoice)
							lastInvoice = t;
						else{
							if(t.getId().compareTo(lastInvoice.getId())>0){
								lastInvoice=t;
							}
						}
					}
				}

				if(null != lastInvoice){
					lastInvoiceDate = DateUtil.toDateString(lastInvoice.getLastIncludeDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
				}
				
				/*
				 * Check timePostAdjustVo.activityDAte against lastInvoice.lastDateIncluded.
				 * Check if activityDate is equal to, or before lastInvoice.lastDateIncluded.
				 */
				if((null != lastInvoice) && DateUtil.hasValue(lastInvoice.getLastIncludeDate()))
					if(DateTransferVo.hasDateString(timeAssignAdjustVo.getActivityDateVo())){
						Date chkDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
						
						if(chkDate.before(lastInvoice.getLastIncludeDate()))
							needPrompt=true;
					}
				
				if((null != lastInvoice) && DateUtil.hasValue(lastInvoice.getLastIncludeDate())){
					Date chkDate=DateTransferVo.getTransferDate(timeAssignAdjustVo.getActivityDateVo());
					if(DateUtil.isSameDate(chkDate
							, lastInvoice.getLastIncludeDate()))
						needPrompt=true;
					
				}
			}
			
		}

		if(needPrompt){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.adjustments","action.0146",new String[]{lastInvoiceDate},PromptVo._YES | PromptVo._NO));
			coaVo.getPromptVo().setYesLabel("YES");
			coaVo.getPromptVo().setNoLabel("NO");
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

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
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
