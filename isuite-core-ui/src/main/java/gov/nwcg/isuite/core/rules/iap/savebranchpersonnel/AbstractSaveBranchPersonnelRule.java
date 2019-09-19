package gov.nwcg.isuite.core.rules.iap.savebranchpersonnel;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveBranchPersonnelRule extends AbstractRule {
	protected IapBranchPersonnelVo iapBranchPersonnelVo;
	protected IapBranchPersonnel entity;
	protected Long iapForm204Id;
	
	public AbstractSaveBranchPersonnelRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveBranchPersonnelRuleFactory.ObjectTypeEnum.IAP_BRANCH_PERSONNEL_VO.name()))
			iapBranchPersonnelVo = (IapBranchPersonnelVo)object;
		if(objectName.equals(SaveBranchPersonnelRuleFactory.ObjectTypeEnum.IAP_BRANCH_PERSONNEL_ENTITY.name()))
			entity = (IapBranchPersonnel)object;
		if(objectName.equals(SaveBranchPersonnelRuleFactory.ObjectTypeEnum.IAP_FORM_204_ID.name()))
			iapForm204Id = (Long)object;
	}
}

