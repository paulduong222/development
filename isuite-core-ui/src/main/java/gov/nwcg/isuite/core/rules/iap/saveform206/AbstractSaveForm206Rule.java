package gov.nwcg.isuite.core.rules.iap.saveform206;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.vo.IapForm206Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm206Rule extends AbstractRule {
	protected IapForm206Vo iapForm206Vo;
	protected IapForm206 entity;
	
	public AbstractSaveForm206Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm206RuleFactory.ObjectTypeEnum.IAP_FORM_206_VO.name()))
			iapForm206Vo = (IapForm206Vo)object;
		if(objectName.equals(SaveForm206RuleFactory.ObjectTypeEnum.IAP_FORM_206_ENTITY.name()))
			entity = (IapForm206)object;
	}
}

