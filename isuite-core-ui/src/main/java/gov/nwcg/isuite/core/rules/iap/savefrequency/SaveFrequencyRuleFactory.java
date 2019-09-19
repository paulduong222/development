package gov.nwcg.isuite.core.rules.iap.savefrequency;

import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveFrequencyRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FREQUENCY_VO
		,IAP_FREQUENCY_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapFrequencyVo vo, IapFrequency entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateFrequencyDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FREQUENCY_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FREQUENCY_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

