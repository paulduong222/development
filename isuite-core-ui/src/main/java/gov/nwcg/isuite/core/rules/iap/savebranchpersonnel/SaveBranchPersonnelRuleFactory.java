package gov.nwcg.isuite.core.rules.iap.savebranchpersonnel;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveBranchPersonnelRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_BRANCH_PERSONNEL_VO
		,IAP_BRANCH_PERSONNEL_ENTITY
		,IAP_FORM_204_ID
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		,CHECK_MAX_204_POSITIONS(30)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapBranchPersonnelVo vo
										, IapBranchPersonnel entity, Long iapForm204Id) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateBranchPersonnelDataRules(ctx);
				break;		
			case 30:
				ruleInstance = new CheckMax204PositionsRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_BRANCH_PERSONNEL_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_BRANCH_PERSONNEL_ENTITY.name());
			ruleInstance.setObject(iapForm204Id, ObjectTypeEnum.IAP_FORM_204_ID.name());
		}
		
		return ruleInstance;
	}

}

