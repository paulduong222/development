package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RunExtractRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_ACCRUAL_DAO
		,EXTRACT_DATE_STRING
		,INCIDENT_ID
		,INCIDENT_GROUP_ID
	}
	
	public enum RuleEnum {
		CHECK_IF_FINALIZED(10)
		//,CHECK_SKIPPED_DATE_PROMPT(20)
		,CHECK_NOT_BEFORE_LAST_DATE(25)
		,CHECK_PREVIOUS_FINALIZED(30)
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
	 * @param extractDate
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostAccrualExtractDao dao, String extractDate, Long incidentId, Long incidentGroupId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckAlreadyFinalizedRules(ctx);
				break;
			case 20:
				//ruleInstance = new CheckSkippedDateRules(ctx);
				break;
			case 25:
				ruleInstance = new CheckNotBeforeLastDateRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckPreviousFinalizedRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(dao, ObjectTypeEnum.COST_ACCRUAL_DAO.name());
			ruleInstance.setObject(extractDate, ObjectTypeEnum.EXTRACT_DATE_STRING.name());
			ruleInstance.setObject(incidentId, ObjectTypeEnum.INCIDENT_ID.name());
			ruleInstance.setObject(incidentGroupId, ObjectTypeEnum.INCIDENT_GROUP_ID.name());
		}
		
		return ruleInstance;
	}
}
