package gov.nwcg.isuite.core.rules.iap.save203position;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveForm203PositionRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_PERSONNEL_VO
		,IAP_PERSONNEL_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapPersonnelVo vo, IapPersonnel entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateForm203PositionDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_PERSONNEL_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_PERSONNEL_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

