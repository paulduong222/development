package gov.nwcg.isuite.core.rules.iap.saveplanprintorder;



import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;

import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public abstract class AbstractSavePlanPrintOrderRule extends AbstractRule {
	protected IapPlanPrintOrderVo IapPlanPrintOrderVo;
	protected IapPlanPrintOrder entity;
	
	public AbstractSavePlanPrintOrderRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SavePlanPrintOrderRuleFactory.ObjectTypeEnum.IAP_PLANPRINTORDER_VO.name()))
			IapPlanPrintOrderVo = (IapPlanPrintOrderVo)object;
		if(objectName.equals(SavePlanPrintOrderRuleFactory.ObjectTypeEnum.IAP_PLANPRINTORDER_ENTITY.name()))
			entity = (IapPlanPrintOrder)object;
		
		
	}
}
