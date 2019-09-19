package gov.nwcg.isuite.core.rules.dailycost.runmanualcost;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RunManualDailyCostRuleFactory {
	public enum ObjectTypeEnum {
		COST_RESOURCE_DATA_VO
		,INCIDENT_RESOURCE_OTHER_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_STATUS(5)
		,VALIDATE_AGENCY(10)
		,VALIDATE_ASSIGN_DATE(20)
		,VALIDATE_NO_AGENCY_HAS_POSTINGS(23)
		,VALIDATE_NO_AGENCY_NO_POSTINGS(26)
		,CHECK_DEF_ACCOUNTING_CODE(30)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,CostResourceDataVo costResourceDataVo, IncidentResourceOther iroEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				// don't use anymore - ruleInstance = new CheckStatusRules(ctx);
				ruleInstance = new CheckStatusRules(ctx);
				break;
			case 10:
				//replaced by 23 and 26  - ruleInstance = new ValidateAgencyDataRules(ctx);
				break;
			case 20:
				//ruleInstance = new ValidateAssignDateDataRules(ctx);
				break;
			case 23:
				//ruleInstance = new ValidateNoAgencyHasPostingsRules(ctx);
				break;
			case 26:
				//ruleInstance = new ValidateNoAgencyNoPostingsRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckDefAccountingCodeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(costResourceDataVo, ObjectTypeEnum.COST_RESOURCE_DATA_VO.name());
			ruleInstance.setObject(iroEntity, ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
