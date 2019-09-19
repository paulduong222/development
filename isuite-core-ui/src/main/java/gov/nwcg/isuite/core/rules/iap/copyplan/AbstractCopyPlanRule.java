package gov.nwcg.isuite.core.rules.iap.copyplan;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCopyPlanRule extends AbstractRule {
	
	IapPlanVo iapPlanSourceVo;
	IapPlanVo iapPlanDestinationVo;
	
	public AbstractCopyPlanRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CopyPlanRuleFactory.ObjectTypeEnum.IAP_PLAN_SOURCE_VO.name()))
			iapPlanSourceVo = (IapPlanVo)object;
		if(objectName.equals(CopyPlanRuleFactory.ObjectTypeEnum.IAP_PLAN_DESTINATION_VO.name()))
			iapPlanDestinationVo = (IapPlanVo)object;
		
	}

}
