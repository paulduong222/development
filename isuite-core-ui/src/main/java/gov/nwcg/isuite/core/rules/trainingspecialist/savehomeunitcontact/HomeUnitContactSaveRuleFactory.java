package gov.nwcg.isuite.core.rules.trainingspecialist.savehomeunitcontact;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class HomeUnitContactSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		HOME_UNIT_CONTACT_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx,
			ResourceHomeUnitContactVo homeUnitContactVo) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(homeUnitContactVo, ObjectTypeEnum.HOME_UNIT_CONTACT_VO.name());
		}
		
		
		return ruleInstance;
		
	}

}
