package gov.nwcg.isuite.core.rules.iap.saveform205;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.vo.IapForm205Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm205Rule extends AbstractRule {
	protected IapForm205Vo iapForm205Vo;
	protected IapForm205 entity;
	
	public AbstractSaveForm205Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm205RuleFactory.ObjectTypeEnum.IAP_FORM_205_VO.name()))
			iapForm205Vo = (IapForm205Vo)object;
		if(objectName.equals(SaveForm205RuleFactory.ObjectTypeEnum.IAP_FORM_205_ENTITY.name()))
			entity = (IapForm205)object;
	}
}

