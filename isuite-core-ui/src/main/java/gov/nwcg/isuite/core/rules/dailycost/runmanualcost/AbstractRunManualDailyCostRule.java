package gov.nwcg.isuite.core.rules.dailycost.runmanualcost;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractRunManualDailyCostRule extends AbstractRule {
	protected IncidentResourceOther iroEntity=null;
	protected CostResourceDataVo costResourceDataVo;
	
	public AbstractRunManualDailyCostRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RunManualDailyCostRuleFactory.ObjectTypeEnum.COST_RESOURCE_DATA_VO.name()))
			costResourceDataVo = (CostResourceDataVo)object;
		if(objectName.equals(RunManualDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name()))
			iroEntity = (IncidentResourceOther)object;
	}
	
	
}
