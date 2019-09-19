package gov.nwcg.isuite.core.rules.iap.saveplan;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSavePlanRule extends AbstractRule {
	protected IapPlanVo iapPlanVo;
	protected IapPlan entity;
	
	public AbstractSavePlanRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SavePlanRuleFactory.ObjectTypeEnum.IAP_PLAN_VO.name()))
			iapPlanVo = (IapPlanVo)object;
		if(objectName.equals(SavePlanRuleFactory.ObjectTypeEnum.IAP_PLAN_ENTITY.name()))
			entity = (IapPlan)object;
	}
}

