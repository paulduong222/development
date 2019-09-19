package gov.nwcg.isuite.core.rules.iap.deletebranchcomm;

import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteBranchCommRule extends AbstractRule {
	protected IapBranchCommSummaryVo iapBranchCommSummaryVo;
	
	public AbstractDeleteBranchCommRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteBranchCommRuleFactory.ObjectTypeEnum.IAP_BRANCH_COMM_SUMMARY_VO.name()))
			iapBranchCommSummaryVo = (IapBranchCommSummaryVo)object;
	}
}

