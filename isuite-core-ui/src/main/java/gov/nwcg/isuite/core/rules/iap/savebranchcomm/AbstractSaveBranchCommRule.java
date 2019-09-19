package gov.nwcg.isuite.core.rules.iap.savebranchcomm;

import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveBranchCommRule extends AbstractRule {
	protected IapBranchCommSummaryVo iapBranchCommSummaryVo;
	protected IapBranchCommSummary entity;
	
	public AbstractSaveBranchCommRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveBranchCommRuleFactory.ObjectTypeEnum.IAP_BRANCH_COMM_SUMMARY_VO.name()))
			iapBranchCommSummaryVo = (IapBranchCommSummaryVo)object;
		if(objectName.equals(SaveBranchCommRuleFactory.ObjectTypeEnum.IAP_BRANCH_COMM_SUMMARY_ENTITY.name()))
			entity = (IapBranchCommSummary)object;
	}
}

