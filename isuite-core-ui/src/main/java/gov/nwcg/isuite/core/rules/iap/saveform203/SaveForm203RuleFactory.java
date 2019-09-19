package gov.nwcg.isuite.core.rules.iap.saveform203;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.vo.IapForm203Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm203RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_203_VO
		,IAP_FORM_203_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm203Vo vo, IapForm203 entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				//ruleInstance = new ValidateDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_203_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_203_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

