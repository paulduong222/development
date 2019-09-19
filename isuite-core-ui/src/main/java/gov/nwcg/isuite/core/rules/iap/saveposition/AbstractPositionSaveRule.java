package gov.nwcg.isuite.core.rules.iap.saveposition;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractPositionSaveRule extends AbstractRule {
	
	IapPositionVo iapPositionVo;
	
	public AbstractPositionSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PositionSaveRuleFactory.ObjectTypeEnum.IAP_POSITION_VO.name()))
			iapPositionVo = (IapPositionVo)object;
	}

}
