package gov.nwcg.isuite.core.rules.contractor.save;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractContractorSaveRule extends AbstractRule {
	protected ContractorVo vo;
	protected Contractor entity;
	protected ContractorDao dao;
	protected ContractorVo originalVo;
	
	public AbstractContractorSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorSaveRuleFactory.ObjectTypeEnum.CONTRACTOR_VO.name()))
			vo = (ContractorVo)object;
		if(objectName.equals(ContractorSaveRuleFactory.ObjectTypeEnum.CONTRACTOR_ENTITY.name()))
			entity = (Contractor)object;
		if(objectName.equals(ContractorSaveRuleFactory.ObjectTypeEnum.CONTRACTOR_DAO.name()))
			dao = (ContractorDao)object;
		if(objectName.equals(ContractorSaveRuleFactory.ObjectTypeEnum.ORIGINAL_CONTRACTOR_VO.name()))
			originalVo = (ContractorVo)object;
	}
	
	
}
