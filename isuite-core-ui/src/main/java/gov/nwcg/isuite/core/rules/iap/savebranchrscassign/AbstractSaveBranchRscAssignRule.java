package gov.nwcg.isuite.core.rules.iap.savebranchrscassign;

import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveBranchRscAssignRule extends AbstractRule {
	protected IapBranchRscAssignVo iapBranchRscAssignVo;
	protected IapBranchRscAssign entity;
	
	public AbstractSaveBranchRscAssignRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveBranchRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_VO.name()))
			iapBranchRscAssignVo = (IapBranchRscAssignVo)object;
		if(objectName.equals(SaveBranchRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_ENTITY.name()))
			entity = (IapBranchRscAssign)object;
	}
}

