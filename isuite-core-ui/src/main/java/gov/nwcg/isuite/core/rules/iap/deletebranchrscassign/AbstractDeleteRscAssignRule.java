package gov.nwcg.isuite.core.rules.iap.deletebranchrscassign;

import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteRscAssignRule extends AbstractRule {
	protected IapBranchRscAssignVo iapBranchRscAssignVo;
	
	public AbstractDeleteRscAssignRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_VO.name()))
			iapBranchRscAssignVo = (IapBranchRscAssignVo)object;
	}
}

