package gov.nwcg.isuite.core.rules.iap.deletearealoccap;

import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteAreaLocCapRule extends AbstractRule {
	protected IapAreaLocationCapabilityVo iapAreaLocationCapabilityVo;
	
	public AbstractDeleteAreaLocCapRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteAreaLocCapRuleFactory.ObjectTypeEnum.IAP_AREA_LOCATION_CAPABILITY_VO.name()))
			iapAreaLocationCapabilityVo = (IapAreaLocationCapabilityVo)object;
	}
}

