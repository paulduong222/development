package gov.nwcg.isuite.core.rules.iap.saveform204;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm204RuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_204_VO
		,IAP_FORM_204_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_204_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapForm204Vo vo, IapBranch entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new Validate204DataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_204_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_FORM_204_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

