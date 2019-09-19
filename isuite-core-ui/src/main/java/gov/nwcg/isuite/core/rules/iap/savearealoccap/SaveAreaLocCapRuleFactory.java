package gov.nwcg.isuite.core.rules.iap.savearealoccap;

import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveAreaLocCapRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_AREA_LOCATION_CAPABILITY_VO
		,IAP_AREA_LOCATION_CAPABILITY_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapAreaLocationCapabilityVo vo, IapAreaLocationCapability entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateAreaLocCapDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_AREA_LOCATION_CAPABILITY_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_AREA_LOCATION_CAPABILITY_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

