package gov.nwcg.isuite.core.rules.contractorrate.delete;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.persistence.ContractorRateDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorRateDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		CONTRACTOR_RATE
		,CONTRACTOR_RATE_DAO
	}
	
	public enum RuleEnum {
		CHECK_TIME_POSTING_INCLUSION(5)
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
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, ContractorRate entity, ContractorRateDao contractorRateDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new CheckIncludedInTimePostingRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.CONTRACTOR_RATE.name());
		}
		if(null != ruleInstance){
			ruleInstance.setObject(contractorRateDao, ObjectTypeEnum.CONTRACTOR_RATE_DAO.name());
		}
		
		return ruleInstance;
	}

}
