package gov.nwcg.isuite.core.rules.iap.savebranchsetting;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveBranchSettingRuleFactory {
	
	public enum ObjectTypeEnum {
		BRANCH_SETTING_VO
		,BRANCH_SETTING_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, BranchSettingVo vo, BranchSetting entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateBranchSettingDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.BRANCH_SETTING_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.BRANCH_SETTING_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

