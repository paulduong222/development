package gov.nwcg.isuite.core.rules.iap.deletebranchposition;

import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteBranchPositionRule extends AbstractRule {
	protected BranchPositionVo branchPositionVo;
	
	public AbstractDeleteBranchPositionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteBranchPositionRuleFactory.ObjectTypeEnum.BRANCH_POSITION_VO.name()))
			branchPositionVo = (BranchPositionVo)object;
	}
}

