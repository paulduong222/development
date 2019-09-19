package gov.nwcg.isuite.core.rules.iap.savearealoccap;

import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAreaLocCapRule extends AbstractRule {
	protected IapAreaLocationCapabilityVo iapAreaLocationCapabilityVo;
	protected IapAreaLocationCapability entity;
	
	public AbstractSaveAreaLocCapRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveAreaLocCapRuleFactory.ObjectTypeEnum.IAP_AREA_LOCATION_CAPABILITY_VO.name()))
			iapAreaLocationCapabilityVo = (IapAreaLocationCapabilityVo)object;
		if(objectName.equals(SaveAreaLocCapRuleFactory.ObjectTypeEnum.IAP_AREA_LOCATION_CAPABILITY_ENTITY.name()))
			entity = (IapAreaLocationCapability)object;
	}
}

