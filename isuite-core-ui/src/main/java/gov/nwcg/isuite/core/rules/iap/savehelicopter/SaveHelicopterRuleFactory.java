package gov.nwcg.isuite.core.rules.iap.savehelicopter;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveHelicopterRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_AIRCRAFT_VO
		,IAP_AIRCRAFT_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapAircraftVo vo, IapAircraft entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateHelicopterDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_AIRCRAFT_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_AIRCRAFT_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

