package gov.nwcg.isuite.core.rules.iap.savebranchposition;

import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveBranchPositionRule extends AbstractRule {
	protected BranchPositionVo branchPositionVo;
	protected Long branchSettingId;
	
	public AbstractSaveBranchPositionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveBranchPositionRuleFactory.ObjectTypeEnum.BRANCH_POSITION_VO.name()))
			branchPositionVo = (BranchPositionVo)object;
		if(objectName.equals(SaveBranchPositionRuleFactory.ObjectTypeEnum.BRANCH_SETTING_ID.name()))
			branchSettingId = (Long)object;
	}
}

