package gov.nwcg.isuite.core.rules.iap.saveairambulance;

import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveAirAmbulanceRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_MEDICAL_AID_VO
		,IAP_MEDICAL_AID_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapMedicalAidVo vo, IapMedicalAid entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateAirAmbulanceDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_MEDICAL_AID_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_MEDICAL_AID_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

