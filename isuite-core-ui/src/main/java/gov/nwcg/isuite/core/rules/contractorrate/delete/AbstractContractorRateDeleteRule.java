package gov.nwcg.isuite.core.rules.contractorrate.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.persistence.ContractorRateDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractContractorRateDeleteRule extends AbstractRule {
	
	protected ContractorRate entity;
	protected ContractorRateDao dao;
	
	public AbstractContractorRateDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorRateDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR_RATE.name()))
			entity = (ContractorRate)object;
		if(objectName.equals(ContractorRateDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR_RATE_DAO.name()))
			dao = (ContractorRateDao)object;
	}

}
