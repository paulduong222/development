package gov.nwcg.isuite.core.rules.iap.deletebranchpersonnel;

import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteBranchPersonnelRule extends AbstractRule {
	protected IapBranchPersonnelVo iapBranchPersonnelVo;
	
	public AbstractDeleteBranchPersonnelRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteBranchPersonnelRuleFactory.ObjectTypeEnum.IAP_BRANCH_PERSONNEL_VO.name()))
			iapBranchPersonnelVo = (IapBranchPersonnelVo)object;
	}
}

