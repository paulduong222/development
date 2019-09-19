package gov.nwcg.isuite.core.rules.iap.copyform;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCopyFormRule extends AbstractRule {
	
	IapCopyVo iapCopyVo;
	
	public AbstractCopyFormRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CopyFormRuleFactory.ObjectTypeEnum.IAP_COPY_VO.name()))
			iapCopyVo = (IapCopyVo)object;
	}

}
