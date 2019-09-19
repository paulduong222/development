package gov.nwcg.isuite.core.rules.iap.saveplanprintorder;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SavePlanPrintOrderRuleFactory {
	public enum ObjectTypeEnum {
		IAP_PLANPRINTORDER_VO
		,IAP_PLANPRINTORDER_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_PLANPRINTORDER_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapPlanPrintOrderVo vo, IapPlanPrintOrder entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidatePlanPrintOrderDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_PLANPRINTORDER_VO.name()); 
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_PLANPRINTORDER_ENTITY.name());
		}
		
		return ruleInstance;
	}
}

