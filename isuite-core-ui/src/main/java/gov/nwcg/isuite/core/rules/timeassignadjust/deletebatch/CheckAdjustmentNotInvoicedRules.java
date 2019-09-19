package gov.nwcg.isuite.core.rules.timeassignadjust.deletebatch;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckAdjustmentNotInvoicedRules extends AbstractTimeAssignAdjustDeleteBatchRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustDeleteBatchRuleFactory.RuleEnum.CHECK_ADJUSTMENT_NOT_INVOICED.name();

	public CheckAdjustmentNotInvoicedRules(ApplicationContext ctx)
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
		 * B.R. 6.0011  Delete and Adjustment
		 *  
		 *   2.	If an adjustment posting was included on an Original Invoice, 
		 *      the system must not allow the user to delete that adjustment posting.
		 */
		if(null != super.timeAssignAdjustEntity){
			
			/*
			 * Is this timeAssignAdjustEntity included on an invoice?
			 */
			Boolean invoiced=false;
			for(TimeInvoice invoice : timeAssignAdjustEntity.getTimeInvoices()){
				if(BooleanUtility.isTrue(invoice.getIsInvoiceOnly()))
					invoiced=true;
			}
			if(invoiced==true){
				String errorMsg="You cannot delete an adjustment that has been included on an original invoice.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time"
												, "error.800000"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
				
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
