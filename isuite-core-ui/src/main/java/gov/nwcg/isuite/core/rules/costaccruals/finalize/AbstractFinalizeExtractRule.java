package gov.nwcg.isuite.core.rules.costaccruals.finalize;

import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractFinalizeExtractRule extends AbstractRule {
	protected CostAccrualExtractVo extractVo;
	
	public AbstractFinalizeExtractRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(FinalizeExtractRuleFactory.ObjectTypeEnum.COST_ACCRUAL_EXTRACT_VO.name()))
			extractVo=(CostAccrualExtractVo)object;
	}
}
