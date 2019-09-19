package gov.nwcg.isuite.core.rules.contractor.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractContractorDeleteRule extends AbstractRule {
	
	protected Contractor entity;
	protected ContractorDao dao;
	
	public AbstractContractorDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR.name()))
			entity = (Contractor)object;
		if(objectName.equals(ContractorDeleteRuleFactory.ObjectTypeEnum.CONTRACTOR_DAO.name()))
			dao = (ContractorDao)object;
	}

}
