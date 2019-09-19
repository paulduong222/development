package gov.nwcg.isuite.core.rules.iap.saveform;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.vo.IapFormVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveFormRule extends AbstractRule {
	protected IapFormVo iapFormVo;
	protected Iap entity;
	
	public AbstractSaveFormRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveFormRuleFactory.ObjectTypeEnum.IAP_FORM_VO.name()))
			iapFormVo = (IapFormVo)object;
		if(objectName.equals(SaveFormRuleFactory.ObjectTypeEnum.IAP_ENTITY.name()))
			entity = (Iap)object;
	}
}

