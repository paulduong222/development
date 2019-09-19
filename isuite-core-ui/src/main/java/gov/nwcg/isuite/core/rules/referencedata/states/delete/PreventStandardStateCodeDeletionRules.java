package gov.nwcg.isuite.core.rules.referencedata.states.delete;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class PreventStandardStateCodeDeletionRules extends AbstractDeleteStateRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataDeleteStateRuleFactory.RuleEnum.PREVENT_STANDARD_STATE_CODE_DELETION.name();

	public PreventStandardStateCodeDeletionRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*H.	16.0008- Manage State Codes
		Use Case ID: 16.0008	Goal: Manage Reference Data for State Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
		Delete a State Code
		
		3.	The system must prevent a user from deleting Standard States.*/

		//TODO:  Implement rule
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
