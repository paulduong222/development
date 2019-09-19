package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveDailyCostRule extends AbstractRule {
	protected IncidentResourceDailyCostVo irdcVo=null;
	protected IncidentResourceDailyCost dailyCost=null;
	protected IncidentResource irEntity=null;
	protected IncidentResourceOther iroEntity=null;
	
	public AbstractSaveDailyCostRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_VO.name()))
			irdcVo = (IncidentResourceDailyCostVo)object;
		if(objectName.equals(SaveDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_ENTITY.name()))
			dailyCost = (IncidentResourceDailyCost)object;
		if(objectName.equals(SaveDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name()))
			irEntity = (IncidentResource)object;
		if(objectName.equals(SaveDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name()))
			iroEntity = (IncidentResourceOther)object;
	}
	
	
}
