package gov.nwcg.isuite.core.rules.iap.savebranchsetting;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveBranchSettingRule extends AbstractRule {
	protected BranchSettingVo branchSettingVo;
	protected BranchSetting entity;
	
	public AbstractSaveBranchSettingRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveBranchSettingRuleFactory.ObjectTypeEnum.BRANCH_SETTING_VO.name()))
			branchSettingVo = (BranchSettingVo)object;
		if(objectName.equals(SaveBranchSettingRuleFactory.ObjectTypeEnum.BRANCH_SETTING_ENTITY.name()))
			entity = (BranchSetting)object;
	}
}

