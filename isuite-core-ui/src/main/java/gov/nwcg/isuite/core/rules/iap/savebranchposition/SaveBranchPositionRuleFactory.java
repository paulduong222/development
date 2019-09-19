package gov.nwcg.isuite.core.rules.iap.savebranchposition;

import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveBranchPositionRuleFactory {
	
	public enum ObjectTypeEnum {
		BRANCH_POSITION_VO
		,BRANCH_SETTING_ID
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, BranchPositionVo vo, Long branchSettingId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateBranchPositionDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.BRANCH_POSITION_VO.name());
			ruleInstance.setObject(branchSettingId, ObjectTypeEnum.BRANCH_SETTING_ID.name());
		}
		
		return ruleInstance;
	}

}

