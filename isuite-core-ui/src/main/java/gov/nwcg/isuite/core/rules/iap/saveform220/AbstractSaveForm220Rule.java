package gov.nwcg.isuite.core.rules.iap.saveform220;

import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm220Rule extends AbstractRule {
	protected IapForm220Vo iapForm220Vo;
	protected IapForm220 entity;
	
	public AbstractSaveForm220Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm220RuleFactory.ObjectTypeEnum.IAP_FORM_220_VO.name()))
			iapForm220Vo = (IapForm220Vo)object;
		if(objectName.equals(SaveForm220RuleFactory.ObjectTypeEnum.IAP_FORM_220_ENTITY.name()))
			entity = (IapForm220)object;
	}
}

