package gov.nwcg.isuite.core.rules.contractor.saveagreement;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAgreementRule extends AbstractRule {
	protected ContractorAgreementVo vo;
	protected ContractorAgreementDao dao;
	protected ContractorAgreement entity;
	protected ContractorAgreementVo originalVo;
	
	
	public AbstractSaveAgreementRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorSaveAgreementRuleFactory.ObjectTypeEnum.CONTRACTOR_AGREEMENT_VO.name()))
			vo = (ContractorAgreementVo)object;
		if(objectName.equals(ContractorSaveAgreementRuleFactory.ObjectTypeEnum.CONTRACTOR_AGREEMENT_DAO.name()))
			dao = (ContractorAgreementDao)object;
		if(objectName.equals(ContractorSaveAgreementRuleFactory.ObjectTypeEnum.CONTRACTOR_AGREEMENT_ENTITY.name()))
			entity = (ContractorAgreement)object;
		if(objectName.equals(ContractorSaveAgreementRuleFactory.ObjectTypeEnum.ORIGINAL_VO.name()))
			originalVo = (ContractorAgreementVo)object;
	}
	
	
}
