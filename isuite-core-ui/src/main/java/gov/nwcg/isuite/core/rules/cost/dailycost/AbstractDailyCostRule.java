package gov.nwcg.isuite.core.rules.cost.dailycost;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDailyCostRule extends AbstractRule {
	protected IncidentResource irEntity=null;
	protected IncidentResourceOther iroEntity=null;
	
	public AbstractDailyCostRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE.name()))
			irEntity = (IncidentResource)object;
		if(objectName.equals(DailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_OTHER.name()))
			iroEntity = (IncidentResourceOther)object; 
	}
}
