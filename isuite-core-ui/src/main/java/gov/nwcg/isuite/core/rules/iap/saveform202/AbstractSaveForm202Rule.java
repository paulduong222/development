package gov.nwcg.isuite.core.rules.iap.saveform202;

import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.vo.IapForm202Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm202Rule extends AbstractRule {
	protected IapForm202Vo iapForm202Vo;
	protected IapForm202 entity;
	
	public AbstractSaveForm202Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm202RuleFactory.ObjectTypeEnum.IAP_FORM_202_VO.name()))
			iapForm202Vo = (IapForm202Vo)object;
		if(objectName.equals(SaveForm202RuleFactory.ObjectTypeEnum.IAP_FORM_202_ENTITY.name()))
			entity = (IapForm202)object;
	}
}

