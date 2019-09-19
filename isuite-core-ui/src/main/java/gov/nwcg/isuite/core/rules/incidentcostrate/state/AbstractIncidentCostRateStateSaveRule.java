package gov.nwcg.isuite.core.rules.incidentcostrate.state;

import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentCostRateStateSaveRule extends AbstractRule {
	protected IncidentCostRateStateVo vo;
	protected String costRateCategory;
	protected Long incidentId;
	protected Long groupId;
	
	public AbstractIncidentCostRateStateSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentCostRateStateSaveRuleFactory.ObjectTypeEnum.INCCOST_RATE_STATE_VO.name()))
			vo = (IncidentCostRateStateVo)object;
		if(objectName.equals(IncidentCostRateStateSaveRuleFactory.ObjectTypeEnum.COST_RATE_CATEGORY.name()))
			costRateCategory = (String)object;
		if(objectName.equals(IncidentCostRateStateSaveRuleFactory.ObjectTypeEnum.INCIDENT_ID.name()))
			incidentId = (Long)object;
		if(objectName.equals(IncidentCostRateStateSaveRuleFactory.ObjectTypeEnum.GROUP_ID.name()))
			groupId = (Long)object;
	}
	
}
