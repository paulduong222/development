package gov.nwcg.isuite.core.rules.costgroups.deleteallocationdate;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.costgroups.save.CheckPermissionsRules;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CostGroupsDeleteAllocationDateRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_AGENCY_DAY_SHARE_ENTITY
		,COST_GROUP_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostGroupAgencyDayShare entity, CostGroupDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.COST_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}

}
