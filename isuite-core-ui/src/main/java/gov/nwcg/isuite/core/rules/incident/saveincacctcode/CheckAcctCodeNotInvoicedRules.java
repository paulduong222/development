package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckAcctCodeNotInvoicedRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_ACCT_CODE_NOT_INVOICED.name();

	public CheckAcctCodeNotInvoicedRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * B.R. 4.0002  Applies to Edit Incident Account Code
		 * 
		 * 		3.	If a user generated an Original Invoice for the selected Incident that 
		 *          includes an Accounting Code associated with that Incident, 
		 *          the system must notify the user that the Accounting Code was used on an 
		 *          Original Invoice, and allow the user to change the Accounting Code 
		 *          for that Incident.
		 * 
		 * The above rule is in conflict with:
		 *    If the user changes an Accounting Code that was included on an Original Invoice 
		 *    for the selected Incident, the system must display a message indicating that the 
		 *    Accounting Code was included on an Original Invoice and requesting the user to 
		 *    confirm the change. Yes and No buttons are available.[Message 0122]
		 *    
		 * 	1.1	If the user selects Yes in response to Message 0122, the system must save the changes to the Accounting Code.
		 *  1.2	If the user selects No in response to Message 0122, the system must not save the changes to the Accounting Code.
		 */
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
