package gov.nwcg.isuite.core.rules.iap.addbranchrscassign;

import gov.nwcg.isuite.core.persistence.IapBranchRscAssignDao;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AddBranchRscAssignRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_BRANCH_RSC_ASSIGN_PLAN_ID
		,IAP_BRANCH_RSC_ASSIGN_RESOURCE_LIST
		,IAP_BRANCH_RSC_ASSIGN_DAO
	}
	
	public enum RuleEnum {
		REASSIGN_RESOURCE(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, 
									ApplicationContext ctx, 
									Long iapPlanId, 
									Collection<IapBranchRscAssignVo> resourceBeingAdded) throws Exception {
		IRule ruleInstance = null;
		
		IapBranchRscAssignDao iapBranchRscAssignDao = (IapBranchRscAssignDao)ctx.getBean("iapBranchRscAssignDao");
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ReAssignBranchRscAssignRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(iapPlanId, ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_PLAN_ID.name());
			ruleInstance.setObject(resourceBeingAdded, ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_RESOURCE_LIST.name());
			ruleInstance.setObject(iapBranchRscAssignDao, ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_DAO.name());
		}
		
		return ruleInstance;
	}

}

