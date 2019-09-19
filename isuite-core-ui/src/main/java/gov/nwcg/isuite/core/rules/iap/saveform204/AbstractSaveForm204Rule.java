package gov.nwcg.isuite.core.rules.iap.saveform204;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm204Rule extends AbstractRule {
	protected IapForm204Vo iapForm204Vo;
	protected IapBranch entity;
	
	public AbstractSaveForm204Rule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm204RuleFactory.ObjectTypeEnum.IAP_FORM_204_VO.name()))
			iapForm204Vo = (IapForm204Vo)object;
		if(objectName.equals(SaveForm204RuleFactory.ObjectTypeEnum.IAP_FORM_204_ENTITY.name()))
			entity = (IapBranch)object;
	}
}

