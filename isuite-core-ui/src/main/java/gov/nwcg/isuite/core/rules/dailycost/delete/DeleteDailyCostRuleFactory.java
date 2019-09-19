package gov.nwcg.isuite.core.rules.dailycost.delete;

import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DeleteDailyCostRuleFactory {
	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_DAILY_COST_VO
		,INCIDENT_RESOURCE_DAILY_COST_DAO
	}
	
	public enum RuleEnum {
		CHECK_FOR_MANUAL_COST(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,IncidentResourceDailyCostVo irdcVo, IncidentResourceDailyCostDao dao ) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance=new CheckForManualCostRules(ctx);
				break;
			case 20:
				break;
			case 30:
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(irdcVo, ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
