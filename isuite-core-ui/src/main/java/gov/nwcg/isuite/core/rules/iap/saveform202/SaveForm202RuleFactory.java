package gov.nwcg.isuite.core.rules.iap.saveform202;

import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.vo.IapForm202Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm202RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_202_VO
		,IAP_FORM_202_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_202_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm202Vo vo, IapForm202 entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new Validate202DataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_202_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_202_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

