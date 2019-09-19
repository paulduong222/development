package gov.nwcg.isuite.core.rules.iap.saveform220;

import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm220RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_220_VO
		,IAP_FORM_220_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm220Vo vo, IapForm220 entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_220_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_220_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

