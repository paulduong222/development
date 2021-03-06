package gov.nwcg.isuite.core.rules.incidentcostrate.ovhd;

import gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class OverwriteDefaultKindRatesRules extends AbstractIncidentCostRateOvhdSaveRule implements IRule{
	public static final String _RULE_NAME="OVERWRITE_DEFAULT_KIND_RATES";
	private Collection<Long> incidentIds = new ArrayList<Long>();

	public OverwriteDefaultKindRatesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		// no rules to apply here, return _ok
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME);
		coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);

		dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		
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
		IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		if(LongUtility.hasValue(super.incidentId)){
			Long igId=igDao.getIncidentGroupIdByIncidentId(super.incidentId);
			if(LongUtility.hasValue(igId)){
				incidentIds=igDao.getIncidentIdsInGroup(igId);
			}
		}else if(LongUtility.hasValue(super.groupId)){
			incidentIds=igDao.getIncidentIdsInGroup(super.groupId);
		}
		
		if(costRateCategory.equalsIgnoreCase("FED"))
		{
			this.updateFedOvhdRates(dialogueVo);
		}

		if(costRateCategory.equalsIgnoreCase("STATE_COOP"))
		{
			this.updateStateCoopOvhdRates(dialogueVo);
		}
		
		if(costRateCategory.equalsIgnoreCase("CONTRACTOR"))
		{
			this.updateContractorOvhdRates(dialogueVo);
		}
		
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int updateFedOvhdRates(DialogueVo dialogueVo) throws Exception {
		IncidentCostRateOvhdDao dao = (IncidentCostRateOvhdDao)context.getBean("incidentCostRateOvhdDao");
		
		/*
		 * Only update fed default rates if the rate is greater than 0.
		 * 
		 * Because some kind codes have both a subordinate and direct flag set to true,
		 * we want to make sure the subordinate rate has a higher priority over the direct rate.
		 * To accomplish this, we always update the rate table with the subordinate
		 * rate last, so that its value (if available) is the last rate saved for the kind code.
		 */

		// check direct rate
		if(vo.getDirectRate().doubleValue() > 0){
			dao.overwriteOvhdKindRates("DIRECT", vo);
			
			if(LongUtility.hasValue(super.groupId)){
				dao.overwriteOvhdKindRatesGroup("DIRECT", vo, groupId, incidentIds);
			}
		}
		
		// check indirect rate
		if(vo.getIndirectRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("INDIRECT", vo);

		// check subordinate rate
		if(vo.getSubordinateRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("SUBORDINATE", vo);
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int updateContractorOvhdRates(DialogueVo dialogueVo) throws Exception {
		
		IncidentCostRateOvhdDao dao = (IncidentCostRateOvhdDao)context.getBean("incidentCostRateOvhdDao");
		
		/*
		 * Only update contractor default rates if the rate is greater than 0.
		 * 
		 * The ovhd single and subordinate rates are basically:
		 *    Non Subordinate
		 *    &
		 *    Subordinate
		 * 
		 * The term 'single resource rate' == 'non subordinate resource rate'.
		 * 
		 */
		if(vo.getDirectRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("NONSUBORDINATE", vo);

		if(vo.getIndirectRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("SUBORDINATE2", vo);
		
		return _OK;
	}	

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int updateStateCoopOvhdRates(DialogueVo dialogueVo) throws Exception {
		
		IncidentCostRateOvhdDao dao = (IncidentCostRateOvhdDao)context.getBean("incidentCostRateOvhdDao");
		
		/*
		 * Only update state_coop default rates if the rate is greater than 0.
		 * 
		 * The ovhd single and subordinate rates are basically:
		 *    Non Subordinate
		 *    &
		 *    Subordinate
		 * 
		 * The term 'single resource rate' == 'non subordinate resource rate'.
		 * 
		 */
		
		if(vo.getDirectRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("NONSUBORDINATE", vo);

		if(vo.getIndirectRate().doubleValue() > 0)
			dao.overwriteOvhdKindRates("SUBORDINATE2", vo);
		
		return _OK;
	}	
	
}
