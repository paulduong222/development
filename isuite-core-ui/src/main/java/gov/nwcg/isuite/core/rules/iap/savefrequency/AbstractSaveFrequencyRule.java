package gov.nwcg.isuite.core.rules.iap.savefrequency;

import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveFrequencyRule extends AbstractRule {
	protected IapFrequencyVo iapFrequencyVo;
	protected IapFrequency entity;
	
	public AbstractSaveFrequencyRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveFrequencyRuleFactory.ObjectTypeEnum.IAP_FREQUENCY_VO.name()))
			iapFrequencyVo = (IapFrequencyVo)object;
		if(objectName.equals(SaveFrequencyRuleFactory.ObjectTypeEnum.IAP_FREQUENCY_ENTITY.name()))
			entity = (IapFrequency)object;
	}
}

