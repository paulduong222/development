package gov.nwcg.isuite.core.rules.iap.deletefrequency;

import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteFrequencyRule extends AbstractRule {
	protected IapFrequencyVo iapFrequencyVo;
	
	public AbstractDeleteFrequencyRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteFrequencyRuleFactory.ObjectTypeEnum.IAP_FREQUENCY_VO.name()))
			iapFrequencyVo = (IapFrequencyVo)object;
	}
}

