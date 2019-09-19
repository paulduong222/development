package gov.nwcg.isuite.core.rules.iap.saveform206;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.vo.IapForm206Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm206RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_206_VO
		,IAP_FORM_206_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_206_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm206Vo vo, IapForm206 entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new Validate206DataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_206_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_206_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

