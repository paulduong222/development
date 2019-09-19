package gov.nwcg.isuite.core.rules.iap.savehospital;

import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveHospitalRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_HOSPITAL_VO
		,IAP_HOSPITAL_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapHospitalVo vo, IapHospital entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateHospitalDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_HOSPITAL_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_HOSPITAL_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

