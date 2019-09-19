package gov.nwcg.isuite.core.rules.contractorrate.save;

import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractContractorRateSaveRule extends AbstractRule {
	protected ContractorRateVo vo;
	
	public AbstractContractorRateSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ContractorRateSaveRuleFactory.ObjectTypeEnum.CONTRACTOR_RATE_VO.name()))
			vo = (ContractorRateVo)object;
	}
	
	
}
