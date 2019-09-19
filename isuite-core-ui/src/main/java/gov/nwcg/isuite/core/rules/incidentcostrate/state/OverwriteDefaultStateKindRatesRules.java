package gov.nwcg.isuite.core.rules.incidentcostrate.state;

import gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class OverwriteDefaultStateKindRatesRules extends AbstractIncidentCostRateStateSaveRule implements IRule{
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
		IncidentCostRateStateDao dao = (IncidentCostRateStateDao)context.getBean("incidentCostRateStateDao");

		Collection<Long> incidentIds = new ArrayList<Long>();
		IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
		IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		Long igid = 0L;
		if(LongUtility.hasValue(super.incidentId)){
			// is incident part of group
			igid = iDao.getIncidentGroupId(super.incidentId);
			if(LongUtility.hasValue(igid))
				incidentIds = igDao.getIncidentIdsInGroup(igid);
		}
		
		if(LongUtility.hasValue(super.groupId)){
			incidentIds = igDao.getIncidentIdsInGroup(super.groupId);
		}
		
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

		// check single rate
		/* 12/7/2012
		if(vo.getSingleRate().doubleValue() > 0)
			dao.overwriteStateOvhdKindRates("NONSUBORDINATE", vo);
		*/
		if(vo.getDirectRate().doubleValue() > 0)
			dao.overwriteStateOvhdKindRates("NONSUBORDINATE", vo, super.groupId, incidentIds);
		
		if(vo.getInDirectRate().doubleValue() > 0) 
			dao.overwriteStateOvhdKindRates("SUBORDINATE2", vo, super.groupId, incidentIds);
		
		
	}

}
