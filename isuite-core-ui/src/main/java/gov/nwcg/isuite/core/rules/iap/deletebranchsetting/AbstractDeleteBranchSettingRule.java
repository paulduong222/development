package gov.nwcg.isuite.core.rules.iap.deletebranchsetting;

import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteBranchSettingRule extends AbstractRule {
	protected BranchSettingVo branchSettingVo;
	
	public AbstractDeleteBranchSettingRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteBranchSettingRuleFactory.ObjectTypeEnum.BRANCH_SETTING_VO.name()))
			branchSettingVo = (BranchSettingVo)object;
	}
}

