package gov.nwcg.isuite.core.rules.iap.deletehelicopter;

import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DeleteHelicopterRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_AIRCRAFT_VO
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapAircraftVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_AIRCRAFT_VO.name());
		}
		
		return ruleInstance;
	}

}

