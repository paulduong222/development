package gov.nwcg.isuite.core.rules.iap.addbranchrscassign;

import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IapBranchRscAssignDao;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractAddBranchRscAssignRule extends AbstractRule {
	protected Long iapPlanId;
	protected Collection<IapBranchRscAssignVo> resourcesBeingAdded;
	protected IapBranchRscAssignDao iapBranchRscAssignDao;
	
	public AbstractAddBranchRscAssignRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(AddBranchRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_PLAN_ID.name()))
			iapPlanId = (Long)object;
		if(objectName.equals(AddBranchRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_RESOURCE_LIST.name()))
			resourcesBeingAdded = (Collection<IapBranchRscAssignVo>)object;
		if(objectName.equals(AddBranchRscAssignRuleFactory.ObjectTypeEnum.IAP_BRANCH_RSC_ASSIGN_DAO.name()))
			iapBranchRscAssignDao = (IapBranchRscAssignDao)object;
	}
}

