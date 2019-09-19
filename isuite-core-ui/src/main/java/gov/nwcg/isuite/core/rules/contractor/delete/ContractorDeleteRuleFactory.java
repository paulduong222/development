package gov.nwcg.isuite.core.rules.contractor.delete;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		CONTRACTOR
		,CONTRACTOR_DAO
	}
	
	public enum RuleEnum {
		CHECK_TIME_POSTINGS(5)
		,CHECK_NOT_USED_IN_INVOICE(10)	
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
	 * @param contractorDao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Contractor entity, ContractorDao contractorDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new CheckTimePostingRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckNotUsedInInvoiceRules(ctx);
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.CONTRACTOR.name());
			ruleInstance.setObject(contractorDao, ObjectTypeEnum.CONTRACTOR_DAO.name());
		}
		
		return ruleInstance;
	}

}
