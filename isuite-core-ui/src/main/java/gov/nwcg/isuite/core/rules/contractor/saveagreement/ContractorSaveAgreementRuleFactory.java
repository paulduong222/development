package gov.nwcg.isuite.core.rules.contractor.saveagreement;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorSaveAgreementRuleFactory {

	public enum ObjectTypeEnum {
		CONTRACTOR_AGREEMENT_VO
		,CONTRACTOR_AGREEMENT_DAO
		,CONTRACTOR_AGREEMENT_ENTITY
		,ORIGINAL_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		,CHECK_UNIQUE_AGREEMENT_NUMBER(10)
		,NUMBER_HISTORY(15)
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
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, ContractorAgreementVo vo, 
					ContractorAgreement entity, ContractorAgreementDao dao, ContractorAgreementVo originalVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckUniqueAgreementNumberRules(ctx);
				break;
			case 15:
				ruleInstance = new AgreementNumberHistoryRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.CONTRACTOR_AGREEMENT_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.CONTRACTOR_AGREEMENT_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.CONTRACTOR_AGREEMENT_ENTITY.name());
			ruleInstance.setObject(originalVo, ObjectTypeEnum.ORIGINAL_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
