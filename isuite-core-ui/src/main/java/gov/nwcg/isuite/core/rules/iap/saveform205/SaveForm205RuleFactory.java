package gov.nwcg.isuite.core.rules.iap.saveform205;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.vo.IapForm205Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm205RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_205_VO
		,IAP_FORM_205_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm205Vo vo, IapForm205 entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				//ruleInstance = new ValidateDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_205_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_205_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

