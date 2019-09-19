package gov.nwcg.isuite.core.rules.iap.saveform;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.vo.IapFormVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveFormRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_FORM_VO
		,IAP_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_FORM_202_DATA(10)
		,VALIDATE_FORM_203_DATA(20)
		,VALIDATE_FORM_204_DATA(30)
		,VALIDATE_FORM_205_DATA(40)
		,VALIDATE_FORM_206_DATA(50)
		,VALIDATE_FORM_220_DATA(60)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapFormVo vo, Iap entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new Validate202DataRules(ctx);
				break;		
			case 20:
				ruleInstance = new Validate203DataRules(ctx);
				break;		
			case 30:
				ruleInstance = new Validate204DataRules(ctx);
				break;		
			case 40:
				ruleInstance = new Validate205DataRules(ctx);
				break;		
			case 50:
				ruleInstance = new Validate206DataRules(ctx);
				break;		
			case 60:
				ruleInstance = new Validate220DataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_FORM_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

