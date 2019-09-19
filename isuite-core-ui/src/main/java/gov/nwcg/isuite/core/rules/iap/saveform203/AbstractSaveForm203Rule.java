package gov.nwcg.isuite.core.rules.iap.saveform203;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.vo.IapForm203Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm203Rule extends AbstractRule {
	protected IapForm203Vo iapForm203Vo;
	protected IapForm203 entity;
	
	public AbstractSaveForm203Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm203RuleFactory.ObjectTypeEnum.IAP_FORM_203_VO.name()))
			iapForm203Vo = (IapForm203Vo)object;
		if(objectName.equals(SaveForm203RuleFactory.ObjectTypeEnum.IAP_FORM_203_ENTITY.name()))
			entity = (IapForm203)object;
	}
}

