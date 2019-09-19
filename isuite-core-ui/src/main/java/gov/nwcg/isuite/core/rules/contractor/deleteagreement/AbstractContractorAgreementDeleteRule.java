package gov.nwcg.isuite.core.rules.contractor.deleteagreement;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractContractorAgreementDeleteRule extends AbstractRule {
	
	protected ContractorAgreement entity;
	protected ContractorAgreementDao dao;
	
	public AbstractContractorAgreementDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorAgreementDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR_AGREEMENT.name()))
			entity = (ContractorAgreement)object;
		if(objectName.equals(ContractorAgreementDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR_AGREEMENT_DAO.name()))
			dao = (ContractorAgreementDao)object;
	}

}
