package gov.nwcg.isuite.core.rules.syscostrate.state;

import gov.nwcg.isuite.core.persistence.SysCostRateStateDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class OverwriteDefaultStateKindRatesRules extends AbstractSysCostRateStateSaveRule implements IRule{
	public static final String _RULE_NAME="OVERWRITE_DEFAULT_STATE_KIND_RATES";

	public OverwriteDefaultStateKindRatesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * if rule check has been completed, return
		 */
		if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
			return _OK;

		// No rules to implement here
		dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * PER - 10.0009 - Manage Global Cost Rates
		 * 
		 * When the user enters a Direct, Indirect and Subordinate Fed rate, 
		 * the system must update all Overhead Item Code rates with the rates 
		 * that are defined 
		 * (based on whether an item code is a Direct, Indirect or Subordinate Item Code).
		 * 
		 *  EXAMPLE:
		 *     If a user enters 
		 *     a Direct Rate of $45.00, 
		 *     an Indirect Rate of $35.00 
		 *     and a Subordinate Rate of $45.00, 
		 *     the system would automatically change the rate 
		 *     for all Direct Item Codes to $45.00, 
		 *     Indirect Item Codes to $35.00 
		 *     and Subordinate Item Codes to $45.00.
		 */
		SysCostRateStateDao dao = (SysCostRateStateDao)context.getBean("sysCostRateStateDao");

		/*
		 * Only update state_coop_custom default rates if the rate is greater than 0.
		 * 
		 * The single and subordinate rates are basically:
		 *    Non Subordinate
		 *    &
		 *    Subordinate
		 * 
		 * The term 'single resource rate' == 'non subordinate resource rate'.
		 * 
		 */

		if(vo.getDirectRate().doubleValue() > 0)
			dao.overwriteStateOvhdKindRates("NONSUBORDINATE", vo);
		
		if(vo.getInDirectRate().doubleValue() > 0)
			dao.overwriteStateOvhdKindRates("SUBORDINATE2", vo);
		
		
	}

}
