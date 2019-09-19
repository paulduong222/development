package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckAgencyRules extends AbstractSaveDailyCostRule implements IRule {
	public static final String _RULE_NAME=SaveDailyCostRuleFactory.RuleEnum.CHECK_AGENCY.name();

	public CheckAgencyRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
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
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * Validate the necessary data is available for processing daily costs for
		 * the resource.
		 * 1) 13.	If there is no Agency defined for a Resource and there are no Actual Costs 
		 * 			for that Resource (i.e., no Time Postings), the system must not create any 
		 * 			Cost records for that Resource.
		 * 			[Message 0270] Reasons for no generation include:
 		 *				No Agency defined for the Resource
		 */
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
