package gov.nwcg.isuite.core.rules.incidentcostrate.ovhd;

import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentCostRateOvhdSaveRule extends AbstractRule {
	protected IncidentCostRateOvhdVo vo;
	protected String costRateCategory;
	protected Long incidentId;
	protected Long groupId;
	
	public AbstractIncidentCostRateOvhdSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentCostRateOvhdSaveRuleFactory.ObjectTypeEnum.INCCOST_RATE_OVHD_VO.name()))
			vo = (IncidentCostRateOvhdVo)object;
		if(objectName.equals(IncidentCostRateOvhdSaveRuleFactory.ObjectTypeEnum.COST_RATE_CATEGORY.name()))
			costRateCategory = (String)object;
		if(objectName.equals(IncidentCostRateOvhdSaveRuleFactory.ObjectTypeEnum.INCIDENT_ID.name()))
			incidentId = (Long)object;
		if(objectName.equals(IncidentCostRateOvhdSaveRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ID.name()))
			groupId = (Long)object;
	}
	
}
