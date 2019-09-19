package gov.nwcg.isuite.core.rules.contractor.deleteagreement;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorAgreementDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		CONTRACTOR_AGREEMENT
		,CONTRACTOR_AGREEMENT_DAO
	}
	
	public enum RuleEnum {
		CHECK_TIME_POSTINGS(5)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}	
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, ContractorAgreement entity, ContractorAgreementDao contractorAgreementDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new CheckTimePostingRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.CONTRACTOR_AGREEMENT.name());
		}
		if(null != ruleInstance){
			ruleInstance.setObject(contractorAgreementDao, ObjectTypeEnum.CONTRACTOR_AGREEMENT_DAO.name());
		}
		
		return ruleInstance;
	}

}
