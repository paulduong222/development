package gov.nwcg.isuite.core.rules.iap.savebranchrscassign;

import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.rules.iap.savebranchcomm.ValidateBranchCommDataRules;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveBranchRscAssignRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_BRANCH_RSC_ASSIGN_VO
		,IAP_BRANCH_RSC_ASSIGN_ENTITY
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapBranchRscAssignVo vo, IapBranchRscAssign entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateBranchRscAssignRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

